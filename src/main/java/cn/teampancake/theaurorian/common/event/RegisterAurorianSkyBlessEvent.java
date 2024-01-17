package cn.teampancake.theaurorian.common.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class RegisterAurorianSkyBlessEvent extends Event {

    private final ServerPlayer serverPlayer;
    private final ServerLevel serverLevel;
    private final int phaseCode;

    public RegisterAurorianSkyBlessEvent(ServerPlayer serverPlayer, ServerLevel serverLevel, int phaseCode) {
        this.serverPlayer = serverPlayer;
        this.serverLevel = serverLevel;
        this.phaseCode = phaseCode;
    }

    public ServerPlayer getServerPlayer() {
        return this.serverPlayer;
    }

    public ServerLevel getServerLevel() {
        return this.serverLevel;
    }

    public int getPhaseCode() {
        return this.phaseCode;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}