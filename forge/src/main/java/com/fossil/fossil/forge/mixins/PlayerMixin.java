package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgePlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IForgePlayer {

    protected PlayerMixin(EntityType<? extends LivingEntity> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public boolean isCloseEnough(Entity entity, double dist) {
        if (IForgePlayer.super.isCloseEnough(entity, dist)) {
            return true;
        }
        if (entity instanceof Prehistoric prehistoric && prehistoric.isCustomMultiPart()) {
            for (MultiPart part : prehistoric.getCustomParts()) {
                if (isCloseEnough(part.getEntity(), dist)) {
                    return true;
                }
            }
        }
        return false;
    }
}
