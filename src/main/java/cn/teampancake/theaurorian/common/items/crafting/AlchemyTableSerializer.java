package cn.teampancake.theaurorian.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class AlchemyTableSerializer<T extends AlchemyTableRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public AlchemyTableSerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input1").forGetter(AlchemyTableRecipe::input1),
                Ingredient.CODEC_NONEMPTY.fieldOf("input2").forGetter(AlchemyTableRecipe::input2),
                Ingredient.CODEC_NONEMPTY.fieldOf("input3").forGetter(AlchemyTableRecipe::input3),
                Ingredient.CODEC_NONEMPTY.fieldOf("material").forGetter(AlchemyTableRecipe::material),
                ItemStack.CODEC.fieldOf("result").forGetter(AlchemyTableRecipe::result),
                Codec.INT.fieldOf("alchemy_time").forGetter(AlchemyTableRecipe::alchemyTime)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient input3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        int alchemyTime = buffer.readVarInt();
        return this.factory.create(input1, input2, input3, material, result, alchemyTime);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input1());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input2());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input3());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
        buffer.writeVarInt(recipe.alchemyTime());
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface Factory<T extends AlchemyTableRecipe> {
        T create(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result, int alchemyTime);
    }

}