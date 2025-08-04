package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DisplayActivationTickS2CPacket(int tick) implements CustomPacketPayload {

    public static final Type<DisplayActivationTickS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.display_activation_tick"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DisplayActivationTickS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DisplayActivationTickS2CPacket::tick, DisplayActivationTickS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DisplayActivationTickS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (context.flow().isClientbound() && player instanceof LocalPlayer) {
                player.setData(TAAttachmentTypes.ACTIVATION_TICKS, packet.tick);
            }
        });
    }

}