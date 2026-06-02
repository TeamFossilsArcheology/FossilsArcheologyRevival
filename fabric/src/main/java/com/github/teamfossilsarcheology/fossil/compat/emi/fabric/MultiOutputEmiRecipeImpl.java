package com.github.teamfossilsarcheology.fossil.compat.emi.fabric;

import com.github.teamfossilsarcheology.fossil.compat.emi.MultiOutputEmiRecipe;
import dev.emi.emi.api.stack.*;
import dev.emi.emi.registry.EmiTags;
import dev.emi.emi.runtime.EmiTagKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Access to some internal code
 * @see MultiOutputEmiRecipe
 */
public class MultiOutputEmiRecipeImpl {
    private static EmiIngredient toIngredient(List<MultiOutputEmiRecipe.WeightedItem> list) {
        if (list.isEmpty()) {
            return EmiStack.EMPTY;
        } else if (list.size() == 1) {
            MultiOutputEmiRecipe.WeightedItem weightedItem = list.get(0);
            if (weightedItem.tagKey() == null) {
                if (weightedItem.stack().isEmpty()) {
                    //Air is a valid output
                    return new ItemEmiStack(weightedItem.stack(), 1).setChance((float) weightedItem.probability());
                }
                return EmiStack.of(weightedItem.stack()).setChance((float) weightedItem.probability());
            } else {
                //For custom recipe display. Probability is the probability of getting anything from the tag
                return new ListEmiIngredient(EmiTagKey.of(weightedItem.tagKey()).stream().map(itemHolder -> EmiStack.of(new ItemStack(itemHolder)).setChance((float) (weightedItem.probability()))).toList(), 1);
            }
        } else {
            return new ListEmiIngredient(toStack(list), 1);
        }
    }

    private static List<EmiIngredient> toStack(List<MultiOutputEmiRecipe.WeightedItem> list) {
        return list.stream().map(weightedItem -> {
            if (weightedItem.tagKey() == null) {
                return EmiStack.of(weightedItem.stack()).setChance((float) weightedItem.probability());
            } else {
                return new TagEmiIngredient(weightedItem.tagKey(), 1).setChance((float) weightedItem.probability());
            }
        }).toList();
    }

    public static List<EmiStack> values(TagKey<Item> key) {
        return EmiTags.getRawValues(EmiTagKey.of(key));
    }
}
