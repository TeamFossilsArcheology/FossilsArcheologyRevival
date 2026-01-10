package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Sync {@link com.github.teamfossilsarcheology.fossil.food.FoodMappings food mappings} on server join to client
 */
public class S2CSyncFoodMappingsMessage {
    private final Map<FoodType, Map<Item, Integer>> itemValues;
    private final Map<FoodType, Map<EntityType<?>, Integer>> entityValues;
    private final Set<EntityType<?>> entities;

    private S2CSyncFoodMappingsMessage(FriendlyByteBuf buf) {
        itemValues = buf.readMap(HashMap::new, FoodType::readBuf, friendlyByteBuf -> friendlyByteBuf.readMap(HashMap::new, buf1 -> Item.byId(buf1.readInt()), FriendlyByteBuf::readInt));
        entityValues = buf.readMap(HashMap::new, FoodType::readBuf, friendlyByteBuf -> friendlyByteBuf.readMap(HashMap::new, buf1 -> BuiltInRegistries.ENTITY_TYPE.get(buf1.readResourceLocation()), FriendlyByteBuf::readInt));
        entities = new HashSet<>(buf.readList(buf1 -> BuiltInRegistries.ENTITY_TYPE.get(buf1.readResourceLocation())));
    }

    public S2CSyncFoodMappingsMessage(Map<FoodType, Map<Item, Integer>> itemValues, Map<FoodType, Map<EntityType<?>, Integer>> entityValues, Set<EntityType<?>> entities) {
        this.itemValues = itemValues;
        this.entityValues = entityValues;
        this.entities = entities;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeMap(itemValues, FoodType::writeBuf, (innerBuf, map) -> innerBuf.writeMap(map, (buf1, item) -> buf1.writeInt(Item.getId(item)), FriendlyByteBuf::writeInt));
        buf.writeMap(entityValues, FoodType::writeBuf, (innerBuf, map) -> innerBuf.writeMap(map, (buf1, entityType) -> buf1.writeResourceLocation(EntityType.getKey(entityType)), FriendlyByteBuf::writeInt));
        buf.writeCollection(entities, (buf1, entityType) -> buf1.writeResourceLocation(EntityType.getKey(entityType)));
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> FoodMappingsManager.INSTANCE.replaceValues(itemValues, entityValues, entities));
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CSyncFoodMappingsMessage.class, S2CSyncFoodMappingsMessage::write, S2CSyncFoodMappingsMessage::new, S2CSyncFoodMappingsMessage::apply);
    }
}
