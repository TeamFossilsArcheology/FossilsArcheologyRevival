package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Gallimimus extends PrehistoricFlocking {

    public Gallimimus(EntityType<Gallimimus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.GALLIMIMUS;
    }

    @Override
    protected int getMaxGroupSize() {
        return 10;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (getRidingPlayer() != null) {
            setMaxUpStep(2);
        } else {
            setMaxUpStep(0.6f);
        }
    }

    @Override
    public PrehistoricEntityInfoAI.Response aiResponseType() {
        if (hasGroupLeader()) {
            return groupLeader.aiResponseType();
        }
        return groupSize >= 3 ? PrehistoricEntityInfoAI.Response.TERRITORIAL : PrehistoricEntityInfoAI.Response.SCARED;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GALLIMIMUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.GALLIMIMUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GALLIMIMUS_DEATH.get();
    }
}