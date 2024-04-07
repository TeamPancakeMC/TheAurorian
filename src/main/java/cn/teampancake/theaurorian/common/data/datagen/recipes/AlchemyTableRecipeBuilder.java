package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AlchemyTableRecipeBuilder implements RecipeBuilder {

    protected final Ingredient input1;
    protected final Ingredient input2;
    protected final Ingredient input3;
    protected final Ingredient material;
    protected final ItemStack result;
    protected int alchemyTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<? extends AlchemyTableRecipe> serializer;

    public AlchemyTableRecipeBuilder(
            Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result, int alchemyTime,
            RecipeSerializer<? extends AlchemyTableRecipe> serializer) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.material = material;
        this.result = result;
        this.alchemyTime = alchemyTime;
        this.serializer = serializer;
    }

    public static AlchemyTableRecipeBuilder addRecipe(
            Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result, int alchemyTime) {
        return new AlchemyTableRecipeBuilder(input1, input2, input3, material, result, alchemyTime, TARecipes.ALCHEMY_TABLE_SERIALIZER.get());
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new Result(recipeId.withPrefix("alchemy_table/"),
                this.input1, this.input2, this.input3, this.material, this.result, this.alchemyTime,
                this.advancement, recipeId.withPrefix("recipes/alchemy_table/"), this.serializer));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @SuppressWarnings({"ClassCanBeRecord", "ConstantConditions"})
    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient input1;
        private final Ingredient input2;
        private final Ingredient input3;
        private final Ingredient material;
        private final ItemStack result;
        private final int alchemyTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AlchemyTableRecipe> serializer;

        public Result(
                ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result,
                int alchemyTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<? extends AlchemyTableRecipe> serializer) {
            this.id = id;
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.material = material;
            this.result = result;
            this.alchemyTime = alchemyTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            Ingredient[] ingredients = new Ingredient[] {this.input1, this.input2, this.input3, this.material};
            String[] names = new String[] {"input1", "input2", "input3", "material"};
            for (int i = 0; i < ingredients.length; i++) {
                json.add(names[i], ingredients[i].toJson());
            }
            var resultJsonObject = new JsonObject();
            resultJsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result.getItem()).toString());
            if (this.result.getCount() > 1) {
                resultJsonObject.addProperty("count", this.result.getCount());
            }
            json.add("result", resultJsonObject);
            json.addProperty("alchemy_time", this.alchemyTime);
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