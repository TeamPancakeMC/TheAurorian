package cn.teampancake.theaurorian.common.command.argument;

import cn.teampancake.theaurorian.common.level.data.sky_color.BaseSkyColor;
import cn.teampancake.theaurorian.common.registry.TASkyColors;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SkyColorParser {

    private static final DynamicCommandExceptionType ERROR_INVALID_ID = new DynamicCommandExceptionType(
            object -> Component.translatableEscape("argument.id.invalid", object));
    private final HolderLookup.RegistryLookup<BaseSkyColor> skyColors;

    public SkyColorParser(HolderLookup.Provider registries) {
        this.skyColors = registries.lookupOrThrow(TASkyColors.KEY);
    }

    public Holder<BaseSkyColor> parse(StringReader reader) throws CommandSyntaxException {
        MutableObject<Holder<BaseSkyColor>> object = new MutableObject<>();
        this.parse(reader, new SimpleVisitor(object));
        return Objects.requireNonNull(object.getValue());
    }

    public void parse(StringReader reader, Visitor visitor) throws CommandSyntaxException {
        try {
            new State(reader, visitor).parse();
        } catch (CommandSyntaxException exception) {
            reader.setCursor(reader.getCursor());
            throw exception;
        }
    }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder builder) {
        StringReader stringReader = new StringReader(builder.getInput());
        stringReader.setCursor(builder.getStart());
        SuggestionsVisitor visitor = new SuggestionsVisitor();
        try {
            new State(stringReader, visitor).parse();
        } catch (CommandSyntaxException ignored) {}
        return visitor.resolveSuggestions(builder, stringReader);
    }

    class State {

        private final StringReader reader;
        private final Visitor visitor;

        State(StringReader reader, Visitor visitor) {
            this.reader = reader;
            this.visitor = visitor;
        }

        public void parse() throws CommandSyntaxException {
            this.visitor.visitSuggestions(this::suggestSkyColor);
            ResourceLocation location = ResourceLocation.read(this.reader);
            ResourceKey<BaseSkyColor> resourceKey = ResourceKey.create(TASkyColors.KEY, location);
            this.visitor.visitSkyColor(skyColors.get(resourceKey).orElseThrow(() -> {
                this.reader.setCursor(this.reader.getCursor());
                return ERROR_INVALID_ID.createWithContext(this.reader, location);
            }));
        }

        private CompletableFuture<Suggestions> suggestSkyColor(SuggestionsBuilder builder) {
            return SharedSuggestionProvider.suggestResource(skyColors.listElementIds().map(ResourceKey::location), builder);
        }

    }

    static class SimpleVisitor implements Visitor {

        private final MutableObject<Holder<BaseSkyColor>> object;

        SimpleVisitor(MutableObject<Holder<BaseSkyColor>> object) {
            this.object = object;
        }

        @Override
        public void visitSkyColor(Holder<BaseSkyColor> skyColor) {
            this.object.setValue(skyColor);
        }

    }

    static class SuggestionsVisitor implements Visitor {

        private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestions = SuggestionsBuilder::buildFuture;

        @Override
        public void visitSuggestions(Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestions) {
            this.suggestions = suggestions;
        }

        public CompletableFuture<Suggestions> resolveSuggestions(SuggestionsBuilder builder, StringReader reader) {
            return this.suggestions.apply(builder.createOffset(reader.getCursor()));
        }

    }

    public interface Visitor {

        default void visitSkyColor(Holder<BaseSkyColor> skyColor) {}

        default void visitSuggestions(Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestions) {}

    }

}