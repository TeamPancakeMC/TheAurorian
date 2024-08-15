package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.api.IRune;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerType;
import cn.teampancake.theaurorian.common.rune.BaseRune;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Map;
import java.util.function.Supplier;

public class TARunes {

    public static final Map<IRune, ResourceLocation> RUNE_GAME_TEXTURES = Maps.newHashMap();
    public static final ResourceKey<Registry<IRune>> RUNE_KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("rune"));
    public static final DeferredRegister<IRune> RUNE = DeferredRegister.create(RUNE_KEY, TheAurorian.MOD_ID);
    public static final Registry<IRune> REGISTRY = new RegistryBuilder<>(RUNE_KEY).create();

    public static final DeferredHolder<IRune, IRune> AURORIAN = register("aurorian", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> AURORIANITE = register("aurorianite", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> BRIGHT_MOON = register("bright_moon", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> CERULEAN = register("cerulean", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> CREEPER = register("creeper", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> CRYSTALLINE = register("crystalline", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> FOREVER = register("forever", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> MOONSTONE = register("moonstone", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> NETHER = register("nether", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> POISON = register("poison", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> UMBRA = register("umbra", BaseRune::new);
    public static final DeferredHolder<IRune, IRune> WEALTH = register("wealth", BaseRune::new);

    private static DeferredHolder<IRune, IRune> register(String name, Supplier<? extends IRune> rune) {
        RUNE_GAME_TEXTURES.put(rune.get(), TheAurorian.prefix("textures/gui/rune/sprite_rune_" + name + ".png"));
        return RUNE.register(name, rune);
    }

}