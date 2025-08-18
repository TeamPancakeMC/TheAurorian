package cn.teampancake.theaurorian.common.stats;

import cn.teampancake.theaurorian.client.gui.screens.RuneGameScreen;
import net.minecraft.stats.StatFormatter;

public interface TAStatFormatter extends StatFormatter {

    StatFormatter STANDARD_TIME_FOR_RUNE_GAME = value -> RuneGameScreen.formatElapsedTime(value).getString();

}