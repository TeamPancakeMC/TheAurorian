package cn.teampancake.theaurorian.common.commands;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.commands.server.NightmareModeSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder
                .<CommandSourceStack> literal(AurorianMod.MOD_ID)
                .then(NightmareModeSettings.register())
                .executes(ctx -> 0));
    }

}