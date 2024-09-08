package cn.teampancake.theaurorian.common.items.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MoonlightForgeSerializer<T extends MoonlightForgeRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public MoonlightForgeSerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("equipment").forGetter(MoonlightForgeRecipe::equipment),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(MoonlightForgeRecipe::upgradeMaterial),
                ItemStack.CODEC.fieldOf("result").forGetter(MoonlightForgeRecipe::result)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient equipment = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient upgradeMaterial = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        return this.factory.create(equipment, upgradeMaterial, result);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.equipment());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.upgradeMaterial());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface Factory<T extends MoonlightForgeRecipe> {
        T create(Ingredient equipment, Ingredient upgradeMaterial, ItemStack result);
    }

}