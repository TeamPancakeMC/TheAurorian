package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.tree.foliage.HemisphereFoliagePlacer;
import cn.teampancake.theaurorian.common.level.feature.tree.foliage.TaperFoliagePlacer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAFoliagePlacerTypes {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, AurorianMod.MOD_ID);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<HemisphereFoliagePlacer>> HEMISPHERE = register("hemisphere", HemisphereFoliagePlacer.CODEC);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<TaperFoliagePlacer>> TAPER = register("taper", TaperFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<P>> register(String name, MapCodec<P> codec) {
        return FOLIAGE_PLACER_TYPES.register(name + "_foliage_placer", () -> new FoliagePlacerType<>(codec));
    }

}