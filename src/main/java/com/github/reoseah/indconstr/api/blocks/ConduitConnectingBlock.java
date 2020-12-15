package com.github.reoseah.indconstr.api.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

/**
 * A block that wants to connect to conduits, usually a conduit-like block
 * itself.
 */
public interface ConduitConnectingBlock {
    /**
     * Conduits can be color coded, in that case they only connect to conduits that
     * are either uncolored or of the same color.
     * 
     * @return conduit color or null if not color-coded
     */
    @Nullable
    DyeColor getColor();

    /**
     * @return false to prevent conduits from connecting to a given side of a block
     */
    default boolean canConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return true;
    }

    /**
     * Returns whether conduits can connect based on their colors.
     */
    static boolean canColorsConnect(@Nullable DyeColor a, @Nullable DyeColor b) {
        return a == null || b == null || a == b;
    }
}
