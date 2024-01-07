package cn.teampancake.theaurorian.common.blocks.state;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum TAVerticalSlabType implements StringRepresentable {

    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    EAST(Direction.EAST),
    WEST(Direction.WEST),
    FULL(null);

    private final Direction direction;

    TAVerticalSlabType(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getModelRotation() {
        return this == FULL ? 0 : (int) this.direction.toYRot() - 180;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static TAVerticalSlabType fromDirection(Direction direction) {
        for (TAVerticalSlabType value : values()) {
            if (value.direction == direction) {
                return value;
            }
        }

        return FULL;
    }

}