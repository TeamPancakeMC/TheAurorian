package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAStats;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record RuneGameAwardStatC2SPacket(int key) implements CustomPacketPayload {

    public static final Type<RuneGameAwardStatC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.rune_game_award_stat"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RuneGameAwardStatC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec(RuneGameAwardStatC2SPacket::write, RuneGameAwardStatC2SPacket::new);
    
    // 使用HashMap替代Map.of，以便更灵活地管理映射
    private static final Map<Integer, ResourceLocation> STATS = new HashMap<>();
    
    // 在静态块中初始化映射
    static {
        STATS.put(0, TAStats.RUNE_GAME_PLAY_COUNT.get());
        STATS.put(1, TAStats.TOTAL_RUNE_GAME_TIME.get());
        STATS.put(2, TAStats.RUNE_GAME_MOVE_COUNT.get());
        STATS.put(3, TAStats.RUNE_GAME_ELIMINATION_COUNT.get());
    }

    public RuneGameAwardStatC2SPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.key);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RuneGameAwardStatC2SPacket packet, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ResourceLocation statLocation = STATS.get(packet.key);
            if (statLocation != null) {
                System.out.println("统计 key=" + packet.key + ", 统计项=" + statLocation);
                player.awardStat(statLocation);
            } else {
                System.err.println("错误：找不到统计项，key=" + packet.key);
            }
        }
    }

}