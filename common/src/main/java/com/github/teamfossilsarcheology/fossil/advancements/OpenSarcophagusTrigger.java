package com.github.teamfossilsarcheology.fossil.advancements;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Triggers whenever the {@link com.github.teamfossilsarcheology.fossil.block.custom_blocks.SarcophagusBlock Sarcophagus} is opened
 */
public class OpenSarcophagusTrigger extends SimpleCriterionTrigger<OpenSarcophagusTrigger.TriggerInstance> {
    private static final ResourceLocation ID = FossilMod.location("open_sarcophagus");

    public void trigger(ServerPlayer player) {
        trigger(player, triggerInstance -> true);
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new TriggerInstance(ID, predicate);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite) {
            super(resourceLocation, composite);
        }

        public static TriggerInstance useScarab() {
            return new TriggerInstance(ID, ContextAwarePredicate.ANY);
        }
    }
}
