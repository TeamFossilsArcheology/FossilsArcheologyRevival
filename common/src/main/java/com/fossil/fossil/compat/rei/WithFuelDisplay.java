package com.fossil.fossil.compat.rei;

import com.fossil.fossil.recipe.WithFuelRecipe;
import com.google.common.collect.Lists;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.Collections;
import java.util.List;


public abstract class WithFuelDisplay implements Display {
    protected final EntryIngredient input;
    protected final EntryIngredient fuel;
    protected final EntryStack<?> output;


    protected WithFuelDisplay(WithFuelRecipe recipe) {
        this(EntryIngredients.ofIngredient(recipe.getInput()), EntryIngredients.ofIngredient(recipe.getFuel()),
                EntryStacks.of(recipe.getResultItem()));
    }

    protected WithFuelDisplay(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output) {
        this.input = input;
        this.fuel = fuel;
        this.output = output;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return Lists.newArrayList(input, fuel);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(EntryIngredient.of(output));
    }

    public static class Serializer<T extends WithFuelDisplay> implements DisplaySerializer<T> {
        protected final Constructor<T> constructor;

        protected Serializer(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public CompoundTag save(CompoundTag tag, T display) {
            tag.put("Input", display.input.saveIngredient());
            tag.put("Fuel", display.fuel.saveIngredient());
            tag.put("Output", display.output.saveStack());
            return tag;
        }

        @Override
        public T read(CompoundTag tag) {
            EntryIngredient input = EntryIngredient.read(tag.getList("Input", Tag.TAG_COMPOUND));
            EntryIngredient fuel = EntryIngredient.read(tag.getList("Fuel", Tag.TAG_COMPOUND));
            EntryStack<?> output = EntryStack.read(tag.getCompound("Output"));
            return constructor.construct(input, fuel, output);
        }

        @FunctionalInterface
        public interface Constructor<R> {
            R construct(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output);
        }
    }
}
