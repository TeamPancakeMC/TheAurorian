package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModEntityTags;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.Tags;
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
        this.tag(Tags.EntityTypes.BOSSES)
                .add(ModEntityTypes.RUNESTONE_KEEPER.get())
                .add(ModEntityTypes.SPIDER_MOTHER.get())
                .add(ModEntityTypes.MOON_QUEEN.get());
        this.tag(ModEntityTags.AFFECTED_BY_NIGHTMARE_MODE)
                .add(ModEntityTypes.DISTURBED_HOLLOW.get())
                .add(ModEntityTypes.UNDEAD_KNIGHT.get())
                .add(ModEntityTypes.SPIDERLING.get())
                .add(ModEntityTypes.MOON_ACOLYTE.get())
                .add(ModEntityTypes.CRYSTALLINE_SPRITE.get());
        this.tag(ModEntityTags.HAS_CUSTOM_DEATH_ANIMATION)
                .add(ModEntityTypes.RUNESTONE_KEEPER.get());
    }

}