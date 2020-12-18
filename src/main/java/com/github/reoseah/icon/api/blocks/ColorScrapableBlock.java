package com.github.reoseah.icon.api.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

/**
 * A block that can be changed with a paint scraper.
 */
public interface ColorScrapableBlock {
    boolean canScrapColor(BlockState state, BlockView world, BlockPos pos);

    /**
     * Only called when a paint scraper has the Parsimony enchantment.
     */
    DyeColor getScrapColor(BlockState state, BlockView world, BlockPos pos);

    /**
     * Only called when a paint scraper has the Parsimony enchantment.
     * 
     * @return dye scrap count, can be zero
     */
    int getScrapCount(BlockState state, BlockView world, BlockPos pos);

    void onScraped(BlockState state, WorldAccess world, BlockPos pos);
}
