package cn.teampancake.theaurorian.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Map;

public class RegisterAurorianSkyColorEvent extends Event {

    private final Map<ResourceLocation, Integer> daySkyColors;

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