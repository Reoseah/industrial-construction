package com.github.reoseah.indconstr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class GearboxBlock extends Block {
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    public static BooleanProperty getConnectionProperty(Direction direction) {
        switch (direction) {
        case NORTH:
            return NORTH;
        case SOUTH:
            return SOUTH;
        case WEST:
            return WEST;
        case EAST:
            return EAST;
        case DOWN:
            return DOWN;
        case UP:
        default:
            return UP;
        }
    }

    public GearboxBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(DOWN, false)
                .with(UP, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getStateForPos(ctx.getWorld(), ctx.getBlockPos());
    }

    public BlockState getStateForPos(BlockView world, BlockPos pos) {
        return this.getDefaultState()
                .with(DOWN, this.connectsTo(world, pos, Direction.DOWN))
                .with(UP, this.connectsTo(world, pos, Direction.UP))
                .with(WEST, this.connectsTo(world, pos, Direction.WEST))
                .with(EAST, this.connectsTo(world, pos, Direction.EAST))
                .with(NORTH, this.connectsTo(world, pos, Direction.NORTH))
                .with(SOUTH, this.connectsTo(world, pos, Direction.SOUTH));
    }

    protected boolean connectsTo(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof AxleBlock) {
            return neighbor.get(AxleBlock.AXIS) == side.getAxis();
        }
        return false;
    }

}
