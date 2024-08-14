package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FrostbiteS2CPacket(int ticksFrostbite) implements CustomPacketPayload {

    public static final Type<FrostbiteS2CPacket> TYPE = new Type<>(TheAurorian.prefix("frostbite"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FrostbiteS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(FrostbiteS2CPacket::write, FrostbiteS2CPacket::new);

    public FrostbiteS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.ticksFrostbite);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FrostbiteS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (context.flow().isClientbound() && player != null) {
                player.setData(TAAttachmentTypes.TICKS_FROSTBITE, packet.ticksFrostbite);
            }
        });
    }

}