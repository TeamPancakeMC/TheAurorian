package cn.teampancake.theaurorian.common.network.message;

import cn.teampancake.theaurorian.common.registry.TACapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FrostbiteSyncMessage {

    public int ticksFrostbite;

    public FrostbiteSyncMessage(int ticksFrostbite) {
        this.ticksFrostbite = ticksFrostbite;
    }

    public static void encode(FrostbiteSyncMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.ticksFrostbite);
    }

    public static FrostbiteSyncMessage decode(FriendlyByteBuf buffer) {
        return new FrostbiteSyncMessage(buffer.readInt());
    }

    public static void handle(FrostbiteSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () ->
                    () -> ClientFrostbiteSync.handlePacket(message, contextSupplier)));
        }

        context.setPacketHandled(true);
    }

}

@OnlyIn(Dist.CLIENT)
class ClientFrostbiteSync {

    public static void handlePacket(FrostbiteSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> miscNBT.ticksFrostbite = message.ticksFrostbite);
        }
    }

}