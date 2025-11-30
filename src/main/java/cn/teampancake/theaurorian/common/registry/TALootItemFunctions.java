package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.storage.functions.SetBoostFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TALootItemFunctions {

    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_ITEM_FUNCTION_TYPES = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<SetBoostFunction>> SET_BOOST =
            LOOT_ITEM_FUNCTION_TYPES.register("set_boost", () -> new LootItemFunctionType<>(SetBoostFunction.CODEC));

}