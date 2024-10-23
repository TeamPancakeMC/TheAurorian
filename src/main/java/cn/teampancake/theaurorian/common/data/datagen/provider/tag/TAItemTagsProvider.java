package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAItemTagsProvider extends ItemTagsProvider {

    public TAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(TABlockTags.VERTICAL_STAIRS, TAItemTags.VERTICAL_STAIRS);
        this.copy(TABlockTags.VERTICAL_SLABS, TAItemTags.VERTICAL_SLABS);
        this.copy(TABlockTags.RUNE_STONE_BLOCK, TAItemTags.RUNE_STONE_BLOCK);
        this.copy(TABlockTags.SILENT_TREE_LOGS, TAItemTags.SILENT_TREE_LOGS);
        this.copy(TABlockTags.WEEPING_WILLOW_LOGS, TAItemTags.WEEPING_WILLOW_LOGS);
        this.copy(TABlockTags.CURTAIN_TREE_LOGS, TAItemTags.CURTAIN_TREE_LOGS);
        this.copy(TABlockTags.CURSED_FROST_TREE_LOGS, TAItemTags.CURSED_FROST_TREE_LOGS);
        this.copy(TABlockTags.AURORIAN_PLANKS, TAItemTags.AURORIAN_PLANKS);
        this.copy(TABlockTags.AURORIAN_GRASS_BLOCK, TAItemTags.AURORIAN_GRASS_BLOCK);
        this.copy(TABlockTags.AURORIAN_CARVER_REPLACEABLES, TAItemTags.AURORIAN_CARVER_REPLACEABLES);
        this.copy(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, TAItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        this.tag(TAItemTags.HAS_CUSTOM_TOOLTIPS).addTag(TAItemTags.BUILDING_BLOCK).addTag(TAItemTags.IS_RARE)
                .addTag(TAItemTags.IS_EPIC).addTag(TAItemTags.IS_LEGENDARY).addTag(TAItemTags.IS_MYTHICAL);
        this.tag(TAItemTags.IS_RARE).addTag(TAItemTags.DUNGEON_KEY);
        for (Item item : TACommonUtils.getKnownItems()) {
            TACommonUtils.getItemProperties(item).itemTagList.forEach(key -> this.tag(key).add(item));
            if (item instanceof ArmorItem armor) {
                switch (armor.getEquipmentSlot()) {
                    case HEAD -> this.tag(ItemTags.HEAD_ARMOR).add(armor);
                    case CHEST -> this.tag(ItemTags.CHEST_ARMOR).add(armor);
                    case LEGS -> this.tag(ItemTags.LEG_ARMOR).add(armor);
                    case FEET -> this.tag(ItemTags.FOOT_ARMOR).add(armor);
                }
            }
        }
    }

}