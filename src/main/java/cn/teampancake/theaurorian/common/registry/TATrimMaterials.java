package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;

public class TATrimMaterials {

    public static final ResourceKey<TrimMaterial> AURORIANITE = registryKey("aurorianite");
    public static final ResourceKey<TrimMaterial> AURORIAN = registryKey("aurorian");
    public static final ResourceKey<TrimMaterial> CERULEAN = registryKey("cerulean");
    public static final ResourceKey<TrimMaterial> CRYSTALLINE = registryKey("crystalline");
    public static final ResourceKey<TrimMaterial> MOONSTONE = registryKey("moonstone");
    public static final ResourceKey<TrimMaterial> UMBRA = registryKey("umbra");

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        TrimMaterials.register(context, AURORIANITE, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x281fc8), 0.15F);
        TrimMaterials.register(context, AURORIAN, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x6605c9), 0.30F);
        TrimMaterials.register(context, CERULEAN, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x04439c), 0.45F);
        TrimMaterials.register(context, CRYSTALLINE, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x4c376c), 0.60F);
        TrimMaterials.register(context, MOONSTONE, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x61697c), 0.75F);
        TrimMaterials.register(context, UMBRA, TAItems.AURORIANITE_INGOT.get(), Style.EMPTY.withColor(0x1f3953), 0.90F);
    }

    private static ResourceKey<TrimMaterial> registryKey(String key) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, AurorianMod.prefix(key));
    }

}