package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class TAPaintingVariants {

    public static final ResourceKey<PaintingVariant> AURORIAN_STEEL = createKey("aurorian_steel");
    public static final ResourceKey<PaintingVariant> PROGRESSION = createKey("progression");
    public static final ResourceKey<PaintingVariant> DUNGEON = createKey("dungeon");
    public static final ResourceKey<PaintingVariant> KEEPER = createKey("keeper");
    public static final ResourceKey<PaintingVariant> KNIGHT = createKey("knight");
    public static final ResourceKey<PaintingVariant> MOON = createKey("moon");
    public static final ResourceKey<PaintingVariant> PORTAL = createKey("portal");
    public static final ResourceKey<PaintingVariant> SLIME = createKey("slime");

    private static ResourceKey<PaintingVariant> createKey(String key) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, AurorianMod.prefix(key));
    }

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, AURORIAN_STEEL, 1, 1);
        register(context, PROGRESSION, 1, 1);
        register(context, DUNGEON, 1, 1);
        register(context, KEEPER, 2, 2);
        register(context, KNIGHT, 1, 2);
        register(context, MOON, 1, 2);
        register(context, PORTAL, 1, 1);
        register(context, SLIME, 1, 1);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
        context.register(key, new PaintingVariant(width, height, key.location()));
    }

}