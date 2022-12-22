package fossilsarcheology.server.entity.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public abstract class EntityToyBase extends LivingEntity {

    protected EntityToyBase(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
