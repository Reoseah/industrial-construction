package reoseah.velvet.blocks.state;

import java.util.Locale;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum OptionalDirection implements StringIdentifiable {
    NONE(null),
    DOWN(Direction.DOWN),
    UP(Direction.UP),
    WEST(Direction.WEST),
    EAST(Direction.EAST),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH);

    public final @Nullable Direction direction;

    private OptionalDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ROOT);
    }

}
