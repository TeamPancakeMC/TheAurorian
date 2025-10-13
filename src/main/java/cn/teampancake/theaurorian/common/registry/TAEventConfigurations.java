package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.world_event.*;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class TAEventConfigurations {

    public static final ResourceKey<Registry<ConfiguredEvent<?, ?>>> KEY =
            ResourceKey.createRegistryKey(TheAurorian.namedRegistry("world_event/config"));
    public static final ResourceKey<ConfiguredEvent<?, ?>> BLOOD_MOON = createKey("blood_moon");
    public static final ResourceKey<ConfiguredEvent<?, ?>> LUNAR_ECLIPSE = createKey("lunar_eclipse");

    private static ResourceKey<ConfiguredEvent<?, ?>> createKey(String name) {
        return ResourceKey.create(KEY, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredEvent<?, ?>> context) {
        context.register(BLOOD_MOON, new ConfiguredEvent<>(TAWorldEvents.BLOOD_MOON.get(),
                new BaseEventConfig(12000, 12000, 2,
                        Boolean.TRUE, Boolean.TRUE, 6000, 6000, Boolean.TRUE)));
    }

}