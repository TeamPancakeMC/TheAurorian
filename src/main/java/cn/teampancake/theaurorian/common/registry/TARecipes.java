package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.crafting.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TARecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AurorianMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, AurorianMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MoonlightForgeRecipe>> MOONLIGHT_FORGE_SERIALIZER =
            RECIPE_SERIALIZERS.register("moonlight_forge", MoonlightForgeSerializer::new);
    public static final RegistryObject<RecipeSerializer<AlchemyTableRecipe>> ALCHEMY_TABLE_SERIALIZER =
            RECIPE_SERIALIZERS.register("alchemy_table", () -> new AlchemyTableSerializer<>(AlchemyTableRecipe::new));
    public static final RegistryObject<RecipeSerializer<ScrapperRecipe>> SCRAPPER_SERIALIZER =
            RECIPE_SERIALIZERS.register("scrapper", () -> new ScrapperSerializer<>(ScrapperRecipe::new));

    public static final RegistryObject<RecipeType<MoonlightForgeRecipe>> MOONLIGHT_FORGE_RECIPE =
            RECIPE_TYPES.register("moonlight_forge", () -> RecipeType.simple(AurorianMod.prefix("moonlight_forge")));
    public static final RegistryObject<RecipeType<AlchemyTableRecipe>> ALCHEMY_TABLE_RECIPE =
            RECIPE_TYPES.register("alchemy_table", () -> RecipeType.simple(AurorianMod.prefix("alchemy_table")));
    public static final RegistryObject<RecipeType<ScrapperRecipe>> SCRAPPER_RECIPE =
            RECIPE_TYPES.register("scrapper", () -> RecipeType.simple(AurorianMod.prefix("scrapper")));

}