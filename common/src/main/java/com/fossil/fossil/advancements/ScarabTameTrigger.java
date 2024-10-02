package com.fossil.fossil.advancements;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.item.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Triggers whenever a {@link com.fossil.fossil.entity.prehistoric.base.Prehistoric Prehistoric} is tamed with a {@link ModItems#SCARAB_GEM}
 */
public class ScarabTameTrigger extends SimpleCriterionTrigger<OpenSarcophagusTrigger.TriggerInstance> {
    private static final ResourceLocation ID = Fossil.location("scarab_tame");

    public void trigger(ServerPlayer player) {
        trigger(player, triggerInstance -> true);
    }

    @Override
    protected @NotNull OpenSarcophagusTrigger.TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        return new OpenSarcophagusTrigger.TriggerInstance(ID, player);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, EntityPredicate.Composite composite) {
            super(resourceLocation, composite);
        }

        public static OpenSarcophagusTrigger.TriggerInstance useScarab() {
            return new OpenSarcophagusTrigger.TriggerInstance(ID, EntityPredicate.Composite.ANY);
        }
    }
}
