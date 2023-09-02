package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModEntityTags;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModEntityTags.AFFECTED_BY_NIGHTMARE_MODE)
                .add(ModEntityTypes.DISTURBED_HOLLOW.get())
                .add(ModEntityTypes.UNDEAD_KNIGHT.get())
                .add(ModEntityTypes.SPIDERLING.get())
                .add(ModEntityTypes.MOON_ACOLYTE.get())
                .add(ModEntityTypes.CRYSTALLINE_SPRITE.get());
    }

}