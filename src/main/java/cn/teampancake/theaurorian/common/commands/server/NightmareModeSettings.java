package cn.teampancake.theaurorian.common.commands.server;

import cn.teampancake.theaurorian.client.gui.NightmareModeSettingsScreen;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAGameRules;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

public class NightmareModeSettings {

    public static final SimpleCommandExceptionType ERROR_NOT_AURORIAN_DIMENSION = new SimpleCommandExceptionType(
            Component.translatable("commands.nightmare_mode_settings.failed.dimension"));
    public static final SimpleCommandExceptionType ERROR_NOT_NIGHTMARE_MODE = new SimpleCommandExceptionType(
            Component.translatable("commands.nightmare_mode_settings.failed.mode"));

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("nightmare_mode_settings")
                .requires(source -> source.hasPermission(2))
                .executes(context -> openSettingsGui(context.getSource()));
    }

    private static int openSettingsGui(CommandSourceStack source) throws CommandSyntaxException {
        ServerLevel serverLevel = source.getLevel();
        GameRules gameRules = serverLevel.getGameRules();
        if (serverLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
            if (gameRules.getBoolean(TAGameRules.RULE_ENABLE_NIGHTMARE_MODE)) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.screen == null) {
                    minecraft.setScreen(new NightmareModeSettingsScreen());
                }
            } else {
                throw ERROR_NOT_NIGHTMARE_MODE.create();
            }
        } else {
            throw ERROR_NOT_AURORIAN_DIMENSION.create();
        }

        return 1;
    }

}