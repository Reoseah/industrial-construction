package com.github.reoseah.indconstr.blocks;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.api.blocks.ColorableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class OpaqueConduitBlock extends ConduitBlock implements ColorableBlock {
    public OpaqueConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable DyeColor getColor() {
        return null;
    }

    @Override
    public boolean canColor(@Nullable DyeColor color) {
        return false;
    }

    @Override
    public BlockState getColoredState(BlockState state, BlockView world, BlockPos pos, @Nullable DyeColor color) {
        return state;
    }
}
