package reoseah.velvet.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public abstract class BareConduitBlock extends AbstractConduitBlock {
    private static final VoxelShape[] SHAPES;
    static {
        SHAPES = new VoxelShape[64];
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
            VoxelShape shape = center;
            for (int face = 0; face < 6; face++) {
                if ((i & 1 << face) != 0) {
                    shape = VoxelShapes.union(shape, connections[face]);
                }
            }
            SHAPES[i] = shape;
        }
    }

    public BareConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(DOWN) ? 1 : 0) |
                (state.get(UP) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(SOUTH) ? 8 : 0) |
                (state.get(WEST) ? 16 : 0) |
                (state.get(EAST) ? 32 : 0);
        return SHAPES[i];
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) && state2.getBlock() instanceof AbstractConduitBlock
                || super.isSideInvisible(state, state2, direction);
    }
}
