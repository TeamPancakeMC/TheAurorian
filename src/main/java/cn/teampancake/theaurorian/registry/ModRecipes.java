package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AurorianMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, AurorianMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ScrapperRecipe>> SCRAPPER_SERIALIZER = RECIPE_SERIALIZERS.register("scrapper", () -> new ScrapperSerializer<>(ScrapperRecipe::new));

    public static final RegistryObject<RecipeType<ScrapperRecipe>> SCRAPPER_RECIPE = RECIPE_TYPES.register("scrapper", () -> RecipeType.simple(AurorianMod.prefix("scrapper")));

}