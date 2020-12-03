package reoseah.velvet.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CatwalkStairsBlock extends Block {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape[] SHAPES;

    static {
        // SHAPES = new VoxelShape[4];

        VoxelShape[] floors = {
                VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 16, 16.0001, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 8.0001, 16), Block.createCuboidShape(8, 16, 0, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 8.0001, 8), Block.createCuboidShape(0, 16, 8, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 8, 16.0001, 16))
        };

        SHAPES = floors;
    }

    public CatwalkStairsBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape[] floors = {
                VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 16, 16.0001, 8)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 8.0001, 16), Block.createCuboidShape(8, 16, 0, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 8.0001, 8), Block.createCuboidShape(0, 16, 8, 16, 16.0001, 16)),
                VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 8.0001, 16), Block.createCuboidShape(0, 16, 0, 8, 16.0001, 16))
        };
//        return SHAPES[state.get(FACING).getHorizontal()];
        return floors[state.get(FACING).getHorizontal()];
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
