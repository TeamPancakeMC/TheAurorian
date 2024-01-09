package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.FallenLogFeature;
import cn.teampancake.theaurorian.common.level.feature.HugeIndigoMushroomFeature;
import cn.teampancake.theaurorian.common.level.feature.UrnFeature;
import cn.teampancake.theaurorian.common.level.feature.config.FallenLogConfig;
import cn.teampancake.theaurorian.common.level.feature.ruin.MediumRuinFeature;
import cn.teampancake.theaurorian.common.level.feature.ruin.SmallRuinFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TAFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, AurorianMod.MOD_ID);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RANDOM_URN = FEATURES.register("random_urn", UrnFeature::new);
    public static final RegistryObject<Feature<FallenLogConfig>> RANDOM_FALLEN_LOGS = FEATURES.register("random_fallen_logs", FallenLogFeature::new);
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_INDIGO_MUSHROOM =
            FEATURES.register("huge_indigo_mushroom", HugeIndigoMushroomFeature::new);
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_SPRING = registerMediumRuins("aurorian_forest_spring");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_REMAINS = registerMediumRuins("aurorian_forest_remains");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_MEMORY_LOOP = registerMediumRuins("aurorian_forest_memory_loop");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_RUINED_PORTAL = registerMediumRuins("aurorian_forest_ruined_portal");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_SHATTERED_WREATH = registerMediumRuins("aurorian_forest_shattered_wreath");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_SHATTERED_PILLAR = registerMediumRuins("aurorian_forest_shattered_pillar");
    public static final RegistryObject<MediumRuinFeature> AURORIAN_FOREST_SHATTERED_FOREST_PILLAR = registerMediumRuins("aurorian_forest_shattered_forest_pillar");
    public static final List<RegistryObject<SmallRuinFeature>> AURORIAN_FOREST_SMALL_RUINS = new ArrayList<>();
    
    private static RegistryObject<MediumRuinFeature> registerMediumRuins(String name) {
        return FEATURES.register(name, () -> new MediumRuinFeature(name));
    }

    private static RegistryObject<SmallRuinFeature> registerSmallRuins(String name) {
        return FEATURES.register(name, () -> new SmallRuinFeature(name));
    }

    static {
        for (int i = 1; i < 23; i++) {
            AURORIAN_FOREST_SMALL_RUINS.add(registerSmallRuins("small_ruins_" + i));
        }
    }

}