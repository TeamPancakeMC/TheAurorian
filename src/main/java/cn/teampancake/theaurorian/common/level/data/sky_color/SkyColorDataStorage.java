package cn.teampancake.theaurorian.common.level.data.sky_color;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class SkyColorDataStorage extends SavedData {

    private static final String FILE_NAME = "the_aurorian_world_sky_data";
    private final SkyColorData skyData;

    public SkyColorDataStorage() {
        this.skyData = new SkyColorData(SkyColorManager.getAvailableSkyColors().getFirst(), 3);
    }

    public SkyColorDataStorage(SkyColorData skyData) {
        this.skyData = skyData;
    }

    public SkyColorData getSkyData() {
        return this.skyData;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("forecastDays", this.skyData.getForecastDays());
        tag.putString("currentColorId", this.skyData.currentDayColor.toString());
        tag.putLong("lastMidnightTime", this.skyData.lastMidnightTime);
        ListTag futureColorsTag = new ListTag();
        for (int i = 0; i < this.skyData.futureColorIds.size(); i++) {
            CompoundTag colorTag = new CompoundTag();
            ResourceLocation id = this.skyData.futureColorIds.get(i);
            colorTag.putString("id", id.toString());
            futureColorsTag.add(colorTag);
        }

        tag.put("futureColors", futureColorsTag);
        return tag;
    }

    public static SkyColorDataStorage load(CompoundTag tag, HolderLookup.Provider registries) {
        try {
            int forecastDays = tag.contains("forecastDays") ? tag.getInt("forecastDays") : 3;
            ResourceLocation currentColorId = ResourceLocation.parse(tag.getString("currentColorId"));
            SkyColorData skyData = new SkyColorData(currentColorId, forecastDays);
            skyData.lastMidnightTime = tag.getLong("lastMidnightTime");
            if (tag.contains("futureColors")) {
                ListTag futureColorsTag = tag.getList("futureColors", Tag.TAG_COMPOUND);
                for (int i = 0; i < Math.min(futureColorsTag.size(), skyData.futureColorIds.size()); i++) {
                    CompoundTag colorTag = futureColorsTag.getCompound(i);
                    String colorId = colorTag.getString("id");
                    skyData.futureColorIds.set(i, ResourceLocation.parse(colorId));
                }
            }

            return new SkyColorDataStorage(skyData);
        } catch (Exception e) {
            TheAurorian.LOGGER.error("Failed to load sky color data, using defaults: {}", e.getMessage());
            return new SkyColorDataStorage();
        }
    }

    public static SkyColorDataStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(SkyColorDataStorage::new, SkyColorDataStorage::load), FILE_NAME);
    }

}