package cn.teampancake.theaurorian.common.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class RegisterAurorianSkyBlessEvent extends Event {

    private final ServerPlayer serverPlayer;
    private final ServerLevel serverLevel;
    private final String colorName;

    public RegisterAurorianSkyBlessEvent(ServerPlayer serverPlayer, ServerLevel serverLevel, String colorName) {
        this.serverPlayer = serverPlayer;
        this.serverLevel = serverLevel;
        this.colorName = colorName;
    }

    public ServerPlayer getServerPlayer() {
        return this.serverPlayer;
    }

    public ServerLevel getServerLevel() {
        return this.serverLevel;
    }

    public String getColorName() {
        return this.colorName;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}