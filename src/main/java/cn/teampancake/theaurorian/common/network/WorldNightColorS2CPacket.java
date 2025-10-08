package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WorldNightColorS2CPacket(int color) implements CustomPacketPayload {

    public static final Type<WorldNightColorS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.night_sky_color"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WorldNightColorS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(WorldNightColorS2CPacket::write, WorldNightColorS2CPacket::new);

    public WorldNightColorS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.color);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(WorldNightColorS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                level.setData(TAAttachmentTypes.NIGHT_SKY_COLOR, packet.color);
            }
        });
    }

}