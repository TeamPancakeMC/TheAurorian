package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.monster.RuneSpider;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CrystalRuneSetC2SPacket(int key) implements CustomPacketPayload {

    public static final Type<CrystalRuneSetC2SPacket> TYPE = new Type<>(AurorianMod.prefix("crystal_rune_set"));
    public static final StreamCodec<ByteBuf, CrystalRuneSetC2SPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CrystalRuneSetC2SPacket::key, CrystalRuneSetC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CrystalRuneSetC2SPacket packet, IPayloadContext context) {
        Player player = context.player();
        if (player instanceof ServerPlayer) {
            CompoundTag persistentData = player.getPersistentData();
            int count = persistentData.getInt("crystal_pillar_count");
            if (count > 0) {
                persistentData.putInt("crystal_pillar_count", count - 1);
                RuneSpider runeSpider = TAEntityTypes.RUNE_SPIDER.get().create(player.level());
                if (runeSpider != null) {
                    runeSpider.setPos(player.getX(), player.getY(), player.getZ());
                    player.level().addFreshEntity(runeSpider);
                }
            }
        }
    }

}