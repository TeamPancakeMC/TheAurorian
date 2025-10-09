package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.event.BaseWorldEvent;
import cn.teampancake.theaurorian.common.level.data.event.BloodMoonEvent;
import cn.teampancake.theaurorian.common.level.data.event.BaseEventConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class TAWorldEvents {

    public static final ResourceKey<Registry<BaseWorldEvent<?>>> KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("world_event"));
    public static final DeferredRegister<BaseWorldEvent<?>> WORLD_EVENTS = DeferredRegister.create(KEY, TheAurorian.MOD_ID);
    public static final Registry<BaseWorldEvent<?>> REGISTRY = new RegistryBuilder<>(KEY).create();

    public static final DeferredHolder<BaseWorldEvent<?>, BaseWorldEvent<BaseEventConfig>> BLOOD_MOON = WORLD_EVENTS.register("blood_moon", BloodMoonEvent::new);

}