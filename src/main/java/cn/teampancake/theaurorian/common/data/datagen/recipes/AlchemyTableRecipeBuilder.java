package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableSerializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlchemyTableRecipeBuilder implements RecipeBuilder {

    protected final Ingredient input1;
    protected final Ingredient input2;
    protected final Ingredient input3;
    protected final Ingredient material;
    protected final ItemStack result;
    protected int alchemyTime;
    protected final AlchemyTableSerializer.Factory<?> factory;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public AlchemyTableRecipeBuilder(
            Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material,
            ItemStack result, int alchemyTime, AlchemyTableSerializer.Factory<?> factory) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.material = material;
        this.result = result;
        this.alchemyTime = alchemyTime;
        this.factory = factory;
    }

    public static AlchemyTableRecipeBuilder addRecipe(
            Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result, int alchemyTime) {
        return new AlchemyTableRecipeBuilder(input1, input2, input3, material, result, alchemyTime, AlchemyTableRecipe::new);
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
    public Item getResult() {
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
        AlchemyTableRecipe recipe = this.factory.create(
                this.input1, this.input2, this.input3,
                this.material, this.result, this.alchemyTime);
        ResourceLocation r1 = id.withPrefix("alchemy_table/");
        ResourceLocation r2 = id.withPrefix("recipes/alchemy_table/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

}