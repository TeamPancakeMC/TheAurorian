package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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
        tag(ModItemTags.SPECTRAL_ARMOR).add(ModItems.SPECTRAL_SUIT_HELMET.get(),
                ModItems.SPECTRAL_SUIT_CHESTPLATE.get(),
                ModItems.SPECTRAL_SUIT_LEGGINGS.get(),
                ModItems.SPECTRAL_SUIT_BOOTS.get());

        tag(ModItemTags.AURORIAN_SLIME_BOOTS).add(ModItems.AURORIAN_SLIME_BOOTS.get());

    }
}