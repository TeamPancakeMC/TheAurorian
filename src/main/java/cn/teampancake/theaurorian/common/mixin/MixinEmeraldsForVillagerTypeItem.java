package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.utils.TATradeUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(VillagerTrades.EmeraldsForVillagerTypeItem.class)
public class MixinEmeraldsForVillagerTypeItem {

    @Shadow @Final private Map<VillagerType, Item> trades;
    @Shadow @Final private int cost;
    @Shadow @Final private int maxUses;
    @Shadow @Final private int villagerXp;

    @Redirect(method = "<init>",at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"))
    private void cancel(Optional instance, Consumer action){
        //Do nothing here to cancel
    }
    /**
     * @author mlus
     * @reason using plains villager if fall back
     */
    @Overwrite
    @Nullable
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        if (pTrader instanceof VillagerDataHolder villager) {
            TATradeUtils.addTradeItem(villager.getVillagerData(),trades);
            ItemStack $$2 = new ItemStack(this.trades.getOrDefault(villager.getVillagerData().getType(),this.trades.get(VillagerType.PLAINS)), this.cost);
            return new MerchantOffer($$2, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.05F);
        } else {
            return null;
        }
    }
}
