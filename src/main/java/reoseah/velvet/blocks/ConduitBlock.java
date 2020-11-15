package reoseah.velvet.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class ConduitBlock extends Block implements Conduit {
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty WEST = Properties.WEST;

    private static final VoxelShape[] SHAPES;

    static {
        VoxelShape center = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
        float min = 4F / 16F;
        float max = 12F / 16F;
        VoxelShape[] connections = new VoxelShape[] {
                VoxelShapes.cuboid(min, 0, min, max, max, max),
                VoxelShapes.cuboid(min, min, min, max, 1, max),
                VoxelShapes.cuboid(min, min, 0, max, max, max),
                VoxelShapes.cuboid(min, min, min, max, max, 1),
                VoxelShapes.cuboid(0, min, min, max, max, max),
                VoxelShapes.cuboid(min, min, min, 1, max, max)
        };

        SHAPES = new VoxelShape[64];
        for (int i = 0; i < 64; i++) {
            VoxelShape shape = center;
            for (int face = 0; face < 6; face++) {
                if ((i & 1 << face) != 0) {
                    shape = VoxelShapes.union(shape, connections[face]);
                }
            }
            SHAPES[i] = shape;
        }
    }

    public ConduitBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DOWN, false).with(UP, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = (state.get(DOWN) ? 1 : 0) |
                (state.get(UP) ? 2 : 0) |
                (state.get(NORTH) ? 4 : 0) |
                (state.get(SOUTH) ? 8 : 0) |
                (state.get(WEST) ? 16 : 0) |
                (state.get(EAST) ? 32 : 0);
        return SHAPES[i];
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
                .with(SOUTH, this.connectsTo(world, pos.south(), Direction.SOUTH, world.getBlockState(pos.south())));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(getConnectionProperty(direction), this.connectsTo(world, posFrom, direction, newState));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction))
                || super.isSideInvisible(state, state2, direction);
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
