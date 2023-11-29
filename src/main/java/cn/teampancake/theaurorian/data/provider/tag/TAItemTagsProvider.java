package cn.teampancake.theaurorian.data.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.TABlockTags;
import cn.teampancake.theaurorian.data.tags.TAItemTags;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class TAItemTagsProvider extends ItemTagsProvider {

    public TAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(Tags.Items.TOOLS_BOWS).add(TAItems.SILENT_WOOD_BOW.get())
                .add(TAItems.KEEPERS_BOW.get());
        this.tag(Tags.Items.TOOLS_SHIELDS).add(TAItems.CERULEAN_SHIELD.get(),
                TAItems.CRYSTALLINE_SHIELD.get(), TAItems.MOON_SHIELD.get(),
                TAItems.UMBRA_SHIELD.get(), TAItems.MOONSTONE_SHIELD.get());
        this.tag(TAItemTags.DUNGEON_KEY).add(TAItems.RUNE_STONE_KEY.get())
                .add(TAItems.RUNE_STONE_LOOT_KEY.get()).add(TAItems.MOON_TEMPLE_KEY.get())
                .add(TAItems.MOON_TEMPLE_CELL_KEY.get()).add(TAItems.DARK_STONE_KEY.get());
        this.tag(TAItemTags.SPECTRAL_ARMOR).add(TAItems.SPECTRAL_HELMET.get(),
                TAItems.SPECTRAL_CHESTPLATE.get(), TAItems.SPECTRAL_LEGGINGS.get(),
                TAItems.SPECTRAL_BOOTS.get());
        this.tag(TAItemTags.HAS_CUSTOM_ARMOR_MODEL).add(TAItems.AURORIAN_STEEL_HELMET.get(),
                TAItems.AURORIAN_STEEL_CHESTPLATE.get(), TAItems.AURORIAN_STEEL_LEGGINGS.get(),
                TAItems.AURORIAN_STEEL_BOOTS.get(), TAItems.CERULEAN_HELMET.get(),
                TAItems.CERULEAN_CHESTPLATE.get(), TAItems.CERULEAN_LEGGINGS.get(),
                TAItems.CERULEAN_BOOTS.get(), TAItems.KNIGHT_HELMET.get(),
                TAItems.KNIGHT_CHESTPLATE.get(), TAItems.KNIGHT_LEGGINGS.get(),
                TAItems.KNIGHT_BOOTS.get(), TAItems.SPECTRAL_HELMET.get(),
                TAItems.SPECTRAL_CHESTPLATE.get(), TAItems.SPECTRAL_LEGGINGS.get(),
                TAItems.SPECTRAL_BOOTS.get());
        this.tag(ItemTags.ARROWS).add(TAItems.CERULEAN_ARROW.get(), TAItems.CRYSTAL_ARROW.get());
        this.tag(TAItemTags.AURORIAN_SLIME_BOOTS).add(TAItems.AURORIAN_SLIME_BOOTS.get());
        this.copy(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, TAItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        this.copy(TABlockTags.SILENT_TREE_LOGS, TAItemTags.SILENT_TREE_LOGS);
        this.copy(TABlockTags.WEEPING_WILLOW_LOGS, TAItemTags.WEEPING_WILLOW_LOGS);
    }

}