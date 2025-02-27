package com.github.teamfossilsarcheology.fossil.forge.tests;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BatchArgument implements ArgumentType<String> {
    public static BatchArgument batch() {
        return new BatchArgument();
    }

    public static String getBatch(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, String.class);
    }
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String string = reader.readUnquotedString();
        Optional<TestFunction> optional = GameTestRegistry.getAllTestFunctions().stream().filter(arg -> arg.getBatchName().equalsIgnoreCase(string)).findFirst();
        if (optional.isPresent()) {
            return string;
        } else {
            Message message = Component.literal("No such batch: " + string);
            throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        Stream<String> stream = GameTestRegistry.getAllTestFunctions().stream().map(TestFunction::getBatchName);
        return SharedSuggestionProvider.suggest(stream, suggestionsBuilder);
    }
}
