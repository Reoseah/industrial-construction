package reoseah.velvet.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.DyeColor;

public interface ConduitConnectable {
    @Nullable
    default DyeColor getColor() {
        return null;
    }

    static boolean canColorsConnect(@Nullable DyeColor a, @Nullable DyeColor b) {
        return a == null || b == null || a == b;
    }
}
