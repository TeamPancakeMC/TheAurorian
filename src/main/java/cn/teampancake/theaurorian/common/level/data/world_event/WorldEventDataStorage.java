package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.TASavedDataUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class WorldEventDataStorage extends SavedData {

    private static final String FILE_NAME = "the_aurorian_world_event_data";
    private final WorldEventData eventData;

    public WorldEventDataStorage() {
        this.eventData = new WorldEventData();
    }

    public WorldEventDataStorage(WorldEventData eventData) {
        this.eventData = eventData;
    }

    public WorldEventData getEventData() {
        return this.eventData;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("isFirstActivation", TASavedDataUtils.saveBooleanMap(this.eventData.isFirstActivation));
        tag.put("lastActivationDays", TASavedDataUtils.saveLongMap(this.eventData.lastActivationDays));
        tag.put("currentTicks", TASavedDataUtils.saveLongMap(this.eventData.currentTicks));
        tag.put("remainingTicks", TASavedDataUtils.saveLongMap(this.eventData.remainingTicks));
        tag.put("eventStates", TASavedDataUtils.saveStringMap(this.eventData.eventStates));
        tag.put("bloodMoonPlayerData", this.getBloodMoonCompoundTag());
        return tag;
    }

    private CompoundTag getBloodMoonCompoundTag() {
        CompoundTag bloodMoonDataTag = new CompoundTag();
        for (var entry : this.eventData.bloodMoonPlayerData.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putInt("kills", entry.getValue().kills);
            playerTag.putBoolean("rewardActive", entry.getValue().rewardActive);
            playerTag.putBoolean("penaltyActive", entry.getValue().penaltyActive);
            playerTag.putLong("rewardUntil", entry.getValue().rewardUntil);
            bloodMoonDataTag.put(entry.getKey().toString(), playerTag);
        }

        return bloodMoonDataTag;
    }

    public static WorldEventDataStorage load(CompoundTag tag, HolderLookup.Provider registries) {
        try {
            WorldEventData eventData = new WorldEventData();
            TASavedDataUtils.loadBooleanMap(tag, "isFirstActivation", eventData.isFirstActivation);
            TASavedDataUtils.loadLongMap(tag, "lastActivationDays", eventData.lastActivationDays);
            TASavedDataUtils.loadLongMap(tag, "currentTicks", eventData.currentTicks);
            TASavedDataUtils.loadLongMap(tag, "remainingTicks", eventData.remainingTicks);
            TASavedDataUtils.loadStringMap(tag, "eventStates", eventData.eventStates);
            if (tag.contains("bloodMoonPlayerData")) {
                CompoundTag bloodMoonDataTag = tag.getCompound("bloodMoonPlayerData");
                for (String key : bloodMoonDataTag.getAllKeys()) {
                    try {
                        UUID playerId = UUID.fromString(key);
                        CompoundTag playerTag = bloodMoonDataTag.getCompound(key);
                        BloodMoonPlayerData playerData = new BloodMoonPlayerData();
                        playerData.kills = playerTag.getInt("kills");
                        playerData.rewardActive = playerTag.getBoolean("rewardActive");
                        playerData.penaltyActive = playerTag.getBoolean("penaltyActive");
                        playerData.rewardUntil = playerTag.getLong("rewardUntil");
                        eventData.bloodMoonPlayerData.put(playerId, playerData);
                    } catch (IllegalArgumentException e) {
                        TheAurorian.LOGGER.error("Invalid UUID in blood moon data: {}", key);
                    }
                }
            }

            return new WorldEventDataStorage(eventData);
        } catch (Exception e) {
            TheAurorian.LOGGER.error("Failed to load world event data, using defaults: {}", e.getMessage());
            return new WorldEventDataStorage();
        }
    }

    public static WorldEventDataStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(WorldEventDataStorage::new, WorldEventDataStorage::load), FILE_NAME);
    }

}