package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
public class TATradeUtils {

    public static void addTradeItem(VillagerData data,Map<VillagerType, Item> trades) {
        Item result = null;
        VillagerProfession profession = data.getProfession();
        if (profession.equals(VillagerProfession.ARMORER)) {
            result = TAItems.CERULEAN_CHESTPLATE.get();
        }
        else if(profession.equals(VillagerProfession.BUTCHER)){

        }
        else if(profession.equals(VillagerProfession.CARTOGRAPHER)){

        }
        else if(profession.equals(VillagerProfession.CLERIC)){

        }
        else if(profession.equals(VillagerProfession.FARMER)){

        }
        else if(profession.equals(VillagerProfession.FISHERMAN)){

        }
        else if(profession.equals(VillagerProfession.FLETCHER)){

        }
        else if(profession.equals(VillagerProfession.LEATHERWORKER)){

        }
        else if(profession.equals(VillagerProfession.LIBRARIAN)){

        }
        else if(profession.equals(VillagerProfession.MASON)){

        }
        else if(profession.equals(VillagerProfession.SHEPHERD)){

        }
        else if(profession.equals(VillagerProfession.TOOLSMITH)){

        }
        else if(profession.equals(VillagerProfession.WEAPONSMITH)){

        }

        if(result!=null)
            trades.put(data.getType(),result);

    }

    public static class EmeraldForItems implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int numberOfEmerald;
        private final int maxUses;
        private final int Xp;
        private final float priceMultiplier;

        public EmeraldForItems(ItemLike item, int cost, int numberOfEmerald, int maxUses, int Xp) {
            this.item = item.asItem();
            this.cost = cost;
            this.numberOfEmerald = numberOfEmerald;
            this.maxUses = maxUses;
            this.Xp = Xp;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD, numberOfEmerald), this.maxUses, this.Xp, this.priceMultiplier);
        }
    }

    static class ItemsForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int Xp;
        private final float priceMultiplier;

        public ItemsForEmeralds(ItemLike item, int emeraldCost, int numberOfItems, int maxUses, int Xp) {
            this(new ItemStack(item), emeraldCost, numberOfItems, maxUses, Xp);
        }

        public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int Xp) {
            this.itemStack = itemStack;
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.Xp = Xp;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.Xp, this.priceMultiplier);
        }
    }

    static class ItemsAndEmeraldsToItems implements VillagerTrades.ItemListing {
        private final ItemStack fromItem;
        private final int fromCount;
        private final int emeraldCost;
        private final ItemStack toItem;
        private final int toCount;
        private final int maxUses;
        private final int Xp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsToItems(ItemLike forItem, int fromCount, int emeraldCost, Item toItem, int toCount, int maxUses, int Xp) {
            this.fromItem = new ItemStack(forItem);
            this.fromCount = fromCount;
            this.emeraldCost = emeraldCost;
            this.toItem = new ItemStack(toItem);
            this.toCount = toCount;
            this.maxUses = maxUses;
            this.Xp = Xp;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.Xp, this.priceMultiplier);
        }
    }


  public static class EnchantedItemForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int baseEmeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EnchantedItemForEmeralds(Item pItem, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp) {
            this(pItem, pBaseEmeraldCost, pMaxUses, pVillagerXp, 0.05F);
        }

        public EnchantedItemForEmeralds(Item pItem, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
            this.itemStack = new ItemStack(pItem);
            this.baseEmeraldCost = pBaseEmeraldCost;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = pPriceMultiplier;
        }

        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            int $$2 = 5 + pRandom.nextInt(15);
            ItemStack $$3 = EnchantmentHelper.enchantItem(pRandom, new ItemStack(this.itemStack.getItem()), $$2, false);
            int $$4 = Math.min(this.baseEmeraldCost + $$2, 64);
            ItemStack $$5 = new ItemStack(Items.EMERALD, $$4);
            return new MerchantOffer($$5, $$3, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class VillagerTypeItemForEmeralds implements VillagerTrades.ItemListing {
        private final Map<VillagerType, Item> trades;
        private final Item defaultTradeItem;
        private final int numberOfItems;
        private final int emeraldCost;
        private final int maxUses;
        private final int Xp;
        private final float priceMultiplier;

        public VillagerTypeItemForEmeralds(int numberOfItems, int emeraldCost, int maxUses, int Xp, Map<VillagerType, Item> trades, Item defaultTradeItem) {

            this.trades = trades;
            this.defaultTradeItem = defaultTradeItem;
            this.numberOfItems = numberOfItems;
            this.emeraldCost = emeraldCost;
            this.maxUses = maxUses;
            this.Xp = Xp;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            if (trader instanceof VillagerDataHolder villagerDataHolder) {
                Item item = this.trades.get(villagerDataHolder.getVillagerData().getType());
                if(item == null) {
                    item = this.defaultTradeItem;
                }
                ItemStack itemstack = new ItemStack(item, this.numberOfItems);
                return new MerchantOffer(new ItemStack(Items.EMERALD, emeraldCost), itemstack, this.maxUses, this.Xp, priceMultiplier);
            } else {
                return null;
            }
        }
    }

   public static class EnchantBookForEmeralds implements VillagerTrades.ItemListing {
        private final int villagerXp;

        public EnchantBookForEmeralds(int pVillagerXp) {
            this.villagerXp = pVillagerXp;
        }

        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            List<Enchantment> $$2 = BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isTradeable).toList();
            Enchantment $$3 = $$2.get(pRandom.nextInt($$2.size()));
            int $$4 = Mth.nextInt(pRandom, $$3.getMinLevel(), $$3.getMaxLevel());
            ItemStack $$5 = EnchantedBookItem.createForEnchantment(new EnchantmentInstance($$3, $$4));
            int $$6 = 2 + pRandom.nextInt(5 + $$4 * 10) + 3 * $$4;
            if ($$3.isTreasureOnly()) {
                $$6 *= 2;
            }

            if ($$6 > 64) {
                $$6 = 64;
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD, $$6), new ItemStack(Items.BOOK), $$5, 12, this.villagerXp, 0.2F);
        }
    }
}
