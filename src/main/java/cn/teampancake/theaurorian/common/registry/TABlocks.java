package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.*;
import cn.teampancake.theaurorian.common.blocks.state.TABlockSetType;
import cn.teampancake.theaurorian.common.blocks.state.TAWoodType;
import cn.teampancake.theaurorian.common.level.feature.TAConfiguredFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cn.teampancake.theaurorian.common.utils.TABlockRegUtils.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

@SuppressWarnings("SpellCheckingInspection")
public class TABlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AurorianMod.MOD_ID);
    public static final RegistryObject<LiquidBlock> MOLTEN_AURORIAN_STEEL = BLOCKS.register("molten_aurorian_steel", () -> new LiquidBlock(TAFluids.MOLTEN_AURORIAN_STEEL_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_CERULEAN = BLOCKS.register("molten_cerulean", () -> new LiquidBlock(TAFluids.MOLTEN_CERULEAN_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_MOONSTONE = BLOCKS.register("molten_moonstone", () -> new LiquidBlock(TAFluids.MOLTEN_MOONSTONE_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOON_WATER = BLOCKS.register("moon_water", () -> new LiquidBlock(TAFluids.MOON_WATER_STILL, copy(Blocks.WATER)));
    public static final RegistryObject<Block> AURORIAN_DIRT = normal("aurorian_dirt", copy(Blocks.DIRT));
    public static final RegistryObject<Block> AURORIAN_STONE = normal("aurorian_stone", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICKS = normal("aurorian_stone_bricks", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE = normal("aurorian_cobblestone", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_GRANITE = normal("aurorian_granite", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_DIORITE = normal("aurorian_diorite", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_ANDESITE = normal("aurorian_andesite", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_BARRIER_STONE = normal("aurorian_barrier_stone", copy(Blocks.BEDROCK));
    public static final RegistryObject<Block> AURORIAN_COAL_ORE = ore("aurorian_coal_ore", defaultStoneProperties(3.0F));
    public static final RegistryObject<Block> AURORIAN_GRASS_BLOCK = register("aurorian_grass_block", () -> new AurorianGrassBlock(copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> LIGHT_AURORIAN_GRASS_BLOCK = register("light_aurorian_grass_block", () -> new AurorianGrassBlock(copy(Blocks.GRASS_BLOCK).lightLevel(s -> 2)));
    public static final RegistryObject<Block> RED_AURORIAN_GRASS_BLOCK = register("red_aurorian_grass_block", () -> new AurorianGrassBlock(copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> AURORIAN_FARM_TILE = register("aurorian_farm_tile", () -> new AurorianFarmTile(copy(Blocks.FARMLAND)));
    public static final RegistryObject<Block> MOON_GLASS = register("moon_glass", () -> new GlassBlock(copy(Blocks.GLASS)));
    public static final RegistryObject<Block> MOON_GLASS_PANE = register("moon_glass_pane", () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)));
    public static final RegistryObject<Block> AURORIAN_GLASS = register("aurorian_glass", () -> new GlassBlock(copy(Blocks.GLASS)));
    public static final RegistryObject<Block> AURORIAN_GLASS_PANE = register("aurorian_glass_pane", () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)));
    public static final RegistryObject<Block> DARK_STONE_GLASS = register("dark_stone_glass", () -> new GlassBlock(copy(Blocks.GLASS)));
    public static final RegistryObject<Block> DARK_STONE_GLASS_PANE = register("dark_stone_glass_pane", () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)));
    public static final RegistryObject<Block> AURORIAN_GRASS = register("aurorian_grass", () -> new TAPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> AURORIAN_GRASS_LIGHT = register("aurorian_grass_light", () -> new TAPlantBlock(copy(Blocks.GRASS).lightLevel(s -> 2), LIGHT_AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> AURORIAN_WATER_GRASS = register("aurorian_water_grass", () -> new TALightPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> AURORIAN_LILY_PAD = register("aurorian_lily_pad", () -> new AurorianWaterSurfacePlant(Block.box(0.5D, 0.0D, 0.5D, 15.5D, 0.5D, 15.5D)));
    public static final RegistryObject<Block> AURORIAN_WATER_MUSHROOM = register("aurorian_water_mushroom", () -> new AurorianWaterSurfacePlant(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.5D, 12.0D)));
    public static final RegistryObject<Block> AURORIAN_FURNACE = register("aurorian_furnace", () -> new AurorianFurnace(defaultStoneProperties(3.5F)));
    public static final RegistryObject<Block> AURORIAN_FURNACE_CHIMNEY = register("aurorian_furnace_chimney", () -> new AurorianFurnaceChimney(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> AURORIAN_PORTAL = BLOCKS.register("aurorian_portal", () -> new AurorianPortal(copy(Blocks.NETHER_PORTAL)));
    public static final RegistryObject<Block> AURORIAN_PORTAL_FRAME_BRICKS = register("aurorian_portal_frame_bricks", () -> new AurorianPortalFrame(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> URN = register("urn", () -> new UrnBlock(of().mapColor(MapColor.STONE).instabreak().sound(SoundType.GLASS)));
    public static final RegistryObject<Block> AURORIAN_FLOWER_1 = register("aurorian_flower_1", TAFlowerBlock::new);
    public static final RegistryObject<Block> AURORIAN_FLOWER_2 = register("aurorian_flower_2", TAFlowerBlock::new);
    public static final RegistryObject<Block> AURORIAN_FLOWER_3 = register("aurorian_flower_3", TAFlowerBlock::new);
    public static final RegistryObject<Block> EQUINOX_FLOWER = register("equinox_flower", TAFlowerBlock::new);
    public static final RegistryObject<Block> WICK_GRASS = BLOCKS.register("wick_grass", WickGrass::new);
    public static final RegistryObject<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", BlueBerryBush::new);
    public static final RegistryObject<Block> LAVENDER_CROP = BLOCKS.register("lavender_crop", () -> new TACropBlock(copy(Blocks.GRASS), TAItems.LAVENDER_SEEDS));
    public static final RegistryObject<Block> SILK_BERRY_CROP = BLOCKS.register("silk_berry_crop", () -> new TACropBlock(copy(Blocks.GRASS), TAItems.SILK_BERRY));
    public static final RegistryObject<Block> LAVENDER_PLANT = register("lavender_plant", () -> new TAPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> PETUNIA_PLANT = register("petunia_plant", () -> new TAPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> TALL_AURORIAN_GRASS = register("tall_aurorian_grass", () -> new TADoublePlantBlock(copy(Blocks.TALL_GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> TALL_LAVENDER_PLANT = register("tall_lavender_plant", () -> new TADoublePlantBlock(copy(Blocks.TALL_GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> TALL_AURORIAN_WATER_GRASS = register("tall_aurorian_water_grass", () -> new TALightDoublePlantBlock(copy(Blocks.TALL_GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE = normal("smooth_aurorian_peridotite", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE = normal("aurorian_peridotite", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> MOONSTONE_ORE = ore("moonstone_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> CERULEAN_ORE = ore("cerulean_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> GEODE_ORE = ore("geode_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> RUNE_STONE = normal("rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_RUNE_STONE = normal("smooth_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_RUNE_STONE = normal("chiseled_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_CASTLE_RUNE_STONE = normal("aurorian_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_STEEL_CASTLE_RUNE_STONE = normal("aurorian_steel_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> CERULEAN_CASTLE_RUNE_STONE = normal("cerulean_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> CRYSTALLINE_CASTLE_RUNE_STONE = normal("crystalline_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_CASTLE_RUNE_STONE = normal("moon_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> TRANSPARENT_RUNE_STONE = normal("transparent_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_CASTLE_RUNE_STONE = normal("umbra_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE = normal("luminous_aurorian_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE = normal("luminous_aurorian_steel_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE = normal("luminous_cerulean_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE = normal("luminous_crystalline_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_MOON_CASTLE_RUNE_STONE = normal("luminous_moon_castle_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> RUNE_STONE_PILLAR = register("rune_stone_pillar", () -> new RotatedPillarBlock(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> DARK_STONE_PILLAR = register("dark_stone_pillar", () -> new RotatedPillarBlock(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_PILLAR = register("moon_temple_pillar", () -> new RotatedPillarBlock(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_BRICKS = normal("moon_temple_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_BRICKS = normal("dark_stone_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_FANCY = normal("dark_stone_fancy", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_LAYERS = normal("dark_stone_layers", breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_DARK_STONE_BRICKS = normal("smooth_dark_stone_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_DARK_STONE_BRICKS = normal("chiseled_dark_stone_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_MOON_TEMPLE_BRICKS = normal("smooth_moon_temple_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_MOON_TEMPLE_BRICKS = normal("chiseled_moon_temple_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> RUNE_STONE_LAMP = normal("rune_stone_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> DARK_STONE_LAMP = normal("dark_stone_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> MOON_TEMPLE_LAMP = normal("moon_temple_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> VOID_STONE = normal("void_stone", breakWithQueenPickaxe().lightLevel(s -> 7));
    public static final RegistryObject<Block> RUNE_CRYSTAL = normal("rune_crystal", breakWithQueenPickaxe().lightLevel(s -> 3));
    public static final RegistryObject<Block> MYSTICAL_BARRIER = register("mystical_barrier", () -> new MysticalBarrier(copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> RUNE_STONE_BARS = register("rune_stone_bars", () -> new IronBarsBlock(breakWithQueenPickaxe().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> DARK_STOME_BARS = register("dark_stone_bars", () -> new IronBarsBlock(breakWithQueenPickaxe().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> MOON_TEMPLE_BARS = register("moon_temple_bars", () -> new IronBarsBlock(breakWithQueenPickaxe().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> RUNE_STONE_GATE = register("rune_stone_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_GATE = register("moon_temple_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> DARK_STONE_GATE = register("dark_stone_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> RUNE_STONE_LOOT_GATE = register("rune_stone_loot_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_CELL_GATE = register("moon_temple_cell_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> DARK_STONE_GATE_KEYHOLE = register("dark_stone_gate_keyhole", () -> new DungeonStoneGateKeyhole(TAItems.DARK_STONE_KEY, DARK_STONE_GATE));
    public static final RegistryObject<Block> MOON_TEMPLE_GATE_KEYHOLE = register("moon_temple_gate_keyhole", () -> new DungeonStoneGateKeyhole(TAItems.MOON_TEMPLE_KEY, MOON_TEMPLE_GATE));
    public static final RegistryObject<Block> RUNE_STONE_GATE_KEYHOLE = register("rune_stone_gate_keyhole", () -> new DungeonStoneGateKeyhole(TAItems.RUNE_STONE_KEY, RUNE_STONE_GATE, Boolean.FALSE));
    public static final RegistryObject<Block> RUNE_STONE_LOOT_GATE_KEYHOLE = register("rune_stone_loot_gate_keyhole", () -> new DungeonStoneGateKeyhole(TAItems.RUNE_STONE_LOOT_KEY, RUNE_STONE_LOOT_GATE));
    public static final RegistryObject<Block> MOON_TEMPLE_CELL_GATE_KEYHOLE = register("moon_temple_cell_gate_keyhole", () -> new DungeonStoneGateKeyhole(TAItems.MOON_TEMPLE_CELL_KEY, MOON_TEMPLE_CELL_GATE));
    public static final RegistryObject<Block> CERULEAN_BLOCK = normal("cerulean_block", defaultStoneProperties(3.0F).mapColor(MapColor.METAL));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = normal("moonstone_block", defaultStoneProperties(3.0F).mapColor(MapColor.METAL));
    public static final RegistryObject<Block> AURORIAN_COAL_BLOCK = normal("aurorian_coal_block", defaultStoneProperties(5.0F).mapColor(MapColor.METAL));
    public static final RegistryObject<Block> AURORIAN_STEEL_BLOCK = normal("aurorian_steel_block", defaultStoneProperties(5.0F).mapColor(MapColor.METAL));
    public static final RegistryObject<Block> CERULEAN_CLUSTER = register("cerulean_cluster", () -> new TAClusterBlock(7, 3, copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> LARGE_CERULEAN_BUD = register("large_cerulean_bud", () -> new TAClusterBlock(5, 3, copy(Blocks.LARGE_AMETHYST_BUD)));
    public static final RegistryObject<Block> MEDIUM_CERULEAN_BUD = register("medium_cerulean_bud", () -> new TAClusterBlock(4, 3, copy(Blocks.MEDIUM_AMETHYST_BUD)));
    public static final RegistryObject<Block> SMALL_CERULEAN_BUD = register("small_cerulean_bud", () -> new TAClusterBlock(3, 4, copy(Blocks.SMALL_AMETHYST_BUD)));
    public static final RegistryObject<Block> MOONSTONE_CLUSTER = register("moonstone_cluster", () -> new TAClusterBlock(7, 3, copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> LARGE_MOONSTONE_BUD = register("large_moonstone_bud", () -> new TAClusterBlock(5, 3, copy(Blocks.LARGE_AMETHYST_BUD)));
    public static final RegistryObject<Block> MEDIUM_MOONSTONE_BUD = register("medium_moonstone_bud", () -> new TAClusterBlock(4, 3, copy(Blocks.MEDIUM_AMETHYST_BUD)));
    public static final RegistryObject<Block> SMALL_MOONSTONE_BUD = register("small_moonstone_bud", () -> new TAClusterBlock(3, 4, copy(Blocks.SMALL_AMETHYST_BUD)));
    public static final RegistryObject<Block> INDIGO_MUSHROOM = register("indigo_mushroom", () -> new IndigoMushroomBlock(copy(Blocks.BROWN_MUSHROOM), TAConfiguredFeatures.HUGE_INDIGO_MUSHROOM));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_BLOCK = register("indigo_mushroom_block", () -> new IndigoMushroom(copy(Blocks.BROWN_MUSHROOM_BLOCK).destroyTime(1.0F)));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_STEM = register("indigo_mushroom_stem", () -> new HugeMushroomBlock(copy(Blocks.MUSHROOM_STEM).destroyTime(1.0F)));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_CRYSTAL = normal("indigo_mushroom_crystal", of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.GLASS).lightLevel(s -> 1));
    public static final RegistryObject<Block> MOONLIGHT_FORGE = register("moonlight_forge", () -> new MoonlightForge(defaultStoneProperties((2.0F)).mapColor(MapColor.METAL).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> MOON_GEM = normal("moon_gem", defaultStoneProperties(2.0F).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.METAL).noOcclusion());
    public static final RegistryObject<Block> MOON_SAND = normal("moon_sand", copy(Blocks.SAND));
    public static final RegistryObject<Block> MOON_SAND_RIVER = normal("moon_sand_river", copy(Blocks.SAND));
    public static final RegistryObject<Block> MOON_SAND_STONE_1 = normal("moon_sand_stone_1", copy(Blocks.SANDSTONE));
    public static final RegistryObject<Block> MOON_SAND_STONE_2 = normal("moon_sand_stone_2", copy(Blocks.SANDSTONE));
    public static final RegistryObject<Block> MOON_SAND_STONE_3 = normal("moon_sand_stone_3", copy(Blocks.SANDSTONE));
    public static final RegistryObject<Block> BRIGHT_MOON_SAND = normal("bright_moon_sand", copy(Blocks.SAND));
    public static final RegistryObject<Block> BRIGHT_MOON_SANDSTONE = normal("bright_moon_sandstone", copy(Blocks.SANDSTONE));
    public static final RegistryObject<Block> MOON_TORCH = register("moon_torch", () -> new TorchBlock(copy(Blocks.TORCH), ParticleTypes.CLOUD));
    public static final RegistryObject<Block> MOON_WALL_TORCH = BLOCKS.register("moon_wall_torch", () -> new WallTorchBlock(copy(Blocks.WALL_TORCH), ParticleTypes.CLOUD));
    public static final RegistryObject<Block> SCRAPPER = register("scrapper", () -> new Scrapper(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> UMBRA_STONE = normal("umbra_stone", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED = normal("umbra_stone_cracked", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_TILES = normal("umbra_stone_roof_tiles", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SILENT_BUSH_LEAVES = register("silent_bush_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final RegistryObject<Block> SILENT_TREE_LEAVES = register("silent_tree_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final RegistryObject<Block> SILENT_TREE_LOG = register("silent_tree_log", () -> Blocks.log(MapColor.COLOR_BLUE, MapColor.COLOR_BLACK));
    public static final RegistryObject<Block> SILENT_TREE_PLANKS = normal("silent_tree_planks", copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> SILENT_TREE_WOOD = wood("silent_tree_wood", MapColor.COLOR_BLUE, 2.0F);
    public static final RegistryObject<Block> SILENT_TREE_SAPLING = register("silent_tree_sapling", () -> new SilentTreeSapling(copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> SILENT_WOOD_TORCH = register("silent_wood_torch", () -> new TorchBlock(copy(Blocks.TORCH), ParticleTypes.FLAME));
    public static final RegistryObject<Block> SILENT_WOOD_WALL_TORCH = BLOCKS.register("silent_wood_wall_torch", () -> new WallTorchBlock(copy(Blocks.WALL_TORCH), ParticleTypes.FLAME));
    public static final RegistryObject<Block> SILENT_WOOD_CRAFTING_TABLE = register("silent_wood_crafting_table", () -> new CraftingTableBlock(copy(Blocks.CRAFTING_TABLE)));
    public static final RegistryObject<Block> SILENT_WOOD_LADDER = register("silent_wood_ladder", () -> new LadderBlock(copy(Blocks.LADDER)));
    public static final RegistryObject<Block> WEEPING_WILLOW_LEAVES = register("weeping_willow_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final RegistryObject<Block> WEEPING_WILLOW_LOG = register("weeping_willow_log", () -> Blocks.log(MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_BLACK));
    public static final RegistryObject<Block> WEEPING_WILLOW_PLANKS = normal("weeping_willow_planks", copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> WEEPING_WILLOW_WOOD = wood("weeping_willow_wood", MapColor.COLOR_LIGHT_GRAY, 2.0F);

    /**
     * Potted Flower Blocks
     */
    public static final RegistryObject<Block> POTTED_AURORIAN_FLOWER_1 = flowerPot(AURORIAN_FLOWER_1.getId().getPath(), AURORIAN_FLOWER_1);
    public static final RegistryObject<Block> POTTED_AURORIAN_FLOWER_2 = flowerPot(AURORIAN_FLOWER_2.getId().getPath(), AURORIAN_FLOWER_2);
    public static final RegistryObject<Block> POTTED_AURORIAN_FLOWER_3 = flowerPot(AURORIAN_FLOWER_3.getId().getPath(), AURORIAN_FLOWER_3);
    public static final RegistryObject<Block> POTTED_EQUINOX_FLOWER = flowerPot(EQUINOX_FLOWER.getId().getPath(), EQUINOX_FLOWER);
    public static final RegistryObject<Block> POTTED_WICK_GRASS = flowerPot(WICK_GRASS.getId().getPath(), WICK_GRASS);
    public static final RegistryObject<Block> POTTED_LAVENDER_PLANT = flowerPot(LAVENDER_PLANT.getId().getPath(), LAVENDER_PLANT);
    public static final RegistryObject<Block> POTTED_PETUNIA_PLANT = flowerPot(PETUNIA_PLANT.getId().getPath(), PETUNIA_PLANT);
    public static final RegistryObject<Block> POTTED_AURORIAN_GRASS = flowerPot(AURORIAN_GRASS.getId().getPath(), AURORIAN_GRASS);
    public static final RegistryObject<Block> POTTED_AURORIAN_GRASS_LIGHT = flowerPot(AURORIAN_GRASS_LIGHT.getId().getPath(), AURORIAN_GRASS_LIGHT);
    public static final RegistryObject<Block> POTTED_SILENT_TREE_SAPLING = flowerPot(SILENT_TREE_SAPLING.getId().getPath(), SILENT_TREE_SAPLING);

    /**
     * Vertical Stair Blocks
     */
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STONE_STAIRS = verticalStair("vertical_aurorian_stone_stairs", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STONE_BRICK_STAIRS = verticalStair("vertical_aurorian_stone_brick_stairs", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_COBBLESTONE_STAIRS = verticalStair("vertical_aurorian_cobblestone_stairs", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_GRANITE_STAIRS = verticalStair("vertical_aurorian_granite_stairs", AURORIAN_GRANITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_DIORITE_STAIRS = verticalStair("vertical_aurorian_diorite_stairs", AURORIAN_DIORITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_ANDESITE_STAIRS = verticalStair("vertical_aurorian_andesite_stairs", AURORIAN_ANDESITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_SILENT_WOOD_STAIRS = verticalStair("vertical_silent_wood_stairs", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> VERTICAL_RUNE_STONE_STAIRS = verticalStair("vertical_rune_stone_stairs", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_RUNE_STONE_STAIRS = verticalStair("vertical_smooth_rune_stone_stairs", SMOOTH_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_RUNE_STONE_STAIRS = verticalStair("vertical_chiseled_rune_stone_stairs", CHISELED_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_AURORIAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_aurorian_steel_castle_rune_stone_stairs", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CERULEAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_MOON_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_aurorian_steel_castle_rune_stone_stairs", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_TRANSPARENT_RUNE_STONE_STAIRS = verticalStair("vertical_transparent_rune_stone_stairs", TRANSPARENT_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_UMBRA_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_umbra_castle_rune_stone_stairs", UMBRA_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_dark_stone_brick_stairs", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_smooth_dark_stone_brick_stairs", SMOOTH_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_chiseled_dark_stone_brick_stairs", CHISELED_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_moon_temple_brick_stairs", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_smooth_moon_temple_brick_stairs", SMOOTH_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_chiseled_moon_temple_brick_stairs", CHISELED_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_STAIRS = verticalStair("vertical_umbra_stone_stairs", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_CRACKED_STAIRS = verticalStair("vertical_umbra_stone_cracked_stairs", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_ROOF_STAIRS = verticalStair("vertical_umbra_stone_roof_stairs", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_WEEPING_WILLOW_STAIRS = verticalStair("vertical_weeping_willow_stairs", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_PERIDOTITE_STAIRS = verticalStair("vertical_aurorian_peridotite_stairs", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_STAIRS = verticalStair("vertical_smooth_aurorian_peridotite_stairs", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));

    /**
     * Vertical Slab Blocks
     */
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STONE_SLAB = verticalSlab("vertical_aurorian_stone_slab", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STONE_BRICK_SLAB = verticalSlab("vertical_aurorian_stone_brick_slab", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_COBBLESTONE_SLAB = verticalSlab("vertical_aurorian_cobblestone_slab", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_GRANITE_SLAB = verticalSlab("vertical_aurorian_granite_slab", AURORIAN_GRANITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_DIORITE_SLAB = verticalSlab("vertical_aurorian_diorite_slab", AURORIAN_DIORITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_ANDESITE_SLAB = verticalSlab("vertical_aurorian_andesite_slab", AURORIAN_ANDESITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> VERTICAL_SILENT_WOOD_SLAB = verticalSlab("vertical_silent_wood_slab", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> VERTICAL_RUNE_STONE_SLAB = verticalSlab("vertical_rune_stone_slab", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_RUNE_STONE_SLAB = verticalSlab("vertical_smooth_rune_stone_slab", SMOOTH_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_RUNE_STONE_SLAB = verticalSlab("vertical_chiseled_rune_stone_slab", CHISELED_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_AURORIAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_aurorian_steel_castle_rune_stone_slab", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CERULEAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_MOON_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_aurorian_steel_castle_rune_stone_slab", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_TRANSPARENT_RUNE_STONE_SLAB = verticalSlab("vertical_transparent_rune_stone_slab", TRANSPARENT_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_UMBRA_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_umbra_castle_rune_stone_slab", UMBRA_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_dark_stone_brick_slab", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_smooth_dark_stone_brick_slab", SMOOTH_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_chiseled_dark_stone_brick_slab", CHISELED_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_moon_temple_brick_slab", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_smooth_moon_temple_brick_slab", SMOOTH_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_CHISELED_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_chiseled_moon_temple_brick_slab", CHISELED_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_SLAB = verticalSlab("vertical_umbra_stone_slab", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_CRACKED_SLAB = verticalSlab("vertical_umbra_stone_cracked_slab", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_UMBRA_STONE_ROOF_SLAB = verticalSlab("vertical_umbra_stone_roof_slab", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_WEEPING_WILLOW_SLAB = verticalSlab("vertical_weeping_willow_slab", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> VERTICAL_AURORIAN_PERIDOTITE_SLAB = verticalSlab("vertical_aurorian_peridotite_slab", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_SLAB = verticalSlab("vertical_smooth_aurorian_peridotite_slab", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));

    /**
     * Pressure Plate Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_PRESSURE_PLATE = pressurePlate("silent_wood_pressure_plate", SILENT_TREE_PLANKS, true, copy(Blocks.OAK_PLANKS), TABlockSetType.SILENT_WOOD);
    public static final RegistryObject<Block> WEEPING_WILLOW_PRESSURE_PLATE = pressurePlate("weeping_willow_pressure_plate", WEEPING_WILLOW_PLANKS, true, copy(Blocks.OAK_PLANKS), TABlockSetType.WEEPING_WILLOW);

    /**
     * Fence Gate Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_FENCE_GATE = fenceGate("silent_wood_fence_gate", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS), TAWoodType.SILENT_WOOD);
    public static final RegistryObject<Block> WEEPING_WILLOW_FENCE_GATE = fenceGate("weeping_willow_fence_gate", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS), TAWoodType.WEEPING_WILLOW);

    /**
     * Trapdoor Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_TRAPDOOR = trapdoor("silent_wood_trapdoor", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS), TABlockSetType.SILENT_WOOD);
    public static final RegistryObject<Block> WEEPING_WILLOW_TRAPDOOR = trapdoor("weeping_willow_trapdoor", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS), TABlockSetType.WEEPING_WILLOW);

    /**
     * Button Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_BUTTON = button("silent_wood_button", SILENT_TREE_PLANKS, true, TABlockSetType.SILENT_WOOD);
    public static final RegistryObject<Block> WEEPING_WILLOW_BUTTON = button("weeping_willow_button", WEEPING_WILLOW_PLANKS, true, TABlockSetType.WEEPING_WILLOW);

    /**
     * Stair Blocks
     */
    public static final RegistryObject<Block> AURORIAN_STONE_STAIRS = stair("aurorian_stone_stairs", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICK_STAIRS = stair("aurorian_stone_brick_stairs", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE_STAIRS = stair("aurorian_cobblestone_stairs", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_GRANITE_STAIRS = stair("aurorian_granite_stairs", AURORIAN_GRANITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_DIORITE_STAIRS = stair("aurorian_diorite_stairs", AURORIAN_DIORITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_ANDESITE_STAIRS = stair("aurorian_andesite_stairs", AURORIAN_ANDESITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> SILENT_WOOD_STAIRS = stair("silent_wood_stairs", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> RUNE_STONE_STAIRS = stair("rune_stone_stairs", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_RUNE_STONE_STAIRS = stair("smooth_rune_stone_stairs", SMOOTH_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_RUNE_STONE_STAIRS = stair("chiseled_rune_stone_stairs", CHISELED_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_CASTLE_RUNE_STONE_STAIRS = stair("aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS = stair("aurorian_steel_castle_rune_stone_stairs", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CERULEAN_CASTLE_RUNE_STONE_STAIRS = stair("cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = stair("crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_CASTLE_RUNE_STONE_STAIRS = stair("moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS = stair("luminous_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_STAIRS = stair("luminous_aurorian_steel_castle_rune_stone_stairs", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS = stair("luminous_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = stair("luminous_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS = stair("luminous_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> TRANSPARENT_RUNE_STONE_STAIRS = stair("transparent_rune_stone_stairs", TRANSPARENT_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_CASTLE_RUNE_STONE_STAIRS = stair("umbra_castle_rune_stone_stairs", UMBRA_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_BRICK_STAIRS = stair("dark_stone_brick_stairs", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_DARK_STONE_BRICK_STAIRS = stair("smooth_dark_stone_brick_stairs", SMOOTH_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_DARK_STONE_BRICK_STAIRS = stair("chiseled_dark_stone_brick_stairs", CHISELED_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_TEMPLE_BRICK_STAIRS = stair("moon_temple_brick_stairs", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_MOON_TEMPLE_BRICK_STAIRS = stair("smooth_moon_temple_brick_stairs", SMOOTH_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_MOON_TEMPLE_BRICK_STAIRS = stair("chiseled_moon_temple_brick_stairs", CHISELED_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_STONE_STAIRS = stair("umbra_stone_stairs", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED_STAIRS = stair("umbra_stone_cracked_stairs", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_STAIRS = stair("umbra_stone_roof_stairs", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> WEEPING_WILLOW_STAIRS = stair("weeping_willow_stairs", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE_STAIRS = stair("aurorian_peridotite_stairs", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE_STAIRS = stair("smooth_aurorian_peridotite_stairs", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));

    /**
     * Fence Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_FENCE = fence("silent_wood_fence", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> WEEPING_WILLOW_FENCE = fence("weeping_willow_fence", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));

    /**
     * Door Blocks
     */
    public static final RegistryObject<Block> SILENT_WOOD_DOOR = door("silent_wood_door", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS), TABlockSetType.SILENT_WOOD);
    public static final RegistryObject<Block> WEEPING_WILLOW_DOOR = door("weeping_willow_door", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS), TABlockSetType.WEEPING_WILLOW);

    /**
     * Slab Blocks
     */
    public static final RegistryObject<Block> AURORIAN_STONE_SLAB = slab("aurorian_stone_slab", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICK_SLAB = slab("aurorian_stone_brick_slab", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE_SLAB = slab("aurorian_cobblestone_slab", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_GRANITE_SLAB = slab("aurorian_granite_slab", AURORIAN_GRANITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_DIORITE_SLAB = slab("aurorian_diorite_slab", AURORIAN_DIORITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_ANDESITE_SLAB = slab("aurorian_andesite_slab", AURORIAN_ANDESITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> SILENT_WOOD_SLAB = slab("silent_wood_slab", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> RUNE_STONE_SLAB = slab("rune_stone_slab", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_RUNE_STONE_SLAB = slab("smooth_rune_stone_slab", SMOOTH_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_RUNE_STONE_SLAB = slab("chiseled_rune_stone_slab", CHISELED_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_CASTLE_RUNE_STONE_SLAB = slab("aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB = slab("aurorian_steel_castle_rune_stone_slab", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CERULEAN_CASTLE_RUNE_STONE_SLAB = slab("cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = slab("crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_CASTLE_RUNE_STONE_SLAB = slab("moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB = slab("luminous_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_SLAB = slab("luminous_aurorian_steel_castle_rune_stone_slab", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB = slab("luminous_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = slab("luminous_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB = slab("luminous_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> TRANSPARENT_RUNE_STONE_SLAB = slab("transparent_rune_stone_slab", TRANSPARENT_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_CASTLE_RUNE_STONE_SLAB = slab("umbra_castle_rune_stone_slab", UMBRA_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_BRICK_SLAB = slab("dark_stone_brick_slab", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_DARK_STONE_BRICK_SLAB = slab("smooth_dark_stone_brick_slab", SMOOTH_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_DARK_STONE_BRICK_SLAB = slab("chiseled_dark_stone_brick_slab", CHISELED_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_TEMPLE_BRICK_SLAB = slab("moon_temple_brick_slab", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_MOON_TEMPLE_BRICK_SLAB = slab("smooth_moon_temple_brick_slab", SMOOTH_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_MOON_TEMPLE_BRICK_SLAB = slab("chiseled_moon_temple_brick_slab", CHISELED_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_STONE_SLAB = slab("umbra_stone_slab", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED_SLAB = slab("umbra_stone_cracked_slab", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_SLAB = slab("umbra_stone_roof_slab", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> WEEPING_WILLOW_SLAB = slab("weeping_willow_slab", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE_SLAB = slab("aurorian_peridotite_slab", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE_SLAB = slab("smooth_aurorian_peridotite_slab", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));

    /**
     * Wall Blocks
     */
    public static final RegistryObject<Block> AURORIAN_STONE_WALL = wall("aurorian_stone_wall", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICK_WALL = wall("aurorian_stone_brick_wall", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE_WALL = wall("aurorian_cobblestone_wall", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_GRANITE_WALL = wall("aurorian_granite_wall", AURORIAN_GRANITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_DIORITE_WALL = wall("aurorian_diorite_wall", AURORIAN_DIORITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_ANDESITE_WALL = wall("aurorian_andesite_wall", AURORIAN_ANDESITE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> RUNE_STONE_WALL = wall("rune_stone_wall", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_RUNE_STONE_WALL = wall("smooth_rune_stone_wall", SMOOTH_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_RUNE_STONE_WALL = wall("chiseled_rune_stone_wall", CHISELED_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_CASTLE_RUNE_STONE_WALL = wall("aurorian_castle_rune_stone_wall", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL = wall("aurorian_steel_castle_rune_stone_wall", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CERULEAN_CASTLE_RUNE_STONE_WALL = wall("cerulean_castle_rune_stone_wall", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CRYSTALLINE_CASTLE_RUNE_STONE_WALL = wall("crystalline_castle_rune_stone_wall", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_CASTLE_RUNE_STONE_WALL = wall("moon_castle_rune_stone_wall", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_WALL = wall("luminous_aurorian_castle_rune_stone_wall", AURORIAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE_WALL = wall("luminous_aurorian_steel_castle_rune_stone_wall", AURORIAN_STEEL_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_WALL = wall("luminous_cerulean_castle_rune_stone_wall", CERULEAN_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_WALL = wall("luminous_crystalline_castle_rune_stone_wall", CRYSTALLINE_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> LUMINOUS_MOON_CASTLE_RUNE_STONE_WALL = wall("luminous_moon_castle_rune_stone_wall", MOON_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> TRANSPARENT_RUNE_STONE_WALL = wall("transparent_rune_stone_wall", TRANSPARENT_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_CASTLE_RUNE_STONE_WALL = wall("umbra_castle_rune_stone_wall", UMBRA_CASTLE_RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_BRICK_WALL = wall("dark_stone_brick_wall", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_DARK_STONE_BRICK_WALL = wall("smooth_dark_stone_brick_wall", SMOOTH_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_DARK_STONE_BRICK_WALL = wall("chiseled_dark_stone_brick_wall", CHISELED_DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_TEMPLE_BRICK_WALL = wall("moon_temple_brick_wall", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_MOON_TEMPLE_BRICK_WALL = wall("smooth_moon_temple_brick_wall", SMOOTH_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> CHISELED_MOON_TEMPLE_BRICK_WALL = wall("chiseled_moon_temple_brick_wall", CHISELED_MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_STONE_WALL = wall("umbra_stone_wall", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED_WALL = wall("umbra_stone_cracked_wall", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_WALL = wall("umbra_stone_roof_wall", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE_WALL = wall("aurorian_peridotite_wall", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE_WALL = wall("smooth_aurorian_peridotite_wall", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));

    private static BlockBehaviour.Properties defaultStoneProperties(float destroyTime) {
        return of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().strength(destroyTime, (destroyTime * 5.0F));
    }

    @SuppressWarnings("deprecation")
    public static BlockBehaviour.Properties breakWithQueenPickaxe() {
        return of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                .strength(Byte.MAX_VALUE, Blocks.BEDROCK.getExplosionResistance());
    }

}