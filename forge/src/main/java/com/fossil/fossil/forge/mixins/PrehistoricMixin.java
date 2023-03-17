package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Forge needs the PartEntity clones in the common module. Overrides {@link IForgeEntity#isMultipartEntity()} and {@link IForgeEntity#getParts()}
 */
@Mixin(Prehistoric.class)
public abstract class PrehistoricMixin extends Entity {

    private PrehistoricMixin(EntityType<?> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public PartEntity<?>[] getParts() {
        Entity[] parts = ((Prehistoric) (Object) this).getPartsF();
        PartEntity<?>[] ret = new PartEntity[parts.length];
        for (int i = 0; i < parts.length; i++) {
            ret[i] = (PartEntity<?>) parts[i];
        }
        return ret;
    }

    @Override
    public boolean isMultipartEntity() {
        return ((Prehistoric) (Object) this).isMultiPartF();
    }
}
