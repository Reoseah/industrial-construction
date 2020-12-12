package com.github.reoseah.indconstr.api.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * A block that can be changed with a paint roller / paint scraper.
 */
public interface ColorableBlock {
    boolean canColor(@Nullable DyeColor color);

    BlockState getColoredState(BlockState state, BlockView world, BlockPos pos, @Nullable DyeColor color);
}
