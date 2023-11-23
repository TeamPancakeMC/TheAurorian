package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.SlabBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.WallBlockWithBase;
import cn.teampancake.theaurorian.data.recipes.MoonlightForgeRecipeBuilder;
import cn.teampancake.theaurorian.data.recipes.ScrapperRecipeBuilder;
import cn.teampancake.theaurorian.data.tags.TAItemTags;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.TACommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class TARecipeProvider extends RecipeProvider {

    public TARecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //Mod Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_COBBLESTONE.get(), 4)
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), 4)
                .define('#', TABlocks.AURORIAN_STONE.get()).pattern("##").pattern("##")
                .unlockedBy(getHasName(TABlocks.AURORIAN_STONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_FURNACE.get()).define('#', TABlocks.AURORIAN_COBBLESTONE.get())
                .pattern("###").pattern("# #").pattern("###")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.RUNE_STONE_BARS.get(), 16).define('#', TABlocks.RUNE_STONE.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.RUNE_STONE.get()), has(TABlocks.RUNE_STONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_TEMPLE_BARS.get(), 16).define('#', TABlocks.MOON_TEMPLE_BRICKS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.MOON_TEMPLE_BRICKS.get()), has(TABlocks.MOON_TEMPLE_BRICKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_FURNACE_CHIMNEY.get())
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).define('I', TAItems.CERULEAN_INGOT.get())
                .pattern("#I#").pattern("#I#").pattern("#I#")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOONLIGHT_FORGE.get())
                .define('#', TABlocks.AURORIAN_COBBLESTONE.get()).define('I', TABlocks.MOON_GEM.get())
                .define('O', TABlocks.SILENT_WOOD_CRAFTING_TABLE.get()).define('U', TABlocks.AURORIAN_FURNACE.get())
                .pattern(" I ").pattern("#O#").pattern("#U#")
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(getHasName(TABlocks.MOON_GEM.get()), has(TABlocks.MOON_GEM.get()))
                .unlockedBy(getHasName(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get()), has(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get()))
                .unlockedBy(getHasName(TABlocks.AURORIAN_FURNACE.get()), has(TABlocks.AURORIAN_FURNACE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_TORCH.get(), 4)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(TABlocks.MOON_GEM.get()))
                .pattern("X").pattern("#").unlockedBy(getHasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_TORCH.get(), 4)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(TAItems.AURORIAN_COAL.get()))
                .pattern("X").pattern("#").unlockedBy(getHasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.AURORIAN_GLASS_PANE.get(), 16).define('#', TABlocks.AURORIAN_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.AURORIAN_GLASS.get()), has(TABlocks.MOON_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.MOON_GLASS_PANE.get(), 16).define('#', TABlocks.MOON_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(getHasName(TABlocks.MOON_GLASS.get()), has(TABlocks.MOON_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_CRAFTING_TABLE.get())
                .define('#', TABlocks.SILENT_TREE_PLANKS.get()).define('X', Blocks.CRAFTING_TABLE)
                .pattern(" # ").pattern("#X#").pattern(" # ")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_PLANKS.get()), has(TABlocks.SILENT_TREE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABlocks.SILENT_WOOD_LADDER.get(), 3)
                .define('#', TAItems.SILENT_WOOD_STICK.get())
                .pattern("# #").pattern("###").pattern("# #")
                .unlockedBy(getHasName(TAItems.SILENT_WOOD_STICK.get()), has(TAItems.SILENT_WOOD_STICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.AURORIAN_SLIME_BOOTS.get())
                .define('I', TAItems.AURORIAN_SLIMEBALL.get())
                .define('O', TABlocks.MOON_GEM.get()).pattern("I I").pattern("O O")
                .unlockedBy(getHasName(TAItems.AURORIAN_SLIMEBALL.get()), has(TAItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(getHasName(TABlocks.MOON_GEM.get()), has(TABlocks.MOON_GEM.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.MOONSTONE_SHIELD.get())
                .define('W', TAItems.MOONSTONE_INGOT.get()).define('o', TAItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(getHasName(TAItems.MOONSTONE_INGOT.get()), has(TAItems.MOONSTONE_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.CERULEAN_SHIELD.get())
                .define('W', TAItems.CERULEAN_INGOT.get()).define('o', TAItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TAItems.SILENT_WOOD_BOW.get())
                .define('#', TAItems.PLANT_FIBER.get()).define('I', TAItems.SILENT_WOOD_STICK.get())
                .pattern(" I#").pattern("I #").pattern(" I#")
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.TEA_CUP.get()).define('#', TAItems.CERULEAN_NUGGET.get())
                .define('I', TAItems.CERULEAN_INGOT.get()).pattern("# #").pattern(" I ")
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.SILENT_WOOD_STICK.get())
                .define('#', TABlocks.SILENT_TREE_PLANKS.get()).pattern("#").pattern("#")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_PLANKS.get()), has(TABlocks.SILENT_TREE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TAItems.LAVENDER_BREAD.get()).define('#', TAItems.LAVENDER.get())
                .pattern("###").unlockedBy(getHasName(TAItems.LAVENDER.get()), has(TAItems.LAVENDER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.LOCK_PICKS.get())
                .define('I', TAItems.CERULEAN_INGOT.get())
                .define('O', TAItems.SILENT_WOOD_STICK.get())
                .pattern("I I").pattern("OO ").pattern("OOI")
                .unlockedBy(getHasName(TAItems.SILENT_WOOD_STICK.get()), has(TAItems.SILENT_WOOD_STICK.get()))
                .unlockedBy(getHasName(TAItems.CERULEAN_INGOT.get()), has(TAItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TAItems.DUNGEON_LOCATOR.get())
                .define('I', TAItems.CERULEAN_NUGGET.get())
                .define('O', TAItems.MOONSTONE_NUGGET.get())
                .define('X', TABlocks.AURORIAN_GLASS.get())
                .pattern("IOI").pattern("OXO").pattern("IOI")
                .unlockedBy(getHasName(TABlocks.AURORIAN_GLASS.get()), has(TABlocks.AURORIAN_GLASS.get())).save(consumer);
        //Vanilla Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING).define('#', TAItems.PLANT_FIBER.get())
                .pattern("###").pattern("###").pattern("###")
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get()))
                .save(consumer, AurorianMod.prefix("string_from_plant_fiber"));
        //Mod Shapeless Recipes
        oneToOneConversionRecipe(consumer, TAItems.LAVENDER_SEEDS.get(), TAItems.LAVENDER.get(), null);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_GLASS.get())
                .requires(TABlocks.MOON_GLASS.get(), (3)).requires(TAItems.PLANT_FIBER.get())
                .unlockedBy(getHasName(TABlocks.MOON_GLASS.get()), has(TABlocks.MOON_GLASS.get()))
                .unlockedBy(getHasName(TAItems.PLANT_FIBER.get()), has(TAItems.PLANT_FIBER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_BERRY_JAM.get()).requires(TAItems.SILK_BERRY.get(), (4))
                .unlockedBy(getHasName(TAItems.SILK_BERRY.get()), has(TAItems.SILK_BERRY.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_BERRY_JAM_SANDWICH.get())
                .requires(TAItems.SILK_BERRY_JAM.get()).requires(TAItems.LAVENDER_BREAD.get())
                .unlockedBy(getHasName(TAItems.SILK_BERRY_JAM.get()), has(TAItems.SILK_BERRY_JAM.get()))
                .unlockedBy(getHasName(TAItems.LAVENDER_BREAD.get()), has(TAItems.LAVENDER_BREAD.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.AURORIAN_BACON.get()).requires(TAItems.COOKED_AURORIAN_PORK.get(), (4))
                .unlockedBy(getHasName(TAItems.AURORIAN_BACON.get()), has(TAItems.AURORIAN_BACON.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TAItems.SILK_SHROOM_STEW.get())
                .requires(TAItems.SILK_BERRY.get(), (2)).requires(TABlocks.INDIGO_MUSHROOM.get(), (2))
                .unlockedBy(getHasName(TAItems.SILK_BERRY.get()), has(TAItems.SILK_BERRY.get()))
                .unlockedBy(getHasName(TABlocks.INDIGO_MUSHROOM.get()), has(TABlocks.INDIGO_MUSHROOM.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, TAItems.STICKY_SPIKER.get())
                .requires(TAItems.AURORIAN_SLIMEBALL.get()).requires(TAItems.CRYSTAL.get())
                .unlockedBy(getHasName(TAItems.AURORIAN_SLIMEBALL.get()), has(TAItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(getHasName(TAItems.CRYSTAL.get()), has(TAItems.CRYSTAL.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.DARK_STONE_KEY.get())
                .requires(TAItems.DUNGEON_KEEPER_AMULET.get()).requires(TAItems.CRYSTAL.get()).requires(TAItems.CERULEAN_NUGGET.get())
                .unlockedBy(getHasName(TAItems.DUNGEON_KEEPER_AMULET.get()), has(TAItems.DUNGEON_KEEPER_AMULET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.MOON_TEMPLE_KEY.get())
                .requires(TAItems.DARK_AMULET.get()).requires(TAItems.CRYSTAL.get()).requires(TABlocks.MOON_GEM.get())
                .unlockedBy(getHasName(TAItems.DARK_AMULET.get()), has(TAItems.DARK_AMULET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TAItems.MOON_TEMPLE_CELL_KEY.get())
                .requires(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(), (2)).requires(TABlocks.MOON_GEM.get())
                .unlockedBy(getHasName(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get()), has(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get())).save(consumer);
        //Vanilla Shapeless Recipes
        oneToOneConversionRecipe(consumer, Items.ROTTEN_FLESH, TAItems.SOULLESS_FLESH.get(), null);
        oneToOneConversionRecipe(consumer, Items.PINK_DYE, TABlocks.PETUNIA_PLANT.get(), null);
        //Mod Simple Cooking Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.AURORIAN_COBBLESTONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STONE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.AURORIAN_COBBLESTONE.get()), has(TABlocks.AURORIAN_COBBLESTONE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.MOON_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.MOON_GLASS.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.MOON_SAND.get()), has(TABlocks.MOON_SAND.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.AURORIAN_PERIDOTITE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.AURORIAN_PERIDOTITE.get()), has(TABlocks.AURORIAN_PERIDOTITE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.RUNE_STONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_RUNE_STONE.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.RUNE_STONE.get()), has(TABlocks.RUNE_STONE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TABlocks.MOON_TEMPLE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS, TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), (0.1F), (200))
                .unlockedBy(getHasName(TABlocks.MOON_TEMPLE_BRICKS.get()), has(TABlocks.MOON_TEMPLE_BRICKS.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TAItems.AURORIAN_PORK.get()),
                        RecipeCategory.FOOD, TAItems.COOKED_AURORIAN_PORK.get(), (0.3F), (200))
                .unlockedBy(getHasName(TAItems.AURORIAN_PORK.get()), has(TAItems.AURORIAN_PORK.get())).save(consumer);
        //Moonlight Forge Recipes
        forging(consumer, TAItems.MOONSTONE_SWORD.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_SWORD.get());
        forging(consumer, TAItems.MOONSTONE_PICKAXE.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_PICKAXE.get());
        forging(consumer, TAItems.MOONSTONE_SHIELD.get(), TAItems.UMBRA_INGOT.get(), TAItems.UMBRA_SHIELD.get());
        forging(consumer, TAItems.MOONSTONE_SWORD.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_SWORD.get());
        forging(consumer, TAItems.MOONSTONE_AXE.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_AXE.get());
        forging(consumer, TAItems.MOONSTONE_PICKAXE.get(), TAItems.AURORIANITE_INGOT.get(), TAItems.AURORIANITE_PICKAXE.get());
        forging(consumer, TAItems.MOONSTONE_SWORD.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_SWORD.get());
        forging(consumer, TAItems.MOONSTONE_PICKAXE.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_PICKAXE.get());
        forging(consumer, TAItems.MOONSTONE_SHIELD.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.CRYSTALLINE_SHIELD.get());
        forging(consumer, TAItems.AURORIAN_STEEL_PICKAXE.get(), TAItems.TROPHY_MOON_QUEEN.get(), TAItems.QUEENS_CHIPPER.get());
        forging(consumer, TAItems.MOONSTONE_SHIELD.get(), TAItems.TROPHY_MOON_QUEEN.get(), TAItems.MOON_SHIELD.get());
        forging(consumer, TAItems.SILENT_WOOD_BOW.get(), TAItems.TROPHY_KEEPER.get(), TAItems.KEEPERS_BOW.get());
        //Scrapper Recipes For Mod
        scrapping(consumer, TAItems.UMBRA_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(consumer, TAItems.UMBRA_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(consumer, TAItems.UMBRA_SHIELD.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(consumer, TAItems.SPIKED_CHESTPLATE.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(consumer, TAItems.AURORIANITE_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(consumer, TAItems.AURORIANITE_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(consumer, TAItems.AURORIANITE_AXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(consumer, TAItems.CRYSTALLINE_SWORD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(consumer, TAItems.CRYSTALLINE_PICKAXE.get(), TAItems.UMBRA_SCRAP.get(), 6);
        scrapping(consumer, TAItems.CRYSTALLINE_SHIELD.get(), TAItems.UMBRA_SCRAP.get(), 9);
        scrapping(consumer, TAItems.ABSORPTION_ORB.get(), TAItems.UMBRA_SCRAP.get(), 8);
        scrapping(consumer, TAItems.LIVING_DIVINING_ROD.get(), TAItems.UMBRA_SCRAP.get(), 4);
        scrapping(consumer, TAItems.AURORIAN_STEEL_SWORD.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 12);
        scrapping(consumer, TAItems.AURORIAN_STEEL_PICKAXE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 18);
        scrapping(consumer, TAItems.AURORIAN_STEEL_AXE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 18);
        scrapping(consumer, TAItems.AURORIAN_STEEL_SHOVEL.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 6);
        scrapping(consumer, TAItems.AURORIAN_STEEL_HOE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 12);
        scrapping(consumer, TAItems.AURORIAN_STEEL_HELMET.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 30);
        scrapping(consumer, TAItems.AURORIAN_STEEL_CHESTPLATE.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 48);
        scrapping(consumer, TAItems.AURORIAN_STEEL_LEGGINGS.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 42);
        scrapping(consumer, TAItems.AURORIAN_STEEL_BOOTS.get(), TAItems.AURORIAN_STEEL_NUGGET.get(), 24);
        scrapping(consumer, TAItems.MOONSTONE_SWORD.get(), TAItems.MOONSTONE_NUGGET.get(), 12);
        scrapping(consumer, TAItems.MOONSTONE_PICKAXE.get(), TAItems.MOONSTONE_NUGGET.get(), 18);
        scrapping(consumer, TAItems.MOONSTONE_AXE.get(), TAItems.MOONSTONE_NUGGET.get(), 18);
        scrapping(consumer, TAItems.MOONSTONE_SHOVEL.get(), TAItems.MOONSTONE_NUGGET.get(), 6);
        scrapping(consumer, TAItems.MOONSTONE_HOE.get(), TAItems.MOONSTONE_NUGGET.get(), 12);
        scrapping(consumer, TAItems.MOONSTONE_SHIELD.get(), TAItems.MOONSTONE_NUGGET.get(), 36);
        scrapping(consumer, TAItems.CERULEAN_HELMET.get(), TAItems.CERULEAN_NUGGET.get(), 30);
        scrapping(consumer, TAItems.CERULEAN_CHESTPLATE.get(), TAItems.CERULEAN_NUGGET.get(), 48);
        scrapping(consumer, TAItems.CERULEAN_LEGGINGS.get(), TAItems.CERULEAN_NUGGET.get(), 42);
        scrapping(consumer, TAItems.CERULEAN_BOOTS.get(), TAItems.CERULEAN_NUGGET.get(), 24);
        scrapping(consumer, TAItems.CERULEAN_SHIELD.get(), TAItems.CERULEAN_NUGGET.get(), 36);
        //Scrapper Recipes For Vanilla
        scrapping(consumer, Items.IRON_SWORD, Items.IRON_NUGGET, 12);
        scrapping(consumer, Items.IRON_PICKAXE, Items.IRON_NUGGET, 18);
        scrapping(consumer, Items.IRON_AXE, Items.IRON_NUGGET, 18);
        scrapping(consumer, Items.IRON_SHOVEL, Items.IRON_NUGGET, 6);
        scrapping(consumer, Items.IRON_HOE, Items.IRON_NUGGET, 12);
        scrapping(consumer, Items.IRON_HELMET, Items.IRON_NUGGET, 30);
        scrapping(consumer, Items.IRON_CHESTPLATE, Items.IRON_NUGGET, 48);
        scrapping(consumer, Items.IRON_LEGGINGS, Items.IRON_NUGGET, 42);
        scrapping(consumer, Items.IRON_BOOTS, Items.IRON_NUGGET, 24);
        scrapping(consumer, Items.GOLDEN_SWORD, Items.GOLD_NUGGET, 12);
        scrapping(consumer, Items.GOLDEN_PICKAXE, Items.GOLD_NUGGET, 18);
        scrapping(consumer, Items.GOLDEN_AXE, Items.GOLD_NUGGET, 18);
        scrapping(consumer, Items.GOLDEN_SHOVEL, Items.GOLD_NUGGET, 6);
        scrapping(consumer, Items.GOLDEN_HOE, Items.GOLD_NUGGET, 12);
        scrapping(consumer, Items.GOLDEN_HELMET, Items.GOLD_NUGGET, 30);
        scrapping(consumer, Items.GOLDEN_CHESTPLATE, Items.GOLD_NUGGET, 48);
        scrapping(consumer, Items.GOLDEN_LEGGINGS, Items.GOLD_NUGGET, 42);
        scrapping(consumer, Items.GOLDEN_BOOTS, Items.GOLD_NUGGET, 24);
        scrapping(consumer, Items.DIAMOND_SWORD, Items.DIAMOND, 1);
        scrapping(consumer, Items.DIAMOND_PICKAXE, Items.DIAMOND, 2);
        scrapping(consumer, Items.DIAMOND_AXE, Items.DIAMOND, 2);
        scrapping(consumer, Items.DIAMOND_SHOVEL, Items.DIAMOND, 1);
        scrapping(consumer, Items.DIAMOND_HOE, Items.DIAMOND, 1);
        scrapping(consumer, Items.DIAMOND_HELMET, Items.DIAMOND, 3);
        scrapping(consumer, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 5);
        scrapping(consumer, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 4);
        scrapping(consumer, Items.DIAMOND_BOOTS, Items.DIAMOND, 2);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STONE_BRICKS.get(), TABlocks.AURORIAN_STONE.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.CERULEAN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.MOONSTONE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_COAL_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TAItems.AURORIAN_STEEL.get(), RecipeCategory.BUILDING_BLOCKS, TABlocks.AURORIAN_STEEL_BLOCK.get());
        oreSmelting(consumer, List.of(TABlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreSmelting(consumer, List.of(TABlocks.MOONSTONE_ORE.get()), RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), (0.1F), (200), "MOONSTONE_");
        oreSmelting(consumer, List.of(TABlocks.CERULEAN_ORE.get()), RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreSmelting(consumer, List.of(TABlocks.GEODE_ORE.get()), RecipeCategory.MISC, TAItems.CRYSTAL.get(), (0.1F), (200), "geode");
        oreBlasting(consumer, List.of(TABlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, TAItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreBlasting(consumer, List.of(TABlocks.MOONSTONE_ORE.get()), RecipeCategory.MISC, TAItems.MOONSTONE_INGOT.get(), (0.1F), (200), "MOONSTONE_");
        oreBlasting(consumer, List.of(TABlocks.CERULEAN_ORE.get()), RecipeCategory.MISC, TAItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreBlasting(consumer, List.of(TABlocks.GEODE_ORE.get()), RecipeCategory.MISC, TAItems.CRYSTAL.get(), (0.1F), (200), "geode");
        planksFromLogs(consumer, TABlocks.SILENT_TREE_PLANKS.get(), TAItemTags.SILENT_TREE_LOGS, 4);
        planksFromLogs(consumer, TABlocks.WEEPING_WILLOW_PLANKS.get(), TAItemTags.WEEPING_WILLOW_LOGS, 4);
        woodFromLogs(consumer, TABlocks.SILENT_TREE_WOOD.get(), TABlocks.SILENT_TREE_LOG.get());
        woodFromLogs(consumer, TABlocks.WEEPING_WILLOW_WOOD.get(), TABlocks.WEEPING_WILLOW_LOG.get());
        this.buildArmorRecipes(consumer, TAItems.AURORIAN_STEEL_HELMET.get(), TAItems.AURORIAN_STEEL_CHESTPLATE.get(),
                TAItems.AURORIAN_STEEL_LEGGINGS.get(), TAItems.AURORIAN_STEEL_BOOTS.get(), TAItems.AURORIAN_STEEL.get());
        this.buildArmorRecipes(consumer, TAItems.CERULEAN_HELMET.get(), TAItems.CERULEAN_CHESTPLATE.get(),
                TAItems.CERULEAN_LEGGINGS.get(), TAItems.CERULEAN_BOOTS.get(), TAItems.CERULEAN_INGOT.get());
        this.buildArmorRecipes(consumer, TAItems.SPECTRAL_HELMET.get(), TAItems.SPECTRAL_CHESTPLATE.get(),
                TAItems.SPECTRAL_LEGGINGS.get(), TAItems.SPECTRAL_BOOTS.get(), TAItems.SPECTRAL_SILK.get());
        this.buildBaseToolRecipes(consumer, TAItems.AURORIAN_STEEL_SWORD.get(), TAItems.AURORIAN_STEEL_PICKAXE.get(),
                TAItems.AURORIAN_STEEL_SHOVEL.get(), TAItems.AURORIAN_STEEL_AXE.get(),
                TAItems.AURORIAN_STEEL_HOE.get(), TAItems.AURORIAN_STEEL.get());
        this.buildBaseToolRecipes(consumer, TAItems.AURORIAN_STONE_SWORD.get(), TAItems.AURORIAN_STONE_PICKAXE.get(),
                TAItems.AURORIAN_STONE_SHOVEL.get(), TAItems.AURORIAN_STONE_AXE.get(),
                TAItems.AURORIAN_STONE_HOE.get(), TABlocks.AURORIAN_COBBLESTONE.get());
        this.buildBaseToolRecipes(consumer, TAItems.MOONSTONE_SWORD.get(), TAItems.MOONSTONE_PICKAXE.get(),
                TAItems.MOONSTONE_SHOVEL.get(), TAItems.MOONSTONE_AXE.get(),
                TAItems.MOONSTONE_HOE.get(), TAItems.MOONSTONE_INGOT.get());
        this.buildBaseToolRecipes(consumer, TAItems.SILENT_WOOD_SWORD.get(), TAItems.SILENT_WOOD_PICKAXE.get(),
                TAItems.SILENT_WOOD_SHOVEL.get(), TAItems.SILENT_WOOD_AXE.get(),
                TAItems.SILENT_WOOD_HOE.get(), TABlocks.SILENT_TREE_PLANKS.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.UMBRA_SCRAP.get(), TAItems.UMBRA_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.AURORIANITE_SCRAP.get(), TAItems.AURORIANITE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.CRYSTALLINE_SCRAP.get(), TAItems.CRYSTALLINE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.CERULEAN_NUGGET.get(), TAItems.CERULEAN_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.MOONSTONE_NUGGET.get(), TAItems.MOONSTONE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.AURORIAN_COAL_NUGGET.get(), TAItems.AURORIAN_COAL.get());
        this.buildNuggetToIngotRecipes(consumer, TAItems.AURORIAN_STEEL_NUGGET.get(), TAItems.AURORIAN_STEEL.get());
        this.buildTeaRecipes(consumer, TAItems.LAVENDER_TEA.get(), TAItems.LAVENDER.get());
        this.buildTeaRecipes(consumer, TAItems.SILKBERRY_TEA.get(), TAItems.SILK_BERRY.get());
        this.buildTeaRecipes(consumer, TAItems.LAVENDER_SEEDY_TEA.get(), TAItems.LAVENDER_SEEDS.get());
        this.buildTeaRecipes(consumer, TAItems.PETUNIA_TEA.get(), TABlocks.PETUNIA_PLANT.get());
        this.buildArrowRecipes(consumer, TAItems.CERULEAN_ARROW.get(), TAItems.CERULEAN_NUGGET.get(), 1);
        this.buildArrowRecipes(consumer, TAItems.CRYSTAL_ARROW.get(), TAItems.CRYSTAL.get(), 16);
        this.buildSickleRecipes(consumer, TAItems.AURORIAN_STEEL_SICKLE.get(), TAItems.AURORIAN_STEEL.get());
        this.buildSickleRecipes(consumer, TAItems.AURORIAN_STONE_SICKLE.get(), TABlocks.AURORIAN_COBBLESTONE.get());
        this.buildSickleRecipes(consumer, TAItems.MOONSTONE_SICKLE.get(), TAItems.MOONSTONE_INGOT.get());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock stairBlock) {
                Block base = stairBlock.base;
                stairBuilder(stairBlock, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(consumer);
                if (stairBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, stairBlock, base);
                }
            } else if (block instanceof SlabBlockWithBase slabBlock) {
                slab(consumer, RecipeCategory.BUILDING_BLOCKS, slabBlock, slabBlock.getBase());
                if (slabBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, slabBlock, slabBlock.getBase(), 2);
                }
            } else if (block instanceof WallBlockWithBase wallBlock) {
                wall(consumer, RecipeCategory.BUILDING_BLOCKS, wallBlock, wallBlock.getBase());
                stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, wallBlock, wallBlock.getBase());
            }
        }
    }

    private static void forging(Consumer<FinishedRecipe> consumer, ItemLike equipment, ItemLike upgradeMaterial, ItemLike result) {
        MoonlightForgeRecipeBuilder.addRecipe(equipment, upgradeMaterial, result).unlockedBy(getHasName(upgradeMaterial), has(upgradeMaterial))
                .save(consumer, AurorianMod.prefix("forge_" + getItemName(equipment) + "_to_" + getItemName(result)));
    }
    
    private static void scrapping(Consumer<FinishedRecipe> consumer, ItemLike ingredient, ItemLike result, int amount) {
        ScrapperRecipeBuilder.addRecipe(ingredient, result, amount).unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, AurorianMod.prefix("scrap_" + getItemName(ingredient) + "_to_" + getItemName(result)));
    }

    private void buildArmorRecipes(Consumer<FinishedRecipe> consumer, ItemLike helmet,
            ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet).define('X', material)
                .pattern("XXX").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate).define('X', material)
                .pattern("X X").pattern("XXX").pattern("XXX")
                .unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings).define('X', material)
                .pattern("XXX").pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots).define('X', material)
                .pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(consumer);
    }

    private void buildBaseToolRecipes(Consumer<FinishedRecipe> consumer, ItemLike sword,
            ItemLike pickaxe, ItemLike shovel, ItemLike axe, ItemLike hoe, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("X").pattern("#").unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("#").pattern("#").unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern("X#").pattern(" #").unlockedBy(getHasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe).define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern(" #").pattern(" #").unlockedBy(getHasName(material), has(material)).save(consumer);
    }

    private void buildNuggetToIngotRecipes(Consumer<FinishedRecipe> consumer, ItemLike nugget, ItemLike ingot) {
        nineBlockStorageRecipesWithCustomPacking(consumer, RecipeCategory.MISC, nugget,
                RecipeCategory.MISC, ingot, getConversionRecipeName(ingot, nugget), getItemName(ingot));
    }

    private void buildTeaRecipes(Consumer<FinishedRecipe> consumer, ItemLike tea, ItemLike ingredient) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, tea).requires(TAItems.TEA_CUP.get()).requires(ingredient, (8))
                .unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void buildArrowRecipes(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike head, int count) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, count)
                .define('#', TAItems.SILENT_WOOD_STICK.get()).define('X', head)
                .define('Y', TABlocks.SILENT_TREE_LEAVES.get())
                .pattern("X").pattern("#").pattern("Y")
                .unlockedBy(getHasName(TABlocks.SILENT_TREE_LEAVES.get()),
                        has(TABlocks.SILENT_TREE_LEAVES.get()))
                .unlockedBy(getHasName(head), has(head)).save(consumer);
    }

    private void buildSickleRecipes(Consumer<FinishedRecipe> consumer, ItemLike sickle, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, sickle).define('#', material).define('I', TAItems.SILENT_WOOD_STICK.get())
                .pattern("##I").pattern("# I").pattern("  I").unlockedBy(getHasName(material), has(material)).save(consumer);
    }

}