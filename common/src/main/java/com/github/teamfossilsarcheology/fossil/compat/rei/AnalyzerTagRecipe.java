package com.github.teamfossilsarcheology.fossil.compat.rei;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Used to visualize analyzer recipes added in {@link com.github.teamfossilsarcheology.fossil.recipe.ModRecipes}
 */
public record AnalyzerTagRecipe(TagKey<Item> input, NavigableMap<Double, Ingredient> weightedOutputs) {

    public static class Builder {
        private final TagKey<Item> input;
        private final NavigableMap<Double, Ingredient> map = new TreeMap<>();
        private double total;

        public Builder(TagKey<Item> input) {
            this.input = input;
        }

        public Builder addOutput(TagKey<Item> tagKey, double weight) {
            total += weight;
            map.put(total, Ingredient.of(tagKey));
            return this;
        }

        public Builder addOutput(ItemLike itemLike, double weight) {
            total += weight;
            map.put(total, Ingredient.of(itemLike));
            return this;
        }

        public AnalyzerTagRecipe build() {
            return new AnalyzerTagRecipe(input, map);
        }
    }
}
