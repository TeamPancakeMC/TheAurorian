package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.common.level.data.TASavedDataUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEventDataStorage extends SavedData {

    private static final String FILE_NAME = "the_aurorian_world_event_data";
    public final Map<ResourceLocation, Long> lastActivationDays = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> currentTicks = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> remainingTicks = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, String> eventStates = new ConcurrentHashMap<>();
    public final Map<UUID, BloodMoonPlayerData> bloodMoonPlayerData = new ConcurrentHashMap<>();

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("lastActivationDays", TASavedDataUtils.saveLongMap(this.lastActivationDays));
        tag.put("currentTicks", TASavedDataUtils.saveLongMap(this.currentTicks));
        tag.put("remainingTicks", TASavedDataUtils.saveLongMap(this.remainingTicks));
        tag.put("eventStates", TASavedDataUtils.saveStringMap(this.eventStates));
        tag.put("bloodMoonPlayerData", this.getBloodMoonCompoundTag());
        return tag;
    }

    private CompoundTag getBloodMoonCompoundTag() {
        CompoundTag bloodMoonDataTag = new CompoundTag();
        for (var entry : this.bloodMoonPlayerData.entrySet()) {
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
        WorldEventDataStorage storage = new WorldEventDataStorage();
        TASavedDataUtils.loadLongMap(tag, "lastActivationDays", storage.lastActivationDays);
        TASavedDataUtils.loadLongMap(tag, "currentTicks", storage.currentTicks);
        TASavedDataUtils.loadLongMap(tag, "remainingTicks", storage.remainingTicks);
        TASavedDataUtils.loadStringMap(tag, "eventStates", storage.eventStates);
        if (tag.contains("bloodMoonPlayerData")) {
            CompoundTag bloodMoonDataTag = tag.getCompound("bloodMoonPlayerData");
            for (String key : bloodMoonDataTag.getAllKeys()) {
                CompoundTag playerTag = bloodMoonDataTag.getCompound(key);
                BloodMoonPlayerData playerData = new BloodMoonPlayerData();
                playerData.kills = playerTag.getInt("kills");
                playerData.rewardActive = playerTag.getBoolean("rewardActive");
                playerData.penaltyActive = playerTag.getBoolean("penaltyActive");
                playerData.rewardUntil = playerTag.getLong("rewardUntil");
                storage.bloodMoonPlayerData.put(UUID.fromString(key), playerData);
            }
        }

        return storage;
    }

    public static WorldEventDataStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(WorldEventDataStorage::new, WorldEventDataStorage::load), FILE_NAME);
    }

}