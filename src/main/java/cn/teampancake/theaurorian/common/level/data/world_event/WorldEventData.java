package cn.teampancake.theaurorian.common.level.data.world_event;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEventData {

    public final Map<ResourceLocation, Long> lastActivationDays = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> currentTicks = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> remainingTicks = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, String> eventStates = new ConcurrentHashMap<>();
    public final Map<UUID, BloodMoonPlayerData> bloodMoonPlayerData = new ConcurrentHashMap<>();

}