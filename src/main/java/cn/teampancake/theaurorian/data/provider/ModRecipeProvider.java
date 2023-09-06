package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //Mod Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_COBBLESTONE.get(), 4)
                .define('#', ModBlocks.AURORIAN_COBBLESTONE.get()).pattern("##").pattern("##")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_COBBLESTONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get(), 4)
                .define('#', ModBlocks.AURORIAN_STONE.get()).pattern("##").pattern("##")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_STONE.get()), has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AURORIAN_FURNACE.get()).define('#', ModBlocks.AURORIAN_COBBLESTONE.get())
                .pattern("###").pattern("# #").pattern("###")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_COBBLESTONE.get()), has(ModBlocks.AURORIAN_COBBLESTONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.RUNE_STONE_BARS.get(), 16).define('#', ModBlocks.RUNE_STONE.get())
                .pattern("###").pattern("###").unlockedBy(this.hasName(ModBlocks.RUNE_STONE.get()), has(ModBlocks.RUNE_STONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.MOON_TEMPLE_BARS.get(), 16).define('#', ModBlocks.MOON_TEMPLE_BRICKS.get())
                .pattern("###").pattern("###").unlockedBy(this.hasName(ModBlocks.MOON_TEMPLE_BRICKS.get()), has(ModBlocks.MOON_TEMPLE_BRICKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AURORIAN_FURNACE_CHIMNEY.get())
                .define('#', ModBlocks.AURORIAN_COBBLESTONE.get()).define('I', ModItems.CERULEAN_INGOT.get())
                .pattern("#I#").pattern("#I#").pattern("#I#")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_COBBLESTONE.get()), has(ModBlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(this.hasName(ModItems.CERULEAN_INGOT.get()), has(ModItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.MOONLIGHT_FORGE.get())
                .define('#', ModBlocks.AURORIAN_COBBLESTONE.get()).define('I', ModBlocks.MOON_GEM.get())
                .define('O', ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get()).define('U', ModBlocks.AURORIAN_FURNACE.get())
                .pattern(" I ").pattern("#O#").pattern("#U#")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_COBBLESTONE.get()), has(ModBlocks.AURORIAN_COBBLESTONE.get()))
                .unlockedBy(this.hasName(ModBlocks.MOON_GEM.get()), has(ModBlocks.MOON_GEM.get()))
                .unlockedBy(this.hasName(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get()), has(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get()))
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_FURNACE.get()), has(ModBlocks.AURORIAN_FURNACE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.MOON_TORCH.get(), 4)
                .define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(ModBlocks.MOON_GEM.get()))
                .pattern("X").pattern("#").unlockedBy(this.hasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SILENT_WOOD_TORCH.get(), 4)
                .define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', Ingredient.of(ModItems.AURORIAN_COAL.get()))
                .pattern("X").pattern("#").unlockedBy(this.hasName(Items.STONE_PICKAXE), has(Items.STONE_PICKAXE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AURORIAN_GLASS_PANE.get(), 16).define('#', ModBlocks.AURORIAN_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(this.hasName(ModBlocks.AURORIAN_GLASS.get()), has(ModBlocks.MOON_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.MOON_GLASS_PANE.get(), 16).define('#', ModBlocks.MOON_GLASS.get())
                .pattern("###").pattern("###").unlockedBy(this.hasName(ModBlocks.MOON_GLASS.get()), has(ModBlocks.MOON_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get())
                .define('#', ModBlocks.SILENT_TREE_PLANKS.get()).define('X', Blocks.CRAFTING_TABLE)
                .pattern(" # ").pattern("#X#").pattern(" # ")
                .unlockedBy(this.hasName(ModBlocks.SILENT_TREE_PLANKS.get()), has(ModBlocks.SILENT_TREE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SILENT_WOOD_LADDER.get(), 3)
                .define('#', ModItems.SILENT_WOOD_STICK.get())
                .pattern("# #").pattern("###").pattern("# #")
                .unlockedBy(this.hasName(ModItems.SILENT_WOOD_STICK.get()), has(ModItems.SILENT_WOOD_STICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.AURORIAN_SLIME_BOOTS.get())
                .define('I', ModItems.AURORIAN_SLIMEBALL.get())
                .define('O', ModBlocks.MOON_GEM.get()).pattern("I I").pattern("O O")
                .unlockedBy(this.hasName(ModItems.AURORIAN_SLIMEBALL.get()), has(ModItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(this.hasName(ModBlocks.MOON_GEM.get()), has(ModBlocks.MOON_GEM.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MOONSTONE_SHIELD.get())
                .define('W', ModItems.MOONSTONE_INGOT.get()).define('o', ModItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(this.hasName(ModItems.MOONSTONE_INGOT.get()), has(ModItems.MOONSTONE_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.CERULEAN_SHIELD.get())
                .define('W', ModItems.CERULEAN_INGOT.get()).define('o', ModItems.PLANT_FIBER.get())
                .pattern("WoW").pattern("WWW").pattern(" W ")
                .unlockedBy(this.hasName(ModItems.CERULEAN_INGOT.get()), has(ModItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SILENT_WOOD_BOW.get())
                .define('#', ModItems.PLANT_FIBER.get()).define('I', ModItems.SILENT_WOOD_STICK.get())
                .pattern(" I#").pattern("I #").pattern(" I#")
                .unlockedBy(this.hasName(ModItems.PLANT_FIBER.get()), has(ModItems.PLANT_FIBER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TEA_CUP.get()).define('#', ModItems.CERULEAN_NUGGET.get())
                .define('I', ModItems.CERULEAN_INGOT.get()).pattern("# #").pattern(" I ")
                .unlockedBy(this.hasName(ModItems.CERULEAN_INGOT.get()), has(ModItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILENT_WOOD_STICK.get())
                .define('#', ModBlocks.SILENT_TREE_PLANKS.get()).pattern("#").pattern("#")
                .unlockedBy(this.hasName(ModBlocks.SILENT_TREE_PLANKS.get()), has(ModBlocks.SILENT_TREE_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.LAVENDER_BREAD.get()).define('#', ModItems.LAVENDER.get())
                .pattern("###").unlockedBy(this.hasName(ModItems.LAVENDER.get()), has(ModItems.LAVENDER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LOCK_PICKS.get())
                .define('I', ModItems.CERULEAN_INGOT.get())
                .define('O', ModItems.SILENT_WOOD_STICK.get())
                .pattern("I I").pattern("OO ").pattern("OOI")
                .unlockedBy(this.hasName(ModItems.SILENT_WOOD_STICK.get()), has(ModItems.SILENT_WOOD_STICK.get()))
                .unlockedBy(this.hasName(ModItems.CERULEAN_INGOT.get()), has(ModItems.CERULEAN_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DUNGEON_LOCATOR.get())
                .define('I', ModItems.CERULEAN_NUGGET.get())
                .define('O', ModItems.MOONSTONE_NUGGET.get())
                .define('X', ModBlocks.AURORIAN_GLASS.get())
                .pattern("IOI").pattern("OXO").pattern("IOI")
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_GLASS.get()), has(ModBlocks.AURORIAN_GLASS.get())).save(consumer);
        //Vanilla Shaped Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING).define('#', ModItems.PLANT_FIBER.get())
                .pattern("###").pattern("###").pattern("###")
                .unlockedBy(this.hasName(ModItems.PLANT_FIBER.get()), has(ModItems.PLANT_FIBER.get()))
                .save(consumer, AurorianMod.prefix("string_from_plant_fiber"));
        //Mod Shapeless Recipes
        oneToOneConversionRecipe(consumer, ModItems.LAVENDER_SEEDS.get(), ModItems.LAVENDER.get(), null);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_GLASS.get())
                .requires(ModBlocks.MOON_GLASS.get(), (3)).requires(ModItems.PLANT_FIBER.get())
                .unlockedBy(this.hasName(ModBlocks.MOON_GLASS.get()), has(ModBlocks.MOON_GLASS.get()))
                .unlockedBy(this.hasName(ModItems.PLANT_FIBER.get()), has(ModItems.PLANT_FIBER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SILK_BERRY_JAM.get()).requires(ModItems.SILK_BERRY.get(), (4))
                .unlockedBy(this.hasName(ModItems.SILK_BERRY.get()), has(ModItems.SILK_BERRY.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SILK_BERRY_JAM_SANDWICH.get())
                .requires(ModItems.SILK_BERRY_JAM.get()).requires(ModItems.LAVENDER_BREAD.get())
                .unlockedBy(this.hasName(ModItems.SILK_BERRY_JAM.get()), has(ModItems.SILK_BERRY_JAM.get()))
                .unlockedBy(this.hasName(ModItems.LAVENDER_BREAD.get()), has(ModItems.LAVENDER_BREAD.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.AURORIAN_BACON.get()).requires(ModItems.COOKED_AURORIAN_PORK.get(), (4))
                .unlockedBy(this.hasName(ModItems.AURORIAN_BACON.get()), has(ModItems.AURORIAN_BACON.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SILK_SHROOM_STEW.get())
                .requires(ModItems.SILK_BERRY.get(), (2)).requires(ModBlocks.INDIGO_MUSHROOM.get(), (2))
                .unlockedBy(this.hasName(ModItems.SILK_BERRY.get()), has(ModItems.SILK_BERRY.get()))
                .unlockedBy(this.hasName(ModBlocks.INDIGO_MUSHROOM.get()), has(ModBlocks.INDIGO_MUSHROOM.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.STICKY_SPIKER.get())
                .requires(ModItems.AURORIAN_SLIMEBALL.get()).requires(ModItems.CRYSTAL.get())
                .unlockedBy(this.hasName(ModItems.AURORIAN_SLIMEBALL.get()), has(ModItems.AURORIAN_SLIMEBALL.get()))
                .unlockedBy(this.hasName(ModItems.CRYSTAL.get()), has(ModItems.CRYSTAL.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DARK_STONE_KEY.get())
                .requires(ModItems.DUNGEON_KEEPER_AMULET.get()).requires(ModItems.CRYSTAL.get()).requires(ModItems.CERULEAN_NUGGET.get())
                .unlockedBy(this.hasName(ModItems.DUNGEON_KEEPER_AMULET.get()), has(ModItems.DUNGEON_KEEPER_AMULET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOON_TEMPLE_KEY.get())
                .requires(ModItems.DARK_AMULET.get()).requires(ModItems.CRYSTAL.get()).requires(ModBlocks.MOON_GEM.get())
                .unlockedBy(this.hasName(ModItems.DARK_AMULET.get()), has(ModItems.DARK_AMULET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOON_TEMPLE_CELL_KEY.get())
                .requires(ModItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(), (2)).requires(ModBlocks.MOON_GEM.get())
                .unlockedBy(this.hasName(ModItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get()), has(ModItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get())).save(consumer);
        //Vanilla Shapeless Recipes
        oneToOneConversionRecipe(consumer, Items.ROTTEN_FLESH, ModItems.SOULLESS_FLESH.get(), null);
        oneToOneConversionRecipe(consumer, Items.PINK_DYE, ModBlocks.PETUNIA_PLANT.get(), null);
        //Mod Simple Cooking Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.AURORIAN_COBBLESTONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_STONE.get(), (0.1F), (200))
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_COBBLESTONE.get()), has(ModBlocks.AURORIAN_COBBLESTONE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.MOON_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOON_GLASS.get(), (0.1F), (200))
                .unlockedBy(this.hasName(ModBlocks.MOON_SAND.get()), has(ModBlocks.MOON_SAND.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.AURORIAN_PERIDOTITE.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get(), (0.1F), (200))
                .unlockedBy(this.hasName(ModBlocks.AURORIAN_PERIDOTITE.get()), has(ModBlocks.AURORIAN_PERIDOTITE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.RUNE_STONE.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_RUNE_STONE.get(), (0.1F), (200))
                .unlockedBy(this.hasName(ModBlocks.RUNE_STONE.get()), has(ModBlocks.RUNE_STONE.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.MOON_TEMPLE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get(), (0.1F), (200))
                .unlockedBy(this.hasName(ModBlocks.MOON_TEMPLE_BRICKS.get()), has(ModBlocks.MOON_TEMPLE_BRICKS.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.AURORIAN_PORK.get()),
                        RecipeCategory.FOOD, ModItems.COOKED_AURORIAN_PORK.get(), (0.3F), (200))
                .unlockedBy(this.hasName(ModItems.AURORIAN_PORK.get()), has(ModItems.AURORIAN_PORK.get())).save(consumer);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_STONE_BRICKS.get(), ModBlocks.AURORIAN_STONE.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.CERULEAN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CERULEAN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.MOONSTONE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOONSTONE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.AURORIAN_COAL.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_COAL_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.AURORIAN_STEEL.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURORIAN_STEEL_BLOCK.get());
        oreSmelting(consumer, List.of(ModBlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, ModItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreSmelting(consumer, List.of(ModBlocks.MOONSTONE_ORE.get()), RecipeCategory.MISC, ModItems.MOONSTONE_INGOT.get(), (0.1F), (200), "moonstone");
        oreSmelting(consumer, List.of(ModBlocks.CERULEAN_ORE.get()), RecipeCategory.MISC, ModItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreSmelting(consumer, List.of(ModBlocks.GEODE_ORE.get()), RecipeCategory.MISC, ModItems.CRYSTAL.get(), (0.1F), (200), "geode");
        oreBlasting(consumer, List.of(ModBlocks.AURORIAN_COAL_ORE.get()), RecipeCategory.MISC, ModItems.AURORIAN_COAL.get(), (0.1F), (200), "aurorian_coal");
        oreBlasting(consumer, List.of(ModBlocks.MOONSTONE_ORE.get()), RecipeCategory.MISC, ModItems.MOONSTONE_INGOT.get(), (0.1F), (200), "moonstone");
        oreBlasting(consumer, List.of(ModBlocks.CERULEAN_ORE.get()), RecipeCategory.MISC, ModItems.CERULEAN_INGOT.get(), (0.1F), (200), "cerulean");
        oreBlasting(consumer, List.of(ModBlocks.GEODE_ORE.get()), RecipeCategory.MISC, ModItems.CRYSTAL.get(), (0.1F), (200), "geode");
        planksFromLogs(consumer, ModBlocks.SILENT_TREE_PLANKS.get(), ModItemTags.SILENT_TREE_LOGS, 4);
        planksFromLogs(consumer, ModBlocks.WEEPING_WILLOW_PLANKS.get(), ModItemTags.WEEPING_WILLOW_LOGS, 4);
        woodFromLogs(consumer, ModBlocks.SILENT_TREE_WOOD.get(), ModBlocks.SILENT_TREE_LOG.get());
        woodFromLogs(consumer, ModBlocks.WEEPING_WILLOW_WOOD.get(), ModBlocks.WEEPING_WILLOW_LOG.get());
        this.buildArmorRecipes(consumer, ModItems.AURORIAN_STEEL_HELMET.get(), ModItems.AURORIAN_STEEL_CHESTPLATE.get(),
                ModItems.AURORIAN_STEEL_LEGGINGS.get(), ModItems.AURORIAN_STEEL_BOOTS.get(), ModItems.AURORIAN_STEEL.get());
        this.buildArmorRecipes(consumer, ModItems.CERULEAN_HELMET.get(), ModItems.CERULEAN_CHESTPLATE.get(),
                ModItems.CERULEAN_LEGGINGS.get(), ModItems.CERULEAN_BOOTS.get(), ModItems.CERULEAN_INGOT.get());
        this.buildArmorRecipes(consumer, ModItems.SPECTRAL_HELMET.get(), ModItems.SPECTRAL_CHESTPLATE.get(),
                ModItems.SPECTRAL_LEGGINGS.get(), ModItems.SPECTRAL_BOOTS.get(), ModItems.SPECTRAL_SILK.get());
        this.buildBaseToolRecipes(consumer, ModItems.AURORIAN_STEEL_SWORD.get(), ModItems.AURORIAN_STEEL_PICKAXE.get(),
                ModItems.AURORIAN_STEEL_SHOVEL.get(), ModItems.AURORIAN_STEEL_AXE.get(),
                ModItems.AURORIAN_STEEL_HOE.get(), ModItems.AURORIAN_STEEL.get());
        this.buildBaseToolRecipes(consumer, ModItems.AURORIAN_STONE_SWORD.get(), ModItems.AURORIAN_STONE_PICKAXE.get(),
                ModItems.AURORIAN_STONE_SHOVEL.get(), ModItems.AURORIAN_STONE_AXE.get(),
                ModItems.AURORIAN_STONE_HOE.get(), ModBlocks.AURORIAN_COBBLESTONE.get());
        this.buildBaseToolRecipes(consumer, ModItems.MOONSTONE_SWORD.get(), ModItems.MOONSTONE_PICKAXE.get(),
                ModItems.MOONSTONE_SHOVEL.get(), ModItems.MOONSTONE_AXE.get(),
                ModItems.MOONSTONE_HOE.get(), ModItems.MOONSTONE_INGOT.get());
        this.buildBaseToolRecipes(consumer, ModItems.SILENT_WOOD_SWORD.get(), ModItems.SILENT_WOOD_PICKAXE.get(),
                ModItems.SILENT_WOOD_SHOVEL.get(), ModItems.SILENT_WOOD_AXE.get(),
                ModItems.SILENT_WOOD_HOE.get(), ModBlocks.SILENT_TREE_PLANKS.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.UMBRA_SCRAP.get(), ModItems.UMBRA_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.AURORIANITE_SCRAP.get(), ModItems.AURORIANITE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.CRYSTALLINE_SCRAP.get(), ModItems.CRYSTALLINE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.CERULEAN_NUGGET.get(), ModItems.CERULEAN_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.MOONSTONE_NUGGET.get(), ModItems.MOONSTONE_INGOT.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.AURORIAN_COAL_NUGGET.get(), ModItems.AURORIAN_COAL.get());
        this.buildNuggetToIngotRecipes(consumer, ModItems.AURORIAN_STEEL_NUGGET.get(),ModItems.AURORIAN_STEEL.get());
        this.buildTeaRecipes(consumer, ModItems.LAVENDER_TEA.get(), ModItems.LAVENDER.get());
        this.buildTeaRecipes(consumer, ModItems.SILKBERRY_TEA.get(), ModItems.SILK_BERRY.get());
        this.buildTeaRecipes(consumer, ModItems.SEEDY_TEA.get(), ModItems.LAVENDER_SEEDS.get());
        this.buildTeaRecipes(consumer, ModItems.PETUNIA_TEA.get(), ModBlocks.PETUNIA_PLANT.get());
        this.buildArrowRecipes(consumer, ModItems.CERULEAN_ARROW.get(), ModItems.CERULEAN_NUGGET.get(), 1);
        this.buildArrowRecipes(consumer, ModItems.CRYSTAL_ARROW.get(), ModItems.CRYSTAL.get(), 16);
        this.buildSickleRecipes(consumer, ModItems.AURORIAN_STEEL_SICKLE.get(), ModItems.AURORIAN_STEEL.get());
        this.buildSickleRecipes(consumer, ModItems.AURORIAN_STONE_SICKLE.get(), ModBlocks.AURORIAN_COBBLESTONE.get());
        this.buildSickleRecipes(consumer, ModItems.MOONSTONE_SICKLE.get(), ModItems.MOONSTONE_INGOT.get());
        for (Block block : ModCommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock stairBlock) {
                Block base = stairBlock.base;
                stairBuilder(stairBlock, Ingredient.of(base)).unlockedBy(this.hasName(base), has(base)).save(consumer);
                if (stairBlock.defaultMapColor() == Blocks.STONE.defaultMapColor()) {
                    stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, stairBlock, base);
                }
            }
        }
    }

    private void buildArmorRecipes(Consumer<FinishedRecipe> consumer, ItemLike helmet,
            ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet).define('X', material)
                .pattern("XXX").pattern("X X")
                .unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate).define('X', material)
                .pattern("X X").pattern("XXX").pattern("XXX")
                .unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings).define('X', material)
                .pattern("XXX").pattern("X X").pattern("X X")
                .unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots).define('X', material)
                .pattern("X X").pattern("X X")
                .unlockedBy(this.hasName(material), has(material)).save(consumer);
    }

    private void buildBaseToolRecipes(Consumer<FinishedRecipe> consumer, ItemLike sword, ItemLike pickaxe, ItemLike shovel, ItemLike axe, ItemLike hoe, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword).define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("X").pattern("#").unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe).define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel).define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("X").pattern("#").pattern("#").unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe).define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern("X#").pattern(" #").unlockedBy(this.hasName(material), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe).define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', material)
                .pattern("XX").pattern(" #").pattern(" #").unlockedBy(this.hasName(material), has(material)).save(consumer);
    }

    private void buildNuggetToIngotRecipes(Consumer<FinishedRecipe> consumer, ItemLike nugget, ItemLike ingot) {
        nineBlockStorageRecipesWithCustomPacking(consumer, RecipeCategory.MISC, nugget,
                RecipeCategory.MISC, ingot, getConversionRecipeName(ingot, nugget), this.name(ingot));
    }

    private void buildTeaRecipes(Consumer<FinishedRecipe> consumer, ItemLike tea, ItemLike ingredient) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, tea).requires(ModItems.TEA_CUP.get()).requires(ingredient, (8))
                .unlockedBy(this.hasName(ingredient), has(ingredient)).save(consumer);
    }

    private void buildArrowRecipes(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike head, int count) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, count)
                .define('#', ModItems.SILENT_WOOD_STICK.get()).define('X', head)
                .define('Y', ModBlocks.SILENT_TREE_LEAVES.get())
                .pattern("X").pattern("#").pattern("Y")
                .unlockedBy(this.hasName(ModBlocks.SILENT_TREE_LEAVES.get()),
                        has(ModBlocks.SILENT_TREE_LEAVES.get()))
                .unlockedBy(this.hasName(head), has(head)).save(consumer);
    }

    private void buildSickleRecipes(Consumer<FinishedRecipe> consumer, ItemLike sickle, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, sickle).define('#', material).define('I', ModItems.SILENT_WOOD_STICK.get())
                .pattern("##I").pattern("# I").pattern("  I").unlockedBy(this.hasName(material), has(material)).save(consumer);
    }

    private String name(ItemLike item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
    }

    private String hasName(ItemLike item) {
        return "has_" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
    }

}