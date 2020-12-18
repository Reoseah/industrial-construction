package com.github.reoseah.icon.api.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

/**
 * A block that can be changed with a paint roller.
 */
public interface PaintableBlock {
    boolean canPaintBlock(DyeColor color, BlockState state, BlockView world, BlockPos pos);

    /**
     * @return 1 unit = 1/8 of a dye item
     */
    int getPaintConsumption(DyeColor color, BlockState state, BlockView world, BlockPos pos);

    void onPainted(DyeColor color, BlockState state, WorldAccess world, BlockPos pos);
}
