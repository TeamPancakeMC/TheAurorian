package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

@SuppressWarnings({"unchecked", "SpellCheckingInspection"})
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
    private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
            {ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_FOREST, ModBiomes.WEEPING_WILLOW_FOREST},
            {ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_FOREST, ModBiomes.WEEPING_WILLOW_FOREST, ModBiomes.WEEPING_WILLOW_FOREST},
            {ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_FOREST, ModBiomes.WEEPING_WILLOW_FOREST, ModBiomes.WEEPING_WILLOW_FOREST},
            {null, null, ModBiomes.AURORIAN_FOREST, null, null}, {null, null, null, null, null}};
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
            {null, null, ModBiomes.AURORIAN_FOREST, null, null},
            {null, null, null, null, ModBiomes.WEEPING_WILLOW_FOREST},
            {Biomes.SUNFLOWER_PLAINS, null, null, ModBiomes.WEEPING_WILLOW_FOREST, null},
            {null, null, ModBiomes.AURORIAN_PLAINS, null, null}, {null, null, null, null, null}};
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
            {ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_PLAINS, ModBiomes.AURORIAN_FOREST, ModBiomes.AURORIAN_FOREST},
            {null, null, ModBiomes.AURORIAN_FOREST, ModBiomes.WEEPING_WILLOW_FOREST, ModBiomes.WEEPING_WILLOW_FOREST},
            {null, null, null, null, ModBiomes.WEEPING_WILLOW_FOREST},
            {ModBiomes.AURORIAN_FOREST_HILLS, ModBiomes.AURORIAN_FOREST_HILLS, ModBiomes.AURORIAN_FOREST, ModBiomes.AURORIAN_FOREST, null},
            {null, null, null, null, null}};
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
            {null, null, null, null, null}, {null, null, null, null, ModBiomes.WEEPING_WILLOW_FOREST},
            {null, null, ModBiomes.AURORIAN_FOREST, ModBiomes.WEEPING_WILLOW_FOREST, null},
            {null, null, null, null, null}, {null, null, null, null, null}};
    
    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer) {
        this.addMidSlice(pConsumer, Climate.Parameter.span(-1.0F, -0.93333334F));
        this.addHighSlice(pConsumer, Climate.Parameter.span(-0.93333334F, -0.7666667F));
        this.addPeaks(pConsumer, Climate.Parameter.span(-0.7666667F, -0.56666666F));
        this.addHighSlice(pConsumer, Climate.Parameter.span(-0.56666666F, -0.4F));
        this.addMidSlice(pConsumer, Climate.Parameter.span(-0.4F, -0.26666668F));
        this.addLowSlice(pConsumer, Climate.Parameter.span(-0.26666668F, -0.05F));
        this.addLowSlice(pConsumer, Climate.Parameter.span(0.05F, 0.26666668F));
        this.addMidSlice(pConsumer, Climate.Parameter.span(0.26666668F, 0.4F));
        this.addHighSlice(pConsumer, Climate.Parameter.span(0.4F, 0.56666666F));
        this.addPeaks(pConsumer, Climate.Parameter.span(0.56666666F, 0.7666667F));
        this.addHighSlice(pConsumer, Climate.Parameter.span(0.7666667F, 0.93333334F));
        this.addMidSlice(pConsumer, Climate.Parameter.span(0.93333334F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter climate$parameter = this.temperatures[i];
            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter climate$parameter1 = this.humidities[j];
                ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey1 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey2 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, pParam);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], pParam, 0.0F, resourcekey2);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pParam, 0.0F, resourcekey3);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[3], pParam, 0.0F, resourcekey1);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[3], pParam, 0.0F, resourcekey3);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, resourcekey);
            }
        }
    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter climate$parameter = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter climate$parameter1 = this.humidities[j];
                ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey1 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey2 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, pParam);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[1], pParam, 0.0F, resourcekey2);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pParam, 0.0F, resourcekey3);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[3], pParam, 0.0F, resourcekey1);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[3], pParam, 0.0F, resourcekey3);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, resourcekey);
            }
        }
    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter climate$parameter = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter climate$parameter1 = this.humidities[j];
                ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey1 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey2 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey4 = this.pickPlateauBiome(i, j, pParam);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], pParam, 0.0F, resourcekey2);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[2], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[2], pParam, 0.0F, resourcekey1);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[2], pParam, 0.0F, resourcekey4);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], pParam, 0.0F, resourcekey1);
                if (pParam.max() < 0L) {
                    this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, resourcekey);
                } else {
                    this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, resourcekey);
                }

                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[5], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[6], pParam, 0.0F, resourcekey);

                if (i == 0) {
                    this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, resourcekey);
                }
            }
        }
    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter climate$parameter = this.temperatures[i];
            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter climate$parameter1 = this.humidities[j];
                ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey1 = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> resourcekey2 = this.pickMiddleBiome(i, j, pParam);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, resourcekey1);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, resourcekey2);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, resourcekey1);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[5], pParam, 0.0F, resourcekey);
                this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pParam, 0.0F, resourcekey);
                if (i == 0) {
                    this.addSurfaceBiome(pConsumer, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, resourcekey);
                }
            }
        }
    }

    private ResourceKey<Biome> pickMiddleBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pParam.max() < 0L) {
            return this.MIDDLE_BIOMES[pTemperature][pHumidity];
        } else {
            ResourceKey<Biome> resourcekey = this.MIDDLE_BIOMES_VARIANT[pTemperature][pHumidity];
            return resourcekey == null ? this.MIDDLE_BIOMES[pTemperature][pHumidity] : resourcekey;
        }
    }

    private ResourceKey<Biome> pickPlateauBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pParam.max() >= 0L) {
            ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[pTemperature][pHumidity];
            if (resourcekey != null) {
                return resourcekey;
            }
        }

        return this.PLATEAU_BIOMES[pTemperature][pHumidity];
    }

    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pTemperature, Climate.Parameter pHumidity, Climate.Parameter pContinentalness, Climate.Parameter pErosion, Climate.Parameter pDepth, float pWeirdness, ResourceKey<Biome> pKey) {
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(0.0F), pDepth, pWeirdness), pKey));
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.0F), pDepth, pWeirdness), pKey));
    }

}