package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.shields.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TAShields {

    public static final ResourceKey<Registry<IShield>> SHIELD_KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("shield"));
    public static final DeferredRegister<IShield> SHIELD = DeferredRegister.create(SHIELD_KEY, TheAurorian.MOD_ID);

    public static final DeferredHolder<IShield, IShield> COMMON = register("common", () -> new CommonShield(0, 0.0f, 15.0f, 0xFF808080));
    public static final DeferredHolder<IShield, IShield> AURORIAN = register("aurorian", () -> new AurorianShield(1, 0.0f,0.0f, 0xFF010e34));
    public static final DeferredHolder<IShield, IShield> TEMP = register("temp", () -> new TempShield(100, 0.0f, 0.0f, 0xFF800000));

    public static DeferredHolder<IShield, IShield> register(String name, Supplier<? extends IShield> supplier) {
        return SHIELD.register(name, supplier);
    }

    public static ResourceLocation getKey(IShield shield) {
        return SHIELD.getEntries().stream().filter(entry -> entry.get().getClass() == shield.getClass()).findFirst()
                .map(DeferredHolder::getId).orElseThrow(() -> new IllegalArgumentException("Shield " + shield + " is not registered"));
    }

}