package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.enchantments.MoltenCoreEffect;
import cn.teampancake.theaurorian.common.enchantments.SpringOfLifeEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAEnchantmentEffectTypes {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, TheAurorian.MOD_ID);

    static {
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("molten_core", () -> MoltenCoreEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("source_of_terra", () -> SpringOfLifeEffect.CODEC);
    }

}