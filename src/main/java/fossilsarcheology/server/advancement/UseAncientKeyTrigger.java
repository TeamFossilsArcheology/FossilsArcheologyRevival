package fossilsarcheology.server.advancement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UseAncientKeyTrigger implements ICriterionTrigger<UseAncientKeyTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation("fossil:ancient_key");
    private final Map<PlayerAdvancements, UseAncientKeyTrigger.Listeners> listeners = Maps.newHashMap();

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<UseAncientKeyTrigger.Instance> listener) {
        UseAncientKeyTrigger.Listeners dNATrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (dNATrigger$listeners == null) {
            dNATrigger$listeners = new UseAncientKeyTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, dNATrigger$listeners);
        }

        dNATrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<UseAncientKeyTrigger.Instance> listener) {
        UseAncientKeyTrigger.Listeners dNATrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (dNATrigger$listeners != null) {
            dNATrigger$listeners.remove(listener);

            if (dNATrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    public UseAncientKeyTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new UseAncientKeyTrigger.Instance();
    }

    public void trigger(EntityPlayerMP player) {
        UseAncientKeyTrigger.Listeners dNATrigger$listeners = this.listeners.get(player.getAdvancements());
        if (dNATrigger$listeners != null) {
            dNATrigger$listeners.trigger(player);
        }
    }

    public static class Instance extends AbstractCriterionInstance {

        public Instance() {
            super(UseAncientKeyTrigger.ID);
        }

        public boolean test() {
            return true;
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<UseAncientKeyTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<UseAncientKeyTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<UseAncientKeyTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player) {
            List<Listener<UseAncientKeyTrigger.Instance>> list = null;

            for (Listener<UseAncientKeyTrigger.Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test()) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (Listener<UseAncientKeyTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}