package com.github.reoseah.icon.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.icon.api.blocks.CatwalkConnectingBlock;
import com.github.reoseah.icon.api.blocks.WrenchableBlock;

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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CatwalkStairsBlock extends Block implements CatwalkConnectingBlock, WrenchableBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public static final BooleanProperty RIGHT = BooleanProperty.of("right");
    public static final BooleanProperty LEFT = BooleanProperty.of("left");

    private static final VoxelShape[][] SHAPES;
    private static final VoxelShape[][] COLLISIONS;

    static {
        SHAPES = new VoxelShape[4][4];
        COLLISIONS = new VoxelShape[4][4];

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

        VoxelShape[] leftRailsCollisions = {
                VoxelShapes.union(Block.createCuboidShape(0.5, 8, 8, 1, 24, 16), Block.createCuboidShape(0.5, 16, 0, 1, 32, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0.5, 8, 24, 1), Block.createCuboidShape(8, 16, 0.5, 16, 32, 1)),
                VoxelShapes.union(Block.createCuboidShape(15, 8, 0, 15.5, 24, 8), Block.createCuboidShape(15, 16, 8, 15.5, 32, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 15, 16, 24, 15.5), Block.createCuboidShape(0, 16, 15, 8, 32, 15.5))
        };
        VoxelShape[] rightRailsCollisions = {
                VoxelShapes.union(Block.createCuboidShape(15, 8, 8, 15.5, 24, 16), Block.createCuboidShape(15, 16, 0, 15.5, 32, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 15, 8, 24, 15.5), Block.createCuboidShape(8, 16, 15, 16, 32, 15.5)),
                VoxelShapes.union(Block.createCuboidShape(0.5, 8, 0, 1, 24, 8), Block.createCuboidShape(0.5, 16, 8, 1, 32, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 0.5, 16, 24, 1), Block.createCuboidShape(0, 16, 0.5, 8, 32, 1))
        };

        for (int i = 0; i < 4; i++) {
            COLLISIONS[i][0] = SHAPES[i][0] = floors[i];
            SHAPES[i][1] = VoxelShapes.union(floors[i], leftRails[i]);
            SHAPES[i][2] = VoxelShapes.union(floors[i], rightRails[i]);
            SHAPES[i][3] = VoxelShapes.union(floors[i], leftRails[i], rightRails[i]);

            COLLISIONS[i][1] = VoxelShapes.union(floors[i], leftRailsCollisions[i]);
            COLLISIONS[i][2] = VoxelShapes.union(floors[i], rightRailsCollisions[i]);
            COLLISIONS[i][3] = VoxelShapes.union(floors[i], leftRailsCollisions[i], rightRailsCollisions[i]);
        }
    }

    public CatwalkStairsBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER).with(RIGHT, true).with(LEFT, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF, RIGHT, LEFT);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(LEFT) ? 1 : 0)
                | (state.get(RIGHT) ? 2 : 0);
        VoxelShape voxelShape = COLLISIONS[state.get(FACING).getHorizontal()][i];

        return state.get(HALF) == DoubleBlockHalf.UPPER ? voxelShape.offset(0, -1, 0) : voxelShape;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        int i = (state.get(LEFT) ? 1 : 0)
                | (state.get(RIGHT) ? 2 : 0);
        VoxelShape voxelShape = SHAPES[state.get(FACING).getHorizontal()][i];

        return state.get(HALF) == DoubleBlockHalf.UPPER ? voxelShape.offset(0, -1, 0) : voxelShape;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(LEFT) ? 1 : 0)
                | (state.get(RIGHT) ? 2 : 0);
        VoxelShape voxelShape = SHAPES[state.get(FACING).getHorizontal()][i];

        return state.get(HALF) == DoubleBlockHalf.UPPER ? voxelShape.offset(0, -1, 0) : voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(LEFT) ? 1 : 0)
                | (state.get(RIGHT) ? 2 : 0);
        VoxelShape voxelShape = SHAPES[state.get(FACING).getHorizontal()][i];

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
            if (!newState.isOf(this) || newState.get(HALF) == half) {
                return Blocks.AIR.getDefaultState();
            }
        }
        if (half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);

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
        return direction.getAxis().isHorizontal() && state2.getBlock() == this && state.get(FACING) == direction && state2.get(FACING) == direction.getOpposite() && state.get(HALF) == DoubleBlockHalf.LOWER
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public boolean shouldCatwalkConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? side == state.get(FACING).getOpposite() : side == state.get(FACING);
    }

    @Override
    public boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, Vec3d hitPos) {
        Direction facing = state.get(FACING);
        Axis perpendicular = facing.rotateYClockwise().getAxis();

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.down();
            state = world.getBlockState(pos);
        }

        if (player != null && player.isSneaking()) {
            world.setBlockState(pos, state.cycle(FACING));
            world.setBlockState(pos.up(), state.cycle(FACING).with(HALF, DoubleBlockHalf.UPPER));
            return true;
        }

        double a = hitPos.getComponentAlongAxis(perpendicular);
        if (a - pos.getComponentAlongAxis(perpendicular) > 0.5 && facing.rotateYClockwise().getDirection() == AxisDirection.POSITIVE
                || a - pos.getComponentAlongAxis(perpendicular) < 0.5 && facing.rotateYClockwise().getDirection() == AxisDirection.NEGATIVE) {
            world.setBlockState(pos, state.cycle(LEFT));
            world.setBlockState(pos.up(), state.cycle(LEFT).with(HALF, DoubleBlockHalf.UPPER));
            return true;
        } else {
            world.setBlockState(pos, state.cycle(RIGHT));
            world.setBlockState(pos.up(), state.cycle(RIGHT).with(HALF, DoubleBlockHalf.UPPER));
            return true;
        }
    }
}
