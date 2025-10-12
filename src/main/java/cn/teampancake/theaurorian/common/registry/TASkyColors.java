package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.sky_color.BaseSkyColor;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class TASkyColors {

    public static final ResourceKey<Registry<BaseSkyColor>> KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("sky_color"));
    public static final DeferredRegister<BaseSkyColor> SKY_COLORS = DeferredRegister.create(KEY, TheAurorian.MOD_ID);
    public static final Registry<BaseSkyColor> REGISTRY = new RegistryBuilder<>(KEY).create();

    public static final DeferredHolder<BaseSkyColor, BaseSkyColor> PROTECTION = SKY_COLORS.register("protection",
            () -> new BaseSkyColor(0xf49cae, entity -> entity.addEffect(blessEffect(TAMobEffects.TOUGH))));
    public static final DeferredHolder<BaseSkyColor, BaseSkyColor> EXPLORATION = SKY_COLORS.register("exploration",
            () -> new BaseSkyColor(0x80e3ec, entity -> entity.addEffect(blessEffect(MobEffects.MOVEMENT_SPEED))));
    public static final DeferredHolder<BaseSkyColor, BaseSkyColor> MINING = SKY_COLORS.register("mining",
            () -> new BaseSkyColor(0xfff089, entity -> entity.addEffect(blessEffect(MobEffects.DIG_SPEED))));
    public static final DeferredHolder<BaseSkyColor, BaseSkyColor> GROWTH = SKY_COLORS.register("growth",
            () -> new BaseSkyColor(0x69c941, entity -> {}));
    public static final DeferredHolder<BaseSkyColor, BaseSkyColor> COMBAT =
            SKY_COLORS.register("combat", () -> new BaseSkyColor(0x8d60d7, entity -> {
                entity.addEffect(blessEffect(MobEffects.DAMAGE_BOOST));
                entity.addEffect(blessEffect(MobEffects.DAMAGE_RESISTANCE));
            }));

    private static MobEffectInstance blessEffect(Holder<MobEffect> effect) {
        return new MobEffectInstance(effect, 320, 0, false, false);
    }

}