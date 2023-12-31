package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.base.VerticalSlabBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.base.VerticalStairBlockWithBase;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class TABlockTagsProvider extends BlockTagsProvider {

    public TABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON)
                .add(TABlocks.RUNE_STONE.get()).add(TABlocks.MOON_TEMPLE_BRICKS.get())
                .add(TABlocks.DARK_STONE_BRICKS.get()).add(TABlocks.DARK_STONE_FANCY.get())
                .add(TABlocks.DARK_STONE_LAYERS.get()).add(TABlocks.SMOOTH_DARK_STONE_BRICKS.get())
                .add(TABlocks.RUNE_STONE_LAMP.get()).add(TABlocks.CHISELED_DARK_STONE_BRICKS.get())
                .add(TABlocks.MOON_TEMPLE_LAMP.get()).add(TABlocks.DARK_STONE_LAMP.get())
                .add(TABlocks.SMOOTH_RUNE_STONE.get()).add(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.tag(TABlockTags.SILENT_TREE_LOGS).add(TABlocks.STRIPPED_SILENT_TREE_LOG.get(),
                TABlocks.STRIPPED_SILENT_TREE_WOOD.get(), TABlocks.SILENT_TREE_LOG.get(), TABlocks.SILENT_TREE_WOOD.get());
        this.tag(TABlockTags.WEEPING_WILLOW_LOGS).add(TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get(),
                TABlocks.STRIPPED_WEEPING_WILLOW_WOOD.get(), TABlocks.WEEPING_WILLOW_LOG.get(), TABlocks.WEEPING_WILLOW_WOOD.get());
        this.tag(TABlockTags.AURORIAN_GRASS_BLOCK).add(TABlocks.AURORIAN_GRASS_BLOCK.get(),
                TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        this.tag(BlockTags.PLANKS).add(TABlocks.SILENT_TREE_PLANKS.get(), TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.tag(BlockTags.LEAVES).add(TABlocks.SILENT_BUSH_LEAVES.get(), TABlocks.SILENT_TREE_LEAVES.get(),
                TABlocks.WEEPING_WILLOW_LEAVES.get(), TABlocks.CURTAIN_TREE_LEAVES.get());
        this.tag(BlockTags.LOGS).addTag(TABlockTags.SILENT_TREE_LOGS).addTag(TABlockTags.WEEPING_WILLOW_LOGS);
        this.tag(BlockTags.SAPLINGS).add(TABlocks.SILENT_TREE_SAPLING.get(), TABlocks.CURTAIN_TREE_SAPLING.get());
        this.tag(BlockTags.CROPS).add(TABlocks.LAVENDER_CROP.get(), TABlocks.SILK_BERRY_CROP.get());
        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(TABlocks.BLUEBERRY_BUSH.get());
        this.tag(BlockTags.SWORD_EFFICIENT).add(TABlocks.BLUEBERRY_BUSH.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(TABlocks.MOONSTONE_ORE.get(), TABlocks.CERULEAN_ORE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(TABlocks.GEODE_ORE.get(), TABlocks.MOON_GEM.get(), TABlocks.MOONLIGHT_FORGE.get(),
                TABlocks.CERULEAN_BLOCK.get(), TABlocks.MOONSTONE_BLOCK.get(), TABlocks.AURORIAN_STEEL_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get(), TABlocks.BLUEBERRY_BUSH.get(),
                TABlocks.VERTICAL_SILENT_WOOD_STAIRS.get(), TABlocks.VERTICAL_SILENT_WOOD_SLAB.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TABlocks.MOON_SAND.get(), TABlocks.AURORIAN_FARM_TILE.get(),
                TABlocks.AURORIAN_DIRT.get(), TABlocks.AURORIAN_GRASS_BLOCK.get(),
                TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TABlocks.AURORIAN_STONE.get(), TABlocks.AURORIAN_STONE_BRICKS.get(),
                TABlocks.AURORIAN_COBBLESTONE.get(), TABlocks.AURORIAN_GRANITE.get(), TABlocks.AURORIAN_DIORITE.get(),
                TABlocks.AURORIAN_ANDESITE.get(), TABlocks.AURORIAN_BARRIER_STONE.get(), TABlocks.AURORIAN_COAL_ORE.get(),
                TABlocks.AURORIAN_FURNACE.get(), TABlocks.AURORIAN_FURNACE_CHIMNEY.get(), TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(),
                TABlocks.AURORIAN_PERIDOTITE.get(), TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), TABlocks.MOONSTONE_ORE.get(),
                TABlocks.CERULEAN_ORE.get(), TABlocks.GEODE_ORE.get(), TABlocks.CERULEAN_BLOCK.get(), TABlocks.MOONSTONE_BLOCK.get(),
                TABlocks.AURORIAN_COAL_BLOCK.get(), TABlocks.AURORIAN_STEEL_BLOCK.get(), TABlocks.INDIGO_MUSHROOM_CRYSTAL.get(),
                TABlocks.MOONLIGHT_FORGE.get(), TABlocks.MOON_GEM.get(), TABlocks.UMBRA_STONE.get(),
                TABlocks.UMBRA_STONE_CRACKED.get(), TABlocks.UMBRA_STONE_ROOF_TILES.get(), TABlocks.MOON_SAND_STONE_1.get(),
                TABlocks.MOON_SAND_STONE_2.get(), TABlocks.MOON_SAND_STONE_3.get(), TABlocks.BRIGHT_MOON_SANDSTONE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(TABlocks.SILENT_WOOD_PRESSURE_PLATE.get(), TABlocks.WEEPING_WILLOW_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(TABlocks.SILENT_WOOD_TRAPDOOR.get(), TABlocks.WEEPING_WILLOW_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(TABlocks.SILENT_WOOD_BUTTON.get(), TABlocks.WEEPING_WILLOW_BUTTON.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(TABlocks.SILENT_WOOD_STAIRS.get(), TABlocks.WEEPING_WILLOW_STAIRS.get());
        this.tag(BlockTags.WOODEN_FENCES).add(TABlocks.SILENT_WOOD_FENCE.get(), TABlocks.WEEPING_WILLOW_FENCE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(TABlocks.SILENT_WOOD_DOOR.get(), TABlocks.WEEPING_WILLOW_DOOR.get());
        this.tag(BlockTags.WOODEN_SLABS).add(TABlocks.SILENT_WOOD_SLAB.get(), TABlocks.WEEPING_WILLOW_SLAB.get());
        this.tag(BlockTags.FENCE_GATES).add(TABlocks.SILENT_WOOD_FENCE_GATE.get(), TABlocks.WEEPING_WILLOW_FENCE_GATE.get());
        this.tag(BlockTags.SAND).add(TABlocks.MOON_SAND.get(), TABlocks.BRIGHT_MOON_SAND.get());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            float f1 = Blocks.BEDROCK.defaultDestroyTime();
            float f2 = Blocks.BEDROCK.getExplosionResistance();
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