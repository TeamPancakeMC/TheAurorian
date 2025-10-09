package cn.teampancake.theaurorian.common.level.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldSkyData {

    public WorldSkyManager.SkyColor currentDayColor;
    public WorldSkyManager.SkyColor[] futureColors;
    public long lastMidnightTime;
    private final int forecastDays;

    public WorldSkyData(WorldSkyManager.SkyColor initialColor, int forecastDays) {
        this.forecastDays = forecastDays;
        this.futureColors = new WorldSkyManager.SkyColor[forecastDays];
        this.currentDayColor = initialColor;
        for (int i = 0; i < forecastDays; i++) {
            this.futureColors[i] = WorldSkyManager.getRandomSkyColor();
        }

        this.lastMidnightTime = 0;
    }

    public int getForecastDays() {
        return forecastDays;
    }

    public WorldSkyManager.SkyColor getFutureColor(int daysAhead) {
        if (daysAhead >= 0 && daysAhead < this.futureColors.length) {
            return this.futureColors[daysAhead];
        }

        return this.futureColors[0];
    }

    public List<WorldSkyManager.SkyColor> getAllColors() {
        List<WorldSkyManager.SkyColor> colors = new ArrayList<>();
        colors.add(this.currentDayColor);
        colors.addAll(Arrays.asList(this.futureColors));
        return colors;
    }

    public void advanceDay() {
        this.currentDayColor = this.futureColors[0];
        for (int i = 0; i < this.forecastDays - 1; i++) {
            this.futureColors[i] = this.futureColors[i + 1];
        }

        this.futureColors[this.forecastDays - 1] = WorldSkyManager.getRandomSkyColor();
    }

}
