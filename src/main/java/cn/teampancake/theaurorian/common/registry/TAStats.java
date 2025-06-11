package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.stats.TAStatFormatter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class TAStats {

    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS = DeferredRegister.create(Registries.CUSTOM_STAT, TheAurorian.MOD_ID);
    private static final List<Runnable> STAT_SETUP = new ArrayList<>();

    public static final DeferredHolder<ResourceLocation, ResourceLocation> RUNE_GAME_WIN_COUNT = makeCustomStat("rune_game_win_count", StatFormatter.DEFAULT);
    public static final DeferredHolder<ResourceLocation, ResourceLocation> RUNE_GAME_PLAY_COUNT = makeCustomStat("rune_game_play_count", StatFormatter.DEFAULT);
    public static final DeferredHolder<ResourceLocation, ResourceLocation> TOTAL_RUNE_GAME_TIME = makeCustomStat("total_rune_game_time", TAStatFormatter.STANDARD_TIME_FOR_RUNE_GAME);
    public static final DeferredHolder<ResourceLocation, ResourceLocation> RUNE_GAME_BEST_TIME = makeCustomStat("rune_game_best_time", TAStatFormatter.STANDARD_TIME_FOR_RUNE_GAME);
    public static final DeferredHolder<ResourceLocation, ResourceLocation> RUNE_GAME_MOVE_COUNT = makeCustomStat("rune_game_move_count", StatFormatter.DEFAULT);
    public static final DeferredHolder<ResourceLocation, ResourceLocation> RUNE_GAME_ELIMINATION_COUNT = makeCustomStat("rune_game_elimination_count", StatFormatter.DEFAULT);

    private static DeferredHolder<ResourceLocation, ResourceLocation> makeCustomStat(String key, StatFormatter formatter) {
        ResourceLocation value = TheAurorian.prefix(key);
        STAT_SETUP.add(() -> Stats.CUSTOM.get(value, formatter));
        return CUSTOM_STATS.register(key, () -> value);
    }

    public static void init() {
        STAT_SETUP.forEach(Runnable::run);
    }

}