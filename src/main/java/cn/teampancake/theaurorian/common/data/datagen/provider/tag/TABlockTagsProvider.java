package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.base.VerticalSlabBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.base.VerticalStairBlockWithBase;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class TABlockTagsProvider extends BlockTagsProvider {

    public TABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES).addTag(TABlockTags.AURORIAN_GRASS_BLOCK)
                .add(TABlocks.AURORIAN_STONE.get(), TABlocks.AURORIAN_DIRT.get(), TABlocks.AURORIAN_GRANITE.get(),
                TABlocks.AURORIAN_DIORITE.get(), TABlocks.AURORIAN_ANDESITE.get(),
                TABlocks.MOON_SAND.get(), TABlocks.BRIGHT_MOON_SAND.get(),
                TABlocks.MOON_SANDSTONE.get(), TABlocks.CUT_MOON_SANDSTONE.get(),
                TABlocks.SMOOTH_MOON_SANDSTONE.get(), TABlocks.BRIGHT_MOON_SANDSTONE.get());
        this.tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON)
                .add(TABlocks.RUNE_STONE.get()).add(TABlocks.MOON_TEMPLE_BRICKS.get())
                .add(TABlocks.DARK_STONE_BRICKS.get()).add(TABlocks.DARK_STONE_FANCY.get())
                .add(TABlocks.DARK_STONE_LAYERS.get()).add(TABlocks.SMOOTH_DARK_STONE_BRICKS.get())
                .add(TABlocks.RUNE_STONE_LAMP.get()).add(TABlocks.CHISELED_DARK_STONE_BRICKS.get())
                .add(TABlocks.MOON_TEMPLE_LAMP.get()).add(TABlocks.DARK_STONE_LAMP.get())
                .add(TABlocks.SMOOTH_RUNE_STONE.get()).add(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.tag(TABlockTags.RUNE_STONE_BLOCK).add(TABlocks.RUNE_STONE.get(),
                TABlocks.AURORIAN_CASTLE_RUNE_STONE.get(), TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get(),
                TABlocks.CERULEAN_CASTLE_RUNE_STONE.get(), TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get(),
                TABlocks.MOON_CASTLE_RUNE_STONE.get(), TABlocks.TRANSPARENT_RUNE_STONE.get(),
                TABlocks.UMBRA_CASTLE_RUNE_STONE.get());
        this.tag(TABlockTags.CAN_HURT_SICKLE).addTag(BlockTags.LEAVES).addTag(BlockTags.WOOL)
                .add(Blocks.COBWEB, Blocks.GRASS, Blocks.FERN, Blocks.DEAD_BUSH,
                        Blocks.HANGING_ROOTS, Blocks.VINE, Blocks.TRIPWIRE);
        this.tag(TABlockTags.SILENT_TREE_LOGS).add(TABlocks.STRIPPED_SILENT_TREE_LOG.get(),
                TABlocks.STRIPPED_SILENT_TREE_WOOD.get(), TABlocks.SILENT_TREE_LOG.get(),
                TABlocks.SILENT_TREE_WOOD.get());
        this.tag(TABlockTags.WEEPING_WILLOW_LOGS).add(TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get(),
                TABlocks.STRIPPED_WEEPING_WILLOW_WOOD.get(), TABlocks.WEEPING_WILLOW_LOG.get(),
                TABlocks.WEEPING_WILLOW_WOOD.get());
        this.tag(TABlockTags.CURTAIN_TREE_LOGS).add(TABlocks.STRIPPED_CURTAIN_TREE_LOG.get(),
                TABlocks.STRIPPED_CURTAIN_TREE_WOOD.get(), TABlocks.CURTAIN_TREE_LOG.get(),
                TABlocks.CURTAIN_TREE_WOOD.get());
        this.tag(TABlockTags.CURSED_FROST_TREE_LOGS).add(TABlocks.STRIPPED_CURSED_FROST_TREE_LOG.get(),
                TABlocks.STRIPPED_CURSED_FROST_TREE_WOOD.get(), TABlocks.CURSED_FROST_TREE_LOG.get(),
                TABlocks.CURSED_FROST_TREE_WOOD.get());
        this.tag(TABlockTags.AURORIAN_PLANKS).add(TABlocks.SILENT_TREE_PLANKS.get(),
                TABlocks.WEEPING_WILLOW_PLANKS.get(), TABlocks.CURTAIN_TREE_PLANKS.get(),
                TABlocks.CURSED_FROST_TREE_PLANKS.get());
        this.tag(TABlockTags.AURORIAN_GRASS_BLOCK).add(TABlocks.AURORIAN_GRASS_BLOCK.get(),
                TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get(),
                TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        this.tag(TABlockTags.AURORIAN_SAND_BLOCK).add(TABlocks.MOON_SAND.get());
        this.tag(TABlockTags.AURORIAN_LIGHT_PLANT_MAY_PLACE_ON).add(TABlocks.MOON_SAND.get());
        this.tag(BlockTags.PLANKS).addTag(TABlockTags.AURORIAN_PLANKS);
        this.tag(BlockTags.LEAVES).add(TABlocks.SILENT_TREE_LEAVES.get(), TABlocks.WEEPING_WILLOW_LEAVES.get(),
                TABlocks.CURTAIN_TREE_LEAVES.get(), TABlocks.CURSED_FROST_TREE_LEAVES.get());
        this.tag(BlockTags.LOGS).addTag(TABlockTags.SILENT_TREE_LOGS).addTag(TABlockTags.WEEPING_WILLOW_LOGS).addTag(TABlockTags.CURTAIN_TREE_LOGS);
        this.tag(BlockTags.SAPLINGS).add(TABlocks.SILENT_TREE_SAPLING.get(), TABlocks.CURTAIN_TREE_SAPLING.get(),
                TABlocks.CURSED_FROST_TREE_SAPLING.get());
        this.tag(BlockTags.CROPS).add(TABlocks.LAVENDER_CROP.get(), TABlocks.SILK_BERRY_CROP.get());
        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(TABlocks.BLUEBERRY_BUSH.get());
        this.tag(BlockTags.SWORD_EFFICIENT).add(TABlocks.BLUEBERRY_BUSH.get());
        this.tag(BlockTags.DIRT).addTag(TABlockTags.AURORIAN_GRASS_BLOCK).add(TABlocks.AURORIAN_DIRT.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(TABlocks.MOONSTONE_ORE.get(), TABlocks.CERULEAN_ORE.get(),TABlocks.EROSIVE_MOONSTONE_ORE.get(),
                TABlocks.EROSIVE_CERULEAN_ORE.get(),TABlocks.AURORIAN_IRON_ORE.get(),TABlocks.EROSIVE_AURORIAN_IRON_ORE.get(),
                TABlocks.AURORIAN_COPPER_ORE.get(),TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get(),TABlocks.AURORIAN_LAPIS_ORE.get(),
                TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(TABlocks.GEODE_ORE.get(),TABlocks.EROSIVE_GEODE_ORE.get(), TABlocks.MOON_GEM.get(),
                TABlocks.MOONLIGHT_FORGE.get(), TABlocks.CERULEAN_BLOCK.get(), TABlocks.MOONSTONE_BLOCK.get(),
                TABlocks.AURORIAN_STEEL_BLOCK.get(),TABlocks.AURORIAN_GOLD_ORE.get(),TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get(),
                TABlocks.AURORIAN_DIAMOND_ORE.get(),TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get(),TABlocks.AURORIAN_REDSTONE_ORE.get(),
                TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get(),TABlocks.AURORIAN_EMERALD_ORE.get(),TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(TABlocks.AURORIAN_CRAFTING_TABLE.get(), TABlocks.BLUEBERRY_BUSH.get(),
                TABlocks.VERTICAL_SILENT_WOOD_STAIRS.get(), TABlocks.VERTICAL_SILENT_WOOD_SLAB.get(), TABlocks.SILENT_WOOD_CHEST.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TABlocks.MOON_SAND.get(), TABlocks.AURORIAN_FARM_TILE.get(),
                TABlocks.AURORIAN_DIRT.get(), TABlocks.AURORIAN_GRASS_BLOCK.get(),
                TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get(),
                TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        this.tag(BlockTags.COAL_ORES).add(TABlocks.AURORIAN_COAL_ORE.get());
        this.tag(BlockTags.IRON_ORES).add(TABlocks.AURORIAN_IRON_ORE.get(),TABlocks.EROSIVE_AURORIAN_IRON_ORE.get());
        this.tag(BlockTags.GOLD_ORES).add(TABlocks.AURORIAN_GOLD_ORE.get(),TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get());
        this.tag(BlockTags.COPPER_ORES).add(TABlocks.AURORIAN_COPPER_ORE.get(),TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get());
        this.tag(BlockTags.LAPIS_ORES).add(TABlocks.AURORIAN_LAPIS_ORE.get(),TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get());
        this.tag(BlockTags.DIAMOND_ORES).add(TABlocks.AURORIAN_DIAMOND_ORE.get(),TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get());
        this.tag(BlockTags.REDSTONE_ORES).add(TABlocks.AURORIAN_REDSTONE_ORE.get(),TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get());
        this.tag(BlockTags.EMERALD_ORES).add(TABlocks.AURORIAN_EMERALD_ORE.get(),TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TABlocks.AURORIAN_STONE.get(), TABlocks.AURORIAN_STONE_BRICKS.get(),
                TABlocks.AURORIAN_COBBLESTONE.get(), TABlocks.AURORIAN_GRANITE.get(), TABlocks.AURORIAN_DIORITE.get(),
                TABlocks.AURORIAN_ANDESITE.get(), TABlocks.AURORIAN_BARRIER_STONE.get(), TABlocks.AURORIAN_COAL_ORE.get(),
                TABlocks.AURORIAN_FURNACE.get(), TABlocks.AURORIAN_FURNACE_CHIMNEY.get(), TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(),
                TABlocks.AURORIAN_PERIDOTITE.get(), TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), TABlocks.MOONSTONE_ORE.get(),
                TABlocks.CERULEAN_ORE.get(), TABlocks.GEODE_ORE.get(), TABlocks.AURORIAN_EMERALD_ORE.get(),TABlocks.AURORIAN_GOLD_ORE.get(),
                TABlocks.AURORIAN_IRON_ORE.get(),TABlocks.AURORIAN_REDSTONE_ORE.get(),TABlocks.AURORIAN_LAPIS_ORE.get(),
                TABlocks.AURORIAN_DIAMOND_ORE.get(), TABlocks.AURORIAN_COPPER_ORE.get(), TABlocks.CERULEAN_BLOCK.get(),
                TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get(),TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get(),TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get(),
                TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get(),TABlocks.EROSIVE_AURORIAN_IRON_ORE.get(),TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get(),
                TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get(), TABlocks.MOONSTONE_BLOCK.get(), TABlocks.AURORIAN_COAL_BLOCK.get(),
                TABlocks.AURORIAN_STEEL_BLOCK.get(), TABlocks.INDIGO_MUSHROOM_CRYSTAL.get(), TABlocks.MOONLIGHT_FORGE.get(),
                TABlocks.MOON_GEM.get(), TABlocks.UMBRA_STONE.get(), TABlocks.UMBRA_STONE_CRACKED.get(),
                TABlocks.UMBRA_STONE_ROOF_TILES.get(), TABlocks.MOON_SANDSTONE.get(), TABlocks.SMOOTH_MOON_SANDSTONE.get(),
                TABlocks.CUT_MOON_SANDSTONE.get(), TABlocks.BRIGHT_MOON_SANDSTONE.get(),TABlocks.EROSIVE_MOONSTONE_ORE.get(),
                TABlocks.EROSIVE_GEODE_ORE.get(),TABlocks.EROSIVE_CERULEAN_ORE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(TABlocks.SILENT_WOOD_PRESSURE_PLATE.get(),
                TABlocks.WEEPING_WILLOW_PRESSURE_PLATE.get(), TABlocks.CURTAIN_WOOD_PRESSURE_PLATE.get(),
                TABlocks.CURSED_FROST_WOOD_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(TABlocks.SILENT_WOOD_TRAPDOOR.get(), TABlocks.WEEPING_WILLOW_TRAPDOOR.get(),
                TABlocks.CURTAIN_WOOD_TRAPDOOR.get(), TABlocks.CURSED_FROST_WOOD_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(TABlocks.SILENT_WOOD_BUTTON.get(), TABlocks.WEEPING_WILLOW_BUTTON.get(),
                TABlocks.CURTAIN_WOOD_BUTTON.get(), TABlocks.CURSED_FROST_WOOD_BUTTON.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(TABlocks.SILENT_WOOD_STAIRS.get(), TABlocks.WEEPING_WILLOW_STAIRS.get(),
                TABlocks.CURTAIN_WOOD_STAIRS.get(), TABlocks.CURSED_FROST_WOOD_STAIRS.get());
        this.tag(BlockTags.WOODEN_FENCES).add(TABlocks.SILENT_WOOD_FENCE.get(), TABlocks.WEEPING_WILLOW_FENCE.get(),
                TABlocks.CURTAIN_WOOD_FENCE.get(), TABlocks.CURSED_FROST_WOOD_FENCE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(TABlocks.SILENT_WOOD_DOOR.get(), TABlocks.WEEPING_WILLOW_DOOR.get(),
                TABlocks.CURTAIN_WOOD_DOOR.get(), TABlocks.CURSED_FROST_WOOD_DOOR.get());
        this.tag(BlockTags.WOODEN_SLABS).add(TABlocks.SILENT_WOOD_SLAB.get(), TABlocks.WEEPING_WILLOW_SLAB.get(),
                TABlocks.CURTAIN_WOOD_SLAB.get(), TABlocks.CURSED_FROST_WOOD_SLAB.get());
        this.tag(BlockTags.FENCE_GATES).add(TABlocks.SILENT_WOOD_FENCE_GATE.get(), TABlocks.WEEPING_WILLOW_FENCE_GATE.get(),
                TABlocks.CURTAIN_WOOD_FENCE_GATE.get(), TABlocks.CURSED_FROST_WOOD_FENCE_GATE.get());
        this.tag(BlockTags.SAND).add(TABlocks.MOON_SAND.get(), TABlocks.MOON_SAND_RIVER.get(), TABlocks.BRIGHT_MOON_SAND.get());
        this.tag(BlockTags.STANDING_SIGNS).add(TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(),
                TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_SIGN.get());
        this.tag(BlockTags.WALL_SIGNS).add(TABlocks.SILENT_WOOD_WALL_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get(),
                TABlocks.CURTAIN_WOOD_WALL_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(TABlocks.SILENT_WOOD_HANGING_SIGN.get(),
                TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(),
                TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(),
                TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(),
                TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get());
        this.tag(BlockTags.ICE).add(TABlocks.FILTHY_ICE.get());
        this.tag(BlockTags.WOOL).add(TABlocks.MYSTERIUM_WOOL.get());
        this.tag(BlockTags.BEDS).add(TABlocks.MYSTERIUM_WOOL_BED.get());
        this.tag(BlockTags.CLIMBABLE).add(TABlocks.SILENT_WOOD_LADDER.get());
        this.tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).add(TABlocks.FILTHY_ICE.get());
        this.tag(Tags.Blocks.CHESTS_WOODEN).add(TABlocks.SILENT_WOOD_CHEST.get());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            float f1 = Blocks.BEDROCK.defaultDestroyTime();
            float f2 = Blocks.BEDROCK.getExplosionResistance();
            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(block);
            if (key != null && key.getNamespace().equals(AurorianMod.MOD_ID)) {
                if (key.getPath().contains("moon_temple")) {
                    this.tag(TABlockTags.MOON_TEMPLE_BLOCK).add(block);
                }
            }

            if (!block.defaultBlockState().ignitedByLava()) {
                if (block instanceof StairBlock) {
                    this.tag(BlockTags.STAIRS).add(block);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                } else if (block instanceof SlabBlock) {
                    this.tag(BlockTags.SLABS).add(block);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                } else if (block instanceof WallBlock) {
                    this.tag(BlockTags.WALLS).add(block);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                } else if (block instanceof VerticalStairBlockWithBase) {
                    this.tag(TABlockTags.VERTICAL_STAIRS).add(block);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                } else if (block instanceof VerticalSlabBlockWithBase) {
                    this.tag(TABlockTags.VERTICAL_SLABS).add(block);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                }
            } else if (block.defaultDestroyTime() != f1 && block.getExplosionResistance() == f2) {
                this.tag(TABlockTags.DUNGEON_BRICKS).add(block);
            } else if (block instanceof FlowerPotBlock) {
                this.tag(BlockTags.FLOWER_POTS).add(block);
            }
        }
    }

}