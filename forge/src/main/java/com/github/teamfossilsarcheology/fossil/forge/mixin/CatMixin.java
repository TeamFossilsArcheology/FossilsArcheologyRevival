package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;

@Mixin(Cat.class)
public class CatMixin {
    @Unique
    private final Ingredient fossil$moreTemptIngredients = Ingredient.of(PrehistoricEntityInfo.ALLIGATOR_GAR.foodItem,
            PrehistoricEntityInfo.COELACANTH.foodItem, PrehistoricEntityInfo.STURGEON.foodItem);

    @ModifyArg(method = "registerGoals", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Cat$CatTemptGoal;<init>(Lnet/minecraft/world/entity/animal/Cat;DLnet/minecraft/world/item/crafting/Ingredient;Z)V"))
    private Ingredient addCatTempFood(Ingredient items) {
        ItemStack[] newItems = Arrays.copyOf(items.getItems(), items.getItems().length + 3);
        newItems[items.getItems().length] = new ItemStack(PrehistoricEntityInfo.ALLIGATOR_GAR.foodItem);
        newItems[items.getItems().length + 1] = new ItemStack(PrehistoricEntityInfo.COELACANTH.foodItem);
        newItems[items.getItems().length + 2] = new ItemStack(PrehistoricEntityInfo.STURGEON.foodItem);
        return Ingredient.of(newItems);
    }

    @WrapMethod(method = "isFood")
    private boolean testCustomFood(ItemStack stack, Operation<Boolean> original) {
        if (fossil$moreTemptIngredients.test(stack)) {
            return true;
        }
        return original.call(stack);
    }
}
