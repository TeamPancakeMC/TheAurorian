package cn.teampancake.theaurorian.common.data.pack;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MaxShieldLoader extends SimpleJsonResourceReloadListener {
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static final Map<String,Map<ResourceLocation,Map<ResourceLocation,Integer>>> MAP = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Dimension = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Item = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Achievements = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Entity = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Armor = Maps.newHashMap();
    public static final Map<ResourceLocation,Map<ResourceLocation,Integer>> Buff = Maps.newHashMap();

    public MaxShieldLoader() {
        super(GSON, "max_shield");
        register();
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> jsonElementMap, @NotNull ResourceManager manager, @NotNull ProfilerFiller filler) {
        MAP.forEach((s, resourceLocationMapMap) -> resourceLocationMapMap.clear());
        jsonElementMap.values().stream()
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .flatMap(jsonElements -> jsonElements.asList().stream())
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .forEach(jsonObject -> {
                    String id = jsonObject.get("id").getAsString();
                    addMap(MAP.get(id),jsonObject);
                });
    }


    private void addMap(Map<ResourceLocation,Map<ResourceLocation,Integer>> map, JsonObject jsonObject){
        ResourceLocation location = ResourceLocation.tryParse(jsonObject.get("location").getAsString());
        ResourceLocation type = ResourceLocation.tryParse(jsonObject.get("type").getAsString());
        int maxShield = jsonObject.get("max_shield").getAsInt();
        map.putIfAbsent(type,Maps.newHashMap());
        map.get(type).put(location,maxShield);
    }

    //register
    public static void register() {
        MAP.put("dimension",Dimension);
        MAP.put("item",Item);
        MAP.put("achievements",Achievements);
        MAP.put("entity",Entity);
        MAP.put("armor",Armor);
        MAP.put("buff",Buff);
    }

}
