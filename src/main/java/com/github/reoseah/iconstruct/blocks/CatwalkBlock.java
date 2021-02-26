package com.github.reoseah.iconstruct.blocks;

import com.github.reoseah.iconstruct.IConstruct;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CatwalkBlock extends Block implements Waterloggable, WrenchableBlock {
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape[] OUTLINE_SHAPES;
    private static final VoxelShape[] COLLISION_SHAPES;
    static {
        VoxelShape cutout = VoxelShapes.union(Block.createCuboidShape(0, 2, 2, 16, 13, 14), Block.createCuboidShape(2, 2, 0, 14, 13, 16));

        OUTLINE_SHAPES = new VoxelShape[16];
        COLLISION_SHAPES = new VoxelShape[16];

        VoxelShape floor = Block.createCuboidShape(0, 0, 0, 16, 0.0001, 16);

        VoxelShape south = Block.createCuboidShape(0, 0, 14, 16, 16, 16);
        VoxelShape west = Block.createCuboidShape(0, 0, 0, 2, 16, 16);
        VoxelShape north = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
        VoxelShape east = Block.createCuboidShape(14, 0, 0, 16, 16, 16);

        VoxelShape floorCollision = Block.createCuboidShape(0.5, 0, 0.5, 15.5, 0.0001, 15.5);
        VoxelShape southCollision = Block.createCuboidShape(0.5, 0, 15, 15.5, 16, 15.5);
        VoxelShape westCollision = Block.createCuboidShape(0.5, 0, 0.5, 1, 16, 15.5);
        VoxelShape northCollision = Block.createCuboidShape(0.5, 0, 0.5, 15.5, 16, 1);
        VoxelShape eastCollision = Block.createCuboidShape(15, 0, 0.5, 15.5, 16, 15.5);

        for (int i = 0; i < 16; i++) {
            VoxelShape outline = floor;
            VoxelShape collision = floorCollision;
            if ((i & 0b1) != 0) {
                outline = VoxelShapes.union(outline, south);
                collision = VoxelShapes.union(collision, southCollision);
            }
            if ((i & 0b10) != 0) {
                outline = VoxelShapes.union(outline, west);
                collision = VoxelShapes.union(collision, westCollision);
            }
            if ((i & 0b100) != 0) {
                outline = VoxelShapes.union(outline, north);
                collision = VoxelShapes.union(collision, northCollision);
            }
            if ((i & 0b1000) != 0) {
                outline = VoxelShapes.union(outline, east);
                collision = VoxelShapes.union(collision, eastCollision);
            }
            OUTLINE_SHAPES[i] = outline;
            COLLISION_SHAPES[i] = VoxelShapes.combineAndSimplify(collision, cutout, BooleanBiFunction.ONLY_FIRST);
        }
    }

    public CatwalkBlock(Block.Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(SOUTH, false).with(WEST, false).with(NORTH, false).with(EAST, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SOUTH, EAST, NORTH, WEST, WATERLOGGED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return COLLISION_SHAPES[i];
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        if (world.getBlockState(pos.up()).isAir()) {
            for (Direction side : Direction.Type.HORIZONTAL) {
                BlockPos neighborAbove = pos.up().offset(side);
                BlockState neighborAboveState = world.getBlockState(neighborAbove);
                Block neighborAboveBlock = neighborAboveState.getBlock();
                if (neighborAboveBlock == this || neighborAboveBlock instanceof CatwalkConnectingBlock && ((CatwalkConnectingBlock) neighborAboveBlock).shouldCatwalkConnect(neighborAboveState, world, neighborAbove, side)) {
                    return IConstruct.CATWALK_STAIRS.getDefaultState().with(CatwalkStairsBlock.FACING, side.getOpposite());
                }
            }
        }

        boolean south = this.hasBorder(world, pos, Direction.SOUTH);
        boolean west = this.hasBorder(world, pos, Direction.WEST);
        boolean north = this.hasBorder(world, pos, Direction.NORTH);
        boolean east = this.hasBorder(world, pos, Direction.EAST);

        return this.getDefaultState().with(SOUTH, south).with(WEST, west).with(NORTH, north).with(EAST, east);
    }

    public boolean hasBorder(BlockView world, BlockPos pos, Direction side) {
        BlockState neighbor = world.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof CatwalkConnectingBlock) {
            CatwalkConnectingBlock connectable = (CatwalkConnectingBlock) block;
            return !connectable.shouldCatwalkConnect(neighbor, world, pos.offset(side), side);
        }
        if (block instanceof CatwalkBlock || block == IConstruct.SCAFFOLDING || block == IConstruct.CONDUIT_IN_SCAFFOLDING || block == Blocks.CAULDRON) {
            return false;
        }
        if (neighbor.isSideSolidFullSquare(world, pos.offset(side), side.getOpposite()) && neighbor.getMaterial() != Material.AGGREGATE) {
            return false;
        }
        return true;
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
        if (direction.getAxis().isHorizontal()) {
            return state.with(getConnectionProperty(direction), this.hasBorder(world, pos, direction));
        }
        return state;
    }

    protected static BooleanProperty getConnectionProperty(Direction direction) {
        switch (direction) {
        case SOUTH:
            return SOUTH;
        case WEST:
            return WEST;
        case NORTH:
            return NORTH;
        case EAST:
        default:
            return EAST;
        }
    }

    @Override
    public boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, Vec3d hitPos) {
        Direction dir = null;
        double x = hitPos.getX() - pos.getX();
        double z = hitPos.getZ() - pos.getZ();

        if (Math.abs(x - 0.5) > Math.abs(z - 0.5)) {
            if (x > 0.5) {
                dir = Direction.EAST;
            } else {
                dir = Direction.WEST;
            }
        } else {
            if (z > 0.5) {
                dir = Direction.SOUTH;
            } else {
                dir = Direction.NORTH;
            }
        }

        world.setBlockState(pos, state.cycle(getConnectionProperty(dir)));

        return true;
    }
}
