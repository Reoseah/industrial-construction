package com.github.reoseah.iconstruct.blocks;

import com.github.reoseah.iconstruct.api.blocks.ConduitConnectingBlock;
import com.github.reoseah.iconstruct.blocks.entities.ConduitBlockEntity;

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
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class SpecialConduitBlock extends Block implements ConduitConnectingBlock, Waterloggable, AttributeProvider {
    public enum ConnectionType implements StringIdentifiable {
        NONE("none"), NORMAL("normal"), SPECIAL("special");

        public final String name;

        private ConnectionType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public static final EnumProperty<ConnectionType> DOWN = EnumProperty.of("down", ConnectionType.class);
    public static final EnumProperty<ConnectionType> UP = EnumProperty.of("up", ConnectionType.class);
    public static final EnumProperty<ConnectionType> NORTH = EnumProperty.of("north", ConnectionType.class);
    public static final EnumProperty<ConnectionType> SOUTH = EnumProperty.of("south", ConnectionType.class);
    public static final EnumProperty<ConnectionType> EAST = EnumProperty.of("east", ConnectionType.class);
    public static final EnumProperty<ConnectionType> WEST = EnumProperty.of("west", ConnectionType.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static EnumProperty<ConnectionType> getConnectionProperty(Direction direction) {
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

    public SpecialConduitBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(DOWN, ConnectionType.NONE)
                .with(UP, ConnectionType.NONE)
                .with(NORTH, ConnectionType.NONE)
                .with(SOUTH, ConnectionType.NONE)
                .with(EAST, ConnectionType.NONE)
                .with(WEST, ConnectionType.NONE)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getStateForPos(ctx.getWorld(), ctx.getBlockPos());
    }

    public BlockState getStateForPos(BlockView world, BlockPos pos) {
        return this.getDefaultState()
                .with(DOWN, this.getConnectionType(world, pos, Direction.DOWN))
                .with(UP, this.getConnectionType(world, pos, Direction.UP))
                .with(WEST, this.getConnectionType(world, pos, Direction.WEST))
                .with(EAST, this.getConnectionType(world, pos, Direction.EAST))
                .with(NORTH, this.getConnectionType(world, pos, Direction.NORTH))
                .with(SOUTH, this.getConnectionType(world, pos, Direction.SOUTH));
    }

    protected ConnectionType getConnectionType(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof ConduitConnectingBlock) {
            ConduitConnectingBlock connectable = (ConduitConnectingBlock) block;
            return ConduitConnectingBlock.canColorsConnect(connectable.getColor(), this.getColor()) && connectable.canConnect(neighbor, view, pos.offset(side), side) ? ConnectionType.NORMAL : ConnectionType.NONE;
        }
        if (view instanceof World) {
            World world = (World) view;
            ItemInsertable insertable = ItemAttributes.INSERTABLE.get(world, pos.offset(side), SearchOptions.inDirection(side));
            return insertable != RejectingItemInsertable.NULL ? ConnectionType.NORMAL : ConnectionType.NONE;
        }
        return block instanceof InventoryProvider ? ConnectionType.NORMAL : ConnectionType.NONE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state.with(getConnectionProperty(direction), this.getConnectionType(world, pos, direction));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (newState.getBlock() instanceof SpecialConduitBlock) {
            return;
        }
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ConduitBlockEntity) {
            ((ConduitBlockEntity) be).onBroken();
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
        Direction direction = to.getSearchDirection();
        if (direction == null || state.get(getConnectionProperty(direction.getOpposite())) != ConnectionType.NORMAL) {
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

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

}
