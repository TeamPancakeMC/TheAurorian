package cn.teampancake.theaurorian.compat.mui;

import icyllis.modernui.mc.TooltipRenderer;

public class ModernUICompat {

    public static boolean LOADED;

    public static void toggleModernUITooltipRenderer(boolean render) {
        if (LOADED) {
            TooltipRenderer.sTooltip = render;
        }
    }

}