package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.api.IRune;
import cn.teampancake.theaurorian.common.rune.BaseRune;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Supplier;

public class TARunes {

    public static final Map<IRune, ResourceLocation> RUNE_GAME_TEXTURES = Maps.newHashMap();
    public static final ResourceKey<Registry<IRune>> RUNE_KEY = ResourceKey.createRegistryKey(AurorianMod.prefix("rune"));
    public static final DeferredRegister<IRune> RUNE = DeferredRegister.create(RUNE_KEY, AurorianMod.MOD_ID);

    public static final RegistryObject<IRune> AURORIAN = register("aurorian", BaseRune::new);
    public static final RegistryObject<IRune> AURORIANITE = register("aurorianite", BaseRune::new);
    public static final RegistryObject<IRune> BRIGHT_MOON = register("bright_moon", BaseRune::new);
    public static final RegistryObject<IRune> CERULEAN = register("cerulean", BaseRune::new);
    public static final RegistryObject<IRune> CREEPER = register("creeper", BaseRune::new);
    public static final RegistryObject<IRune> CRYSTALLINE = register("crystalline", BaseRune::new);
    public static final RegistryObject<IRune> FOREVER = register("forever", BaseRune::new);
    public static final RegistryObject<IRune> MOONSTONE = register("moonstone", BaseRune::new);
    public static final RegistryObject<IRune> NETHER = register("nether", BaseRune::new);
    public static final RegistryObject<IRune> POISON = register("poison", BaseRune::new);
    public static final RegistryObject<IRune> UMBRA = register("umbra", BaseRune::new);
    public static final RegistryObject<IRune> WEALTH = register("wealth", BaseRune::new);

    private static RegistryObject<IRune> register(String name, Supplier<? extends IRune> rune) {
        RUNE_GAME_TEXTURES.put(rune.get(), AurorianMod.prefix("textures/gui/rune/sprite_rune_" + name + ".png"));
        return RUNE.register(name, rune);
    }

}