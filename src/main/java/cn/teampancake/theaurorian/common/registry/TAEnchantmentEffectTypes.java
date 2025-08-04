package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.enchantments.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAEnchantmentEffectTypes {

    public static final DeferredRegister<MapCodec<? extends EnchantmentValueEffect>> ENCHANTMENT_VALUE_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE, TheAurorian.MOD_ID);

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, TheAurorian.MOD_ID);

    static {
        // Value Effect
        ENCHANTMENT_VALUE_EFFECT_TYPES.register("experience_ore", () -> ExperienceOreEffect.CODEC);
        ENCHANTMENT_VALUE_EFFECT_TYPES.register("sunder_armor_slash", () -> SunderArmorSlashEffect.CODEC);
        // Entity Effect
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("savage", () -> SavageEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("molten_core", () -> MoltenCoreEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("freeze_aspect", () -> FreezeAspectEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("spring_of_life", () -> SpringOfLifeEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("source_of_terra", () -> SourceOfTerraEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("roundabout_throw", () -> RoundaboutThrowEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("arrow_rain_store_pos", () -> ArrowRainStorePosEffect.CODEC);
        ENCHANTMENT_ENTITY_EFFECT_TYPES.register("arrow_rain_summon_arrow", () -> ArrowRainSummonArrowEffect.CODEC);
    }

}