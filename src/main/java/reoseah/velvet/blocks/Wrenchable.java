package reoseah.velvet.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface Wrenchable {
    boolean useWrench(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Vec3d hitPos);
}
