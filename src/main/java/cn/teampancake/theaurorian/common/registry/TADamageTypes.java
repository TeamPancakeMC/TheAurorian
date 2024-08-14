package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class TADamageTypes {

    public static final ResourceKey<DamageType> CORRUPTION = createKey("corruption");

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(CORRUPTION, new DamageType("corruption", 0.0F));
    }

}