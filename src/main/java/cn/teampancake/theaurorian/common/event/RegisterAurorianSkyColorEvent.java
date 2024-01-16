package cn.teampancake.theaurorian.common.event;

import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;

public class RegisterAurorianSkyColorEvent extends Event {

    private final Map<ResourceLocation, Integer> daySkyColors;

    /**
     * See {@link TASkyRenderer#getDaySkyColors()} to know
     * about how to register new SkyColors
     * @param daySkyColors use RBG format, for example 0x80e3ec.
     */

    public RegisterAurorianSkyColorEvent(Map<ResourceLocation, Integer> daySkyColors) {
        this.daySkyColors = daySkyColors;
    }

    public void register(ResourceLocation colorName, int colorRGB) {
        if (this.daySkyColors.containsKey(colorName)) {
            throw new IllegalArgumentException("Duplicate sky color key: " + colorName);
        } else {
            this.daySkyColors.put(colorName, colorRGB);
        }
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
    
}