package com.github.teamfossilsarcheology.fossil.compat.emi;

import com.github.teamfossilsarcheology.fossil.recipe.SifterRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.NavigableMap;
import java.util.TreeMap;

public class SifterEmiRecipe extends MultiOutputEmiRecipe<SifterRecipe> {
    public SifterEmiRecipe(SifterRecipe recipe) {
        super(recipe);
    }

    public SifterEmiRecipe(ResourceLocation id, TagKey<Item> input, NavigableMap<Double, Pair<ItemStack, TagKey<Item>>> map) {
        super(id, input, map);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FossilEmiPlugin.SIFTER_CATEGORY;
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

        public MultiOutputEmiRecipe<SifterRecipe> build() {
            return new SifterEmiRecipe(null, input, map);
        }
    }
}
