package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TAAdvancementProvider extends AdvancementProvider {

    public TAAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new TAAdvancements()));
    }

    private static class TAAdvancements implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            // 第一个成就：进入极光维度
            AdvancementHolder firstTraveler = Advancement.Builder.advancement()
                    .display(
                            TAItems.AURORIAN_CRYSTAL.get(),
                            Component.translatable("advancement.theaurorian.first_traveler.title"),
                            Component.translatable("advancement.theaurorian.first_traveler.desc"),
                            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("enter_aurorian", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(
                            ResourceKey.create(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")), 
                                    TheAurorian.prefix("the_aurorian"))
                    ))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(consumer, TheAurorian.MOD_ID + ":first_traveler");
            
            // 第二个成就：击杀极光生物
            Advancement.Builder.advancement()
                    .parent(firstTraveler)
                    .display(
                            TAItems.SILENT_WOOD_SWORD.get(),
                            Component.translatable("advancement.theaurorian.not_friendly.title"),
                            Component.translatable("advancement.theaurorian.not_friendly.desc"),
                            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("kill_aurorian_mob", KilledTrigger.TriggerInstance.playerKilledEntity(
                            EntityPredicate.Builder.entity().of(TAEntityTags.AURORIAN_MOBS)
                    ))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(consumer, TheAurorian.MOD_ID + ":not_friendly");
        }
    }
} 