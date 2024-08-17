package cn.teampancake.theaurorian.common.data.pack;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MaxShieldLoader extends SimpleJsonResourceReloadListener {

    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static final Map<String, Map<ResourceLocation, Map<ResourceLocation, Integer>>> MAP = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> DIMENSION = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> ITEM = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> ACHIEVEMENTS = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> ENTITY = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> ARMOR = Maps.newHashMap();
    public static final Map<ResourceLocation, Map<ResourceLocation, Integer>> BUFF = Maps.newHashMap();

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
        map.putIfAbsent(type, Maps.newHashMap());
        map.get(type).put(location, maxShield);
    }

    public static void register() {
        MAP.put("dimension", DIMENSION);
        MAP.put("item", ITEM);
        MAP.put("achievements", ACHIEVEMENTS);
        MAP.put("entity", ENTITY);
        MAP.put("armor", ARMOR);
        MAP.put("buff", BUFF);
    }

}