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
    public static final DeferredHolder<MobEffect, MobEffect> STUN = MOB_EFFECTS.register("stun", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x8b0000)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("stun_ms"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, TheAurorian.prefix("stun_ad"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, TheAurorian.prefix("stun_js"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, TheAurorian.prefix("stun_bbs"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, TheAurorian.prefix("stun_bir"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, TheAurorian.prefix("stun_eir"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.MINING_EFFICIENCY, TheAurorian.prefix("stun_me"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0xc09c72)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("paralysis_ms"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, TheAurorian.prefix("paralysis_js"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> OVERHEATING = MOB_EFFECTS.register("overheating", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0xdb5f39)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("overheating_ms"), -0.1D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, TheAurorian.prefix("overheating_as"), -0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> TOUGH = MOB_EFFECTS.register("tough", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xf49cae));
    public static final DeferredHolder<MobEffect, MobEffect> BROKEN = MOB_EFFECTS.register("broken", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x808080));
    public static final DeferredHolder<MobEffect, MobEffect> CRESCENT = MOB_EFFECTS.register("crescent", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xd1b9a1));
    public static final DeferredHolder<MobEffect, MobEffect> PRESSURE = MOB_EFFECTS.register("pressure", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x714bdb));
    public static final DeferredHolder<MobEffect, MobEffect> MOON_BEFALL = MOB_EFFECTS.register("moon_befall", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xb76ce6));
    public static final DeferredHolder<MobEffect, MobEffect> BLESS_OF_MOON = MOB_EFFECTS.register("bless_of_moon", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xe3d5aa));
    public static final DeferredHolder<MobEffect, MobEffect> EIDOLON_POISON = MOB_EFFECTS.register("eidolon_poison", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x36d6be));
    public static final DeferredHolder<MobEffect, MobEffect> CRYSTALLIZATION = MOB_EFFECTS.register("crystallization", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x17d1c7));
    public static final DeferredHolder<MobEffect, MobEffect> MOON_OF_VENGEANCE = MOB_EFFECTS.register("moon_of_vengeance", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0x960a17));
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