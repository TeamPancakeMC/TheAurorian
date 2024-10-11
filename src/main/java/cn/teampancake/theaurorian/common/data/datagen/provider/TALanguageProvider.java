package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.*;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class TALanguageProvider extends LanguageProvider {

    private final Map<String, String> enData = new TreeMap<>();
    private final Map<String, String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;

    public TALanguageProvider(PackOutput output, String locale) {
        super(output, TheAurorian.MOD_ID, locale);
        this.output = output;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        this.add("death.attack.corruption_1", "When %1$s gaze long into an abyss, the abyss also gazes into %1$s", "当%1$s在凝视深渊的时候，深渊也在凝视着%1$s");
        this.add("death.attack.corruption_2", "It seems that Saigyouji Yuyuko doesn't like %1$s", "西行寺幽幽子似乎并不喜欢%1$s");
        this.add("death.attack.corruption_3", "%1$s died under the cherry blossom tree", "%1$s死在了樱花树下");
        this.add("itemGroup." + TheAurorian.MOD_ID + ".normal", "The Aurorian", "极光幽境");
        this.add("itemGroup." + TheAurorian.MOD_ID + ".building", "The Aurorian (Building Materials)", "极光幽境（建材）");
        this.add(TheAurorian.MOD_ID + ".container.aurorian_furnace", "Aurorian Furnace", "极光熔炉");
        this.add(TheAurorian.MOD_ID + ".container.moonlight_forge", "Moonlight Forge", "月光融锻台");
        this.add(TheAurorian.MOD_ID + ".container.alchemy_table", "Alchemy Table", "炼药桌");
        this.add(TheAurorian.MOD_ID + ".container.scrapper", "Scrapper", "粉碎器");
        this.add("gamerule.enableAurorianBless", "Enable Aurorian Bless", "启用极光赐福");
        this.add("gamerule.enableAurorianBless.description",
                "Controls whether players can obtain beneficial effects from the Aurorian Night.",
                "控制玩家是否能在极光夜获得增益效果。");
        this.add("gamerule.enableNightmareMode", "Enable Nightmare Mode", "开启噩梦模式");
        this.add("gamerule.enableNightmareMode.description",
                "Enable to make Aurorian mobs(not bosses) fast and strong, change multiplier to adjust the strength of these effects.",
                "开启后，极光维度中的所有非Boss怪物的部分基础属性将会得到增强，可通过调整相关的乘数来控制。");
        this.add("gamerule.nightmareModeMultiplier", "Nightmare Mode Multiplier", "噩梦模式乘数");
        this.add("gamerule.nightmareModeMultiplier.description",
                "Controls the multiplier of monster attributes when Nightmare Mode is enable.",
                "用于控制噩梦模式开启后，怪物属性翻的倍数。");

        //MOD BIOMES
        this.addBiome(TABiomes.AURORIAN_FOREST, "Aurorian Forest", "谧树森林");
        this.addBiome(TABiomes.AURORIAN_PLAINS, "Aurorian Plains", "极光平原");
        this.addBiome(TABiomes.AURORIAN_BEACH, "Aurorian Beach", "极光沙滩");
        this.addBiome(TABiomes.AURORIAN_RIVER, "Aurorian River", "极光河流");
        this.addBiome(TABiomes.AURORIAN_LAKE, "Aurorian Lake", "极光湖泊");
        this.addBiome(TABiomes.AURORIAN_FOREST_HILL, "Aurorian Forest Hill", "谧树森林山丘");
        this.addBiome(TABiomes.EQUINOX_FLOWER_PLAINS, "Equinox Flower Plains", "彼岸花平原");
        this.addBiome(TABiomes.LAVENDER_PLAINS, "Lavender Plains", "薰衣草平原");
        this.addBiome(TABiomes.WEEPING_WILLOW_FOREST, "Weeping Willow Forest", "垂柳森林");
        this.addBiome(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD, "Filthy Ice Crystal Snowfield", "黯晶雪原");
        this.addBiome(TABiomes.BRIGHT_MOON_DESERT, "Bright Moon Desert", "皓月沙漠");

        //MOD BLOCK
        this.addBlock(TABlocks.AURORIAN_DIRT, "Aurorian Dirt", "极光泥土");
        this.addBlock(TABlocks.AURORIAN_STONE, "Aurorian Stone", "极光石");
        this.addBlock(TABlocks.AURORIAN_EROSIVE, "Aurorian Erosive Stone", "极光侵蚀岩");
        this.addBlock(TABlocks.AURORIAN_STONE_BRICKS, "Aurorian Stone Bricks", "极光石砖");
        this.addBlock(TABlocks.AURORIAN_COBBLESTONE, "Aurorian Cobblestone", "极光圆石");
        this.addBlock(TABlocks.AURORIAN_GRANITE, "Aurorian Granite", "极光花岗岩");
        this.addBlock(TABlocks.AURORIAN_DIORITE, "Aurorian Diorite", "极光闪长岩");
        this.addBlock(TABlocks.AURORIAN_ANDESITE, "Aurorian Andesite", "极光安山岩");
        this.addBlock(TABlocks.AURORIAN_BARRIER_STONE, "Aurorian Barrier Stone", "极光结界石");
        this.addBlock(TABlocks.AURORIAN_COAL_ORE, "Aurorian Coal Ore", "极光煤矿石");
        this.addBlock(TABlocks.AURORIAN_GRASS_BLOCK, "Aurorian Grass Block", "极光草方块");
        this.addBlock(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK, "Light Aurorian Grass Block", "发光极光草方块");
        this.addBlock(TABlocks.SNOW_AURORIAN_GRASS_BLOCK, "Snow Aurorian Grass Block", "积雪极光草方块");
        this.addBlock(TABlocks.RED_AURORIAN_GRASS_BLOCK, "Red Aurorian Grass Block", "红色极光草方块");
        this.addBlock(TABlocks.AURORIAN_FARM_TILE, "Aurorian Farm Tile", "极光农砖");
        this.addBlock(TABlocks.FILTHY_ICE, "Filthy Ice", "污秽的冰");
        this.addBlock(TABlocks.MOON_GLASS, "Moon Glass", "皎月玻璃");
        this.addBlock(TABlocks.AURORIAN_GLASS, "Aurorian Glass", "极光玻璃");
        this.addBlock(TABlocks.DARK_STONE_GLASS, "Dark Stone Glass", "暗石玻璃");
        this.addBlock(TABlocks.MOON_GLASS_PANE, "Moon Glass Pane", "皎月玻璃板");
        this.addBlock(TABlocks.AURORIAN_GLASS_PANE, "Aurorian Glass Pane", "极光玻璃板");
        this.addBlock(TABlocks.DARK_STONE_GLASS_PANE, "Dark Stone Glass Pane", "暗石玻璃板");
        this.addBlock(TABlocks.AURORIAN_GRASS, "Aurorian Grass", "极光草");
        this.addBlock(TABlocks.AURORIAN_GRASS_LIGHT, "Light Aurorian Grass", "发光极光草");
        this.addBlock(TABlocks.AURORIAN_WATER_GRASS, "Aurorian Water Grass", "极光水草");
        this.addBlock(TABlocks.AURORIAN_LILY_PAD, "Aurorian Lily Pad", "极光睡莲");
        this.addBlock(TABlocks.AURORIAN_WATER_MUSHROOM, "Aurorian Water Mushroom", "极光水菇");
        this.addBlock(TABlocks.AURORIAN_FURNACE, "Aurorian Furnace", "极光熔炉");
        this.addBlock(TABlocks.AURORIAN_FURNACE_CHIMNEY, "Aurorian Furnace Chimney", "极光熔炉孔道");
        this.addBlock(TABlocks.AURORIAN_CRAFTING_TABLE, "Aurorian Crafting Table", "极光工作台");
        this.addBlock(TABlocks.AURORIAN_PORTAL, "Aurorian Portal", "极光传送门");
        this.addBlock(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS, "Aurorian Portal Frame Brick", "极光传送门框架");
        this.addBlock(TABlocks.URN, "Urn", "瓮");
        this.addBlock(TABlocks.MYSTERIUM_WOOL, "Mysterium Wool", "秘境羊毛");
        this.addBlock(TABlocks.DREAMSCAPE_PISTIL, "Dreamscape Pistil", "梦境之蕊");
        this.addBlock(TABlocks.FROST_TEARS_FLOWER, "Frost Tears Flower", "霜泪花");
        this.addBlock(TABlocks.NEBULA_BLOSSOM_CLUSTER, "Nebula Blossom Cluster", "星云花簇");
        this.addBlock(TABlocks.MOON_FROST_FLOWER, "Moon Frost Flower", "月霜花");
        this.addBlock(TABlocks.VOID_CANDLE_FLOWER, "Void Candle Flower", "空烛花");
        this.addBlock(TABlocks.EQUINOX_FLOWER, "Equinox Flower", "彼岸花");
        this.addBlock(TABlocks.WICK_GRASS, "Wick Grass", "灯芯草");
        this.addBlock(TABlocks.TALL_WICK_GRASS, "Tall Wick Grass", "高灯芯草");
        this.addBlock(TABlocks.LAVENDER_PLANT, "Lavender Plant", "薰衣草");
        this.addBlock(TABlocks.PETUNIA_PLANT, "Petunia Plant", "牵牛花");
        this.addBlock(TABlocks.CRISPED_MALLOW, "Crisped Mallow", "冬葵");
        this.addBlock(TABlocks.FROST_SNOW_GRASS, "Frost Snow Grass", "霜雪草");
        this.addBlock(TABlocks.ICE_CALENDULA, "Ice Calendula", "冰金盏");
        this.addBlock(TABlocks.WINTER_ROOT, "Winter Root", "冬根草");
        this.addBlock(TABlocks.TALL_LAVENDER_PLANT, "Tall Lavender Plant", "高薰衣草");
        this.addBlock(TABlocks.TALL_AURORIAN_GRASS, "Tall Aurorian Grass", "高极光草");
        this.addBlock(TABlocks.TALL_AURORIAN_WATER_GRASS, "Tall Aurorian Water Grass", "高极光水草");
        this.addBlock(TABlocks.TALL_AURORIAN_GRASS_LIGHT, "Tall Aurorian Light Grass", "发光高极光草");
        this.addBlock(TABlocks.AURORIAN_PERIDOTITE, "Aurorian Peridotite", "极光橄榄岩");
        this.addBlock(TABlocks.SMOOTH_AURORIAN_PERIDOTITE, "Smooth Aurorian Peridotite", "平滑极光橄榄岩");
        this.addBlock(TABlocks.MOONSTONE_ORE, "Moonstone Ore", "皎月石矿石");
        this.addBlock(TABlocks.EROSIVE_MOONSTONE_ORE, "Erosive Moonstone Ore","侵蚀化皎月石矿石");
        this.addBlock(TABlocks.CERULEAN_ORE, "Cerulean Ore", "晶蓝矿石");
        this.addBlock(TABlocks.EROSIVE_CERULEAN_ORE,"Erosive Cerulean Ore","侵蚀化晶蓝矿石");
        this.addBlock(TABlocks.GEODE_ORE, "Geode Ore", "晶簇矿石");
        this.addBlock(TABlocks.EROSIVE_GEODE_ORE, "Erosive Geode Ore", "侵蚀化晶簇矿石");
        this.addBlock(TABlocks.AURORIAN_EMERALD_ORE, "Aurorian Emerald Ore", "极光绿宝石矿石");
        this.addBlock(TABlocks.AURORIAN_GOLD_ORE, "Aurorian Gold Ore", "极光金矿石");
        this.addBlock(TABlocks.AURORIAN_IRON_ORE, "Aurorian Iron Ore", "极光铁矿石");
        this.addBlock(TABlocks.AURORIAN_LAPIS_ORE, "Aurorian Lapis Ore", "极光青金石矿石");
        this.addBlock(TABlocks.AURORIAN_REDSTONE_ORE, "Aurorian Redstone Ore", "极光红石矿石");
        this.addBlock(TABlocks.AURORIAN_COPPER_ORE, "Aurorian Copper Ore", "极光铜矿石");
        this.addBlock(TABlocks.AURORIAN_DIAMOND_ORE, "Aurorian Diamond Ore", "极光钻石矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_EMERALD_ORE, "Erosive Aurorian Emerald Ore", "侵蚀化极光绿宝石矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_GOLD_ORE, "Erosive Aurorian Gold Ore", "侵蚀化极光金矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_IRON_ORE, "Erosive Aurorian Iron Ore", "侵蚀化极光铁矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_LAPIS_ORE, "Erosive Aurorian Lapis Ore", "侵蚀化极光青金石矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE, "Erosive Aurorian Redstone Ore", "侵蚀化极光红石矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_COPPER_ORE, "Erosive Aurorian Copper Ore", "侵蚀化极光铜矿石");
        this.addBlock(TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE, "Erosive Aurorian Diamond Ore", "侵蚀化极光钻石矿石");
        this.addBlock(TABlocks.RUNE_STONE, "Runestone", "符石");
        this.addBlock(TABlocks.SMOOTH_RUNE_STONE, "Smooth Runestone", "平滑符石");
        this.addBlock(TABlocks.CHISELED_RUNE_STONE, "Chiseled Runestone", "雕纹符石");
        this.addBlock(TABlocks.AURORIAN_CASTLE_RUNE_STONE, "Aurorian Castle Runestone", "极光符文符石");
        this.addBlock(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE, "Aurorian Steel Castle Runestone", "极光钢符文符石");
        this.addBlock(TABlocks.CERULEAN_CASTLE_RUNE_STONE, "Cerulean Castle Runestone", "晶蓝符文符石");
        this.addBlock(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE, "Crystalline Castle Runestone", "月凝晶符文符石");
        this.addBlock(TABlocks.MOON_CASTLE_RUNE_STONE, "Moon Castle Runestone", "皎月符文符石");
        this.addBlock(TABlocks.TRANSPARENT_RUNE_STONE, "Transparent Runestone", "透明符石");
        this.addBlock(TABlocks.UMBRA_CASTLE_RUNE_STONE, "Umbra Castle Runestone", "本影符文符石");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE, "Luminous Aurorian Castle Runestone", "发光极光符文符石");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE, "Luminous Aurorian Steel Castle Runestone", "发光极光钢符文符石");
        this.addBlock(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE, "Luminous Cerulean Castle Runestone", "发光晶蓝符文符石");
        this.addBlock(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE, "Luminous Moon Castle Runestone", "发光月凝晶符文符石");
        this.addBlock(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE, "Luminous Moon Castle Runestone", "发光皎月符文符石");
        this.addBlock(TABlocks.RUNE_STONE_PILLAR, "Runestone Pillar", "符石柱");
        this.addBlock(TABlocks.DARK_STONE_PILLAR, "Dark Stone Pillar", "暗石柱");
        this.addBlock(TABlocks.MOON_TEMPLE_PILLAR, "Moon Temple Pillar", "月宫柱");
        this.addBlock(TABlocks.MOON_TEMPLE_BRICKS, "Moon Temple Bricks", "月宫砖块");
        this.addBlock(TABlocks.DARK_STONE_BRICKS, "Dark Stone Bricks", "暗石砖块");
        this.addBlock(TABlocks.DARK_STONE_FANCY, "Dark Stone Fancy", "装饰性暗石");
        this.addBlock(TABlocks.DARK_STONE_LAYERS, "Dark Stone Layers", "暗石地层");
        this.addBlock(TABlocks.SMOOTH_DARK_STONE_BRICKS, "Smooth Dark Stone", "平滑暗石");
        this.addBlock(TABlocks.CHISELED_DARK_STONE_BRICKS, "Chiseled Dark Stone", "雕纹暗石");
        this.addBlock(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS, "Smooth Moon Temple Bricks", "平滑月宫砖块");
        this.addBlock(TABlocks.CHISELED_MOON_TEMPLE_BRICKS, "Chiseled Moon Temple Bricks", "雕纹月宫砖块");
        this.addBlock(TABlocks.RUNE_STONE_LAMP, "Runestone Lamp", "符石灯");
        this.addBlock(TABlocks.DARK_STONE_LAMP, "Dark Stone Lamp", "暗石灯");
        this.addBlock(TABlocks.MOON_TEMPLE_LAMP, "Moon Temple Lamp", "月宫灯");
        this.addBlock(TABlocks.VOID_STONE, "Void Stone", "虚空石");
        this.addBlock(TABlocks.RUNE_CRYSTAL, "Rune Crystal", "符石水晶");
        this.addBlock(TABlocks.CERULEAN_BLOCK, "Cerulean Block", "晶蓝块");
        this.addBlock(TABlocks.MOONSTONE_BLOCK, "Moonstone Block", "皎月石块");
        this.addBlock(TABlocks.AURORIAN_COAL_BLOCK, "Aurorian Coal Block", "极光煤块");
        this.addBlock(TABlocks.AURORIAN_STEEL_BLOCK, "Aurorian Steel Block", "极光钢块");
        this.addBlock(TABlocks.SMALL_FILTHY_ICE_SPIKE, "Small Filthy Ice Spike", "小型污秽冰刺");
        this.addBlock(TABlocks.MEDIUM_FILTHY_ICE_SPIKE, "Medium Filthy Ice Spike", "中型污秽冰刺");
        this.addBlock(TABlocks.LARGE_FILTHY_ICE_SPIKE, "Large Filthy Ice Spike", "大型污秽冰刺");
        this.addBlock(TABlocks.CERULEAN_CLUSTER, "Cerulean Cluster", "晶蓝簇");
        this.addBlock(TABlocks.LARGE_CERULEAN_BUD, "Large Cerulean Bud", "大型晶蓝芽");
        this.addBlock(TABlocks.MEDIUM_CERULEAN_BUD, "Medium Cerulean Bud", "中型晶蓝芽");
        this.addBlock(TABlocks.SMALL_CERULEAN_BUD, "Small Cerulean Bud", "小型晶蓝芽");
        this.addBlock(TABlocks.MOONSTONE_CLUSTER, "Cerulean Cluster", "皎月石簇");
        this.addBlock(TABlocks.LARGE_MOONSTONE_BUD, "Large Moonstone Bud", "大型皎月石芽");
        this.addBlock(TABlocks.MEDIUM_MOONSTONE_BUD, "Medium Moonstone Bud", "中型皎月石芽");
        this.addBlock(TABlocks.SMALL_MOONSTONE_BUD, "Small Moonstone Bud", "小型皎月石芽");
        this.addBlock(TABlocks.MYSTICAL_BARRIER, "Mystical Barrier", "神秘屏障");
        this.addBlock(TABlocks.RUNE_STONE_BARS, "Rune stone Bars", "符石栏杆");
        this.addBlock(TABlocks.DARK_STONE_BARS, "Dark stone Bars", "暗石栏杆");
        this.addBlock(TABlocks.MOON_TEMPLE_BARS, "Moon Temple Bars", "月宫栏杆");
        this.addBlock(TABlocks.RUNE_STONE_GATE, "Runestone Gate", "符石门");
        this.addBlock(TABlocks.DARK_STONE_GATE, "Dark Stone Gate", "暗石门");
        this.addBlock(TABlocks.MOON_TEMPLE_GATE, "Moon Temple Gate", "月宫门");
        this.addBlock(TABlocks.RUNE_STONE_LOOT_GATE, "Runestone Loot Gate", "符石宝藏室门");
        this.addBlock(TABlocks.MOON_TEMPLE_CELL_GATE, "Moon Temple Cell Gate", "月宫内室门");
        this.addBlock(TABlocks.RUNE_STONE_GATE_KEYHOLE, "Runestone Gate Keyhole", "符石门锁孔");
        this.addBlock(TABlocks.DARK_STONE_GATE_KEYHOLE, "Dark Stone Gate Keyhole", "暗石门锁孔");
        this.addBlock(TABlocks.MOON_TEMPLE_GATE_KEYHOLE, "Moon Temple Gate Keyhole", "月宫门锁孔");
        this.addBlock(TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE, "Runestone Loot Gate Keyhole", "符石宝藏室门锁孔");
        this.addBlock(TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE, "Moon Temple Cell Gate Keyhole", "月宫内室门锁孔");
        this.addBlock(TABlocks.INDIGO_MUSHROOM, "Indigo Mushroom", "深蓝蘑菇");
        this.addBlock(TABlocks.INDIGO_MUSHROOM_BLOCK, "Indigo Mushroom Block", "深蓝蘑菇块");
        this.addBlock(TABlocks.INDIGO_MUSHROOM_STEM, "Indigo Mushroom Stem", "深蓝蘑菇茎");
        this.addBlock(TABlocks.INDIGO_MUSHROOM_CRYSTAL, "Indigo Mushroom Crystal", "深蓝蘑菇水晶");
        this.addBlock(TABlocks.MOONLIGHT_FORGE, "Moonlight Forge", "月光融锻台");
        this.addBlock(TABlocks.MOON_GEM, "Moon Gem", "皎月宝石");
        this.addBlock(TABlocks.MOON_SAND, "Moon Sand", "皎月沙");
        this.addBlock(TABlocks.MOON_SAND_RIVER, "River Moon Sand", "皎月河沙");
        this.addBlock(TABlocks.MOON_SANDSTONE, "Moon Sandstone", "皎月砂岩");
        this.addBlock(TABlocks.CUT_MOON_SANDSTONE, "Cut Moon Sandstone", "切制皎月砂岩");
        this.addBlock(TABlocks.SMOOTH_MOON_SANDSTONE, "Smooth Moon Sandstone", "平滑皎月砂岩");
        this.addBlock(TABlocks.BRIGHT_MOON_SAND, "Bright Moon Sand", "皓月沙");
        this.addBlock(TABlocks.BRIGHT_MOON_SANDSTONE, "Bright Moon Sandstone", "皓月砂岩");
        this.addBlock(TABlocks.MOON_TORCH, "Moon Torch", "皎月火把");
        this.addBlock(TABlocks.SCRAPPER, "Scrapper", "粉碎器");
        this.addBlock(TABlocks.UMBRA_STONE, "Umbra Stone", "本影石");
        this.addBlock(TABlocks.UMBRA_STONE_CRACKED, "Cracked Umbra Stone", "裂纹本影石");
        this.addBlock(TABlocks.UMBRA_STONE_ROOF_TILES, "Umbra Stone Roof Tiles", "本影石瓦");
        this.addBlock(TABlocks.STRIPPED_SILENT_TREE_LOG, "Stripped Silent Tree Log", "去皮谧树原木");
        this.addBlock(TABlocks.STRIPPED_SILENT_TREE_WOOD, "Stripped Silent Tree Wood", "去皮谧树木头");
        this.addBlock(TABlocks.SILENT_TREE_LEAVES, "Silent Tree Leaves", "谧树树叶");
        this.addBlock(TABlocks.SILENT_TREE_PLANKS, "Silent Tree Planks", "谧树木板");
        this.addBlock(TABlocks.SILENT_TREE_LOG, "Silent Tree Log", "谧树原木");
        this.addBlock(TABlocks.SILENT_TREE_WOOD, "Silent Tree Wood", "谧树木头");
        this.addBlock(TABlocks.SILENT_TREE_SAPLING, "Silent Tree Sapling", "谧树树苗");
        this.addBlock(TABlocks.SILENT_WOOD_TORCH, "Silent Wood Torch", "谧木火把");
        this.addBlock(TABlocks.SILENT_WOOD_CHEST, "Silent Wood Chest", "谧木箱子");
        this.addBlock(TABlocks.SILENT_WOOD_LADDER, "Silent Wood Ladder", "谧木梯子");
        this.addBlock(TABlocks.SILENT_WOOD_SIGN, "Silent Wood Sign", "谧木告示牌");
        this.addBlock(TABlocks.SILENT_WOOD_HANGING_SIGN, "Silent Wood Hanging Sign", "悬挂式谧木告示牌");
        this.addBlock(TABlocks.STRIPPED_WEEPING_WILLOW_LOG, "Stripped Weeping Willow Log", "去皮垂柳原木");
        this.addBlock(TABlocks.STRIPPED_WEEPING_WILLOW_WOOD, "Stripped Weeping Willow Wood", "去皮垂柳木头");
        this.addBlock(TABlocks.WEEPING_WILLOW_LEAVES, "Weeping Willow Leaves", "垂柳树叶");
        this.addBlock(TABlocks.WEEPING_WILLOW_PLANKS, "Weeping Willow Planks", "垂柳木板");
        this.addBlock(TABlocks.WEEPING_WILLOW_LOG, "Weeping Willow Log", "垂柳原木");
        this.addBlock(TABlocks.WEEPING_WILLOW_WOOD, "Weeping Willow Wood", "垂柳木头");
        this.addBlock(TABlocks.WEEPING_WILLOW_WOOD_SIGN, "Weeping Willow Wood Sign", "垂柳木告示牌");
        this.addBlock(TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN, "Weeping Willow Wood Hanging Sign", "悬挂式垂柳木告示牌");
        this.addBlock(TABlocks.STRIPPED_CURTAIN_TREE_LOG, "Stripped Curtain Tree Log", "去皮幽帘树原木");
        this.addBlock(TABlocks.STRIPPED_CURTAIN_TREE_WOOD, "Stripped Curtain Tree Wood", "去皮幽帘树木头");
        this.addBlock(TABlocks.CURTAIN_TREE_LEAVES, "Curtain Tree Leaves", "幽帘树叶");
        this.addBlock(TABlocks.CURTAIN_TREE_PLANKS, "Curtain Tree Planks", "幽帘树木板");
        this.addBlock(TABlocks.CURTAIN_TREE_LOG, "Curtain Tree Log", "幽帘树原木");
        this.addBlock(TABlocks.CURTAIN_TREE_WOOD, "Curtain Tree Wood", "幽帘树木头");
        this.addBlock(TABlocks.CURTAIN_TREE_SAPLING, "Curtain Tree Sapling", "幽帘树苗");
        this.addBlock(TABlocks.CURTAIN_WOOD_SIGN, "Curtain Wood Sign", "幽帘木告示牌");
        this.addBlock(TABlocks.CURTAIN_WOOD_HANGING_SIGN, "Curtain Wood Hanging Sign", "悬挂式幽帘木告示牌");
        this.addBlock(TABlocks.STRIPPED_CURSED_FROST_TREE_LOG, "Stripped Cursed Frost Tree Log", "去皮咒霜树原木");
        this.addBlock(TABlocks.STRIPPED_CURSED_FROST_TREE_WOOD, "Stripped Cursed Frost Tree Wood", "去皮咒霜树木头");
        this.addBlock(TABlocks.CURSED_FROST_TREE_LEAVES, "Cursed Frost Tree Leaves", "咒霜树叶");
        this.addBlock(TABlocks.CURSED_FROST_TREE_PLANKS, "Cursed Frost Tree Planks", "咒霜树木板");
        this.addBlock(TABlocks.CURSED_FROST_TREE_LOG, "Cursed Frost Tree Log", "咒霜树原木");
        this.addBlock(TABlocks.CURSED_FROST_TREE_WOOD, "Cursed Frost Tree Wood", "咒霜树木头");
        this.addBlock(TABlocks.CURSED_FROST_TREE_SAPLING, "Cursed Frost Tree Sapling", "咒霜树苗");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_SIGN, "Cursed Frost Wood Sign", "咒霜木告示牌");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_HANGING_SIGN, "Cursed Frost Wood Hanging Sign", "悬挂式咒霜木告示牌");
        this.addBlock(TABlocks.SILENT_WOOD_PRESSURE_PLATE, "Silent Wood Pressure Plate", "谧木压力板");
        this.addBlock(TABlocks.WEEPING_WILLOW_PRESSURE_PLATE, "Weeping Willow Pressure Plate", "垂柳木压力板");
        this.addBlock(TABlocks.CURTAIN_WOOD_PRESSURE_PLATE, "Curtain Wood Pressure Plate", "幽帘木压力板");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_PRESSURE_PLATE, "Cursed Frost Wood Pressure Plate", "咒霜木压力板");
        this.addBlock(TABlocks.SILENT_WOOD_FENCE_GATE, "Silent Wood Fence Gate", "谧木栅栏门");
        this.addBlock(TABlocks.WEEPING_WILLOW_FENCE_GATE, "Weeping Willow Fence Gate", "垂柳木栅栏门");
        this.addBlock(TABlocks.CURTAIN_WOOD_FENCE_GATE, "Curtain Wood Fence Gate", "幽帘木栅栏门");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_FENCE_GATE, "Cursed Frost Wood Fence Gate", "咒霜木栅栏门");
        this.addBlock(TABlocks.SILENT_WOOD_TRAPDOOR, "Silent Wood Trapdoor", "谧木活板门");
        this.addBlock(TABlocks.WEEPING_WILLOW_TRAPDOOR, "Weeping Willow Trapdoor", "垂柳木活板门");
        this.addBlock(TABlocks.CURTAIN_WOOD_TRAPDOOR, "Curtain Wood Trapdoor", "幽帘木活板门");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_TRAPDOOR, "Cursed Frost Wood Trapdoor", "咒霜木活板门");
        this.addBlock(TABlocks.SILENT_WOOD_BUTTON, "Silent Wood Button", "谧木按钮");
        this.addBlock(TABlocks.WEEPING_WILLOW_BUTTON, "Weeping Willow Button", "垂柳木按钮");
        this.addBlock(TABlocks.CURTAIN_WOOD_BUTTON, "Curtain Wood Button", "幽帘木按钮");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_BUTTON, "Cursed Frost Wood Button", "咒霜木按钮");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STONE_STAIRS, "Vertical Aurorian Stone Stairs", "竖极光石楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STONE_BRICK_STAIRS, "Vertical Aurorian Stone Brick Stairs", "竖极光石砖楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_COBBLESTONE_STAIRS, "Vertical Aurorian Cobblestone Stairs", "竖极光圆石楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_GRANITE_STAIRS, "Vertical Aurorian Granite Stairs", "竖极光花岗岩楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_DIORITE_STAIRS, "Vertical Aurorian Diorite Stairs", "竖极光闪长岩楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_ANDESITE_STAIRS, "Vertical Aurorian Andesite Stairs", "竖极光安山岩楼梯");
        this.addBlock(TABlocks.VERTICAL_RUNE_STONE_STAIRS, "Vertical Runestone Stairs", "竖符石楼梯");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_RUNE_STONE_STAIRS, "Vertical Smooth Runestone Stairs", "竖平滑符石楼梯");
        this.addBlock(TABlocks.VERTICAL_CHISELED_RUNE_STONE_STAIRS, "Vertical Chiseled Runestone Stairs", "竖雕纹符石楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_CASTLE_RUNE_STONE_STAIRS, "Vertical Aurorian Castle Runestone Stairs", "竖极光符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS, "Vertical Aurorian Steel Castle Runestone Stairs", "竖极光钢符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_CERULEAN_CASTLE_RUNE_STONE_STAIRS, "Vertical Cerulean Castle Runestone Stairs", "竖晶蓝符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS, "Vertical Crystalline Castle Runestone Stairs", "竖月凝晶符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_MOON_CASTLE_RUNE_STONE_STAIRS, "Vertical Moon Castle Runestone Stairs", "竖皎月符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS, "Vertical Luminous Aurorian Castle Runestone Stairs", "竖发光极光符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS, "Vertical Luminous Aurorian Steel Castle Runestone Stairs", "竖发光极光钢符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS, "Vertical Luminous Cerulean Castle Runestone Stairs", "竖发光晶蓝符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS, "Vertical Luminous Crystalline Castle Runestone Stairs", "竖发光月凝晶符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS, "Vertical Luminous Moon Castle Runestone Stairs", "竖发光皎月符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_TRANSPARENT_RUNE_STONE_STAIRS, "Vertical Transparent Runestone Stairs", "竖透明符石楼梯");
        this.addBlock(TABlocks.VERTICAL_UMBRA_CASTLE_RUNE_STONE_STAIRS, "Vertical Umbra Castle Runestone Stairs", "竖本影符文符石楼梯");
        this.addBlock(TABlocks.VERTICAL_DARK_STONE_BRICK_STAIRS, "Vertical Dark Stone Brick Stairs", "竖暗石楼梯");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_DARK_STONE_BRICK_STAIRS, "Vertical Smooth Dark Stone Brick Stairs", "竖平滑暗石砖块楼梯");
        this.addBlock(TABlocks.VERTICAL_CHISELED_DARK_STONE_BRICK_STAIRS, "Vertical Chiseled Dark Stone Brick Stairs", "竖雕纹暗石砖块楼梯");
        this.addBlock(TABlocks.VERTICAL_MOON_TEMPLE_BRICK_STAIRS, "Vertical Moon Temple Brick Stairs", "竖月宫楼梯");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_STAIRS, "Vertical Smooth Moon Temple Brick Stairs", "竖平滑月宫楼梯");
        this.addBlock(TABlocks.VERTICAL_CHISELED_MOON_TEMPLE_BRICK_STAIRS, "Vertical Chiseled Moon Temple Brick Stairs", "竖雕纹月宫楼梯");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_STAIRS, "Vertical Umbra Stone Stairs", "竖本影石楼梯");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_CRACKED_STAIRS, "Vertical Cracked Umbra Stone Stairs", "竖裂纹本影石楼梯");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_ROOF_STAIRS, "Vertical Umbra Stone Roof Tiles Stairs", "竖本影石瓦楼梯");
        this.addBlock(TABlocks.VERTICAL_SILENT_WOOD_STAIRS, "Vertical Silent Wood Stairs", "竖谧木楼梯");
        this.addBlock(TABlocks.VERTICAL_WEEPING_WILLOW_STAIRS, "Vertical Weeping Willow Stairs", "竖垂柳木楼梯");
        this.addBlock(TABlocks.VERTICAL_CURTAIN_WOOD_STAIRS, "Vertical Curtain Wood Stairs", "竖幽帘木楼梯");
        this.addBlock(TABlocks.VERTICAL_CURSED_FROST_WOOD_STAIRS, "Vertical Cursed Frost Wood Stairs", "竖咒霜木楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_PERIDOTITE_STAIRS, "Vertical Aurorian Peridotite Stairs", "竖极光橄榄岩楼梯");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_STAIRS, "Vertical Smooth Aurorian Peridotite Stairs", "竖平滑极光橄榄岩楼梯");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STONE_SLAB, "Vertical Aurorian Stone Slab", "竖极光石台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STONE_BRICK_SLAB, "Vertical Aurorian Stone Brick Slab", "竖极光石砖台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_COBBLESTONE_SLAB, "Vertical Aurorian Cobblestone Slab", "竖极光圆石台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_GRANITE_SLAB, "Vertical Aurorian Granite Slab", "竖极光花岗岩台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_DIORITE_SLAB, "Vertical Aurorian Diorite Slab", "竖极光闪长岩台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_ANDESITE_SLAB, "Vertical Aurorian Andesite Slab", "竖极光安山岩台阶");
        this.addBlock(TABlocks.VERTICAL_RUNE_STONE_SLAB, "Vertical Runestone Slab", "竖符石台阶");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_RUNE_STONE_SLAB, "Vertical Smooth Runestone Slab", "竖平滑符石台阶");
        this.addBlock(TABlocks.VERTICAL_CHISELED_RUNE_STONE_SLAB, "Vertical Chiseled Runestone Slab", "竖雕纹符石台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_CASTLE_RUNE_STONE_SLAB, "Vertical Aurorian Castle Runestone Slab", "竖极光符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB, "Vertical Aurorian Steel Castle Runestone Slab", "竖极光钢符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_CERULEAN_CASTLE_RUNE_STONE_SLAB, "Vertical Cerulean Castle Runestone Slab", "竖晶蓝符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB, "Vertical Crystalline Castle Runestone Slab", "竖月凝晶符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_MOON_CASTLE_RUNE_STONE_SLAB, "Vertical Moon Castle Runestone Slab", "竖皎月符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB, "Vertical Luminous Aurorian Castle Runestone Slab", "竖发光极光符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB, "Vertical Luminous Aurorian Steel Castle Runestone Slab", "竖发光极光钢符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB, "Vertical Luminous Cerulean Castle Runestone Slab", "竖发光晶蓝符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB, "Vertical Luminous Crystalline Castle Runestone Slab", "竖发光月凝晶符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB, "Vertical Luminous Moon Castle Runestone Slab", "竖发光皎月符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_TRANSPARENT_RUNE_STONE_SLAB, "Vertical Transparent Runestone Slab", "竖透明符石台阶");
        this.addBlock(TABlocks.VERTICAL_UMBRA_CASTLE_RUNE_STONE_SLAB, "Vertical Umbra Castle Runestone Slab", "竖本影符文符石台阶");
        this.addBlock(TABlocks.VERTICAL_DARK_STONE_BRICK_SLAB, "Vertical Dark Stone Brick Slab", "竖暗石台阶");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_DARK_STONE_BRICK_SLAB, "Vertical Smooth Dark Stone Brick Slab", "竖平滑暗石砖块台阶");
        this.addBlock(TABlocks.VERTICAL_CHISELED_DARK_STONE_BRICK_SLAB, "Vertical Chiseled Dark Stone Brick Slab", "竖雕纹暗石砖块台阶");
        this.addBlock(TABlocks.VERTICAL_MOON_TEMPLE_BRICK_SLAB, "Vertical Moon Temple Brick Slab", "竖月宫台阶");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_SLAB, "Vertical Smooth Moon Temple Brick Slab", "竖平滑月宫台阶");
        this.addBlock(TABlocks.VERTICAL_CHISELED_MOON_TEMPLE_BRICK_SLAB, "Vertical Chiseled Moon Temple Brick Slab", "竖雕纹月宫台阶");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_SLAB, "Vertical Umbra Stone Slab", "竖本影石台阶");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_CRACKED_SLAB, "Vertical Cracked Umbra Stone Slab", "竖裂纹本影石台阶");
        this.addBlock(TABlocks.VERTICAL_UMBRA_STONE_ROOF_SLAB, "Vertical Umbra Stone Roof Tiles Slab", "竖本影石瓦台阶");
        this.addBlock(TABlocks.VERTICAL_SILENT_WOOD_SLAB, "Vertical Silent Wood Slab", "竖谧木台阶");
        this.addBlock(TABlocks.VERTICAL_WEEPING_WILLOW_SLAB, "Vertical Weeping Willow Slab", "竖垂柳木台阶");
        this.addBlock(TABlocks.VERTICAL_CURTAIN_WOOD_SLAB, "Vertical Curtain Wood Slab", "竖幽帘木台阶");
        this.addBlock(TABlocks.VERTICAL_CURSED_FROST_WOOD_SLAB, "Vertical Cursed Frost Wood Slab", "竖咒霜木台阶");
        this.addBlock(TABlocks.VERTICAL_AURORIAN_PERIDOTITE_SLAB, "Vertical Aurorian Peridotite Slab", "竖极光橄榄岩台阶");
        this.addBlock(TABlocks.VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_SLAB, "Vertical Smooth Aurorian Peridotite Slab", "竖平滑极光橄榄岩台阶");
        this.addBlock(TABlocks.AURORIAN_STONE_STAIRS, "Vertical Aurorian Stone Stairs", "极光石楼梯");
        this.addBlock(TABlocks.AURORIAN_STONE_BRICK_STAIRS, "Vertical Aurorian Stone Brick Stairs", "极光石砖楼梯");
        this.addBlock(TABlocks.AURORIAN_COBBLESTONE_STAIRS, "Vertical Aurorian Cobblestone Stairs", "极光圆石楼梯");
        this.addBlock(TABlocks.AURORIAN_GRANITE_STAIRS, "Aurorian Granite Stairs", "极光花岗岩楼梯");
        this.addBlock(TABlocks.AURORIAN_DIORITE_STAIRS, "Aurorian Diorite Stairs", "极光闪长岩楼梯");
        this.addBlock(TABlocks.AURORIAN_ANDESITE_STAIRS, "Aurorian Andesite Stairs", "极光安山岩楼梯");
        this.addBlock(TABlocks.RUNE_STONE_STAIRS, "Runestone Stairs", "符石楼梯");
        this.addBlock(TABlocks.SMOOTH_RUNE_STONE_STAIRS, "Smooth Runestone Stairs", "平滑符石楼梯");
        this.addBlock(TABlocks.CHISELED_RUNE_STONE_STAIRS, "Chiseled Runestone Stairs", "雕纹符石楼梯");
        this.addBlock(TABlocks.AURORIAN_CASTLE_RUNE_STONE_STAIRS, "Aurorian Castle Runestone Stairs", "极光符文符石楼梯");
        this.addBlock(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS, "Aurorian Steel Castle Runestone Stairs", "极光钢符文符石楼梯");
        this.addBlock(TABlocks.CERULEAN_CASTLE_RUNE_STONE_STAIRS, "Cerulean Castle Runestone Stairs", "晶蓝符文符石楼梯");
        this.addBlock(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS, "Crystalline Castle Runestone Stairs", "月凝晶符文符石楼梯");
        this.addBlock(TABlocks.MOON_CASTLE_RUNE_STONE_STAIRS, "Moon Castle Runestone Stairs", "皎月符文符石楼梯");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS, "Luminous Aurorian Castle Runestone Stairs", "发光极光符文符石楼梯");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS, "Luminous Aurorian Steel Castle Runestone Stairs", "发光极光钢符文符石楼梯");
        this.addBlock(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS, "Luminous Cerulean Castle Runestone Stairs", "发光晶蓝符文符石楼梯");
        this.addBlock(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS, "Luminous Crystalline Castle Runestone Stairs", "发光月凝晶符文符石楼梯");
        this.addBlock(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS, "Luminous Moon Castle Runestone Stairs", "发光皎月符文符石楼梯");
        this.addBlock(TABlocks.TRANSPARENT_RUNE_STONE_STAIRS, "Transparent Runestone Stairs", "透明符石楼梯");
        this.addBlock(TABlocks.UMBRA_CASTLE_RUNE_STONE_STAIRS, "Umbra Castle Runestone Stairs", "本影符文符石楼梯");
        this.addBlock(TABlocks.DARK_STONE_BRICK_STAIRS, "Dark Stone Brick Stairs", "暗石楼梯");
        this.addBlock(TABlocks.SMOOTH_DARK_STONE_BRICK_STAIRS, "Smooth Dark Stone Brick Stairs", "平滑暗石砖块楼梯");
        this.addBlock(TABlocks.CHISELED_DARK_STONE_BRICK_STAIRS, "Chiseled Dark Stone Brick Stairs", "雕纹暗石砖块楼梯");
        this.addBlock(TABlocks.MOON_TEMPLE_BRICK_STAIRS, "Moon Temple Brick Stairs", "月宫楼梯");
        this.addBlock(TABlocks.SMOOTH_MOON_TEMPLE_BRICK_STAIRS, "Smooth Moon Temple Brick Stairs", "平滑月宫楼梯");
        this.addBlock(TABlocks.CHISELED_MOON_TEMPLE_BRICK_STAIRS, "Chiseled Moon Temple Brick Stairs", "雕纹月宫楼梯");
        this.addBlock(TABlocks.UMBRA_STONE_STAIRS, "Umbra Stone Stairs", "本影石楼梯");
        this.addBlock(TABlocks.UMBRA_STONE_CRACKED_STAIRS, "Cracked Umbra Stone Stairs", "裂纹本影石楼梯");
        this.addBlock(TABlocks.UMBRA_STONE_ROOF_STAIRS, "Umbra Stone Roof Tiles Stairs", "本影石瓦楼梯");
        this.addBlock(TABlocks.SILENT_WOOD_STAIRS, "Silent Wood Stairs", "谧木楼梯");
        this.addBlock(TABlocks.WEEPING_WILLOW_STAIRS, "Weeping Willow Stairs", "垂柳木楼梯");
        this.addBlock(TABlocks.CURTAIN_WOOD_STAIRS, "Curtain Wood Stairs", "幽帘木楼梯");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_STAIRS, "Cursed Frost Wood Stairs", "咒霜木楼梯");
        this.addBlock(TABlocks.AURORIAN_PERIDOTITE_STAIRS, "Aurorian Peridotite Stairs", "极光橄榄岩楼梯");
        this.addBlock(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS, "Smooth Aurorian Peridotite Stairs", "平滑极光橄榄岩楼梯");
        this.addBlock(TABlocks.SILENT_WOOD_FENCE, "Silent Wood Fence", "谧木栅栏");
        this.addBlock(TABlocks.WEEPING_WILLOW_FENCE, "Weeping Willow Fence", "垂柳木栅栏");
        this.addBlock(TABlocks.CURTAIN_WOOD_FENCE, "Curtain Wood Fence", "幽帘木栅栏");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_FENCE, "Cursed Frost Wood Fence", "咒霜木栅栏");
        this.addBlock(TABlocks.SILENT_WOOD_DOOR, "Silent Wood Door", "谧木门");
        this.addBlock(TABlocks.WEEPING_WILLOW_DOOR, "Weeping Willow Door", "垂柳木门");
        this.addBlock(TABlocks.CURTAIN_WOOD_DOOR, "Curtain Wood Door", "幽帘木门");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_DOOR, "Cursed Frost Wood Door", "咒霜木门");
        this.addBlock(TABlocks.AURORIAN_STONE_SLAB, "Aurorian Stone Slab", "极光石台阶");
        this.addBlock(TABlocks.AURORIAN_STONE_BRICK_SLAB, "Aurorian Stone Brick Slab", "极光石砖台阶");
        this.addBlock(TABlocks.AURORIAN_COBBLESTONE_SLAB, "Aurorian Cobblestone Slab", "极光圆石台阶");
        this.addBlock(TABlocks.AURORIAN_GRANITE_SLAB, "Aurorian Granite Slab", "极光花岗岩台阶");
        this.addBlock(TABlocks.AURORIAN_DIORITE_SLAB, "Aurorian Diorite Slab", "极光闪长岩台阶");
        this.addBlock(TABlocks.AURORIAN_ANDESITE_SLAB, "Aurorian Andesite Slab", "极光安山岩台阶");
        this.addBlock(TABlocks.RUNE_STONE_SLAB, "Runestone Slab", "符石台阶");
        this.addBlock(TABlocks.SMOOTH_RUNE_STONE_SLAB, "Smooth Runestone Slab", "平滑符石台阶");
        this.addBlock(TABlocks.CHISELED_RUNE_STONE_SLAB, "Chiseled Runestone Slab", "雕纹符石台阶");
        this.addBlock(TABlocks.AURORIAN_CASTLE_RUNE_STONE_SLAB, "Aurorian Castle Runestone Slab", "极光符文符石台阶");
        this.addBlock(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB, "Aurorian Steel Castle Runestone Slab", "极光钢符文符石台阶");
        this.addBlock(TABlocks.CERULEAN_CASTLE_RUNE_STONE_SLAB, "Cerulean Castle Runestone Slab", "晶蓝符文符石台阶");
        this.addBlock(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_SLAB, "Crystalline Castle Runestone Slab", "月凝晶符文符石台阶");
        this.addBlock(TABlocks.MOON_CASTLE_RUNE_STONE_SLAB, "Moon Castle Runestone Slab", "皎月符文符石台阶");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB, "Luminous Aurorian Castle Runestone Slab", "发光极光符文符石台阶");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB, "Luminous Aurorian Steel Castle Runestone Slab", "发光极光钢符文符石台阶");
        this.addBlock(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB, "Luminous Cerulean Castle Runestone Slab", "发光晶蓝符文符石台阶");
        this.addBlock(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB, "Luminous Crystalline Castle Runestone Slab", "发光月凝晶符文符石台阶");
        this.addBlock(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB, "Luminous Moon Castle Runestone Slab", "发光皎月符文符石台阶");
        this.addBlock(TABlocks.TRANSPARENT_RUNE_STONE_SLAB, "Transparent Runestone Slab", "透明符石台阶");
        this.addBlock(TABlocks.UMBRA_CASTLE_RUNE_STONE_SLAB, "Umbra Castle Runestone Slab", "本影符文符石台阶");
        this.addBlock(TABlocks.DARK_STONE_BRICK_SLAB, "Dark Stone Brick Slab", "暗石砖块台阶");
        this.addBlock(TABlocks.SMOOTH_DARK_STONE_BRICK_SLAB, "Smooth Dark Stone Brick Slab", "平滑暗石砖块台阶");
        this.addBlock(TABlocks.CHISELED_DARK_STONE_BRICK_SLAB, "Chiseled Dark Stone Brick Slab", "雕纹暗石砖块台阶");
        this.addBlock(TABlocks.MOON_TEMPLE_BRICK_SLAB, "Moon Temple Brick Slab", "月宫砖块台阶");
        this.addBlock(TABlocks.SMOOTH_MOON_TEMPLE_BRICK_SLAB, "Smooth Moon Temple Brick Slab", "平滑月宫砖块台阶");
        this.addBlock(TABlocks.CHISELED_MOON_TEMPLE_BRICK_SLAB, "Chiseled Moon Temple Brick Slab", "雕纹月宫砖块台阶");
        this.addBlock(TABlocks.UMBRA_STONE_SLAB, "Umbra Stone Slab", "本影石台阶");
        this.addBlock(TABlocks.UMBRA_STONE_CRACKED_SLAB, "Cracked Umbra Stone Slab", "裂纹本影石台阶");
        this.addBlock(TABlocks.UMBRA_STONE_ROOF_SLAB, "Umbra Stone Roof Tiles Slab", "本影石瓦台阶");
        this.addBlock(TABlocks.SILENT_WOOD_SLAB, "Silent Wood Slab", "谧木台阶");
        this.addBlock(TABlocks.WEEPING_WILLOW_SLAB, "Weeping Willow Slab", "垂柳木台阶");
        this.addBlock(TABlocks.CURTAIN_WOOD_SLAB, "Curtain Wood Slab", "幽帘木台阶");
        this.addBlock(TABlocks.CURSED_FROST_WOOD_SLAB, "Cursed Frost Wood Slab", "咒霜木台阶");
        this.addBlock(TABlocks.AURORIAN_PERIDOTITE_SLAB, "Aurorian Peridotite Slab", "橄榄岩台阶");
        this.addBlock(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_SLAB, "Smooth Aurorian Peridotite Slab", "平滑橄榄岩台阶");
        this.addBlock(TABlocks.AURORIAN_STONE_WALL, "Aurorian Stone Wall", "极光石墙");
        this.addBlock(TABlocks.AURORIAN_STONE_BRICK_WALL, "Aurorian Stone Brick Wall", "极光石砖墙");
        this.addBlock(TABlocks.AURORIAN_COBBLESTONE_WALL, "Aurorian Cobblestone Wall", "极光圆石墙");
        this.addBlock(TABlocks.AURORIAN_GRANITE_WALL, "Aurorian Granite Wall", "极光花岗岩墙");
        this.addBlock(TABlocks.AURORIAN_DIORITE_WALL, "Aurorian Diorite Wall", "极光闪长岩墙");
        this.addBlock(TABlocks.AURORIAN_ANDESITE_WALL, "Aurorian Andesite Wall", "极光安山岩墙");
        this.addBlock(TABlocks.RUNE_STONE_WALL, "Runestone Wall", "符石墙");
        this.addBlock(TABlocks.SMOOTH_RUNE_STONE_WALL, "Smooth Runestone Wall", "平滑符石墙");
        this.addBlock(TABlocks.CHISELED_RUNE_STONE_WALL, "Chiseled Runestone Wall", "雕纹符石墙");
        this.addBlock(TABlocks.AURORIAN_CASTLE_RUNE_STONE_WALL, "Aurorian Castle Runestone Wall", "极光符文符石墙");
        this.addBlock(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL, "Aurorian Steel Castle Runestone Wall", "极光钢符文符石墙");
        this.addBlock(TABlocks.CERULEAN_CASTLE_RUNE_STONE_WALL, "Cerulean Castle Runestone Wall", "晶蓝符文符石墙");
        this.addBlock(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_WALL, "Crystalline Castle Runestone Wall", "月凝晶符文符石墙");
        this.addBlock(TABlocks.MOON_CASTLE_RUNE_STONE_WALL, "Moon Castle Runestone Wall", "皎月符文符石墙");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_WALL, "Luminous Aurorian Castle Runestone Wall", "发光极光符文符石墙");
        this.addBlock(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL, "Luminous Aurorian Steel Castle Runestone Wall", "发光极光钢符文符石墙");
        this.addBlock(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_WALL, "Luminous Cerulean Castle Runestone Wall", "发光晶蓝符文符石墙");
        this.addBlock(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_WALL, "Luminous Crystalline Castle Runestone Wall", "发光月凝晶符文符石墙");
        this.addBlock(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE_WALL, "Luminous Moon Castle Runestone Wall", "发光皎月符文符石墙");
        this.addBlock(TABlocks.TRANSPARENT_RUNE_STONE_WALL, "Transparent Runestone Wall", "透明符石墙");
        this.addBlock(TABlocks.UMBRA_CASTLE_RUNE_STONE_WALL, "Umbra Castle Runestone Wall", "本影符文符石墙");
        this.addBlock(TABlocks.DARK_STONE_BRICK_WALL, "Dark Stone Brick Wall", "暗石砖块墙");
        this.addBlock(TABlocks.SMOOTH_DARK_STONE_BRICK_WALL, "Smooth Dark Stone Brick Wall", "平滑暗石砖块墙");
        this.addBlock(TABlocks.CHISELED_DARK_STONE_BRICK_WALL, "Chiseled Dark Stone Brick Wall", "雕纹暗石砖块墙");
        this.addBlock(TABlocks.MOON_TEMPLE_BRICK_WALL, "Moon Temple Brick Wall", "月宫砖块墙");
        this.addBlock(TABlocks.SMOOTH_MOON_TEMPLE_BRICK_WALL, "Smooth Moon Temple Brick Wall", "平滑月宫砖块墙");
        this.addBlock(TABlocks.CHISELED_MOON_TEMPLE_BRICK_WALL, "Chiseled Moon Temple Brick Wall", "雕纹月宫砖块墙");
        this.addBlock(TABlocks.UMBRA_STONE_WALL, "Umbra Stone Wall", "本影石墙");
        this.addBlock(TABlocks.UMBRA_STONE_CRACKED_WALL, "Cracked Umbra Stone Wall", "裂纹本影石墙");
        this.addBlock(TABlocks.UMBRA_STONE_ROOF_WALL, "Umbra Stone Roof Tiles Wall", "本影石瓦墙");
        this.addBlock(TABlocks.AURORIAN_PERIDOTITE_WALL, "Aurorian Peridotite Wall", "橄榄岩墙");
        this.addBlock(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_WALL, "Smooth Aurorian Peridotite Wall", "平滑橄榄岩墙");

        //MISC
        this.addBlock(TABlocks.ALCHEMY_TABLE, "Alchemy Table", "炼药桌");
        this.addBlock(TABlocks.RELIC_TABLE, "Relic Table", "圣物台");
        this.addBlock(TABlocks.ASTROLOGY_TABLE, "Astrology Table", "占星仪");
        this.addBlock(TABlocks.MYSTERIUM_WOOL_BED,"Mysterium Wool Bed","秘境羊毛床");
        this.addBlock(TABlocks.SILENT_CAMPFIRE,"Slient Campfire","静谧营火");

        //MOD ITEM
        this.addItem(TAItems.DARK_STONE_KEY, "Dark Stone Key", "暗石钥匙");
        this.addItem(TAItems.MOON_TEMPLE_KEY, "Moon Temple Key", "月宫钥匙");
        this.addItem(TAItems.RUNE_STONE_KEY, "Rune stone Key", "符石钥匙");
        this.addItem(TAItems.RUNE_STONE_LOOT_KEY, "Rune Stone Loot Key", "符石宝藏钥匙");
        this.addItem(TAItems.MOON_TEMPLE_CELL_KEY, "Moon Temple Cell Key", "月宫内室钥匙");
        this.addItem(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT, "Moon Temple Cell Key Fragment", "月宫内室钥匙碎片");
        this.addItem(TAItems.ABSORPTION_ORB, "Absorption Orb", "修复宝珠");
        this.addItem(TAItems.AURORIAN_BACON, "Aurorian Bacon", "极光培根");
        this.addItem(TAItems.AURORIAN_COAL, "Aurorian Coal", "极光煤炭");
        this.addItem(TAItems.AURORIAN_COAL_NUGGET, "Aurorian Coal Nugget", "极光煤粒");
        this.addItem(TAItems.AURORIAN_PORK, "Aurorian Pork", "生极光猪排");
        this.addItem(TAItems.AURORIAN_BEEF, "Aurorian Beef", "生极光牛肉");
        this.addItem(TAItems.AURORIAN_MUTTON, "Aurorian Mutton", "生极光羊肉");
        this.addItem(TAItems.AURORIAN_RABBIT, "Aurorian Rabbit", "生极光兔肉");
        this.addItem(TAItems.BREAD_BEAST_SPAWN_EGG, "Bread Beast Spawn Egg", "面包果兽刷怪蛋");
        this.addItem(TAItems.ICEFIELD_DEER_SPAWN_EGG, "Icefield Deer Spawn Egg", "冰原鹿刷怪蛋");
        this.addItem(TAItems.BLUE_TAIL_WOLF_SPAWN_EGG, "Blue Tail Wolf Spawn Egg", "青尾狼刷怪蛋");
        this.addItem(TAItems.MOON_FISH_SPAWN_EGG, "Moon Fish Spawn Egg", "皎月鱼刷怪蛋");
        this.addItem(TAItems.AURORIAN_WINGED_FISH_SPAWN_EGG, "Aurorian Winged Fish Spawn Egg", "极光翅鱼刷怪蛋");
        this.addItem(TAItems.AURORIAN_PIG_SPAWN_EGG, "Aurorian Pig Spawn Egg", "极光猪刷怪蛋");
        this.addItem(TAItems.AURORIAN_COW_SPAWN_EGG, "Aurorian Cow Spawn Egg", "极光牛刷怪蛋");
        this.addItem(TAItems.AURORIAN_VILLAGER_SPAWN_EGG, "Aurorian Villager Spawn Egg", "极光村民刷怪蛋");
        this.addItem(TAItems.AURORIAN_RABBIT_SPAWN_EGG, "Aurorian Rabbit Spawn Egg", "极光兔刷怪蛋");
        this.addItem(TAItems.AURORIAN_SHEEP_SPAWN_EGG, "Aurorian Sheep Spawn Egg", "极光羊刷怪蛋");
        this.addItem(TAItems.AURORIAN_PIXIE_SPAWN_EGG, "Aurorian Pixie Spawn Egg", "极光精灵刷怪蛋");
        this.addItem(TAItems.AURORIAN_SLIME_SPAWN_EGG, "Aurorian Slime Spawn Egg", "极光史莱姆刷怪蛋");
        this.addItem(TAItems.AURORIAN_SLIME_BOOTS, "Aurorian Slime Boots", "极光粘液靴子");
        this.addItem(TAItems.AURORIAN_SLIMEBALL, "Aurorian Slime Ball", "极光粘液球");
        this.addItem(TAItems.AURORIAN_STEEL, "Aurorian Steel", "极光钢锭");
        this.addItem(TAItems.AURORIAN_STEEL_NUGGET, "Aurorian Steel Nugget", "极光钢粒");
        this.addItem(TAItems.AURORIAN_STEEL_AXE, "Aurorian Steel Axe", "极光钢斧");
        this.addItem(TAItems.AURORIAN_STEEL_HOE, "Aurorian Steel Hoe", "极光钢锄");
        this.addItem(TAItems.AURORIAN_STEEL_PICKAXE, "Aurorian Steel Pickaxe", "极光钢镐");
        this.addItem(TAItems.AURORIAN_STEEL_SHOVEL, "Aurorian Steel Shovel", "极光钢铲");
        this.addItem(TAItems.AURORIAN_STEEL_SWORD, "Aurorian Steel Sword", "极光钢剑");
        this.addItem(TAItems.AURORIAN_STEEL_HELMET, "Aurorian Steel Helmet", "极光钢头盔");
        this.addItem(TAItems.AURORIAN_STEEL_CHESTPLATE, "Aurorian Steel Chestplate", "极光钢胸甲");
        this.addItem(TAItems.AURORIAN_STEEL_LEGGINGS, "Aurorian Steel Leggings", "极光钢护腿");
        this.addItem(TAItems.AURORIAN_STEEL_BOOTS, "Aurorian Steel Boots", "极光钢靴子");
        this.addItem(TAItems.AURORIAN_STONE_SICKLE, "Aurorian Stone Sickle", "极光石镰");
        this.addItem(TAItems.AURORIAN_STONE_AXE, "Aurorian Stone Axe", "极光石斧");
        this.addItem(TAItems.AURORIAN_STONE_HOE, "Aurorian Stone Hoe", "极光石锄");
        this.addItem(TAItems.AURORIAN_STONE_PICKAXE, "Aurorian Stone Pickaxe", "极光石镐");
        this.addItem(TAItems.AURORIAN_STONE_SHOVEL, "Aurorian Stone Shovel", "极光石铲");
        this.addItem(TAItems.AURORIAN_STONE_SWORD, "Aurorian Stone Sword", "极光石剑");
        this.addItem(TAItems.AURORIANITE_INGOT, "Aurorianite Ingot", "极光锭");
        this.addItem(TAItems.AURORIANITE_SCRAP, "Aurorianite Scrap", "极光碎片");
        this.addItem(TAItems.AURORIANITE_SWORD, "Aurorianite Sword", "极光剑");
        this.addItem(TAItems.AURORIANITE_AXE, "Aurorianite Axe", "极光斧");
        this.addItem(TAItems.AURORIANITE_PICKAXE, "Aurorianite Pickaxe", "极光镐");
        this.addItem(TAItems.BEPSI, "Bepsi", "旦事可乐");
        this.addItem(TAItems.RAW_CERULEAN, "Raw Cerulean", "粗晶蓝矿");
        this.addItem(TAItems.CERULEAN_ARROW, "Cerulean Arrow", "晶蓝箭");
        this.addItem(TAItems.CERULEAN_INGOT, "Cerulean Ingot", "晶蓝锭");
        this.addItem(TAItems.CERULEAN_NUGGET, "Cerulean Nugget", "晶蓝粒");
        this.addItem(TAItems.CERULEAN_HELMET, "Cerulean Helmet", "晶蓝头盔");
        this.addItem(TAItems.CERULEAN_CHESTPLATE, "Cerulean Chestplate", "晶蓝胸甲");
        this.addItem(TAItems.CERULEAN_LEGGINGS, "Cerulean Leggings", "晶蓝护腿");
        this.addItem(TAItems.CERULEAN_BOOTS, "Cerulean Boots", "晶蓝靴子");
        this.addItem(TAItems.CERULEAN_SHIELD, "Cerulean Shield", "晶蓝盾牌");
        this.addItem(TAItems.COOKED_AURORIAN_PORK, "Cooked Aurorian Pork", "熟极光猪排");
        this.addItem(TAItems.COOKED_AURORIAN_BEEF, "Cooked Aurorian Beef", "熟极光牛肉");
        this.addItem(TAItems.COOKED_AURORIAN_MUTTON, "Cooked Aurorian Mutton", "熟极光羊肉");
        this.addItem(TAItems.COOKED_AURORIAN_RABBIT, "Cooked Aurorian Rabbit", "熟极光兔肉");
        this.addItem(TAItems.MOON_SHURIKEN, "Moon Shuriken", "皎月手里剑");
        this.addItem(TAItems.AURORIAN_SLATE_BRICK, "Aurorian Slate Brick", "极光石板砖");
        this.addItem(TAItems.UNSTABLE_CRYSTAL, "Unstable Crystal", "失稳晶体");
        this.addItem(TAItems.CRYSTAL, "Crystal", "水晶");
        this.addItem(TAItems.CRYSTAL_ARROW, "Crystal Arrow", "水晶箭");
        this.addItem(TAItems.CRYSTALLINE_INGOT, "Crystalline Ingot", "月凝晶锭");
        this.addItem(TAItems.CRYSTALLINE_SCRAP, "Crystalline Scrap", "月凝晶碎片");
        this.addItem(TAItems.CRYSTALLINE_PICKAXE, "Crystalline Pickaxe", "月凝晶镐");
        this.addItem(TAItems.CRYSTALLINE_SHIELD, "Crystalline Shield", "月凝晶盾牌");
        this.addItem(TAItems.CRYSTALLINE_SWORD, "Crystalline Sword", "月凝晶剑");
        this.addItem(TAItems.CRYSTALLINE_SPRITE_SPAWN_EGG, "Crystalline Sprite Spawn Egg", "月凝晶魂刷怪蛋");
        this.addItem(TAItems.DARK_AMULET, "Dark Amulet", "暗黑护符");
        this.addItem(TAItems.DUNGEON_KEEPER_AMULET, "Dungeon Keeper Amulet", "地牢守卫护身符");
        this.addItem(TAItems.DISTURBED_HOLLOW_SPAWN_EGG, "Disturbed Hollow Spawn Egg", "空谷之扰刷怪蛋");
        this.addItem(TAItems.CAVE_DWELLER_SPAWN_EGG, "Cave Dweller Spawn Egg", "晶洞居者刷怪蛋");
        this.addItem(TAItems.ROCK_HAMMER_SPAWN_EGG, "Rock Hammer Spawn Egg", "岩锤兽刷怪蛋");
        this.addItem(TAItems.TONG_SCORPION_SPAWN_EGG, "Tong Scorpion Spawn Egg", "钳蝎刷怪蛋");
        this.addItem(TAItems.SNOW_TUNDRA_GIANT_CRAB_SPAWN_EGG, "Snow Tundra Giant Crab Spawn Egg", "雪苔巨蟹刷怪蛋");
        this.addItem(TAItems.FLOWER_LEECH_SPAWN_EGG, "Flower Leech Spawn Egg", "花蛭刷怪蛋");
        this.addItem(TAItems.FORGOTTEN_MAGIC_BOOK_SPAWN_EGG, "Forgotten Magic Book Spawn Egg", "遗落魔书刷怪蛋");
        this.addItem(TAItems.HYPHA_WALKING_MUSHROOM_SPAWN_EGG, "Hypha Walking Mushroom Spawn Egg", "挂丝走路菇刷怪蛋");
        this.addItem(TAItems.DUNGEON_LOCATOR, "Dungeon Locator", "地牢定位器");
        this.addItem(TAItems.KEEPERS_BOW, "Keeper's Bow", "守卫之弓");
        this.addItem(TAItems.KNIGHT_HELMET, "Knight Helmet", "骑士头盔");
        this.addItem(TAItems.KNIGHT_CHESTPLATE, "Knight Chestplate", "骑士胸甲");
        this.addItem(TAItems.KNIGHT_LEGGINGS, "Knight Leggings", "骑士护腿");
        this.addItem(TAItems.KNIGHT_BOOTS, "Knight Boots", "骑士靴子");
        this.addItem(TAItems.LAVENDER, "Lavender", "薰衣草");
        this.addItem(TAItems.LAVENDER_BREAD, "Lavender Bread", "薰衣草面包");
        this.addItem(TAItems.LAVENDER_TEA, "Lavender Tea", "薰衣草茶");
        this.addItem(TAItems.LAVENDER_SEEDS, "Lavender Seeds", "薰衣草种子");
        this.addItem(TAItems.LIVING_DIVINING_ROD, "Living Divining Rod", "生命卜窥权杖");
        this.addItem(TAItems.MOON_WATER_BUCKET, "Moon Water Bucket", "皎月水桶");
        this.addItem(TAItems.MOON_FISH_BUCKET, "Moon Fish Bucket", "皎月鱼桶");
        this.addItem(TAItems.AURORIAN_WINGED_FISH_BUCKET, "Aurorian Winged Fish Bucket", "极光翅鱼桶");
        this.addItem(TAItems.LOCK_PICKS, "Lock Picks", "开锁器");
        this.addItem(TAItems.DEVELOPER_GIFT, "Developer Gift", "开发者礼物");
        this.addItem(TAItems.RAW_MOONSTONE, "Raw Moonstone", "粗皎月石");
        this.addItem(TAItems.MOONLIGHT_KNIGHT_SPAWN_EGG, "Moonlight Knight Spawn Egg", "月光骑士刷怪蛋");
        this.addItem(TAItems.MOON_ACOLYTE_SPAWN_EGG, "Moon Acolyte Spawn Egg", "皎月侍从刷怪蛋");
        this.addItem(TAItems.MOON_QUEEN_SPAWN_EGG, "Moon Queen Spawn Egg", "皎月女王刷怪蛋");
        this.addItem(TAItems.MOONSTONE_INGOT, "Moonstone Ingot", "皎月石锭");
        this.addItem(TAItems.MOONSTONE_NUGGET, "Moonstone Nugget", "皎月石粒");
        this.addItem(TAItems.MOON_SHIELD, "Moon Shield", "皎月之盾");
        this.addItem(TAItems.MOONSTONE_SHIELD, "Moonstone Shield", "皎月石盾");
        this.addItem(TAItems.MOONSTONE_AXE, "Moonstone Axe", "皎月石斧");
        this.addItem(TAItems.MOONSTONE_HOE, "Moonstone Hoe", "皎月石锄");
        this.addItem(TAItems.MOONSTONE_PICKAXE, "Moonstone Pickaxe", "皎月石镐");
        this.addItem(TAItems.MOONSTONE_SHOVEL, "Moonstone Shovel", "皎月石铲");
        this.addItem(TAItems.MOONSTONE_SWORD, "Moonstone Sword", "皎月石剑");
        this.addItem(TAItems.MOONSTONE_SICKLE, "Moonstone Sickle", "皎月石镰");
        this.addItem(TAItems.PETUNIA_TEA, "Petunia Tea", "牵牛花茶");
        this.addItem(TAItems.PLANT_FIBER, "Plant Fiber", "植物纤维");
        this.addItem(TAItems.QUEENS_CHIPPER, "Queen's Chipper", "女王镐");
        this.addItem(TAItems.RUNESTONE_KEEPER_SPAWN_EGG, "Runestone Keeper Spawn Egg", "符石守卫刷怪蛋");
        this.addItem(TAItems.LAVENDER_SEEDY_TEA, "Lavender Seedy Tea", "薰衣草籽茶");
        this.addItem(TAItems.SILENT_WOOD_SICKLE, "Silent Wood Sickle", "谧木镰");
        this.addItem(TAItems.SILENT_WOOD_AXE, "Silent Wood Axe", "谧木斧");
        this.addItem(TAItems.SILENT_WOOD_HOE, "Silent Wood Hoe", "谧木锄");
        this.addItem(TAItems.SILENT_WOOD_PICKAXE, "Silent Wood Pickaxe", "谧木镐");
        this.addItem(TAItems.SILENT_WOOD_SHOVEL, "Silent Wood Shovel", "谧木铲");
        this.addItem(TAItems.SILENT_WOOD_SWORD, "Silent Wood Sword", "谧木剑");
        this.addItem(TAItems.SILENT_WOOD_BOW, "Silent Wood Bow", "谧木弓");
        this.addItem(TAItems.SILENT_WOOD_STICK, "Silent Wood Stick", "谧木棍");
        this.addItem(TAItems.BLUEBERRY, "Blueberry", "蓝莓");
        this.addItem(TAItems.SILK_BERRY, "Silk Berry", "桑葚");
        this.addItem(TAItems.SILK_BERRY_JAM, "Silk Berry Jam", "桑葚酱");
        this.addItem(TAItems.SILK_BERRY_JAM_SANDWICH, "Silk Berry Jam Sandwich", "桑葚酱三明治");
        this.addItem(TAItems.SILK_BERRY_TEA, "Slik Berry Tea", "桑葚茶");
        this.addItem(TAItems.SILK_SHROOM_STEW, "Silk Shroom Stew", "桑葚煲");
        this.addItem(TAItems.SLEEPING_BLACK_TEA, "Sleeping Black Tea", "昏睡红茶");
        this.addItem(TAItems.SOULLESS_FLESH, "Soulless Flesh", "魂灭之肉");
        this.addItem(TAItems.AURORIAN_WINGED_FISH, "Aurorian Winged Fish", "生极光翅鱼");
        this.addItem(TAItems.MOON_FISH, "Moon Fish", "生皎月鱼");
        this.addItem(TAItems.COOKED_MOON_FISH, "Cooked Moon Fish", "熟皎月鱼");
        this.addItem(TAItems.COOKED_AURORIAN_WINGED_FISH, "Cooked Aurorian Winged Fish", "熟极光翅鱼");
        this.addItem(TAItems.WHITE_CHOCOLATE, "White Chocolate", "白巧克力");
        this.addItem(TAItems.CANDY, "Candy", "糖果");
        this.addItem(TAItems.CANDY_CANE, "Candy Cane", "拐杖糖");
        this.addItem(TAItems.GINGERBREAD_MAN, "Gingerbread Man", "姜饼人");
        this.addItem(TAItems.AURORIAN_SPECIALTY_DRINK,
                "Aurorian Specialty Drink", "极光特饮");
        this.addItem(TAItems.MOONLIT_BLUEBERRY_SPECIALTY_DRINK,
                "Moonlit Blueberry Specialty Drink", "皎月蓝莓特饮");
        this.addItem(TAItems.STRANGE_MEAT, "Strange Meat", "奇怪的肉");
        this.addItem(TAItems.LAVENDER_SALAD, "Lavender Salad", "薰衣草沙拉");
        this.addItem(TAItems.FAKE_ALGAL_PIT_FISH, "Fake Algal Pit Fish", "伪藻渊鱼");
        this.addItem(TAItems.SASHIMI, "Sashimi", "生鱼片");
        this.addItem(TAItems.SILENT_WOOD_FRUIT, "Silent Wood Fruit", "谧木果");
        this.addItem(TAItems.GOLDEN_SILENT_WOOD_FRUIT, "Golden Silent Wood Fruit", "金谧木果");
        this.addItem(TAItems.KEBAB_WITH_MUSHROOM, "Kebab with Mushroom", "菇肉串");
        this.addItem(TAItems.AURORIAN_WINTER_ROOT, "Aurorian Winter Root", "极光冬根");
        this.addItem(TAItems.ROASTED_AURORIAN_WINTER_ROOT, "Roasted Aurorian Winter Root", "烤极光冬根");
        this.addItem(TAItems.DARK_STONE_SHRIMP, "Dark Stone Shrimp", "暗石虾");
        this.addItem(TAItems.SPECTRAL_SILK, "Spectral Silk", "幽冥丝绸");
        this.addItem(TAItems.SPECTRAL_HELMET, "Spectral Helmet", "幽冥头盔");
        this.addItem(TAItems.SPECTRAL_CHESTPLATE, "Spectral Chestplate", "幽冥胸甲");
        this.addItem(TAItems.SPECTRAL_LEGGINGS, "Spectral Leggings", "幽冥护腿");
        this.addItem(TAItems.SPECTRAL_BOOTS, "Spectral Boots", "幽冥靴子");
        this.addItem(TAItems.MYSTERIUM_WOOL_HELMET, "Mysterium Wool Helmet", "秘境羊毛帽子");
        this.addItem(TAItems.MYSTERIUM_WOOL_CHESTPLATE, "Mysterium Wool Chestplate", "秘境羊毛外套");
        this.addItem(TAItems.MYSTERIUM_WOOL_LEGGINGS, "Mysterium Wool Leggings", "秘境羊毛裤子");
        this.addItem(TAItems.MYSTERIUM_WOOL_BOOTS, "Mysterium Wool Boots", "秘境羊毛靴子");
        this.addItem(TAItems.CRYSTAL_RUNE_HELMET, "Crystal Rune Helmet", "水晶符石头盔");
        this.addItem(TAItems.CRYSTAL_RUNE_CHESTPLATE, "Crystal Rune Chestplate", "水晶符石胸甲");
        this.addItem(TAItems.CRYSTAL_RUNE_LEGGINGS, "Crystal Rune Leggings", "水晶符石护腿");
        this.addItem(TAItems.CRYSTAL_RUNE_BOOTS, "Crystal Rune Boots", "水晶符石靴子");
        this.addItem(TAItems.SPIDER_MOTHER_SPAWN_EGG, "Spider Mother Spawn Egg", "蛛母刷怪蛋");
        this.addItem(TAItems.SPIDERLING_SPAWN_EGG, "Spiderling Spawn Egg", "幼蛛刷怪蛋");
        this.addItem(TAItems.SPIDERLING_CRYSTAL_SHELL_SPAWN_EGG, "Crystal Shell Spiderling Spawn Egg", "水晶壳幼蛛刷怪蛋");
        this.addItem(TAItems.SPIDERLING_WALL_CLIMBER_SPAWN_EGG, "Wall Climbing Spiderling Spawn Egg", "爬墙幼蛛刷怪蛋");
        this.addItem(TAItems.GIANT_CRYSTAL_SPIDER_SPAWN_EGG, "Giant Crystal Spiderling Spawn Egg", "巨型水晶幼蛛刷怪蛋");
        this.addItem(TAItems.RUNE_SPIDER_SPAWN_EGG, "Rune Spider Spawn Egg", "符石蜘蛛刷怪蛋");
        this.addItem(TAItems.SPIRIT_SPAWN_EGG, "Spirit Spawn Egg", "魂灵刷怪蛋");
        this.addItem(TAItems.SPIKED_CHESTPLATE, "Spiked Chestplate", "尖钉胸甲");
        this.addItem(TAItems.STICKY_SPIKER, "Sticky Spiker", "粘性尖钉");
        this.addItem(TAItems.TEA_CUP, "Tea Cup", "茶杯");
        this.addItem(TAItems.TROPHY_KEEPER, "Keeper Trophy", "符石守卫奖励");
        this.addItem(TAItems.TROPHY_MOON_QUEEN, "Moon Queen Trophy", "皎月女王奖励");
        this.addItem(TAItems.TROPHY_SPIDER_MOTHER, "Spider Mother Trophy", "蛛母奖励");
        this.addItem(TAItems.RUNE_KNOWLEDGE_FRAGMENT, "Rune Knowledge Fragments", "符文知识残卷");
        this.addItem(TAItems.RUNESTONE_ICE, "Ice Runestone", "寒冰符文石");
        this.addItem(TAItems.RUNESTONE_LIFE, "Life Runestone", "生命符文石");
        this.addItem(TAItems.RUNESTONE_LIGHT, "Light Runestone", "光明符文石");
        this.addItem(TAItems.RUNESTONE_WATER, "Water Runestone", "水流符文石");
        this.addItem(TAItems.RUNESTONE_BLAZE, "Blaze Runestone", "烈焰符文石");
        this.addItem(TAItems.RUNESTONE_THUNDER, "Thunder Runestone", "雷电符文石");
        this.addItem(TAItems.RUNESTONE_DARKNESS, "Darkness Runestone", "黑暗符文石");
        this.addItem(TAItems.UMBRA_INGOT, "Umbra Ingot", "本影锭");
        this.addItem(TAItems.UMBRA_SCRAP, "Umbra Scrap", "本影碎片");
        this.addItem(TAItems.UMBRA_SHIELD, "Umbra Shield", "本影盾牌");
        this.addItem(TAItems.UMBRA_SWORD, "Umbra Sword", "本影剑");
        this.addItem(TAItems.UMBRA_PICKAXE, "Umbra Pickaxe", "本影镐");
        this.addItem(TAItems.UNDEAD_KNIGHT_SPAWN_EGG, "Undead Knight Spawn Egg", "不死骑士刷怪蛋");
        this.addItem(TAItems.AURORIAN_CHAIN, "Aurorian Chain", "极光锁链");
        this.addItem(TAItems.AURORIAN_BERRY, "Aurorian Berry", "极光浆果");
        this.addItem(TAItems.AURORIAN_CRYSTAL, "Aurorian Crystal", "极光水晶");
        this.addItem(TAItems.BROKEN_OX_HORN, "Broken Ox Horn", "碎裂的牛角");
        this.addItem(TAItems.LUCKY_RABBIT_EAR, "Lucky Rabbit Ear", "幸运兔耳");
        this.addItem(TAItems.EQUINOX_MUSHROOM, "Equinox Mushroom", "彼岸蘑菇");
        this.addItem(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT, "Dream Dyeing Crystal Fragment", "融梦水晶碎片");
        this.addItem(TAItems.RED_BOOK, "Red Book", "红皮书");
        this.addItem(TAItems.RED_BOOK_RING, "Red Book Ring", "红皮书戒指");
        this.addItem(TAItems.CAT_BELL, "Cat Bell", "猫猫的铃铛");
        this.addItem(TAItems.TSLAT_SWORD, "Tslat's Sword", "Tslat的剑");
        this.addItem(TAItems.WORLD_SCROLL_FRAGMENT, "World Scroll Fragment", "世界残卷");
        this.addItem(TAItems.WORLD_SCROLL, "World Scroll", "世界卷轴");
        this.addItem(TAItems.WEEPING_WILLOW_SAP, "Weeping Willow Sap", "垂柳树汁");
        this.addItem(TAItems.WEBBING, "Webbing", "蛛网");

        //MOD ENTITY
        this.addEntityType(TAEntityTypes.CRYSTALLINE_BEAM, "Crystalline Beam", "月凝晶射线");
        this.addEntityType(TAEntityTypes.CERULEAN_ARROW, "Cerulean Arrow", "晶蓝箭");
        this.addEntityType(TAEntityTypes.CRYSTAL_ARROW, "Crystal Arrow", "水晶箭");
        this.addEntityType(TAEntityTypes.STICKY_SPIKER, "Sticky Spiker", "粘性尖刺");
        this.addEntityType(TAEntityTypes.WEBBING, "Webbing", "蛛母之网");
        this.addEntityType(TAEntityTypes.EYE_OF_DISTURBED, "Eye Of Disturbed", "空谷之眼");
        this.addEntityType(TAEntityTypes.BREAD_BEAST, "Bread Beast", "面包果兽");
        this.addEntityType(TAEntityTypes.ICEFIELD_DEER, "Icefield Deer", "冰原鹿");
        this.addEntityType(TAEntityTypes.BLUE_TAIL_WOLF, "Blue Tail Wolf", "青尾狼");
        this.addEntityType(TAEntityTypes.MOON_FISH, "Moon Fish", "皎月鱼");
        this.addEntityType(TAEntityTypes.AURORIAN_WINGED_FISH, "Aurorian Winged Fish", "极光翅鱼");
        this.addEntityType(TAEntityTypes.AURORIAN_VILLAGER, "Aurorian Villager", "极光村民");
        this.addEntityType(TAEntityTypes.AURORIAN_RABBIT, "Aurorian Rabbit", "极光兔");
        this.addEntityType(TAEntityTypes.AURORIAN_SHEEP, "Aurorian Sheep", "极光羊");
        this.addEntityType(TAEntityTypes.AURORIAN_PIG, "Aurorian Pig", "极光猪");
        this.addEntityType(TAEntityTypes.AURORIAN_COW, "Aurorian Cow", "极光牛");
        this.addEntityType(TAEntityTypes.AURORIAN_PIXIE, "Aurorian Pixie", "极光精灵");
        this.addEntityType(TAEntityTypes.AURORIAN_SLIME, "Aurorian Slime", "极光史莱姆");
        this.addEntityType(TAEntityTypes.DISTURBED_HOLLOW, "Disturbed Hollow", "空谷之扰");
        this.addEntityType(TAEntityTypes.UNDEAD_KNIGHT, "Undead Knight", "不死骑士");
        this.addEntityType(TAEntityTypes.SPIRIT, "Spirit", "魂灵");
        this.addEntityType(TAEntityTypes.MOON_ACOLYTE, "Moon Acolyte", "皎月侍从");
        this.addEntityType(TAEntityTypes.SPIDERLING, "Spiderling", "幼蛛");
        this.addEntityType(TAEntityTypes.SPIDERLING_CRYSTAL_SHELL,"Crystal Shell Spiderling", "水晶壳幼蛛");
        this.addEntityType(TAEntityTypes.SPIDERLING_WALL_CLIMBER,"Wall Climbing Spiderling", "爬墙幼蛛");
        this.addEntityType(TAEntityTypes.GIANT_CRYSTAL_SPIDER,"Giant Crystal Spiderling", "巨型水晶幼蛛");
        this.addEntityType(TAEntityTypes.RUNE_SPIDER, "Rune Spider", "符石蜘蛛");
        this.addEntityType(TAEntityTypes.CRYSTALLINE_SPRITE, "Crystalline Sprite", "月凝晶魂");
        this.addEntityType(TAEntityTypes.CAVE_DWELLER, "Cave Dweller", "晶洞居者");
        this.addEntityType(TAEntityTypes.ROCK_HAMMER, "Rock Hammer", "岩锤兽");
        this.addEntityType(TAEntityTypes.TONG_SCORPION, "Tong Scorpion", "钳蝎");
        this.addEntityType(TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB, "Snow Tundra Giant Crab", "雪苔巨蟹");
        this.addEntityType(TAEntityTypes.FLOWER_LEECH, "Flower Leech", "花蛭");
        this.addEntityType(TAEntityTypes.FORGOTTEN_MAGIC_BOOK, "Forgotten Magic Book", "遗落魔书");
        this.addEntityType(TAEntityTypes.HYPHA_WALKING_MUSHROOM, "Hypha Walking Mushroom", "挂丝走路菇");
        this.addEntityType(TAEntityTypes.MOONLIGHT_KNIGHT, "Moonlight Knight", "月光骑士");
        this.addEntityType(TAEntityTypes.RUNESTONE_KEEPER, "Runestone Keeper", "符石守卫");
        this.addEntityType(TAEntityTypes.SPIDER_MOTHER, "Spider Mother", "蛛母");
        this.addEntityType(TAEntityTypes.MOON_QUEEN, "Moon Queen", "皎月女王");
        //VILLAGER WITH PROFESSION
        this.add("entity.theaurorian.aurorian_villager.none","Villager","村民");
        this.add("entity.theaurorian.aurorian_villager.nitwit","Nitwit","傻子");
        this.add("entity.theaurorian.aurorian_villager.farmer","Farmer","农民");
        this.add("entity.theaurorian.aurorian_villager.armorer","Armorer","盔甲匠");
        this.add("entity.theaurorian.aurorian_villager.butcher","Butcher","屠夫");
        this.add("entity.theaurorian.aurorian_villager.cartographer","Cartographer","制图师");
        this.add("entity.theaurorian.aurorian_villager.cleric","Cleric","牧师");
        this.add("entity.theaurorian.aurorian_villager.fisherman","Fisherman","渔夫");
        this.add("entity.theaurorian.aurorian_villager.fletcher","Fletcher","制箭师");
        this.add("entity.theaurorian.aurorian_villager.leatherworker","Leather Worker","皮匠");
        this.add("entity.theaurorian.aurorian_villager.librarian","Librarian","图书管理员");
        this.add("entity.theaurorian.aurorian_villager.mason","Mason","石匠");
        this.add("entity.theaurorian.aurorian_villager.shepherd","Shepherd","牧羊人");
        this.add("entity.theaurorian.aurorian_villager.toolsmith","Tool Smith","工具匠");
        this.add("entity.theaurorian.aurorian_villager.weaponsmith","Weapon Smith","武器匠");
        this.add("entity.theaurorian.aurorian_villager.theaurorian.astrologer", "Astrologer", "占星师");
        this.add("entity.minecraft.villager.theaurorian.astrologer", "Astrologer", "占星师");
        //MOD FLUID
        this.addFluidType(TAFluidTypes.MOLTEN_AURORIAN_STEEL,"Molten Aurorian Steel","熔融极光钢");
        this.addFluidType(TAFluidTypes.MOLTEN_CERULEAN,"Molten Cerulean","熔融晶蓝");
        this.addFluidType(TAFluidTypes.MOLTEN_MOONSTONE,"Molten Moonstone","熔融极光石");
        this.addFluidType(TAFluidTypes.MOON_WATER,"Moon Water","皎月水");
        //MOB EFFECT
        this.addEffect(TAMobEffects.WARM, "Warm", "温暖");
        this.addEffect(TAMobEffects.STUN, "Stun", "眩晕");
        this.addEffect(TAMobEffects.TOUGH, "Tough", "坚韧");
        this.addEffect(TAMobEffects.BROKEN,"Broken", "破盾");
        this.addEffect(TAMobEffects.NATURE, "Nature", "自然");
        this.addEffect(TAMobEffects.TREMOR, "Tremor", "震颤");
        this.addEffect(TAMobEffects.CRESCENT, "Crescent", "新月");
        this.addEffect(TAMobEffects.HOLINESS, "Holiness", "圣洁");
        this.addEffect(TAMobEffects.PRESSURE, "Pressure", "威压");
        this.addEffect(TAMobEffects.PARALYSIS, "Paralysis", "瘫痪");
        this.addEffect(TAMobEffects.CONFUSION, "Confusion", "混乱");
        this.addEffect(TAMobEffects.FROSTBITE, "Frostbite", "冻伤");
        this.addEffect(TAMobEffects.LACERATION, "Laceration", "裂伤");
        this.addEffect(TAMobEffects.CORRUPTION, "Corruption", "堕落");
        this.addEffect(TAMobEffects.INCANTATION, "Incantation", "咒文");
        this.addEffect(TAMobEffects.OVERHEATING, "Overheating", "过热");
        this.addEffect(TAMobEffects.MOON_BEFALL, "Befall of Moon", "月临");
        this.addEffect(TAMobEffects.BLESS_OF_MOON, "Bless of Moon", "护月");
        this.addEffect(TAMobEffects.VULNERABILITY, "Vulnerability", "脆化");
        this.addEffect(TAMobEffects.EIDOLON_POISON, "Eidolon Poison", "幻光毒");
        this.addEffect(TAMobEffects.CRYSTALLIZATION, "Crystallization", "晶化");
        this.addEffect(TAMobEffects.FORBIDDEN_CURSE, "Forbidden Curse", "禁咒");
        this.addEffect(TAMobEffects.MOON_OF_VENGEANCE, "Moon of Vengeance", "仇月");

        //MOD ENCHANTMENT
        this.addEnchantment(TAEnchantments.IMPALE, "Impale", "刺穿",
                "The arrow will have a penetrating effect on the target","箭矢会对目标造成穿透效果");
        this.addEnchantment(TAEnchantments.AURORA, "Aurora", "极光",
                "Enchanting the helmet will highlight ores within an eight-block radius","附魔在头盔上，会将八格范围内的矿物进行高亮显示");
        this.addEnchantment(TAEnchantments.SAVAGE, "Savage", "野蛮人",
                "Deals extra damage to neutral animals and has a chance to drop additional loot","对中立动物额外造成伤害，并一定概率额外掉落战利品");
        this.addEnchantment(TAEnchantments.OVERLOAD, "Overload", "过载",
                "Consume additional durability to cause extra damage","消耗额外的耐久，造成额外的伤害");
        this.addEnchantment(TAEnchantments.GUARDIAN, "Guardian", "守护者",
                "Grants the wearer one chance to resurrect, but the enchanted equipment will disappear","给予一次复活的机会，但附魔的装备会消失");
        this.addEnchantment(TAEnchantments.MOONLIGHT, "Moonlight", "月光",
                "Highlight creatures within a 20-block radius","使附近20格范围内的生物高亮显示");
        this.addEnchantment(TAEnchantments.SOUL_SLASH, "Soul Slash", "灵魂斩击",
                "Each attack has a chance to ignore enemy armor","每次攻击，概率无视敌方护甲");
        this.addEnchantment(TAEnchantments.CLEAR_MIND, "Clear Mind", "清晰头脑",
                "Players below level 30 will gain additional experience.","玩家等级低于30级，会获得额外经验");
        this.addEnchantment(TAEnchantments.WIND_RUNNER, "Wind Runner", "风行者",
                "Decreases hunger value by half when moving, swimming, jumping, or sprinting.","移动，游泳，跳跃，饥饿，疾跑时跳跃，饥饿等级减半");
        this.addEnchantment(TAEnchantments.MOLTEN_CORE, "Molten Core", "熔火之心",
                "Deals additional damage When taking fire or lava damage.","受到火焰伤害或熔岩伤害时，会造成额外伤害");
        this.addEnchantment(TAEnchantments.NIGHT_WALKER, "Night Walker", "夜行者",
                "Deals additional damage when the light level around the wearer is low.","周围光源等级过低时，会造成额外伤害");
        this.addEnchantment(TAEnchantments.REFLECT_AURA, "Reflect Aura", "反射光环",
                "Have a low chance to return damage when attacked","受到伤害时，会有较低概率将伤害进行返还");
        this.addEnchantment(TAEnchantments.AMNESIA_CURSE, "Amnesia Curse", "遗忘诅咒",
                "Inflicted with blindness and weakness effects to nearby creatures When the armor is destroyed.","佩戴者的盔甲被摧毁时，周围的生物会被施加失明和虚弱效果");
        this.addEnchantment(TAEnchantments.FREEZE_ASPECT, "Freeze Aspect", "冰霜附加",
                "Inflicts a continuous armor-piercing freezing effect when cause damage.","造成伤害时，会造成持续的无视盔甲的冰冻效果");
        this.addEnchantment(TAEnchantments.SPRING_OF_LIFE, "Spring of Life", "生命之泉",
                "Regenerate health for a period of time continuously when HP is low.","生命值过低时，会一定时间内持续恢复生命值");
        this.addEnchantment(TAEnchantments.EXPERIENCE_ORE, "Experience Ore", "经验矿石",
                "Have a chance to gain additional experience when mining ores.","挖掘矿物时，有一定概率获得额外经验");
        this.addEnchantment(TAEnchantments.LEGENDARY_HERO, "Legendary Hero", "传说的勇者",
                "Increased the more attack damage the more villagers and players nearby.","附近的村民及玩家越多，提升的攻击越高");
        this.addEnchantment(TAEnchantments.VIRTUALIZATION, "Virtualization", "虚化",
                "Have a low chance to be immune to the damage when be attacked.","受到伤害时，较低概率免疫该次伤害");
        this.addEnchantment(TAEnchantments.SOURCE_OF_TERRA, "Source of Terra", "大地之源",
                "Teleport drop items to the binding chest.","将挖掘的掉落物，传送至所绑定的箱子");
        this.addEnchantment(TAEnchantments.COBWEB_CROSSING, "Cobweb Crossing", "蛛网穿行",
                "Crossing cobweb freely.","佩戴者不再受到蛛网的减速效果");
        this.addEnchantment(TAEnchantments.ROUNDABOUT_THROW, "Roundabout Throw", "回旋投掷",
                "Allow the axe to be throw as boomerang.","允许将该附魔的斧头像回旋镖一样进行投掷");
        this.addEnchantment(TAEnchantments.LIGHTNING_DAMAGE, "Lightning Damage", "雷电伤害",
                "Does extra damage depending on how much armor the target is wearing.","根据对方穿戴的护甲数量造成额外伤害");
        this.addEnchantment(TAEnchantments.SUNDER_ARMOR_SLASH, "Sunder Armor Slash", "破甲斩击",
                "Does extra damage to target wearing armor.","对穿戴盔甲的对象造成额外伤害");
        this.addEnchantment(TAEnchantments.LIGHTNING_RESISTANCE, "Lightning Resistance", "雷电抵御",
                "Negates damage that would have been done by lightning enchantment.","降低“雷电”魔咒造成的额外伤害，同时完全抵消被雷击中的伤害");

        //MOD PAINTING
        this.addPainting(TAPaintingVariants.AURORIAN_STEEL, "Aurorian Steel", "极光钢");
        this.addPainting(TAPaintingVariants.PROGRESSION, "Progression", "前进");
        this.addPainting(TAPaintingVariants.DUNGEON, "Dungeon", "地牢");
        this.addPainting(TAPaintingVariants.KEEPER, "Keeper", "守卫");
        this.addPainting(TAPaintingVariants.KNIGHT, "Knight", "骑士");
        this.addPainting(TAPaintingVariants.MOON, "Moon", "皎月");
        this.addPainting(TAPaintingVariants.PORTAL, "Portal", "传送门");
        this.addPainting(TAPaintingVariants.SLIME, "Slime", "史莱姆");

        //MOD GUI TOOLTIPS
        this.add("tooltips.block.theaurorian.moonlight_forge.gui.redstone", "Currently disabled by redstone power.", "目前已被红石信号禁用！");
        this.add("tooltips.block.theaurorian.moonlight_forge.gui.no_moonlight", "No Moonlight Detected!", "没有检测到月光！");
        this.add("tooltips.block.theaurorian.moonlight_forge.gui.has_moonlight","Blessed By Moonlight!", "月光祝福中！");

        //MOD MESSAGES
        this.add("messages." + TAItems.AURORIAN_STEEL_SWORD.get().getDescriptionId() + ".holiness", "All evil has been dispelled!", "所有的罪恶已被驱散！");
        this.add("messages." + TAItems.UMBRA_PICKAXE.get().getDescriptionId() + ".selected", "Selected Block: %s", "已选择方块：%s");
        this.add("messages." + TAItems.UMBRA_PICKAXE.get().getDescriptionId() + ".fail", "The remaining durability is less than 60 so select fail!", "选择失败，因为剩余耐久已不足60！");

        //MOD TOOLTIPS
        this.addTooltips(TAItems.AURORIAN_STEEL_HELMET, "The truth blessing of the aurora! The enchantment in this armors will upgrade slowly as time goes on until the max level!", "真正的极光赐福！盔甲上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STEEL_CHESTPLATE, "The truth blessing of the aurora! The enchantment in this armors will upgrade slowly as time goes on until the max level!", "真正的极光赐福！盔甲上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STEEL_LEGGINGS, "The truth blessing of the aurora! The enchantment in this armors will upgrade slowly as time goes on until the max level!", "真正的极光赐福！盔甲上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STEEL_BOOTS, "The truth blessing of the aurora! The enchantment in this armors will upgrade slowly as time goes on until the max level!", "真正的极光赐福！盔甲上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.CERULEAN_HELMET, "A blue armor but strength is not bad, which can temporarily protecting you for explore this world.", "强度不错的蓝色盔甲，能暂时保护你探索这个世界.");
        this.addTooltips(TAItems.CERULEAN_CHESTPLATE, "A blue armor but strength is not bad, which can temporarily protecting you for explore this world.", "强度不错的蓝色盔甲，能暂时保护你探索这个世界.");
        this.addTooltips(TAItems.CERULEAN_LEGGINGS, "A blue armor but strength is not bad, which can temporarily protecting you for explore this world.", "强度不错的蓝色盔甲，能暂时保护你探索这个世界.");
        this.addTooltips(TAItems.CERULEAN_BOOTS, "A blue armor but strength is not bad, which can temporarily protecting you for explore this world.", "强度不错的蓝色套盔甲，能暂时保护你探索这个世界.");
        this.addTooltips(TAItems.KNIGHT_HELMET, "A cursed knight item. You will get Strength I effect if you wear full sets!", "被诅咒的骑士之物.穿上整套将附有力量I增幅！");
        this.addTooltips(TAItems.KNIGHT_CHESTPLATE, "A cursed knight item. You will get Strength I effect if you wear full sets!", "被诅咒的骑士之物.穿上整套将附有力量I增幅！");
        this.addTooltips(TAItems.KNIGHT_LEGGINGS, "A cursed knight item. You will get Strength I effect if you wear full sets!", "被诅咒的骑士之物.穿上整套将附有力量I增幅！");
        this.addTooltips(TAItems.KNIGHT_BOOTS, "A cursed knight item. You will get Strength I effect if you wear full sets!", "被诅咒的骑士之物.穿上整套将附有力量I增幅!");
        this.addTooltips(TAItems.SPECTRAL_HELMET, "Attach the residual thought of soul. There is a 6% chance to clear all harmful effects when attack!", "附着魂灵的残念.每件盔甲都有 6% 的几率在攻击时净化身上的负面效果！");
        this.addTooltips(TAItems.SPECTRAL_CHESTPLATE, "Attach the residual thought of soul. There is a 6% chance to clear all harmful effects when attack!", "附着魂灵的残念.每件盔甲都有 6% 的几率在攻击时净化身上的负面效果！");
        this.addTooltips(TAItems.SPECTRAL_LEGGINGS, "Attach the residual thought of soul. There is a 6% chance to clear all harmful effects when attack!", "附着魂灵的残念.每件盔甲都有 6% 的几率在攻击时净化身上的负面效果！");
        this.addTooltips(TAItems.SPECTRAL_BOOTS, "Attach the residual thought of soul. There is a 6% chance to clear all harmful effects when attack!", "附着魂灵的残念.每件盔甲都有 6% 的几率在攻击时净化身上的负面效果！");
        this.addTooltips(TAItems.SPIKED_CHESTPLATE, "Counter attack those enemies who attack you. Has Thorns III but only when crouched and give Slowness effect to the wearer when crouching.", "反击那些攻击你的敌人.潜行时将会获得荆棘III附魔与缓慢I效果！");
        this.addTooltips(TAItems.AURORIAN_SLIME_BOOTS, "Jump while sneaking to do an extra high jump! Also blocks fall damage.", "用处不错，但黏糊糊的感觉并不好.潜行时起跳会跳得更高，并且还能消除摔落伤害！");
        this.addTooltips(TAItems.AURORIAN_STEEL_SWORD, "The truth blessing of the aurora! The enchantment in this sword will upgrade slowly as time goes on until the max level!", "真正的极光赐福！剑上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STONE_SWORD, "Just a little stronger than the wood sword.", "仅仅只比木剑强力一点点.");
        this.addTooltips(TAItems.SILENT_WOOD_SWORD, "Even for self-defense, it is not recommended.", "即使是防身也并不推荐.");
        this.addTooltips(TAItems.UMBRA_SWORD, "The cursed sword! Costs 20 durability amount to get Absorption, Weakness, Slowness II, Resistance III for 6s. The cool down time is 45s.", "诅咒之剑！右击消耗 20 点耐久度，立即获得伤害吸收、虚弱、缓慢 II、抗性提升 III 各 6 秒，冷却时间 45 秒.");
        this.addTooltips(TAItems.AURORIANITE_SWORD, "Has the ability to levitate everyone nearby! Costs 5 durability amount to enable oneself and all surrounding creatures to achieve a Levitation effect that lasts for 3s when right click. The cool down time is 30s.",
                "极光让周围的人与我一同失重！右键时消耗五点耐久，让自己与周围的所有生物获得持续三秒的飘浮效果，冷却时间为三十秒.");
        this.addTooltips(TAItems.CRYSTALLINE_SWORD, "Charge up to fire a Crystalline Beam! Charge up and costs 1 durability amount to fire a Crystalline Beam that unaffected by gravity!", "充能！发射水晶光束！长按右键蓄力，消耗一点耐久，蓄力满后松开右键发射出一道不受重力影响，速度略逊于弓箭的光柱.");
        this.addTooltips(TAItems.MOONSTONE_SWORD, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.AURORIAN_STEEL_SHOVEL, "The truth blessing of the aurora! The enchantment in this shovel will upgrade slowly as time goes on until the max level!", "真正的极光赐福！铲上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STONE_SHOVEL, "Just a little stronger than the wood shovel.", "仅仅只比木铲强力一点点.");
        this.addTooltips(TAItems.SILENT_WOOD_SHOVEL, "Even for digging, it is not recommended.", "即使是挖掘也并不推荐.");
        this.addTooltips(TAItems.MOONSTONE_SHOVEL, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.AURORIAN_STEEL_AXE, "The truth blessing of the aurora! The enchantment in this axe will upgrade slowly as time goes on until the max level!", "真正的极光赐福！斧上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIANITE_AXE, "The Aurorian let me have endless strength! Can chop down whole trees at the expense of extra 2 damage!", "极光让我力大无穷！以消耗额外的耐久为代价，砍倒整棵谧木树！！");
        this.addTooltips(TAItems.AURORIAN_STONE_AXE, "Just a little stronger than the wood axe.", "仅仅只比木斧强力一点点.");
        this.addTooltips(TAItems.SILENT_WOOD_AXE, "Magical Silentwood has a chance to heal itself when chopping Silentwood Logs!", "神奇的谧木工具在砍伐谧木时，有机会恢复自己的耐久！");
        this.addTooltips(TAItems.MOONSTONE_AXE, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.AURORIAN_STEEL_PICKAXE, "The truth blessing of the aurora! The enchantment in this pickaxe will upgrade slowly as time goes on until the max level!", "真正的极光赐福！镐上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIANITE_PICKAXE, "The Aurorian give me endless energy! Mines faster a short while after mining an ore!", "极光让我拥有无穷精力！持续的挖掘会缓慢提高挖掘速度！");
        this.addTooltips(TAItems.UMBRA_PICKAXE, "The cursed pickaxe! Select a block to gain bonus mining speed towards that type! Selecting a block costs a lot of durability.", "诅咒之镐！选择一种方块，对它进行挖掘的速度会有所提高，但是选择这一过程会减少大量耐久度！");
        this.addTooltips(TAItems.CRYSTALLINE_PICKAXE, "Praise the moonlight! Ores drop ingots plus a few nuggets!", "赞美月光！挖掘矿石会有额外掉落！");
        this.addTooltips(TAItems.AURORIAN_STONE_PICKAXE, "It has a chance to heal itself when mining Aurorian Stone!", "开采极光石时，有机会恢复自身耐久.");
        this.addTooltips(TAItems.SILENT_WOOD_PICKAXE, "Magical Silentwood can mine higher leveled blocks like diamond when at lower durability!", "神奇的谧木工具在耐久度低时会提高挖掘等级！");
        this.addTooltips(TAItems.MOONSTONE_PICKAXE, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.QUEENS_CHIPPER, "The soul of the poor queen is sealed in this pickaxe. So it has extremely strong destructive power. Right click can destroy dungeons that could not have been destroyed to form blocks!",
                "可怜的女王，灵魂被封印在这把镐子里，让其拥有极强的破坏力.右击可以破坏原本无法破坏的地牢组成方块!");
        this.addTooltips(TAItems.AURORIAN_STEEL_HOE, "The truth blessing of the aurora! The enchantment in this hoe will upgrade slowly as time goes on until the max level!", "真正的极光赐福！锄上的附魔会随时间推移而缓慢升级，直到达到对应附魔的等级上限！");
        this.addTooltips(TAItems.AURORIAN_STONE_HOE, "Just a little stronger than wood hoe.", "仅仅只比木锄强力一点点.");
        this.addTooltips(TAItems.SILENT_WOOD_HOE, "Even for hoeing, it is not recommended.", "即使是锄地也并不推荐.");
        this.addTooltips(TAItems.MOONSTONE_HOE, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.SILENT_WOOD_SICKLE, "Can collect items such as leaves, grass, cobwebs, etc. like shears，and can also be used to obtain plant fibers. Got from breaking the Aurorian tall grass or light Aurorian tall grass.",
                "可以像剪刀一样采集树叶、草、蜘蛛网等物品，同样也可以被用于获取植物纤维，破坏极光高草丛和发光极光高草丛即可.");
        this.addTooltips(TAItems.AURORIAN_STONE_SICKLE, "It's more durable than wood sickle.", "比木镰更加耐用.");
        this.addTooltips(TAItems.MOONSTONE_SICKLE, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.UMBRA_SHIELD, "The cursed shield! Lights whoever is in front of you on fire when blocking! Blocking for too long will cause it to overheat.", "诅咒之盾！在抵挡攻击状态下时会点燃正前方的实体。抵挡时间过长会过载！.");
        this.addTooltips(TAItems.CERULEAN_SHIELD, "A blue shield but strength is not bad, which can temporarily protecting you for explore this world.", "强度不错的蓝色盾牌，能暂时保护你探索这个世界..");
        this.addTooltips(TAItems.CRYSTALLINE_SHIELD, "Praise the moonlight! Will repair items in your mainhand when blocking attacks!", "赞美月光！完成抵挡后，修复你的主手物品！");
        this.addTooltips(TAItems.MOONSTONE_SHIELD, "The queen's scorn! There is a chance that it will not costs durability in bright moon night, but there is a chance to costs 2 durability amount in aurora night.", "女王的蔑视！在皎月夜晚有几率不会消耗耐久，但在极光夜有几率消耗 2 点耐久.");
        this.addTooltips(TAItems.MOON_SHIELD, "Eulogize the queen! Hold block to Charge at foes and knock them up in the air!", "赞颂女王！右击蓄能，然后将敌人狠狠地击飞到空中！");
        this.addTooltips(TAItems.SILENT_WOOD_BOW, "Ordinary long-range weapons.", "普通的远程武器。");
        this.addTooltips(TAItems.KEEPERS_BOW, "Fires 3 arrows when fully drawn!", "完全充能后自动一次性射出三支箭矢！");
        this.addTooltips(TAItems.LAVENDER_TEA, "Leisure time. Drink and got Resistance I in 15s.", "悠闲时光：饮用后获得持续15秒的抗性提升I效果。");
        this.addTooltips(TAItems.SILK_BERRY_TEA, "Leisure time. Drink and got Regeneration I in 5s.", "悠闲时光：饮用后获得持续5秒的生命恢复I效果。");
        this.addTooltips(TAItems.LAVENDER_SEEDY_TEA, "Leisure time. Drink and got Speed I in 5s.", "悠闲时光：饮用后获得持续10秒的速度I效果。");
        this.addTooltips(TAItems.PETUNIA_TEA, "Leisure time. Drink and got strength I in 15s.", "悠闲时光：饮用后获得持续15秒的力量I效果。");
        this.addTooltips(TAItems.BEPSI, "How'd this get here??", "哪来的山寨货？？");
        this.addTooltips(TAItems.WEEPING_WILLOW_SAP, "A nice antidote, which will cleanse poison but gives you slowness. The duration depends on the remaining duration before the poison effect is removed.",
                "不错的解毒剂！它能去除你的中毒效果，但却会使你获得缓慢效果，持续时间取决于中毒效果被移除之前的剩余持续时间。");
        this.addTooltips(TAItems.LAVENDER_SALAD, "The charm of nature!", "自然的魅力！");
        this.addTooltips(TAItems.FAKE_ALGAL_PIT_FISH, "I heard that this rare fish can only be eaten raw and will accelerate decay when exposed to heat.", "听说这个稀有的鱼只能生吃，遇热会加速腐败。");
        this.addTooltips(TAItems.SASHIMI, "Sweet food eaten raw is even more delicious.", "甜食生吃，更加可口。");
        this.addTooltips(TAItems.SILENT_WOOD_FRUIT, "Can it be considered the apple of this world?", "它算得上是这个世界的苹果吗？");
        this.addTooltips(TAItems.GOLDEN_SILENT_WOOD_FRUIT, "After eating it, my body feels more relaxed.", "感觉吃下去之后，身体变得更加轻松了。");
        this.addTooltips(TAItems.KEBAB_WITH_MUSHROOM, "What is more satisfying than skewering large meat skewers?", "有什么比来串大肉串更加饱肚子的呢？");
        this.addTooltips(TAItems.AURORIAN_WINTER_ROOT, "Rare ingredients in Frosty Land.", "寒霜之地少见的食材。");
        this.addTooltips(TAItems.ROASTED_AURORIAN_WINTER_ROOT, "I feel my body warm.", "感觉身子暖暖的。");
        this.addTooltips(TAItems.DARK_STONE_SHRIMP, "The shell seems incredibly hard.", "外壳似乎坚硬无比。");
        this.addTooltips(TAItems.AURORIAN_COAL, "A fuel that better than coal.", "比普通煤炭更持久一点。");
        this.addTooltips(TAItems.AURORIANITE_INGOT, "Aurorian Blessing!", "极光加护！");
        this.addTooltips(TAItems.AURORIAN_STEEL, "The truth blessing of the aurora! The enchantment will upgrade slowly as time goes on until the max level!", "真正的极光赐福！随着时间的推移，此物品上的附魔会升级！");
        this.addTooltips(TAItems.CERULEAN_INGOT, "A nice armor material.", "不错的装备材料。");
        this.addTooltips(TAItems.CRYSTALLINE_INGOT, "It's the cream of the bright moon! Praise the queen!", "皎月的精华！赞美女王！");
        this.addTooltips(TAItems.MOONSTONE_INGOT, "The queen's scorn! Moon Queen? Exile!", "女王的蔑视！极光女王？放逐！");
        this.addTooltips(TAItems.UMBRA_INGOT, "Has a faint dreadful aura...", "有一种不详的气息...");
        this.addTooltips(TAItems.AURORIANITE_SCRAP, "Got from explore the Runestone Dungeon and smelt or scrap the Aurorianite tools.", "通过探索符石地牢，烧制或者粉碎极光工具获取.");
        this.addTooltips(TAItems.CRYSTALLINE_SCRAP, "Got from explore the Moon Temple and smelt or scrap the Crystalline tools.", "通过探索月宫，烧制或者粉碎月凝晶工具获取.");
        this.addTooltips(TAItems.UMBRA_SCRAP, "Got from explore the Darkstone Dungeon and smelt or scrap the Umbra tools.", "通过探索暗石地牢，烧制或者粉碎本影工具获取.");
        this.addTooltips(TAItems.SPECTRAL_SILK, "Obtained from Spirits. It seems that it can craft a good armors", "魂灵掉落物，似乎能合成不错的套装.");
        this.addTooltips(TAItems.DARK_AMULET, "Has a faint dreadful aura. Used for crafting the Moon Temple Key.", "恐惧之息细微弥漫，用于制作月宫钥匙.");
        this.addTooltips(TAItems.DUNGEON_KEEPER_AMULET, "Pulsates with corrupted power. Used for crafting the Darkstone Key.", "腐败之力在蠢蠢欲动，用于制作暗石钥匙.");
        this.addTooltips(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT, "Used to craft the Moon Temple Cell Key.", "用于合成进入月宫内室的月宫内室钥匙.");
        this.addTooltips(TAItems.RUNE_STONE_KEY, "Used to unlock Runestone Gates. Single use!", "用于开启符石地牢.");
        this.addTooltips(TAItems.DARK_STONE_KEY, "Used to unlock Darkstone Gates. Single use!", "用于开启暗石地牢入口大门.");
        this.addTooltips(TAItems.RUNE_STONE_LOOT_KEY, "Used to unlock Runestone Dungeon's loot room.", "用于开启符文地牢宝藏室.");
        this.addTooltips(TAItems.MOON_TEMPLE_KEY, "Used to unlock Moon Temple Gates. Single use!", "用于开启月宫入口大门.");
        this.addTooltips(TAItems.MOON_TEMPLE_CELL_KEY, "Used to unlock Moon Temple's inner room. Single use!", "用于开启暗石地牢入口大门.");
        this.addTooltips(TAItems.ABSORPTION_ORB, "Heals mainhand item when held in offhand!", "在副手时会修复你主手持握的物品！");
        this.addTooltips(TAItems.SILENT_WOOD_STICK, "Common materials that can be seen everywhere.", "随处可见的普通材料.");
        this.addTooltips(TAItems.STICKY_SPIKER, "Does poison damage when it hits an entity.", "使命中的实体中毒.");
        this.addTooltips(TAItems.LAVENDER, "Got from breaking lavender plants with a sickle or farmed. Making food must be delicious if use this fragrant plants.", "芳香的植物，制作食物一定美味！可通过镰刀收割获得.");
        this.addTooltips(TAItems.PLANT_FIBER, "Got from breaking aurorian tall grass with a sickle.", "似乎有丝线般的强度，用镰破坏极光高草丛获得.");
        this.addTooltips(TAItems.TROPHY_KEEPER, "A spirit exiled for 500 years are being laid to rest.", "被放逐500年的亡灵终于得到安息.");
        this.addTooltips(TAItems.TROPHY_SPIDER_MOTHER, "The cursed evil has brought its free.", "被诅咒的邪祟引来了它的解脱.");
        this.addTooltips(TAItems.TROPHY_MOON_QUEEN, "The Aurorian world finally welcomed its freedom, but it seems like it's just the beginning of another nightmare.", "极光世界终于迎来了它的自由，但似乎只是另一个噩梦的开始.");
        this.addTooltips(TAItems.CRYSTAL, "Got from breaking crystal cluster ore. Place on scrapper to speed it up! Has a chance to break every craft.", "挖掘晶簇矿石获得，用于合成和粉碎器耗材.");
        this.addTooltips(TAItems.SLEEPING_BLACK_TEA, "Hey uuz! Your pseudo-girl day is over. Give me the sleeping black tea!", "嘿，幼幼紫，你动不动就变男娘的日子结束了，把昏睡红茶给我！");
        this.addTooltips(TAItems.WHITE_CHOCOLATE, "This the Matara's relic? Oh, is it developer item. She died on the chair, so this chocolate grows legs and walks away. Now it's your chocolate, what a coincidence! " +
                        "This chocolate has the magic of Matara due to regarded by Matara as a treasure all year round. If you eat it recklessly, something terrible may happen!",
                "摩多罗的遗物（不是）开发者物品，她在椅子上死掉了所以这个巧克力长腿跑了。现在它是你的巧克力了，真巧。因为常年被摩多罗视作珍宝，所以这块巧克力上有摩多罗的魔力。妄然吃掉的话可能会发生不得了的事情……！");
        this.addTooltips(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT, "青春猪头尘不会梦到幼幼紫学姐.", "青春猪头尘不会梦到幼幼紫学姐.");
        this.addTooltips(TAItems.RED_BOOK, "A book full of various causes of death in the world. But in reality, it is a specious chief signal officer", "写满世间各种死因的书，却是个徒有其表的通讯装置？！");
        this.addTooltips(TAItems.RED_BOOK_RING, "I didn't expect that person to make an specious item!", "没想到那个人会制作一个徒有其表的东西啊！");
        this.addTooltips(TAItems.CAT_BELL, "It won't change you into a cat girl, but it can make you fast like a cat!", "虽然不会把你变猫娘，但是可以让你像猫一样快！");
        this.addTooltips(TAItems.TSLAT_SWORD, "Nobody know how to make muti-dims mod better than Tslat XD.", "没有人比tslat更懂该怎么做muti-dims mod XD");

        this.add("theaurorian.item.locator1","Set to searching RuneStone Dungeon","设置为寻找符石地牢");
        this.add("theaurorian.item.locator2","Set to searching DarkStone Dungeon","设置为寻找黑石地牢");
        this.add("theaurorian.item.locator3","Set to searching MoonTemple","设置为寻找月宫");
        this.add("equipment_set.theaurorian.knight_armor_set", "Knight Armor Set", "骑士套装");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        this.addTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(TheAurorian.MOD_ID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }

        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }

        return CompletableFuture.allOf();
    }

    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }

    private void addBlock(Supplier<? extends Block> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addItem(Supplier<? extends Item> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addEntityType(Supplier<? extends EntityType<?>> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addFluidType(Supplier<? extends FluidType> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addEffect(Supplier<? extends MobEffect> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addBiome(ResourceKey<Biome> biome, String en, String cn) {
        this.add("biome." + biome.location().toLanguageKey(), en, cn);
    }

    private void addTooltips(Supplier<Item> key, String en, String cn) {
        this.add("tooltips." + key.get().getDescriptionId(), en, cn);
    }

    private void addEnchantment(ResourceKey<Enchantment> key, String en, String cn, String description_en, String description_cn) {
        String languageKey = key.location().toLanguageKey();
        this.add("enchantment." + languageKey, en, cn);
        this.add("enchantment." + languageKey + ".desc", description_en, description_cn);
    }

    private void addPainting(ResourceKey<PaintingVariant> key, String en, String cn) {
        this.add("painting." + key.location().toLanguageKey(), en, cn);
    }

    private void add(String key, String en, String cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, cn);
        }
    }

}