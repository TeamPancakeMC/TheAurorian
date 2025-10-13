package cn.teampancake.theaurorian.common.level.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TASavedDataUtils {

    public static CompoundTag saveBooleanMap(Map<ResourceLocation, Boolean> booleanMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Boolean> entry : booleanMap.entrySet()) {
            tag.putBoolean(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }
    
    public static CompoundTag saveLongMap(Map<ResourceLocation, Long> timeMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Long> entry : timeMap.entrySet()) {
            tag.putLong(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }

    public static CompoundTag saveIntMap(Map<ResourceLocation, Integer> timeMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Integer> entry : timeMap.entrySet()) {
            tag.putInt(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }

    public static CompoundTag saveStringMap(Map<ResourceLocation, String> timeMap) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, String> entry : timeMap.entrySet()) {
            tag.putString(entry.getKey().toString(), entry.getValue());
        }

        return tag;
    }

    public static void loadBooleanMap(CompoundTag tag, String key, Map<ResourceLocation, Boolean> booleanMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) booleanMap.put(eventId, newTag.getBoolean(s));
            }
        }
    }

    public static void loadLongMap(CompoundTag tag, String key, Map<ResourceLocation, Long> timeMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) timeMap.put(eventId, newTag.getLong(s));
            }
        }
    }

    public static void loadIntMap(CompoundTag tag, String key, Map<ResourceLocation, Integer> timeMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) timeMap.put(eventId, newTag.getInt(s));
            }
        }
    }

    public static void loadStringMap(CompoundTag tag, String key, Map<ResourceLocation, String> timeMap) {
        if (tag.contains(key)) {
            CompoundTag newTag = tag.getCompound(key);
            for (String s : tag.getAllKeys()) {
                ResourceLocation eventId = ResourceLocation.tryParse(s);
                if (eventId != null) timeMap.put(eventId, newTag.getString(s));
            }
        }
    }
    
}