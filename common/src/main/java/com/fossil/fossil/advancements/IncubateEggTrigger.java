package com.fossil.fossil.advancements;

import com.fossil.fossil.Fossil;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

public class IncubateEggTrigger extends SimpleCriterionTrigger<IncubateEggTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(Fossil.MOD_ID, "incubate_egg");

    public void trigger(ServerPlayer player, Entity entity) {
        LootContext lootContext = EntityPredicate.createContext(player, entity);
        trigger(player, triggerInstance -> triggerInstance.matches(lootContext));
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        EntityPredicate.Composite composite = EntityPredicate.Composite.fromJson(json, "entity", context);
        return new TriggerInstance(ID, player, composite);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite entity;

        public TriggerInstance(ResourceLocation resourceLocation, EntityPredicate.Composite composite, EntityPredicate.Composite entity) {
            super(resourceLocation, composite);
            this.entity = entity;
        }

        public static TriggerInstance incubateEgg(EntityType<?> entityType) {
            return new TriggerInstance(ID, EntityPredicate.Composite.ANY,
                    EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(entityType).build()));
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
