package cn.teampancake.theaurorian.common.command;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber.NightPhase;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Arrays;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class TACommands {

    private static final SimpleCommandExceptionType NOT_IN_AURORIAN_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.theaurorian.night_phase.not_in_aurorian"));
    
    private static final SimpleCommandExceptionType INVALID_PHASE_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.theaurorian.night_phase.invalid_phase"));
    
    private static final SuggestionProvider<CommandSourceStack> NIGHT_PHASE_SUGGESTIONS = 
            (context, builder) -> SharedSuggestionProvider.suggest(Arrays.asList(NightPhase.getAllNames()), builder);

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("aurorian")
                .then(Commands.literal("night")
                        .requires(source -> source.hasPermission(2))
                        .executes(TACommands::showCurrentNightPhase)
                        .then(Commands.argument("phase", StringArgumentType.word())
                                .suggests(NIGHT_PHASE_SUGGESTIONS)
                                .executes(TACommands::setNightPhase)
                        )));
    }
    
    private static int setNightPhase(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        String phaseName = StringArgumentType.getString(context, "phase");
        
        NightPhase phase = NightPhase.fromName(phaseName);
        if (phase == NightPhase.CUSTOM) {
            throw INVALID_PHASE_EXCEPTION.create();
        }
        
        boolean success = LevelEventSubscriber.setNightPhase(phase, level);
        if (!success) {
            throw NOT_IN_AURORIAN_EXCEPTION.create();
        }
        
        return Command.SINGLE_SUCCESS;
    }
    
    private static int showCurrentNightPhase(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String currentPhaseName = NightPhase.getDisplayName(LevelEventSubscriber.phaseCode);
        source.sendSuccess(() -> Component.translatable("commands.theaurorian.night_phase.current", currentPhaseName), true);
        
        return Command.SINGLE_SUCCESS;
    }
} 