package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.feature.tree.trunk.StraightWithFallenLeavesTrunkPlacer;
import cn.teampancake.theaurorian.common.level.feature.tree.trunk.StraightWithRootTrunkPlacer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TATrunkPlacerTypes {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<StraightWithRootTrunkPlacer>> STRAIGHT_WITH_ROOT =
            register("straight_with_root", StraightWithRootTrunkPlacer.CODEC);
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<StraightWithFallenLeavesTrunkPlacer>> STRAIGHT_WITH_FALLEN_LEAVES =
            register("straight_with_fallen_leaves", StraightWithFallenLeavesTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<P>> register(String name, MapCodec<P> codec) {
        return TRUNK_PLACER_TYPES.register(name + "_trunk_placer", () -> new TrunkPlacerType<>(codec));
    }

}