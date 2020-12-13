package com.github.reoseah.indconstr.blocks;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.api.blocks.WrenchableBlock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ScaffoldingBlock extends Block implements ScaffoldingConnectable, Waterloggable, WrenchableBlock {
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.5, 0, 0.5, 15.5, 16, 15.5);

    public ScaffoldingBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ATTACHED, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED, WATERLOGGED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == IndConstr.Items.CONDUIT
                && context.getPlayer() != null && !context.getPlayer().isSneaking() || super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == IndConstr.Blocks.CONDUIT) {
            return IndConstr.Blocks.CONDUIT_IN_SCAFFOLDING.getPlacementState(ctx);
        }
        return this.getDefaultState()
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.UP, world.getBlockState(pos.up())));
    }

    public boolean isFrame(BlockView world, BlockPos pos, Direction side, BlockState neighbor) {
        Block block = neighbor.getBlock();
        return block instanceof ScaffoldingConnectable;
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
        if (direction == Direction.UP) {
            return state.with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
        }
        return state;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.fallDistance = 0;
            Vec3d velocity = player.getVelocity();
            if (entity.isSneaking()) {
                player.setVelocity(MathHelper.clamp(velocity.getX(), -0.01, 0.01), 0.08, MathHelper.clamp(velocity.getZ(), -0.01, 0.01));
            } else if (player.horizontalCollision) {
                player.setVelocity(MathHelper.clamp(velocity.getX(), -0.01, 0.01), 0.2, MathHelper.clamp(velocity.getZ(), -0.01, 0.01));
            } else {
                player.setVelocity(MathHelper.clamp(velocity.getX(), -0.01, 0.01), Math.max(velocity.getY(), -0.07), MathHelper.clamp(velocity.getZ(), -0.01, 0.01));
            }
        }
    }

    @Override
    public boolean useWrench(BlockState state, World world, BlockPos pos, Direction direction, PlayerEntity player, Hand hand, Vec3d hitPos) {
        world.setBlockState(pos, state.cycle(ScaffoldingBlock.ATTACHED));
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.getBlock() instanceof ScaffoldingConnectable && direction == Direction.UP) {
            return state.get(ATTACHED);
        }
        return stateFrom.getBlock() instanceof ScaffoldingConnectable ? true : super.isSideInvisible(state, stateFrom, direction);
    }
}
