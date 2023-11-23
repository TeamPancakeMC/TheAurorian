package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.registry.TABiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.BiConsumer;

@SuppressWarnings({"SpellCheckingInspection", "unchecked"})
public class AurorianBiomeBuilder {

    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)};
    private final Climate.Parameter[] erosions = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)};
    private final Climate.Parameter fullRange = Climate.Parameter.span(-1.0F, 1.0F);
    private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    private final ResourceKey<Biome>[] biomes = new ResourceKey[] {
            TABiomes.AURORIAN_FOREST, TABiomes.AURORIAN_PLAINS,
            TABiomes.AURORIAN_LAKES, TABiomes.WEEPING_WILLOW_FOREST};

    public void addBiomes(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
        this.addSurfaceBiome(consumer, this.temperatures[0], this.fullRange, this.coastContinentalness, this.erosions[6],
                Climate.Parameter.span(-0.26666668F, -0.05F), 0.0F, this.biomes[2]);
        this.addSurfaceBiome(consumer, this.temperatures[0], this.fullRange, this.coastContinentalness, this.erosions[6],
                Climate.Parameter.span(0.05F, 0.26666668F), 0.0F, this.biomes[2]);
        this.addSurfaceBiome(consumer, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.fullRange,
                Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6],
                Climate.Parameter.span(-0.26666668F, -0.05F), 0.0F, this.biomes[1]);
        this.addSurfaceBiome(consumer, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.fullRange,
                Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6],
                Climate.Parameter.span(0.05F, 0.26666668F), 0.0F, this.biomes[1]);
        this.addSurfaceBiome(consumer, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.fullRange,
                Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6],
                Climate.Parameter.span(-0.26666668F, -0.05F), 0.0F, this.biomes[0]);
        this.addSurfaceBiome(consumer, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.fullRange,
                Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[6],
                Climate.Parameter.span(0.05F, 0.26666668F), 0.0F, this.biomes[0]);
        this.addSurfaceBiome(consumer, this.temperatures[4], this.fullRange, this.coastContinentalness, this.erosions[6],
                Climate.Parameter.span(-0.26666668F, -0.05F), 0.0F, this.biomes[3]);
        this.addSurfaceBiome(consumer, this.temperatures[4], this.fullRange, this.coastContinentalness, this.erosions[6],
                Climate.Parameter.span(0.05F, 0.26666668F), 0.0F, this.biomes[3]);
    }

    private void addSurfaceBiome(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter depth, float weirdness, ResourceKey<Biome> key) {
        consumer.accept(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0F), depth, weirdness), key);
        consumer.accept(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), depth, weirdness), key);
    }

}