package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModBlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON)
                .add(ModBlocks.RUNE_STONE.get()).add(ModBlocks.MOON_TEMPLE_BRICKS.get())
                .add(ModBlocks.DARK_STONE_BRICKS.get()).add(ModBlocks.DARK_STONE_FANCY.get())
                .add(ModBlocks.DARK_STONE_LAYERS.get()).add(ModBlocks.RUNE_STONE_LAMP.get())
                .add(ModBlocks.MOON_TEMPLE_LAMP.get()).add(ModBlocks.DARK_STONE_LAMP.get())
                .add(ModBlocks.SMOOTH_RUNE_STONE.get()).add(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.tag(ModBlockTags.SILENT_TREE_LOGS).add(ModBlocks.SILENT_TREE_LOG.get(), ModBlocks.SILENT_TREE_WOOD.get());
        this.tag(ModBlockTags.WEEPING_WILLOW_LOGS).add(ModBlocks.WEEPING_WILLOW_LOG.get(), ModBlocks.WEEPING_WILLOW_WOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.SILENT_TREE_PLANKS.get(), ModBlocks.WEEPING_WILLOW_PLANKS.get());
        this.tag(BlockTags.LEAVES).add(ModBlocks.SILENT_TREE_LEAVES.get(), ModBlocks.WEEPING_WILLOW_LEAVES.get());
        this.tag(BlockTags.LOGS).addTag(ModBlockTags.SILENT_TREE_LOGS).addTag(ModBlockTags.WEEPING_WILLOW_LOGS);
        this.tag(BlockTags.SAPLINGS).add(ModBlocks.SILENT_TREE_SAPLING.get());
        this.tag(BlockTags.CROPS).add(ModBlocks.LAVENDER_CROP.get(), ModBlocks.SILK_BERRY_CROP.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.MOONSTONE_ORE.get(), ModBlocks.CERULEAN_ORE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.GEODE_ORE.get(), ModBlocks.MOON_GEM.get(), ModBlocks.MOONLIGHT_FORGE.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.MOON_SAND.get(), ModBlocks.AURORIAN_FARM_TILE.get(),
                ModBlocks.AURORIAN_DIRT.get(), ModBlocks.AURORIAN_GRASS_BLOCK.get(), ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.AURORIAN_STONE.get(), ModBlocks.AURORIAN_STONE_BRICKS.get(),
                ModBlocks.AURORIAN_COBBLESTONE.get(), ModBlocks.AURORIAN_COAL_ORE.get(), ModBlocks.AURORIAN_FURNACE.get(),
                ModBlocks.AURORIAN_FURNACE_CHIMNEY.get(), ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(),
                ModBlocks.AURORIAN_PERIDOTITE.get(), ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), ModBlocks.MOONSTONE_ORE.get(),
                ModBlocks.CERULEAN_ORE.get(), ModBlocks.GEODE_ORE.get(), ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get(),
                ModBlocks.MOONLIGHT_FORGE.get(), ModBlocks.MOON_GEM.get(), ModBlocks.UMBRA_STONE.get(),
                ModBlocks.UMBRA_STONE_CRACKED.get(), ModBlocks.UMBRA_STONE_ROOF_TILES.get(),
                ModBlocks.AURORIAN_STONE_STAIRS.get(), ModBlocks.AURORIAN_STONE_BRICK_STAIRS.get(),
                ModBlocks.AURORIAN_COBBLESTONE_STAIRS.get(), ModBlocks.UMBRA_STONE_STAIRS.get(),
                ModBlocks.UMBRA_STONE_CRACKED_STAIRS.get(), ModBlocks.UMBRA_STONE_ROOF_STAIRS.get(),
                ModBlocks.AURORIAN_PERIDOTITE_STAIRS.get(), ModBlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.SILENT_WOOD_STAIRS.get(), ModBlocks.WEEPING_WILLOW_STAIRS.get());
        this.tag(BlockTags.SAND).add(ModBlocks.MOON_SAND.get());
        for (Block block : ModCommonUtils.getKnownBlocks()) {
            float f1 = Blocks.BEDROCK.defaultDestroyTime();
            float f2 = Blocks.BEDROCK.getExplosionResistance();
            if (block instanceof StairBlock && !block.defaultBlockState().ignitedByLava()) {
                this.tag(BlockTags.STAIRS).add(block);
            } else if (block.defaultDestroyTime() != f1 && block.getExplosionResistance() == f2) {
                this.tag(ModBlockTags.DUNGEON_BRICKS).add(block);
            }
        }
    }

}