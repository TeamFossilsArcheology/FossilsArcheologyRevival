package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Pachyrhinosaurus extends Prehistoric {

    public Pachyrhinosaurus(EntityType<Pachyrhinosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PACHYRHINOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PACHYCEPHALOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PACHYCEPHALOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PACHYCEPHALOSAURUS_DEATH.get();
    }
}