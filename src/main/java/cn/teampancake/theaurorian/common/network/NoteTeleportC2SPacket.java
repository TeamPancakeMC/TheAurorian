package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NoteTeleportC2SPacket(ResourceKey<Level> current, ResourceKey<Level> target) implements CustomPacketPayload {

    public static final Type<NoteTeleportC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.note_teleport"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NoteTeleportC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec(NoteTeleportC2SPacket::write, NoteTeleportC2SPacket::new);

    public NoteTeleportC2SPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readResourceKey(Registries.DIMENSION), buf.readResourceKey(Registries.DIMENSION));
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeResourceKey(this.current);
        buf.writeResourceKey(this.target);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(NoteTeleportC2SPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ServerLevel toLevel = player.server.getLevel(packet.target);
            if (toLevel != null && packet.current != packet.target) {
                BlockPos sharedSpawnPos = toLevel.getSharedSpawnPos();
                if (packet.target.equals(TADimensions.AURORIAN_DIMENSION)) {
                    TAEntityUtils.teleportToAurorian(player, toLevel);
                } else {
                    double x = sharedSpawnPos.getX() + 0.5F;
                    double y = sharedSpawnPos.getY() + 1.0F;
                    double z = sharedSpawnPos.getZ() + 0.5F;
                    player.teleportTo(toLevel, x, y, z, player.getYRot(), player.getXRot());
                }
            }
        }
    }

} 