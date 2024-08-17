package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FutureNightS2CPacket(int[] futureNight) implements CustomPacketPayload {

    public static final Type<FutureNightS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.future_night"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FutureNightS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(FutureNightS2CPacket::write, FutureNightS2CPacket::new);

    public FutureNightS2CPacket(FriendlyByteBuf buf) {
        this(buf.readVarIntArray());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVarIntArray(this.futureNight);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FutureNightS2CPacket packet, IPayloadContext context) {

    }

}