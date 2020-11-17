package reoseah.velvet.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import reoseah.velvet.Velvet;

public class FrameBlock extends Block implements Frame {
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;

    public FrameBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ATTACHED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED);
    }

    public boolean isFrame(BlockView world, BlockPos pos, Direction side, BlockState neighbor) {
        Block block = neighbor.getBlock();
        return block instanceof Frame;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Velvet.Blocks.CONDUIT) {
            return Velvet.Blocks.FRAMED_CONDUIT.getPlacementState(ctx);
        }
        return this.getDefaultState()
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.UP, world.getBlockState(pos.up())));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getBlock() instanceof Frame ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == Velvet.Items.CONDUIT || super.canReplace(state, context);
    }
}
