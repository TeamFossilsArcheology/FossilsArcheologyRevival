package fossilsarcheology.server.util;

import fossilsarcheology.server.entity.FAEntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public enum PrehistoricEntityType {
    TRICERATOPS(FAEntityRegistry.TRICERATOPS, MobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 1);

    public static final ResourceLocation DINOSAUR_LOOT = null;
    private EntityType entityType;
    public MobType mobType;
    private float eggScale = 1.0F;
    public TimePeriod timePeriod;
    public Diet diet;
    public Item eggItem;
    public Item uncultivatedEggItem;

    PrehistoricEntityType(EntityType entityType, MobType mobType, TimePeriod timePeriod, Diet diet, float eggScale){
        this.entityType = entityType;
        this.mobType = mobType;
        this.diet = diet;
        this.timePeriod = timePeriod;
    }


    public Entity create(World world) {
        return null;
    }
}
