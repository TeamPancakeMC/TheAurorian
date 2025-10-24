package cn.teampancake.theaurorian.common.level.storage.functions;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TALootItemFunctions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public class SetFixedHealthBoostFunction extends LootItemConditionalFunction {

    public static final MapCodec<SetFixedHealthBoostFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> commonFields(instance).and(instance.group(
                    NumberProviders.CODEC.fieldOf("boost").forGetter(function -> function.value),
                    Codec.BOOL.fieldOf("add").orElse(false).forGetter(function -> function.add))
            ).apply(instance, SetFixedHealthBoostFunction::new));
    private final NumberProvider value;
    private final boolean add;

    public SetFixedHealthBoostFunction(List<LootItemCondition> predicates, NumberProvider value, boolean add) {
        super(predicates);
        this.value = value;
        this.add = add;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return TALootItemFunctions.SET_FIXED_HEALTH_BOOST.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return this.value.getReferencedContextParams();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        stack.set(TADataComponents.FIX_HEALTH_BOOST, this.value.getFloat(context));
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> setBoost(NumberProvider boostValue) {
        return simpleBuilder(conditions -> new SetFixedHealthBoostFunction(conditions, boostValue, false));
    }

}