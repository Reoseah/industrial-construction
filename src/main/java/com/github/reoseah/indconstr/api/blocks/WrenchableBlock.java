package com.github.reoseah.indconstr.api.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A block on which the wrench can be used.
 * 
 * <p>
 * Why not use Zundrel's WrenchableBlock API? <br>
 * 
 * First, it doesn't return success/failure value - a conduit might not have
 * other inventories to rotate to, therefore wrench couldn't be applied. <br>
 * 
 * Second, it enforces a number of interaction that for me don't make sense,
 * such as rotating a log... in a living tree!
 */
public interface WrenchableBlock {
    /**
     * @return whether player should swing a wrench & use its durability in survival
     */
    boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, Vec3d hitPos);
}
