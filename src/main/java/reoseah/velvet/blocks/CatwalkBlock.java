package reoseah.velvet.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import reoseah.velvet.Velvet;

public class CatwalkBlock extends Block {
    public static final VoxelShape SHAPE_FLOOR = Block.createCuboidShape(0, 0, 0, 16, 0.0001, 16);
    public static final VoxelShape SHAPE_WEST = Block.createCuboidShape(0, 0, 0, 2, 16, 16);
    public static final VoxelShape SHAPE_EAST = Block.createCuboidShape(14, 0, 0, 16, 16, 16);
    public static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
    public static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(0, 0, 14, 16, 16, 16);

    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    private static final VoxelShape[] SHAPES;
    static {
        SHAPES = new VoxelShape[16];

        for (int i = 0; i < 16; i++) {
            VoxelShape shape = SHAPE_FLOOR;
            if ((i & 0b1) != 0) {
                shape = VoxelShapes.union(shape, SHAPE_SOUTH);
            }
            if ((i & 0b10) != 0) {
                shape = VoxelShapes.union(shape, SHAPE_WEST);
            }
            if ((i & 0b100) != 0) {
                shape = VoxelShapes.union(shape, SHAPE_NORTH);
            }
            if ((i & 0b1000) != 0) {
                shape = VoxelShapes.union(shape, SHAPE_EAST);
            }
            SHAPES[i] = shape;
        }
    }

    public CatwalkBlock(Block.Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return SHAPES[i];
    }

    public boolean hasBorder(BlockView world, BlockPos pos, Direction side) {
        BlockState neighbor = world.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block instanceof CatwalkBlock) {
            return false;
        }
        return !neighbor.isSideSolidFullSquare(world, pos.offset(side), side);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return this.getDefaultState()
                .with(WEST, this.hasBorder(world, pos, Direction.WEST))
                .with(EAST, this.hasBorder(world, pos, Direction.EAST))
                .with(NORTH, this.hasBorder(world, pos, Direction.NORTH))
                .with(SOUTH, this.hasBorder(world, pos, Direction.SOUTH));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction.getAxis().isHorizontal()) {
            return state.with(getConnectionProperty(direction), this.hasBorder(world, pos, direction));
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == Velvet.Items.ADJUSTABLE_WRENCH) {
            if (hit.getSide().getAxis().isHorizontal()) {
                boolean inside = false;
                if (hit.getSide().getAxis() == Axis.X) {
                    double dx = hit.getPos().getX() - hit.getBlockPos().getX();
                    inside = dx > 0 && dx < 1;
                } else {
                    double dz = hit.getPos().getZ() - hit.getBlockPos().getZ();
                    inside = dz > 0 && dz < 1;
                }
                if (inside) {
                    world.setBlockState(pos, world.getBlockState(pos).cycle(getConnectionProperty(hit.getSide().getOpposite())));
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).cycle(getConnectionProperty(hit.getSide())));
                }
            } else {
                world.setBlockState(pos, world.getBlockState(pos).cycle(getConnectionProperty(player.getHorizontalFacing())));
            }
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
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
        default:
            return EAST;
        }
    }
}
