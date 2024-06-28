package com.fossil.fossil.advancements;

import com.fossil.fossil.Fossil;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class OpenSarcophagusTrigger extends SimpleCriterionTrigger<OpenSarcophagusTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(Fossil.MOD_ID, "open_sarcophagus");

    public void trigger(ServerPlayer player) {
        trigger(player, triggerInstance -> true);
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        return new TriggerInstance(ID, player);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, EntityPredicate.Composite composite) {
            super(resourceLocation, composite);
        }

        public static TriggerInstance useScarab() {
            return new TriggerInstance(ID, EntityPredicate.Composite.ANY);
        }
    }
}
