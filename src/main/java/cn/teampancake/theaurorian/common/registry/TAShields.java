package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.shields.AurorianShield;
import cn.teampancake.theaurorian.common.shields.BaseShield;
import cn.teampancake.theaurorian.common.shields.TempShield;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class TAShields {

    public static final ResourceKey<Registry<BaseShield>> KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("shield"));
    public static final DeferredRegister<BaseShield> SHIELDS = DeferredRegister.create(KEY, TheAurorian.MOD_ID);
    public static final Registry<BaseShield> REGISTRY = new RegistryBuilder<>(KEY).sync(true).create();

    public static final DeferredHolder<BaseShield, BaseShield> COMMON = SHIELDS.register("common", () -> new BaseShield(
            new BaseShield.Properties().component(TADataComponents.MAX_SHIELD.get(), 15.0F).priority(0).rate(0.25F).color(0x808080)));
    public static final DeferredHolder<BaseShield, BaseShield> AURORIAN = SHIELDS.register("aurorian", () -> new AurorianShield(
            new BaseShield.Properties().component(TADataComponents.MAX_SHIELD.get(), 5.0F).priority(1).rate(0.25F).color(0x010e34)));
    public static final DeferredHolder<BaseShield, BaseShield> TEMP = SHIELDS.register("temp", () -> new TempShield(
            new BaseShield.Properties().component(TADataComponents.MAX_SHIELD.get(), 0.0F).priority(50).rate(0.0F).color(0x800000)));

}