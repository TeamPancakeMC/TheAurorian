package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TAVillagerProfession;
import cn.teampancake.theaurorian.common.registry.TAVillagerType;
import cn.teampancake.theaurorian.common.utils.TATradeUtils;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class VillageTradeSubscriber {
    public static final int DEFAULT_SUPPLY = 12;
    public static final int COMMON_ITEMS_SUPPLY = 16;
    public static final int CURRENCY_EXCHANGE_SUPPLY = 32;
    public static final int UNCOMMON_ITEMS_SUPPLY = 6;
    public static final int ONLY_SUPPLY_ONCE = 1;

    public static final int XP_LEVEL_1_SELL = 1;
    public static final int XP_LEVEL_1_BUY = 2;
    public static final int XP_LEVEL_2_SELL = 5;
    public static final int XP_LEVEL_2_BUY = 10;
    public static final int XP_LEVEL_3_SELL = 10;
    public static final int XP_LEVEL_3_BUY = 20;
    public static final int XP_LEVEL_4_SELL = 15;
    public static final int XP_LEVEL_4_BUY = 30;
    public static final int XP_LEVEL_5_TRADE = 30;

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        if(event.getType().equals(TAVillagerProfession.PROF_ASTROLOGER.get())) {
            trades.get(1).add(new TATradeUtils.EmeraldForItems(Items.STICK, 32, 1,16,XP_LEVEL_1_BUY));
            trades.get(1).add(new VillagerTrades.EmeraldsForVillagerTypeItem(8, 1, XP_LEVEL_1_SELL,
                    ImmutableMap.<VillagerType, Item>builder()
                            .put(VillagerType.PLAINS, Items.OAK_SAPLING)
                            .put(VillagerType.TAIGA, Items.SPRUCE_SAPLING)
                            .put(VillagerType.SNOW, Items.SPRUCE_SAPLING)
                            .put(VillagerType.DESERT, Items.JUNGLE_SAPLING)
                            .put(VillagerType.JUNGLE, Items.JUNGLE_SAPLING)
                            .put(VillagerType.SAVANNA, Items.ACACIA_SAPLING)
                            .put(VillagerType.SWAMP, Items.DARK_OAK_SAPLING)
                            .put(TAVillagerType.AURORIAN_PLAINS.get(), Items.OAK_SAPLING)
                            .build()
            ));
            trades.get(1).add(new VillagerTrades.EmeraldsForVillagerTypeItem(2, 1, XP_LEVEL_1_BUY,
                    ImmutableMap.<VillagerType, Item>builder()
                            .put(VillagerType.PLAINS, Items.OAK_LOG)
                            .put(VillagerType.TAIGA, Items.SPRUCE_LOG)
                            .put(VillagerType.SNOW, Items.SPRUCE_LOG)
                            .put(VillagerType.DESERT, Items.JUNGLE_LOG)
                            .put(VillagerType.JUNGLE, Items.JUNGLE_LOG)
                            .put(VillagerType.SAVANNA, Items.ACACIA_LOG)
                            .put(VillagerType.SWAMP, Items.DARK_OAK_LOG)
                            .put(TAVillagerType.AURORIAN_PLAINS.get(), Items.OAK_LOG)
                            .build()
            ));
//            trades.get(2).add(new TATradeUtils.ItemsAndEmeraldsToItems(Items.NETHER_WART_BLOCK, 1, 4, Items.NETHER_WART, 8, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_2_SELL));
//            trades.get(2).add(new TATradeUtils.ItemsForEmeralds(Items.IRON_AXE, 3, 1, DEFAULT_SUPPLY, XP_LEVEL_2_SELL));
//            trades.get(2).add(new TATradeUtils.EmeraldForItems(Items.BOOKSHELF, 1, 2, DEFAULT_SUPPLY, XP_LEVEL_2_BUY));
//            trades.get(2).add(new TATradeUtils.EmeraldForItems(Items.CRAFTING_TABLE, 2, 1, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_2_BUY));
//            trades.get(3).add(new TATradeUtils.ItemsAndEmeraldsToItems(Items.WARPED_WART_BLOCK, 1, 4, WARPED_WART.get(), 8, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_3_SELL));
//            trades.get(3).add(new TATradeUtils.ItemsForEmeralds(Items.BOWL, 1, 6, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_3_SELL));
//            trades.get(3).add(new TATradeUtils.EmeraldForItems(Items.CHEST, 3, 1, DEFAULT_SUPPLY, XP_LEVEL_3_BUY));
//            trades.get(4).add(new TATradeUtils.ItemsForEmeralds(Items.BEEHIVE, 3, 1, DEFAULT_SUPPLY, XP_LEVEL_4_SELL));
//            trades.get(4).add(new TATradeUtils.ItemsForEmeralds(Items.JUKEBOX, 3, 1, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_4_SELL));
            trades.get(4).add(new VillagerTrades.EmeraldsForVillagerTypeItem(4, 1, XP_LEVEL_4_BUY,
                    ImmutableMap.<VillagerType, Item>builder()
                            .put(VillagerType.PLAINS, Items.OAK_SIGN)
                            .put(VillagerType.TAIGA, Items.SPRUCE_SIGN)
                            .put(VillagerType.SNOW, Items.SPRUCE_SIGN)
                            .put(VillagerType.DESERT, Items.JUNGLE_SIGN)
                            .put(VillagerType.JUNGLE, Items.JUNGLE_SIGN)
                            .put(VillagerType.SAVANNA, Items.ACACIA_SIGN)
                            .put(VillagerType.SWAMP, Items.DARK_OAK_SIGN)
                            .put(TAVillagerType.AURORIAN_PLAINS.get(),Items.OAK_SIGN)
                            .build()
            ));
//            trades.get(5).add(new EnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, UNCOMMON_ITEMS_SUPPLY, XP_LEVEL_5_TRADE));
//            trades.get(5).add(new ItemsForEmeralds(Items.NOTE_BLOCK, 4, 4, COMMON_ITEMS_SUPPLY, XP_LEVEL_5_TRADE));
//            trades.get(5).add(new ItemsAndEmeraldsToItems(Items.SHULKER_SHELL, 1, 12, Items.SHULKER_BOX, 1, DEFAULT_SUPPLY, XP_LEVEL_5_TRADE));
        }
    }
}
