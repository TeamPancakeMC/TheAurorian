package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.TAEntityTags;
import cn.teampancake.theaurorian.registry.TAEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAEntityTagsProvider extends EntityTypeTagsProvider {

    public TAEntityTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.EntityTypes.BOSSES)
                .add(TAEntityTypes.RUNESTONE_KEEPER.get())
                .add(TAEntityTypes.SPIDER_MOTHER.get())
                .add(TAEntityTypes.MOON_QUEEN.get());
        this.tag(TAEntityTags.AFFECTED_BY_NIGHTMARE_MODE)
                .add(TAEntityTypes.DISTURBED_HOLLOW.get())
                .add(TAEntityTypes.UNDEAD_KNIGHT.get())
                .add(TAEntityTypes.SPIDERLING.get())
                .add(TAEntityTypes.MOON_ACOLYTE.get())
                .add(TAEntityTypes.CRYSTALLINE_SPRITE.get());
        this.tag(TAEntityTags.HAS_CUSTOM_DEATH_ANIMATION)
                .add(TAEntityTypes.RUNESTONE_KEEPER.get());
    }

}