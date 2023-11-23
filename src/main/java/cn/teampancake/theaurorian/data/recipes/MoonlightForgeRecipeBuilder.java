package cn.teampancake.theaurorian.data.recipes;

import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.registry.TARecipes;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class MoonlightForgeRecipeBuilder implements RecipeBuilder {

    private final Ingredient equipment;
    private final Ingredient upgradeMaterial;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<? extends MoonlightForgeRecipe> serializer;

    public MoonlightForgeRecipeBuilder(Ingredient equipment, Ingredient upgradeMaterial,
            Item result, RecipeSerializer<? extends MoonlightForgeRecipe> serializer) {
        this.equipment = equipment;
        this.upgradeMaterial = upgradeMaterial;
        this.result = result;
        this.serializer = serializer;
    }

    public static MoonlightForgeRecipeBuilder addRecipe(ItemLike equipment, ItemLike upgradeMaterial, ItemLike result) {
        return new MoonlightForgeRecipeBuilder(Ingredient.of(equipment), Ingredient.of(upgradeMaterial),
                result.asItem(), TARecipes.MOONLIGHT_FORGE_SERIALIZER.get());
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new MoonlightForgeRecipeBuilder.Result(recipeId.withPrefix("moonlight_forge/"),
                this.equipment, this.upgradeMaterial, this.result, this.advancement,
                recipeId.withPrefix("recipes/moonlight_forge/"), this.serializer));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @MethodsReturnNonnullByDefault
    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient equipment;
        private final Ingredient upgradeMaterial;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends MoonlightForgeRecipe> serializer;

        public Result(ResourceLocation id, Ingredient equipment, Ingredient upgradeMaterial,
                Item result, Advancement.Builder advancement, ResourceLocation advancementId,
                RecipeSerializer<? extends MoonlightForgeRecipe> serializer) {
            this.id = id;
            this.equipment = equipment;
            this.upgradeMaterial = upgradeMaterial;
            this.result = result;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        public void serializeRecipeData(JsonObject json) {
            json.add("equipment", this.equipment.toJson());
            json.add("upgrade_material", this.upgradeMaterial.toJson());
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).getPath());
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Nullable @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }

    }

}