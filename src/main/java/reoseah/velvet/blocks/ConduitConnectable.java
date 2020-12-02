package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public interface ConduitConnectable {
    @Nullable
    default DyeColor getColor() {
        return null;
    }

    default boolean canConnect(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return true;
    }

    static boolean canColorsConnect(@Nullable DyeColor a, @Nullable DyeColor b) {
        return a == null || b == null || a == b;
    }
}
