package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviderZHCN extends LanguageProvider {

    public ModLanguageProviderZHCN(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + AurorianMod.MOD_ID, "极光幽境");
        this.add(ModBlocks.AURORIAN_DIRT.get(), "极光泥土");
        this.add(ModBlocks.AURORIAN_STONE.get(), "极光石");
        this.add(ModBlocks.AURORIAN_STONE_BRICK.get(), "极光石砖");
        this.add(ModBlocks.AURORIAN_COBBLESTONE.get(), "极光圆石");
        this.add(ModBlocks.AURORIAN_COAL_ORE.get(), "极光煤矿石");
        this.add(ModBlocks.AURORIAN_GRASS_BLOCK.get(), "极光草方块");
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(), "发光极光草方块");
        this.add(ModBlocks.AURORIAN_FARMLAND.get(), "极光耕地");
        this.add(ModBlocks.AURORIAN_GLASS.get(), "极光玻璃");
        this.add(ModBlocks.MOON_GLASS.get(), "皎月玻璃");
        this.add(ModBlocks.AURORIAN_GLASS_PANE.get(), "极光玻璃板");
        this.add(ModBlocks.MOON_GLASS_PANE.get(), "皎月玻璃板");
        this.add(ModBlocks.AURORIAN_GRASS.get(), "极光草");
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT.get(), "发光极光草");
        this.add(ModBlocks.AURORIAN_FURNACE.get(), "极光熔炉");
        this.add(ModBlocks.AURORIAN_FURNACE_CHIMNEY.get(), "极光熔炉孔道");
        this.add(ModBlocks.AURORIAN_PORTAL.get(), "极光传送门");
        this.add(ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), "极光传送门框架");
        this.add(ModBlocks.URN.get(), "瓮");
        this.add(ModBlocks.AURORIAN_PERIDOTITE.get(), "极光橄榄岩");
        this.add(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), "平滑极光橄榄岩");
        this.add(ModBlocks.MOONSTONE_ORE.get(), "皎月石矿石");
        this.add(ModBlocks.CERULEAN_ORE.get(), "晶蓝矿石");
        this.add(ModBlocks.GEODE_ORE.get(), "晶簇矿石");
        this.add(ModBlocks.RUNE_STONE.get(), "符石");
        this.add(ModBlocks.MOON_TEMPLE_BRICKS.get(), "月宫砖块");
        this.add(ModBlocks.DARK_STONE_BRICKS.get(), "暗石砖块");
        this.add(ModBlocks.DARK_STONE_FANCY.get(), "装饰性暗石");
        this.add(ModBlocks.DARK_STONE_LAYERS.get(), "暗石地层");
        this.add(ModBlocks.SMOOTH_RUNE_STONE.get(), "平滑符石");
        this.add(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), "平滑月宫砖块");
        this.add(ModBlocks.RUNE_STONE_LAMP.get(), "符石灯");
        this.add(ModBlocks.DARK_STONE_LAMP.get(), "暗石灯");
        this.add(ModBlocks.MOON_TEMPLE_LAMP.get(), "月宫灯");
        this.add(ModBlocks.CERULEAN_BLOCK.get(), "晶蓝块");
        this.add(ModBlocks.MOONSTONE_BLOCK.get(), "皎月石块");
        this.add(ModBlocks.AURORIAN_COAL_BLOCK.get(), "极光煤块");
        this.add(ModBlocks.AURORIAN_STEEL_BLOCK.get(), "极光钢块");
        this.add(ModBlocks.MYSTICAL_BARRIER.get(), "神秘屏障");
        this.add(ModBlocks.RUNE_STONE_BARS.get(), "符石栏杆");
        this.add(ModBlocks.MOON_TEMPLE_BARS.get(), "月宫栏杆");
        this.add(ModBlocks.RUNE_STONE_GATE.get(), "符石门");
        this.add(ModBlocks.DARK_STONE_GATE.get(), "暗石门");
        this.add(ModBlocks.MOON_TEMPLE_GATE.get(), "月宫门");
        this.add(ModBlocks.RUNE_STONE_LOOT_GATE.get(), "符石宝藏室门");
        this.add(ModBlocks.MOON_TEMPLE_CELL_GATE.get(), "月宫内室门");
        this.add(ModBlocks.RUNE_STONE_GATE_KEYHOLE.get(), "符石门锁孔");
        this.add(ModBlocks.DARK_STONE_GATE_KEYHOLE.get(), "暗石门锁孔");
        this.add(ModBlocks.MOON_TEMPLE_GATE_KEYHOLE.get(), "月宫门锁孔");
        this.add(ModBlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get(), "符石宝藏室门锁孔");
        this.add(ModBlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get(), "月宫内室门锁孔");
        this.add(ModBlocks.INDIGO_MUSHROOM.get(), "深蓝蘑菇");
        this.add(ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get(), "深蓝蘑菇水晶");
        this.add(ModBlocks.MOONLIGHT_FORGE.get(), "月光融锻台");
        this.add(ModBlocks.MOON_GEM.get(), "皎月宝石");
        this.add(ModBlocks.MOON_SAND.get(), "皎月沙");
        this.add(ModBlocks.UMBRA_STONE.get(), "本影石");
        this.add(ModBlocks.UMBRA_STONE_CRACKED.get(), "裂纹本影石");
        this.add(ModBlocks.UMBRA_STONE_ROOF_TILES.get(), "本影石瓦");
        this.add(ModBlocks.SILENT_TREE_LEAVES.get(), "谧树树叶");
        this.add(ModBlocks.SILENT_TREE_LOG.get(), "谧树原木");
        this.add(ModBlocks.SILENT_TREE_PLANKS.get(), "谧树木板");
        this.add(ModBlocks.SILENT_TREE_WOOD.get(), "谧树木头");
        this.add(ModBlocks.SILENT_WOOD_TORCH.get(), "谧树木火把");
        this.add(ModBlocks.SILENT_WOOD_LADDER.get(), "谧树木楼梯");
        this.add(ModBlocks.WEEPING_WILLOW_LEAVES.get(), "垂柳树叶");
        this.add(ModBlocks.WEEPING_WILLOW_LOG.get(), "垂柳原木");
        this.add(ModBlocks.WEEPING_WILLOW_PLANKS.get(), "垂柳木板");
        this.add(ModBlocks.WEEPING_WILLOW_WOOD.get(), "垂柳木头");
        this.add(ModBlocks.AURORIAN_STONE_STAIRS.get(), "极光石楼梯");
        this.add(ModBlocks.AURORIAN_STONE_BRICK_STAIRS.get(), "极光石砖楼梯");
        this.add(ModBlocks.AURORIAN_COBBLESTONE_STAIRS.get(), "极光圆石楼梯");
        this.add(ModBlocks.SILENT_WOOD_STAIRS.get(), "谧木楼梯");
        this.add(ModBlocks.RUNE_STONE_STAIRS.get(), "符石楼梯");
        this.add(ModBlocks.MOON_TEMPLE_STAIRS.get(), "月宫楼梯");
        this.add(ModBlocks.DARK_STONE_STAIRS.get(), "符石楼梯");
        this.add(ModBlocks.UMBRA_STONE_STAIRS.get(), "本影石楼梯");
        this.add(ModBlocks.UMBRA_STONE_CRACKED_STAIRS.get(), "裂纹本影石楼梯");
        this.add(ModBlocks.UMBRA_STONE_ROOF_STAIRS.get(), "本影石瓦楼梯");
        this.add(ModBlocks.WEEPING_WILLOW_STAIRS.get(), "垂柳木楼梯");
        this.add(ModBlocks.AURORIAN_PERIDOTITE_STAIRS.get(), "橄榄岩楼梯");
        this.add(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get(), "平滑橄榄岩楼梯");
        add(ModEnchantments.LIGHTNING_RESISTANCE.get(), "雷电抵御");
        add(ModEnchantments.LIGHTNING_DAMAGE.get(), "雷电");
        add(ModEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "降低“雷电”魔咒造成的额外伤害，同时完全抵消被雷击中的伤害");
        add(ModEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId() + ".desc", "根据对方穿戴的护甲数量造成额外伤害");
    }

}