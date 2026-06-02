package com.github.teamfossilsarcheology.fossil.fabric.compat.emi;

import com.github.teamfossilsarcheology.fossil.recipe.AnalyzerRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.NavigableMap;
import java.util.TreeMap;

public class AnalyzerEmiRecipe extends MultiOutputEmiRecipe<AnalyzerRecipe> {
    public AnalyzerEmiRecipe(AnalyzerRecipe recipe) {
        super(recipe);
    }

    public AnalyzerEmiRecipe(ResourceLocation id, TagKey<Item> input, NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> map) {
        super(id, input, map);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FossilEmiPlugin.ANALYZER_CATEGORY;
    }

    public static class Builder {
        private final TagKey<Item> input;
        private final NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> map = new TreeMap<>();
        private double total;

        public Builder(TagKey<Item> input) {
            this.input = input;
        }

        public Builder addOutput(TagKey<Item> tagKey, double weight) {
            total += weight;
            map.put(total, Pair.of(null, tagKey));
            return this;
        }

        public Builder addOutput(ItemLike itemLike, double weight) {
            total += weight;
            map.put(total, Pair.of(new ItemStack(itemLike), null));
            return this;
        }

        public MultiOutputEmiRecipe<AnalyzerRecipe> build() {
            return new AnalyzerEmiRecipe(null, input, map);
        }
    }
}
