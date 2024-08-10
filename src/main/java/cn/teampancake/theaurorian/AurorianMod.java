package cn.teampancake.theaurorian;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

@Mod(AurorianMod.MOD_ID)
public class AurorianMod {

    public static final String MOD_ID = "theaurorian";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public AurorianMod(IEventBus modEventBus, ModContainer modContainer) {

    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return ResourceLocation.fromNamespaceAndPath("aurorian", name.toLowerCase(Locale.ROOT));
    }

}