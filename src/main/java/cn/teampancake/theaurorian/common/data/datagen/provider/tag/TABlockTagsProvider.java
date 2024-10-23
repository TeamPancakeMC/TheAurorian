package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TABlockTagsProvider extends BlockTagsProvider {

    public TABlockTagsProvider(
            PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TABlockTags.AURORIAN_CARVER_REPLACEABLES).addTag(TABlockTags.AURORIAN_GRASS_BLOCK);
        this.tag(TABlockTags.CAN_HURT_SICKLE).addTag(BlockTags.LEAVES).addTag(BlockTags.WOOL)
                .add(Blocks.COBWEB, Blocks.SHORT_GRASS, Blocks.FERN, Blocks.DEAD_BUSH,
                        Blocks.HANGING_ROOTS, Blocks.VINE, Blocks.TRIPWIRE);
        this.tag(BlockTags.DIRT).addTag(TABlockTags.AURORIAN_GRASS_BLOCK);
        this.tag(BlockTags.PLANKS).addTag(TABlockTags.AURORIAN_PLANKS);
        this.tag(BlockTags.LOGS).addTag(TABlockTags.SILENT_TREE_LOGS)
                .addTag(TABlockTags.WEEPING_WILLOW_LOGS)
                .addTag(TABlockTags.CURTAIN_TREE_LOGS);
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof LeavesBlock) {
                this.tag(BlockTags.LEAVES).add(block);
            }

            if (block instanceof FlowerPotBlock) {
                this.tag(BlockTags.FLOWER_POTS).add(block);
            }

            if (block.properties() instanceof TABlockProperties properties) {
                properties.blockTagList.forEach(tag -> this.tag(tag).add(block));
            }
        }
    }

}