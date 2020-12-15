package com.github.reoseah.indconstr.api.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public interface ColorScrapableBlock {
    boolean canScrapColor(BlockState state, BlockView world, BlockPos pos);

    DyeColor getScrapColor(BlockState state, BlockView world, BlockPos pos);

    int getScrapCount(BlockState state, BlockView world, BlockPos pos);

    void onScraped(BlockState state, WorldAccess world, BlockPos pos);
}
