package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cn.teampancake.theaurorian.utils.ModBlockRegUtils.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

@SuppressWarnings("SpellCheckingInspection")
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AurorianMod.MOD_ID);
    public static final RegistryObject<LiquidBlock> MOLTEN_AURORIAN_STEEL = BLOCKS.register("molten_aurorian_steel",
            () -> new LiquidBlock(ModFluids.MOLTEN_AURORIAN_STEEL_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_CERULEAN = BLOCKS.register("molten_cerulean",
            () -> new LiquidBlock(ModFluids.MOLTEN_CERULEAN_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_MOONSTONE = BLOCKS.register("molten_moonstone",
            () -> new LiquidBlock(ModFluids.MOLTEN_MOONSTONE_STILL, copy(Blocks.LAVA)));
    public static final RegistryObject<Block> AURORIAN_DIRT = normal("aurorian_dirt", copy(Blocks.DIRT));
    public static final RegistryObject<Block> AURORIAN_STONE = normal("aurorian_stone", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICKS = normal("aurorian_stone_bricks", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE = normal("aurorian_cobblestone", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COAL_ORE = ore("aurorian_coal_ore", defaultStoneProperties(3.0F));
    public static final RegistryObject<Block> AURORIAN_GRASS_BLOCK = register("aurorian_grass_block", () -> new AurorianGrassBlock(copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> AURORIAN_GRASS_LIGHT_BLOCK = register("aurorian_grass_light_block", () -> new AurorianGrassBlock(copy(Blocks.GRASS_BLOCK).lightLevel(s -> 2)));
    public static final RegistryObject<Block> AURORIAN_FARM_TILE = register("aurorian_farm_tile", () -> new AurorianFarmTile(copy(Blocks.FARMLAND)));
    public static final RegistryObject<Block> AURORIAN_GLASS = register("aurorian_glass", () -> new GlassBlock(copy(Blocks.GLASS)));
    public static final RegistryObject<Block> MOON_GLASS = register("moon_glass", () -> new GlassBlock(copy(Blocks.GLASS)));
    public static final RegistryObject<Block> AURORIAN_GLASS_PANE = register("aurorian_glass_pane", () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)));
    public static final RegistryObject<Block> MOON_GLASS_PANE = register("moon_glass_pane", () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)));
    public static final RegistryObject<Block> AURORIAN_GRASS = register("aurorian_grass", () -> new AbstractPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> AURORIAN_GRASS_LIGHT = register("aurorian_grass_light", () -> new AbstractPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_LIGHT_BLOCK));
    public static final RegistryObject<Block> AURORIAN_FURNACE = register("aurorian_furnace", () -> new AurorianFurnace(defaultStoneProperties(3.5F)));
    public static final RegistryObject<Block> AURORIAN_FURNACE_CHIMNEY = register("aurorian_furnace_chimney", () -> new AurorianFurnaceChimney(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> AURORIAN_PORTAL = BLOCKS.register("aurorian_portal", () -> new AurorianPortal(copy(Blocks.NETHER_PORTAL)));
    public static final RegistryObject<Block> AURORIAN_PORTAL_FRAME_BRICKS = register("aurorian_portal_frame_bricks", () -> new AurorianPortalFrame(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> URN = register("urn", () -> new UrnBlock(of().mapColor(MapColor.STONE).instabreak().sound(SoundType.GLASS)));
    public static final RegistryObject<Block> LAVENDER_CROP = BLOCKS.register("lavender_crop", () -> new AbstractCropBlock(copy(Blocks.GRASS), ModItems.LAVENDER_SEEDS));
    public static final RegistryObject<Block> SILK_BERRY_CROP = BLOCKS.register("silk_berry_crop", () -> new AbstractCropBlock(copy(Blocks.GRASS), ModItems.SILK_BERRY));
    public static final RegistryObject<Block> PETUNIA_PLANT = register("petunia_plant", () -> new AbstractPlantBlock(copy(Blocks.GRASS), AURORIAN_GRASS_BLOCK));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE = normal("aurorian_peridotite", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE = normal("smooth_aurorian_peridotite", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> MOONSTONE_ORE = ore("moonstone_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> CERULEAN_ORE = ore("cerulean_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> GEODE_ORE = ore("geode_ore", defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> RUNE_STONE = normal("rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_TEMPLE_BRICKS = normal("moon_temple_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_BRICKS = normal("dark_stone_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_FANCY = normal("dark_stone_fancy", breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_LAYERS = normal("dark_stone_layers", breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_RUNE_STONE = normal("smooth_rune_stone", breakWithQueenPickaxe());
    public static final RegistryObject<Block> SMOOTH_MOON_TEMPLE_BRICKS = normal("smooth_moon_temple_bricks", breakWithQueenPickaxe());
    public static final RegistryObject<Block> RUNE_STONE_LAMP = normal("rune_stone_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> DARK_STONE_LAMP = normal("dark_stone_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> MOON_TEMPLE_LAMP = normal("moon_temple_lamp", breakWithQueenPickaxe().lightLevel(s -> 15));
    public static final RegistryObject<Block> CERULEAN_BLOCK = normal("cerulean_block", breakWithQueenPickaxe().mapColor(MapColor.METAL));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = normal("moonstone_block", breakWithQueenPickaxe().mapColor(MapColor.METAL));
    public static final RegistryObject<Block> AURORIAN_COAL_BLOCK = normal("aurorian_coal_block", breakWithQueenPickaxe().mapColor(MapColor.METAL));
    public static final RegistryObject<Block> AURORIAN_STEEL_BLOCK = normal("aurorian_steel_block", breakWithQueenPickaxe().mapColor(MapColor.METAL));
    public static final RegistryObject<Block> MYSTICAL_BARRIER = register("mystical_barrier", () -> new MysticalBarrier(copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> RUNE_STONE_BARS = register("rune_stone_bars", () -> new IronBarsBlock(breakWithQueenPickaxe().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> MOON_TEMPLE_BARS = register("moon_temple_bars", () -> new IronBarsBlock(breakWithQueenPickaxe().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> RUNE_STONE_GATE = register("rune_stone_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_GATE = register("moon_temple_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> DARK_STONE_GATE = register("dark_stone_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> RUNE_STONE_LOOT_GATE = register("rune_stone_loot_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> MOON_TEMPLE_CELL_GATE = register("moon_temple_cell_gate", () -> new DungeonStoneGate(breakWithQueenPickaxe()));
    public static final RegistryObject<Block> DARK_STONE_GATE_KEYHOLE = register("dark_stone_gate_keyhole", () -> new DungeonStoneGateKeyhole(ModItems.DARK_STONE_KEY));
    public static final RegistryObject<Block> MOON_TEMPLE_GATE_KEYHOLE = register("moon_temple_gate_keyhole", () -> new DungeonStoneGateKeyhole(ModItems.MOON_TEMPLE_KEY));
    public static final RegistryObject<Block> RUNE_STONE_GATE_KEYHOLE = register("rune_stone_gate_keyhole", () -> new DungeonStoneGateKeyhole(ModItems.RUNE_STONE_KEY, Boolean.FALSE));
    public static final RegistryObject<Block> RUNE_STONE_LOOT_GATE_KEYHOLE = register("rune_stone_loot_gate_keyhole", () -> new DungeonStoneGateKeyhole(ModItems.RUNE_STONE_LOOT_KEY));
    public static final RegistryObject<Block> MOON_TEMPLE_CELL_GATE_KEYHOLE = register("moon_temple_cell_gate_keyhole", () -> new DungeonStoneGateKeyhole(ModItems.MOON_TEMPLE_CELL_KEY));
    public static final RegistryObject<Block> INDIGO_MUSHROOM = register("indigo_mushroom", () -> new MushroomBlock(copy(Blocks.BROWN_MUSHROOM), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_BLOCK = register("indigo_mushroom_block", () -> new IndigoMushroom(copy(Blocks.BROWN_MUSHROOM_BLOCK).destroyTime(1.0F)));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_STEM = register("indigo_mushroom_stem", () -> new HugeMushroomBlock(copy(Blocks.MUSHROOM_STEM).destroyTime(1.0F)));
    public static final RegistryObject<Block> INDIGO_MUSHROOM_CRYSTAL = normal("indigo_mushroom_crystal", of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.GLASS).lightLevel(s -> 1));
    public static final RegistryObject<Block> MOONLIGHT_FORGE = register("moonlight_forge", () -> new MoonlightForge(defaultStoneProperties((2.0F)).mapColor(MapColor.METAL).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> MOON_GEM = register("moon_gem", () -> new MoonGem(defaultStoneProperties(2.0F).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> MOON_SAND = normal("moon_sand", copy(Blocks.SAND));
    public static final RegistryObject<Block> MOON_TORCH = register("moon_torch", () -> new TorchBlock(copy(Blocks.TORCH), ParticleTypes.CLOUD));
    public static final RegistryObject<Block> MOON_WALL_TORCH = BLOCKS.register("moon_wall_torch", () -> new WallTorchBlock(copy(Blocks.WALL_TORCH), ParticleTypes.CLOUD));
    public static final RegistryObject<Block> SCRAPPER = register("scrapper", () -> new Scrapper(defaultStoneProperties(2.0F)));
    public static final RegistryObject<Block> UMBRA_STONE = normal("umbra_stone", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED = normal("umbra_stone_cracked", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_TILES = normal("umbra_stone_roof_tiles", defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SILENT_TREE_LEAVES = register("silent_tree_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final RegistryObject<Block> SILENT_TREE_LOG = register("silent_tree_log", () -> Blocks.log(MapColor.COLOR_BLUE, MapColor.COLOR_BLACK));
    public static final RegistryObject<Block> SILENT_TREE_PLANKS = normal("silent_tree_planks", copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> SILENT_TREE_WOOD = wood("silent_tree_wood", MapColor.COLOR_BLUE, 2.0F);
    public static final RegistryObject<Block> SILENT_WOOD_TORCH = register("silent_wood_torch", () -> new TorchBlock(copy(Blocks.TORCH), ParticleTypes.FLAME));
    public static final RegistryObject<Block> SILENT_WOOD_WALL_TORCH = BLOCKS.register("silent_wood_wall_torch", () -> new WallTorchBlock(copy(Blocks.WALL_TORCH), ParticleTypes.FLAME));
    public static final RegistryObject<Block> SILENT_WOOD_CRAFTING_TABLE = register("silent_wood_crafting_table", () -> new CraftingTableBlock(copy(Blocks.CRAFTING_TABLE)));
    public static final RegistryObject<Block> SILENT_WOOD_LADDER = register("silent_wood_ladder", () -> new LadderBlock(copy(Blocks.LADDER)));
    public static final RegistryObject<Block> WEEPING_WILLOW_LEAVES = register("weeping_willow_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final RegistryObject<Block> WEEPING_WILLOW_LOG = register("weeping_willow_log", () -> Blocks.log(MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_BLACK));
    public static final RegistryObject<Block> WEEPING_WILLOW_PLANKS = normal("weeping_willow_planks", copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> WEEPING_WILLOW_WOOD = wood("weeping_willow_wood", MapColor.COLOR_LIGHT_GRAY, 2.0F);
    public static final RegistryObject<Block> AURORIAN_STONE_STAIRS = stair("aurorian_stone_stairs", AURORIAN_STONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_STONE_BRICK_STAIRS = stair("aurorian_stone_brick_stairs", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> AURORIAN_COBBLESTONE_STAIRS = stair("aurorian_cobblestone_stairs", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F));
    public static final RegistryObject<Block> SILENT_WOOD_STAIRS = stair("silent_wood_stairs", SILENT_TREE_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> RUNE_STONE_STAIRS = stair("rune_stone_stairs", RUNE_STONE, breakWithQueenPickaxe());
    public static final RegistryObject<Block> MOON_TEMPLE_STAIRS = stair("moon_temple_stairs", MOON_TEMPLE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> DARK_STONE_STAIRS = stair("dark_stone_stairs", DARK_STONE_BRICKS, breakWithQueenPickaxe());
    public static final RegistryObject<Block> UMBRA_STONE_STAIRS = stair("umbra_stone_stairs", UMBRA_STONE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_CRACKED_STAIRS = stair("umbra_stone_cracked_stairs", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> UMBRA_STONE_ROOF_STAIRS = stair("umbra_stone_roof_stairs", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> WEEPING_WILLOW_STAIRS = stair("weeping_willow_stairs", WEEPING_WILLOW_PLANKS, copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<Block> AURORIAN_PERIDOTITE_STAIRS = stair("aurorian_peridotite_stairs", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    public static final RegistryObject<Block> SMOOTH_AURORIAN_PERIDOTITE_STAIRS = stair("smooth_aurorian_peridotite_stairs", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F));
    
    private static BlockBehaviour.Properties defaultStoneProperties(float destroyTime) {
        return of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().destroyTime(destroyTime)
                .explosionResistance((destroyTime * 5.0F));
    }

    @SuppressWarnings("deprecation")
    public static BlockBehaviour.Properties breakWithQueenPickaxe() {
        final float f = Blocks.BEDROCK.getExplosionResistance();
        return of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().strength(Byte.MAX_VALUE, f);
    }

}