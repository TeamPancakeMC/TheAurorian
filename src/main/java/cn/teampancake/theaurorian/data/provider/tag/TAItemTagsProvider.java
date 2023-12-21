package cn.teampancake.theaurorian.data.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.developer.IDeveloperItem;
import cn.teampancake.theaurorian.data.tags.TABlockTags;
import cn.teampancake.theaurorian.data.tags.TAItemTags;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAItemTagsProvider extends ItemTagsProvider {

    public TAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(TABlockTags.SILENT_TREE_LOGS, TAItemTags.SILENT_TREE_LOGS);
        this.copy(TABlockTags.WEEPING_WILLOW_LOGS, TAItemTags.WEEPING_WILLOW_LOGS);
        this.copy(TABlockTags.AURORIAN_GRASS_BLOCK, TAItemTags.AURORIAN_GRASS_BLOCK);
        this.copy(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, TAItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        this.tag(Tags.Items.TOOLS_CROSSBOWS).add(TAItems.STAR_OCEAN_CROSSBOW.get());
        this.tag(Tags.Items.TOOLS_BOWS).add(TAItems.SILENT_WOOD_BOW.get()).add(TAItems.KEEPERS_BOW.get());
        this.tag(Tags.Items.TOOLS_SHIELDS).add(TAItems.CERULEAN_SHIELD.get(), TAItems.CRYSTALLINE_SHIELD.get(),
                TAItems.MOON_SHIELD.get(), TAItems.UMBRA_SHIELD.get(), TAItems.MOONSTONE_SHIELD.get());
        this.tag(ItemTags.ARROWS).add(TAItems.CERULEAN_ARROW.get(), TAItems.CRYSTAL_ARROW.get());
        this.tag(TAItemTags.DUNGEON_KEY).add(TAItems.RUNE_STONE_KEY.get()).add(TAItems.RUNE_STONE_LOOT_KEY.get())
                .add(TAItems.MOON_TEMPLE_KEY.get()).add(TAItems.MOON_TEMPLE_CELL_KEY.get()).add(TAItems.DARK_STONE_KEY.get());
        this.tag(TAItemTags.SPECTRAL_ARMOR).add(TAItems.SPECTRAL_HELMET.get(), TAItems.SPECTRAL_CHESTPLATE.get(),
                TAItems.SPECTRAL_LEGGINGS.get(), TAItems.SPECTRAL_BOOTS.get());
        this.tag(TAItemTags.HAS_CUSTOM_TOOLTIPS).addTag(TAItemTags.IS_UNCOMMON).addTag(TAItemTags.IS_RARE)
                .addTag(TAItemTags.IS_EPIC).addTag(TAItemTags.IS_LEGENDARY).addTag(TAItemTags.IS_MYTHICAL)
                .add(TAItems.WHITE_CHOCOLATE.get(), TAItems.RED_BOOK.get(), TAItems.RED_BOOK_RING.get(),
                        TAItems.STAR_OCEAN_CROSSBOW.get());
        this.tag(TAItemTags.IS_EPIC).add(TAItems.AURORIAN_STEEL.get());
        this.tag(TAItemTags.IS_LEGENDARY).add(TAItems.TROPHY_KEEPER.get(), TAItems.TROPHY_SPIDER_MOTHER.get(),
                TAItems.TROPHY_MOON_QUEEN.get(), TAItems.DEVELOPER_GIFT.get(), TAItems.AURORIAN_CRYSTAL.get());
        this.tag(TAItemTags.IS_MYTHICAL).add(TAItems.SLEEPING_BLACK_TEA.get());
        for (Item item : TACommonUtils.getKnownItems()) {
            if (!(item instanceof IDeveloperItem)) {
                if (item.canBeDepleted()) {
                    this.tag(TAItemTags.IS_EPIC).add(item);
                } else if (item.isEdible()) {
                    this.tag(TAItemTags.IS_RARE).add(item);
                } else if (item instanceof BlockItem) {
                    this.tag(TAItemTags.IS_UNCOMMON).add(item);
                }
            }
        }
    }

}