package reoseah.velvet.blocks.state;

import java.util.Locale;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum OptionalDirection implements StringIdentifiable {
    NONE(null),
    DOWN(Direction.DOWN),
    UP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST);

    public final @Nullable Direction direction;

    private OptionalDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static OptionalDirection of(@Nullable Direction direction) {
        if (direction == null) {
            return OptionalDirection.NONE;
        }
        switch (direction) {
        case DOWN:
            return OptionalDirection.DOWN;
        case UP:
            return OptionalDirection.UP;
        case WEST:
            return OptionalDirection.WEST;
        case EAST:
            return OptionalDirection.EAST;
        case NORTH:
            return OptionalDirection.NORTH;
        case SOUTH:
            return OptionalDirection.SOUTH;
        default:
            return OptionalDirection.NONE;
        }
    }

}
