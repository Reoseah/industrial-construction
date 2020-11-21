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
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;

    private static final VoxelShape[] OUTLINE_SHAPES;
    private static final VoxelShape[] COLLISION_SHAPES;
    static {
        OUTLINE_SHAPES = new VoxelShape[16];
        COLLISION_SHAPES = new VoxelShape[16];

        VoxelShape floor = Block.createCuboidShape(0, 0, 0, 16, 0.0001, 16);

        VoxelShape south = Block.createCuboidShape(0, 0, 14, 16, 16, 16);
        VoxelShape west = Block.createCuboidShape(0, 0, 0, 2, 16, 16);
        VoxelShape north = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
        VoxelShape east = Block.createCuboidShape(14, 0, 0, 16, 16, 16);

        VoxelShape southCollision = Block.createCuboidShape(0, 0, 15, 16, 24, 16);
        VoxelShape westCollision = Block.createCuboidShape(0, 0, 0, 1, 24, 16);
        VoxelShape northCollision = Block.createCuboidShape(0, 0, 0, 16, 24, 1);
        VoxelShape eastCollision = Block.createCuboidShape(15, 0, 0, 16, 24, 16);

        for (int i = 0; i < 16; i++) {
            VoxelShape outline = floor;
            VoxelShape collision = floor;
            if ((i & 0b1) != 0) {
                outline = VoxelShapes.union(outline, south);
                collision = VoxelShapes.union(collision, southCollision);
            }
            if ((i & 0b10) != 0) {
                outline = VoxelShapes.union(outline, west);
                collision = VoxelShapes.union(collision, westCollision);
            }
            if ((i & 0b100) != 0) {
                outline = VoxelShapes.union(outline, north);
                collision = VoxelShapes.union(collision, northCollision);
            }
            if ((i & 0b1000) != 0) {
                outline = VoxelShapes.union(outline, east);
                collision = VoxelShapes.union(collision, eastCollision);
            }
            OUTLINE_SHAPES[i] = outline;
            COLLISION_SHAPES[i] = collision;
        }
    }

    public CatwalkBlock(Block.Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(SOUTH, false).with(WEST, false).with(NORTH, false).with(EAST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SOUTH, EAST, NORTH, WEST);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return COLLISION_SHAPES[i];
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(SOUTH) ? 1 : 0) |
                (state.get(WEST) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(EAST) ? 8 : 0);
        return OUTLINE_SHAPES[i];
    }

    public boolean hasBorder(BlockView world, BlockPos pos, Direction side) {
        BlockState neighbor = world.getBlockState(pos.offset(side));
        Block block = neighbor.getBlock();
        if (block == this) {
            return false;
        }
        if (neighbor.isSideSolidFullSquare(world, pos.offset(side), side)) {
            return false;
        }
        BlockState ground = world.getBlockState(pos.offset(side).down());
        return ground.getBlock() != Velvet.Blocks.FRAME && ground.getBlock() != Velvet.Blocks.FRAMED_CONDUIT;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return this.getDefaultState()
                .with(SOUTH, this.hasBorder(world, pos, Direction.SOUTH))
                .with(WEST, this.hasBorder(world, pos, Direction.WEST))
                .with(NORTH, this.hasBorder(world, pos, Direction.NORTH))
                .with(EAST, this.hasBorder(world, pos, Direction.EAST));
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
        case SOUTH:
            return SOUTH;
        case WEST:
            return WEST;
        case NORTH:
            return NORTH;
        case EAST:
        default:
            return EAST;
        }
    }
}
