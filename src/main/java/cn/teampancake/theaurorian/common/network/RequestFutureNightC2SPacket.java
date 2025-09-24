package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestFutureNightC2SPacket() implements CustomPacketPayload {

    public static final Type<RequestFutureNightC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.request_future_night"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestFutureNightC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec((buf, pkt) -> {}, buf -> new RequestFutureNightC2SPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(RequestFutureNightC2SPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            if (level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                int[] arr = LevelEventSubscriber.getFuturePhases();
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player, new FutureNightS2CPacket(arr[0], arr[1], arr[2]));
            }
        }
    }
} 