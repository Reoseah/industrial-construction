package reoseah.velvet.blocks;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;

public class FramedConduitBlock extends AbstractConduitBlock implements FrameConnectable, BlockEntityProvider, Waterloggable {
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public FramedConduitBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ATTACHED, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ATTACHED, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return super.getPlacementState(ctx)
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
    }

    public boolean isFrame(BlockView world, BlockPos pos, Direction side, BlockState neighbor) {
        Block block = neighbor.getBlock();
        return block instanceof FrameConnectable;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getBlock() instanceof FrameConnectable ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return Velvet.Blocks.FRAME.getDroppedStacks(state, builder);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Velvet.Blocks.FRAME);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Velvet.Blocks.CONDUIT.getDefaultState()
                .with(DOWN, state.get(DOWN))
                .with(UP, state.get(UP))
                .with(WEST, state.get(WEST))
                .with(EAST, state.get(EAST))
                .with(NORTH, state.get(NORTH))
                .with(SOUTH, state.get(SOUTH)), 11);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ConduitBlockEntity();
    }
}
