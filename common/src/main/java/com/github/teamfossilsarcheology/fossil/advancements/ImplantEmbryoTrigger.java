package com.github.teamfossilsarcheology.fossil.advancements;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Triggers whenever a player inserts an embryo into a mob
 */
public class ImplantEmbryoTrigger extends SimpleCriterionTrigger<ImplantEmbryoTrigger.TriggerInstance> {
    private static final ResourceLocation ID = FossilMod.location("implant_embryo");

    public void trigger(ServerPlayer player, ItemStack stack) {
        trigger(player, triggerInstance -> triggerInstance.matches(stack));
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        JsonObject jsonObject = GsonHelper.getAsJsonObject(json, "item", json);
        ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject);
        return new TriggerInstance(ID, predicate, itemPredicate);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate embryoItem;

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite, ItemPredicate embryoItem) {
            super(resourceLocation, composite);
            this.embryoItem = embryoItem;
        }

        public static TriggerInstance implantEmbryo(Item embryoItem) {
            return new TriggerInstance(ID, ContextAwarePredicate.ANY,
                    ItemPredicate.Builder.item().of(embryoItem).build());
        }

        public boolean matches(ItemStack itemStack) {
            return embryoItem.matches(itemStack);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("item", embryoItem.serializeToJson());
            return jsonObject;
        }
    }
}
