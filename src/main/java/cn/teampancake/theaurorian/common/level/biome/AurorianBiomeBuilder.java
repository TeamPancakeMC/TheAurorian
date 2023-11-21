package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.registry.ModBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.BiConsumer;

@SuppressWarnings("SpellCheckingInspection")
public class AurorianBiomeBuilder {

    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)};
    private final Climate.Parameter[] humidities = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.35F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)};
    private final Climate.Parameter[] erosions = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)};
    Climate.Parameter[] lowSliceWeirds = new Climate.Parameter[] {
            Climate.Parameter.span(-0.05F, -0.05F),
            Climate.Parameter.span(0.05F, 0.26666668F)};
    private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(0.01F, 0.02F);
    private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.02F, 0.03F);

    public void addBiomes(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
        this.addWeepingWillowForestBiome(consumer);
        this.addAurorianForestBiome(consumer);
        this.addAurorianPlainsBiome(consumer);
    }

    private void addWeepingWillowForestBiome(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
//        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
//        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.WEEPING_WILLOW_FOREST);
    }

    private void addAurorianForestBiome(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
        for (int i = 2; i <= 4; i++) {
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], this.nearInlandContinentalness, this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
//            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], Climate.Parameter.span(this.nearInlandContinentalness, this.nearInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
            this.addSurfaceBiome(consumer, this.temperatures[i], this.humidities[3], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_FOREST);
        }
    }

    private void addAurorianPlainsBiome(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
//        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[0], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
//        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[1], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
//        this.addSurfaceBiome(consumer, this.temperatures[2], this.humidities[1], this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[2], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[4], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
        this.addSurfaceBiome(consumer, this.temperatures[2], this.humidities[1], Climate.Parameter.span(this.midInlandContinentalness, this.midInlandContinentalness), this.erosions[5], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
//        this.addSurfaceBiome(consumer, this.temperatures[2], this.humidities[1], Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6], Climate.Parameter.point(-0.05F), ModBiomes.AURORIAN_PLAINS);
    }

    private void addSurfaceBiome(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer, Climate.Parameter temperature, Climate.Parameter humidity,
                                 Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, ResourceKey<Biome> key) {
        consumer.accept(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0F), weirdness, 0.0F), key);
        consumer.accept(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), weirdness, 0.0F), key);
    }

}