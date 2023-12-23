package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.tree.foliage.HemisphereFoliagePlacer;
import cn.teampancake.theaurorian.common.level.feature.tree.foliage.TaperFoliagePlacer;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAFoliagePlacerTypes {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<FoliagePlacerType<HemisphereFoliagePlacer>> HEMISPHERE = register("hemisphere", HemisphereFoliagePlacer.CODEC);
    public static final RegistryObject<FoliagePlacerType<TaperFoliagePlacer>> TAPER = register("taper", TaperFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> RegistryObject<FoliagePlacerType<P>> register(String name, Codec<P> codec) {
        return FOLIAGE_PLACER_TYPES.register(name + "_foliage_placer", () -> new FoliagePlacerType<>(codec));
    }

}