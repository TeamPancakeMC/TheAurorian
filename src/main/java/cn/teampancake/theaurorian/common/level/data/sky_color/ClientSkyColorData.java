package cn.teampancake.theaurorian.common.level.data.sky_color;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSkyColorData {

    private static final Map<Level, ClientSkyData> CLIENT_DATA = new HashMap<>();

    public static class ClientSkyData {

        public ResourceLocation currentDayColor;
        public List<ResourceLocation> futureColors;

        public ClientSkyData(int forecastDays) {
            List<ResourceLocation> colors = SkyColorManager.getAvailableSkyColors();
            this.currentDayColor = colors.getFirst();
            this.futureColors = new ArrayList<>();
            for (int i = 0; i < forecastDays; i++) {
                this.futureColors.add(colors.getFirst());
            }
        }

    }

    public static ClientSkyData getClientData(Level level, int forecastDays) {
        return CLIENT_DATA.computeIfAbsent(level, k -> new ClientSkyData(forecastDays));
    }

    public static void updateClientData(
            Level level, ResourceLocation currentColor,
            List<ResourceLocation> futureColors) {
        ClientSkyData data = getClientData(level, futureColors.size());
        data.currentDayColor = currentColor;
        data.futureColors = futureColors;
    }

    public static void clearClientData(Level level) {
        CLIENT_DATA.remove(level);
    }

}