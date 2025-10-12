package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.sky_color.ClientSkyColorData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record SkyColorS2CPacket(ResourceLocation currentColorId, List<ResourceLocation> futureColorIds) implements CustomPacketPayload {

    public static final Type<SkyColorS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.day_sky_color"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkyColorS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, SkyColorS2CPacket::currentColorId,
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), SkyColorS2CPacket::futureColorIds,
            SkyColorS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SkyColorS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                List<ResourceLocation> futureColors = new ArrayList<>(packet.futureColorIds);
                ClientSkyColorData.updateClientData(level, packet.currentColorId, futureColors);
            }
        });
    }

}