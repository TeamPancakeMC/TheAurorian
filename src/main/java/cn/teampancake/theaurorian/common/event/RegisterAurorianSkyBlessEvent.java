package cn.teampancake.theaurorian.common.event;

import net.minecraftforge.eventbus.api.Event;

public class RegisterAurorianSkyBlessEvent extends Event {

    private final String colorName;

    public RegisterAurorianSkyBlessEvent(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return this.colorName;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}