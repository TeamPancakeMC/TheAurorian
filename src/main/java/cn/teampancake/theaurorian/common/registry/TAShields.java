package cn.teampancake.theaurorian.common.registry;


import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.shield.AurorianShield;
import cn.teampancake.theaurorian.common.shield.CommonShield;
import cn.teampancake.theaurorian.common.shield.TempShield;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.*;

import java.util.Map;
import java.util.function.Supplier;

public class TAShields {
    public static final Map<Class<? extends IShield>,ResourceLocation> SHIELD_MAP = Maps.newHashMap();
    public static final ResourceKey<Registry<IShield>> SHIELD_KEY = ResourceKey.createRegistryKey(AurorianMod.prefix("shield"));
    public static final DeferredRegister<IShield> SHIELD = DeferredRegister.create(SHIELD_KEY, AurorianMod.MOD_ID);

    public static final RegistryObject<IShield> COMMON = register("common", ()->new CommonShield(0,0.0f,15.0f,0xFF808080));
    public static final RegistryObject<IShield> AURORIAN = register("aurorian", ()->new AurorianShield(1,0.0f,0.0f,0xFF010e34));
    public static final RegistryObject<IShield> TEMP = register("temp", ()->new TempShield(100,0.0f,0.0f,0xFF800000));

    public static RegistryObject<IShield> register(String name, Supplier<? extends IShield> supplier) {
        RegistryObject<IShield> register = SHIELD.register(name, supplier);
        SHIELD_MAP.put(register.get().getClass(),register.getId());
        return register;
    }

    public static ResourceLocation getKey(IShield shield) {
        return SHIELD.getEntries().stream()
                .filter(entry -> entry.get().getClass() == shield.getClass())
                .findFirst().map(RegistryObject::getId)
                .orElseThrow(() -> new IllegalArgumentException("Shield " + shield + " is not registered"));
    }
}
