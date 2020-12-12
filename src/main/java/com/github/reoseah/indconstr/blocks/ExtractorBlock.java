package com.github.reoseah.indconstr.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.api.blocks.WrenchableBlock;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.impl.EmptyItemExtractable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class ExtractorBlock extends AbstractConduitConnectingBlock implements BlockEntityProvider, Waterloggable, WrenchableBlock {
    public static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape[][] SHAPES = new VoxelShape[6][];
    static {
        VoxelShape[] extractors = new VoxelShape[] {
                Block.createCuboidShape(2, 0, 2, 14, 4, 14),
                Block.createCuboidShape(2, 12, 2, 14, 16, 14),
                Block.createCuboidShape(2, 2, 0, 14, 14, 4),
                Block.createCuboidShape(2, 2, 12, 14, 14, 16),
                Block.createCuboidShape(0, 2, 2, 4, 14, 14),
                Block.createCuboidShape(12, 2, 2, 16, 14, 14)
        };
        for (int j = 0; j < 6; j++) {
            SHAPES[j] = new VoxelShape[64];
            float min = 4;
            float max = 12;
            VoxelShape center = Block.createCuboidShape(min, min, min, max, max, max);
            VoxelShape[] connections = new VoxelShape[] {
                    Block.createCuboidShape(min, 0, min, max, max, max),
                    Block.createCuboidShape(min, min, min, max, 16, max),
                    Block.createCuboidShape(min, min, 0, max, max, max),
                    Block.createCuboidShape(min, min, min, max, max, 16),
                    Block.createCuboidShape(0, min, min, max, max, max),
                    Block.createCuboidShape(min, min, min, 16, max, max)
            };

            for (int i = 0; i < 64; i++) {
                VoxelShape shape = VoxelShapes.union(center, extractors[j]);
                for (int face = 0; face < 6; face++) {
                    if ((i & 1 << face) != 0) {
                        shape = VoxelShapes.union(shape, connections[face]);
                    }
                }
                SHAPES[j][i] = shape;
            }
        }
    }

    public ExtractorBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, Direction.DOWN).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DIRECTION, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(DOWN) ? 1 : 0) |
                (state.get(UP) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(SOUTH) ? 8 : 0) |
                (state.get(WEST) ? 16 : 0) |
                (state.get(EAST) ? 32 : 0);
        return SHAPES[state.get(DIRECTION).ordinal()][i];
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        WorldView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();
        for (int i = 0; i < directions.length; ++i) {
            Direction direction = directions[i];
            if (this.canExtract(state, world, pos, direction)) {
                return state.with(DIRECTION, direction);
            }
        }

        return state.with(DIRECTION, ctx.getPlayer() != null && ctx.getPlayer().isSneaking() ? ctx.getSide() : ctx.getSide().getOpposite());
    }

    public boolean canExtract(BlockState state, WorldView world, BlockPos pos, Direction direction) {
        if (world instanceof World) {
            return ItemAttributes.EXTRACTABLE.get((World) world, pos.offset(direction), SearchOptions.inDirection(direction)) != EmptyItemExtractable.NULL;
        }
        return false;
    }

    @Override
    public boolean canConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return side.getOpposite() != state.get(DIRECTION);
    }

    @Override
    protected boolean connectsTo(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof ExtractorBlock) {
            return false;
        }
        if (block instanceof LeverBlock) {
            return getLeverDirection(neighbor) == side;
        }
        return super.connectsTo(view, pos, side);
    }

    protected static Direction getLeverDirection(BlockState state) {
        switch (state.get(WallMountedBlock.FACE)) {
        case CEILING:
            return Direction.DOWN;
        case FLOOR:
            return Direction.UP;
        default:
            return state.get(HorizontalFacingBlock.FACING);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, Vec3d hitPos) {
        int start = state.get(DIRECTION).ordinal();
        for (int i = (start + 1) % 6; i != start; i = (i + 1) % 6) {
            Direction direction = Direction.values()[i];
            if (this.canExtract(state, world, pos, direction)) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION, direction));
                return true;
            }
        }
        return false;
    }
}
