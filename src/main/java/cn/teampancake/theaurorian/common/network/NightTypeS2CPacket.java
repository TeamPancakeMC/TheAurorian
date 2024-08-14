package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.hud.NightBarRender;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NightTypeS2CPacket(int nightType) implements CustomPacketPayload {

    public static final Type<NightTypeS2CPacket> TYPE = new Type<>(TheAurorian.prefix("frostbite"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NightTypeS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(NightTypeS2CPacket::write, NightTypeS2CPacket::new);

    public NightTypeS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.nightType);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(NightTypeS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            int nightType = packet.nightType;
            NightBarRender.nightType = nightType;
            TASkyRenderer.setCurrentPhase(nightType);
        });
    }

}