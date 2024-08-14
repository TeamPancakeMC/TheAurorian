package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperSerializer;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScrapperRecipeBuilder implements RecipeBuilder {

    private final Ingredient ingredient;
    private final ItemStack result;
    private final int amount;
    protected final ScrapperSerializer.Factory<?> factory;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ScrapperRecipeBuilder(
            Ingredient ingredient, ItemStack result, int amount,
            ScrapperSerializer.Factory<?> factory) {
        this.ingredient = ingredient;
        this.result = result;
        this.amount = amount;
        this.factory = factory;
    }

    public static ScrapperRecipeBuilder addRecipe(ItemLike input, ItemStack result, int amount) {
        return new ScrapperRecipeBuilder(Ingredient.of(input), result, amount, ScrapperRecipe::new);
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        ScrapperRecipe recipe = this.factory.create(this.ingredient, this.result, this.amount);
        ResourceLocation r1 = id.withPrefix("scrapper/");
        ResourceLocation r2 = id.withPrefix("recipes/scrapper/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

}