package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviderENUS extends LanguageProvider {

    public ModLanguageProviderENUS(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + AurorianMod.MOD_ID, "The Aurorian");
        this.add(ModBlocks.AURORIAN_DIRT.get(), "Aurorian Dirt");
        this.add(ModBlocks.AURORIAN_STONE.get(), "Aurorian Stone");
        this.add(ModBlocks.AURORIAN_STONE_BRICKS.get(), "Aurorian Stone Bricks");
        this.add(ModBlocks.AURORIAN_COBBLESTONE.get(), "Aurorian Cobblestone");
        this.add(ModBlocks.AURORIAN_COAL_ORE.get(), "Aurorian Coal Ore");
        this.add(ModBlocks.AURORIAN_GRASS_BLOCK.get(), "Aurorian Grass Block");
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(), "Light Aurorian Grass Block");
        this.add(ModBlocks.AURORIAN_FARM_TILE.get(), "Aurorian Farm Tile");
        this.add(ModBlocks.AURORIAN_GLASS.get(), "Aurorian Glass");
        this.add(ModBlocks.MOON_GLASS.get(), "Moon Glass");
        this.add(ModBlocks.AURORIAN_GLASS_PANE.get(), "Aurorian Glass Pane");
        this.add(ModBlocks.MOON_GLASS_PANE.get(), "Moon Glass Pane");
        this.add(ModBlocks.AURORIAN_GRASS.get(), "Aurorian Grass");
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT.get(), "Light Aurorian Grass");
        this.add(ModBlocks.AURORIAN_FURNACE.get(), "Aurorian Furnace");
        this.add(ModBlocks.AURORIAN_FURNACE_CHIMNEY.get(), "Aurorian Furnace Chimney");
        this.add(ModBlocks.AURORIAN_PORTAL.get(), "Aurorian Portal");
        this.add(ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), "Aurorian Portal Frame Brick");
        this.add(ModBlocks.URN.get(), "Urn");
        this.add(ModBlocks.AURORIAN_PERIDOTITE.get(), "Aurorian Peridotite");
        this.add(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), "Smooth Aurorian Peridotite");
        this.add(ModBlocks.MOONSTONE_ORE.get(), "Moonstone Ore");
        this.add(ModBlocks.CERULEAN_ORE.get(), "Cerulean Ore");
        this.add(ModBlocks.GEODE_ORE.get(), "Geode Ore");
        this.add(ModBlocks.RUNE_STONE.get(), "Runestone");
        this.add(ModBlocks.MOON_TEMPLE_BRICKS.get(), "Moon Temple Bricks");
        this.add(ModBlocks.DARK_STONE_BRICKS.get(), "Dark Stone Bricks");
        this.add(ModBlocks.DARK_STONE_FANCY.get(), "Dark Stone Fancy");
        this.add(ModBlocks.DARK_STONE_LAYERS.get(), "Dark Stone Layers");
        this.add(ModBlocks.SMOOTH_RUNE_STONE.get(), "Smooth Runestone");
        this.add(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), "Smooth Moon Temple Bricks");
        this.add(ModBlocks.RUNE_STONE_LAMP.get(), "Runestone Lamp");
        this.add(ModBlocks.DARK_STONE_LAMP.get(), "Dark Stone Lamp");
        this.add(ModBlocks.MOON_TEMPLE_LAMP.get(), "Moon Temple Lamp");
        this.add(ModBlocks.CERULEAN_BLOCK.get(), "Cerulean Block");
        this.add(ModBlocks.MOONSTONE_BLOCK.get(), "Moonstone Block");
        this.add(ModBlocks.AURORIAN_COAL_BLOCK.get(), "Aurorian Coal Block");
        this.add(ModBlocks.AURORIAN_STEEL_BLOCK.get(), "Aurorian Steel Block");
        this.add(ModBlocks.MYSTICAL_BARRIER.get(), "Mystical Barrier");
        this.add(ModBlocks.RUNE_STONE_BARS.get(), "Rune stone Bars");
        this.add(ModBlocks.MOON_TEMPLE_BARS.get(), "Moon Temple Bars");
        this.add(ModBlocks.RUNE_STONE_GATE.get(), "Runestone Gate");
        this.add(ModBlocks.DARK_STONE_GATE.get(), "Dark Stone Gate");
        this.add(ModBlocks.MOON_TEMPLE_GATE.get(), "Moon Temple Gate");
        this.add(ModBlocks.RUNE_STONE_LOOT_GATE.get(), "Runestone Loot Gate");
        this.add(ModBlocks.MOON_TEMPLE_CELL_GATE.get(), "Moon Temple Cell Gate");
        this.add(ModBlocks.RUNE_STONE_GATE_KEYHOLE.get(), "Runestone Gate Keyhole");
        this.add(ModBlocks.DARK_STONE_GATE_KEYHOLE.get(), "Dark Stone Gate Keyhole");
        this.add(ModBlocks.MOON_TEMPLE_GATE_KEYHOLE.get(), "Moon Temple Gate Keyhole");
        this.add(ModBlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get(), "Runestone Loot Gate Keyhole");
        this.add(ModBlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get(), "Moon Temple Cell Gate Keyhole");
        this.add(ModBlocks.INDIGO_MUSHROOM_BLOCK.get(), "Indigo Mushroom Block");
        this.add(ModBlocks.INDIGO_MUSHROOM_STEM.get(), "Indigo Mushroom Stem");
        this.add(ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get(), "Indigo Mushroom Crystal");
        this.add(ModBlocks.MOONLIGHT_FORGE.get(), "Moonlight Forge");
        this.add(ModBlocks.MOON_GEM.get(), "Moon Gem");
        this.add(ModBlocks.MOON_SAND.get(), "Moon Sand");
        this.add(ModBlocks.MOON_TORCH.get(), "Moon Torch");
        this.add(ModBlocks.UMBRA_STONE.get(), "Umbra Stone");
        this.add(ModBlocks.UMBRA_STONE_CRACKED.get(), "Cracked Umbra Stone");
        this.add(ModBlocks.UMBRA_STONE_ROOF_TILES.get(), "Umbra Stone Roof Tiles");
        this.add(ModBlocks.SILENT_TREE_LEAVES.get(), "Silent Tree Leaves");
        this.add(ModBlocks.SILENT_TREE_LOG.get(), "Silent Tree Log");
        this.add(ModBlocks.SILENT_TREE_PLANKS.get(), "Silent Tree Planks");
        this.add(ModBlocks.SILENT_TREE_WOOD.get(), "Silent Tree Wood");
        this.add(ModBlocks.SILENT_WOOD_TORCH.get(), "Silent Wood Torch");
        this.add(ModBlocks.SILENT_WOOD_LADDER.get(), "Silent Wood Ladder");
        this.add(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get(), "Silent Wood Crafting");
        this.add(ModBlocks.WEEPING_WILLOW_LEAVES.get(), "Weeping Willow Leaves");
        this.add(ModBlocks.WEEPING_WILLOW_LOG.get(), "Weeping Willow Log");
        this.add(ModBlocks.WEEPING_WILLOW_PLANKS.get(), "Weeping Willow Planks");
        this.add(ModBlocks.WEEPING_WILLOW_WOOD.get(), "Weeping Willow Wood");
        this.add(ModBlocks.AURORIAN_STONE_STAIRS.get(), "Aurorian Stone Stairs");
        this.add(ModBlocks.AURORIAN_STONE_BRICK_STAIRS.get(), "Aurorian Stone Brick Stairs");
        this.add(ModBlocks.AURORIAN_COBBLESTONE_STAIRS.get(), "Aurorian Cobblestone Stairs");
        this.add(ModBlocks.SILENT_WOOD_STAIRS.get(), "Silent Wood Stairs");
        this.add(ModBlocks.RUNE_STONE_STAIRS.get(), "Runestone Stairs");
        this.add(ModBlocks.MOON_TEMPLE_STAIRS.get(), "Moon Temple Stairs");
        this.add(ModBlocks.DARK_STONE_STAIRS.get(), "Dark Stone Stairs");
        this.add(ModBlocks.UMBRA_STONE_STAIRS.get(), "Umbra Stone Stairs");
        this.add(ModBlocks.UMBRA_STONE_CRACKED_STAIRS.get(), "Cracked Umbra Stone Stairs");
        this.add(ModBlocks.UMBRA_STONE_ROOF_STAIRS.get(), "Umbra Stone Roof Tiles Stairs");
        this.add(ModBlocks.WEEPING_WILLOW_STAIRS.get(), "Weeping Willow Stairs");
        this.add(ModBlocks.AURORIAN_PERIDOTITE_STAIRS.get(), "Aurorian Peridotite Stairs");
        this.add(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get(), "Smooth Aurorian Peridotite Stairs");
        this.add(ModEnchantments.LIGHTNING_RESISTANCE.get(), "Lightning Resistance");
        this.add(ModEnchantments.LIGHTNING_DAMAGE.get(), "Lightning");
        this.add(ModEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "Negates damage that would have been done by lightning enchantment");
        this.add(ModEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId()+ ".desc", "Does extra damage depending on how much armor the target is wearing");
    }

}