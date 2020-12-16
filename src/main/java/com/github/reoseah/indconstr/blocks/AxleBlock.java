package com.github.reoseah.indconstr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;

public class AxleBlock extends Block {
    public static final EnumProperty<Axis> AXIS = Properties.AXIS;

    public AxleBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
        case COUNTERCLOCKWISE_90:
        case CLOCKWISE_90:
            switch (state.get(AXIS)) {
            case X:
                return state.with(AXIS, Direction.Axis.Z);
            case Z:
                return state.with(AXIS, Direction.Axis.X);
            default:
                return state;
            }
        default:
            return state;
        }
    }

//    @Override
//    public BlockRenderType getRenderType(BlockState state) {
//        return BlockRenderType.INVISIBLE;
//    }
}
