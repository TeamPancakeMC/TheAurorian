package cn.teampancake.theaurorian.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class TAClientConfig {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.EnumValue<Style> SHIELD_STYLE;

    static {
        BUILDER.push("HUD");
        SHIELD_STYLE = BUILDER.comment("Determine whether HUD style of Shield is horizontal or vertical.")
                .defineEnum("shieldBarStyle", Style.HORIZONTAL);
        BUILDER.pop();
    }

    static {
        SPEC = BUILDER.build();
    }

    public enum Style {
        HORIZONTAL, VERTICAL
    }

}