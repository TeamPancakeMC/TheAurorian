package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;

public class TAFeatureFlags {

    public static final FeatureFlag EMISSIVITY = FeatureFlags.REGISTRY.getFlag(TheAurorian.prefix("emissivity"));
    public static final FeatureFlag DUNGEON = FeatureFlags.REGISTRY.getFlag(TheAurorian.prefix("dungeon"));
    public static final FeatureFlag BUILDING = FeatureFlags.REGISTRY.getFlag(TheAurorian.prefix("building"));
    public static final FeatureFlag WOOD_MATERIAL = FeatureFlags.REGISTRY.getFlag(TheAurorian.prefix("wood_material"));

}