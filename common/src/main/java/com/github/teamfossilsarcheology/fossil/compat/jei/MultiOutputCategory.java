package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.recipe.MultiOutputAndSlotsRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.doubles.DoubleComparators;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MultiOutputCategory<T extends MultiOutputAndSlotsRecipe> implements IRecipeCategory<T> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/multi_output.png");
    private static final DecimalFormat FORMAT = new DecimalFormat("#.#'%'");
    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<T, List<SlotItem>> probabilities = CacheBuilder.newBuilder()
            .build(new CacheLoader<>() {
                private static final int COLUMNS = 5;
                private static final int ROWS = 3;

                @Override
                public @NotNull List<SlotItem> load(@NotNull T recipe) {//TODO: Cycle duration
                    var iterator = recipe.getWeightedOutputs().entrySet().iterator();
                    List<WeightedItem> sortedOutputs = new ArrayList<>();
                    if (iterator.hasNext()) {
                        double total = recipe.getWeightedOutputs().lastKey();
                        var entry = iterator.next();
                        double previous = entry.getKey();
                        sortedOutputs.add(new WeightedItem(previous / total * 100, entry.getValue()));
                        while (iterator.hasNext()) {
                            entry = iterator.next();
                            sortedOutputs.add(new WeightedItem(((entry.getKey() - previous) / total) * 100, entry.getValue()));
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
                        newOutputs.add(new SlotItem(groupedOutputs.get(i), 8 + x * 30, 21 + y * 25));
                    }
                    return newOutputs;
                }
            });

    protected MultiOutputCategory(IGuiHelper guiHelper, ItemLike iconItem) {
        background = guiHelper.createDrawable(TEXTURE, 0, 0, 152, 106);
        icon = guiHelper.createDrawableItemStack(new ItemStack(iconItem));
    }

    @Override
    public void draw(@NotNull T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        List<SlotItem> slotItems = probabilities.getUnchecked(recipe);
        List<IRecipeSlotView> slots = recipeSlotsView.getSlotViews(RecipeIngredientRole.OUTPUT);
        for (int i = 0; i < slots.size(); i++) {
            Optional<ItemStack> currentStack = slots.get(i).getDisplayedItemStack();
            if (currentStack.isEmpty()) {
                break;
            }
            for (WeightedItem item : slotItems.get(i).items) {
                if (ItemStack.isSameItem(currentStack.get(), item.stack)) {
                    renderProbability(guiGraphics, Minecraft.getInstance().font, item.probability, slotItems.get(i).x, slotItems.get(i).y);
                    break;
                }
            }
        }
    }

    private void renderProbability(GuiGraphics guiGraphics, Font fr, double percentage, int xPosition, int yPosition) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0, 0.0, 100 + 200.0F);
        String string = FORMAT.format(percentage);
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        float xOffset = Math.min(11, fr.width(string) / 2f);
        fr.drawInBatch(string, (xPosition + 8 - xOffset), (yPosition + 16 + 1), 16777215, true,
                guiGraphics.pose().last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        bufferSource.endBatch();
        guiGraphics.pose().popPose();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        int centerX = 150 / 2;
        builder.addSlot(RecipeIngredientRole.INPUT, centerX - 7, 2).addIngredients(recipe.getInput());
        List<SlotItem> outputs = probabilities.getUnchecked(recipe);
        for (SlotItem output : outputs) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, output.x, output.y + 1).addIngredients(Ingredient.of(output.items.stream().map(weightedItem -> weightedItem.stack)));
        }
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    public record WeightedItem(double probability, ItemStack stack) {

    }

    public record SlotItem(List<WeightedItem> items, int x, int y) {

    }
}
