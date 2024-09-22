package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TABiomeTagsProvider extends BiomeTagsProvider {

    public TABiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TABiomeTags.HAS_RUINS_ALTAR).add(TABiomes.AURORIAN_FOREST, TABiomes.AURORIAN_FOREST_HILL);
        this.tag(TABiomeTags.IS_AUARORIAN_FOREST).add(TABiomes.AURORIAN_FOREST,
                TABiomes.AURORIAN_FOREST_HILL, TABiomes.WEEPING_WILLOW_FOREST);
        provider.lookupOrThrow(Registries.BIOME).listElementIds()
                .filter(key -> key.location().getNamespace().equals(TheAurorian.MOD_ID))
                .toList().forEach(key -> this.tag(TABiomeTags.IS_AURORIAN).add(key));
    }

}