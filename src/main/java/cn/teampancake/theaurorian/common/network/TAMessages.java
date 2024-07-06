package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.network.message.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class TAMessages {

    private static final String VERSION = "0.0.1";
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(AurorianMod.prefix("main"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void register() {
        INSTANCE.registerMessage(id(), ShieldSyncS2CMessage.class, ShieldSyncS2CMessage::encode, ShieldSyncS2CMessage::decode, ShieldSyncS2CMessage::handle);
        INSTANCE.registerMessage(id(), NightSyncMessage.class, NightSyncMessage::encode, NightSyncMessage::decode, NightSyncMessage::handle);
        INSTANCE.registerMessage(id(), FrostbiteSyncMessage.class, FrostbiteSyncMessage::encode, FrostbiteSyncMessage::decode, FrostbiteSyncMessage::handle);
        INSTANCE.registerMessage(id(), FutureNightMessage.class, FutureNightMessage::encode,FutureNightMessage::decode,FutureNightMessage::handle);
        INSTANCE.registerMessage(id(), CrystalRuneSetC2SMessage.class, CrystalRuneSetC2SMessage::encode, CrystalRuneSetC2SMessage::decode, CrystalRuneSetC2SMessage::handle);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }

}