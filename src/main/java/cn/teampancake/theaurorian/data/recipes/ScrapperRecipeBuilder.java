package cn.teampancake.theaurorian.data.recipes;

import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.registry.ModRecipes;
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
public class ScrapperRecipeBuilder implements RecipeBuilder {

    private final Ingredient ingredient;
    private final Item result;
    private final int amount;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<? extends ScrapperRecipe> serializer;

    public ScrapperRecipeBuilder(Ingredient ingredient, Item result, int amount,
            RecipeSerializer<? extends ScrapperRecipe> serializer) {
        this.ingredient = ingredient;
        this.result = result;
        this.amount = amount;
        this.serializer = serializer;
    }

    public static ScrapperRecipeBuilder addRecipe(ItemLike input, ItemLike result, int amount) {
        return new ScrapperRecipeBuilder(Ingredient.of(input), result.asItem(), amount, ModRecipes.SCRAPPER_SERIALIZER.get());
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
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        } else {
            this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                    .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
            finishedRecipeConsumer.accept(new ScrapperRecipeBuilder.Result(recipeId.withPrefix("scrap/"), this.ingredient, this.result, this.amount,
                    this.advancement, recipeId.withPrefix("recipes/scrap/"), this.serializer));
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @MethodsReturnNonnullByDefault
    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int amount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends ScrapperRecipe> serializer;

        public Result(ResourceLocation id, Ingredient ingredient, Item result, int amount, Advancement.Builder advancement,
                ResourceLocation advancementId, RecipeSerializer<? extends ScrapperRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.amount = amount;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).getPath());
            json.addProperty("amount", this.amount);
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