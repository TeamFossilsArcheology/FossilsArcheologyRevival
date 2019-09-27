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

public class EggTrigger implements ICriterionTrigger<EggTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation("fossil:egg");
    private final Map<PlayerAdvancements, EggTrigger.Listeners> listeners = Maps.newHashMap();

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<EggTrigger.Instance> listener) {
        EggTrigger.Listeners dNATrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (dNATrigger$listeners == null) {
            dNATrigger$listeners = new EggTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, dNATrigger$listeners);
        }

        dNATrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<EggTrigger.Instance> listener) {
        EggTrigger.Listeners dNATrigger$listeners = this.listeners.get(playerAdvancementsIn);

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

    public EggTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new EggTrigger.Instance();
    }

    public void trigger(EntityPlayerMP player) {
        EggTrigger.Listeners dNATrigger$listeners = this.listeners.get(player.getAdvancements());
        if (dNATrigger$listeners != null) {
            dNATrigger$listeners.trigger(player);
        }
    }

    public static class Instance extends AbstractCriterionInstance {

        public Instance() {
            super(EggTrigger.ID);
        }

        public boolean test() {
            return true;
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<EggTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<EggTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<EggTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player) {
            List<Listener<EggTrigger.Instance>> list = null;

            for (Listener<EggTrigger.Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test()) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (Listener<EggTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}