package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.common.blocks.AurorianFurnace;
import cn.teampancake.theaurorian.common.blocks.*;
import cn.teampancake.theaurorian.common.blocks.crystal.LunarSourcePrism;
import cn.teampancake.theaurorian.common.blocks.crystal.ReceivingCrystal;
import cn.teampancake.theaurorian.common.blocks.crystal.LunarSplitter;
import cn.teampancake.theaurorian.common.blocks.crystal.LunarDeflector;
import cn.teampancake.theaurorian.common.blocks.modified.AxeStrippableBlock;
import cn.teampancake.theaurorian.common.blocks.modified.HoeTillableBlock;
import cn.teampancake.theaurorian.common.blocks.sign.*;
import cn.teampancake.theaurorian.common.blocks.state.TABlockSetType;
import cn.teampancake.theaurorian.common.blocks.state.TAWoodType;
import cn.teampancake.theaurorian.common.blocks.technical.TempBarrier;
import cn.teampancake.theaurorian.common.blocks.technical.TrapHoleRestorer;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cn.teampancake.theaurorian.common.data.datagen.provider.TABlockStateProvider.*;
import static cn.teampancake.theaurorian.common.data.datagen.provider.TAItemModelProvider.*;
import static cn.teampancake.theaurorian.common.utils.TABlockRegUtils.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.*;

public class TABlocks {

