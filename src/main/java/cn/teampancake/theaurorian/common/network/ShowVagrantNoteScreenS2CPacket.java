package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.VagrantNoteScreen;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record ShowVagrantNoteScreenS2CPacket(List<Integer> chapters) implements CustomPacketPayload {

    public static final Type<ShowVagrantNoteScreenS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.show_vagrant_note_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShowVagrantNoteScreenS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.list(Codec.INT)), ShowVagrantNoteScreenS2CPacket::chapters, ShowVagrantNoteScreenS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShowVagrantNoteScreenS2CPacket packet, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> openScreen(packet.chapters));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(List<Integer> chapters) {
        Minecraft.getInstance().setScreen(new VagrantNoteScreen(chapters));
    }

}