package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.ClientSkyColorData;
import cn.teampancake.theaurorian.common.level.data.WorldSkyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WorldDayColorS2CPacket(int currentColorId, int currentColorValue, int[] futureColorIds, int[] futureColorValues) implements CustomPacketPayload {

    public static final Type<WorldDayColorS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.day_sky_color"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WorldDayColorS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(WorldDayColorS2CPacket::write, WorldDayColorS2CPacket::new);

    public WorldDayColorS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readVarIntArray(), buf.readVarIntArray());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.currentColorId);
        buf.writeInt(this.currentColorValue);
        buf.writeVarIntArray(this.futureColorIds);
        buf.writeVarIntArray(this.futureColorValues);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(WorldDayColorS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                WorldSkyManager.SkyColor firstSkyColor = WorldSkyManager.getAvailableSkyColors().getFirst();
                WorldSkyManager.SkyColor currentColor = WorldSkyManager.getSkyColorById(packet.currentColorId).orElse(firstSkyColor);
                WorldSkyManager.SkyColor[] futureColors = new WorldSkyManager.SkyColor[packet.futureColorIds.length];
                for (int i = 0; i < packet.futureColorIds.length; i++) {
                    futureColors[i] = WorldSkyManager.getSkyColorById(packet.futureColorIds[i]).orElse(firstSkyColor);
                }

                ClientSkyColorData.updateClientData(level, currentColor, futureColors);
            }
        });
    }

}