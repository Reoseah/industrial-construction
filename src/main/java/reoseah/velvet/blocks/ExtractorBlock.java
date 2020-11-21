package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.impl.EmptyItemExtractable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.entities.ExtractorBlockEntity;
import reoseah.velvet.blocks.state.OptionalDirection;

public class ExtractorBlock extends AbstractConduitBlock implements BlockEntityProvider {
    public static final EnumProperty<OptionalDirection> DIRECTION = EnumProperty.of("direction", OptionalDirection.class);
    public static final BooleanProperty ENABLED = Properties.ENABLED;

    private static final VoxelShape[][] SHAPES = new VoxelShape[7][];
    static {
        VoxelShape[] extractors = new VoxelShape[] {
                VoxelShapes.empty(),
                Block.createCuboidShape(2, 0, 2, 14, 4, 14),
                Block.createCuboidShape(2, 12, 2, 14, 16, 14),
                Block.createCuboidShape(2, 2, 0, 14, 14, 4),
                Block.createCuboidShape(2, 2, 12, 14, 14, 16),
                Block.createCuboidShape(0, 2, 2, 4, 14, 14),
                Block.createCuboidShape(12, 2, 2, 16, 14, 14)
        };
        for (int j = 0; j < 7; j++) {
            SHAPES[j] = new VoxelShape[64];
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
                VoxelShape shape = VoxelShapes.union(center, extractors[j]);
                for (int face = 0; face < 6; face++) {
                    if ((i & 1 << face) != 0) {
                        shape = VoxelShapes.union(shape, connections[face]);
                    }
                }
                SHAPES[j][i] = shape;
            }
        }
    }

    public ExtractorBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, OptionalDirection.NONE).with(ENABLED, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DIRECTION, ENABLED);
    }

    @Override
    public boolean connectsTo(BlockView view, BlockPos pos, Direction side) {
        BlockState neighbor = view.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block == this) {
            return false;
        }
        return super.connectsTo(view, pos, side);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) && state2.getBlock() instanceof AbstractConduitBlock
                || super.isSideInvisible(state, state2, direction);
    }

    public boolean canExtract(BlockState state, WorldView world, BlockPos pos, Direction direction) {
        if (world instanceof World) {
            return ItemAttributes.EXTRACTABLE.get((World) world, pos.offset(direction), SearchOptions.inDirection(direction)) != EmptyItemExtractable.NULL;
        }
        return false;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        WorldView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();
        for (int i = 0; i < directions.length; ++i) {
            Direction direction = directions[i];
            if (this.canExtract(state, world, pos, direction)) {
                return state.with(DIRECTION, OptionalDirection.of(direction));
            }
        }

        return state.with(DIRECTION, OptionalDirection.NONE);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        state = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        if (state.get(DIRECTION) == OptionalDirection.NONE && this.canExtract(state, world, pos, direction)) {
            state = state.with(DIRECTION, OptionalDirection.of(direction));
        } else if (state.get(DIRECTION).direction == direction) {
            if (!this.canExtract(state, world, pos, direction)) {
                state = state.with(DIRECTION, OptionalDirection.of(null));
            }
        }
        return state;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ExtractorBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(DOWN) ? 1 : 0) |
                (state.get(UP) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(SOUTH) ? 8 : 0) |
                (state.get(WEST) ? 16 : 0) |
                (state.get(EAST) ? 32 : 0);
        return SHAPES[state.get(DIRECTION).ordinal()][i];
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == Velvet.Items.ADJUSTABLE_WRENCH) {
            int start = state.get(DIRECTION).ordinal();
            for (int i = (start + 1) % 7; i != start; i = (i + 1) % 7) {
                if (i == 0) {
                    continue;
                }
                Direction direction = OptionalDirection.values()[i].direction;
                if (this.canExtract(state, world, pos, direction)) {
                    world.setBlockState(pos, world.getBlockState(pos).with(DIRECTION, OptionalDirection.values()[i]));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
