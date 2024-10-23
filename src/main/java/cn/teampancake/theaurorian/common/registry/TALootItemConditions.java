package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.storage.predicate.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TALootItemConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES =
            DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> SAVAGE_ENCHANTMENT =
            register("savage_enchantment", SavageEnchantmentCondition.CODEC);
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> NIGHT_WALKER_ENCHANTMENT =
            register("night_walker_enchantment", NightWalkerEnchantmentCondition.CODEC);
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> MOLTEN_CORE_ENCHANTMENT =
            register("molten_core_enchantment", MoltenCoreEnchantmentCondition.CODEC);

    private static DeferredHolder<LootItemConditionType, LootItemConditionType> register(String name, MapCodec<? extends LootItemCondition> codec) {
        return LOOT_CONDITION_TYPES.register(name, () -> new LootItemConditionType(codec));
    }

}