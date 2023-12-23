package cn.teampancake.theaurorian.common.data.pack;

import com.google.common.collect.Lists;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.List;
import java.util.Map;

public class RuneGameLoader extends SimpleJsonResourceReloadListener {
    public static final List<int[][][]> RUNE_GAME = Lists.newArrayList();
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    public RuneGameLoader() {
        super(GSON, "rune_game");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager manager, ProfilerFiller filler) {
        RUNE_GAME.clear();
        jsonElementMap.forEach((resourceLocation, jsonElement) -> {
            RUNE_GAME.add(GSON.fromJson(jsonElement, int[][][].class));
        });
    }

    public static int[][][] getRandomLevel() {
        return RUNE_GAME.get((int) (Math.random() * RUNE_GAME.size()));
    }
}
