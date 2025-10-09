package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.event.*;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class TAEventConfigurations {

    public static final ResourceKey<Registry<ConfiguredEvent<?, ?>>> KEY =
            ResourceKey.createRegistryKey(TheAurorian.namedRegistry("world_event/config"));
    public static final ResourceKey<ConfiguredEvent<?, ?>> BLOOD_MOON = createKey("blood_moon");
    public static final ResourceKey<ConfiguredEvent<?, ?>> ECLIPSE = createKey("eclipse");

    private static ResourceKey<ConfiguredEvent<?, ?>> createKey(String name) {
        return ResourceKey.create(KEY, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredEvent<?, ?>> context) {
        context.register(BLOOD_MOON, new ConfiguredEvent<>(TAWorldEvents.BLOOD_MOON.get(),
                new BaseEventConfig(2, 6000, 6000,
                        EventTimeRange.byTicks(12000, 12000))));
    }

}