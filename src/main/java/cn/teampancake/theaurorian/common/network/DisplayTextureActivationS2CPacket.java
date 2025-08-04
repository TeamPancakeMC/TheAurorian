package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DisplayTextureActivationS2CPacket(ResourceLocation texture, int activationTicks) implements CustomPacketPayload {

    public static final Type<DisplayTextureActivationS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.display_texture_activation"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DisplayTextureActivationS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, DisplayTextureActivationS2CPacket::texture,
            ByteBufCodecs.INT, DisplayTextureActivationS2CPacket::activationTicks,
            DisplayTextureActivationS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DisplayTextureActivationS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (context.flow().isClientbound() && player instanceof LocalPlayer localPlayer) {
                GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
                player.setData(TAAttachmentTypes.ANIMATION_TEXTURE, packet.texture);
                player.setData(TAAttachmentTypes.ACTIVATION_TICKS, packet.activationTicks);
                player.setData(TAAttachmentTypes.MAX_ACTIVATION_TICKS, packet.activationTicks);
                gameRenderer.itemActivationOffX = gameRenderer.random.nextFloat() * 2.0F - 1.0F;
                gameRenderer.itemActivationOffY = gameRenderer.random.nextFloat() * 2.0F - 1.0F;
                playTotemSound(localPlayer);
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void playTotemSound(LocalPlayer player) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            clientLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE,
                    player.getSoundSource(), 1.0F, 1.0F, false);
        }
    }

}