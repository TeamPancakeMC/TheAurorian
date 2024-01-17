package cn.teampancake.theaurorian.common.network.message;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NightSyncMessage {

    public int nightType;

    public NightSyncMessage(int nightType){
        this.nightType = nightType;
    }

    public static void encode(NightSyncMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.nightType);
    }

    public static NightSyncMessage decode(FriendlyByteBuf buffer) {
        return new NightSyncMessage(buffer.readInt());
    }

    public static void handle(NightSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientNightSync.handlePacket(message, contextSupplier)));
        }

        context.setPacketHandled(true);
    }

}

@OnlyIn(Dist.CLIENT)
class ClientNightSync {

    public static void handlePacket(NightSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        int nightType = message.nightType;
        TASkyRenderer.setCurrentPhase(nightType);
        ServerPlayer sender = contextSupplier.get().getSender();
        if (sender != null) {
            ServerLevel serverLevel = sender.serverLevel();
            long dayTime = serverLevel.dayTime();
            if (dayTime % 200 == 0) {
                if (dayTime > 12000 && dayTime <= 24000) {
                    sender.addEffect(createEffect(TAMobEffects.PRESSURE.get()));
                } else {
                    String currentPhase = TASkyRenderer.getCurrentPhase().toString();
                    if (currentPhase.equals(AurorianMod.prefix("ta_cyan").toString())) {
                        sender.addEffect(createEffect(MobEffects.DIG_SPEED));
                    } else if (currentPhase.equals(AurorianMod.prefix("ta_purple").toString())) {
                        sender.addEffect(createEffect(MobEffects.MOVEMENT_SPEED));
                    } else if (currentPhase.equals(AurorianMod.prefix("ta_orange").toString())) {
                        sender.addEffect(createEffect(MobEffects.DAMAGE_BOOST));
                    } else if (currentPhase.equals(AurorianMod.prefix("ta_lime").toString())) {
                        sender.addEffect(createEffect(MobEffects.DAMAGE_RESISTANCE));
                    } else if (currentPhase.equals(AurorianMod.prefix("ta_pink").toString())) {
                        
                    } else {
                        TAEventFactory.onRegisterAurorianSkyBless(sender, serverLevel, currentPhase);
                    }
                }
            }
        }
    }

    private static MobEffectInstance createEffect(MobEffect effect) {
        MobEffectInstance instance = new MobEffectInstance(effect);
        instance.amplifier = 200;
        instance.ambient = false;
        instance.visible = false;
        return instance;
    }

}