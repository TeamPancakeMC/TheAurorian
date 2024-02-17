package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AurorianMod.MOD_ID);
    public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x8b0000)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "e8c3b0b0-3d6c-11eb-b378-0242ac130002", (-1), AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0xc09c72)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "3e4b0f9a-243e-4ea0-96ed-240437577f30", (-1), AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> MOON_OF_VENGEANCE = MOB_EFFECTS.register("moon_of_vengeance", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0x960a17)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "b8b5ad84-3a19-416a-870f-ac44bdee5954", 2.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> WARN = MOB_EFFECTS.register("warm", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xfdefab));
    public static final RegistryObject<MobEffect> BROKEN = MOB_EFFECTS.register("broken", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x808080));
    public static final RegistryObject<MobEffect> CRESCENT = MOB_EFFECTS.register("crescent", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xd1b9a1));
    public static final RegistryObject<MobEffect> PRESSURE = MOB_EFFECTS.register("pressure", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x714bdb));
    public static final RegistryObject<MobEffect> FALL_OF_MOON = MOB_EFFECTS.register("fall_of_moon", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xb76ce6));
    public static final RegistryObject<MobEffect> BLESS_OF_MOON = MOB_EFFECTS.register("bless_of_moon", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xe3d5aa));
    public static final RegistryObject<MobEffect> NATURE = MOB_EFFECTS.register("nature", NatureEffect::new);
    public static final RegistryObject<MobEffect> HOLINESS = MOB_EFFECTS.register("holiness", HolinessEffect::new);
    public static final RegistryObject<MobEffect> CONFUSION = MOB_EFFECTS.register("confusion", ConfusionEffect::new);
    public static final RegistryObject<MobEffect> FROSTBITE = MOB_EFFECTS.register("frostbite", FrostbiteEffect::new);
    public static final RegistryObject<MobEffect> CORRUPTION = MOB_EFFECTS.register("corruption", CorruptionEffect::new);
    public static final RegistryObject<MobEffect> TEMP_SHIELD = MOB_EFFECTS.register("temp_shield", TempShieldEffect::new);
    public static final RegistryObject<MobEffect> INCANTATION = MOB_EFFECTS.register("incantation", IncantationEffect::new);
    public static final RegistryObject<MobEffect> FORBIDDEN_CURSE = MOB_EFFECTS.register("forbidden_curse", ForbiddenCurseEffect::new);
    public static final RegistryObject<MobEffect> TREMOR = MOB_EFFECTS.register("tremor", TremorEffect::new);


}