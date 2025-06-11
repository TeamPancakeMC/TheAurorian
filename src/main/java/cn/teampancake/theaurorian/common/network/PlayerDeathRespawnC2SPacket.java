package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayerDeathRespawnC2SPacket(boolean customSpawn) implements CustomPacketPayload {

    public static final Type<PlayerDeathRespawnC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.player_death_respawn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerDeathRespawnC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec(PlayerDeathRespawnC2SPacket::write, PlayerDeathRespawnC2SPacket::new);

    public PlayerDeathRespawnC2SPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(this.customSpawn);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PlayerDeathRespawnC2SPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            player.setData(TAAttachmentTypes.SHOULD_SPAWN_IN_AURORIAN.get(), packet.customSpawn);
        }
    }

}