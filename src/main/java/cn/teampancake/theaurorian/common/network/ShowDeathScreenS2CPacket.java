package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.TADeathScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShowDeathScreenS2CPacket(Component message, boolean hardcore, boolean hasSpawnPoint) implements CustomPacketPayload {

    public static final Type<ShowDeathScreenS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.show_death_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShowDeathScreenS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.TRUSTED_STREAM_CODEC, ShowDeathScreenS2CPacket::message,
            ByteBufCodecs.BOOL, ShowDeathScreenS2CPacket::hardcore,
            ByteBufCodecs.BOOL, ShowDeathScreenS2CPacket::hasSpawnPoint,
            ShowDeathScreenS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShowDeathScreenS2CPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> openScreen(packet.message, packet.hardcore, packet.hasSpawnPoint));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(Component causeOfDeath, boolean hardcore, boolean hasSpawnPoint) {
        Minecraft.getInstance().setScreen(new TADeathScreen(causeOfDeath, hardcore, hasSpawnPoint));
    }

}