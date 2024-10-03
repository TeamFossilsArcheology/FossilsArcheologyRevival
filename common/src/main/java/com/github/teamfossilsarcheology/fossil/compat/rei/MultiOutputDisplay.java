package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.github.teamfossilsarcheology.fossil.recipe.MultiOutputAndSlotsRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.doubles.DoubleComparators;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class MultiOutputDisplay implements Display {
    protected final EntryIngredient input;
    protected final NavigableMap<Double, EntryIngredient> weightedOutputs;
    public final List<WeightedItem> outputs;
    protected final @Nullable ResourceLocation location;


    protected MultiOutputDisplay(MultiOutputAndSlotsRecipe recipe) {
        this(EntryIngredients.ofIngredient(recipe.getInput()), map(recipe.getWeightedOutputs()), recipe.getId());
    }

    private static NavigableMap<Double, EntryIngredient> map(NavigableMap<Double, ItemStack> output) {
        NavigableMap<Double, EntryIngredient> newMap = new TreeMap<>();
        output.forEach((aDouble, itemStack) -> newMap.put(aDouble, EntryIngredients.of(itemStack)));
        return newMap;
    }

    protected MultiOutputDisplay(EntryIngredient input, NavigableMap<Double, EntryIngredient> output, @Nullable ResourceLocation location) {
        this.input = input;
        this.weightedOutputs = output;
        this.location = location;
        var iterator = weightedOutputs.entrySet().iterator();
        List<WeightedItem> newOutputs = new ArrayList<>();
        if (iterator.hasNext()) {
            double total = weightedOutputs.lastKey();
            Map.Entry<Double, EntryIngredient> entry = iterator.next();
            double previous = entry.getKey();
            newOutputs.add(new WeightedItem(previous / total * 100, entry.getValue()));
            while (iterator.hasNext()) {
                entry = iterator.next();
                newOutputs.add(new WeightedItem(((entry.getKey() - previous) / total) * 100, entry.getValue()));
                previous = entry.getKey();
            }
        }
        newOutputs.sort((o1, o2) -> DoubleComparators.OPPOSITE_COMPARATOR.compare(o1.probability, o2.probability));
        this.outputs = newOutputs;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Lists.newArrayList(weightedOutputs.values());
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.ofNullable(location);
    }

    public static class Serializer<T extends MultiOutputDisplay> implements DisplaySerializer<T> {
        protected final Serializer.Constructor<T> constructor;

        protected Serializer(Serializer.Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public CompoundTag save(CompoundTag tag, T display) {
            tag.put("Input", display.input.saveIngredient());
            ListTag weights = new ListTag();
            ListTag items = new ListTag();
            for (Map.Entry<Double, EntryIngredient> entry : display.weightedOutputs.entrySet()) {
                weights.add(DoubleTag.valueOf(entry.getKey()));
                weights.add(entry.getValue().saveIngredient());
            }
            tag.put("Weights", weights);
            tag.put("Items", items);
            tag.putString("Location", display.location.toString());
            return tag;
        }

        @Override
        public T read(CompoundTag tag) {
            EntryIngredient input = EntryIngredient.read(tag.getList("Input", Tag.TAG_COMPOUND));
            ListTag weights = tag.getList("Weights", Tag.TAG_DOUBLE);
            ListTag items = tag.getList("Items", Tag.TAG_LIST);
            NavigableMap<Double, EntryIngredient> weighted = new TreeMap<>();
            double total = 0;
            for (int i = 0; i < items.size(); i++) {
                EntryIngredient item = EntryIngredient.read(items.getList(i));
                if (item.isEmpty()) continue;
                total += weights.getDouble(i);
                weighted.put(total, item);
            }
            ResourceLocation location = new ResourceLocation(tag.getString("Location"));
            return constructor.construct(input, weighted, location);
        }

        @FunctionalInterface
        public interface Constructor<R> {
            R construct(EntryIngredient input, NavigableMap<Double, EntryIngredient> output, ResourceLocation location);
        }
    }

    public record WeightedItem(double probability, EntryIngredient item) {

    }
}
