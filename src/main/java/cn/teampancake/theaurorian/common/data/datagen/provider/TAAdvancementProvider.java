package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TAAdvancementProvider extends AdvancementProvider {

    private static final ResourceLocation BACKGROUND_LOCATION = TheAurorian.prefix("textures/block/aurorian_stone_bricks.png");

    public TAAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new TAAdvancements()));
    }

    private static class TAAdvancements implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            AdvancementHolder enterAurorian = Advancement.Builder.advancement().display(
                            TAItems.AURORIAN_CRYSTAL.get(),
                            Component.translatable("advancement.theaurorian.enter_aurorian.title"),
                            Component.translatable("advancement.theaurorian.enter_aurorian.desc"),
                            BACKGROUND_LOCATION, AdvancementType.TASK, true, true, false)
                    .addCriterion("entered_aurorian", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TADimensions.AURORIAN_DIMENSION))
                    .save(consumer, TheAurorian.MOD_ID + ":enter_aurorian");
            Advancement.Builder.advancement().parent(enterAurorian).display(
                            TAItems.SILENT_WOOD_SWORD.get(),
                            Component.translatable("advancement.theaurorian.kill_aurorian_mob.title"),
                            Component.translatable("advancement.theaurorian.kill_aurorian_mob.desc"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("killed_aurorian_mob", KilledTrigger.TriggerInstance.playerKilledEntity(
                            EntityPredicate.Builder.entity().of(TAEntityTags.AURORIAN_MOBS)))
                    .save(consumer, TheAurorian.MOD_ID + ":kill_aurorian_mob");
        }

    }

} 