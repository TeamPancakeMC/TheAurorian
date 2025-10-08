package cn.teampancake.theaurorian.common.level.data;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class WorldSkyDataStorage extends SavedData {

    private static final String FILE_NAME = "the_aurorian_world_sky_data";
    private final WorldSkyData skyData;

    public WorldSkyDataStorage() {
        this.skyData = new WorldSkyData(WorldSkyManager.getAvailableSkyColors().getFirst(), 3);
    }

    public WorldSkyDataStorage(WorldSkyData skyData) {
        this.skyData = skyData;
    }

    public WorldSkyData getSkyData() {
        return this.skyData;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("currentColorId", this.skyData.currentDayColor.id());
        tag.putInt("currentColorValue", this.skyData.currentDayColor.color());
        tag.putLong("lastMidnightTime", this.skyData.lastMidnightTime);
        tag.putInt("forecastDays", this.skyData.getForecastDays());
        ListTag futureColorsTag = new ListTag();
        for (int i = 0; i < this.skyData.futureColors.length; i++) {
            CompoundTag colorTag = new CompoundTag();
            colorTag.putInt("id", this.skyData.futureColors[i].id());
            colorTag.putInt("color", this.skyData.futureColors[i].color());
            futureColorsTag.add(colorTag);
        }

        tag.put("futureColors", futureColorsTag);
        return tag;
    }

    public static WorldSkyDataStorage load(CompoundTag tag, HolderLookup.Provider registries) {
        try {
            int forecastDays = tag.contains("forecastDays") ? tag.getInt("forecastDays") : 3;
            int currentColorId = tag.getInt("currentColorId");
            WorldSkyManager.SkyColor currentColor = WorldSkyManager.getSkyColorById(currentColorId)
                    .orElse(WorldSkyManager.getAvailableSkyColors().getFirst());
            WorldSkyData skyData = new WorldSkyData(currentColor, forecastDays);
            skyData.lastMidnightTime = tag.getLong("lastMidnightTime");
            if (tag.contains("futureColors")) {
                ListTag futureColorsTag = tag.getList("futureColors", Tag.TAG_COMPOUND);
                for (int i = 0; i < Math.min(futureColorsTag.size(), skyData.futureColors.length); i++) {
                    CompoundTag colorTag = futureColorsTag.getCompound(i);
                    int colorId = colorTag.getInt("id");
                    WorldSkyManager.SkyColor color = WorldSkyManager.getSkyColorById(colorId)
                            .orElse(WorldSkyManager.getRandomSkyColor());
                    skyData.futureColors[i] = color;
                }
            }

            return new WorldSkyDataStorage(skyData);
        } catch (Exception e) {
            TheAurorian.LOGGER.error("Failed to load sky color data, using defaults: {}", e.getMessage());
            return new WorldSkyDataStorage();
        }
    }

    public static WorldSkyDataStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldSkyDataStorage::new, WorldSkyDataStorage::load), FILE_NAME);
    }

}