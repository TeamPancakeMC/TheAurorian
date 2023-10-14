package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModEnchantments;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviderENUS extends LanguageProvider {

    public ModLanguageProviderENUS(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + AurorianMod.MOD_ID, "The Aurorian");
        this.add("theaurorian.container.scrapper", "Scrapper");
        this.add("theaurorian.container.moonlight_forge", "Moonlight Forge");
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
        this.add(ModBlocks.SCRAPPER.get(), "Scrapper");
        this.add(ModBlocks.UMBRA_STONE.get(), "Umbra Stone");
        this.add(ModBlocks.UMBRA_STONE_CRACKED.get(), "Cracked Umbra Stone");
        this.add(ModBlocks.UMBRA_STONE_ROOF_TILES.get(), "Umbra Stone Roof Tiles");
        this.add(ModBlocks.SILENT_TREE_LEAVES.get(), "Silent Tree Leaves");
        this.add(ModBlocks.SILENT_TREE_LOG.get(), "Silent Tree Log");
        this.add(ModBlocks.SILENT_TREE_PLANKS.get(), "Silent Tree Planks");
        this.add(ModBlocks.SILENT_TREE_WOOD.get(), "Silent Tree Wood");
        this.add(ModBlocks.SILENT_TREE_SAPLING.get(), "Silent Tree Sapling");
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
        //MOD ITEM
        this.add(ModItems.DARK_STONE_KEY.get(),"Dark Stone Key");
        this.add(ModItems.MOON_TEMPLE_KEY.get(),"Moon Temple Key");
        this.add(ModItems.RUNE_STONE_KEY.get(),"Rune stone Key");
        this.add(ModItems.RUNE_STONE_LOOT_KEY.get(),"Rune Stone Loot Key");
        this.add(ModItems.MOON_TEMPLE_CELL_KEY.get(),"Moon Temple Cell Key");
        this.add(ModItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(),"Moon Temple Cell Key Fragment");
        this.add(ModItems.ABSORPTION_ORB.get(),"Absorption Orb");
        this.add(ModItems.AURORIAN_BACON.get(),"Aurorian Bacon");
        this.add(ModItems.AURORIAN_COAL.get(),"Aurorian Coal");
        this.add(ModItems.AURORIAN_COAL_NUGGET.get(),"Aurorian Coal Nugget");
        this.add(ModItems.AURORIAN_PORK.get(),"Aurorian Pork");
        this.add(ModItems.AURORIAN_PIG_SPAWN_EGG.get(),"Aurorian Pig Spawn Egg");
        this.add(ModItems.AURORIAN_RABBIT_SPAWN_EGG.get(),"Aurorian Rabbit Spawn Egg");
        this.add(ModItems.AURORIAN_SHEEP_SPAWN_EGG.get(),"Aurorian Sheep Spawn Egg");
        this.add(ModItems.AURORIAN_SLIME_SPAWN_EGG.get(),"Aurorian Slime Spawn Egg");
        this.add(ModItems.AURORIAN_SLIME_BOOTS.get(),"Aurorian Slime Boots");
        this.add(ModItems.AURORIAN_SLIMEBALL.get(),"Aurorian Slime Ball");
        this.add(ModItems.AURORIAN_STEEL.get(),"Aurorian Steel");
        this.add(ModItems.AURORIAN_STEEL_NUGGET.get(),"Aurorian Steel Nugget");
        this.add(ModItems.AURORIAN_STEEL_AXE.get(),"Aurorian Steel Axe");
        this.add(ModItems.AURORIAN_STEEL_PICKAXE.get(),"Aurorian Steel Pickaxe");
        this.add(ModItems.AURORIAN_STEEL_SHOVEL.get(),"Aurorian Steel Shovel");
        this.add(ModItems.AURORIAN_STONE_SICKLE.get(),"Aurorian Steel Sickle");
        this.add(ModItems.AURORIAN_STEEL_SWORD.get(),"Aurorian Steel Sword");
        this.add(ModItems.AURORIAN_STEEL_HELMET.get(),"Aurorian Steel Helmet");
        this.add(ModItems.AURORIAN_STEEL_CHESTPLATE.get(),"Aurorian Steel Chestplate");
        this.add(ModItems.AURORIAN_STEEL_LEGGINGS.get(),"Aurorian Steel Leggings");
        this.add(ModItems.AURORIAN_STEEL_BOOTS.get(),"Aurorian Steel Boots");
        this.add(ModItems.AURORIAN_STONE_AXE.get(),"Aurorian Stone Axe");
        this.add(ModItems.AURORIAN_STONE_PICKAXE.get(),"Aurorian Stone Pickaxe");
        this.add(ModItems.AURORIAN_STONE_SHOVEL.get(),"Aurorian Stone Shovel");
        this.add(ModItems.AURORIAN_STONE_SWORD.get(),"Aurorian Stone Sword");
        this.add(ModItems.AURORIANITE_INGOT.get(),"Aurorianite Ingot");
        this.add(ModItems.AURORIANITE_SCRAP.get(),"Aurorianite Scrap");
        this.add(ModItems.AURORIANITE_AXE.get(),"Aurorianite Axe");
        this.add(ModItems.AURORIANITE_PICKAXE.get(),"Aurorianite Pickaxe");
        this.add(ModItems.BEPSI.get(),"Bepsi");
        this.add(ModItems.CERULEAN_ARROW.get(),"Cerulean Arrow");
        this.add(ModItems.CERULEAN_INGOT.get(),"Cerulean Ingot");
        this.add(ModItems.CERULEAN_NUGGET.get(),"Cerulean Nugget");
        this.add(ModItems.CERULEAN_HELMET.get(),"Cerulean Helmet");
        this.add(ModItems.CERULEAN_CHESTPLATE.get(),"Cerulean Chestplate");
        this.add(ModItems.CERULEAN_LEGGINGS.get(),"Cerulean Leggings");
        this.add(ModItems.CERULEAN_BOOTS.get(),"Cerulean Boots");
        this.add(ModItems.CERULEAN_SHIELD.get(),"Cerulean Shield");
        this.add(ModItems.COOKED_AURORIAN_PORK.get(),"Cooked Aurorian Pork");
        this.add(ModItems.CRYSTAL.get(),"Crystal");
        this.add(ModItems.CRYSTAL_ARROW.get(),"Crystal Arrow");
        this.add(ModItems.CRYSTALLINE_INGOT.get(),"Crystalline Ingot");
        this.add(ModItems.CRYSTALLINE_SCRAP.get(),"Crystalline Scrap");
        this.add(ModItems.CRYSTALLINE_PICKAXE.get(),"Crystalline Pickaxe");
        this.add(ModItems.CRYSTALLINE_SHIELD.get(),"Crystalline Shield");
        this.add(ModItems.CRYSTALLINE_SWORD.get(),"Crystalline Sword");
        this.add(ModItems.CRYSTALLINE_SPRITE.get(),"Crystalline Sprite");
        this.add(ModItems.CRYSTALLINE_SPRITE_SPAWN_EGG.get(),"Crystalline Sprite Spawn Egg");
        this.add(ModItems.DARK_AMULET.get(),"Dark Amulet");
        this.add(ModItems.DUNGEON_KEEPER_AMULET.get(),"Dungeon Keeper Amulet");
        this.add(ModItems.TROPHY_KEEPER.get(),"Keeper Trophy");
        this.add(ModItems.DISTURBED_HOLLOW_SPAWN_EGG.get(),"Disturbed Hollow Spawn Egg");
        this.add(ModItems.DUNGEON_LOCATOR.get(),"Dungeon Locator");
        this.add(ModItems.KEEPERS_BOW.get(),"Keeper's Bow");
        this.add(ModItems.KNIGHT_HELMET.get(),"Knight Helmet");
        this.add(ModItems.KNIGHT_CHESTPLATE.get(),"Knight Chestplate");
        this.add(ModItems.KNIGHT_LEGGINGS.get(),"Knight Leggings");
        this.add(ModItems.KNIGHT_BOOTS.get(),"Knight Boots");
        this.add(ModItems.LAVENDER.get(),"Lavender");
        this.add(ModItems.LAVENDER_BREAD.get(),"Lavender Bread");
        this.add(ModItems.LAVENDER_TEA.get(),"Lavender Tea");
        this.add(ModItems.LAVENDER_SEEDS.get(),"Lavender Seeds");
        this.add(ModItems.LIVING_DIVINING_ROD.get(),"Living Divining Rod");
        this.add(ModItems.LOCK_PICKS.get(),"Lock Picks");
        this.add(ModItems.MOON_ACOLYTE_SPAWN_EGG.get(),"Moon Acolyte Spawn Egg");
        this.add(ModItems.MOON_QUEEN_SPAWN_EGG.get(),"Moon Queen Spawn Egg");
        this.add(ModItems.MOONSTONE_INGOT.get(),"Moonstone Ingot");
        this.add(ModItems.MOONSTONE_NUGGET.get(),"Moonstone Nugget");
        this.add(ModItems.MOON_SHIELD.get(),"Moon Shield");
        this.add(ModItems.MOONSTONE_AXE.get(),"Moonstone Axe");
        this.add(ModItems.MOONSTONE_HOE.get(),"Moonstone Hoe");
        this.add(ModItems.MOONSTONE_PICKAXE.get(),"Moonstone Pickaxe");
        this.add(ModItems.MOONSTONE_SHOVEL.get(),"Moonstone Shovel");
        this.add(ModItems.MOONSTONE_SWORD.get(),"Moonstone Sword");
        this.add(ModItems.MOONSTONE_SICKLE.get(),"Moonstone Sickle");
        this.add(ModItems.PETUNIA_TEA.get(),"Petunia Tea");
        this.add(ModItems.PLANT_FIBER.get(),"Plant Fiber");
        this.add(ModItems.QUEENS_CHIPPER.get(),"Queen's Chipper");
        this.add(ModItems.RUNESTONE_KEEPER_SPAWN_EGG.get(),"Runestone Keeper Spawn Egg");
        this.add(ModItems.LAVENDER_SEEDY_TEA.get(),"Lavender Seedy Tea");
        this.add(ModItems.SILENT_WOOD_AXE.get(),"Silent Wood Axe");
        this.add(ModItems.SILENT_WOOD_HOE.get(),"Silent Wood Hoe");
        this.add(ModItems.SILENT_WOOD_PICKAXE.get(),"Silent Wood Pickaxe");
        this.add(ModItems.SILENT_WOOD_SHOVEL.get(),"Silent Wood Shovel");
        this.add(ModItems.SILENT_WOOD_SWORD.get(),"Silent Wood Sword");
        this.add(ModItems.SILENT_WOOD_BOW.get(),"Silent Wood Bow");
        this.add(ModItems.SILK_BERRY.get(),"Silk Berry");
        this.add(ModItems.SILK_BERRY_JAM.get(),"Silk Berry Jam");
        this.add(ModItems.SILK_BERRY_JAM_SANDWICH.get(),"Silk Berry Jam Sandwich");
        this.add(ModItems.SILKBERRY_TEA.get(),"Slik Berry Tea");
        this.add(ModItems.SILK_SHROOM_STEW.get(),"Silk Shroom Stew");
        this.add(ModItems.SLEEPING_BLACK_TEA.get(),"Sleeping Black Tea");
        this.add(ModItems.SOULLESS_FLESH.get(),"Soulless Flesh");
        this.add(ModItems.SPECTRAL_SILK.get(),"Spectral Silk");
        this.add(ModItems.SPECTRAL_HELMET.get(),"Spectral Helmet");
        this.add(ModItems.SPECTRAL_CHESTPLATE.get(),"Spectral Chestplate");
        this.add(ModItems.SPECTRAL_LEGGINGS.get(),"Spectral Leggings");
        this.add(ModItems.SPECTRAL_BOOTS.get(),"Spectral Boots");
        this.add(ModItems.SPIDER_MOTHER_SPAWN_EGG.get(),"Spider Mother Spawn Egg");
        this.add(ModItems.SPIDERLING_SPAWN_EGG.get(),"Spiderling Spawn Egg");
        this.add(ModItems.SPIRIT_SPAWN_EGG.get(),"Spirit Spawn Egg");
        this.add(ModItems.SPIKED_CHESTPLATE.get(),"Spiked Chestplate");
        this.add(ModItems.STICKY_SPIKER.get(),"Sticky Spiker");
        this.add(ModItems.TEA_CUP.get(),"Tea Cup");
        this.add(ModItems.TROPHY_MOON_QUEEN.get(),"Moon Queen Trophy");
        this.add(ModItems.TROPHY_SPIDER_MOTHER.get(),"Spider Mother Trophy");
        this.add(ModItems.UMBRA_INGOT.get(),"Umbra Ingot");
        this.add(ModItems.UMBRA_SCRAP.get(),"Umbra Scrap");
        this.add(ModItems.UMBRA_SHIELD.get(),"Umbra Shield");
        this.add(ModItems.UMBRA_SWORD.get(),"Umbra Sword");
        this.add(ModItems.UMBRA_PICKAXE.get(),"Umbra Pickaxe");
        this.add(ModItems.UNDEAD_KNIGHT_SPAWN_EGG.get(),"Undead Knight Spawn Egg");
        this.add(ModItems.WEBBING.get(),"Webbing");
        this.add(ModItems.WEEPING_WILLOW_SAP.get(),"Weeping Willow Sap");

        this.add(ModEnchantments.LIGHTNING_RESISTANCE.get(), "Lightning Resistance");
        this.add(ModEnchantments.LIGHTNING_DAMAGE.get(), "Lightning");
        this.add(ModEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "Negates damage that would have been done by lightning enchantment");
        this.add(ModEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId()+ ".desc", "Does extra damage depending on how much armor the target is wearing");
    }

}