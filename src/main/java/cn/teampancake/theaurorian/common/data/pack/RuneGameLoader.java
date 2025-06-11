package cn.teampancake.theaurorian.common.data.pack;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RuneGameLoader extends SimpleJsonResourceReloadListener {

    public static final List<int[][][]> RUNE_GAME = Lists.newArrayList();
    public static final List<int[][][]> RANDOM_SHAPE_PATTERNS = Lists.newArrayList();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    // 存储所有可用的符文游戏模式名称
    private static final List<String> PATTERN_NAMES = new ArrayList<>();

    public RuneGameLoader() {
        super(GSON, "rune_game");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager manager, ProfilerFiller filler) {
        RUNE_GAME.clear();
        RANDOM_SHAPE_PATTERNS.clear();
        PATTERN_NAMES.clear();
        
        jsonElementMap.forEach((resourceLocation, jsonElement) -> {
            int[][][] pattern = GSON.fromJson(jsonElement, int[][][].class);
            String path = resourceLocation.getPath();
            
            // 将随机形状模式添加到专门的列表中
            if (path.startsWith("random_shape")) {
                RANDOM_SHAPE_PATTERNS.add(pattern);
            }
            
            // 所有模式都添加到主列表中
            RUNE_GAME.add(pattern);
            PATTERN_NAMES.add(path);
        });
    }

    public static int[][][] getRandomLevel() {
        if (!RANDOM_SHAPE_PATTERNS.isEmpty()) {
            // 优先使用随机形状模式
            RandomSource random = RandomSource.create();
            int index = random.nextInt(RANDOM_SHAPE_PATTERNS.size());
            return RANDOM_SHAPE_PATTERNS.get(index);
        } else if (!RUNE_GAME.isEmpty()) {
            // 如果没有随机形状模式，则使用任何可用模式
            RandomSource random = RandomSource.create();
            int index = random.nextInt(RUNE_GAME.size());
            return RUNE_GAME.get(index);
        } else {
            // 如果没有加载任何符文游戏模式，返回一个简单的默认模式
            return createDefaultLevel();
        }
    }
    
    // 创建一个简单的默认符文游戏模式，以防没有加载任何JSON文件
    private static int[][][] createDefaultLevel() {
        return new int[][][] {
            {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
            }
        };
    }
    
    // 获取所有可用的符文游戏模式名称，用于调试
    public static List<String> getPatternNames() {
        return PATTERN_NAMES;
    }
    
    // 获取随机形状模式名称
    public static List<String> getRandomShapePatternNames() {
        return PATTERN_NAMES.stream()
            .filter(name -> name.startsWith("random_shape"))
            .collect(Collectors.toList());
    }
}