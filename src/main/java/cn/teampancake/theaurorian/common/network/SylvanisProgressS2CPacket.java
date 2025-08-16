package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SylvanisProgressS2CPacket(float sylvanis) implements CustomPacketPayload {

    public static final Type<SylvanisProgressS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.sylvanis_progress"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SylvanisProgressS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(SylvanisProgressS2CPacket::write, SylvanisProgressS2CPacket::new);

    public SylvanisProgressS2CPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeFloat(this.sylvanis);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SylvanisProgressS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (context.flow().isClientbound() && player instanceof LocalPlayer) {
                player.setData(TAAttachmentTypes.SYLVANIS_PROGRESS.get(), packet.sylvanis);
            }
        });
    }

}