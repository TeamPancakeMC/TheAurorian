package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.enchantments.MoltenCoreEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAEnchantmentEffectTypes {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, AurorianMod.MOD_ID);
    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<MoltenCoreEffect>> MOLTEN_CORE =
            ENCHANTMENT_ENTITY_EFFECT_TYPES.register("molten_core", () -> MoltenCoreEffect.CODEC);

}