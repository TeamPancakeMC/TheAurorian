package cn.teampancake.theaurorian.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ScrapperSerializer<T extends ScrapperRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public ScrapperSerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ScrapperRecipe::ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(ScrapperRecipe::result),
                Codec.INT.fieldOf("amount").forGetter(ScrapperRecipe::amount)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack resultStack = ItemStack.STREAM_CODEC.decode(buffer);
        int amount = buffer.readVarInt();
        return this.factory.create(ingredient, resultStack, amount);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
        buffer.writeVarInt(recipe.amount());
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface Factory<T extends ScrapperRecipe> {
        T create(Ingredient ingredient, ItemStack result, int amount);
    }

}