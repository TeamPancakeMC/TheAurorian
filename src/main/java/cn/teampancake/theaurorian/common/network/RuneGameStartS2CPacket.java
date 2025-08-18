package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.RuneGameScreen;
import cn.teampancake.theaurorian.common.utils.AlgorithmUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RuneGameStartS2CPacket(String level) implements CustomPacketPayload {

    public static final Type<RuneGameStartS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.rune_game_start"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RuneGameStartS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(RuneGameStartS2CPacket::write, RuneGameStartS2CPacket::new);

    public RuneGameStartS2CPacket(FriendlyByteBuf buf) {
        this(buf.readUtf());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.level);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RuneGameStartS2CPacket packet, IPayloadContext context) {
        if (!packet.level.isEmpty() && context.flow().isClientbound()) {
            int[][][] randomLevel = AlgorithmUtils.convertStringTo3D(packet.level);
            context.enqueueWork(() -> openScreen(randomLevel));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(int[][][] randomLevel) {
        Minecraft.getInstance().setScreen(new RuneGameScreen(randomLevel));
    }

}