package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.tree.trunk.StraightWithFallenLeavesTrunkPlacer;
import cn.teampancake.theaurorian.common.level.feature.tree.trunk.StraightWithRootTrunkPlacer;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TATrunkPlacerTypes {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, AurorianMod.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<StraightWithRootTrunkPlacer>> STRAIGHT_WITH_ROOT =
            register("straight_with_root", StraightWithRootTrunkPlacer.CODEC);
    public static final RegistryObject<TrunkPlacerType<StraightWithFallenLeavesTrunkPlacer>> STRAIGHT_WITH_FALLEN_LEAVES =
            register("straight_with_fallen_leaves", StraightWithFallenLeavesTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> RegistryObject<TrunkPlacerType<P>> register(String name, Codec<P> codec) {
        return TRUNK_PLACER_TYPES.register(name + "_trunk_placer", () -> new TrunkPlacerType<>(codec));
    }

}