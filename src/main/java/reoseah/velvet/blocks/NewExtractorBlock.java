package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemInsertable;
import alexiil.mc.lib.attributes.item.impl.EmptyItemExtractable;
import alexiil.mc.lib.attributes.item.impl.RejectingItemInsertable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import reoseah.velvet.blocks.state.OptionalDirection;

public class NewExtractorBlock extends Block implements Waterloggable, Wrenchable {
    public static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class);
    public static final EnumProperty<OptionalDirection> OUTPUT = EnumProperty.of("output", OptionalDirection.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape[][] SHAPES;
    static {
        SHAPES = new VoxelShape[6][];
        VoxelShape[] extractors = new VoxelShape[] {
                Block.createCuboidShape(2, 0, 2, 14, 4, 14),
                Block.createCuboidShape(2, 12, 2, 14, 16, 14),
                Block.createCuboidShape(2, 2, 0, 14, 14, 4),
                Block.createCuboidShape(2, 2, 12, 14, 14, 16),
                Block.createCuboidShape(0, 2, 2, 4, 14, 14),
                Block.createCuboidShape(12, 2, 2, 16, 14, 14)
        };
        float min = 4;
        float max = 12;
        VoxelShape center = Block.createCuboidShape(min, min, min, max, max, max);
        VoxelShape[] outputs = new VoxelShape[] {
                VoxelShapes.empty(),
                Block.createCuboidShape(min, 0, min, max, max, max),
                Block.createCuboidShape(min, min, min, max, 16, max),
                Block.createCuboidShape(min, min, 0, max, max, max),
                Block.createCuboidShape(min, min, min, max, max, 16),
                Block.createCuboidShape(0, min, min, max, max, max),
                Block.createCuboidShape(min, min, min, 16, max, max)
        };
        for (int i = 0; i < Direction.values().length; i++) {
            SHAPES[i] = new VoxelShape[7];
            for (int j = 0; j < OptionalDirection.values().length; j++) {
                VoxelShape shape = VoxelShapes.union(center, extractors[i], outputs[j]);
                SHAPES[i][j] = shape;
            }
        }
    }

    public NewExtractorBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, Direction.DOWN).with(OUTPUT, OptionalDirection.NONE).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, OUTPUT, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(DIRECTION).ordinal()][state.get(OUTPUT).ordinal()];
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);

        WorldView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();

        Direction extractable = ctx.getPlayer() != null && ctx.getPlayer().isSneaking() ? ctx.getSide().getOpposite()
                : this.findExtractable(world, pos, directions);
        state = state.with(DIRECTION, extractable);

        state = state.with(OUTPUT, this.findOutput(world, pos, directions, extractable));

        return state;
    }

    protected Direction findExtractable(WorldView world, BlockPos pos, Direction[] directions) {
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            if (this.canExtract(world, pos, direction)) {
                return direction;
            }
        }

        return directions[0];
    }

    protected boolean canExtract(WorldView world, BlockPos pos, Direction direction) {
        if (world instanceof World) {
            return ItemAttributes.EXTRACTABLE.get((World) world, pos.offset(direction), SearchOptions.inDirection(direction)) != EmptyItemExtractable.NULL;
        }
        return false;
    }

    protected OptionalDirection findOutput(WorldView world, BlockPos pos, Direction[] directions, Direction ignored) {
        for (int i = directions.length - 1; i >= 0; i--) {
            Direction direction = directions[i];
            if (direction != ignored && this.canOutput(world, pos, direction)) {
                return OptionalDirection.of(direction);
            }
        }

        return OptionalDirection.NONE;
    }

    protected boolean canOutput(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof ConduitConnectabilityBlock) {
            return true;
        }
        if (view instanceof World) {
            World world = (World) view;
            ItemInsertable insertable = ItemAttributes.INSERTABLE.get(world, pos.offset(side), SearchOptions.inDirection(side));
            return insertable != RejectingItemInsertable.NULL;
        }
        return block instanceof InventoryProvider;
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
        if (state.get(OUTPUT).direction == direction && !this.canOutput(world, pos, direction)) {
            state = state.with(OUTPUT, OptionalDirection.NONE);
        } else if (state.get(OUTPUT) == OptionalDirection.NONE && this.canOutput(world, pos, direction)) {
            state = state.with(OUTPUT, OptionalDirection.of(direction));
        }
        return state;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(OUTPUT) == OptionalDirection.of(direction) && state2.getBlock() instanceof ConduitConnectabilityBlock
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Vec3d hitPos) {
        if (player != null && player.isSneaking()) {
            int start = state.get(OUTPUT).ordinal();
            for (int i = (start + 1) % 7; i != start; i = (i + 1) % 7) {
                if (i == 0) {
                    continue;
                }
                OptionalDirection output = OptionalDirection.values()[i];
                if (output.direction == state.get(DIRECTION)) {
                    continue;
                }
                if (this.canOutput(world, pos, output.direction)) {
                    world.setBlockState(pos, state.with(OUTPUT, output));
                    return true;
                }
            }
        } else {
            int start = state.get(DIRECTION).ordinal();
            for (int i = (start + 1) % 6; i != start; i = (i + 1) % 6) {
                Direction direction = Direction.byId(i);
                if (this.canExtract(world, pos, direction)) {
                    BlockState newState = state.with(DIRECTION, direction);
                    @Nullable
                    Direction output = state.get(OUTPUT).direction;
                    if (output == null || output == direction || !this.canOutput(world, pos, output)) {
                        newState = newState.with(OUTPUT, this.findOutput(world, pos, Direction.values(), direction));
                    }
                    world.setBlockState(pos, newState);
                    return true;
                }
            }
        }
        return false;
    }
}