    public static final DeferredHolder<Block, LiquidBlock> MOLTEN_MOONSILVER = registerNoItemBuilder("molten_moonsilver", p -> new LiquidBlock(TAFluids.MOLTEN_MOONSILVER_STILL.get(), ofFullCopy(Blocks.LAVA))).register();
    public static final DeferredHolder<Block, LiquidBlock> MOLTEN_CERULEAN = registerNoItemBuilder("molten_cerulean", p -> new LiquidBlock(TAFluids.MOLTEN_CERULEAN_STILL.get(), ofFullCopy(Blocks.LAVA))).register();
    public static final DeferredHolder<Block, LiquidBlock> MOLTEN_MOONSTONE = registerNoItemBuilder("molten_moonstone", p -> new LiquidBlock(TAFluids.MOLTEN_MOONSTONE_STILL.get(), ofFullCopy(Blocks.LAVA))).register();
    public static final DeferredHolder<Block, LiquidBlock> MOON_WATER = registerNoItemBuilder("moon_water", p -> new LiquidBlock(TAFluids.MOON_WATER_STILL.get(), ofFullCopy(Blocks.WATER))).register();
    public static final DeferredHolder<Block, Block> AURORIAN_STONE = simpleBuilder("aurorian_stone", defaultStoneProperties(2.0F)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).register();
    public static final DeferredHolder<Block, Block> AURORIAN_EROSIVE = simpleBuilder("aurorian_erosive", defaultStoneProperties(2.0F)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_STONE_BRICKS = simpleBuilder("aurorian_stone_bricks", defaultStoneProperties(2.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_COBBLESTONE = simpleBuilder("aurorian_cobblestone", defaultStoneProperties(2.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_GRANITE = simpleBuilder("aurorian_granite", defaultStoneProperties(2.0F)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_DIORITE = simpleBuilder("aurorian_diorite", defaultStoneProperties(2.0F)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_ANDESITE = simpleBuilder("aurorian_andesite", defaultStoneProperties(2.0F)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_BARRIER_STONE = simpleBuilder("aurorian_barrier_stone", ofFullCopy(Blocks.BEDROCK)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, AurorianGrassBlock> AURORIAN_GRASS_BLOCK = registerBuilder("aurorian_grass_block", p -> new AurorianGrassBlock(
            ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks())).tag(BlockTags.MINEABLE_WITH_SHOVEL, TABlockTags.AURORIAN_GRASS_BLOCK)
            .blockstate((ctx, prov) -> registerGrassBlockState(ctx.get(), prov)).item().tag(TAItemTags.AURORIAN_GRASS_BLOCK).build().register();
    public static final DeferredHolder<Block, AurorianGrassBlock> LIGHT_AURORIAN_GRASS_BLOCK = registerBuilder("light_aurorian_grass_block", p -> new AurorianGrassBlock(
            ofFullCopy(Blocks.GRASS_BLOCK).lightLevel(s -> 2).mapColor(MapColor.TERRACOTTA_WHITE))).tag(BlockTags.MINEABLE_WITH_SHOVEL, TABlockTags.AURORIAN_GRASS_BLOCK)
            .blockstate((ctx, prov) -> registerGrassBlockState(ctx.get(), prov)).item().tag(TAItemTags.AURORIAN_GRASS_BLOCK).build().register();
    public static final DeferredHolder<Block, AurorianGrassBlock> SNOW_AURORIAN_GRASS_BLOCK = registerBuilder("snow_aurorian_grass_block", p -> new AurorianGrassBlock(
            ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.SNOW))).tag(BlockTags.MINEABLE_WITH_SHOVEL, TABlockTags.AURORIAN_GRASS_BLOCK)
            .blockstate((ctx, prov) -> registerGrassBlockState(ctx.get(), prov)).item().tag(TAItemTags.AURORIAN_GRASS_BLOCK).build().register();
    public static final DeferredHolder<Block, AurorianGrassBlock> RED_AURORIAN_GRASS_BLOCK = registerBuilder("red_aurorian_grass_block", p -> new AurorianGrassBlock(
            ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_RED))).tag(BlockTags.MINEABLE_WITH_SHOVEL, TABlockTags.AURORIAN_GRASS_BLOCK)
            .blockstate((ctx, prov) -> registerGrassBlockState(ctx.get(), prov)).item().tag(TAItemTags.AURORIAN_GRASS_BLOCK).build().register();
    public static final DeferredHolder<Block, AurorianFarmTile> AURORIAN_FARM_TILE = registerNoItemModelBuilder("aurorian_farm_tile",
            p -> new AurorianFarmTile(ofFullCopy(Blocks.FARMLAND))).tag(BlockTags.MINEABLE_WITH_SHOVEL).register();
    public static final DeferredHolder<Block, HoeTillableBlock> AURORIAN_DIRT = registerBuilder("aurorian_dirt", p -> new HoeTillableBlock(AURORIAN_FARM_TILE, ofFullCopy(Blocks.DIRT)))
            .tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_SHOVEL).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, IceBlock> FILTHY_ICE = registerBuilder("filthy_ice", p -> new IceBlock(ofFullCopy(Blocks.ICE)))
            .tag(BlockTags.ICE, BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), TRANSLUCENT, prov)).register();
    public static final DeferredHolder<Block, TransparentBlock> MOON_GLASS = registerBuilder("moon_glass", p -> new TransparentBlock(ofFullCopy(Blocks.GLASS)))
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch).blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), TRANSLUCENT, prov)).register();
    public static final DeferredHolder<Block, TransparentBlock> AURORIAN_GLASS = registerBuilder("aurorian_glass", p -> new TransparentBlock(ofFullCopy(Blocks.GLASS)))
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch).blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), TRANSLUCENT, prov)).register();
    public static final DeferredHolder<Block, TransparentBlock> DARK_STONE_GLASS = registerBuilder("dark_stone_glass", p -> new TransparentBlock(ofFullCopy(Blocks.GLASS)))
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch).blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), TRANSLUCENT, prov)).register();
    public static final DeferredHolder<Block, IronBarsBlock> MOON_GLASS_PANE = registerBuilder("moon_glass_pane", p -> new IronBarsBlock(ofFullCopy(Blocks.GLASS_PANE))).loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .blockstate((ctx, prov) -> prov.paneBlockWithRenderType(ctx.get(), prov.blockTexture(MOON_GLASS.get()), prov.blockTexture(ctx.get()), TRANSLUCENT)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), MOON_GLASS.get(), prov)).build().register();
    public static final DeferredHolder<Block, IronBarsBlock> AURORIAN_GLASS_PANE = registerBuilder("aurorian_glass_pane", p -> new IronBarsBlock(ofFullCopy(Blocks.GLASS_PANE))).loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .blockstate((ctx, prov) -> prov.paneBlockWithRenderType(ctx.get(), prov.blockTexture(AURORIAN_GLASS.get()), prov.blockTexture(ctx.get()), TRANSLUCENT)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), AURORIAN_GLASS.get(), prov)).build().register();
    public static final DeferredHolder<Block, IronBarsBlock> DARK_STONE_GLASS_PANE = registerBuilder("dark_stone_glass_pane", p -> new IronBarsBlock(ofFullCopy(Blocks.GLASS_PANE))).loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .blockstate((ctx, prov) -> prov.paneBlockWithRenderType(ctx.get(), prov.blockTexture(DARK_STONE_GLASS.get()), prov.blockTexture(ctx.get()), TRANSLUCENT)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), DARK_STONE_GLASS.get(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> AURORIAN_GRASS = registerBuilder("aurorian_grass", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> AURORIAN_GRASS_LIGHT = registerBuilder("aurorian_grass_light", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS).lightLevel(s -> 2)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TALightPlantBlock> AURORIAN_WATER_GRASS = registerBuilder("aurorian_water_grass", p -> new TALightPlantBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerLightPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, AurorianWaterSurfacePlant> AURORIAN_LILY_PAD = registerNoItemBuilder("aurorian_lily_pad", p -> new AurorianWaterSurfacePlant(
            Block.box(0.5D, 0.0D, 0.5D, 15.5D, 0.5D, 15.5D))).blockstate((ctx, prov) -> registerWaterSurfacePlantStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, AurorianWaterSurfacePlant> AURORIAN_WATER_MUSHROOM = registerNoItemBuilder("aurorian_water_mushroom", p -> new AurorianWaterSurfacePlant(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.5D, 12.0D))).blockstate((ctx, prov) -> registerWaterSurfacePlantStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, AurorianFurnace> AURORIAN_FURNACE = registerNoItemModelBuilder("aurorian_furnace", p -> new AurorianFurnace(defaultStoneProperties(3.5F))).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, AurorianFurnaceChimney> AURORIAN_FURNACE_CHIMNEY = registerNoItemModelBuilder("aurorian_furnace_chimney", p -> new AurorianFurnaceChimney(defaultStoneProperties(2.0F))).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, AurorianChest> AURORIAN_CHEST = registerNoItemBuilder("aurorian_chest", p -> new AurorianChest()).tag(Tags.Blocks.CHESTS_WOODEN, BlockTags.MINEABLE_WITH_AXE).defaultLoot()
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().getBuilder(name(ctx.get())).texture("particle", prov.modLoc("block/" + name(TABlocks.SILENT_TREE_PLANKS.get())))))
            .item().model((ctx, prov) -> prov.withExistingParent(blockName(ctx.get().getBlock()), prov.mcLoc("item/chest")).texture("particle", prov.modLoc("block/" + name(TABlocks.SILENT_TREE_PLANKS.get())))).build().register();
    public static final DeferredHolder<Block, AurorianCraftingTable> AURORIAN_CRAFTING_TABLE = registerBuilder("aurorian_crafting_table", AurorianCraftingTable::new).tag(BlockTags.MINEABLE_WITH_AXE).defaultLoot().register();
    public static final DeferredHolder<Block, AurorianPortal> AURORIAN_PORTAL = registerNoItemBuilder("aurorian_portal", p -> new AurorianPortal(ofFullCopy(Blocks.NETHER_PORTAL))).register();
    public static final DeferredHolder<Block, Block> AURORIAN_PORTAL_FRAME_BRICKS = simpleBuilder("aurorian_portal_frame_bricks", defaultStoneProperties(2.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, UrnBlock> URN = registerNoItemBuilder("urn", p -> new UrnBlock(p.mapColor(MapColor.STONE).instabreak().sound(SoundType.GLASS))).register();
    public static final DeferredHolder<Block, Block> MYSTERIUM_WOOL = simpleBuilder("mysterium_wool", ofFullCopy(Blocks.WHITE_WOOL)).tag(BlockTags.WOOL).defaultLoot().register();
    public static final DeferredHolder<Block, TASnowfieldTallPlantBlock> DREAMSCAPE_PISTIL = registerNoItemModelBuilder("dreamscape_pistil", p -> new TASnowfieldTallPlantBlock()).register();
    public static final DeferredHolder<Block, TASnowfieldTallPlantBlock> FROST_TEARS_FLOWER = registerNoItemModelBuilder("frost_tears_flower", p -> new TASnowfieldTallPlantBlock()).register();
    public static final DeferredHolder<Block, TAFlowerBlock> NEBULA_BLOSSOM_CLUSTER = registerNoItemBuilder("nebula_blossom_cluster", TAFlowerBlock::new).defaultLoot()
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAFlowerBlock> MOON_FROST_FLOWER = registerNoItemBuilder("moon_frost_flower", TAFlowerBlock::new).defaultLoot()
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAFlowerBlock> VOID_CANDLE_FLOWER = registerNoItemBuilder("void_candle_flower", TAFlowerBlock::new).defaultLoot()
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAFlowerBlock> EQUINOX_FLOWER = registerNoItemBuilder("equinox_flower", TAFlowerBlock::new).defaultLoot()
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, WickGrass> WICK_GRASS = registerNoItemBuilder("wick_grass", WickGrass::new)
            .blockstate((ctx, prov) -> registerLightPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TallWickGrass> TALL_WICK_GRASS = registerNoItemBuilder("tall_wick_grass", p -> new TallWickGrass())
            .blockstate((ctx, prov) -> registerDoubleLightPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov, "_upper")).build().register();
    public static final DeferredHolder<Block, BlueberryBush> BLUEBERRY_BUSH = registerNoItemBuilder("blueberry_bush", p -> new BlueberryBush())
            .tag(BlockTags.FALL_DAMAGE_RESETTING, BlockTags.SWORD_EFFICIENT, BlockTags.MINEABLE_WITH_AXE).blockstate((ctx, prov) -> registerCrossStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, SilentWoodStick> SILENT_WOOD_STICK = registerNoItemBuilder("silent_wood_stick", p -> new SilentWoodStick(ofFullCopy(Blocks.OAK_PLANKS).instabreak().pushReaction(PushReaction.DESTROY))).register();
    public static final DeferredHolder<Block, TACropBlock> LAVENDER_CROP = registerNoItemBuilder("lavender_crop", p -> new TACropBlock(ofFullCopy(Blocks.SHORT_GRASS), TAItems.LAVENDER_SEEDS)).tag(BlockTags.CROPS).blockstate((ctx, prov) -> registerCropStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, TACropBlock> SILK_BERRY_CROP = registerNoItemBuilder("silk_berry_crop", p -> new TACropBlock(ofFullCopy(Blocks.SHORT_GRASS), TAItems.SILK_BERRY)).tag(BlockTags.CROPS).blockstate((ctx, prov) -> registerCropStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, DeadBushBlock> LAVENDER_PLANT = registerNoItemBuilder("lavender_plant", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> PETUNIA_PLANT = registerNoItemBuilder("petunia_plant", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> CRISPED_MALLOW = registerNoItemBuilder("crisped_mallow", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> FROST_SNOW_GRASS = registerNoItemBuilder("frost_snow_grass", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> ICE_CALENDULA = registerNoItemBuilder("ice_calendula", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DeadBushBlock> WINTER_ROOT = registerNoItemBuilder("winter_root", p -> new DeadBushBlock(ofFullCopy(Blocks.SHORT_GRASS)))
            .blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DoublePlantBlock> TALL_AURORIAN_GRASS = registerNoItemBuilder("tall_aurorian_grass", p -> new DoublePlantBlock(ofFullCopy(Blocks.TALL_GRASS)))
            .blockstate((ctx, prov) -> registerDoublePlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov, "_upper")).build().register();
    public static final DeferredHolder<Block, DoublePlantBlock> TALL_LAVENDER_PLANT = registerNoItemBuilder("tall_lavender_plant", p -> new DoublePlantBlock(ofFullCopy(Blocks.TALL_GRASS)))
            .blockstate((ctx, prov) -> registerDoublePlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov, "_upper")).build().register();
    public static final DeferredHolder<Block, TALightDoublePlantBlock> TALL_AURORIAN_WATER_GRASS = registerNoItemBuilder("tall_aurorian_water_grass", p -> new TALightDoublePlantBlock(ofFullCopy(Blocks.TALL_GRASS)))
            .blockstate((ctx, prov) -> registerDoubleLightPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov, "_upper")).build().register();
    public static final DeferredHolder<Block, TALightDoublePlantBlock> TALL_AURORIAN_GRASS_LIGHT = registerNoItemBuilder("tall_aurorian_grass_light", p -> new TALightDoublePlantBlock(ofFullCopy(Blocks.TALL_GRASS)))
            .blockstate((ctx, prov) -> registerDoubleLightPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov, "_upper")).build().register();
    public static final DeferredHolder<Block, Block> SMOOTH_AURORIAN_PERIDOTITE = simpleBuilder("smooth_aurorian_peridotite", defaultStoneProperties(5.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> AURORIAN_PERIDOTITE = simpleBuilder("aurorian_peridotite", defaultStoneProperties(5.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();

    /**
     * Ore
     */
    public static final DeferredHolder<Block, DropExperienceBlock> MOONSTONE_ORE = oreBuilder("moonstone_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.RAW_MOONSTONE.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> CERULEAN_ORE = oreBuilder("cerulean_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.RAW_CERULEAN.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> GEODE_ORE = oreBuilder("geode_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.CRYSTAL.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_MOONSTONE_ORE = oreBuilder("erosive_moonstone_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.RAW_MOONSTONE.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_CERULEAN_ORE = oreBuilder("erosive_cerulean_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.RAW_CERULEAN.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_GEODE_ORE = oreBuilder("erosive_geode_ore", ConstantInt.ZERO, defaultStoneProperties(2.0F)).tag(BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.CRYSTAL.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_COAL_ORE = oreBuilder("aurorian_coal_ore", UniformInt.of(0, 2), ofFullCopy(Blocks.COAL_ORE)).tag(BlockTags.COAL_ORES).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, TAItems.AURORIAN_COAL.get()))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_IRON_ORE = oreBuilder("aurorian_iron_ore", ConstantInt.ZERO, ofFullCopy(Blocks.IRON_ORE)).tag(BlockTags.IRON_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.RAW_IRON))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_GOLD_ORE = oreBuilder("aurorian_gold_ore", ConstantInt.ZERO, ofFullCopy(Blocks.GOLD_ORE)).tag(BlockTags.GOLD_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.RAW_GOLD))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_LAPIS_ORE = oreBuilder("aurorian_lapis_ore", UniformInt.of(2, 5), ofFullCopy(Blocks.LAPIS_ORE)).tag(BlockTags.LAPIS_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createLapisOreDrops(prov))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_COPPER_ORE = oreBuilder("aurorian_copper_ore", ConstantInt.ZERO, ofFullCopy(Blocks.COPPER_ORE)).tag(BlockTags.COPPER_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createCopperOreDrops(prov))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_DIAMOND_ORE = oreBuilder("aurorian_diamond_ore", UniformInt.of(3, 7), ofFullCopy(Blocks.DIAMOND_ORE)).tag(BlockTags.DIAMOND_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.DIAMOND))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> AURORIAN_EMERALD_ORE = oreBuilder("aurorian_emerald_ore", UniformInt.of(3, 7), ofFullCopy(Blocks.EMERALD_ORE)).tag(BlockTags.EMERALD_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.EMERALD))).register();
    public static final DeferredHolder<Block, RedStoneOreBlock> AURORIAN_REDSTONE_ORE = registerBuilder("aurorian_redstone_ore", p -> new RedStoneOreBlock(ofFullCopy(Blocks.REDSTONE_ORE))).tag(BlockTags.REDSTONE_ORES, BlockTags.NEEDS_IRON_TOOL).defaultBlockstate().loot((ctx, prov) -> ctx.add(prov, ctx.createRedstoneOreDrops(prov))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_IRON_ORE = oreBuilder("erosive_aurorian_iron_ore", ConstantInt.ZERO, ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)).tag(BlockTags.IRON_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.RAW_IRON))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_GOLD_ORE = oreBuilder("erosive_aurorian_gold_ore", ConstantInt.ZERO, ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE)).tag(BlockTags.GOLD_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.RAW_GOLD))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_LAPIS_ORE = oreBuilder("erosive_aurorian_lapis_ore", UniformInt.of(2, 5), ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE)).tag(BlockTags.LAPIS_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createLapisOreDrops(prov))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_COPPER_ORE = oreBuilder("erosive_aurorian_copper_ore", ConstantInt.ZERO, ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)).tag(BlockTags.COPPER_ORES, BlockTags.NEEDS_STONE_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createCopperOreDrops(prov))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_DIAMOND_ORE = oreBuilder("erosive_aurorian_diamond_ore", UniformInt.of(3, 7), ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)).tag(BlockTags.DIAMOND_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.DIAMOND))).register();
    public static final DeferredHolder<Block, DropExperienceBlock> EROSIVE_AURORIAN_EMERALD_ORE = oreBuilder("erosive_aurorian_emerald_ore", UniformInt.of(3, 7), ofFullCopy(Blocks.DEEPSLATE_EMERALD_ORE)).tag(BlockTags.EMERALD_ORES, BlockTags.NEEDS_IRON_TOOL).loot((ctx, prov) -> ctx.add(prov, ctx.createOreDrop(prov, Items.EMERALD))).register();
    public static final DeferredHolder<Block, RedStoneOreBlock> EROSIVE_AURORIAN_REDSTONE_ORE = registerBuilder("erosive_aurorian_redstone_ore", p -> new RedStoneOreBlock(ofFullCopy(Blocks.DEEPSLATE_REDSTONE_ORE))).tag(BlockTags.REDSTONE_ORES, BlockTags.NEEDS_IRON_TOOL).defaultBlockstate().loot((ctx, prov) -> ctx.add(prov, ctx.createRedstoneOreDrops(prov))).register();

    public static final DeferredHolder<Block, Block> RUNE_STONE = runestoneBuilder("rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> SMOOTH_RUNE_STONE = runestoneBuilder("smooth_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> CHISELED_RUNE_STONE = runestoneBuilder("chiseled_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> AURORIAN_CASTLE_RUNE_STONE = runestoneBuilder("aurorian_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> MOONSILVER_CASTLE_RUNE_STONE = runestoneBuilder("moonsilver_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> CERULEAN_CASTLE_RUNE_STONE = runestoneBuilder("cerulean_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> CRYSTALLINE_CASTLE_RUNE_STONE = runestoneBuilder("crystalline_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> MOON_CASTLE_RUNE_STONE = runestoneBuilder("moon_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> TRANSPARENT_RUNE_STONE = runestoneBuilder("transparent_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> UMBRA_CASTLE_RUNE_STONE = runestoneBuilder("umbra_castle_rune_stone", of()).tag(TABlockTags.RUNE_STONE_BLOCK).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE = runestoneBuilder("luminous_aurorian_castle_rune_stone", of().noOcclusion()).tag(TABlockTags.RUNE_STONE_BLOCK).blockstate((ctx, prov) -> registerLuminousStates(ctx.get(), AURORIAN_CASTLE_RUNE_STONE.get(), prov)).register();
    public static final DeferredHolder<Block, Block> LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE = runestoneBuilder("luminous_moonsilver_castle_rune_stone", of().noOcclusion()).tag(TABlockTags.RUNE_STONE_BLOCK).blockstate((ctx, prov) -> registerLuminousStates(ctx.get(), MOONSILVER_CASTLE_RUNE_STONE.get(), prov)).register();
    public static final DeferredHolder<Block, Block> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE = runestoneBuilder("luminous_cerulean_castle_rune_stone", of().noOcclusion()).tag(TABlockTags.RUNE_STONE_BLOCK).blockstate((ctx, prov) -> registerLuminousStates(ctx.get(), CERULEAN_CASTLE_RUNE_STONE.get(), prov)).register();
    public static final DeferredHolder<Block, Block> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE = runestoneBuilder("luminous_crystalline_castle_rune_stone", of().noOcclusion()).tag(TABlockTags.RUNE_STONE_BLOCK).blockstate((ctx, prov) -> registerLuminousStates(ctx.get(), CRYSTALLINE_CASTLE_RUNE_STONE.get(), prov)).register();
    public static final DeferredHolder<Block, Block> LUMINOUS_MOON_CASTLE_RUNE_STONE = runestoneBuilder("luminous_moon_castle_rune_stone", of().noOcclusion()).tag(TABlockTags.RUNE_STONE_BLOCK).blockstate((ctx, prov) -> registerLuminousStates(ctx.get(), MOON_CASTLE_RUNE_STONE.get(), prov)).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> RUNE_STONE_PILLAR = registerBuilder("rune_stone_pillar", p -> new RotatedPillarBlock(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().blockstate((ctx, prov) -> prov.logBlock(ctx.get())).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> DARK_STONE_PILLAR = registerBuilder("dark_stone_pillar", p -> new RotatedPillarBlock(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().blockstate((ctx, prov) -> prov.logBlock(ctx.get())).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> MOON_TEMPLE_PILLAR = registerBuilder("moon_temple_pillar", p -> new RotatedPillarBlock(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> prov.logBlock(ctx.get())).register();
    public static final DeferredHolder<Block, Block> MOON_TEMPLE_BRICKS = runestoneBuilder("moon_temple_bricks", of()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> DARK_STONE_BRICKS = runestoneBuilder("dark_stone_bricks", of()).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> DARK_STONE_FANCY = runestoneBuilder("dark_stone_fancy", of()).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> DARK_STONE_LAYERS = runestoneBuilder("dark_stone_layers", of()).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> SMOOTH_DARK_STONE_BRICKS = runestoneBuilder("smooth_dark_stone_bricks", of()).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> CHISELED_DARK_STONE_BRICKS = runestoneBuilder("chiseled_dark_stone_bricks", of()).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> SMOOTH_MOON_TEMPLE_BRICKS = runestoneBuilder("smooth_moon_temple_bricks", of()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> CHISELED_MOON_TEMPLE_BRICKS = runestoneBuilder("chiseled_moon_temple_bricks", of()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> RUNE_STONE_LAMP = runestoneBuilder("rune_stone_lamp", of().lightLevel(s -> 15)).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> DARK_STONE_LAMP = runestoneBuilder("dark_stone_lamp", of().lightLevel(s -> 15)).tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> MOON_TEMPLE_LAMP = runestoneBuilder("moon_temple_lamp", of().lightLevel(s -> 15)).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> VOID_STONE = runestoneBuilder("void_stone", of().lightLevel(s -> 7)).defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> RUNE_CRYSTAL = runestoneBuilder("rune_crystal", of().lightLevel(s -> 3)).defaultBlockstate().register();
    public static final DeferredHolder<Block, MysticalBarrier> MYSTICAL_BARRIER = registerNoItemBuilder("mystical_barrier", p -> new MysticalBarrier(ofFullCopy(Blocks.BEDROCK))).register();
    public static final DeferredHolder<Block, IronBarsBlock> RUNE_STONE_BARS = registerNoItemBuilder("rune_stone_bars", p -> new IronBarsBlock(runestoneProperties(p).mapColor(MapColor.METAL)))
            .tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerBarStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, IronBarsBlock> DARK_STONE_BARS = registerNoItemBuilder("dark_stone_bars", p -> new IronBarsBlock(runestoneProperties(p).mapColor(MapColor.METAL)))
            .tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerBarStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, IronBarsBlock> MOON_TEMPLE_BARS = registerNoItemBuilder("moon_temple_bars", p -> new IronBarsBlock(runestoneProperties(p).mapColor(MapColor.METAL)))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerBarStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, DungeonStoneGate> RUNE_STONE_GATE = registerBuilder("rune_stone_gate", p -> new DungeonStoneGate(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, DungeonStoneGate> MOON_TEMPLE_GATE = registerBuilder("moon_temple_gate", p -> new DungeonStoneGate(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, DungeonStoneGate> DARK_STONE_GATE = registerBuilder("dark_stone_gate", p -> new DungeonStoneGate(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, DungeonStoneGate> RUNE_STONE_LOOT_GATE = registerBuilder("rune_stone_loot_gate", p -> new DungeonStoneGate(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, DungeonStoneGate> MOON_TEMPLE_CELL_GATE = registerBuilder("moon_temple_cell_gate", p -> new DungeonStoneGate(runestoneProperties(p))).tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, DungeonStoneGateKeyhole> DARK_STONE_GATE_KEYHOLE = registerBuilder("dark_stone_gate_keyhole", p -> new DungeonStoneGateKeyhole(p, TAItems.DARK_STONE_KEY, DARK_STONE_GATE))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerKeyholeStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, DungeonStoneGateKeyhole> MOON_TEMPLE_GATE_KEYHOLE = registerBuilder("moon_temple_gate_keyhole", p -> new DungeonStoneGateKeyhole(p, TAItems.MOON_TEMPLE_KEY, MOON_TEMPLE_GATE))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerKeyholeStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, DungeonStoneGateKeyhole> RUNE_STONE_GATE_KEYHOLE = registerBuilder("rune_stone_gate_keyhole", p -> new DungeonStoneGateKeyhole(p, TAItems.RUNE_STONE_KEY, RUNE_STONE_GATE, Boolean.TRUE))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerKeyholeStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, DungeonStoneGateKeyhole> RUNE_STONE_LOOT_GATE_KEYHOLE = registerBuilder("rune_stone_loot_gate_keyhole", p -> new DungeonStoneGateKeyhole(p, TAItems.RUNE_STONE_LOOT_KEY, RUNE_STONE_LOOT_GATE))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerKeyholeStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, DungeonStoneGateKeyhole> MOON_TEMPLE_CELL_GATE_KEYHOLE = registerBuilder("moon_temple_cell_gate_keyhole", p -> new DungeonStoneGateKeyhole(p, TAItems.MOON_TEMPLE_CELL_KEY, MOON_TEMPLE_CELL_GATE))
            .tag(TABlockTags.DUNGEON_BLOCKS, TABlockTags.MOON_TEMPLE_BLOCKS).defaultLoot().blockstate((ctx, prov) -> registerKeyholeStates(ctx.get(), prov)).register();
    public static final DeferredHolder<Block, Block> CERULEAN_BLOCK = simpleBuilder("cerulean_block", defaultStoneProperties(3.0F).mapColor(MapColor.METAL)).tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).defaultLoot().register();
    public static final DeferredHolder<Block, Block> MOONSTONE_BLOCK = simpleBuilder("moonstone_block", defaultStoneProperties(3.0F).mapColor(MapColor.METAL)).tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).defaultLoot().register();
    public static final DeferredHolder<Block, Block> AURORIAN_COAL_BLOCK = simpleBuilder("aurorian_coal_block", defaultStoneProperties(5.0F).mapColor(MapColor.METAL)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> MOONSILVER_BLOCK = simpleBuilder("moonsilver_block", defaultStoneProperties(5.0F).mapColor(MapColor.METAL)).tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).defaultLoot().register();
    public static final DeferredHolder<Block, LargeFilthyIceSpike> LARGE_FILTHY_ICE_SPIKE = registerBuilder("large_filthy_ice_spike", p -> new LargeFilthyIceSpike())
            .blockstate((ctx, prov) -> registerLargeFilthyIceSpike(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_large_bud")).build().register();
    public static final DeferredHolder<Block, FilthyIceSpike> MEDIUM_FILTHY_ICE_SPIKE = registerBuilder("medium_filthy_ice_spike", p -> new FilthyIceSpike(FilthyIceSpike.Size.MEDIUM))
            .blockstate((ctx, prov) -> registerFilthyIceSpike(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_medium_bud")).build().register();
    public static final DeferredHolder<Block, FilthyIceSpike> SMALL_FILTHY_ICE_SPIKE = registerBuilder("small_filthy_ice_spike", p -> new FilthyIceSpike(FilthyIceSpike.Size.SMALL))
            .blockstate((ctx, prov) -> registerFilthyIceSpike(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_small_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> CERULEAN_CLUSTER = registerBuilder("cerulean_cluster", p -> new TAClusterBlock(7, 3, ofFullCopy(Blocks.AMETHYST_CLUSTER)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_cluster")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> LARGE_CERULEAN_BUD = registerBuilder("large_cerulean_bud", p -> new TAClusterBlock(5, 3, ofFullCopy(Blocks.LARGE_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_large_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> MEDIUM_CERULEAN_BUD = registerBuilder("medium_cerulean_bud", p -> new TAClusterBlock(4, 3, ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_medium_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> SMALL_CERULEAN_BUD = registerBuilder("small_cerulean_bud", p -> new TAClusterBlock(3, 4, ofFullCopy(Blocks.SMALL_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_small_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> MOONSTONE_CLUSTER = registerBuilder("moonstone_cluster", p -> new TAClusterBlock(7, 3, ofFullCopy(Blocks.AMETHYST_CLUSTER)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_cluster")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> LARGE_MOONSTONE_BUD = registerBuilder("large_moonstone_bud", p -> new TAClusterBlock(5, 3, ofFullCopy(Blocks.LARGE_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_large_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> MEDIUM_MOONSTONE_BUD = registerBuilder("medium_moonstone_bud", p -> new TAClusterBlock(4, 3, ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_medium_bud")).build().register();
    public static final DeferredHolder<Block, TAClusterBlock> SMALL_MOONSTONE_BUD = registerBuilder("small_moonstone_bud", p -> new TAClusterBlock(3, 4, ofFullCopy(Blocks.SMALL_AMETHYST_BUD)))
            .blockstate((ctx, prov) -> registerClusterStates(ctx.get(), prov)).item().model((ctx, prov) -> clusterBlockItem(ctx.get().getBlock(), prov, "ta_small_bud")).build().register();
    public static final DeferredHolder<Block, IndigoMushroomBlock> INDIGO_MUSHROOM = registerNoItemBuilder("indigo_mushroom", p -> new IndigoMushroomBlock(ofFullCopy(Blocks.BROWN_MUSHROOM), TAConfiguredFeatures.HUGE_INDIGO_MUSHROOM))
            .defaultLoot().blockstate((ctx, prov) -> registerPlantStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, IndigoMushroom> INDIGO_MUSHROOM_BLOCK = registerNoItemModelBuilder("indigo_mushroom_block", p -> new IndigoMushroom(ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK).destroyTime(1.0F))).register();
    public static final DeferredHolder<Block, HugeMushroomBlock> INDIGO_MUSHROOM_STEM = registerNoItemModelBuilder("indigo_mushroom_stem", p -> new HugeMushroomBlock(ofFullCopy(Blocks.MUSHROOM_STEM).destroyTime(1.0F))).loot(RegistrateBlockLootTables::dropWhenSilkTouch).register();
    public static final DeferredHolder<Block, Block> INDIGO_MUSHROOM_CRYSTAL = simpleBuilder("indigo_mushroom_crystal", of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.GLASS).lightLevel(s -> 1)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, MoonlightForge> MOONLIGHT_FORGE = registerNoItemModelBuilder("moonlight_forge", p -> new MoonlightForge(defaultStoneProperties(2.0F).mapColor(MapColor.METAL).sound(SoundType.METAL).noOcclusion())).tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).defaultLoot().register();
    public static final DeferredHolder<Block, Block> MOON_GEM = registerNoItemModelBuilder("moon_gem", p -> new Block(defaultStoneProperties(2.0F).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.METAL).noOcclusion())).tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).defaultLoot().register();
    public static final DeferredHolder<Block, TASandBlock> MOON_SAND = registerBuilder("moon_sand", p -> new TASandBlock(14406560, ofFullCopy(Blocks.SAND))).defaultLoot().defaultBlockstate()
            .tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, TABlockTags.AURORIAN_SAND_BLOCK, TABlockTags.AURORIAN_LIGHT_PLANT_MAY_PLACE_ON, BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND).register();
    public static final DeferredHolder<Block, TASandBlock> MOON_SAND_RIVER = registerBuilder("moon_sand_river", p -> new TASandBlock(14406560, ofFullCopy(Blocks.SAND))).tag(BlockTags.SAND).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, Block> MOON_SANDSTONE = simpleBuilder("moon_sandstone", ofFullCopy(Blocks.SANDSTONE)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> CUT_MOON_SANDSTONE = simpleBuilder("cut_moon_sandstone", ofFullCopy(Blocks.CUT_SANDSTONE)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> SMOOTH_MOON_SANDSTONE = simpleBuilder("smooth_moon_sandstone", ofFullCopy(Blocks.SMOOTH_SANDSTONE)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> BRIGHT_MOON_SANDSTONE = simpleBuilder("bright_moon_sandstone", ofFullCopy(Blocks.SANDSTONE)).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, TASandBlock> BRIGHT_MOON_SAND =  registerBuilder("bright_moon_sand", p -> new TASandBlock(14406560, ofFullCopy(Blocks.SAND))).tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES, BlockTags.SAND).defaultLoot().defaultBlockstate().register();
    public static final DeferredHolder<Block, TorchBlock> MOON_TORCH = registerNoItemBuilder("moon_torch", p -> new TorchBlock(ParticleTypes.CLOUD, ofFullCopy(Blocks.TORCH))).tag(BlockTags.WALL_POST_OVERRIDE).defaultLoot()
            .blockstate((ctx, prov) -> registerTorchStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, WallTorchBlock> MOON_WALL_TORCH = registerNoItemBuilder("moon_wall_torch", p -> new WallTorchBlock(
            ParticleTypes.CLOUD, ofFullCopy(Blocks.WALL_TORCH))).loot((ctx, prov) -> ctx.dropOther(prov, TAItems.MOON_TORCH.get())).register();
    public static final DeferredHolder<Block, Scrapper> SCRAPPER = registerBuilder("scrapper", p -> new Scrapper(defaultStoneProperties(2.0F))).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> UMBRA_STONE = simpleBuilder("umbra_stone", defaultStoneProperties(5.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> UMBRA_STONE_CRACKED = simpleBuilder("umbra_stone_cracked", defaultStoneProperties(5.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, Block> UMBRA_STONE_ROOF_TILES = simpleBuilder("umbra_stone_roof_tiles", defaultStoneProperties(5.0F)).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultLoot().register();
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SILENT_TREE_LOG = strippedWood("stripped_silent_tree_log", MapColor.COLOR_BLUE, 2.0F, TABlockTags.SILENT_TREE_LOGS);
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SILENT_TREE_WOOD = strippedWood("stripped_silent_tree_wood", MapColor.COLOR_BLUE, 2.0F, TABlockTags.SILENT_TREE_LOGS);
    public static final DeferredHolder<Block, Block> SILENT_TREE_LEAVES = registerNoItemBuilder("silent_tree_leaves", p -> Blocks.leaves(SoundType.GRASS)).tag(BlockTags.LEAVES)
            .blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), CUTOUT_MIPPED, prov)).item().model((ctx, prov) -> prov.simpleBlockItem(ctx.get().getBlock())).build().register();
    public static final DeferredHolder<Block, Block> SILENT_TREE_PLANKS = simpleBuilder("silent_tree_planks", ofFullCopy(Blocks.OAK_PLANKS)).tag(TABlockTags.AURORIAN_PLANKS).defaultLoot().item().tag(TAItemTags.AURORIAN_PLANKS).build().register();
    public static final DeferredHolder<Block, AxeStrippableBlock> SILENT_TREE_LOG = wood("silent_tree_log", STRIPPED_SILENT_TREE_LOG, MapColor.COLOR_BLUE, 2.0F, TABlockTags.SILENT_TREE_LOGS);
    public static final DeferredHolder<Block, AxeStrippableBlock> SILENT_TREE_WOOD = wood("silent_tree_wood", STRIPPED_SILENT_TREE_WOOD, MapColor.COLOR_BLUE, 2.0F, TABlockTags.SILENT_TREE_LOGS);
    public static final DeferredHolder<Block, SaplingBlock> SILENT_TREE_SAPLING = registerNoItemBuilder("silent_tree_sapling", p -> new SaplingBlock(TATreeGrower.SILENT_TREE, ofFullCopy(Blocks.OAK_SAPLING))).tag(BlockTags.SAPLINGS)
            .defaultLoot().blockstate((ctx, prov) -> registerSaplingStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TorchBlock> SILENT_WOOD_TORCH = registerNoItemBuilder("silent_wood_torch", p -> new TorchBlock(ParticleTypes.FLAME, ofFullCopy(Blocks.TORCH))).tag(BlockTags.WALL_POST_OVERRIDE).defaultLoot()
            .blockstate((ctx, prov) -> registerTorchStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, WallTorchBlock> SILENT_WOOD_WALL_TORCH = registerNoItemBuilder("silent_wood_wall_torch", p -> new WallTorchBlock(
            ParticleTypes.FLAME, ofFullCopy(Blocks.WALL_TORCH))).loot((ctx, prov) -> ctx.dropOther(prov, TAItems.SILENT_WOOD_TORCH.get())).register();
    public static final DeferredHolder<Block, LadderBlock> SILENT_WOOD_LADDER = registerNoItemBuilder("silent_wood_ladder", p -> new LadderBlock(ofFullCopy(Blocks.LADDER))).tag(BlockTags.CLIMBABLE).defaultLoot()
            .blockstate((ctx, prov) -> registerSilentWoodLadderState(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAStandingSignBlock> SILENT_WOOD_SIGN = registerNoItemBuilder("silent_wood_sign", p -> new TAStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.SILENT)).tag(BlockTags.STANDING_SIGNS).register();
    public static final DeferredHolder<Block, TAWallSignBlock> SILENT_WOOD_WALL_SIGN = registerNoItemBuilder("silent_wood_wall_sign", p -> new TAWallSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.SILENT)).tag(BlockTags.WALL_SIGNS).register();
    public static final DeferredHolder<Block, TACeilingHangingSignBlock> SILENT_WOOD_HANGING_SIGN = registerNoItemBuilder("silent_wood_hanging_sign", p -> new TACeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), TAWoodType.SILENT))
            .tag(BlockTags.CEILING_HANGING_SIGNS).blockstate((ctx, prov) -> registerCeilingHangingSignStates(ctx.get(), STRIPPED_SILENT_TREE_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, TAWallHangingSignBlock> SILENT_WOOD_WALL_HANGING_SIGN = registerNoItemBuilder("silent_wood_wall_hanging_sign", p -> new TAWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), TAWoodType.SILENT))
            .tag(BlockTags.WALL_HANGING_SIGNS).blockstate((ctx, prov) -> registerWallHangingSignStates(SILENT_WOOD_HANGING_SIGN.get(), ctx.get(), STRIPPED_SILENT_TREE_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_WEEPING_WILLOW_LOG = strippedWood("stripped_weeping_willow_log", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.WEEPING_WILLOW_LOGS);
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_WEEPING_WILLOW_WOOD = strippedWood("stripped_weeping_willow_wood", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.WEEPING_WILLOW_LOGS);
    public static final DeferredHolder<Block, Block> WEEPING_WILLOW_LEAVES = registerNoItemBuilder("weeping_willow_leaves", p -> Blocks.leaves(SoundType.GRASS)).tag(BlockTags.LEAVES)
            .blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), CUTOUT_MIPPED, prov)).item().model((ctx, prov) -> prov.simpleBlockItem(ctx.get().getBlock())).build().register();
    public static final DeferredHolder<Block, Block> WEEPING_WILLOW_PLANKS = simpleBuilder("weeping_willow_planks", ofFullCopy(Blocks.OAK_PLANKS)).tag(TABlockTags.AURORIAN_PLANKS).defaultLoot().item().tag(TAItemTags.AURORIAN_PLANKS).build().register();
    public static final DeferredHolder<Block, AxeStrippableBlock> WEEPING_WILLOW_LOG = wood("weeping_willow_log", STRIPPED_WEEPING_WILLOW_LOG, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.WEEPING_WILLOW_LOGS);
    public static final DeferredHolder<Block, AxeStrippableBlock> WEEPING_WILLOW_WOOD = wood("weeping_willow_wood", STRIPPED_WEEPING_WILLOW_WOOD, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.WEEPING_WILLOW_LOGS);
    public static final DeferredHolder<Block, TAStandingSignBlock> WEEPING_WILLOW_WOOD_SIGN = registerNoItemBuilder("weeping_willow_wood_sign",
            p -> new TAStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.WEEPING_WILLOW)).tag(BlockTags.STANDING_SIGNS).register();
    public static final DeferredHolder<Block, TAWallSignBlock> WEEPING_WILLOW_WOOD_WALL_SIGN = registerNoItemBuilder("weeping_willow_wood_wall_sign",
            p -> new TAWallSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.WEEPING_WILLOW)).tag(BlockTags.WALL_SIGNS).register();
    public static final DeferredHolder<Block, TACeilingHangingSignBlock> WEEPING_WILLOW_WOOD_HANGING_SIGN = registerNoItemBuilder("weeping_willow_wood_hanging_sign",
            p -> new TACeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), TAWoodType.WEEPING_WILLOW)).tag(BlockTags.CEILING_HANGING_SIGNS)
            .blockstate((ctx, prov) -> registerCeilingHangingSignStates(ctx.get(), STRIPPED_WEEPING_WILLOW_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, TAWallHangingSignBlock> WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN = registerNoItemBuilder("weeping_willow_wood_wall_hanging_sign", p -> new TAWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), TAWoodType.WEEPING_WILLOW))
            .tag(BlockTags.WALL_HANGING_SIGNS).blockstate((ctx, prov) -> registerWallHangingSignStates(WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), ctx.get(), STRIPPED_WEEPING_WILLOW_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_CURTAIN_TREE_LOG = strippedWood("stripped_curtain_tree_log", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURTAIN_TREE_LOGS);
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_CURTAIN_TREE_WOOD = strippedWood("stripped_curtain_tree_wood", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURTAIN_TREE_LOGS);
    public static final DeferredHolder<Block, Block> CURTAIN_TREE_LEAVES = registerNoItemBuilder("curtain_tree_leaves", p -> Blocks.leaves(SoundType.GRASS)).tag(BlockTags.LEAVES)
            .blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), CUTOUT_MIPPED, prov)).item().model((ctx, prov) -> prov.simpleBlockItem(ctx.get().getBlock())).build().register();
    public static final DeferredHolder<Block, Block> CURTAIN_TREE_PLANKS = simpleBuilder("curtain_tree_planks", ofFullCopy(Blocks.OAK_PLANKS)).tag(TABlockTags.AURORIAN_PLANKS).defaultLoot().item().tag(TAItemTags.AURORIAN_PLANKS).build().register();
    public static final DeferredHolder<Block, AxeStrippableBlock> CURTAIN_TREE_LOG = wood("curtain_tree_log", STRIPPED_CURTAIN_TREE_LOG, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURTAIN_TREE_LOGS);
    public static final DeferredHolder<Block, AxeStrippableBlock> CURTAIN_TREE_WOOD = wood("curtain_tree_wood", STRIPPED_CURTAIN_TREE_WOOD, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURTAIN_TREE_LOGS);
    public static final DeferredHolder<Block, SaplingBlock> CURTAIN_TREE_SAPLING = registerNoItemBuilder("curtain_tree_sapling", p -> new SaplingBlock(TATreeGrower.CURTAIN_TREE, ofFullCopy(Blocks.OAK_SAPLING)))
            .tag(BlockTags.SAPLINGS).defaultLoot().blockstate((ctx, prov) -> registerSaplingStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAStandingSignBlock> CURTAIN_WOOD_SIGN = registerNoItemBuilder("curtain_wood_sign", p -> new TAStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.CURTAIN)).tag(BlockTags.STANDING_SIGNS).register();
    public static final DeferredHolder<Block, TAWallSignBlock> CURTAIN_WOOD_WALL_SIGN = registerNoItemBuilder("curtain_wood_wall_sign", p -> new TAWallSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.CURTAIN)).tag(BlockTags.WALL_SIGNS).register();
    public static final DeferredHolder<Block, TACeilingHangingSignBlock> CURTAIN_WOOD_HANGING_SIGN = registerNoItemBuilder("curtain_wood_hanging_sign", p -> new TACeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), TAWoodType.CURTAIN))
            .tag(BlockTags.CEILING_HANGING_SIGNS).blockstate((ctx, prov) -> registerCeilingHangingSignStates(ctx.get(), STRIPPED_CURTAIN_TREE_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, TAWallHangingSignBlock> CURTAIN_WOOD_WALL_HANGING_SIGN = registerNoItemBuilder("curtain_wood_wall_hanging_sign", p -> new TAWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), TAWoodType.CURTAIN))
            .tag(BlockTags.WALL_HANGING_SIGNS).blockstate((ctx, prov) -> registerWallHangingSignStates(CURTAIN_WOOD_HANGING_SIGN.get(), ctx.get(), STRIPPED_CURTAIN_TREE_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_CURSED_FROST_TREE_LOG = strippedWood("stripped_cursed_frost_tree_log", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURSED_FROST_TREE_LOGS);
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_CURSED_FROST_TREE_WOOD = strippedWood("stripped_cursed_frost_tree_wood", MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURSED_FROST_TREE_LOGS);
    public static final DeferredHolder<Block, Block> CURSED_FROST_TREE_LEAVES = registerNoItemBuilder("cursed_frost_tree_leaves", p -> Blocks.leaves(SoundType.GRASS)).tag(BlockTags.LEAVES)
            .blockstate((ctx, prov) -> simpleBlockWithRenderType(ctx.get(), CUTOUT_MIPPED, prov)).item().model((ctx, prov) -> prov.simpleBlockItem(ctx.get().getBlock())).build().register();
    public static final DeferredHolder<Block, Block> CURSED_FROST_TREE_PLANKS = simpleBuilder("cursed_frost_tree_planks", ofFullCopy(Blocks.OAK_PLANKS)).tag(TABlockTags.AURORIAN_PLANKS).defaultLoot().item().tag(TAItemTags.AURORIAN_PLANKS).build().register();
    public static final DeferredHolder<Block, AxeStrippableBlock> CURSED_FROST_TREE_LOG = wood("cursed_frost_tree_log", STRIPPED_CURSED_FROST_TREE_LOG, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURSED_FROST_TREE_LOGS);
    public static final DeferredHolder<Block, AxeStrippableBlock> CURSED_FROST_TREE_WOOD = wood("cursed_frost_tree_wood", STRIPPED_CURSED_FROST_TREE_WOOD, MapColor.COLOR_LIGHT_GRAY, 2.0F, TABlockTags.CURSED_FROST_TREE_LOGS);
    public static final DeferredHolder<Block, SaplingBlock> CURSED_FROST_TREE_SAPLING = registerNoItemBuilder("cursed_frost_tree_sapling", p -> new SaplingBlock(TATreeGrower.CURSED_FROST_TREE, ofFullCopy(Blocks.OAK_SAPLING)))
            .tag(BlockTags.SAPLINGS).defaultLoot().blockstate((ctx, prov) -> registerSaplingStates(ctx.get(), prov)).item().model((ctx, prov) -> crossingBlockItem(ctx.get().getBlock(), prov)).build().register();
    public static final DeferredHolder<Block, TAStandingSignBlock> CURSED_FROST_WOOD_SIGN = registerNoItemBuilder("cursed_frost_wood_sign", p -> new TAStandingSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.CURSED_FROST)).tag(BlockTags.STANDING_SIGNS).register();
    public static final DeferredHolder<Block, TAWallSignBlock> CURSED_FROST_WOOD_WALL_SIGN = registerNoItemBuilder("cursed_frost_wood_wall_sign", p -> new TAWallSignBlock(ofFullCopy(Blocks.OAK_SIGN), TAWoodType.CURSED_FROST)).tag(BlockTags.WALL_SIGNS).register();
    public static final DeferredHolder<Block, TACeilingHangingSignBlock> CURSED_FROST_WOOD_HANGING_SIGN = registerNoItemBuilder("cursed_frost_wood_hanging_sign", p -> new TACeilingHangingSignBlock(ofFullCopy(Blocks.OAK_HANGING_SIGN), TAWoodType.CURTAIN))
            .tag(BlockTags.CEILING_HANGING_SIGNS).blockstate((ctx, prov) -> registerCeilingHangingSignStates(ctx.get(), STRIPPED_CURSED_FROST_TREE_LOG.get(), prov)).register();
    public static final DeferredHolder<Block, TAWallHangingSignBlock> CURSED_FROST_WOOD_WALL_HANGING_SIGN = registerNoItemBuilder("cursed_frost_wood_wall_hanging_sign", p -> new TAWallHangingSignBlock(ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), TAWoodType.CURTAIN))
            .tag(BlockTags.WALL_HANGING_SIGNS).blockstate((ctx, prov) -> registerWallHangingSignStates(CURSED_FROST_WOOD_HANGING_SIGN.get(), ctx.get(), STRIPPED_CURSED_FROST_TREE_LOG.get(), prov)).register();

    /**
     * Potted Flower Blocks
     */
    public static final DeferredHolder<Block, Block> POTTED_AURORIAN_FLOWER_1 = flowerPot(NEBULA_BLOSSOM_CLUSTER);
    public static final DeferredHolder<Block, Block> POTTED_AURORIAN_FLOWER_2 = flowerPot(MOON_FROST_FLOWER);
    public static final DeferredHolder<Block, Block> POTTED_AURORIAN_FLOWER_3 = flowerPot(VOID_CANDLE_FLOWER);
    public static final DeferredHolder<Block, Block> POTTED_EQUINOX_FLOWER = flowerPot(EQUINOX_FLOWER);
    public static final DeferredHolder<Block, Block> POTTED_WICK_GRASS = flowerPot(WICK_GRASS);
    public static final DeferredHolder<Block, Block> POTTED_LAVENDER_PLANT = flowerPot(LAVENDER_PLANT);
    public static final DeferredHolder<Block, Block> POTTED_PETUNIA_PLANT = flowerPot(PETUNIA_PLANT);
    public static final DeferredHolder<Block, Block> POTTED_AURORIAN_GRASS = flowerPot(AURORIAN_GRASS);
    public static final DeferredHolder<Block, Block> POTTED_SILENT_TREE_SAPLING = flowerPot(SILENT_TREE_SAPLING);
    public static final DeferredHolder<Block, Block> POTTED_AURORIAN_GRASS_LIGHT = flowerPot(AURORIAN_GRASS_LIGHT);
    public static final DeferredHolder<Block, Block> POTTED_CURTAIN_TREE_SAPLING = flowerPot(CURTAIN_TREE_SAPLING);
    public static final DeferredHolder<Block, Block> POTTED_CURSED_FROST_TREE_SAPLING = flowerPot(CURSED_FROST_TREE_SAPLING);

    /**
     * Vertical Stair Blocks
     */
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_STONE_STAIRS = verticalStair("vertical_aurorian_stone_stairs", AURORIAN_STONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_STONE_BRICK_STAIRS = verticalStair("vertical_aurorian_stone_brick_stairs", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_COBBLESTONE_STAIRS = verticalStair("vertical_aurorian_cobblestone_stairs", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_GRANITE_STAIRS = verticalStair("vertical_aurorian_granite_stairs", AURORIAN_GRANITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_DIORITE_STAIRS = verticalStair("vertical_aurorian_diorite_stairs", AURORIAN_DIORITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_ANDESITE_STAIRS = verticalStair("vertical_aurorian_andesite_stairs", AURORIAN_ANDESITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_RUNE_STONE_STAIRS = verticalStair("vertical_rune_stone_stairs", RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_SMOOTH_RUNE_STONE_STAIRS = verticalStair("vertical_smooth_rune_stone_stairs", SMOOTH_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CHISELED_RUNE_STONE_STAIRS = verticalStair("vertical_chiseled_rune_stone_stairs", CHISELED_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_MOONSILVER_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_moonsilver_castle_rune_stone_stairs", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CERULEAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_MOON_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_moonsilver_castle_rune_stone_stairs", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_luminous_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_TRANSPARENT_RUNE_STONE_STAIRS = verticalStair("vertical_transparent_rune_stone_stairs", TRANSPARENT_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_UMBRA_CASTLE_RUNE_STONE_STAIRS = verticalStair("vertical_umbra_castle_rune_stone_stairs", UMBRA_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_dark_stone_brick_stairs", DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_SMOOTH_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_smooth_dark_stone_brick_stairs", SMOOTH_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CHISELED_DARK_STONE_BRICK_STAIRS = verticalStair("vertical_chiseled_dark_stone_brick_stairs", CHISELED_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_moon_temple_brick_stairs", MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_smooth_moon_temple_brick_stairs", SMOOTH_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CHISELED_MOON_TEMPLE_BRICK_STAIRS = verticalStair("vertical_chiseled_moon_temple_brick_stairs", CHISELED_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_UMBRA_STONE_STAIRS = verticalStair("vertical_umbra_stone_stairs", UMBRA_STONE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_UMBRA_STONE_CRACKED_STAIRS = verticalStair("vertical_umbra_stone_cracked_stairs", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_UMBRA_STONE_ROOF_STAIRS = verticalStair("vertical_umbra_stone_roof_stairs", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_SILENT_WOOD_STAIRS = verticalStair("vertical_silent_wood_stairs", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_WEEPING_WILLOW_STAIRS = verticalStair("vertical_weeping_willow_stairs", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CURTAIN_WOOD_STAIRS = verticalStair("vertical_curtain_wood_stairs", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_CURSED_FROST_WOOD_STAIRS = verticalStair("vertical_cursed_frost_wood_stairs", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_AURORIAN_PERIDOTITE_STAIRS = verticalStair("vertical_aurorian_peridotite_stairs", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalStairBlock> VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_STAIRS = verticalStair("vertical_smooth_aurorian_peridotite_stairs", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();

    /**
     * Vertical Slab Blocks
     */
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_STONE_SLAB = verticalSlab("vertical_aurorian_stone_slab", AURORIAN_STONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_STONE_BRICK_SLAB = verticalSlab("vertical_aurorian_stone_brick_slab", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_COBBLESTONE_SLAB = verticalSlab("vertical_aurorian_cobblestone_slab", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_GRANITE_SLAB = verticalSlab("vertical_aurorian_granite_slab", AURORIAN_GRANITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_DIORITE_SLAB = verticalSlab("vertical_aurorian_diorite_slab", AURORIAN_DIORITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_ANDESITE_SLAB = verticalSlab("vertical_aurorian_andesite_slab", AURORIAN_ANDESITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_RUNE_STONE_SLAB = verticalSlab("vertical_rune_stone_slab", RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_SMOOTH_RUNE_STONE_SLAB = verticalSlab("vertical_smooth_rune_stone_slab", SMOOTH_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CHISELED_RUNE_STONE_SLAB = verticalSlab("vertical_chiseled_rune_stone_slab", CHISELED_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_MOONSILVER_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_moonsilver_castle_rune_stone_slab", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CERULEAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_MOON_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_moonsilver_castle_rune_stone_slab", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_luminous_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_TRANSPARENT_RUNE_STONE_SLAB = verticalSlab("vertical_transparent_rune_stone_slab", TRANSPARENT_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_UMBRA_CASTLE_RUNE_STONE_SLAB = verticalSlab("vertical_umbra_castle_rune_stone_slab", UMBRA_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_dark_stone_brick_slab", DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_SMOOTH_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_smooth_dark_stone_brick_slab", SMOOTH_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CHISELED_DARK_STONE_BRICK_SLAB = verticalSlab("vertical_chiseled_dark_stone_brick_slab", CHISELED_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_moon_temple_brick_slab", MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_SMOOTH_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_smooth_moon_temple_brick_slab", SMOOTH_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CHISELED_MOON_TEMPLE_BRICK_SLAB = verticalSlab("vertical_chiseled_moon_temple_brick_slab", CHISELED_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_UMBRA_STONE_SLAB = verticalSlab("vertical_umbra_stone_slab", UMBRA_STONE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_UMBRA_STONE_CRACKED_SLAB = verticalSlab("vertical_umbra_stone_cracked_slab", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_UMBRA_STONE_ROOF_SLAB = verticalSlab("vertical_umbra_stone_roof_slab", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_SILENT_WOOD_SLAB = verticalSlab("vertical_silent_wood_slab", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_WEEPING_WILLOW_SLAB = verticalSlab("vertical_weeping_willow_slab", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CURTAIN_WOOD_SLAB = verticalSlab("vertical_curtain_wood_slab", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_CURSED_FROST_WOOD_SLAB = verticalSlab("vertical_cursed_frost_wood_slab", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_AURORIAN_PERIDOTITE_SLAB = verticalSlab("vertical_aurorian_peridotite_slab", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, VerticalSlabBlock> VERTICAL_SMOOTH_AURORIAN_PERIDOTITE_SLAB = verticalSlab("vertical_smooth_aurorian_peridotite_slab", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();

    /**
     * Pressure Plate Blocks
     */
    public static final DeferredHolder<Block, PressurePlateBlock> SILENT_WOOD_PRESSURE_PLATE = pressurePlate("silent_wood_pressure_plate", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TABlockSetType.SILENT, BlockTags.WOODEN_PRESSURE_PLATES);
    public static final DeferredHolder<Block, PressurePlateBlock> WEEPING_WILLOW_PRESSURE_PLATE = pressurePlate("weeping_willow_pressure_plate", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TABlockSetType.WEEPING_WILLOW, BlockTags.WOODEN_PRESSURE_PLATES);
    public static final DeferredHolder<Block, PressurePlateBlock> CURTAIN_WOOD_PRESSURE_PLATE = pressurePlate("curtain_wood_pressure_plate", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TABlockSetType.CURTAIN, BlockTags.WOODEN_PRESSURE_PLATES);
    public static final DeferredHolder<Block, PressurePlateBlock> CURSED_FROST_WOOD_PRESSURE_PLATE = pressurePlate("cursed_frost_wood_pressure_plate", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TABlockSetType.CURSED_FROST, BlockTags.WOODEN_PRESSURE_PLATES);

    /**
     * Fence Gate Blocks
     */
    public static final DeferredHolder<Block, FenceGateBlock> SILENT_WOOD_FENCE_GATE = fenceGate("silent_wood_fence_gate", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_FENCE_GATE), TAWoodType.SILENT);
    public static final DeferredHolder<Block, FenceGateBlock> WEEPING_WILLOW_FENCE_GATE = fenceGate("weeping_willow_fence_gate", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_FENCE_GATE), TAWoodType.WEEPING_WILLOW);
    public static final DeferredHolder<Block, FenceGateBlock> CURTAIN_WOOD_FENCE_GATE = fenceGate("curtain_wood_fence_gate", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_FENCE_GATE), TAWoodType.CURTAIN);
    public static final DeferredHolder<Block, FenceGateBlock> CURSED_FROST_WOOD_FENCE_GATE = fenceGate("cursed_frost_wood_fence_gate", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_FENCE_GATE), TAWoodType.CURSED_FROST);

    /**
     * Trapdoor Blocks
     */
    public static final DeferredHolder<Block, TrapDoorBlock> SILENT_WOOD_TRAPDOOR = trapdoor("silent_wood_trapdoor", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_TRAPDOOR), TABlockSetType.SILENT, BlockTags.WOODEN_TRAPDOORS);
    public static final DeferredHolder<Block, TrapDoorBlock> WEEPING_WILLOW_TRAPDOOR = trapdoor("weeping_willow_trapdoor", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_TRAPDOOR), TABlockSetType.WEEPING_WILLOW, BlockTags.WOODEN_TRAPDOORS);
    public static final DeferredHolder<Block, TrapDoorBlock> CURTAIN_WOOD_TRAPDOOR = trapdoor("curtain_wood_trapdoor", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_TRAPDOOR), TABlockSetType.CURTAIN, BlockTags.WOODEN_TRAPDOORS);
    public static final DeferredHolder<Block, TrapDoorBlock> CURSED_FROST_WOOD_TRAPDOOR = trapdoor("cursed_frost_wood_trapdoor", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_TRAPDOOR), TABlockSetType.CURSED_FROST, BlockTags.WOODEN_TRAPDOORS);

    /**
     * Button Blocks
     */
    public static final DeferredHolder<Block, ButtonBlock> SILENT_WOOD_BUTTON = button("silent_wood_button", SILENT_TREE_PLANKS, true, TABlockSetType.SILENT, BlockTags.WOODEN_BUTTONS);
    public static final DeferredHolder<Block, ButtonBlock> WEEPING_WILLOW_BUTTON = button("weeping_willow_button", WEEPING_WILLOW_PLANKS, true, TABlockSetType.WEEPING_WILLOW, BlockTags.WOODEN_BUTTONS);
    public static final DeferredHolder<Block, ButtonBlock> CURTAIN_WOOD_BUTTON = button("curtain_wood_button", CURTAIN_TREE_PLANKS, true, TABlockSetType.CURTAIN, BlockTags.WOODEN_BUTTONS);
    public static final DeferredHolder<Block, ButtonBlock> CURSED_FROST_WOOD_BUTTON = button("cursed_frost_wood_button", CURSED_FROST_TREE_PLANKS, true, TABlockSetType.CURSED_FROST, BlockTags.WOODEN_BUTTONS);

    /**
     * Stair Blocks
     */
    public static final DeferredHolder<Block, StairBlock> AURORIAN_STONE_STAIRS = stair("aurorian_stone_stairs", AURORIAN_STONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_STONE_BRICK_STAIRS = stair("aurorian_stone_brick_stairs", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_COBBLESTONE_STAIRS = stair("aurorian_cobblestone_stairs", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_GRANITE_STAIRS = stair("aurorian_granite_stairs", AURORIAN_GRANITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_DIORITE_STAIRS = stair("aurorian_diorite_stairs", AURORIAN_DIORITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_ANDESITE_STAIRS = stair("aurorian_andesite_stairs", AURORIAN_ANDESITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, StairBlock> RUNE_STONE_STAIRS = stair("rune_stone_stairs", RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> SMOOTH_RUNE_STONE_STAIRS = stair("smooth_rune_stone_stairs", SMOOTH_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> CHISELED_RUNE_STONE_STAIRS = stair("chiseled_rune_stone_stairs", CHISELED_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_CASTLE_RUNE_STONE_STAIRS = stair("aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> MOONSILVER_CASTLE_RUNE_STONE_STAIRS = stair("moonsilver_castle_rune_stone_stairs", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> CERULEAN_CASTLE_RUNE_STONE_STAIRS = stair("cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = stair("crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> MOON_CASTLE_RUNE_STONE_STAIRS = stair("moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_STAIRS = stair("luminous_aurorian_castle_rune_stone_stairs", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE_STAIRS = stair("luminous_moonsilver_castle_rune_stone_stairs", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_STAIRS = stair("luminous_cerulean_castle_rune_stone_stairs", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_STAIRS = stair("luminous_crystalline_castle_rune_stone_stairs", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> LUMINOUS_MOON_CASTLE_RUNE_STONE_STAIRS = stair("luminous_moon_castle_rune_stone_stairs", MOON_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> TRANSPARENT_RUNE_STONE_STAIRS = stair("transparent_rune_stone_stairs", TRANSPARENT_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> UMBRA_CASTLE_RUNE_STONE_STAIRS = stair("umbra_castle_rune_stone_stairs", UMBRA_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> DARK_STONE_BRICK_STAIRS = stair("dark_stone_brick_stairs", DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> SMOOTH_DARK_STONE_BRICK_STAIRS = stair("smooth_dark_stone_brick_stairs", SMOOTH_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> CHISELED_DARK_STONE_BRICK_STAIRS = stair("chiseled_dark_stone_brick_stairs", CHISELED_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> MOON_TEMPLE_BRICK_STAIRS = stair("moon_temple_brick_stairs", MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> SMOOTH_MOON_TEMPLE_BRICK_STAIRS = stair("smooth_moon_temple_brick_stairs", SMOOTH_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> CHISELED_MOON_TEMPLE_BRICK_STAIRS = stair("chiseled_moon_temple_brick_stairs", CHISELED_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, StairBlock> UMBRA_STONE_STAIRS = stair("umbra_stone_stairs", UMBRA_STONE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, StairBlock> UMBRA_STONE_CRACKED_STAIRS = stair("umbra_stone_cracked_stairs", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, StairBlock> UMBRA_STONE_ROOF_STAIRS = stair("umbra_stone_roof_stairs", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, StairBlock> SILENT_WOOD_STAIRS = stair("silent_wood_stairs", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, StairBlock> WEEPING_WILLOW_STAIRS = stair("weeping_willow_stairs", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, StairBlock> CURTAIN_WOOD_STAIRS = stair("curtain_wood_stairs", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, StairBlock> CURSED_FROST_WOOD_STAIRS = stair("cursed_frost_wood_stairs", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, StairBlock> AURORIAN_PERIDOTITE_STAIRS = stair("aurorian_peridotite_stairs", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, StairBlock> SMOOTH_AURORIAN_PERIDOTITE_STAIRS = stair("smooth_aurorian_peridotite_stairs", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();

    /**
     * Fence Blocks
     */
    public static final DeferredHolder<Block, FenceBlock> SILENT_WOOD_FENCE = fence("silent_wood_fence", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), BlockTags.WOODEN_FENCES);
    public static final DeferredHolder<Block, FenceBlock> WEEPING_WILLOW_FENCE = fence("weeping_willow_fence", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), BlockTags.WOODEN_FENCES);
    public static final DeferredHolder<Block, FenceBlock> CURTAIN_WOOD_FENCE = fence("curtain_wood_fence", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), BlockTags.WOODEN_FENCES);
    public static final DeferredHolder<Block, FenceBlock> CURSED_FROST_WOOD_FENCE = fence("cursed_frost_wood_fence", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), BlockTags.WOODEN_FENCES);

    /**
     * Door Blocks
     */
    public static final DeferredHolder<Block, DoorBlock> SILENT_WOOD_DOOR = door("silent_wood_door", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_DOOR), TABlockSetType.SILENT, BlockTags.WOODEN_DOORS);
    public static final DeferredHolder<Block, DoorBlock> WEEPING_WILLOW_DOOR = door("weeping_willow_door", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_DOOR), TABlockSetType.WEEPING_WILLOW, BlockTags.WOODEN_DOORS);
    public static final DeferredHolder<Block, DoorBlock> CURTAIN_WOOD_DOOR = door("curtain_wood_door", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_DOOR), TABlockSetType.CURTAIN, BlockTags.WOODEN_DOORS);
    public static final DeferredHolder<Block, DoorBlock> CURSED_FROST_WOOD_DOOR = door("cursed_frost_wood_door", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_DOOR), TABlockSetType.CURSED_FROST, BlockTags.WOODEN_DOORS);

    /**
     * Slab Blocks
     */
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_STONE_SLAB = slab("aurorian_stone_slab", AURORIAN_STONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_STONE_BRICK_SLAB = slab("aurorian_stone_brick_slab", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_COBBLESTONE_SLAB = slab("aurorian_cobblestone_slab", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_GRANITE_SLAB = slab("aurorian_granite_slab", AURORIAN_GRANITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_DIORITE_SLAB = slab("aurorian_diorite_slab", AURORIAN_DIORITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_ANDESITE_SLAB = slab("aurorian_andesite_slab", AURORIAN_ANDESITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> RUNE_STONE_SLAB = slab("rune_stone_slab", RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> SMOOTH_RUNE_STONE_SLAB = slab("smooth_rune_stone_slab", SMOOTH_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> CHISELED_RUNE_STONE_SLAB = slab("chiseled_rune_stone_slab", CHISELED_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_CASTLE_RUNE_STONE_SLAB = slab("aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> MOONSILVER_CASTLE_RUNE_STONE_SLAB = slab("moonsilver_castle_rune_stone_slab", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> CERULEAN_CASTLE_RUNE_STONE_SLAB = slab("cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = slab("crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> MOON_CASTLE_RUNE_STONE_SLAB = slab("moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_SLAB = slab("luminous_aurorian_castle_rune_stone_slab", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE_SLAB = slab("luminous_moonsilver_castle_rune_stone_slab", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_SLAB = slab("luminous_cerulean_castle_rune_stone_slab", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_SLAB = slab("luminous_crystalline_castle_rune_stone_slab", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> LUMINOUS_MOON_CASTLE_RUNE_STONE_SLAB = slab("luminous_moon_castle_rune_stone_slab", MOON_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> TRANSPARENT_RUNE_STONE_SLAB = slab("transparent_rune_stone_slab", TRANSPARENT_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> UMBRA_CASTLE_RUNE_STONE_SLAB = slab("umbra_castle_rune_stone_slab", UMBRA_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> DARK_STONE_BRICK_SLAB = slab("dark_stone_brick_slab", DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> SMOOTH_DARK_STONE_BRICK_SLAB = slab("smooth_dark_stone_brick_slab", SMOOTH_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> CHISELED_DARK_STONE_BRICK_SLAB = slab("chiseled_dark_stone_brick_slab", CHISELED_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> MOON_TEMPLE_BRICK_SLAB = slab("moon_temple_brick_slab", MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> SMOOTH_MOON_TEMPLE_BRICK_SLAB = slab("smooth_moon_temple_brick_slab", SMOOTH_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> CHISELED_MOON_TEMPLE_BRICK_SLAB = slab("chiseled_moon_temple_brick_slab", CHISELED_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, SlabBlock> UMBRA_STONE_SLAB = slab("umbra_stone_slab", UMBRA_STONE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> UMBRA_STONE_CRACKED_SLAB = slab("umbra_stone_cracked_slab", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> UMBRA_STONE_ROOF_SLAB = slab("umbra_stone_roof_slab", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> SILENT_WOOD_SLAB = slab("silent_wood_slab", SILENT_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, SlabBlock> WEEPING_WILLOW_SLAB = slab("weeping_willow_slab", WEEPING_WILLOW_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, SlabBlock> CURTAIN_WOOD_SLAB = slab("curtain_wood_slab", CURTAIN_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, SlabBlock> CURSED_FROST_WOOD_SLAB = slab("cursed_frost_wood_slab", CURSED_FROST_TREE_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), TAFeatureFlags.WOOD_MATERIAL).register();
    public static final DeferredHolder<Block, SlabBlock> AURORIAN_PERIDOTITE_SLAB = slab("aurorian_peridotite_slab", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, SlabBlock> SMOOTH_AURORIAN_PERIDOTITE_SLAB = slab("smooth_aurorian_peridotite_slab", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();

    /**
     * Wall Blocks
     */
    public static final DeferredHolder<Block, WallBlock> AURORIAN_STONE_WALL = wall("aurorian_stone_wall", AURORIAN_STONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_STONE_BRICK_WALL = wall("aurorian_stone_brick_wall", AURORIAN_STONE_BRICKS, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_COBBLESTONE_WALL = wall("aurorian_cobblestone_wall", AURORIAN_COBBLESTONE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_GRANITE_WALL = wall("aurorian_granite_wall", AURORIAN_GRANITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_DIORITE_WALL = wall("aurorian_diorite_wall", AURORIAN_DIORITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_ANDESITE_WALL = wall("aurorian_andesite_wall", AURORIAN_ANDESITE, defaultStoneProperties(2.0F)).register();
    public static final DeferredHolder<Block, WallBlock> RUNE_STONE_WALL = wall("rune_stone_wall", RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> SMOOTH_RUNE_STONE_WALL = wall("smooth_rune_stone_wall", SMOOTH_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> CHISELED_RUNE_STONE_WALL = wall("chiseled_rune_stone_wall", CHISELED_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_CASTLE_RUNE_STONE_WALL = wall("aurorian_castle_rune_stone_wall", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> MOONSILVER_CASTLE_RUNE_STONE_WALL = wall("moonsilver_castle_rune_stone_wall", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> CERULEAN_CASTLE_RUNE_STONE_WALL = wall("cerulean_castle_rune_stone_wall", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> CRYSTALLINE_CASTLE_RUNE_STONE_WALL = wall("crystalline_castle_rune_stone_wall", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> MOON_CASTLE_RUNE_STONE_WALL = wall("moon_castle_rune_stone_wall", MOON_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> LUMINOUS_AURORIAN_CASTLE_RUNE_STONE_WALL = wall("luminous_aurorian_castle_rune_stone_wall", AURORIAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> LUMINOUS_MOONSILVER_CASTLE_RUNE_STONE_WALL = wall("luminous_moonsilver_castle_rune_stone_wall", MOONSILVER_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> LUMINOUS_CERULEAN_CASTLE_RUNE_STONE_WALL = wall("luminous_cerulean_castle_rune_stone_wall", CERULEAN_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE_WALL = wall("luminous_crystalline_castle_rune_stone_wall", CRYSTALLINE_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> LUMINOUS_MOON_CASTLE_RUNE_STONE_WALL = wall("luminous_moon_castle_rune_stone_wall", MOON_CASTLE_RUNE_STONE, runestoneProperties(), TAFeatureFlags.EMISSIVITY).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> TRANSPARENT_RUNE_STONE_WALL = wall("transparent_rune_stone_wall", TRANSPARENT_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> UMBRA_CASTLE_RUNE_STONE_WALL = wall("umbra_castle_rune_stone_wall", UMBRA_CASTLE_RUNE_STONE, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> DARK_STONE_BRICK_WALL = wall("dark_stone_brick_wall", DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> SMOOTH_DARK_STONE_BRICK_WALL = wall("smooth_dark_stone_brick_wall", SMOOTH_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> CHISELED_DARK_STONE_BRICK_WALL = wall("chiseled_dark_stone_brick_wall", CHISELED_DARK_STONE_BRICKS, runestoneProperties()).tag(TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> MOON_TEMPLE_BRICK_WALL = wall("moon_temple_brick_wall", MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> SMOOTH_MOON_TEMPLE_BRICK_WALL = wall("smooth_moon_temple_brick_wall", SMOOTH_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> CHISELED_MOON_TEMPLE_BRICK_WALL = wall("chiseled_moon_temple_brick_wall", CHISELED_MOON_TEMPLE_BRICKS, runestoneProperties()).tag(TABlockTags.MOON_TEMPLE_BLOCKS, TABlockTags.DUNGEON_BLOCKS).register();
    public static final DeferredHolder<Block, WallBlock> UMBRA_STONE_WALL = wall("umbra_stone_wall", UMBRA_STONE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, WallBlock> UMBRA_STONE_CRACKED_WALL = wall("umbra_stone_cracked_wall", UMBRA_STONE_CRACKED, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, WallBlock> UMBRA_STONE_ROOF_WALL = wall("umbra_stone_roof_wall", UMBRA_STONE_ROOF_TILES, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, WallBlock> AURORIAN_PERIDOTITE_WALL = wall("aurorian_peridotite_wall", AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();
    public static final DeferredHolder<Block, WallBlock> SMOOTH_AURORIAN_PERIDOTITE_WALL = wall("smooth_aurorian_peridotite_wall", SMOOTH_AURORIAN_PERIDOTITE, defaultStoneProperties(5.0F)).register();

    /**
     * Misc
     */
    public static final DeferredHolder<Block, TempBarrier> TEMP_BARRIER = registerNoItemBuilder("temp_barrier", TempBarrier::new).register();
    public static final DeferredHolder<Block, TrapHoleRestorer> TRAP_HOLE_RESTORER = registerNoItemBuilder("trap_hole_restorer", TrapHoleRestorer::new).register();
    public static final DeferredHolder<Block, AstrologyTable> ASTROLOGY_TABLE = registerNoItemBuilder("astrology_table", AstrologyTable::new).defaultLoot().register();
    public static final DeferredHolder<Block, SacrificeTable> SACRIFICE_TABLE = registerNoItemBuilder("sacrifice_table", SacrificeTable::new).tag(BlockTags.MINEABLE_WITH_AXE).defaultLoot().register();
    public static final DeferredHolder<Block, MysteriumWoolBed> MYSTERIUM_WOOL_BED = registerNoItemBuilder("mysterium_wool_bed", MysteriumWoolBed::new)
            .tag(BlockTags.BEDS).item().model((ctx, prov) -> prov.basicItem(ctx.get())).build().register();
    public static final DeferredHolder<Block, LunarSourcePrism> LUNAR_SOURCE_PRISM = registerNoItemBuilder("lunar_source_prism", LunarSourcePrism::new).register();
    public static final DeferredHolder<Block, LunarDeflector> LUNAR_DEFLECTOR = registerNoItemBuilder("lunar_deflector", LunarDeflector::new).register();
    public static final DeferredHolder<Block, LunarSplitter> LUNAR_SPLITTER = registerNoItemBuilder("lunar_splitter", LunarSplitter::new).register();
    public static final DeferredHolder<Block, ReceivingCrystal> RECEIVING_CRYSTAL = registerNoItemBuilder("receiving_crystal", ReceivingCrystal::new).register();
    public static final DeferredHolder<Block, SilentCampfire> SILENT_CAMPFIRE = registerNoItemModelBuilder("silent_campfire", SilentCampfire::new)
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch).item().model((ctx, prov) -> prov.basicItem(ctx.get())).build().register();
    public static final DeferredHolder<Block, AlchemyTable> ALCHEMY_TABLE = registerNoItemModelBuilder("alchemy_table", AlchemyTable::new).defaultLoot().register();
    public static final DeferredHolder<Block, RelicTable> RELIC_TABLE = registerNoItemModelBuilder("relic_table", RelicTable::new).defaultLoot().register();

    public static Properties defaultStoneProperties(float destroyTime) {
        return of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE)
                .requiresCorrectToolForDrops().destroyTime(destroyTime).explosionResistance(destroyTime * 5.0F);
    }

    public static Properties runestoneProperties(Properties properties) {
        return properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().destroyTime(-1.0F).explosionResistance(3600000.0F);
    }

    public static Properties runestoneProperties() {
        return runestoneProperties(of());
    }

}