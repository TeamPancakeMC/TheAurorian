package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlchemyTableRecipeBuilder implements RecipeBuilder {

    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Ingredient material;
    private final ItemStack result;
    private int alchemyTime = 140;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private AlchemyTableRecipeBuilder(Ingredient material, ItemStack result) {
        this.material = material;
        this.result = result;
    }

    public static AlchemyTableRecipeBuilder alchemy(Ingredient material, ItemStack result) {
        return new AlchemyTableRecipeBuilder(material, result);
    }

    public AlchemyTableRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public AlchemyTableRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public AlchemyTableRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public AlchemyTableRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public AlchemyTableRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public AlchemyTableRecipeBuilder time(int time) {
        this.alchemyTime = time;
        return this;
    }

    @Override
    public AlchemyTableRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);
        return this;
    }

    @Override
    public AlchemyTableRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (this.criteria.isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AlchemyTableRecipe recipe = new AlchemyTableRecipe(
                this.ingredients, this.material, this.result, this.alchemyTime);
        ResourceLocation r1 = id.withPrefix("alchemy_table/");
        ResourceLocation r2 = id.withPrefix("recipes/alchemy_table/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

}