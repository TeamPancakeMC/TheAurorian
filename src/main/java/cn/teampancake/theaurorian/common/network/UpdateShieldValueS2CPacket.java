package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateShieldValueS2CPacket(float shield) implements CustomPacketPayload {

    public static final Type<UpdateShieldValueS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.update_shield_value"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateShieldValueS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, UpdateShieldValueS2CPacket::shield, UpdateShieldValueS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateShieldValueS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
                shieldStack.set(TADataComponents.SHIELD, packet.shield);
                player.setData(TAAttachmentTypes.CURRENT_SHIELD, shieldStack);
            }
        });
    }

}