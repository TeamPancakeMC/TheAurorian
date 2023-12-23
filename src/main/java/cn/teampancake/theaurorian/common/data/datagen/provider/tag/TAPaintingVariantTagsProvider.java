package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TAPaintingVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

    public TAPaintingVariantTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TAPaintingVariants.PAINTING_VARIANTS.getEntries().forEach(paintingVariant -> {
            if (paintingVariant.getKey() != null) {
                this.tag(PaintingVariantTags.PLACEABLE).add(paintingVariant.getKey());
            }
        });
    }

}