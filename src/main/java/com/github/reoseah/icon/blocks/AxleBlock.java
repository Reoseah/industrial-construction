package com.github.reoseah.icon.blocks;

import com.github.reoseah.icon.blocks.entities.AxleBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class AxleBlock extends Block implements BlockEntityProvider {
    public static final EnumProperty<Axis> AXIS = Properties.AXIS;

    private static final VoxelShape SHAPE_Y = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    private static final VoxelShape SHAPE_X = Block.createCuboidShape(0, 6, 6, 16, 10, 10);
    private static final VoxelShape SHAPE_Z = Block.createCuboidShape(6, 6, 0, 10, 10, 16);

    public AxleBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
        case X:
            return SHAPE_X;
        case Z:
            return SHAPE_Z;
        default:
            return SHAPE_Y;
        }
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

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new AxleBlockEntity();
    }
}
