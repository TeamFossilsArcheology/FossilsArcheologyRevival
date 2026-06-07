package com.github.teamfossilsarcheology.fossil.compat.emi;

import com.github.teamfossilsarcheology.fossil.recipe.MultiOutputAndSlotsRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleComparators;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MultiOutputEmiRecipe<T extends MultiOutputAndSlotsRecipe> implements EmiRecipe {
    private static final int COLUMNS = 5;
    private static final int ROWS = 3;
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final List<SlotItem> slots;

    protected MultiOutputEmiRecipe(T recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getInput()));
        this.output = recipe.getWeightedOutputs().values().stream().map(EmiStack::of).toList();
        NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> newMap = new TreeMap<>();
        recipe.getWeightedOutputs().forEach((aDouble, itemStack) -> newMap.put(aDouble, Pair.of(itemStack, null)));
        this.slots = createOutputSlots(newMap);
    }

    protected MultiOutputEmiRecipe(ResourceLocation id, TagKey<Item> input, NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> map) {
        this.id = id;
        this.input = List.of(EmiIngredient.of(input));
        this.output = values(input);
        this.slots = createOutputSlots(map);
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 152;
    }

    @Override
    public int getDisplayHeight() {
        return 106;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        int centerX = 150 / 2;
        widgetHolder.addSlot(input.get(0), centerX - 7, 2);

        for (SlotItem slot : slots) {
            widgetHolder.add(new MultiOutputSlotWidget(slot.items, slot.x, slot.y)).recipeContext(this);
        }
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    public record WeightedItem(double probability, ItemStack stack, TagKey<Item> tagKey) {

    }

    public record SlotItem(EmiIngredient items, int x, int y) {

    }

    private static List<SlotItem> createOutputSlots(NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> map) {
        var iterator = map.entrySet().iterator();
        List<WeightedItem> sortedOutputs = new ArrayList<>();
        if (iterator.hasNext()) {
            double total = map.lastKey();
            var entry = iterator.next();
            double previous = entry.getKey();
            sortedOutputs.add(new WeightedItem(previous / total, entry.getValue().key(), entry.getValue().value()));
            while (iterator.hasNext()) {
                entry = iterator.next();
                sortedOutputs.add(new WeightedItem(((entry.getKey() - previous) / total), entry.getValue().key(), entry.getValue().value()));
                previous = entry.getKey();
            }
        }
        sortedOutputs.sort((o1, o2) -> DoubleComparators.OPPOSITE_COMPARATOR.compare(o1.probability, o2.probability));

        int numPerPage = COLUMNS * ROWS;
        Map<Integer, List<WeightedItem>> groupedOutputs = sortedOutputs.stream().collect(Collectors.groupingBy(weightedItem -> sortedOutputs.indexOf(weightedItem) % numPerPage));
        List<SlotItem> newOutputs = new ArrayList<>();
        for (int i = 0; i < groupedOutputs.size(); i++) {
            int x = i % COLUMNS;// index - (row * width)
            int y = i / COLUMNS;// int/int
            newOutputs.add(new SlotItem(toIngredient(groupedOutputs.get(i)), 8 + x * 30, 21 + y * 27));
        }
        return newOutputs;
    }

    @ExpectPlatform
    private static List<EmiStack> values(TagKey<Item> list) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    private static EmiIngredient toIngredient(List<WeightedItem> list) {
        throw new NotImplementedException();
    }

    private static class MultiOutputSlotWidget extends SlotWidget {

        public MultiOutputSlotWidget(EmiIngredient stack, int x, int y) {
            super(stack, x, y);
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
            super.render(poseStack, mouseX, mouseY, delta);
            Bounds bounds = getBounds();
            if (!getStack().getEmiStacks().isEmpty()) {
                int item = (int) (System.currentTimeMillis() / 1000 % getStack().getEmiStacks().size());
                EmiStack emiStack = getStack().getEmiStacks().get(item);
                renderProbability(poseStack, Minecraft.getInstance().font, emiStack.getChance() * 100, bounds.x() + 1, bounds.y() + 1);
            }
        }

        private void renderProbability(PoseStack poseStack, Font fr, double probability, int xPosition, int yPosition) {
            poseStack.pushPose();
            poseStack.translate(0.0, 0.0, 100 + 200.0F);
            String string = new DecimalFormat("#.#'%'").format(probability);
            MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            float xOffset = Math.min(11, fr.width(string) / 2f);
            fr.drawInBatch(string, (xPosition + 8 - xOffset), (yPosition + 16 + 1), 4210752, false,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            bufferSource.endBatch();
            poseStack.popPose();
        }
    }
}
