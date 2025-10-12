package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.StarSignsScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FutureNightS2CPacket(ResourceLocation ...futureColors) implements CustomPacketPayload {

    public static final Type<FutureNightS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.future_night"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FutureNightS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(FutureNightS2CPacket::write, FutureNightS2CPacket::new);

    public FutureNightS2CPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation(), buf.readResourceLocation(), buf.readResourceLocation());
    }

    public void write(FriendlyByteBuf buf) {
        for (ResourceLocation futureColor : this.futureColors) {
            buf.writeResourceLocation(futureColor);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FutureNightS2CPacket packet, IPayloadContext context) {
        StarSignsScreen.setForecast(packet.futureColors[0], packet.futureColors[1], packet.futureColors[2]);
    }

}