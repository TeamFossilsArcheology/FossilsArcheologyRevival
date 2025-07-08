package com.github.teamfossilsarcheology.fossil.advancements;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

/**
 * Triggers whenever an egg hatches
 */
public class IncubateEggTrigger extends SimpleCriterionTrigger<IncubateEggTrigger.TriggerInstance> {
    private static final ResourceLocation ID = FossilMod.location("incubate_egg");

    public void trigger(ServerPlayer player, Entity entity) {
        LootContext lootContext = EntityPredicate.createContext(player, entity);
        trigger(player, triggerInstance -> triggerInstance.matches(lootContext));
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new TriggerInstance(ID, predicate, EntityPredicate.fromJson(json, "entity", context));
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate entity;

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite, ContextAwarePredicate entity) {
            super(resourceLocation, composite);
            this.entity = entity;
        }

        public static TriggerInstance incubateEgg(EntityType<?> entityType) {
            return new TriggerInstance(ID, ContextAwarePredicate.ANY,
                    EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityType).build()));
        }

        public boolean matches(LootContext context) {
            return entity.matches(context);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("entity", entity.toJson(context));
            return jsonObject;
        }
    }
}
