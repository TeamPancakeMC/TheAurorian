package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.effect.FreezeEffect;
import cn.teampancake.theaurorian.common.effect.TAMobEffect;
import cn.teampancake.theaurorian.common.effect.TempShieldEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TAMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AurorianMod.MOD_ID);
    public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x8B0000)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "e8c3b0b0-3d6c-11eb-b378-0242ac130002", (-1), AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> WARN = MOB_EFFECTS.register("warm", () -> new TAMobEffect(MobEffectCategory.BENEFICIAL, 0xfdefab));
    public static final RegistryObject<MobEffect> MOON_CURSE = MOB_EFFECTS.register("moon_curse", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0x714bdb));
    public static final RegistryObject<MobEffect> BROKEN = MOB_EFFECTS.register("broken", () -> new TAMobEffect(MobEffectCategory.HARMFUL, 0xFF808080));
    public static final RegistryObject<MobEffect> TEMP_SHIELD = MOB_EFFECTS.register("temp_shield", TempShieldEffect::new);
    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze", FreezeEffect::new);

}