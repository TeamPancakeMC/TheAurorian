package cn.teampancake.theaurorian.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class AlchemyTableSerializer implements RecipeSerializer<AlchemyTableRecipe> {

    private final MapCodec<AlchemyTableRecipe> codec;

    public AlchemyTableSerializer() {
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf(1, 3).fieldOf("ingredients").flatXmap(list -> {
                    Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                    }, DataResult::success).forGetter(AlchemyTableRecipe::ingredients),
                Ingredient.CODEC_NONEMPTY.fieldOf("material").forGetter(AlchemyTableRecipe::material),
                ItemStack.CODEC.fieldOf("result").forGetter(AlchemyTableRecipe::result),
                Codec.INT.fieldOf("alchemy_time").forGetter(AlchemyTableRecipe::alchemyTime)
        ).apply(instance, AlchemyTableRecipe::new));
    }

    public AlchemyTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
        ingredients.replaceAll(ingredient -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        return new AlchemyTableRecipe(ingredients, material, result, buffer.readVarInt());
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, AlchemyTableRecipe recipe) {
        buffer.writeVarInt(recipe.ingredients().size());
        for (Ingredient ingredient : recipe.ingredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }

        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
        buffer.writeVarInt(recipe.alchemyTime());
    }

    @Override
    public MapCodec<AlchemyTableRecipe> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, AlchemyTableRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

}