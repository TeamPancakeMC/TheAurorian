package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.common.level.TANaturalSpawner;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.*;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"ConstantConditions", "deprecation"})
public class TAChunkGenerator extends NoiseBasedChunkGenerator {

    public static final MapCodec<TAChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.chunkGenerator),
            NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(o -> o.noiseGeneratorSettings)
    ).apply(instance, TAChunkGenerator::new));

    private final ChunkGenerator chunkGenerator;
    private final Holder<NoiseGeneratorSettings> noiseGeneratorSettings;

    public TAChunkGenerator(ChunkGenerator chunkGenerator, Holder<NoiseGeneratorSettings> noiseGenSettings) {
        super(chunkGenerator.getBiomeSource(), noiseGenSettings);
        this.chunkGenerator = chunkGenerator;
        this.noiseGeneratorSettings = noiseGenSettings;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        if (!this.generatorSettings().value().disableMobGeneration()) {
            ChunkPos chunkPos = level.getCenter();
            Holder<Biome> holder = level.getBiome(chunkPos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            worldgenRandom.setDecorationSeed(level.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
            TANaturalSpawner.spawnMobsForChunkGeneration(level, holder, chunkPos, worldgenRandom);
        }
    }

}