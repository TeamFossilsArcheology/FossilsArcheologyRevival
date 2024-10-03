package com.github.teamfossilsarcheology.fossil.loot;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

/**
 * Changes the amount of food a {@link Prehistoric} drops based on age
 */
public class CustomizeToDinoFunction extends LootItemConditionalFunction {
    private final LootContext.EntityTarget entityTarget;

    protected CustomizeToDinoFunction(LootItemCondition[] lootItemConditions, LootContext.EntityTarget entityTarget) {
        super(lootItemConditions);
        this.entityTarget = entityTarget;
    }

    public static LootItemConditionalFunction.Builder<?> apply(LootContext.EntityTarget target) {
        return simpleBuilder(lootItemConditions -> new CustomizeToDinoFunction(lootItemConditions, target));
    }

    @Override
    protected @NotNull ItemStack run(ItemStack stack, LootContext context) {
        if (!stack.isEmpty()) {
            Entity entity = context.getParamOrNull(entityTarget.getParam());
            if (entity instanceof Prehistoric prehistoric) {
                if (stack.is(ModItemTags.UNCOOKED_MEAT)) {
                    stack.setCount(Math.min(prehistoric.getAgeInDays(), prehistoric.data().adultAgeDays()));
                    return prehistoric.isOnFire() ? new ItemStack(prehistoric.info().cookedFoodItem, stack.getCount()) : stack;
                }
            }
        }
        return stack;
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return ModLootItemFunctionTypes.CUSTOMIZE_TO_DINOSAUR.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<CustomizeToDinoFunction> {
        @Override
        public void serialize(JsonObject json, CustomizeToDinoFunction value, JsonSerializationContext serializationContext) {
            super.serialize(json, value, serializationContext);
            json.add("entity", serializationContext.serialize(value.entityTarget));
        }

        @Override
        public @NotNull CustomizeToDinoFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
            LootContext.EntityTarget entityTarget = GsonHelper.getAsObject(object, "entity", deserializationContext, LootContext.EntityTarget.class);
            return new CustomizeToDinoFunction(conditions, entityTarget);
        }
    }
}
