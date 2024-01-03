package cn.teampancake.theaurorian.common.network.message;

import cn.teampancake.theaurorian.common.capability.ShieldCap;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShieldSyncS2CMessage {

    public CompoundTag tag;

    public ShieldSyncS2CMessage(CompoundTag tag) {
        this.tag = tag;
    }

    public static void encode(ShieldSyncS2CMessage message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.tag);
    }

    public static ShieldSyncS2CMessage decode(FriendlyByteBuf buffer) {
        return new ShieldSyncS2CMessage(buffer.readNbt());
    }

    public static void handle(ShieldSyncS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientShieldSyncS2CMessage.handlePacket(message, contextSupplier)));
        }

        context.setPacketHandled(true);
    }

}

@OnlyIn(Dist.CLIENT)
class ClientShieldSyncS2CMessage {

    public static void handlePacket(ShieldSyncS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            ShieldCap.getCapability(player).ifPresent(cap -> cap.deserializeNBT(message.tag));
        }
    }

}