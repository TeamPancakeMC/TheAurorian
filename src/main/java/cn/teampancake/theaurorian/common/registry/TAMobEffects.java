package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.effect.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, TheAurorian.MOD_ID);
    public static final DeferredHolder<MobEffect, MobEffect> STUN = MOB_EFFECTS.register("stun", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x8b0000)
            // 降低80%移动速度，允许微弱移动
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("stun_ms"), -0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低90%攻击伤害，保留最小伤害能力
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, TheAurorian.prefix("stun_ad"), -0.9, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低90%跳跃力量，几乎无法跳跃
            .addAttributeModifier(Attributes.JUMP_STRENGTH, TheAurorian.prefix("stun_js"), -0.9, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低95%方块破坏速度，几乎无法破坏方块
            .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, TheAurorian.prefix("stun_bbs"), -0.95, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低70%方块交互范围，只能与近距离方块交互
            .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, TheAurorian.prefix("stun_bir"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低70%实体交互范围，只能与近距离实体交互
            .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, TheAurorian.prefix("stun_eir"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低95%挖掘效率，几乎无法挖掘
            .addAttributeModifier(Attributes.MINING_EFFICIENCY, TheAurorian.prefix("stun_me"), -0.95, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            // 降低70%攻击速度，大幅降低战斗能力
            .addAttributeModifier(Attributes.ATTACK_SPEED, TheAurorian.prefix("stun_as"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", () -> new MobEffect(MobEffectCategory.HARMFUL, 0xc09c72)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("paralysis_ms"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, TheAurorian.prefix("paralysis_js"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> OVERHEATING = MOB_EFFECTS.register("overheating", () -> new MobEffect(MobEffectCategory.HARMFUL, 0xdb5f39)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("overheating_ms"), -0.05D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, TheAurorian.prefix("overheating_as"), -0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> TOUGH = MOB_EFFECTS.register("tough", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xf49cae));
    public static final DeferredHolder<MobEffect, MobEffect> BROKEN = MOB_EFFECTS.register("broken", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x808080));
    public static final DeferredHolder<MobEffect, MobEffect> CRESCENT = MOB_EFFECTS.register("crescent", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xd1b9a1));
    public static final DeferredHolder<MobEffect, MobEffect> PRESSURE = MOB_EFFECTS.register("pressure", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x714bdb));
    public static final DeferredHolder<MobEffect, MobEffect> DEAFNESS = MOB_EFFECTS.register("deafness", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x886671));
    public static final DeferredHolder<MobEffect, MobEffect> MOON_BEFALL = MOB_EFFECTS.register("moon_befall", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xb76ce6));
    public static final DeferredHolder<MobEffect, MobEffect> BLESS_OF_MOON = MOB_EFFECTS.register("bless_of_moon", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xe3d5aa));
    public static final DeferredHolder<MobEffect, MobEffect> EIDOLON_POISON = MOB_EFFECTS.register("eidolon_poison", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x36d6be));
    public static final DeferredHolder<MobEffect, MobEffect> CRYSTALLIZATION = MOB_EFFECTS.register("crystallization", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x17d1c7));
    public static final DeferredHolder<MobEffect, MobEffect> SHADOWED_SIGHT = MOB_EFFECTS.register("shadowed_sight", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x2b2b2b));
    public static final DeferredHolder<MobEffect, MobEffect> MOON_OF_VENGEANCE = MOB_EFFECTS.register("moon_of_vengeance", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x960a17));
    public static final DeferredHolder<MobEffect, MobEffect> WARM = MOB_EFFECTS.register("warm", WarmEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> NATURE = MOB_EFFECTS.register("nature", NatureEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TREMOR = MOB_EFFECTS.register("tremor", TremorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> HOLINESS = MOB_EFFECTS.register("holiness", HolinessEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CONFUSION = MOB_EFFECTS.register("confusion", ConfusionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FROSTBITE = MOB_EFFECTS.register("frostbite", FrostbiteEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> LACERATION = MOB_EFFECTS.register("laceration", LacerationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CORRUPTION = MOB_EFFECTS.register("corruption", CorruptionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> INCANTATION = MOB_EFFECTS.register("incantation", IncantationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability", VulnerabilityEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FORBIDDEN_CURSE = MOB_EFFECTS.register("forbidden_curse", ForbiddenCurseEffect::new);

}