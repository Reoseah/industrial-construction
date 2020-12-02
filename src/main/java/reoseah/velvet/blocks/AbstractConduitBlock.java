package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemInsertable;
import alexiil.mc.lib.attributes.item.impl.RejectingItemInsertable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import reoseah.velvet.blocks.entities.ConduitBlockEntity;

public abstract class AbstractConduitBlock extends Block implements ConduitConnectable, AttributeProvider {
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    protected final @Nullable DyeColor color;

    public AbstractConduitBlock(DyeColor color, Block.Settings settings) {
        super(settings);
        this.color = color;
        this.setDefaultState(this.getDefaultState().with(DOWN, false).with(UP, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.makeConnections(ctx.getWorld(), ctx.getBlockPos());
    }

    public BlockState makeConnections(BlockView world, BlockPos pos) {
        return this.getDefaultState()
                .with(DOWN, this.canConnect(world, pos, Direction.DOWN))
                .with(UP, this.canConnect(world, pos, Direction.UP))
                .with(WEST, this.canConnect(world, pos, Direction.WEST))
                .with(EAST, this.canConnect(world, pos, Direction.EAST))
                .with(NORTH, this.canConnect(world, pos, Direction.NORTH))
                .with(SOUTH, this.canConnect(world, pos, Direction.SOUTH));
    }

    public boolean canConnect(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof ConduitConnectable) {
            ConduitConnectable connectable = (ConduitConnectable) block;
            return ConduitConnectable.canColorsConnect(connectable.getColor(), this.getColor());
        }
        if (view instanceof World) {
            World world = (World) view;
            ItemInsertable insertable = ItemAttributes.INSERTABLE.get(world, pos.offset(side), SearchOptions.inDirection(side));
            return insertable != RejectingItemInsertable.NULL;
        }
        return block instanceof InventoryProvider;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(getConnectionProperty(direction), this.canConnect(world, pos, direction));
    }

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

    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
        Direction direction = to.getSearchDirection();
        if (direction == null || !state.get(getConnectionProperty(direction.getOpposite()))) {
            return;
        }
        ConduitBlockEntity be = (ConduitBlockEntity) world.getBlockEntity(pos);
        if (be == null) {
            return;
        }
        to.offer(new ItemInsertable() {
            @Override
            public ItemStack attemptInsertion(ItemStack stack, Simulation simulation) {
                if (simulation == Simulation.SIMULATE || stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                be.doInsert(stack, direction.getOpposite());
                return ItemStack.EMPTY;
            }
        });
    }
}
