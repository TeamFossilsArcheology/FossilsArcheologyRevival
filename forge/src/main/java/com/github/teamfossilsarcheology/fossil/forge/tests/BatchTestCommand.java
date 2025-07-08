package com.github.teamfossilsarcheology.fossil.forge.tests;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BatchTestCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("batch_test")
                .then(Commands.argument("batchName", BatchArgument.batch())
                        .executes(context -> BatchTestCommand.runBatch(context.getSource(), BatchArgument.getBatch(context, "batchName"))));
    }

    private static int runBatch(CommandSourceStack source, String batchName) {
        Collection<TestFunction> collection = GameTestRegistry.getAllTestFunctions();
        Collection<GameTestBatch> batched = groupTestsIntoBatches(collection, batchName);
        say(source, "Running all " + batched.size() + " tests in batch " + batchName + "...");
        GameTestRegistry.forgetFailedTests();
        BlockPos blockPos = BlockPos.containing(source.getPosition());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), source.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, blockPos).getY(), blockPos.getZ() + 3);
        Collection<GameTestInfo> collection2 = GameTestRunner.runTestBatches(batched, blockPos2, Rotation.NONE, source.getLevel(), GameTestTicker.SINGLETON, 8);
        MultipleTestTracker multipleTestTracker = new MultipleTestTracker(collection2);
        multipleTestTracker.addListener(new TestSummaryDisplayer(source.getLevel(), multipleTestTracker));
        multipleTestTracker.addFailureListener(gameTestInfo -> GameTestRegistry.rememberFailedTest(gameTestInfo.getTestFunction()));
        return 1;
    }
    private static Collection<GameTestBatch> groupTestsIntoBatches(Collection<TestFunction> testFunctions, String batchName) {
        List<TestFunction> batched = testFunctions.stream().filter(testFunction -> testFunction.getBatchName().equals(batchName)).toList();
        Map<String, List<TestFunction>> a = batched.stream().collect(Collectors.groupingBy(TestFunction::getTestName));

        return a.entrySet().stream().flatMap(entry -> {
            String string = entry.getKey();
            Consumer<ServerLevel> consumer = GameTestRegistry.getBeforeBatchFunction(string);
            Consumer<ServerLevel> consumer2 = GameTestRegistry.getAfterBatchFunction(string);
            MutableInt mutableInt = new MutableInt();
            Collection<TestFunction> collection = entry.getValue();
            return Streams.stream(Iterables.partition(collection, 100)).map((list) -> {
                return new GameTestBatch(string + ":" + mutableInt.incrementAndGet(), ImmutableList.copyOf(list), consumer, consumer2);
            });
        }).collect(ImmutableList.toImmutableList());
    }

    private static int runAllTests(CommandSourceStack source, int rotationSteps, int testsPerRow) {
        GameTestRunner.clearMarkers(source.getLevel());
        Collection<TestFunction> collection = GameTestRegistry.getAllTestFunctions();
        say(source, "Running all " + collection.size() + " tests...");
        GameTestRegistry.forgetFailedTests();
        Map<String, List<TestFunction>> batchedRuns = collection.stream().collect(Collectors.groupingBy(TestFunction::getBatchName));
        runTests(source, batchedRuns, rotationSteps, testsPerRow);
        return 1;
    }

    private static void runTests(CommandSourceStack source, Map<String, List<TestFunction>> batchedRuns, int rotationSteps, int testsPerRow) {
        /*for (Map.Entry<String, Collection<TestFunction>> entry : batchedRuns.entrySet()) {

        }
        BlockPos blockPos = new BlockPos(source.getPosition());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), source.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, blockPos).getY(), blockPos.getZ() + 3);
        ServerLevel serverLevel = source.getLevel();
        Rotation rotation = StructureUtils.getRotationForRotationSteps(rotationSteps);
        Collection<GameTestInfo> collection2 = GameTestRunner.runTests(collection, blockPos2, rotation, serverLevel, GameTestTicker.SINGLETON, testsPerRow);
        MultipleTestTracker multipleTestTracker = new MultipleTestTracker(collection2);
        multipleTestTracker.addListener(new TestCommand.TestSummaryDisplayer(serverLevel, multipleTestTracker));
        multipleTestTracker.addFailureListener(gameTestInfo -> GameTestRegistry.rememberFailedTest(gameTestInfo.getTestFunction()));*/
    }

    private static void say(CommandSourceStack source, String message) {
        source.sendSuccess(() -> Component.literal(message), false);
    }

    private static void say(ServerLevel serverLevel, String message, ChatFormatting formatting) {
        serverLevel.getPlayers(arg -> true).forEach(arg2 -> arg2.sendSystemMessage(Component.literal(formatting + message)));
    }

    static void showTestSummaryIfAllDone(ServerLevel serverLevel, MultipleTestTracker tracker) {
        if (tracker.isDone()) {
            say(serverLevel, "GameTest done! " + tracker.getTotalCount() + " tests were run", ChatFormatting.WHITE);
            if (tracker.hasFailedRequired()) {
                say(serverLevel, tracker.getFailedRequiredCount() + " required tests failed :(", ChatFormatting.RED);
            } else {
                say(serverLevel, "All required tests passed :)", ChatFormatting.GREEN);
            }
            if (tracker.hasFailedOptional()) {
                say(serverLevel, tracker.getFailedOptionalCount() + " optional tests failed", ChatFormatting.GRAY);
            }
        }
    }

    private static class TestSummaryDisplayer implements GameTestListener {
        private final ServerLevel level;
        private final MultipleTestTracker tracker;

        public TestSummaryDisplayer(ServerLevel arg, MultipleTestTracker arg2) {
            this.level = arg;
            this.tracker = arg2;
        }

        public void testStructureLoaded(GameTestInfo testInfo) {
        }

        public void testPassed(GameTestInfo testInfo) {
            showTestSummaryIfAllDone(this.level, this.tracker);
        }

        public void testFailed(GameTestInfo testInfo) {
            showTestSummaryIfAllDone(this.level, this.tracker);
        }
    }
}
