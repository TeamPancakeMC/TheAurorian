package cn.teampancake.theaurorian.compat;

import icyllis.modernui.mc.TooltipRenderer;

public class ModernUICompat {
    public static boolean LOADED;

    public static void ResetModernUIRender(){
        if(LOADED){
            TooltipRenderer.sTooltip = true;
        }
    }
    public static void CancelModernUIRender(){
        if(LOADED){
            TooltipRenderer.sTooltip = false;
        }
    }
}
