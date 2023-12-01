package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.*;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class TALanguageProviderZHCN extends LanguageProvider {

    public TALanguageProviderZHCN(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + AurorianMod.MOD_ID, "极光幽境");
        this.add("theaurorian.container.scrapper", "粉碎器");
        this.add("theaurorian.container.moonlight_forge", "月光融锻台");

        //MOD BLOCK
        this.add(TABlocks.AURORIAN_DIRT.get(), "极光泥土");
        this.add(TABlocks.AURORIAN_STONE.get(), "极光石");
        this.add(TABlocks.AURORIAN_STONE_BRICKS.get(), "极光石砖");
        this.add(TABlocks.AURORIAN_COBBLESTONE.get(), "极光圆石");
        this.add(TABlocks.AURORIAN_COAL_ORE.get(), "极光煤矿石");
        this.add(TABlocks.AURORIAN_GRASS_BLOCK.get(), "极光草方块");
        this.add(TABlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(), "发光极光草方块");
        this.add(TABlocks.AURORIAN_FARM_TILE.get(), "极光农砖");
        this.add(TABlocks.AURORIAN_GLASS.get(), "极光玻璃");
        this.add(TABlocks.MOON_GLASS.get(), "皎月玻璃");
        this.add(TABlocks.AURORIAN_GLASS_PANE.get(), "极光玻璃板");
        this.add(TABlocks.MOON_GLASS_PANE.get(), "皎月玻璃板");
        this.add(TABlocks.AURORIAN_GRASS.get(), "极光草");
        this.add(TABlocks.AURORIAN_GRASS_LIGHT.get(), "发光极光草");
        this.add(TABlocks.AURORIAN_FURNACE.get(), "极光熔炉");
        this.add(TABlocks.AURORIAN_FURNACE_CHIMNEY.get(), "极光熔炉孔道");
        this.add(TABlocks.AURORIAN_PORTAL.get(), "极光传送门");
        this.add(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), "极光传送门框架");
        this.add(TABlocks.URN.get(), "瓮");
        this.add(TABlocks.AURORIAN_FLOWER_1.get(), "极光花");
        this.add(TABlocks.AURORIAN_FLOWER_2.get(), "极光花");
        this.add(TABlocks.AURORIAN_FLOWER_3.get(), "极光花");
        this.add(TABlocks.EQUINOX_FLOWER.get(), "彼岸花");
        this.add(TABlocks.AURORIAN_PERIDOTITE.get(), "极光橄榄岩");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), "平滑极光橄榄岩");
        this.add(TABlocks.MOONSTONE_ORE.get(), "皎月石矿石");
        this.add(TABlocks.CERULEAN_ORE.get(), "晶蓝矿石");
        this.add(TABlocks.GEODE_ORE.get(), "晶簇矿石");
        this.add(TABlocks.RUNE_STONE.get(), "符石");
        this.add(TABlocks.SMOOTH_RUNE_STONE.get(), "平滑符石");
        this.add(TABlocks.CHISELED_RUNE_STONE.get(), "雕文符石");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE.get(), "极光符文符石");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get(), "极光钢符文符石");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE.get(), "晶蓝符文符石");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get(), "月凝晶符文符石");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE.get(), "皎月符文符石");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE.get(), "透明符石");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE.get(), "本影符文符石");
        this.add(TABlocks.RUNE_STONE_PILLAR.get(), "符石柱");
        this.add(TABlocks.MOON_TEMPLE_BRICKS.get(), "月宫砖块");
        this.add(TABlocks.DARK_STONE_BRICKS.get(), "暗石砖块");
        this.add(TABlocks.DARK_STONE_FANCY.get(), "装饰性暗石");
        this.add(TABlocks.DARK_STONE_LAYERS.get(), "暗石地层");
        this.add(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), "平滑月宫砖块");
        this.add(TABlocks.RUNE_STONE_LAMP.get(), "符石灯");
        this.add(TABlocks.DARK_STONE_LAMP.get(), "暗石灯");
        this.add(TABlocks.MOON_TEMPLE_LAMP.get(), "月宫灯");
        this.add(TABlocks.CERULEAN_BLOCK.get(), "晶蓝块");
        this.add(TABlocks.MOONSTONE_BLOCK.get(), "皎月石块");
        this.add(TABlocks.AURORIAN_COAL_BLOCK.get(), "极光煤块");
        this.add(TABlocks.AURORIAN_STEEL_BLOCK.get(), "极光钢块");
        this.add(TABlocks.MYSTICAL_BARRIER.get(), "神秘屏障");
        this.add(TABlocks.RUNE_STONE_BARS.get(), "符石栏杆");
        this.add(TABlocks.MOON_TEMPLE_BARS.get(), "月宫栏杆");
        this.add(TABlocks.RUNE_STONE_GATE.get(), "符石门");
        this.add(TABlocks.DARK_STONE_GATE.get(), "暗石门");
        this.add(TABlocks.MOON_TEMPLE_GATE.get(), "月宫门");
        this.add(TABlocks.RUNE_STONE_LOOT_GATE.get(), "符石宝藏室门");
        this.add(TABlocks.MOON_TEMPLE_CELL_GATE.get(), "月宫内室门");
        this.add(TABlocks.RUNE_STONE_GATE_KEYHOLE.get(), "符石门锁孔");
        this.add(TABlocks.DARK_STONE_GATE_KEYHOLE.get(), "暗石门锁孔");
        this.add(TABlocks.MOON_TEMPLE_GATE_KEYHOLE.get(), "月宫门锁孔");
        this.add(TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get(), "符石宝藏室门锁孔");
        this.add(TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get(), "月宫内室门锁孔");
        this.add(TABlocks.INDIGO_MUSHROOM_BLOCK.get(), "深蓝蘑菇块");
        this.add(TABlocks.INDIGO_MUSHROOM_STEM.get(), "深蓝蘑菇茎");
        this.add(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get(), "深蓝蘑菇水晶");
        this.add(TABlocks.MOONLIGHT_FORGE.get(), "月光融锻台");
        this.add(TABlocks.MOON_GEM.get(), "皎月宝石");
        this.add(TABlocks.MOON_SAND.get(), "皎月沙");
        this.add(TABlocks.MOON_SAND_RIVER.get(), "皎月河沙");
        this.add(TABlocks.MOON_SAND_STONE_1.get(), "皎月砂岩");
        this.add(TABlocks.MOON_SAND_STONE_2.get(), "皎月砂岩");
        this.add(TABlocks.MOON_SAND_STONE_3.get(), "皎月砂岩");
        this.add(TABlocks.BRIGHT_MOON_SAND.get(), "皓月沙");
        this.add(TABlocks.MOON_TORCH.get(), "皎月火把");
        this.add(TABlocks.SCRAPPER.get(), "粉碎器");
        this.add(TABlocks.UMBRA_STONE.get(), "本影石");
        this.add(TABlocks.UMBRA_STONE_CRACKED.get(), "裂纹本影石");
        this.add(TABlocks.UMBRA_STONE_ROOF_TILES.get(), "本影石瓦");
        this.add(TABlocks.SILENT_TREE_LEAVES.get(), "谧树树叶");
        this.add(TABlocks.SILENT_TREE_LOG.get(), "谧树原木");
        this.add(TABlocks.SILENT_TREE_PLANKS.get(), "谧树木板");
        this.add(TABlocks.SILENT_TREE_WOOD.get(), "谧树木头");
        this.add(TABlocks.SILENT_TREE_SAPLING.get(), "谧树树苗");
        this.add(TABlocks.SILENT_WOOD_TORCH.get(), "谧木火把");
        this.add(TABlocks.SILENT_WOOD_LADDER.get(), "谧木梯子");
        this.add(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get(), "谧木工作台");
        this.add(TABlocks.WEEPING_WILLOW_LEAVES.get(), "垂柳树叶");
        this.add(TABlocks.WEEPING_WILLOW_LOG.get(), "垂柳原木");
        this.add(TABlocks.WEEPING_WILLOW_PLANKS.get(), "垂柳木板");
        this.add(TABlocks.WEEPING_WILLOW_WOOD.get(), "垂柳木头");
        this.add(TABlocks.AURORIAN_STONE_STAIRS.get(), "极光石楼梯");
        this.add(TABlocks.AURORIAN_STONE_BRICK_STAIRS.get(), "极光石砖楼梯");
        this.add(TABlocks.AURORIAN_COBBLESTONE_STAIRS.get(), "极光圆石楼梯");
        this.add(TABlocks.SILENT_WOOD_STAIRS.get(), "谧木楼梯");
        this.add(TABlocks.RUNE_STONE_STAIRS.get(), "符石楼梯");
        this.add(TABlocks.SMOOTH_RUNE_STONE_STAIRS.get(), "平滑符石楼梯");
        this.add(TABlocks.CHISELED_RUNE_STONE_STAIRS.get(), "雕文符石楼梯");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_STAIRS.get(), "极光符文符石楼梯");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS.get(), "极光钢符文符石楼梯");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_STAIRS.get(), "晶蓝符文符石楼梯");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS.get(), "月凝晶符文符石楼梯");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_STAIRS.get(), "皎月符文符石楼梯");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_STAIRS.get(), "透明符石楼梯");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_STAIRS.get(), "本影符文符石楼梯");
        this.add(TABlocks.MOON_TEMPLE_STAIRS.get(), "月宫楼梯");
        this.add(TABlocks.DARK_STONE_STAIRS.get(), "符石楼梯");
        this.add(TABlocks.UMBRA_STONE_STAIRS.get(), "本影石楼梯");
        this.add(TABlocks.UMBRA_STONE_CRACKED_STAIRS.get(), "裂纹本影石楼梯");
        this.add(TABlocks.UMBRA_STONE_ROOF_STAIRS.get(), "本影石瓦楼梯");
        this.add(TABlocks.WEEPING_WILLOW_STAIRS.get(), "垂柳木楼梯");
        this.add(TABlocks.AURORIAN_PERIDOTITE_STAIRS.get(), "橄榄岩楼梯");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get(), "平滑橄榄岩楼梯");
        this.add(TABlocks.AURORIAN_STONE_SLAB.get(), "极光石台阶");
        this.add(TABlocks.AURORIAN_STONE_BRICK_SLAB.get(), "极光石砖台阶");
        this.add(TABlocks.AURORIAN_COBBLESTONE_SLAB.get(), "极光圆石台阶");
        this.add(TABlocks.SILENT_WOOD_SLAB.get(), "谧木台阶");
        this.add(TABlocks.RUNE_STONE_SLAB.get(), "符石台阶");
        this.add(TABlocks.SMOOTH_RUNE_STONE_SLAB.get(), "平滑符石台阶");
        this.add(TABlocks.CHISELED_RUNE_STONE_SLAB.get(), "雕文符石台阶");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_SLAB.get(), "极光符文符石台阶");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB.get(), "极光钢符文符石台阶");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_SLAB.get(), "晶蓝符文符石台阶");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_SLAB.get(), "月凝晶符文符石台阶");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_SLAB.get(), "皎月符文符石台阶");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_SLAB.get(), "透明符石台阶");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_SLAB.get(), "本影符文符石台阶");
        this.add(TABlocks.MOON_TEMPLE_SLAB.get(), "月宫台阶");
        this.add(TABlocks.DARK_STONE_SLAB.get(), "符石台阶");
        this.add(TABlocks.UMBRA_STONE_SLAB.get(), "本影石台阶");
        this.add(TABlocks.UMBRA_STONE_CRACKED_SLAB.get(), "裂纹本影石台阶");
        this.add(TABlocks.UMBRA_STONE_ROOF_SLAB.get(), "本影石瓦台阶");
        this.add(TABlocks.WEEPING_WILLOW_SLAB.get(), "垂柳木台阶");
        this.add(TABlocks.AURORIAN_PERIDOTITE_SLAB.get(), "橄榄岩台阶");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_SLAB.get(), "平滑橄榄岩台阶");
        this.add(TABlocks.AURORIAN_STONE_WALL.get(), "极光石墙");
        this.add(TABlocks.AURORIAN_STONE_BRICK_WALL.get(), "极光石砖墙");
        this.add(TABlocks.AURORIAN_COBBLESTONE_WALL.get(), "极光圆石墙");
        this.add(TABlocks.RUNE_STONE_WALL.get(), "符石墙");
        this.add(TABlocks.SMOOTH_RUNE_STONE_WALL.get(), "平滑符石墙");
        this.add(TABlocks.CHISELED_RUNE_STONE_WALL.get(), "雕文符石墙");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_WALL.get(), "极光符文符石墙");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL.get(), "极光钢符文符石墙");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_WALL.get(), "晶蓝符文符石墙");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_WALL.get(), "月凝晶符文符石墙");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_WALL.get(), "皎月符文符石墙");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_WALL.get(), "透明符石墙");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_WALL.get(), "本影符文符石墙");
        this.add(TABlocks.MOON_TEMPLE_WALL.get(), "月宫墙");
        this.add(TABlocks.DARK_STONE_WALL.get(), "符石墙");
        this.add(TABlocks.UMBRA_STONE_WALL.get(), "本影石墙");
        this.add(TABlocks.UMBRA_STONE_CRACKED_WALL.get(), "裂纹本影石墙");
        this.add(TABlocks.UMBRA_STONE_ROOF_WALL.get(), "本影石瓦墙");
        this.add(TABlocks.AURORIAN_PERIDOTITE_WALL.get(), "橄榄岩墙");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_WALL.get(), "平滑橄榄岩墙");

        //MOD ITEM
        this.add(TAItems.DARK_STONE_KEY.get(), "暗石钥匙");
        this.add(TAItems.MOON_TEMPLE_KEY.get(), "月宫钥匙");
        this.add(TAItems.RUNE_STONE_KEY.get(), "符石钥匙");
        this.add(TAItems.RUNE_STONE_LOOT_KEY.get(), "符石宝藏钥匙");
        this.add(TAItems.MOON_TEMPLE_CELL_KEY.get(), "月宫内室钥匙");
        this.add(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(), "月宫内室钥匙碎片");
        this.add(TAItems.ABSORPTION_ORB.get(), "修复宝珠");
        this.add(TAItems.AURORIAN_BACON.get(), "极光培根");
        this.add(TAItems.AURORIAN_COAL.get(), "极光煤炭");
        this.add(TAItems.AURORIAN_COAL_NUGGET.get(), "极光煤块");
        this.add(TAItems.AURORIAN_PORK.get(), "生极光猪排");
        this.add(TAItems.AURORIAN_PIG_SPAWN_EGG.get(), "极光猪刷怪蛋");
        this.add(TAItems.AURORIAN_RABBIT_SPAWN_EGG.get(), "极光兔刷怪蛋");
        this.add(TAItems.AURORIAN_SHEEP_SPAWN_EGG.get(), "极光羊刷怪蛋");
        this.add(TAItems.AURORIAN_PIXIE_SPAWN_EGG.get(), "极光精灵刷怪蛋");
        this.add(TAItems.AURORIAN_SLIME_SPAWN_EGG.get(), "极光史莱姆刷怪蛋");
        this.add(TAItems.AURORIAN_SLIME_BOOTS.get(), "极光粘液靴子");
        this.add(TAItems.AURORIAN_SLIMEBALL.get(), "极光粘液球");
        this.add(TAItems.AURORIAN_STEEL.get(), "极光钢锭");
        this.add(TAItems.AURORIAN_STEEL_NUGGET.get(), "极光钢块");
        this.add(TAItems.AURORIAN_STEEL_AXE.get(), "极光钢斧");
        this.add(TAItems.AURORIAN_STEEL_PICKAXE.get(), "极光钢镐");
        this.add(TAItems.AURORIAN_STEEL_SHOVEL.get(), "极光钢铲");
        this.add(TAItems.AURORIAN_STEEL_SWORD.get(), "极光钢剑");
        this.add(TAItems.AURORIAN_STEEL_HELMET.get(), "极光钢头盔");
        this.add(TAItems.AURORIAN_STEEL_CHESTPLATE.get(), "极光钢胸甲");
        this.add(TAItems.AURORIAN_STEEL_LEGGINGS.get(), "极光钢护腿");
        this.add(TAItems.AURORIAN_STEEL_BOOTS.get(), "极光钢靴子");
        this.add(TAItems.AURORIAN_STONE_SICKLE.get(), "极光石镰");
        this.add(TAItems.AURORIAN_STONE_AXE.get(), "极光石斧");
        this.add(TAItems.AURORIAN_STONE_PICKAXE.get(), "极光石镐");
        this.add(TAItems.AURORIAN_STONE_SHOVEL.get(), "极光石铲");
        this.add(TAItems.AURORIAN_STONE_SWORD.get(), "极光石剑");
        this.add(TAItems.AURORIANITE_INGOT.get(), "极光锭");
        this.add(TAItems.AURORIANITE_SCRAP.get(), "极光碎片");
        this.add(TAItems.AURORIANITE_SWORD.get(), "极光剑");
        this.add(TAItems.AURORIANITE_AXE.get(), "极光斧");
        this.add(TAItems.AURORIANITE_PICKAXE.get(), "极光镐");
        this.add(TAItems.BEPSI.get(), "旦事可乐");
        this.add(TAItems.CERULEAN_ARROW.get(), "晶蓝箭");
        this.add(TAItems.CERULEAN_INGOT.get(), "晶蓝锭");
        this.add(TAItems.CERULEAN_NUGGET.get(), "晶蓝块");
        this.add(TAItems.CERULEAN_HELMET.get(), "晶蓝头盔");
        this.add(TAItems.CERULEAN_CHESTPLATE.get(), "晶蓝胸甲");
        this.add(TAItems.CERULEAN_LEGGINGS.get(), "晶蓝护腿");
        this.add(TAItems.CERULEAN_BOOTS.get(), "晶蓝靴子");
        this.add(TAItems.CERULEAN_SHIELD.get(), "晶蓝盾牌");
        this.add(TAItems.COOKED_AURORIAN_PORK.get(), "熟极光猪排");
        this.add(TAItems.CRYSTAL.get(), "水晶");
        this.add(TAItems.CRYSTAL_ARROW.get(), "水晶箭");
        this.add(TAItems.CRYSTALLINE_INGOT.get(), "月凝晶锭");
        this.add(TAItems.CRYSTALLINE_SCRAP.get(), "月凝晶碎片");
        this.add(TAItems.CRYSTALLINE_PICKAXE.get(), "月凝晶镐");
        this.add(TAItems.CRYSTALLINE_SHIELD.get(), "月凝晶盾牌");
        this.add(TAItems.CRYSTALLINE_SWORD.get(), "月凝晶剑");
        this.add(TAItems.CRYSTALLINE_SPRITE.get(), "月凝晶魂");
        this.add(TAItems.CRYSTALLINE_SPRITE_SPAWN_EGG.get(), "月凝晶魂刷怪蛋");
        this.add(TAItems.DARK_AMULET.get(), "暗黑护符");
        this.add(TAItems.DUNGEON_KEEPER_AMULET.get(), "地牢守卫护身符");
        this.add(TAItems.TROPHY_KEEPER.get(), "符石地牢守卫奖励");
        this.add(TAItems.DISTURBED_HOLLOW_SPAWN_EGG.get(), "空谷之扰刷怪蛋");
        this.add(TAItems.DUNGEON_LOCATOR.get(), "地牢定位器");
        this.add(TAItems.KEEPERS_BOW.get(), "守卫之弓");
        this.add(TAItems.KNIGHT_HELMET.get(), "骑士头盔");
        this.add(TAItems.KNIGHT_CHESTPLATE.get(), "骑士胸甲");
        this.add(TAItems.KNIGHT_LEGGINGS.get(), "骑士护腿");
        this.add(TAItems.KNIGHT_BOOTS.get(), "骑士靴子");
        this.add(TAItems.LAVENDER.get(), "薰衣草");
        this.add(TAItems.LAVENDER_BREAD.get(), "薰衣草面包");
        this.add(TAItems.LAVENDER_TEA.get(), "薰衣草茶");
        this.add(TAItems.LIVING_DIVINING_ROD.get(), "生命卜窥权杖");
        this.add(TAItems.LOCK_PICKS.get(), "开锁器");
        this.add(TAItems.MOON_ACOLYTE_SPAWN_EGG.get(), "皎月侍从刷怪蛋");
        this.add(TAItems.MOON_QUEEN_SPAWN_EGG.get(), "皎月女王刷怪蛋");
        this.add(TAItems.MOONSTONE_INGOT.get(), "皎月石锭");
        this.add(TAItems.MOONSTONE_NUGGET.get(), "皎月石块");
        this.add(TAItems.MOON_SHIELD.get(), "皎月之盾");
        this.add(TAItems.MOONSTONE_AXE.get(), "皎月石斧");
        this.add(TAItems.MOONSTONE_HOE.get(), "皎月石锄");
        this.add(TAItems.MOONSTONE_PICKAXE.get(), "皎月石镐");
        this.add(TAItems.MOONSTONE_SHOVEL.get(), "皎月石铲");
        this.add(TAItems.MOONSTONE_SWORD.get(), "皎月石剑");
        this.add(TAItems.MOONSTONE_SICKLE.get(), "皎月石镰");
        this.add(TAItems.PETUNIA_TEA.get(), "牵牛花茶");
        this.add(TAItems.PLANT_FIBER.get(), "植物纤维");
        this.add(TAItems.QUEENS_CHIPPER.get(), "女王镐");
        this.add(TAItems.RUNESTONE_KEEPER_SPAWN_EGG.get(), "符石守卫刷怪蛋");
        this.add(TAItems.LAVENDER_SEEDY_TEA.get(), "薰衣草籽茶");
        this.add(TAItems.SILENT_WOOD_AXE.get(), "谧木斧");
        this.add(TAItems.SILENT_WOOD_HOE.get(), "谧木锄");
        this.add(TAItems.SILENT_WOOD_PICKAXE.get(), "谧木镐");
        this.add(TAItems.SILENT_WOOD_SHOVEL.get(), "谧木铲");
        this.add(TAItems.SILENT_WOOD_SWORD.get(), "谧木剑");
        this.add(TAItems.SILENT_WOOD_BOW.get(), "谧木弓");
        this.add(TAItems.SILENT_WOOD_STICK.get(), "谧木棍");
        this.add(TAItems.SILK_BERRY.get(), "桑葚");
        this.add(TAItems.SILK_BERRY_JAM.get(), "桑葚酱");
        this.add(TAItems.SILK_BERRY_JAM_SANDWICH.get(), "桑葚酱三明治");
        this.add(TAItems.SILKBERRY_TEA.get(), "桑葚茶");
        this.add(TAItems.SILK_SHROOM_STEW.get(), "桑葚煲");
        this.add(TAItems.SLEEPING_BLACK_TEA.get(), "昏睡红茶");
        this.add(TAItems.SOULLESS_FLESH.get(), "魂灭之肉");
        this.add(TAItems.SPECTRAL_SILK.get(), "幽冥丝绸");
        this.add(TAItems.SPECTRAL_HELMET.get(), "幽冥头盔");
        this.add(TAItems.SPECTRAL_CHESTPLATE.get(), "幽冥胸甲");
        this.add(TAItems.SPECTRAL_LEGGINGS.get(), "幽冥护腿");
        this.add(TAItems.SPECTRAL_BOOTS.get(), "幽冥靴子");
        this.add(TAItems.SPIDER_MOTHER_SPAWN_EGG.get(), "蛛母刷怪蛋");
        this.add(TAItems.SPIDERLING_SPAWN_EGG.get(), "幼蛛刷怪蛋");
        this.add(TAItems.SPIRIT_SPAWN_EGG.get(), "魂灵刷怪蛋");
        this.add(TAItems.SPIKED_CHESTPLATE.get(), "尖钉胸甲");
        this.add(TAItems.STICKY_SPIKER.get(), "粘性尖钉");
        this.add(TAItems.TEA_CUP.get(), "茶杯");
        this.add(TAItems.TROPHY_MOON_QUEEN.get(), "皎月女王奖励");
        this.add(TAItems.TROPHY_SPIDER_MOTHER.get(), "蛛母奖励");
        this.add(TAItems.UMBRA_INGOT.get(), "本影锭");
        this.add(TAItems.UMBRA_SCRAP.get(), "本影碎片");
        this.add(TAItems.UMBRA_SHIELD.get(), "本影盾牌");
        this.add(TAItems.UMBRA_SWORD.get(), "本影剑");
        this.add(TAItems.UMBRA_PICKAXE.get(), "本影镐");
        this.add(TAItems.UNDEAD_KNIGHT_SPAWN_EGG.get(), "不死骑士刷怪蛋");
        this.add(TAItems.WEEPING_WILLOW_SAP.get(), "垂柳树汁");
        this.add(TAItems.WEBBING.get(), "蛛网");

        //MOD ENTITY
        this.add(TAEntityTypes.CRYSTALLINE_BEAM.get(), "月凝晶射线");
        this.add(TAEntityTypes.CERULEAN_ARROW.get(), "晶蓝箭");
        this.add(TAEntityTypes.CRYSTAL_ARROW.get(), "水晶箭");
        this.add(TAEntityTypes.STICKY_SPIKER.get(), "粘性尖刺");
        this.add(TAEntityTypes.WEBBING.get(), "蛛母之网");
        this.add(TAEntityTypes.EYE_OF_DISTURBED.get(), "空谷之眼");
        this.add(TAEntityTypes.AURORIAN_RABBIT.get(), "极光兔");
        this.add(TAEntityTypes.AURORIAN_SHEEP.get(), "极光羊");
        this.add(TAEntityTypes.AURORIAN_PIG.get(), "极光猪");
        this.add(TAEntityTypes.AURORIAN_PIXIE.get(), "极光精灵");
        this.add(TAEntityTypes.AURORIAN_SLIME.get(), "极光史莱姆");
        this.add(TAEntityTypes.DISTURBED_HOLLOW.get(), "空谷之扰");
        this.add(TAEntityTypes.UNDEAD_KNIGHT.get(), "不死骑士");
        this.add(TAEntityTypes.SPIRIT.get(), "魂灵");
        this.add(TAEntityTypes.MOON_ACOLYTE.get(), "皎月侍从");
        this.add(TAEntityTypes.SPIDERLING.get(), "幼蛛");
        this.add(TAEntityTypes.CRYSTALLINE_SPRITE.get(), "月凝晶魂");
        this.add(TAEntityTypes.RUNESTONE_KEEPER.get(), "符石守卫");
        this.add(TAEntityTypes.SPIDER_MOTHER.get(), "蛛母");
        this.add(TAEntityTypes.MOON_QUEEN.get(), "皎月女王");

        //MOB EFFECT
        this.add(TAMobEffects.STUN.get(), "眩晕");
        this.add(TAMobEffects.MOON_CURSE.get(), "皎月之咒");

        //MOD ENCHANTMENT
        this.add(TAEnchantments.LIGHTNING_RESISTANCE.get(), "雷电抵御");
        this.add(TAEnchantments.LIGHTNING_DAMAGE.get(), "雷电");
        this.add(TAEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "降低“雷电”魔咒造成的额外伤害，同时完全抵消被雷击中的伤害");
        this.add(TAEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId() + ".desc", "根据对方穿戴的护甲数量造成额外伤害");

        //MOD PAINTING
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.AURORIAN_STEEL.get()), "极光钢");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.PROGRESSION.get()), "前进");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.DUNGEON.get()), "地牢");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.KEEPER.get()), "守卫");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.KNIGHT.get()), "骑士");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.MOON.get()), "皎月");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.PORTAL.get()), "传送门");
        this.add(TAPaintingVariants.createDescriptionId(TAPaintingVariants.SLIME.get()), "史莱姆");
    }

}