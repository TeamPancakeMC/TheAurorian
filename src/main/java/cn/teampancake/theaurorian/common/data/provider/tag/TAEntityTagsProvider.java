package cn.teampancake.theaurorian.common.data.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAEntityTagsProvider extends EntityTypeTagsProvider {

    public TAEntityTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(TAEntityTypes.AURORIAN_PIXIE.get());
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
    }

}