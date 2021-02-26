package com.github.reoseah.iconstruct.api.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

/**
 * A block that wants to connect to conduits, usually a conduit-like block
 * itself but can technically be a machine too.
 */
public interface ConduitConnectingBlock {
    /**
     * Conduits can be color-coded to only connect to the same colored or uncolored
     * conduits.
     * 
     * @return color or null if not color-coded
     */
    @Nullable
    DyeColor getColor();

    /**
     * Returns whether conduits can connect based on their colors.
     */
    static boolean canColorsConnect(@Nullable DyeColor a, @Nullable DyeColor b) {
        return a == null || b == null || a == b;
    }

    /**
     * @return false to prevent conduits from connecting to a given side of a block
     */
    default boolean canConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return true;
    }
}
