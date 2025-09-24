package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NoteTeleportC2SPacket(ResourceKey<Level> target) implements CustomPacketPayload {

    public static final Type<NoteTeleportC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.note_teleport"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NoteTeleportC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec(NoteTeleportC2SPacket::write, NoteTeleportC2SPacket::new);

    public NoteTeleportC2SPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readResourceKey(Registries.DIMENSION));
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeResourceKey(this.target);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(NoteTeleportC2SPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ServerLevel toLevel = player.server.getLevel(packet.target());
            if (toLevel != null) {
                if (packet.target().equals(TADimensions.AURORIAN_DIMENSION)) {
                    TAEntityUtils.teleportToAurorian(player, toLevel);
                } else {
                    // 对于其它两个维度，直接安全传送到对应世界的出生点上方
                    player.teleportTo(toLevel, toLevel.getSharedSpawnPos().getX() + 0.5, toLevel.getSharedSpawnPos().getY() + 1, toLevel.getSharedSpawnPos().getZ() + 0.5, player.getYRot(), player.getXRot());
                }
            }
        }
    }
} 