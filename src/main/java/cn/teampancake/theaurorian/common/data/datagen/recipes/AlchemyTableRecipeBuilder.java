package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AlchemyTableRecipeBuilder implements RecipeBuilder {

    protected final Item input1;
    protected final Item input2;
    protected final Item input3;
    protected final Item material;
    protected final Item result;
    protected int input1Amount;
    protected int input2Amount;
    protected int input3Amount;
    protected int resultAmount;
    protected int alchemyTime;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<? extends AlchemyTableRecipe> serializer;

    public AlchemyTableRecipeBuilder(
            Item input1, Item input2, Item input3, Item material, Item result,
            int input1Amount, int input2Amount, int input3Amount, int resultAmount, int alchemyTime,
            RecipeSerializer<? extends AlchemyTableRecipe> serializer) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.material = material;
        this.result = result;
        this.input1Amount = input1Amount;
        this.input2Amount = input2Amount;
        this.input3Amount = input3Amount;
        this.resultAmount = resultAmount;
        this.alchemyTime = alchemyTime;
        this.serializer = serializer;
    }

    public static AlchemyTableRecipeBuilder addRecipe(
            ItemLike input1, ItemLike input2, ItemLike input3, ItemLike material, Item result,
            int input1Amount, int input2Amount, int input3Amount, int resultAmount, int alchemyTime) {
        return new AlchemyTableRecipeBuilder(input1.asItem(), input2.asItem(), input3.asItem(), material.asItem(), result,
                input1Amount, input2Amount, input3Amount, resultAmount, alchemyTime, TARecipes.ALCHEMY_TABLE_SERIALIZER.get());
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
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new Result(recipeId.withPrefix("alchemy_table/"),
                this.input1, this.input2, this.input3, this.material, this.result,
                this.input1Amount, this.input2Amount, this.input3Amount, this.resultAmount, this.alchemyTime,
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
        private final Item input1;
        private final Item input2;
        private final Item input3;
        private final Item material;
        private final Item result;
        private final int input1Amount;
        private final int input2Amount;
        private final int input3Amount;
        private final int resultAmount;
        private final int alchemyTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AlchemyTableRecipe> serializer;

        public Result(
                ResourceLocation id, Item input1, Item input2, Item input3, Item material, Item result,
                int input1Amount, int input2Amount, int input3Amount, int resultAmount, int alchemyTime,
                Advancement.Builder advancement, ResourceLocation advancementId,
                RecipeSerializer<? extends AlchemyTableRecipe> serializer) {
            this.id = id;
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.material = material;
            this.result = result;
            this.input1Amount = input1Amount;
            this.input2Amount = input2Amount;
            this.input3Amount = input3Amount;
            this.resultAmount = resultAmount;
            this.alchemyTime = alchemyTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            Item[] items = new Item[] {this.input1, this.input2, this.input3, this.result};
            int[] amounts = new int[] {this.input1Amount, this.input2Amount, this.input3Amount, this.resultAmount};
            String[] names = new String[] {"input1", "input2", "input3", "result"};
            JsonObject[] jsonObjects = new JsonObject[items.length];
            for (int i = 0; i < items.length; i++) {
                jsonObjects[i] = new JsonObject();
                String value = ForgeRegistries.ITEMS.getKey(items[i]).toString();
                jsonObjects[i].addProperty("item", value);
                if (amounts[i] > 1) {
                    jsonObjects[i].addProperty("amount", amounts[i]);
                }

                json.add(names[i], jsonObjects[i]);
            }

            json.addProperty("material", ForgeRegistries.ITEMS.getKey(this.material).toString());
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