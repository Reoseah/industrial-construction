package com.github.reoseah.indconstr.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.api.blocks.ColorableBlock;
import com.github.reoseah.indconstr.blocks.entities.ExtractorBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class TransparentExtractorBlock extends ExtractorBlock implements ColorableBlock {
    public TransparentExtractorBlock(Block.Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState state2, Direction direction) {
        return state.get(getConnectionProperty(direction)) == ConnectionType.NORMAL && state2.getBlock() instanceof AbstractConduitBlock && direction != state.get(DIRECTION) && !(state2.getBlock() instanceof OpaqueConduitBlock) && !(state2.getBlock() instanceof ColoredOpaqueConduitBlock)
                || super.isSideInvisible(state, state2, direction);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return null;
    }

    @Override
    public boolean canColor(@Nullable DyeColor color) {
        return color != null;
    }

    @Override
    public BlockState getColoredState(BlockState state, BlockView world, BlockPos pos, @Nullable DyeColor color) {
        return ((AbstractConduitBlock) ColoredTransparentExtractorBlock.INSTANCES.get(color))
                .getStateForPos(world, pos).with(DIRECTION, state.get(DIRECTION));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return ExtractorBlockEntity.createTransparent();
    }

}
