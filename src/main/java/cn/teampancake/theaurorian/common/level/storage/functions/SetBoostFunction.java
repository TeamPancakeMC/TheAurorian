package cn.teampancake.theaurorian.common.level.storage.functions;

import cn.teampancake.theaurorian.common.registry.TALootItemFunctions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SetBoostFunction extends LootItemConditionalFunction {

    public static final MapCodec<SetBoostFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> commonFields(instance).and(instance.group(
                    DataComponentType.CODEC.fieldOf("component").forGetter(function -> function.component),
                    NumberProviders.CODEC.fieldOf("boost").forGetter(function -> function.value),
                    Codec.BOOL.fieldOf("add").orElse(false).forGetter(function -> function.add))
            ).apply(instance, SetBoostFunction::new));
    private final DataComponentType<?> component;
    private final NumberProvider value;
    private final boolean add;

    public SetBoostFunction(List<LootItemCondition> predicates, DataComponentType<?> component, NumberProvider value, boolean add) {
        super(predicates);
        this.component = component;
        this.value = value;
        this.add = add;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return TALootItemFunctions.SET_BOOST.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return this.value.getReferencedContextParams();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        stack.set((DataComponentType<Float>) this.component, this.value.getFloat(context));
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> setBoost(DataComponentType<Float> component, NumberProvider boostValue) {
        return simpleBuilder(conditions -> new SetBoostFunction(conditions, component, boostValue, false));
    }

}