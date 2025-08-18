package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.StunEffectScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShowStunScreenS2CPacket(int stunDuration) implements CustomPacketPayload {

    public static final Type<ShowStunScreenS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.show_stun_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShowStunScreenS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(ShowStunScreenS2CPacket::write, ShowStunScreenS2CPacket::new);

    public ShowStunScreenS2CPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readVarInt());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeVarInt(this.stunDuration);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShowStunScreenS2CPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> openScreen(packet.stunDuration));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(int stunDuration) {
        Minecraft.getInstance().setScreen(new StunEffectScreen(stunDuration));
    }

}