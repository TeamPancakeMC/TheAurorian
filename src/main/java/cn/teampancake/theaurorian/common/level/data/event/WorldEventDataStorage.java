package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;
import java.util.UUID;

public class WorldEventDataStorage extends SavedData {

    private static final String FILE_NAME = "the_aurorian_world_event_data";
    private final WorldEventData eventData;

    public WorldEventDataStorage() {
        this.eventData = new WorldEventData(0);
    }

    public WorldEventDataStorage(WorldEventData eventData) {
        this.eventData = eventData;
    }

    public WorldEventData getEventData() {
        return this.eventData;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putLong("worldStartTime", this.eventData.worldStartTime);
        tag.putLong("lastProcessedTime", this.eventData.lastProcessedTime);
        tag.put("eventEndTimes", this.saveTimeMap(this.eventData.eventEndTimes));
        tag.put("lastTriggerDays", this.saveTimeMap(this.eventData.lastTriggerDays));
        tag.put("lastActivationTime", this.saveTimeMap(this.eventData.lastActivationTime));
        tag.put("lastAbsoluteActivationTime", this.saveTimeMap(this.eventData.lastAbsoluteActivationTime));
        tag.put("scheduledEventTimes", this.saveTimeMap(this.eventData.scheduledEventTimes));
        tag.put("currentlyActive", this.saveBooleanMap(this.eventData.currentlyActive));
        tag.put("wasActiveLastTick", this.saveBooleanMap(this.eventData.wasActiveLastTick));
        tag.put("omenAftermathData", this.getOmenAftermathTag());
        tag.put("bloodMoonPlayerData", this.getBloodMoonCompoundTag());
        return tag;
    }

    private CompoundTag getOmenAftermathTag() {
        CompoundTag omenAftermathTag = new CompoundTag();
        omenAftermathTag.put("lastEventEndTimes", this.saveTimeMap(this.eventData.lastEventEndTimes));
        omenAftermathTag.put("omenExecuted", this.saveBooleanMap(this.eventData.omenExecuted));
        omenAftermathTag.put("aftermathExecuted", this.saveBooleanMap(this.eventData.aftermathExecuted));
        return omenAftermathTag;
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

    private CompoundTag saveTimeMap(Map<ResourceLocation, Long> timeMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Long> entry : timeMap.entrySet()) {
            tag.putLong(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }

    private CompoundTag saveBooleanMap(Map<ResourceLocation, Boolean> booleanMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Boolean> entry : booleanMap.entrySet()) {
            tag.putBoolean(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }

    public static WorldEventDataStorage load(CompoundTag tag, HolderLookup.Provider registries) {
        try {
            long worldStartTime = tag.getLong("worldStartTime");
            long lastProcessedTime = tag.contains("lastProcessedTime") ? tag.getLong("lastProcessedTime") : worldStartTime;
            WorldEventData eventData = new WorldEventData(worldStartTime);
            eventData.lastProcessedTime = lastProcessedTime;
            loadTimeMap(tag, "eventEndTimes", eventData.eventEndTimes);
            loadTimeMap(tag, "lastTriggerDays", eventData.lastTriggerDays);
            loadTimeMap(tag, "lastActivationTime", eventData.lastActivationTime);
            loadTimeMap(tag, "lastAbsoluteActivationTime", eventData.lastAbsoluteActivationTime);
            loadTimeMap(tag, "scheduledEventTimes", eventData.scheduledEventTimes);
            loadBooleanMap(tag, "currentlyActive", eventData.currentlyActive);
            loadBooleanMap(tag, "wasActiveLastTick", eventData.wasActiveLastTick);
            if (tag.contains("bloodMoonPlayerData")) {
                CompoundTag bloodMoonDataTag = tag.getCompound("bloodMoonPlayerData");
                for (String key : bloodMoonDataTag.getAllKeys()) {
                    try {
                        UUID playerId = UUID.fromString(key);
                        CompoundTag playerTag = bloodMoonDataTag.getCompound(key);
                        WorldEventData.BloodMoonPlayerData playerData = new WorldEventData.BloodMoonPlayerData();
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

            if (tag.contains("omenAftermathData")) {
                CompoundTag omenAftermathTag = tag.getCompound("omenAftermathData");
                loadTimeMap(omenAftermathTag, "lastEventEndTimes", eventData.lastEventEndTimes);
                loadBooleanMap(omenAftermathTag, "omenExecuted", eventData.omenExecuted);
                loadBooleanMap(omenAftermathTag, "aftermathExecuted", eventData.aftermathExecuted);
            }

            return new WorldEventDataStorage(eventData);
        } catch (Exception e) {
            TheAurorian.LOGGER.error("Failed to load world event data, using defaults: {}", e.getMessage());
            return new WorldEventDataStorage();
        }
    }

    private static void loadTimeMap(CompoundTag tag, String key, Map<ResourceLocation, Long> timeMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) timeMap.put(eventId, newTag.getLong(s));
            }
        }
    }

    private static void loadBooleanMap(CompoundTag tag, String key, Map<ResourceLocation, Boolean> booleanMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) booleanMap.put(eventId, newTag.getBoolean(s));
            }
        }
    }

    public static WorldEventDataStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(WorldEventDataStorage::new, WorldEventDataStorage::load), FILE_NAME);
    }

}