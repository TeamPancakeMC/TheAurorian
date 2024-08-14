package cn.teampancake.theaurorian.common.blocks.state.properties;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;

@MethodsReturnNonnullByDefault
public enum AlchemyTablePart implements StringRepresentable {

    LEFT("left"),
    RIGHT("right");

    private final String name;

    AlchemyTablePart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}