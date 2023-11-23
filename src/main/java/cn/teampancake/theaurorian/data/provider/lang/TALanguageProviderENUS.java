package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAEnchantments;
import cn.teampancake.theaurorian.registry.TAEntityTypes;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class TALanguageProviderENUS extends LanguageProvider {

    public TALanguageProviderENUS(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + AurorianMod.MOD_ID, "The Aurorian");
        this.add("theaurorian.container.scrapper", "Scrapper");
        this.add("theaurorian.container.moonlight_forge", "Moonlight Forge");

        //MOD BLOCK
        this.add(TABlocks.AURORIAN_DIRT.get(), "Aurorian Dirt");
        this.add(TABlocks.AURORIAN_STONE.get(), "Aurorian Stone");
        this.add(TABlocks.AURORIAN_STONE_BRICKS.get(), "Aurorian Stone Bricks");
        this.add(TABlocks.AURORIAN_COBBLESTONE.get(), "Aurorian Cobblestone");
        this.add(TABlocks.AURORIAN_COAL_ORE.get(), "Aurorian Coal Ore");
        this.add(TABlocks.AURORIAN_GRASS_BLOCK.get(), "Aurorian Grass Block");
        this.add(TABlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(), "Light Aurorian Grass Block");
        this.add(TABlocks.AURORIAN_FARM_TILE.get(), "Aurorian Farm Tile");
        this.add(TABlocks.AURORIAN_GLASS.get(), "Aurorian Glass");
        this.add(TABlocks.MOON_GLASS.get(), "Moon Glass");
        this.add(TABlocks.AURORIAN_GLASS_PANE.get(), "Aurorian Glass Pane");
        this.add(TABlocks.MOON_GLASS_PANE.get(), "Moon Glass Pane");
        this.add(TABlocks.AURORIAN_GRASS.get(), "Aurorian Grass");
        this.add(TABlocks.AURORIAN_GRASS_LIGHT.get(), "Light Aurorian Grass");
        this.add(TABlocks.AURORIAN_FURNACE.get(), "Aurorian Furnace");
        this.add(TABlocks.AURORIAN_FURNACE_CHIMNEY.get(), "Aurorian Furnace Chimney");
        this.add(TABlocks.AURORIAN_PORTAL.get(), "Aurorian Portal");
        this.add(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), "Aurorian Portal Frame Brick");
        this.add(TABlocks.URN.get(), "Urn");
        this.add(TABlocks.AURORIAN_PERIDOTITE.get(), "Aurorian Peridotite");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), "Smooth Aurorian Peridotite");
        this.add(TABlocks.MOONSTONE_ORE.get(), "Moonstone Ore");
        this.add(TABlocks.CERULEAN_ORE.get(), "Cerulean Ore");
        this.add(TABlocks.GEODE_ORE.get(), "Geode Ore");
        this.add(TABlocks.RUNE_STONE.get(), "Runestone");
        this.add(TABlocks.SMOOTH_RUNE_STONE.get(), "Smooth Runestone");
        this.add(TABlocks.CHISELED_RUNE_STONE.get(), "Chiseled Runestone");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE.get(), "Aurorian Castle Runestone");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get(), "Aurorian Steel Castle Runestone");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE.get(), "Cerulean Castle Runestone");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get(), "Crystalline Castle Runestone");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE.get(), "Moon Castle Runestone");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE.get(), "Transparent Runestone");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE.get(), "Umbra Castle Runestone");
        this.add(TABlocks.RUNE_STONE_PILLAR.get(), "Runestone Pillar");
        this.add(TABlocks.MOON_TEMPLE_BRICKS.get(), "Moon Temple Bricks");
        this.add(TABlocks.DARK_STONE_BRICKS.get(), "Dark Stone Bricks");
        this.add(TABlocks.DARK_STONE_FANCY.get(), "Dark Stone Fancy");
        this.add(TABlocks.DARK_STONE_LAYERS.get(), "Dark Stone Layers");
        this.add(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), "Smooth Moon Temple Bricks");
        this.add(TABlocks.RUNE_STONE_LAMP.get(), "Runestone Lamp");
        this.add(TABlocks.DARK_STONE_LAMP.get(), "Dark Stone Lamp");
        this.add(TABlocks.MOON_TEMPLE_LAMP.get(), "Moon Temple Lamp");
        this.add(TABlocks.CERULEAN_BLOCK.get(), "Cerulean Block");
        this.add(TABlocks.MOONSTONE_BLOCK.get(), "Moonstone Block");
        this.add(TABlocks.AURORIAN_COAL_BLOCK.get(), "Aurorian Coal Block");
        this.add(TABlocks.AURORIAN_STEEL_BLOCK.get(), "Aurorian Steel Block");
        this.add(TABlocks.MYSTICAL_BARRIER.get(), "Mystical Barrier");
        this.add(TABlocks.RUNE_STONE_BARS.get(), "Rune stone Bars");
        this.add(TABlocks.MOON_TEMPLE_BARS.get(), "Moon Temple Bars");
        this.add(TABlocks.RUNE_STONE_GATE.get(), "Runestone Gate");
        this.add(TABlocks.DARK_STONE_GATE.get(), "Dark Stone Gate");
        this.add(TABlocks.MOON_TEMPLE_GATE.get(), "Moon Temple Gate");
        this.add(TABlocks.RUNE_STONE_LOOT_GATE.get(), "Runestone Loot Gate");
        this.add(TABlocks.MOON_TEMPLE_CELL_GATE.get(), "Moon Temple Cell Gate");
        this.add(TABlocks.RUNE_STONE_GATE_KEYHOLE.get(), "Runestone Gate Keyhole");
        this.add(TABlocks.DARK_STONE_GATE_KEYHOLE.get(), "Dark Stone Gate Keyhole");
        this.add(TABlocks.MOON_TEMPLE_GATE_KEYHOLE.get(), "Moon Temple Gate Keyhole");
        this.add(TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get(), "Runestone Loot Gate Keyhole");
        this.add(TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get(), "Moon Temple Cell Gate Keyhole");
        this.add(TABlocks.INDIGO_MUSHROOM_BLOCK.get(), "Indigo Mushroom Block");
        this.add(TABlocks.INDIGO_MUSHROOM_STEM.get(), "Indigo Mushroom Stem");
        this.add(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get(), "Indigo Mushroom Crystal");
        this.add(TABlocks.MOONLIGHT_FORGE.get(), "Moonlight Forge");
        this.add(TABlocks.MOON_GEM.get(), "Moon Gem");
        this.add(TABlocks.MOON_SAND.get(), "Moon Sand");
        this.add(TABlocks.MOON_TORCH.get(), "Moon Torch");
        this.add(TABlocks.SCRAPPER.get(), "Scrapper");
        this.add(TABlocks.UMBRA_STONE.get(), "Umbra Stone");
        this.add(TABlocks.UMBRA_STONE_CRACKED.get(), "Cracked Umbra Stone");
        this.add(TABlocks.UMBRA_STONE_ROOF_TILES.get(), "Umbra Stone Roof Tiles");
        this.add(TABlocks.SILENT_TREE_LEAVES.get(), "Silent Tree Leaves");
        this.add(TABlocks.SILENT_TREE_LOG.get(), "Silent Tree Log");
        this.add(TABlocks.SILENT_TREE_PLANKS.get(), "Silent Tree Planks");
        this.add(TABlocks.SILENT_TREE_WOOD.get(), "Silent Tree Wood");
        this.add(TABlocks.SILENT_TREE_SAPLING.get(), "Silent Tree Sapling");
        this.add(TABlocks.SILENT_WOOD_TORCH.get(), "Silent Wood Torch");
        this.add(TABlocks.SILENT_WOOD_LADDER.get(), "Silent Wood Ladder");
        this.add(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get(), "Silent Wood Crafting");
        this.add(TABlocks.WEEPING_WILLOW_LEAVES.get(), "Weeping Willow Leaves");
        this.add(TABlocks.WEEPING_WILLOW_LOG.get(), "Weeping Willow Log");
        this.add(TABlocks.WEEPING_WILLOW_PLANKS.get(), "Weeping Willow Planks");
        this.add(TABlocks.WEEPING_WILLOW_WOOD.get(), "Weeping Willow Wood");
        this.add(TABlocks.AURORIAN_STONE_STAIRS.get(), "Aurorian Stone Stairs");
        this.add(TABlocks.AURORIAN_STONE_BRICK_STAIRS.get(), "Aurorian Stone Brick Stairs");
        this.add(TABlocks.AURORIAN_COBBLESTONE_STAIRS.get(), "Aurorian Cobblestone Stairs");
        this.add(TABlocks.SILENT_WOOD_STAIRS.get(), "Silent Wood Stairs");
        this.add(TABlocks.RUNE_STONE_STAIRS.get(), "Runestone Stairs");
        this.add(TABlocks.SMOOTH_RUNE_STONE_STAIRS.get(), "Smooth Runestone Stairs");
        this.add(TABlocks.CHISELED_RUNE_STONE_STAIRS.get(), "Chiseled Runestone Stairs");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_STAIRS.get(), "Aurorian Castle Runestone Stairs");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS.get(), "Aurorian Steel Castle Runestone Stairs");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_STAIRS.get(), "Cerulean Castle Runestone Stairs");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS.get(), "Crystalline Castle Runestone Stairs");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_STAIRS.get(), "Moon Castle Runestone Stairs");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_STAIRS.get(), "Transparent Runestone Stairs");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_STAIRS.get(), "Umbra Castle Runestone Stairs");
        this.add(TABlocks.MOON_TEMPLE_STAIRS.get(), "Moon Temple Stairs");
        this.add(TABlocks.DARK_STONE_STAIRS.get(), "Dark Stone Stairs");
        this.add(TABlocks.UMBRA_STONE_STAIRS.get(), "Umbra Stone Stairs");
        this.add(TABlocks.UMBRA_STONE_CRACKED_STAIRS.get(), "Cracked Umbra Stone Stairs");
        this.add(TABlocks.UMBRA_STONE_ROOF_STAIRS.get(), "Umbra Stone Roof Tiles Stairs");
        this.add(TABlocks.WEEPING_WILLOW_STAIRS.get(), "Weeping Willow Stairs");
        this.add(TABlocks.AURORIAN_PERIDOTITE_STAIRS.get(), "Aurorian Peridotite Stairs");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get(), "Smooth Aurorian Peridotite Stairs");
        this.add(TABlocks.AURORIAN_STONE_SLAB.get(), "Aurorian Stone Slab");
        this.add(TABlocks.AURORIAN_STONE_BRICK_SLAB.get(), "Aurorian Stone Brick Slab");
        this.add(TABlocks.AURORIAN_COBBLESTONE_SLAB.get(), "Aurorian Cobblestone Slab");
        this.add(TABlocks.SILENT_WOOD_SLAB.get(), "Silent Wood Slab");
        this.add(TABlocks.RUNE_STONE_SLAB.get(), "Runestone Slab");
        this.add(TABlocks.SMOOTH_RUNE_STONE_SLAB.get(), "Smooth Runestone Slab");
        this.add(TABlocks.CHISELED_RUNE_STONE_SLAB.get(), "Chiseled Runestone Slab");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_SLAB.get(), "Aurorian Castle Runestone Slab");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB.get(), "Aurorian Steel Castle Runestone Slab");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_SLAB.get(), "Cerulean Castle Runestone Slab");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_SLAB.get(), "Crystalline Castle Runestone Slab");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_SLAB.get(), "Moon Castle Runestone Slab");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_SLAB.get(), "Transparent Runestone Slab");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_SLAB.get(), "Umbra Castle Runestone Slab");
        this.add(TABlocks.MOON_TEMPLE_SLAB.get(), "Moon Temple Slab");
        this.add(TABlocks.DARK_STONE_SLAB.get(), "Dark Stone Slab");
        this.add(TABlocks.UMBRA_STONE_SLAB.get(), "Umbra Stone Slab");
        this.add(TABlocks.UMBRA_STONE_CRACKED_SLAB.get(), "Cracked Umbra Stone Slab");
        this.add(TABlocks.UMBRA_STONE_ROOF_SLAB.get(), "Umbra Stone Roof Tiles Slab");
        this.add(TABlocks.WEEPING_WILLOW_SLAB.get(), "Weeping Willow Slab");
        this.add(TABlocks.AURORIAN_PERIDOTITE_SLAB.get(), "Aurorian Peridotite Slab");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_SLAB.get(), "Smooth Aurorian Peridotite Slab");
        this.add(TABlocks.AURORIAN_STONE_WALL.get(), "Aurorian Stone Wall");
        this.add(TABlocks.AURORIAN_STONE_BRICK_WALL.get(), "Aurorian Stone Brick Wall");
        this.add(TABlocks.AURORIAN_COBBLESTONE_WALL.get(), "Aurorian Cobblestone Wall");
        this.add(TABlocks.RUNE_STONE_WALL.get(), "Runestone Wall");
        this.add(TABlocks.SMOOTH_RUNE_STONE_WALL.get(), "Smooth Runestone Wall");
        this.add(TABlocks.CHISELED_RUNE_STONE_WALL.get(), "Chiseled Runestone Wall");
        this.add(TABlocks.AURORIAN_CASTLE_RUNE_STONE_WALL.get(), "Aurorian Castle Runestone Wall");
        this.add(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL.get(), "Aurorian Steel Castle Runestone Wall");
        this.add(TABlocks.CERULEAN_CASTLE_RUNE_STONE_WALL.get(), "Cerulean Castle Runestone Wall");
        this.add(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE_WALL.get(), "Crystalline Castle Runestone Wall");
        this.add(TABlocks.MOON_CASTLE_RUNE_STONE_WALL.get(), "Moon Castle Runestone Wall");
        this.add(TABlocks.TRANSPARENT_RUNE_STONE_WALL.get(), "Transparent Runestone Wall");
        this.add(TABlocks.UMBRA_CASTLE_RUNE_STONE_WALL.get(), "Umbra Castle Runestone Wall");
        this.add(TABlocks.MOON_TEMPLE_WALL.get(), "Moon Temple Wall");
        this.add(TABlocks.DARK_STONE_WALL.get(), "Dark Stone Wall");
        this.add(TABlocks.UMBRA_STONE_WALL.get(), "Umbra Stone Wall");
        this.add(TABlocks.UMBRA_STONE_CRACKED_WALL.get(), "Cracked Umbra Stone Wall");
        this.add(TABlocks.UMBRA_STONE_ROOF_WALL.get(), "Umbra Stone Roof Tiles Wall");
        this.add(TABlocks.AURORIAN_PERIDOTITE_WALL.get(), "Aurorian Peridotite Wall");
        this.add(TABlocks.SMOOTH_AURORIAN_PERIDOTITE_WALL.get(), "Smooth Aurorian Peridotite Wall");
        //MOD ITEM
        this.add(TAItems.DARK_STONE_KEY.get(),"Dark Stone Key");
        this.add(TAItems.MOON_TEMPLE_KEY.get(),"Moon Temple Key");
        this.add(TAItems.RUNE_STONE_KEY.get(),"Rune stone Key");
        this.add(TAItems.RUNE_STONE_LOOT_KEY.get(),"Rune Stone Loot Key");
        this.add(TAItems.MOON_TEMPLE_CELL_KEY.get(),"Moon Temple Cell Key");
        this.add(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(),"Moon Temple Cell Key Fragment");
        this.add(TAItems.ABSORPTION_ORB.get(),"Absorption Orb");
        this.add(TAItems.AURORIAN_BACON.get(),"Aurorian Bacon");
        this.add(TAItems.AURORIAN_COAL.get(),"Aurorian Coal");
        this.add(TAItems.AURORIAN_COAL_NUGGET.get(),"Aurorian Coal Nugget");
        this.add(TAItems.AURORIAN_PORK.get(),"Aurorian Pork");
        this.add(TAItems.AURORIAN_PIG_SPAWN_EGG.get(),"Aurorian Pig Spawn Egg");
        this.add(TAItems.AURORIAN_RABBIT_SPAWN_EGG.get(),"Aurorian Rabbit Spawn Egg");
        this.add(TAItems.AURORIAN_SHEEP_SPAWN_EGG.get(),"Aurorian Sheep Spawn Egg");
        this.add(TAItems.AURORIAN_SLIME_SPAWN_EGG.get(),"Aurorian Slime Spawn Egg");
        this.add(TAItems.AURORIAN_SLIME_BOOTS.get(),"Aurorian Slime Boots");
        this.add(TAItems.AURORIAN_SLIMEBALL.get(),"Aurorian Slime Ball");
        this.add(TAItems.AURORIAN_STEEL.get(),"Aurorian Steel");
        this.add(TAItems.AURORIAN_STEEL_NUGGET.get(),"Aurorian Steel Nugget");
        this.add(TAItems.AURORIAN_STEEL_AXE.get(),"Aurorian Steel Axe");
        this.add(TAItems.AURORIAN_STEEL_PICKAXE.get(),"Aurorian Steel Pickaxe");
        this.add(TAItems.AURORIAN_STEEL_SHOVEL.get(),"Aurorian Steel Shovel");
        this.add(TAItems.AURORIAN_STONE_SICKLE.get(),"Aurorian Steel Sickle");
        this.add(TAItems.AURORIAN_STEEL_SWORD.get(),"Aurorian Steel Sword");
        this.add(TAItems.AURORIAN_STEEL_HELMET.get(),"Aurorian Steel Helmet");
        this.add(TAItems.AURORIAN_STEEL_CHESTPLATE.get(),"Aurorian Steel Chestplate");
        this.add(TAItems.AURORIAN_STEEL_LEGGINGS.get(),"Aurorian Steel Leggings");
        this.add(TAItems.AURORIAN_STEEL_BOOTS.get(),"Aurorian Steel Boots");
        this.add(TAItems.AURORIAN_STONE_AXE.get(),"Aurorian Stone Axe");
        this.add(TAItems.AURORIAN_STONE_PICKAXE.get(),"Aurorian Stone Pickaxe");
        this.add(TAItems.AURORIAN_STONE_SHOVEL.get(),"Aurorian Stone Shovel");
        this.add(TAItems.AURORIAN_STONE_SWORD.get(),"Aurorian Stone Sword");
        this.add(TAItems.AURORIANITE_INGOT.get(),"Aurorianite Ingot");
        this.add(TAItems.AURORIANITE_SCRAP.get(),"Aurorianite Scrap");
        this.add(TAItems.AURORIANITE_SWORD.get(), "Aurorianite Sword");
        this.add(TAItems.AURORIANITE_AXE.get(),"Aurorianite Axe");
        this.add(TAItems.AURORIANITE_PICKAXE.get(),"Aurorianite Pickaxe");
        this.add(TAItems.BEPSI.get(),"Bepsi");
        this.add(TAItems.CERULEAN_ARROW.get(),"Cerulean Arrow");
        this.add(TAItems.CERULEAN_INGOT.get(),"Cerulean Ingot");
        this.add(TAItems.CERULEAN_NUGGET.get(),"Cerulean Nugget");
        this.add(TAItems.CERULEAN_HELMET.get(),"Cerulean Helmet");
        this.add(TAItems.CERULEAN_CHESTPLATE.get(),"Cerulean Chestplate");
        this.add(TAItems.CERULEAN_LEGGINGS.get(),"Cerulean Leggings");
        this.add(TAItems.CERULEAN_BOOTS.get(),"Cerulean Boots");
        this.add(TAItems.CERULEAN_SHIELD.get(),"Cerulean Shield");
        this.add(TAItems.COOKED_AURORIAN_PORK.get(),"Cooked Aurorian Pork");
        this.add(TAItems.CRYSTAL.get(),"Crystal");
        this.add(TAItems.CRYSTAL_ARROW.get(),"Crystal Arrow");
        this.add(TAItems.CRYSTALLINE_INGOT.get(),"Crystalline Ingot");
        this.add(TAItems.CRYSTALLINE_SCRAP.get(),"Crystalline Scrap");
        this.add(TAItems.CRYSTALLINE_PICKAXE.get(),"Crystalline Pickaxe");
        this.add(TAItems.CRYSTALLINE_SHIELD.get(),"Crystalline Shield");
        this.add(TAItems.CRYSTALLINE_SWORD.get(),"Crystalline Sword");
        this.add(TAItems.CRYSTALLINE_SPRITE.get(),"Crystalline Sprite");
        this.add(TAItems.CRYSTALLINE_SPRITE_SPAWN_EGG.get(),"Crystalline Sprite Spawn Egg");
        this.add(TAItems.DARK_AMULET.get(),"Dark Amulet");
        this.add(TAItems.DUNGEON_KEEPER_AMULET.get(),"Dungeon Keeper Amulet");
        this.add(TAItems.TROPHY_KEEPER.get(),"Keeper Trophy");
        this.add(TAItems.DISTURBED_HOLLOW_SPAWN_EGG.get(),"Disturbed Hollow Spawn Egg");
        this.add(TAItems.DUNGEON_LOCATOR.get(),"Dungeon Locator");
        this.add(TAItems.KEEPERS_BOW.get(),"Keeper's Bow");
        this.add(TAItems.KNIGHT_HELMET.get(),"Knight Helmet");
        this.add(TAItems.KNIGHT_CHESTPLATE.get(),"Knight Chestplate");
        this.add(TAItems.KNIGHT_LEGGINGS.get(),"Knight Leggings");
        this.add(TAItems.KNIGHT_BOOTS.get(),"Knight Boots");
        this.add(TAItems.LAVENDER.get(),"Lavender");
        this.add(TAItems.LAVENDER_BREAD.get(),"Lavender Bread");
        this.add(TAItems.LAVENDER_TEA.get(),"Lavender Tea");
        this.add(TAItems.LAVENDER_SEEDS.get(),"Lavender Seeds");
        this.add(TAItems.LIVING_DIVINING_ROD.get(),"Living Divining Rod");
        this.add(TAItems.LOCK_PICKS.get(),"Lock Picks");
        this.add(TAItems.MOON_ACOLYTE_SPAWN_EGG.get(),"Moon Acolyte Spawn Egg");
        this.add(TAItems.MOON_QUEEN_SPAWN_EGG.get(),"Moon Queen Spawn Egg");
        this.add(TAItems.MOONSTONE_INGOT.get(),"Moonstone Ingot");
        this.add(TAItems.MOONSTONE_NUGGET.get(),"Moonstone Nugget");
        this.add(TAItems.MOON_SHIELD.get(),"Moon Shield");
        this.add(TAItems.MOONSTONE_AXE.get(),"Moonstone Axe");
        this.add(TAItems.MOONSTONE_HOE.get(),"Moonstone Hoe");
        this.add(TAItems.MOONSTONE_PICKAXE.get(),"Moonstone Pickaxe");
        this.add(TAItems.MOONSTONE_SHOVEL.get(),"Moonstone Shovel");
        this.add(TAItems.MOONSTONE_SWORD.get(),"Moonstone Sword");
        this.add(TAItems.MOONSTONE_SICKLE.get(),"Moonstone Sickle");
        this.add(TAItems.PETUNIA_TEA.get(),"Petunia Tea");
        this.add(TAItems.PLANT_FIBER.get(),"Plant Fiber");
        this.add(TAItems.QUEENS_CHIPPER.get(),"Queen's Chipper");
        this.add(TAItems.RUNESTONE_KEEPER_SPAWN_EGG.get(),"Runestone Keeper Spawn Egg");
        this.add(TAItems.LAVENDER_SEEDY_TEA.get(),"Lavender Seedy Tea");
        this.add(TAItems.SILENT_WOOD_AXE.get(),"Silent Wood Axe");
        this.add(TAItems.SILENT_WOOD_HOE.get(),"Silent Wood Hoe");
        this.add(TAItems.SILENT_WOOD_PICKAXE.get(),"Silent Wood Pickaxe");
        this.add(TAItems.SILENT_WOOD_SHOVEL.get(),"Silent Wood Shovel");
        this.add(TAItems.SILENT_WOOD_SWORD.get(),"Silent Wood Sword");
        this.add(TAItems.SILENT_WOOD_BOW.get(),"Silent Wood Bow");
        this.add(TAItems.SILK_BERRY.get(),"Silk Berry");
        this.add(TAItems.SILK_BERRY_JAM.get(),"Silk Berry Jam");
        this.add(TAItems.SILK_BERRY_JAM_SANDWICH.get(),"Silk Berry Jam Sandwich");
        this.add(TAItems.SILKBERRY_TEA.get(),"Slik Berry Tea");
        this.add(TAItems.SILK_SHROOM_STEW.get(),"Silk Shroom Stew");
        this.add(TAItems.SLEEPING_BLACK_TEA.get(),"Sleeping Black Tea");
        this.add(TAItems.SOULLESS_FLESH.get(),"Soulless Flesh");
        this.add(TAItems.SPECTRAL_SILK.get(),"Spectral Silk");
        this.add(TAItems.SPECTRAL_HELMET.get(),"Spectral Helmet");
        this.add(TAItems.SPECTRAL_CHESTPLATE.get(),"Spectral Chestplate");
        this.add(TAItems.SPECTRAL_LEGGINGS.get(),"Spectral Leggings");
        this.add(TAItems.SPECTRAL_BOOTS.get(),"Spectral Boots");
        this.add(TAItems.SPIDER_MOTHER_SPAWN_EGG.get(),"Spider Mother Spawn Egg");
        this.add(TAItems.SPIDERLING_SPAWN_EGG.get(),"Spiderling Spawn Egg");
        this.add(TAItems.SPIRIT_SPAWN_EGG.get(),"Spirit Spawn Egg");
        this.add(TAItems.SPIKED_CHESTPLATE.get(),"Spiked Chestplate");
        this.add(TAItems.STICKY_SPIKER.get(),"Sticky Spiker");
        this.add(TAItems.TEA_CUP.get(),"Tea Cup");
        this.add(TAItems.TROPHY_MOON_QUEEN.get(),"Moon Queen Trophy");
        this.add(TAItems.TROPHY_SPIDER_MOTHER.get(),"Spider Mother Trophy");
        this.add(TAItems.UMBRA_INGOT.get(),"Umbra Ingot");
        this.add(TAItems.UMBRA_SCRAP.get(),"Umbra Scrap");
        this.add(TAItems.UMBRA_SHIELD.get(),"Umbra Shield");
        this.add(TAItems.UMBRA_SWORD.get(),"Umbra Sword");
        this.add(TAItems.UMBRA_PICKAXE.get(),"Umbra Pickaxe");
        this.add(TAItems.UNDEAD_KNIGHT_SPAWN_EGG.get(),"Undead Knight Spawn Egg");
        this.add(TAItems.WEBBING.get(),"Webbing");
        this.add(TAItems.WEEPING_WILLOW_SAP.get(),"Weeping Willow Sap");

        //MOD ENTITY
        this.add(TAEntityTypes.AURORIAN_RABBIT.get(), "Aurorian Rabbit");
        this.add(TAEntityTypes.AURORIAN_SHEEP.get(), "Aurorian Sheep");
        this.add(TAEntityTypes.AURORIAN_PIG.get(), "Aurorian Pig");
        this.add(TAEntityTypes.AURORIAN_SLIME.get(), "Aurorian Slime");
        this.add(TAEntityTypes.DISTURBED_HOLLOW.get(), "Disturbed Hollow");
        this.add(TAEntityTypes.UNDEAD_KNIGHT.get(), "Undead Knight");
        this.add(TAEntityTypes.SPIRIT.get(), "Spirit");
        this.add(TAEntityTypes.MOON_ACOLYTE.get(), "Moon Acolyte");
        this.add(TAEntityTypes.SPIDERLING.get(), "Spiderling");
        this.add(TAEntityTypes.CRYSTALLINE_SPRITE.get(), "Crystalline Sprite");
        this.add(TAEntityTypes.RUNESTONE_KEEPER.get(), "Runestone Keeper");
        this.add(TAEntityTypes.SPIDER_MOTHER.get(), "Spider Mother");
        this.add(TAEntityTypes.MOON_QUEEN.get(), "Moon Queen");

        //MOD ENCHANTMENT
        this.add(TAEnchantments.LIGHTNING_RESISTANCE.get(), "Lightning Resistance");
        this.add(TAEnchantments.LIGHTNING_DAMAGE.get(), "Lightning");
        this.add(TAEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "Negates damage that would have been done by lightning enchantment");
        this.add(TAEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId()+ ".desc", "Does extra damage depending on how much armor the target is wearing");
    }

}