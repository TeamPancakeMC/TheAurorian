package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.FallenLogFeature;
import cn.teampancake.theaurorian.common.level.feature.HugeIndigoMushroomFeature;
import cn.teampancake.theaurorian.common.level.feature.UrnFeature;
import cn.teampancake.theaurorian.common.level.feature.config.FallenLogConfig;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, AurorianMod.MOD_ID);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RANDOM_URN = FEATURES.register("random_urn", UrnFeature::new);
    public static final RegistryObject<Feature<FallenLogConfig>> RANDOM_FALLEN_LOGS = FEATURES.register("random_fallen_logs", FallenLogFeature::new);
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_INDIGO_MUSHROOM = FEATURES.register("huge_indigo_mushroom", HugeIndigoMushroomFeature::new);

}