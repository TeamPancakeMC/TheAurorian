package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAEntityTagsProvider extends EntityTypeTagsProvider {

    public TAEntityTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(TAEntityTypes.AURORIAN_PIXIE.get(), TAEntityTypes.MOON_QUEEN.get(),
                TAEntityTypes.CRYSTALLINE_SPRITE.get()).addTag(TAEntityTags.SPIDERLING);
        this.tag(EntityTypeTags.UNDEAD).add(TAEntityTypes.RUNESTONE_KEEPER.value(),
                TAEntityTypes.SPIRIT.get(), TAEntityTypes.UNDEAD_KNIGHT.get());
        this.tag(EntityTypeTags.ARTHROPOD).add(TAEntityTypes.SPIDER_MOTHER.get());
        this.tag(TAEntityTags.SPIDERLING).add(TAEntityTypes.SPIDERLING.get(), TAEntityTypes.GIANT_CRYSTAL_SPIDER.get(),
                TAEntityTypes.SPIDERLING_CRYSTAL_SHELL.get(), TAEntityTypes.SPIDERLING_WALL_CLIMBER.get());
        this.tag(TAEntityTags.WOLF_NON_TAME_ATTACK_TARGET).add(EntityType.SHEEP, EntityType.RABBIT, EntityType.FOX,
                TAEntityTypes.AURORIAN_SHEEP.get(), TAEntityTypes.AURORIAN_RABBIT.get(), TAEntityTypes.ICEFIELD_DEER.get());
        this.tag(TAEntityTags.ALERTED_BY_BLUE_TAIL_WOLF).add(EntityType.WOLF, TAEntityTypes.BLUE_TAIL_WOLF.get());
        this.tag(TAEntityTags.AURORIAN_BOSS).add(TAEntityTypes.MOONLIGHT_KNIGHT.get(), TAEntityTypes.RUNESTONE_KEEPER.get(),
                TAEntityTypes.SPIDER_MOTHER.get(), TAEntityTypes.MOON_QUEEN.get());
        this.tag(Tags.EntityTypes.BOSSES).addTag(TAEntityTags.AURORIAN_BOSS);
    }

}