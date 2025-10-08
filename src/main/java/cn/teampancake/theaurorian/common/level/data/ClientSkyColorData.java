package cn.teampancake.theaurorian.common.level.data;

import net.minecraft.world.level.Level;
import java.util.HashMap;
import java.util.Map;

public class ClientSkyColorData {

    private static final Map<Level, ClientSkyData> CLIENT_DATA = new HashMap<>();

    public static class ClientSkyData {

        public WorldSkyManager.SkyColor currentDayColor;
        public WorldSkyManager.SkyColor[] futureColors;
        public long lastSyncTime;

        public ClientSkyData() {
            this.currentDayColor = WorldSkyManager.getAvailableSkyColors().getFirst();
            this.futureColors = new WorldSkyManager.SkyColor[3];
            for (int i = 0; i < futureColors.length; i++) {
                this.futureColors[i] = WorldSkyManager.getAvailableSkyColors().getFirst();
            }

            this.lastSyncTime = 0;
        }

    }

    public static ClientSkyData getClientData(Level level) {
        return CLIENT_DATA.computeIfAbsent(level, k -> new ClientSkyData());
    }

    public static void updateClientData(
            Level level, WorldSkyManager.SkyColor currentColor,
            WorldSkyManager.SkyColor[] futureColors) {
        ClientSkyData data = getClientData(level);
        data.currentDayColor = currentColor;
        data.futureColors = futureColors;
        data.lastSyncTime = level.getGameTime();
    }

    public static void clearClientData(Level level) {
        CLIENT_DATA.remove(level);
    }

}