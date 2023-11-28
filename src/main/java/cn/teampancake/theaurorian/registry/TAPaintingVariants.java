package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.Util;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAPaintingVariants {

    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, AurorianMod.MOD_ID);
    public static final RegistryObject<PaintingVariant> AURORIAN_STEEL = PAINTING_VARIANTS.register("aurorian_steel", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> PROGRESSION = PAINTING_VARIANTS.register("progression", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> DUNGEON = PAINTING_VARIANTS.register("dungeon", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> KEEPER = PAINTING_VARIANTS.register("keeper", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> KNIGHT = PAINTING_VARIANTS.register("knight", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> MOON = PAINTING_VARIANTS.register("moon", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> PORTAL = PAINTING_VARIANTS.register("portal", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> SLIME = PAINTING_VARIANTS.register("slime", () -> new PaintingVariant(16, 16));

    public static String createDescriptionId(PaintingVariant value) {
        return Util.makeDescriptionId("painting", ForgeRegistries.PAINTING_VARIANTS.getKey(value));
    }

}