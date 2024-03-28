package cn.teampancake.theaurorian.common.network.message;

import cn.teampancake.theaurorian.common.entities.monster.RuneSpider;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CrystalRuneSetC2SMessage {
    public void encode(FriendlyByteBuf friendlyByteBuf) {

    }
    public static CrystalRuneSetC2SMessage decode(FriendlyByteBuf friendlyByteBuf) {
        return new CrystalRuneSetC2SMessage();
    }
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                CompoundTag persistentData = player.getPersistentData();
                int count = persistentData.getInt("crystalPillarCount");
                if (count > 0){
                    persistentData.putInt("crystalPillarCount", count - 1);
                    RuneSpider runeSpider = TAEntityTypes.RUNE_SPIDER.get().create(player.level());
                    if (runeSpider != null){
                        runeSpider.setPos(player.getX(), player.getY(), player.getZ());
                        player.level().addFreshEntity(runeSpider);
                    }
                }
            }
        });
    }

}
