package cn.teampancake.theaurorian.common.data.datagen.recipes;

import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeSerializer;
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

public class MoonlightForgeRecipeBuilder implements RecipeBuilder {

    private final Ingredient equipment;
    private final Ingredient upgradeMaterial;
    private final ItemStack result;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    protected final MoonlightForgeSerializer.Factory<?> factory;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public MoonlightForgeRecipeBuilder(
            Ingredient equipment, Ingredient upgradeMaterial,
            ItemStack result, MoonlightForgeSerializer.Factory<?> factory) {
        this.equipment = equipment;
        this.upgradeMaterial = upgradeMaterial;
        this.result = result;
        this.factory = factory;
    }

    public static MoonlightForgeRecipeBuilder addRecipe(ItemLike equipment, ItemLike upgradeMaterial, ItemStack result) {
        return new MoonlightForgeRecipeBuilder(Ingredient.of(equipment), Ingredient.of(upgradeMaterial), result, MoonlightForgeRecipe::new);
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
        MoonlightForgeRecipe recipe = this.factory.create(this.equipment, this.upgradeMaterial, this.result);
        ResourceLocation r1 = id.withPrefix("moonlight_forge/");
        ResourceLocation r2 = id.withPrefix("recipes/moonlight_forge/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

}