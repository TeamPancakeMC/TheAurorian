package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DisplayItemActivationS2CPacket(ItemStack stack) implements CustomPacketPayload {

    public static final Type<DisplayItemActivationS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.display_item_activation"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DisplayItemActivationS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, DisplayItemActivationS2CPacket::stack, DisplayItemActivationS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DisplayItemActivationS2CPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> displayItemActivation(packet.stack));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void displayItemActivation(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        GameRenderer gameRenderer = minecraft.gameRenderer;
        ClientLevel clientLevel = minecraft.level;
        LocalPlayer player = minecraft.player;
        gameRenderer.displayItemActivation(stack);
        if (clientLevel != null && player != null) {
            clientLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE,
                    player.getSoundSource(), 1.0F, 1.0F, false);
        }
    }

}