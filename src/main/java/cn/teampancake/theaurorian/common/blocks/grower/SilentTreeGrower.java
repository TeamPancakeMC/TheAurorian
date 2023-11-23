package cn.teampancake.theaurorian.common.blocks.grower;

import cn.teampancake.theaurorian.common.level.feature.TAConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class SilentTreeGrower extends AbstractTreeGrower {

    @Nullable @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return TAConfiguredFeatures.SILENT_TREE;
    }

}