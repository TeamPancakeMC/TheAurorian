package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAEntityTagsProvider extends EntityTypeTagsProvider {

    public TAEntityTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, TheAurorian.MOD_ID, existingFileHelper);
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
        this.tag(TAEntityTags.AFFECTED_BY_NIGHTMARE_MODE).add(TAEntityTypes.CRYSTALLINE_SPRITE.get(), TAEntityTypes.DISTURBED_HOLLOW.get(),
                TAEntityTypes.MOON_ACOLYTE.get(), TAEntityTypes.SPIDERLING.get(), TAEntityTypes.UNDEAD_KNIGHT.get());
        this.tag(TAEntityTags.AURORIAN_BOSS).add(TAEntityTypes.MOONLIGHT_KNIGHT.get(), TAEntityTypes.RUNESTONE_KEEPER.get(),
                TAEntityTypes.SPIDER_MOTHER.get(), TAEntityTypes.MOON_QUEEN.get());
        this.tag(Tags.EntityTypes.BOSSES).addTag(TAEntityTags.AURORIAN_BOSS);
        
        // 添加极光生物标签
        this.tag(TAEntityTags.AURORIAN_MOBS)
            .add(TAEntityTypes.AURORIAN_PIG.get())
            .add(TAEntityTypes.AURORIAN_SHEEP.get())
            .add(TAEntityTypes.AURORIAN_COW.get())
            .add(TAEntityTypes.AURORIAN_RABBIT.get())
            .add(TAEntityTypes.AURORIAN_PIXIE.get())
            .add(TAEntityTypes.AURORIAN_SLIME.get())
            .add(TAEntityTypes.AURORIAN_VILLAGER.get())
            .add(TAEntityTypes.AURORIAN_WINGED_FISH.get())
            .add(TAEntityTypes.BLUE_TAIL_WOLF.get())
            .add(TAEntityTypes.BREAD_BEAST.get())
            .add(TAEntityTypes.CAVE_DWELLER.get())
            .add(TAEntityTypes.CRYSTALLINE_SPRITE.get())
            .add(TAEntityTypes.DISTURBED_HOLLOW.get())
            .add(TAEntityTypes.FLOWER_LEECH.get())
            .add(TAEntityTypes.FORGOTTEN_MAGIC_BOOK.get())
            .add(TAEntityTypes.GIANT_CRYSTAL_SPIDER.get())
            .add(TAEntityTypes.HYPHA_WALKING_MUSHROOM.get())
            .add(TAEntityTypes.ICEFIELD_DEER.get())
            .add(TAEntityTypes.MOON_ACOLYTE.get())
            .add(TAEntityTypes.MOON_FISH.get())
            .add(TAEntityTypes.MOON_QUEEN.get())
            .add(TAEntityTypes.MOONLIGHT_KNIGHT.get())
            .add(TAEntityTypes.ROCK_HAMMER.get())
            .add(TAEntityTypes.RUNE_SPIDER.get())
            .add(TAEntityTypes.RUNESTONE_KEEPER.get())
            .add(TAEntityTypes.SELENA.get())
            .add(TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB.get())
            .add(TAEntityTypes.SPIDER_MOTHER.get())
            .add(TAEntityTypes.SPIDERLING.get())
            .add(TAEntityTypes.SPIDERLING_CRYSTAL_SHELL.get())
            .add(TAEntityTypes.SPIDERLING_WALL_CLIMBER.get())
            .add(TAEntityTypes.SPIRIT.get())
            .add(TAEntityTypes.STICKY_SPIKER.get())
            .add(TAEntityTypes.TONG_SCORPION.get())
            .add(TAEntityTypes.UNDEAD_KNIGHT.get());
    }

}