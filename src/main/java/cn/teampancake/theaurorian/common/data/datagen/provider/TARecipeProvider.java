package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.base.*;
import cn.teampancake.theaurorian.common.data.datagen.recipes.AlchemyTableRecipeBuilder;
import cn.teampancake.theaurorian.common.data.datagen.recipes.MoonlightForgeRecipeBuilder;
import cn.teampancake.theaurorian.common.data.datagen.recipes.ScrapperRecipeBuilder;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class TARecipeProvider extends RecipeProvider {

    public TARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //Mod Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_COBBLESTONE.get(), 4)
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), 4)
                .define('#', TABlocks.AURORIAN_STONE.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(TABlocks.AURORIAN_STONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_FURNACE.get())
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get())
                .pattern("###").pattern("# #").pattern("###")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()),
                        has(TABlocks.AURORIAN_COBBLESTONE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.RUNE_STONE_BARS.get(), 16).define('#', TABlocks.RUNE_STONE.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.RUNE_STONE.get()), has(TABlocks.RUNE_STONE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.DARK_STONE_BARS.get(), 16).define('#', TABlocks.DARK_STONE_BRICKS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.DARK_STONE_BRICKS.get()), has(TABlocks.DARK_STONE_BRICKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_TEMPLE_BARS.get(), 16).define('#', TABlocks.MOON_TEMPLE_BRICKS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.MOON_TEMPLE_BRICKS.get()), has(TABlocks.MOON_TEMPLE_BRICKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_FURNACE_CHIMNEY.get())
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).define('I', TAItems.CERULEAN_INGOT.get())
                .pattern("#I#").pattern("#I#").pattern("#I#")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOONLIGHT_FORGE.get())
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).define('I', TABlocks.MOON_GEM.get())
                .define('O', TABlocks.AURORIAN_CRAFTING_TABLE.get()).define('U', TABlocks.AURORIAN_FURNACE.get())
                .pattern(" I ").pattern("#O#").pattern("#U#")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(getHasName(TABlocks.MOON_GEM.get()), has(TABlocks.MOON_GEM.get()))
                .unlockedBy(getHasName(TABlocks.AURORIAN_CRAFTING_TABLE.get()), has(TABlocks.AURORIAN_CRAFTING_TABLE.get()))
                .unlockedBy(getHasName(TABlocks.AURORIAN_FURNACE.get()), has(TABlocks.AURORIAN_FURNACE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_TORCH.get(), 4)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(TABlocks.MOON_GEM.get()))
                .pattern("X").pattern("#").unlockedBy(getHasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_TORCH.get(), 4)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(TAItems.AURORIAN_COAL.get()))
                .pattern("X").pattern("#").unlockedBy(getHasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_GLASS_PANE.get(), 16).define('#', TABlocks.MOON_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.MOON_GLASS.get()), has(TABlocks.MOON_SAND.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_GLASS_PANE.get(), 16).define('#', TABlocks.AURORIAN_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.AURORIAN_GLASS.get()), has(TABlocks.MOON_SAND.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.DARK_STONE_GLASS_PANE.get(), 16).define('#', TABlocks.DARK_STONE_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.DARK_STONE_GLASS.get()), has(TABlocks.MOON_SAND.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_CRAFTING_TABLE.get())
                .define('#', TABlocks.SILENT_TREE_PLANKS.get()).define('X', Blocks.CRAFTING_TABLE)
                .pattern(" # ").pattern("#X#").pattern(" # ")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_PLANKS.get()), has(TABlocks.SILENT_TREE_PLANKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_LADDER.get(), 3)
                .define('#', TAItems.SILENT_WOOD_STICK.get())
                .pattern("# #").pattern("###").pattern("# #")
                .unlockedBy(getHasName(TAItems.SILENT_WOOD_STICK.get()), has(TAItems.SILENT_WOOD_STICK.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.AURORIAN_SLIME_BOOTS.get())
                .define('I', TAItems.AURORIAN_SLIMEBALL.get())
                .define('O', TABlocks.MOON_GEM.get()).pattern("I I").pattern("O O")
                .unlockedBy(getHasName(TAItems.AURORIAN_SLIMEBALL.get()), has(TAItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(getHasName(TABlocks.MOON_GEM.get()), has(TABlocks.MOON_GEM.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.MOONSTONE_SHIELD.get())
                .define('W', TAItems.MOONSTONE_INGOT.get()).define('o', TAItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(getHasName(TAItems.MOONSTONE_INGOT.get()), has(TAItems.MOONSTONE_INGOT.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.CERULEAN_SHIELD.get())
                .define('W', TAItems.CERULEAN_INGOT.get()).define('o', TAItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.SILENT_WOOD_BOW.get())
                .define('#', TAItems.PLANT_FIBER.get()).define('I', TAItems.SILENT_WOOD_STICK.get())
                .pattern(" I#").pattern("I #").pattern(" I#")
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.TEA_CUP.get()).define('#', TAItems.CERULEAN_NUGGET.get())
                .define('I', TAItems.CERULEAN_INGOT.get()).pattern("# #").pattern(" I ")
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.SILENT_WOOD_STICK.get())
                .define('#', TABlocks.SILENT_TREE_PLANKS.get()).pattern("#").pattern("#")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_PLANKS.get()), has(TABlocks.SILENT_TREE_PLANKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TAItems.LAVENDER_BREAD.get()).define('#', TAItems.LAVENDER.get())
                .pattern("###").unlockedBy(getHasName(TAItems.LAVENDER.get()), has(TAItems.LAVENDER.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.LOCK_PICKS.get())
                .define('I', TAItems.CERULEAN_INGOT.get())
                .define('O', TAItems.SILENT_WOOD_STICK.get())
                .pattern("I I").pattern("OO ").pattern("OOI")
                .unlockedBy(getHasName(TAItems.SILENT_WOOD_STICK.get()), has(TAItems.SILENT_WOOD_STICK.get()))
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.DUNGEON_LOCATOR.get())
                .define('I', TAItems.CERULEAN_NUGGET.get())
                .define('O', TAItems.MOONSTONE_NUGGET.get())
                .define('X', TABlocks.AURORIAN_GLASS.get())
                .pattern("IOI").pattern("OXO").pattern("IOI")
                .unlockedBy(getHasName(TABlocks.AURORIAN_GLASS.get()), has(TABlocks.AURORIAN_GLASS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MYSTERIUM_WOOL_BED.get())
                .define('#', TABlocks.MYSTERIUM_WOOL.get())
                .define('X', TAItemTags.AURORIAN_PLANKS)
                .pattern("###").pattern("XXX").group("bed")
                .unlockedBy(getHasName(TABlocks.MYSTERIUM_WOOL.get()), has(TABlocks.MYSTERIUM_WOOL.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_CHEST.get())
                .define('#', TABlocks.SILENT_TREE_PLANKS.get()).define('X', Blocks.CHEST)
                .pattern("###").pattern("#X#").pattern("###")
                .unlockedBy("has_lots_of_items", CriteriaTriggers.INVENTORY_CHANGED
                        .createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(),
                        new InventoryChangeTrigger.TriggerInstance.Slots(MinMaxBounds.Ints.atLeast(10),
                                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY), List.of()))).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TAItems.GOLDEN_SILENT_WOOD_FRUIT.get())
                .define('#', Items.GOLD_INGOT).define('X', TAItems.SILENT_WOOD_FRUIT.get())
                .pattern("###").pattern("#X#").pattern("###")
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TAItems.KEBAB_WITH_MUSHROOM.get())
                .define('A', TAItemTags.COOKED_MEAT)
                .define('B', TAItemTags.COOKED_MEAT)
                .define('C', TABlocks.INDIGO_MUSHROOM.get())
                .pattern("A").pattern("B").pattern("C")
                .unlockedBy("has_cooked_meat", has(TAItemTags.COOKED_MEAT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.MOON_SHURIKEN.get())
                .define('#', TAItems.MOONSTONE_NUGGET.get())
                .pattern(" # ").pattern("# #").pattern(" # ")
                .unlockedBy(getHasName(TAItems.MOONSTONE_NUGGET.get()),
                        has(TAItems.MOONSTONE_NUGGET.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.UNSTABLE_CRYSTAL.get())
                .define('#', TAItems.CERULEAN_NUGGET.get()).define('X', Items.GUNPOWDER)
                .pattern("###").pattern("#X#").pattern("###")
                .unlockedBy(getHasName(TAItems.CERULEAN_NUGGET.get()),
                        has(TAItems.CERULEAN_NUGGET.get())).save(recipeOutput);
        //Vanilla Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING).define('#', TAItems.PLANT_FIBER.get())
                .pattern("###").pattern("###").pattern("###")
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get()))
                .save(recipeOutput, TheAurorian.prefix("string_from_plant_fiber"));
        //Mod Shapeless Recipes
        oneToOneConversionRecipe(recipeOutput, TAItems.LAVENDER_SEEDS.get(), TAItems.LAVENDER.get(), null);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_GLASS.get())
                .requires(TABlocks.MOON_GLASS.get(), (3)).requires(TAItems.PLANT_FIBER.get())
                .unlockedBy(getHasName(TABlocks.MOON_GLASS.get()), has(TABlocks.MOON_GLASS.get()))
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_BERRY_JAM.get()).requires(TAItems.SILK_BERRY.get(), (4))
                .unlockedBy(getHasName(TAItems.SILK_BERRY.get()), has(TAItems.SILK_BERRY.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_BERRY_JAM_SANDWICH.get())
                .requires(TAItems.SILK_BERRY_JAM.get()).requires(TAItems.LAVENDER_BREAD.get())
                .unlockedBy(getHasName(TAItems.SILK_BERRY_JAM.get()), has(TAItems.SILK_BERRY_JAM.get()))
                .unlockedBy(getHasName(TAItems.LAVENDER_BREAD.get()), has(TAItems.LAVENDER_BREAD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.AURORIAN_BACON.get(), 10).requires(TAItems.COOKED_AURORIAN_PORK.get(), (4))
                .unlockedBy(getHasName(TAItems.AURORIAN_BACON.get()), has(TAItems.AURORIAN_BACON.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_SHROOM_STEW.get())
                .requires(TAItems.SILK_BERRY.get(), (2)).requires(TABlocks.INDIGO_MUSHROOM.get(), (2))
                .unlockedBy(getHasName(TAItems.SILK_BERRY.get()), has(TAItems.SILK_BERRY.get()))
                .unlockedBy(getHasName(TABlocks.INDIGO_MUSHROOM.get()), has(TABlocks.INDIGO_MUSHROOM.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, TAItems.STICKY_SPIKER.get())
                .requires(TAItems.AURORIAN_SLIMEBALL.get()).requires(TAItems.CRYSTAL.get())
                .unlockedBy(getHasName(TAItems.AURORIAN_SLIMEBALL.get()), has(TAItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(getHasName(TAItems.CRYSTAL.get()), has(TAItems.CRYSTAL.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.DARK_STONE_KEY.get())
                .requires(TAItems.DUNGEON_KEEPER_AMULET.get()).requires(TAItems.CRYSTAL.get()).requires(TAItems.CERULEAN_NUGGET.get())
                .unlockedBy(getHasName(TAItems.DUNGEON_KEEPER_AMULET.get()), has(TAItems.DUNGEON_KEEPER_AMULET.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.MOON_TEMPLE_KEY.get())
                .requires(TAItems.DARK_AMULET.get()).requires(TAItems.CRYSTAL.get()).requires(TABlocks.MOON_GEM.get())
                .unlockedBy(getHasName(TAItems.DARK_AMULET.get()), has(TAItems.DARK_AMULET.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.MOON_TEMPLE_CELL_KEY.get())
                .requires(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(), (2)).requires(TABlocks.MOON_GEM.get())
                .unlockedBy(getHasName(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get()),
                        has(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.LAVENDER_SALAD.get())
                .requires(TAItems.LAVENDER.get()).requires(TAItems.BLUEBERRY.get())
                .requires(TAItems.SILK_BERRY_JAM.get()).requires(TABlocks.INDIGO_MUSHROOM.get()).requires(Items.BOWL)
                .unlockedBy(getHasName(TAItems.LAVENDER.get()), has(TAItems.LAVENDER.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SASHIMI.get())
                .requires(TAItems.FAKE_ALGAL_PIT_FISH.get()).requires(TAItems.SILK_BERRY_JAM.get())
                .unlockedBy(getHasName(TAItems.LAVENDER.get()), has(TAItems.LAVENDER.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, TAItems.AURORIAN_SLATE_BRICK.get())
                .requires(TABlocks.AURORIAN_COBBLESTONE.get(), 2)
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()),
                        has(TABlocks.AURORIAN_COBBLESTONE.get())).save(recipeOutput);
        //Vanilla Shapeless Recipes
        oneToOneConversionRecipe(recipeOutput, Items.ROTTEN_FLESH, TAItems.SOULLESS_FLESH.get(), null);
        oneToOneConversionRecipe(recipeOutput, Items.PINK_DYE, TABlocks.PETUNIA_PLANT.get(), null);
        //Mod Simple Cooking Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.AURORIAN_COBBLESTONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STONE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.MOON_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.MOON_GLASS.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.MOON_SAND.get()), has(TABlocks.MOON_SAND.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.AURORIAN_PERIDOTITE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.AURORIAN_PERIDOTITE.get()), has(TABlocks.AURORIAN_PERIDOTITE.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.RUNE_STONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_RUNE_STONE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.RUNE_STONE.get()), has(TABlocks.RUNE_STONE.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.DARK_STONE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_DARK_STONE_BRICKS.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.RUNE_STONE.get()), has(TABlocks.RUNE_STONE.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.MOON_TEMPLE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.MOON_TEMPLE_BRICKS.get()), has(TABlocks.MOON_TEMPLE_BRICKS.get())).save(recipeOutput);
        //food
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_PORK.get(), TAItems.COOKED_AURORIAN_PORK.get(), 0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_BEEF.get(), TAItems.COOKED_AURORIAN_BEEF.get(), 0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_RABBIT.get(), TAItems.COOKED_AURORIAN_RABBIT.get(), 0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_MUTTON.get() ,TAItems.COOKED_AURORIAN_MUTTON.get(), 0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.MOON_FISH.get(), TAItems.COOKED_MOON_FISH.get(),0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_WINGED_FISH.get(), TAItems.COOKED_AURORIAN_WINGED_FISH.get(),0.3F);
        buildFoodProcessRecipes(recipeOutput, TAItems.AURORIAN_WINTER_ROOT.get(), TAItems.ROASTED_AURORIAN_WINTER_ROOT.get(), 0.3F);

        //Moonlight Forge Recipes
        forging(recipeOutput, TAItems.MOONSTONE_SWORD.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_SWORD.get());
        forging(recipeOutput, TAItems.MOONSTONE_PICKAXE.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_PICKAXE.get());
        forging(recipeOutput, TAItems.MOONSTONE_SHIELD.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_SHIELD.get());
        forging(recipeOutput, TAItems.MOONSTONE_SWORD.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_SWORD.get());
        forging(recipeOutput, TAItems.MOONSTONE_AXE.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_AXE.get());
        forging(recipeOutput, TAItems.MOONSTONE_PICKAXE.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_PICKAXE.get());
        forging(recipeOutput, TAItems.MOONSTONE_SWORD.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_SWORD.get());
        forging(recipeOutput, TAItems.MOONSTONE_PICKAXE.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_PICKAXE.get());
        forging(recipeOutput, TAItems.MOONSTONE_SHIELD.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_SHIELD.get());
        forging(recipeOutput, TAItems.AURORIAN_STEEL_PICKAXE.get(), TAItems.TROPHY_MOON_QUEEN.get(), TAItems.QUEENS_CHIPPER.get());
        forging(recipeOutput, TAItems.MOONSTONE_SHIELD.get(), TAItems.TROPHY_MOON_QUEEN.get(), TAItems.MOON_SHIELD.get());
        forging(recipeOutput, TAItems.SILENT_WOOD_BOW.get(), TAItems.TROPHY_KEEPER.get(), TAItems.KEEPERS_BOW.get());
        //Alchemy Recipes
        alchemy(recipeOutput, TAItems.LAVENDER.get(), TABlocks.PETUNIA_PLANT.get(), TABlocks.ICE_CALENDULA.get(), Items.POTION,
                TAItems.AURORIAN_SPECIALTY_DRINK.get().getDefaultInstance(), 140);
        alchemy(recipeOutput, TAItems.LAVENDER.get(), TAItems.BLUEBERRY.get(), TABlocks.ICE_CALENDULA.get(), Items.POTION,
                TAItems.MOONLIT_BLUEBERRY_SPECIALTY_DRINK.get().getDefaultInstance(), 140);
        //Scrapper Recipes For Mod
        scrapping(recipeOutput, TAItems.UMBRA_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(recipeOutput, TAItems.UMBRA_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(recipeOutput, TAItems.UMBRA_SHIELD.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(recipeOutput, TAItems.SPIKED_CHESTPLATE.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(recipeOutput, TAItems.AURORIANITE_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(recipeOutput, TAItems.AURORIANITE_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(recipeOutput, TAItems.AURORIANITE_AXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(recipeOutput, TAItems.CRYSTALLINE_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(recipeOutput, TAItems.CRYSTALLINE_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(recipeOutput, TAItems.CRYSTALLINE_SHIELD.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(recipeOutput, TAItems.ABSORPTION_ORB.get(), TAItems.UMBRA_SCRAP.get(), 8);
        scrapping(recipeOutput, TAItems.LIVING_DIVINING_ROD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_SWORD.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 12);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_PICKAXE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 18);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_AXE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 18);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_SHOVEL.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 6);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_HOE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 12);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_HELMET.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 30);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_CHESTPLATE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 48);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_LEGGINGS.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 42);
        scrapping(recipeOutput, TAItems.AURORIAN_STEEL_BOOTS.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 24);
        scrapping(recipeOutput, TAItems.MOONSTONE_SWORD.get(), TAItems.MOONSTONE_NUGGET.get(), 12);
        scrapping(recipeOutput, TAItems.MOONSTONE_PICKAXE.get(), TAItems.MOONSTONE_NUGGET.get(), 18);
        scrapping(recipeOutput, TAItems.MOONSTONE_AXE.get(), TAItems.MOONSTONE_NUGGET.get(), 18);
        scrapping(recipeOutput, TAItems.MOONSTONE_SHOVEL.get(), TAItems.MOONSTONE_NUGGET.get(), 6);
        scrapping(recipeOutput, TAItems.MOONSTONE_HOE.get(), TAItems.MOONSTONE_NUGGET.get(), 12);
        scrapping(recipeOutput, TAItems.MOONSTONE_SHIELD.get(), TAItems.MOONSTONE_NUGGET.get(), 36);
        scrapping(recipeOutput, TAItems.CERULEAN_HELMET.get(), TAItems.CERULEAN_NUGGET.get(), 30);
        scrapping(recipeOutput, TAItems.CERULEAN_CHESTPLATE.get(), TAItems.CERULEAN_NUGGET.get(), 48);
        scrapping(recipeOutput, TAItems.CERULEAN_LEGGINGS.get(), TAItems.CERULEAN_NUGGET.get(), 42);
        scrapping(recipeOutput, TAItems.CERULEAN_BOOTS.get(), TAItems.CERULEAN_NUGGET.get(), 24);
        scrapping(recipeOutput, TAItems.CERULEAN_SHIELD.get(), TAItems.CERULEAN_NUGGET.get(), 36);
        //Scrapper Recipes For Vanilla
        scrapping(recipeOutput, Items.IRON_SWORD, Items.IRON_NUGGET, 12);
        scrapping(recipeOutput, Items.IRON_PICKAXE, Items.IRON_NUGGET, 18);
        scrapping(recipeOutput, Items.IRON_AXE, Items.IRON_NUGGET, 18);
        scrapping(recipeOutput, Items.IRON_SHOVEL, Items.IRON_NUGGET, 6);
        scrapping(recipeOutput, Items.IRON_HOE, Items.IRON_NUGGET, 12);
        scrapping(recipeOutput, Items.IRON_HELMET, Items.IRON_NUGGET, 30);
        scrapping(recipeOutput, Items.IRON_CHESTPLATE, Items.IRON_NUGGET, 48);
        scrapping(recipeOutput, Items.IRON_LEGGINGS, Items.IRON_NUGGET, 42);
        scrapping(recipeOutput, Items.IRON_BOOTS, Items.IRON_NUGGET, 24);
        scrapping(recipeOutput, Items.GOLDEN_SWORD, Items.GOLD_NUGGET, 12);
        scrapping(recipeOutput, Items.GOLDEN_PICKAXE, Items.GOLD_NUGGET, 18);
        scrapping(recipeOutput, Items.GOLDEN_AXE, Items.GOLD_NUGGET, 18);
        scrapping(recipeOutput, Items.GOLDEN_SHOVEL, Items.GOLD_NUGGET, 6);
        scrapping(recipeOutput, Items.GOLDEN_HOE, Items.GOLD_NUGGET, 12);
        scrapping(recipeOutput, Items.GOLDEN_HELMET, Items.GOLD_NUGGET, 30);
        scrapping(recipeOutput, Items.GOLDEN_CHESTPLATE, Items.GOLD_NUGGET, 48);
        scrapping(recipeOutput, Items.GOLDEN_LEGGINGS, Items.GOLD_NUGGET, 42);
        scrapping(recipeOutput, Items.GOLDEN_BOOTS, Items.GOLD_NUGGET, 24);
        scrapping(recipeOutput, Items.DIAMOND_SWORD, Items.DIAMOND, 1);
        scrapping(recipeOutput, Items.DIAMOND_PICKAXE, Items.DIAMOND, 2);
        scrapping(recipeOutput, Items.DIAMOND_AXE, Items.DIAMOND, 2);
        scrapping(recipeOutput, Items.DIAMOND_SHOVEL, Items.DIAMOND, 1);
        scrapping(recipeOutput, Items.DIAMOND_HOE, Items.DIAMOND, 1);
        scrapping(recipeOutput, Items.DIAMOND_HELMET, Items.DIAMOND, 3);
        scrapping(recipeOutput, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 5);
        scrapping(recipeOutput, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 4);
        scrapping(recipeOutput, Items.DIAMOND_BOOTS, Items.DIAMOND, 2);

        signBuilder(TAItems.SILENT_WOOD_SIGN.get(), Ingredient.of(TABlocks.SILENT_TREE_PLANKS.get()));
        signBuilder(TAItems.WEEPING_WILLOW_WOOD_SIGN.get(), Ingredient.of(TABlocks.WEEPING_WILLOW_PLANKS.get()));
        signBuilder(TAItems.CURTAIN_WOOD_SIGN.get(), Ingredient.of(TABlocks.CURTAIN_TREE_PLANKS.get()));
        signBuilder(TAItems.CURSED_FROST_WOOD_SIGN.get(), Ingredient.of(TABlocks.CURSED_FROST_TREE_PLANKS.get()));
        hangingSign(recipeOutput, TAItems.SILENT_WOOD_HANGING_SIGN.get(), TABlocks.STRIPPED_SILENT_TREE_LOG.get());
        hangingSign(recipeOutput, TAItems.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get());
        hangingSign(recipeOutput, TAItems.CURTAIN_WOOD_HANGING_SIGN.get(), TABlocks.STRIPPED_CURTAIN_TREE_LOG.get());
        hangingSign(recipeOutput, TAItems.CURSED_FROST_WOOD_HANGING_SIGN.get(), TABlocks.STRIPPED_CURSED_FROST_TREE_LOG.get());
        cut(recipeOutput, RecipeCategory.BUILDING_BLOCKS, TABlocks.CUT_MOON_SANDSTONE.get(), TABlocks.MOON_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, TABlocks.CUT_MOON_SANDSTONE.get(), TABlocks.MOON_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STONE_BRICKS.get(), TABlocks.AURORIAN_STONE.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.CERULEAN_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.MOONSTONE_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_COAL_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, TAItems.AURORIAN_STEEL.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STEEL_BLOCK.get());
        oreSmelting(recipeOutput, List.of(TABlocks.MOONSTONE_ORE.get(), TAItems.RAW_MOONSTONE.get(), TABlocks.EROSIVE_MOONSTONE_ORE.get()), RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), (0.1F), (200), "moonstone");
        oreSmelting(recipeOutput, List.of(TABlocks.CERULEAN_ORE.get(), TAItems.RAW_CERULEAN.get(), TABlocks.EROSIVE_CERULEAN_ORE.get()), RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreSmelting(recipeOutput, List.of(TABlocks.GEODE_ORE.get(), TABlocks.EROSIVE_GEODE_ORE.get()), RecipeCategory.MISC, TAItems.CRYSTAL.get(), (0.1F), (200), "geode");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_IRON_ORE.get(), TABlocks.EROSIVE_AURORIAN_IRON_ORE.get()), RecipeCategory.MISC,Items.IRON_INGOT, (0.1F), (200), "aurorian_iron");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_GOLD_ORE.get(), TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get()), RecipeCategory.MISC,Items.GOLD_INGOT, (0.1F), (200), "aurorian_gold");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_COPPER_ORE.get(), TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get()), RecipeCategory.MISC,Items.COPPER_INGOT, (0.1F), (200), "aurorian_copper");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_DIAMOND_ORE.get(), TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get()), RecipeCategory.MISC,Items.DIAMOND, (0.1F), (200), "aurorian_diamond");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_REDSTONE_ORE.get(), TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get()), RecipeCategory.MISC,Items.REDSTONE, (0.1F), (200), "aurorian_redstone");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_LAPIS_ORE.get(), TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get()), RecipeCategory.MISC,Items.LAPIS_LAZULI, (0.1F), (200), "aurorian_lapis");
        oreSmelting(recipeOutput, List.of(TABlocks.AURORIAN_EMERALD_ORE.get(), TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get()), RecipeCategory.MISC,Items.EMERALD, (0.1F), (200), "aurorian_emerald");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreBlasting(recipeOutput, List.of(TABlocks.MOONSTONE_ORE.get(), TAItems.RAW_MOONSTONE.get(), TABlocks.EROSIVE_MOONSTONE_ORE.get()), RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), (0.1F), (200), "moonstone");
        oreBlasting(recipeOutput, List.of(TABlocks.CERULEAN_ORE.get(), TAItems.RAW_CERULEAN.get(), TABlocks.EROSIVE_CERULEAN_ORE.get()), RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreBlasting(recipeOutput, List.of(TABlocks.GEODE_ORE.get(), TABlocks.EROSIVE_GEODE_ORE.get()), RecipeCategory.MISC, TAItems.CRYSTAL.get(), (0.1F), (200), "geode");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_IRON_ORE.get(), TABlocks.EROSIVE_AURORIAN_IRON_ORE.get()), RecipeCategory.MISC,Items.IRON_INGOT, (0.1F), (200), "aurorian_iron");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_GOLD_ORE.get(), TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get()), RecipeCategory.MISC,Items.GOLD_INGOT, (0.1F), (200), "aurorian_gold");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_COPPER_ORE.get(), TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get()), RecipeCategory.MISC,Items.COPPER_INGOT, (0.1F), (200), "aurorian_copper");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_DIAMOND_ORE.get(), TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get()), RecipeCategory.MISC,Items.DIAMOND, (0.1F), (200), "aurorian_diamond");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_REDSTONE_ORE.get(), TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get()), RecipeCategory.MISC,Items.REDSTONE, (0.1F), (200), "aurorian_redstone");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_LAPIS_ORE.get(), TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get()), RecipeCategory.MISC,Items.LAPIS_LAZULI, (0.1F), (200), "aurorian_lapis");
        oreBlasting(recipeOutput, List.of(TABlocks.AURORIAN_EMERALD_ORE.get(), TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get()), RecipeCategory.MISC,Items.EMERALD, (0.1F), (200), "aurorian_emerald");
        planksFromLogs(recipeOutput, TABlocks.SILENT_TREE_PLANKS.get(), TAItemTags.SILENT_TREE_LOGS, 4);
        planksFromLogs(recipeOutput, TABlocks.WEEPING_WILLOW_PLANKS.get(), TAItemTags.WEEPING_WILLOW_LOGS, 4);
        planksFromLogs(recipeOutput, TABlocks.CURTAIN_TREE_PLANKS.get(), TAItemTags.CURTAIN_TREE_LOGS, 4);
        planksFromLogs(recipeOutput, TABlocks.CURSED_FROST_TREE_PLANKS.get(), TAItemTags.CURSED_FROST_TREE_LOGS, 4);
        woodFromLogs(recipeOutput, TABlocks.SILENT_TREE_WOOD.get(), TABlocks.SILENT_TREE_LOG.get());
        woodFromLogs(recipeOutput, TABlocks.WEEPING_WILLOW_WOOD.get(), TABlocks.WEEPING_WILLOW_LOG.get());
        woodFromLogs(recipeOutput, TABlocks.CURTAIN_TREE_WOOD.get(), TABlocks.CURTAIN_TREE_LOG.get());
        woodFromLogs(recipeOutput, TABlocks.CURSED_FROST_TREE_WOOD.get(), TABlocks.CURSED_FROST_TREE_LOG.get());
        this.buildArmorRecipes(recipeOutput, TAItems.AURORIAN_STEEL_HELMET.get(), TAItems.AURORIAN_STEEL_CHESTPLATE.get(),
                TAItems.AURORIAN_STEEL_LEGGINGS.get(), TAItems.AURORIAN_STEEL_BOOTS.get(), TAItems.AURORIAN_STEEL.get());
        this.buildArmorRecipes(recipeOutput, TAItems.CERULEAN_HELMET.get(), TAItems.CERULEAN_CHESTPLATE.get(),
                TAItems.CERULEAN_LEGGINGS.get(), TAItems.CERULEAN_BOOTS.get(), TAItems.CERULEAN_INGOT.get());
        this.buildArmorRecipes(recipeOutput, TAItems.SPECTRAL_HELMET.get(), TAItems.SPECTRAL_CHESTPLATE.get(),
                TAItems.SPECTRAL_LEGGINGS.get(), TAItems.SPECTRAL_BOOTS.get(), TAItems.SPECTRAL_SILK.get());
        this.buildArmorRecipes(recipeOutput, TAItems.MYSTERIUM_WOOL_HELMET.get(), TAItems.MYSTERIUM_WOOL_CHESTPLATE.get(),
                TAItems.MYSTERIUM_WOOL_LEGGINGS.get(), TAItems.MYSTERIUM_WOOL_BOOTS.get(), TABlocks.MYSTERIUM_WOOL.get());
        this.buildBaseToolRecipes(recipeOutput, TAItems.AURORIAN_STEEL_SWORD.get(), TAItems.AURORIAN_STEEL_PICKAXE.get(),
                TAItems.AURORIAN_STEEL_SHOVEL.get(), TAItems.AURORIAN_STEEL_AXE.get(),
                TAItems.AURORIAN_STEEL_HOE.get(), TAItems.AURORIAN_STEEL.get());
        this.buildBaseToolRecipes(recipeOutput, TAItems.AURORIAN_STONE_SWORD.get(), TAItems.AURORIAN_STONE_PICKAXE.get(),
                TAItems.AURORIAN_STONE_SHOVEL.get(), TAItems.AURORIAN_STONE_AXE.get(),
                TAItems.AURORIAN_STONE_HOE.get(), TABlocks.AURORIAN_COBBLESTONE.get());
        this.buildBaseToolRecipes(recipeOutput, TAItems.MOONSTONE_SWORD.get(), TAItems.MOONSTONE_PICKAXE.get(),
                TAItems.MOONSTONE_SHOVEL.get(), TAItems.MOONSTONE_AXE.get(),
                TAItems.MOONSTONE_HOE.get(), TAItems.MOONSTONE_INGOT.get());
        this.buildBaseToolRecipes(recipeOutput, TAItems.SILENT_WOOD_SWORD.get(), TAItems.SILENT_WOOD_PICKAXE.get(),
                TAItems.SILENT_WOOD_SHOVEL.get(), TAItems.SILENT_WOOD_AXE.get(),
                TAItems.SILENT_WOOD_HOE.get(), TABlocks.SILENT_TREE_PLANKS.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.UMBRA_SCRAP.get(), TAItems.UMBRA_INGOT.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.AURORIANITE_SCRAP.get(), TAItems.AURORIANITE_INGOT.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.CRYSTALLINE_SCRAP.get(), TAItems.CRYSTALLINE_INGOT.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.CERULEAN_NUGGET.get(), TAItems.CERULEAN_INGOT.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.MOONSTONE_NUGGET.get(), TAItems.MOONSTONE_INGOT.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.AURORIAN_COAL_NUGGET.get(), TAItems.AURORIAN_COAL.get());
        this.buildNuggetToIngotRecipes(recipeOutput, TAItems.AURORIAN_STEEL_NUGGET.get(), TAItems.AURORIAN_STEEL.get());
        this.buildTeaRecipes(recipeOutput, TAItems.LAVENDER_TEA.get(), TAItems.LAVENDER.get());
        this.buildTeaRecipes(recipeOutput, TAItems.SILK_BERRY_TEA.get(), TAItems.SILK_BERRY.get());
        this.buildTeaRecipes(recipeOutput, TAItems.LAVENDER_SEEDY_TEA.get(), TAItems.LAVENDER_SEEDS.get());
        this.buildTeaRecipes(recipeOutput, TAItems.PETUNIA_TEA.get(), TABlocks.PETUNIA_PLANT.get());
        this.buildArrowRecipes(recipeOutput, TAItems.CERULEAN_ARROW.get(), TAItems.CERULEAN_NUGGET.get(), 1);
        this.buildArrowRecipes(recipeOutput, TAItems.CRYSTAL_ARROW.get(), TAItems.CRYSTAL.get(), 16);
        this.buildSickleRecipes(recipeOutput, TAItems.SILENT_WOOD_SICKLE.get(), TAItems.AURORIAN_STEEL.get());
        this.buildSickleRecipes(recipeOutput, TAItems.AURORIAN_STONE_SICKLE.get(), TABlocks.AURORIAN_COBBLESTONE.get());
        this.buildSickleRecipes(recipeOutput, TAItems.MOONSTONE_SICKLE.get(), TAItems.MOONSTONE_INGOT.get());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof PressurePlateBlockWithBase pressurePlateBlock) {
                pressurePlate(recipeOutput, pressurePlateBlock, pressurePlateBlock.getBase());
            } else if (block instanceof FenceGateBlockWithBase fenceGateBlock) {
                Block base = fenceGateBlock.getBase();
                fenceGateBuilder(fenceGateBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
            } else if (block instanceof TrapDoorBlockWithBase trapDoorBlock) {
                Block base = trapDoorBlock.getBase();
                trapdoorBuilder(trapDoorBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
            } else if (block instanceof ButtonBlockWithBase buttonBlock) {
                Block base = buttonBlock.getBase();
                buttonBuilder(buttonBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
            } else if (block instanceof StairBlock stairBlock) {
                Block base = stairBlock.base;
                stairBuilder(stairBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
                if (stairBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, stairBlock, base);
                }
            } else if (block instanceof FenceBlockWithBase fenceBlock) {
                Block base = fenceBlock.getBase();
                fenceBuilder(fenceBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                Block base = doorBlock.getBase();
                doorBuilder(doorBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(recipeOutput);
            } else if (block instanceof SlabBlockWithBase slabBlock) {
                slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, slabBlock, slabBlock.getBase());
                if (slabBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, slabBlock, slabBlock.getBase(), 2);
                }
            } else if (block instanceof WallBlockWithBase wallBlock) {
                wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, wallBlock, wallBlock.getBase());
                stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, wallBlock, wallBlock.getBase());
            } else if (block instanceof VerticalStairBlockWithBase verticalStairBlock) {
                verticalStairs(recipeOutput, verticalStairBlock);
                if (verticalStairBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, verticalStairBlock, verticalStairBlock.getBase());
                }
            } else if (block instanceof VerticalSlabBlockWithBase verticalSlabBlock) {
                verticalSlab(recipeOutput, verticalSlabBlock);
                if (verticalSlabBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, verticalSlabBlock, verticalSlabBlock.getBase(), 2);
                }
            }
        }
    }

    public static void forging(RecipeOutput recipeOutput, ItemLike equipment, ItemLike upgradeMaterial, ItemLike result) {
        MoonlightForgeRecipeBuilder.addRecipe(equipment, upgradeMaterial, result.asItem().getDefaultInstance()).unlockedBy(getHasName(upgradeMaterial), has(upgradeMaterial))
                .save(recipeOutput, TheAurorian.prefix("forge_" + getItemName(equipment) + "_to_" + getItemName(result)));
    }

    public static void alchemy(
            RecipeOutput recipeOutput, ItemLike input1, ItemLike input2, ItemLike input3, ItemLike material, ItemStack result, int alchemyTime) {
        AlchemyTableRecipeBuilder.addRecipe(Ingredient.of(input1), Ingredient.of(input2), Ingredient.of(input3), Ingredient.of(material), result, alchemyTime)
                .unlockedBy(getHasName(input1), has(input1)).save(recipeOutput, TheAurorian.prefix("alchemy_" + getItemName(result.getItem())));
    }

    public static void scrapping(RecipeOutput recipeOutput, ItemLike ingredient, ItemLike result, int amount) {
        ScrapperRecipeBuilder.addRecipe(ingredient, result.asItem().getDefaultInstance(), amount).unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, TheAurorian.prefix("scrap_" + getItemName(ingredient) + "_to_" + getItemName(result)));
    }

    private static void verticalStairs(RecipeOutput recipeOutput, VerticalStairBlockWithBase slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 4).define('#', Ingredient.of(slab.getBase()))
                .pattern("#").pattern("#").pattern("#")
                .unlockedBy(getHasName(slab.getBase()), has(slab.getBase())).save(recipeOutput);
    }

    private static void verticalSlab(RecipeOutput recipeOutput, VerticalSlabBlockWithBase stair) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair, 6).define('#', Ingredient.of(stair.getBase()))
                .pattern("###").pattern(" ##").pattern("  #")
                .unlockedBy(getHasName(stair.getBase()), has(stair.getBase())).save(recipeOutput);
    }

    private void buildArmorRecipes(RecipeOutput recipeOutput, ItemLike helmet,
            ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet).define('X', material)
                .pattern("XXX").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate).define('X', material)
                .pattern("X X").pattern("XXX").pattern("XXX")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings).define('X', material)
                .pattern("XXX").pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots).define('X', material)
                .pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    private void buildBaseToolRecipes(RecipeOutput recipeOutput, ItemLike sword,
            ItemLike pickaxe, ItemLike shovel, ItemLike axe, ItemLike hoe, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("X").pattern("#").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("#").pattern("#").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern("X#").pattern(" #").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern(" #").pattern(" #").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    private void buildNuggetToIngotRecipes(RecipeOutput recipeOutput, ItemLike nugget, ItemLike ingot) {
        nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, nugget,
                RecipeCategory.MISC, ingot, getConversionRecipeName(ingot, nugget), getItemName(ingot));
    }

    private void buildTeaRecipes(RecipeOutput recipeOutput, ItemLike tea, ItemLike ingredient) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, tea).requires(TAItems.TEA_CUP.get()).requires(ingredient, (8))
                .unlockedBy(getHasName(ingredient), has(ingredient)).save(recipeOutput);
    }

    private void buildArrowRecipes(RecipeOutput recipeOutput, ItemLike result, ItemLike head, int count) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, count)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', head)
                .define('Y', TABlocks.SILENT_TREE_LEAVES.get())
                .pattern("X").pattern("#").pattern("Y")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_LEAVES.get()),
                        has(TABlocks.SILENT_TREE_LEAVES.get()))
                .unlockedBy(getHasName(head), has(head)).save(recipeOutput);
    }

    private void buildSickleRecipes(RecipeOutput recipeOutput, ItemLike sickle, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, sickle).define('#', material).define('I', TAItems.SILENT_WOOD_STICK.get())
                .pattern("##I").pattern("# I").pattern("  I").unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    public static void buildFoodProcessRecipes(RecipeOutput recipeOutput, Item input, Item output, float xp) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.FOOD, output, xp, 200)
                .unlockedBy(getHasName(input), has(input)).save(recipeOutput, TheAurorian.prefix(getItemName(output) + "_smelting"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), RecipeCategory.FOOD, output, xp, 100)
                .unlockedBy(getHasName(input), has(input)).save(recipeOutput, TheAurorian.prefix(getItemName(output) + "_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), RecipeCategory.FOOD, output, xp, 600)
                .unlockedBy(getHasName(input), has(input)).save(recipeOutput, TheAurorian.prefix(getItemName(output) + "_campfire_cooking"));
    }

}