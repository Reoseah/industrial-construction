package com.github.reoseah.indconstr.blocks;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.blocks.entities.ConduitBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ConduitInScaffoldingBlock extends AbstractConduitConnectingBlock implements ScaffoldingConnectable, BlockEntityProvider, Waterloggable {
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.5, 0, 0.5, 15.5, 16, 15.5);

    public ConduitInScaffoldingBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ATTACHED, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
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
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return super.getPlacementState(ctx)
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
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

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
                .with(ATTACHED, this.isFrame(world, pos.up(), Direction.DOWN, world.getBlockState(pos.up())));
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
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, IndConstr.Blocks.CONDUIT.getDefaultState()
                .with(DOWN, state.get(DOWN))
                .with(UP, state.get(UP))
                .with(WEST, state.get(WEST))
                .with(EAST, state.get(EAST))
                .with(NORTH, state.get(NORTH))
                .with(SOUTH, state.get(SOUTH)), 11);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return IndConstr.Blocks.SCAFFOLDING.getDroppedStacks(state, builder);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return ConduitBlockEntity.createTransparent();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(IndConstr.Blocks.SCAFFOLDING);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getBlock() instanceof ScaffoldingConnectable ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return null;
    }

}
