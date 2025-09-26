package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.StarSignsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShowStarSignScreenS2CPacket() implements CustomPacketPayload {

    public static final Type<ShowStarSignScreenS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.show_star_sign_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShowStarSignScreenS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec((buf, pkt) -> {}, buf -> new ShowStarSignScreenS2CPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShowStarSignScreenS2CPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(ShowStarSignScreenS2CPacket::openScreen);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen() {
        Minecraft.getInstance().setScreen(new StarSignsScreen());
    }

} 