package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CatwalkStairsBlock extends Block {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public CatwalkStairsBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape[] shapes = new VoxelShape[4];

        VoxelShape[] floors = {
                VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 16, 16.0001, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 8.0001, 16), Block.createCuboidShape(8, 16, 0, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 8.0001, 8), Block.createCuboidShape(0, 16, 8, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 8, 16.0001, 16))
        };

        VoxelShape[] leftRails = {
                VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 2, 24, 16), Block.createCuboidShape(0, 16, 0, 2, 32, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 24, 2), Block.createCuboidShape(8, 16, 0, 16, 32, 2)),
                VoxelShapes.union(Block.createCuboidShape(14, 8, 0, 16, 24, 8), Block.createCuboidShape(14, 16, 8, 16, 32, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 14, 16, 24, 16), Block.createCuboidShape(0, 16, 14, 8, 32, 16))
        };
        VoxelShape[] rightRails = {
                VoxelShapes.union(Block.createCuboidShape(14, 8, 8, 16, 24, 16), Block.createCuboidShape(14, 16, 0, 16, 32, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 14, 8, 24, 16), Block.createCuboidShape(8, 16, 14, 16, 32, 16)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 2, 24, 8), Block.createCuboidShape(0, 16, 8, 2, 32, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 24, 2), Block.createCuboidShape(0, 16, 0, 8, 32, 2))
        };

        for (int i = 0; i < 4; i++) {
            shapes[i] = VoxelShapes.union(floors[i], leftRails[i], rightRails[i]);
        }
        VoxelShape voxelShape = shapes[state.get(FACING).getHorizontal()];

        return state.get(HALF) == DoubleBlockHalf.UPPER ? voxelShape.offset(0, -1, 0) : voxelShape;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                onBreakInCreative(world, pos, state, player);
            } else {
                dropStacks(state, world, pos, (BlockEntity) null, player, player.getMainHandStack());
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        DoubleBlockHalf half = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return newState.isOf(this) && newState.get(HALF) != half ? state : Blocks.AIR.getDefaultState();
        } else {
            return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf half = state.get(HALF);
        if (half == DoubleBlockHalf.UPPER) {
            BlockPos below = pos.down();
            BlockState stateBelow = world.getBlockState(below);
            if (stateBelow.getBlock() == state.getBlock() && stateBelow.get(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockState(below, Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, below, Block.getRawIdFromState(stateBelow));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return direction.getAxis().isHorizontal() && state2.getBlock() == this && (state.get(FACING) == direction && state2.get(FACING) == direction.getOpposite() && state.get(HALF) == DoubleBlockHalf.LOWER)
                || super.isSideInvisible(state, state2, direction);
    }
}
