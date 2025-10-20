package cn.teampancake.theaurorian.common.command.argument;

import cn.teampancake.theaurorian.common.level.data.sky_color.BaseSkyColor;
import cn.teampancake.theaurorian.common.registry.TASkyColors;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SkyColorArgument implements ArgumentType<Holder<BaseSkyColor>> {

    private final SkyColorParser parser;

    public SkyColorArgument(CommandBuildContext context) {
        this.parser = new SkyColorParser(context);
    }

    @Override
    public Holder<BaseSkyColor> parse(StringReader reader) throws CommandSyntaxException {
        return this.parser.parse(reader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return this.parser.fillSuggestions(builder);
    }

    @Override
    public Collection<String> getExamples() {
        DeferredHolder<BaseSkyColor, BaseSkyColor> holder = TASkyColors.COMBAT;
        return Arrays.asList(holder.getId().getPath(), holder.toString());
    }

}