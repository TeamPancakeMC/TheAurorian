package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class TATradeUtils {

    public static void addTradeItem(VillagerData data, Map<VillagerType, Item> trades) {
        Item result = null;
        VillagerProfession profession = data.getProfession();
        if (profession.equals(VillagerProfession.ARMORER)) {
            result = TAItems.CERULEAN_CHESTPLATE.get();
        } else if (profession.equals(VillagerProfession.BUTCHER)) {

        } else if (profession.equals(VillagerProfession.CARTOGRAPHER)) {

        } else if (profession.equals(VillagerProfession.CLERIC)) {

        } else if (profession.equals(VillagerProfession.FARMER)) {

        } else if (profession.equals(VillagerProfession.FISHERMAN)) {

        } else if (profession.equals(VillagerProfession.FLETCHER)) {

        } else if (profession.equals(VillagerProfession.LEATHERWORKER)) {

        } else if (profession.equals(VillagerProfession.LIBRARIAN)) {

        } else if (profession.equals(VillagerProfession.MASON)) {

        } else if (profession.equals(VillagerProfession.SHEPHERD)) {

        } else if (profession.equals(VillagerProfession.TOOLSMITH)) {

        } else if (profession.equals(VillagerProfession.WEAPONSMITH)) {

        }

        if (result != null) {
            trades.put(data.getType(), result);
        }
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

        @Nullable @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemCost baseCostA = new ItemCost(this.item, this.cost);
            ItemStack result = new ItemStack(Items.EMERALD, this.numberOfEmerald);
            return new MerchantOffer(baseCostA, result, this.maxUses, this.Xp, this.priceMultiplier);
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

        @Nullable @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemCost baseCostA = new ItemCost(Items.EMERALD, this.emeraldCost);
            ItemStack result = new ItemStack(this.itemStack.getItem(), this.numberOfItems);
            return new MerchantOffer(baseCostA, result, this.maxUses, this.Xp, this.priceMultiplier);
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

        @Nullable @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemCost baseCostA = new ItemCost(Items.EMERALD, this.emeraldCost);
            ItemCost costB = new ItemCost(this.fromItem.getItem(), this.fromCount);
            ItemStack result = new ItemStack(this.toItem.getItem(), this.toCount);
            return new MerchantOffer(baseCostA, Optional.of(costB), result, this.maxUses, this.Xp, this.priceMultiplier);
        }

    }


  public static class EnchantedItemForEmeralds implements VillagerTrades.ItemListing {

        private final ItemStack itemStack;
        private final int baseEmeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EnchantedItemForEmeralds(Item item, int baseEmeraldCost, int maxUses, int villagerXp) {
            this(item, baseEmeraldCost, maxUses, villagerXp, 0.05F);
        }

        public EnchantedItemForEmeralds(Item item, int baseEmeraldCost, int maxUses, int villagerXp, float priceMultiplier) {
            this.itemStack = new ItemStack(item);
            this.baseEmeraldCost = baseEmeraldCost;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            int i = 5 + random.nextInt(15);
            RegistryAccess registryAccess = trader.level().registryAccess();
            Optional<HolderSet.Named<Enchantment>> optional = registryAccess
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getTag(EnchantmentTags.ON_TRADED_EQUIPMENT);
            ItemStack stack = new ItemStack(this.itemStack.getItem());
            ItemStack itemstack = EnchantmentHelper.enchantItem(random, stack, i, registryAccess, optional);
            int j = Math.min(this.baseEmeraldCost + i, 64);
            ItemCost itemcost = new ItemCost(Items.EMERALD, j);
            return new MerchantOffer(itemcost, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
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
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            if (trader instanceof VillagerDataHolder villagerDataHolder) {
                Item item = this.trades.get(villagerDataHolder.getVillagerData().getType());
                if (item == null) {
                    item = this.defaultTradeItem;
                }

                ItemStack itemstack = new ItemStack(item, this.numberOfItems);
                ItemCost baseCostA = new ItemCost(Items.EMERALD, emeraldCost);
                return new MerchantOffer(baseCostA, itemstack, this.maxUses, this.Xp, priceMultiplier);
            } else {
                return null;
            }
        }

    }

   public static class EnchantBookForEmeralds implements VillagerTrades.ItemListing {

       private final int villagerXp;
       private final TagKey<Enchantment> tradeableEnchantments;
       private final int minLevel;
       private final int maxLevel;

       public EnchantBookForEmeralds(int villagerXp, int minLevel, int maxLevel, TagKey<Enchantment> tradeableEnchantments) {
           this.minLevel = minLevel;
           this.maxLevel = maxLevel;
           this.villagerXp = villagerXp;
           this.tradeableEnchantments = tradeableEnchantments;
       }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            Optional<Holder<Enchantment>> optional = trader.level()
                    .registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                    .getRandomElementOf(this.tradeableEnchantments, random);
            int i;
            ItemStack itemStack;
            if (optional.isPresent()) {
                Holder<Enchantment> holder = optional.get();
                Enchantment enchantment = holder.value();
                int j = Math.max(enchantment.getMinLevel(), this.minLevel);
                int k = Math.min(enchantment.getMaxLevel(), this.maxLevel);
                int l = Mth.nextInt(random, j, k);
                itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holder, l));
                i = 2 + random.nextInt(5 + l * 10) + 3 * l;
                if (holder.is(EnchantmentTags.DOUBLE_TRADE_PRICE)) {
                    i *= 2;
                }

                if (i > 64) {
                    i = 64;
                }
            } else {
                i = 1;
                itemStack = new ItemStack(Items.BOOK);
            }

            ItemCost baseCostA = new ItemCost(Items.EMERALD, i);
            Optional<ItemCost> costB = Optional.of(new ItemCost(Items.BOOK));
            return new MerchantOffer(baseCostA, costB, itemStack, 12, this.villagerXp, 0.2F);
        }

    }

}