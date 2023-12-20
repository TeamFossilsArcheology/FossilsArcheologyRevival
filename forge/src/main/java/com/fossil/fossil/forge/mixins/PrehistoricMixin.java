package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/**
 * Forge needs the PartEntity clones in the common module. Overrides {@link IForgeEntity#isMultipartEntity()} and {@link IForgeEntity#getParts()}
 */
@Mixin(Prehistoric.class)
public abstract class PrehistoricMixin extends Entity {

    private PrehistoricMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public PartEntity<?>[] getParts() {
        List<MultiPart> parts = ((Prehistoric) (Object) this).getCustomParts();
        PartEntity<?>[] ret = new PartEntity[parts.size()];
        for (int i = 0; i < parts.size(); i++) {
            ret[i] = (PartEntity<?>) parts.get(i).getEntity();
        }
        return ret;
    }

    @Override
    public boolean isMultipartEntity() {
        return ((Prehistoric) (Object) this).isCustomMultiPart();
    }
}
