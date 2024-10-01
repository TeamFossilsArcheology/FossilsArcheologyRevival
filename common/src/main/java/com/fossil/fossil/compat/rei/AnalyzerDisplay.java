package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.AnalyzerRecipe;
import it.unimi.dsi.fastutil.doubles.DoubleComparators;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class AnalyzerDisplay implements Display {
    public static final CategoryIdentifier<AnalyzerDisplay> ID = CategoryIdentifier.of(new ResourceLocation(Fossil.MOD_ID, "analyzer"));
    //TODO: Sifter, DNA sources

    protected final EntryIngredient input;
    private final NavigableMap<Double, ItemStack> weightedOutputs;
    public final List<WeightedItem> outputs;
    protected final ResourceLocation location;

    public AnalyzerDisplay(AnalyzerRecipe recipe) {
        this(EntryIngredients.ofIngredient(recipe.getInput()), recipe.getWeightedOutputs(), recipe.getId());
    }

    public AnalyzerDisplay(EntryIngredient input, NavigableMap<Double, ItemStack> output, ResourceLocation location) {
        this.input = input;
        this.weightedOutputs = output;
        this.location = location;
        var iterator = weightedOutputs.entrySet().iterator();
        List<WeightedItem> outputs = new ArrayList<>();
        if (iterator.hasNext()) {
            double total = weightedOutputs.lastKey();
            Map.Entry<Double, ItemStack> entry = iterator.next();
            double previous = entry.getKey();
            outputs.add(new WeightedItem(previous / total * 100, EntryStacks.of(entry.getValue())));
            while (iterator.hasNext()) {
                entry = iterator.next();
                outputs.add(new WeightedItem(((entry.getKey() - previous) / total) * 100, EntryStacks.of(entry.getValue())));
                previous = entry.getKey();
            }
        }
        outputs.sort((o1, o2) -> DoubleComparators.OPPOSITE_COMPARATOR.compare(o1.probability, o2.probability));
        this.outputs = outputs;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        EntryIngredient.Builder stacks = EntryIngredient.builder();
        for (Map.Entry<Double, ItemStack> output : weightedOutputs.entrySet()) {
            stacks.add(EntryStacks.of(output.getValue()));
        }
        return Collections.singletonList(stacks.build());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }

    public static class Serializer implements DisplaySerializer<AnalyzerDisplay> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CompoundTag save(CompoundTag tag, AnalyzerDisplay display) {
            tag.put("Input", display.input.saveIngredient());
            ListTag weights = new ListTag();
            ListTag items = new ListTag();
            for (Map.Entry<Double, ItemStack> entry : display.weightedOutputs.entrySet()) {
                weights.add(DoubleTag.valueOf(entry.getKey()));
                weights.add(entry.getValue().save(new CompoundTag()));
            }
            tag.put("Weights", weights);
            tag.put("Items", items);
            tag.putString("Location", display.location.toString());
            return tag;
        }

        @Override
        public AnalyzerDisplay read(CompoundTag tag) {
            EntryIngredient input = EntryIngredient.read(tag.getList("Input", Tag.TAG_COMPOUND));
            ListTag weights = tag.getList("Weights", Tag.TAG_DOUBLE);
            ListTag items = tag.getList("Items", Tag.TAG_DOUBLE);
            NavigableMap<Double, ItemStack> weighted = new TreeMap<>();
            double total = 0;
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = ItemStack.of(items.getCompound(i));
                if (item.isEmpty()) continue;
                total += weights.getDouble(i);
                weighted.put(total, item);
            }
            ResourceLocation location = new ResourceLocation(tag.getString("Location"));
            return new AnalyzerDisplay(input, weighted, location);
        }
    }

    public record WeightedItem(double probability, EntryStack<?> item) {

    }
}
