package reoseah.velvet.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;

public class ConduitBlock extends AbstractConduitBlock implements BlockEntityProvider, Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape[] SHAPES;
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

    public ConduitBlock(DyeColor color, Block.Settings settings) {
        super(color, settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
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

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return super.canReplace(state, context)
                || this == Velvet.Blocks.CONDUIT
                        && context.getStack().getItem() == Velvet.Items.FRAME
                        && context.getPlayer() != null && !context.getPlayer().isSneaking();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (this == Velvet.Blocks.CONDUIT && ctx.getWorld().getBlockState(ctx.getBlockPos()).getBlock() == Velvet.Blocks.FRAME) {
            return Velvet.Blocks.FRAMED_CONDUIT.getPlacementState(ctx);
        }
        return super.getPlacementState(ctx);
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
    public BlockEntity createBlockEntity(BlockView world) {
        return new ConduitBlockEntity();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) && state2.getBlock() instanceof ConduitConnectable
                || super.isSideInvisible(state, state2, direction);
    }
}
