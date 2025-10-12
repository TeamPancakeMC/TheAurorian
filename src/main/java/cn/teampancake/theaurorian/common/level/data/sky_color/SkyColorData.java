package cn.teampancake.theaurorian.common.level.data.sky_color;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SkyColorData {

    public ResourceLocation currentDayColor;
    public List<ResourceLocation> futureColorIds;
    public long lastMidnightTime;
    private final int forecastDays;

    public SkyColorData(ResourceLocation initialColor, int forecastDays) {
        this.forecastDays = forecastDays;
        this.futureColorIds = new ArrayList<>();
        this.currentDayColor = initialColor;
        for (int i = 0; i < forecastDays; i++) {
            this.futureColorIds.add(SkyColorManager.getRandomSkyColor());
        }

        this.lastMidnightTime = 0;
    }

    public int getForecastDays() {
        return this.forecastDays;
    }

    public ResourceLocation getFutureColor(int daysAhead) {
        if (daysAhead >= 0 && daysAhead < this.futureColorIds.size()) {
            return this.futureColorIds.get(daysAhead);
        }

        return this.futureColorIds.getFirst();
    }

    public List<ResourceLocation> getAllColors() {
        List<ResourceLocation> colors = new ArrayList<>();
        colors.add(this.currentDayColor);
        colors.addAll(this.futureColorIds);
        return colors;
    }

    public void advanceDay() {
        this.currentDayColor = this.futureColorIds.getFirst();
        for (int i = 0; i < this.forecastDays - 1; i++) {
            this.futureColorIds.set(i, this.futureColorIds.get(i + 1));
        }

        this.futureColorIds.set(this.forecastDays - 1, SkyColorManager.getRandomSkyColor());
    }

}
