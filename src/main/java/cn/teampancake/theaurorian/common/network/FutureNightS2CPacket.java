package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FutureNightS2CPacket(int d1, int d2, int d3) implements CustomPacketPayload {

    public static final Type<FutureNightS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.future_night"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FutureNightS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(FutureNightS2CPacket::write, FutureNightS2CPacket::new);

    public FutureNightS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.d1);
        buf.writeInt(this.d2);
        buf.writeInt(this.d3);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FutureNightS2CPacket packet, IPayloadContext context) {
        // Client-side: handled by StarSignsScreen through a static holder
        cn.teampancake.theaurorian.client.gui.screens.StarSignsScreen.setForecast(packet.d1, packet.d2, packet.d3);
    }
}