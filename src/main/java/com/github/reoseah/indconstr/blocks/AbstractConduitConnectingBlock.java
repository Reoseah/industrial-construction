package com.github.reoseah.indconstr.blocks;

import com.github.reoseah.indconstr.api.blocks.ConduitConnectingBlock;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity.TravellingItem;

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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class AbstractConduitConnectingBlock extends Block implements ConduitConnectingBlock, AttributeProvider {
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    public AbstractConduitConnectingBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DOWN, false).with(UP, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
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
        if (block instanceof ConduitConnectingBlock) {
            ConduitConnectingBlock connectable = (ConduitConnectingBlock) block;
            return ConduitConnectingBlock.canColorsConnect(connectable.getColor(), this.getColor()) && connectable.canConnect(neighbor, view, pos.offset(side), side);
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
        return state.with(getConnectionProperty(direction), this.connectsTo(world, pos, direction));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (newState.getBlock() instanceof AbstractConduitConnectingBlock) {
            return;
        }
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ConduitBlockEntity) {
            ConduitBlockEntity conduit = (ConduitBlockEntity) be;
            for (TravellingItem item : conduit.items) {
                Vec3d offset = item.interpolatePosition(world.getTime(), 0, false);
                ItemEntity entity = new ItemEntity(world, pos.getX() + offset.getX(), pos.getY() + offset.getY(), pos.getZ() + offset.getZ(), item.stack);
                world.spawnEntity(entity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
