package cn.teampancake.theaurorian.common.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenLogConfig(BlockStateProvider logState, float mushroomChance) implements FeatureConfiguration {

    public static final Codec<FallenLogConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("logState").forGetter(config -> config.logState),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("mushroomChance").forGetter(config -> config.mushroomChance))
            .apply(instance, FallenLogConfig::new));

}