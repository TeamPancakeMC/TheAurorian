package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
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
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(TAEntityTypes.AURORIAN_PIXIE.get(), TAEntityTypes.CRYSTALLINE_SPRITE.get());
        this.tag(Tags.EntityTypes.BOSSES).add(TAEntityTypes.RUNESTONE_KEEPER.get(), TAEntityTypes.SPIDER_MOTHER.get(), TAEntityTypes.MOON_QUEEN.get());
        this.tag(TAEntityTags.AFFECTED_BY_NIGHTMARE_MODE).add(TAEntityTypes.DISTURBED_HOLLOW.get(), TAEntityTypes.UNDEAD_KNIGHT.get(),
                TAEntityTypes.SPIDERLING.get(), TAEntityTypes.MOON_ACOLYTE.get(), TAEntityTypes.CRYSTALLINE_SPRITE.get());
        this.tag(TAEntityTags.WOLF_NON_TAME_ATTACK_TARGET).add(EntityType.SHEEP, EntityType.RABBIT, EntityType.FOX,
                TAEntityTypes.AURORIAN_SHEEP.get(), TAEntityTypes.AURORIAN_RABBIT.get());
    }

}