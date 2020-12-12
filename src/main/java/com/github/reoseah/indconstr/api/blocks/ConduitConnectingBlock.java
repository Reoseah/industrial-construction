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
    @Nullable
    DyeColor getColor();

    default boolean canConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return true;
    }

    /**
     * A utility method for determining whether conduits can connect based on their
     * colors.
     */
    static boolean canColorsConnect(@Nullable DyeColor a, @Nullable DyeColor b) {
        return a == null || b == null || a == b;
    }
}
