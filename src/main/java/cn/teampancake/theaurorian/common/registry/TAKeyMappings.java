package cn.teampancake.theaurorian.common.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TAKeyMappings {
    private static final String CATEGORY = "key.categories.theaurorian";
    public static final Lazy<KeyMapping> ARMOR_SET = Lazy.of(() -> new KeyMapping("key.theaurorian.armor_set",
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_UP, CATEGORY));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(ARMOR_SET.get());
    }
}
