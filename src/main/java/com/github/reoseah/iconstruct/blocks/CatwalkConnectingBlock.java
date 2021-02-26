package com.github.reoseah.iconstruct.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

/**
 * A block that wants to overwrite default catwalk connecting rules.
 * <p>
 * Usually, such block itself is a catwalk or a catwalk-like block that needs to
 * disable hand-rails.
 */
public interface CatwalkConnectingBlock {
    boolean shouldCatwalkConnect(BlockState state, BlockView world, BlockPos pos, Direction side);
}
