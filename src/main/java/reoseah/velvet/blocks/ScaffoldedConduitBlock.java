package reoseah.velvet.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ScaffoldedConduitBlock extends Block implements Conduit, Scaffolding {
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    public static final BooleanProperty ATTACHED = Properties.ATTACHED;

    public ScaffoldedConduitBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DOWN, false).with(UP, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false).with(ATTACHED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, EAST, WEST, ATTACHED);
    }

    public boolean connectsTo(BlockView world, BlockPos pos, Direction side, BlockState neighbor) {
        Block block = neighbor.getBlock();
        return block instanceof Conduit || block instanceof InventoryProvider || block instanceof BarrelBlock;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return this.getDefaultState()
                .with(DOWN, this.connectsTo(world, pos.down(), Direction.DOWN, world.getBlockState(pos.down())))
                .with(UP, this.connectsTo(world, pos.up(), Direction.UP, world.getBlockState(pos.up())))
                .with(WEST, this.connectsTo(world, pos.west(), Direction.WEST, world.getBlockState(pos.west())))
                .with(EAST, this.connectsTo(world, pos.east(), Direction.EAST, world.getBlockState(pos.east())))
                .with(NORTH, this.connectsTo(world, pos.north(), Direction.NORTH, world.getBlockState(pos.north())))
                .with(SOUTH, this.connectsTo(world, pos.south(), Direction.SOUTH, world.getBlockState(pos.south())))
                .with(ATTACHED, this.isScaffolding(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(getConnectionProperty(direction), this.connectsTo(world, posFrom, direction, newState))
                .with(ATTACHED, this.isScaffolding(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
    }

    public boolean isScaffolding(BlockView world, BlockPos pos, Direction side, BlockState neighbor) {
        Block block = neighbor.getBlock();
        return block instanceof Scaffolding;
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getBlock() instanceof Scaffolding ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    protected static BooleanProperty getConnectionProperty(Direction direction) {
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
}
