package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TABiomeTagsProvider extends BiomeTagsProvider {

    public TABiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TABiomeTags.HAS_RUINS_ALTAR);
    }

}