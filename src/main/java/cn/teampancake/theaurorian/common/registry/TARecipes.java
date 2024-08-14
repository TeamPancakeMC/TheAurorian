package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.crafting.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TARecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TheAurorian.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, TheAurorian.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MoonlightForgeRecipe>> MOONLIGHT_FORGE_SERIALIZER =
            RECIPE_SERIALIZERS.register("moonlight_forge", () -> new MoonlightForgeSerializer<>(MoonlightForgeRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlchemyTableRecipe>> ALCHEMY_TABLE_SERIALIZER =
            RECIPE_SERIALIZERS.register("alchemy_table", () -> new AlchemyTableSerializer<>(AlchemyTableRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ScrapperRecipe>> SCRAPPER_SERIALIZER =
            RECIPE_SERIALIZERS.register("scrapper", () -> new ScrapperSerializer<>(ScrapperRecipe::new));

    public static final DeferredHolder<RecipeType<?>, RecipeType<MoonlightForgeRecipe>> MOONLIGHT_FORGE_RECIPE =
            RECIPE_TYPES.register("moonlight_forge", () -> RecipeType.simple(TheAurorian.prefix("moonlight_forge")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlchemyTableRecipe>> ALCHEMY_TABLE_RECIPE =
            RECIPE_TYPES.register("alchemy_table", () -> RecipeType.simple(TheAurorian.prefix("alchemy_table")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ScrapperRecipe>> SCRAPPER_RECIPE =
            RECIPE_TYPES.register("scrapper", () -> RecipeType.simple(TheAurorian.prefix("scrapper")));

}