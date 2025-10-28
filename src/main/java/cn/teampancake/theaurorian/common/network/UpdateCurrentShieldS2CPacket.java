package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateCurrentShieldS2CPacket(ShieldStack shieldStack) implements CustomPacketPayload {

    public static final Type<UpdateCurrentShieldS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.update_current_shield"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCurrentShieldS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ShieldStack.STREAM_CODEC, UpdateCurrentShieldS2CPacket::shieldStack, UpdateCurrentShieldS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateCurrentShieldS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.setData(TAAttachmentTypes.CURRENT_SHIELD, packet.shieldStack);
            }
        });
    }

}