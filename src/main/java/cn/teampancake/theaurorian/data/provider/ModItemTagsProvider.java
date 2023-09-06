package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModItemTags.DUNGEON_KEY).add(ModItems.RUNE_STONE_KEY.get())
                .add(ModItems.RUNE_STONE_LOOT_KEY.get())
                .add(ModItems.MOON_TEMPLE_KEY.get())
                .add(ModItems.MOON_TEMPLE_CELL_KEY.get())
                .add(ModItems.DARK_STONE_KEY.get());
        tag(ModItemTags.SPECTRAL_ARMOR).add(ModItems.SPECTRAL_HELMET.get(),
                ModItems.SPECTRAL_CHESTPLATE.get(),
                ModItems.SPECTRAL_LEGGINGS.get(),
                ModItems.SPECTRAL_BOOTS.get());
        tag(ModItemTags.AURORIAN_SLIME_BOOTS).add(ModItems.AURORIAN_SLIME_BOOTS.get());
        tag(ItemTags.ARROWS).add(ModItems.CERULEAN_ARROW.get(), ModItems.CRYSTAL_ARROW.get());
        copy(ModBlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, ModItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        copy(ModBlockTags.SILENT_TREE_LOGS, ModItemTags.SILENT_TREE_LOGS);
        copy(ModBlockTags.WEEPING_WILLOW_LOGS, ModItemTags.WEEPING_WILLOW_LOGS);
    }

}