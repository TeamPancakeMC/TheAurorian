package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * 客户端触发：播放“初次进入奥罗瑞安维度”的背景音乐。
 * 仅向传送的该玩家发送该数据包，其他玩家不会收到也不会播放。
 */
public record PlayAurorianMusicS2CPacket() implements CustomPacketPayload {

    public static final Type<PlayAurorianMusicS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.play_aurorian_music"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayAurorianMusicS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(PlayAurorianMusicS2CPacket::write, PlayAurorianMusicS2CPacket::new);

    public PlayAurorianMusicS2CPacket(RegistryFriendlyByteBuf buf) {
        this();
    }

    public void write(RegistryFriendlyByteBuf buf) {
        // 无负载
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PlayAurorianMusicS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            // 仅客户端执行：停止当前音乐并播放奥罗瑞安森林音乐
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            if (level != null && level.isClientSide) {
                MusicManager musicManager = mc.getMusicManager();
                musicManager.stopPlaying();
                SoundManager soundManager = mc.getSoundManager();
                soundManager.play(SimpleSoundInstance.forAmbientAddition(TASoundEvents.AURORIAN_FOREST.get()));
            }
        });
    }
}
