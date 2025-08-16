package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayerLostInForestS2CPacket(boolean lost) implements CustomPacketPayload {

    public static final Type<PlayerLostInForestS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.lost_in_forest"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerLostInForestS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(PlayerLostInForestS2CPacket::write, PlayerLostInForestS2CPacket::new);

    public PlayerLostInForestS2CPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(this.lost);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PlayerLostInForestS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (context.flow().isClientbound() && player instanceof LocalPlayer) {
                player.setData(TAAttachmentTypes.LOST_IN_FOREST, packet.lost);
            }
        });
    }

}