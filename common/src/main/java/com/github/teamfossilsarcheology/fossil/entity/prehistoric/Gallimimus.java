package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
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

import java.util.HashSet;
import java.util.Set;

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
        Set<PrehistoricFlocking> visited = new HashSet<>();
        PrehistoricFlocking current = this;

        // checking for looping chains, which would lead to a stack overflow
        // like, if A's leader is B, B's leader is C, C's leader is A
        // (could go on for longer chains)
        // would be cleaner to do something similar in PrehistoricFlocking's startFollowing method instead
        // but I'm worried about breaking stuff, I'm not yet used to this codebase.
        while (current.hasGroupLeader()) {
            if (!visited.add(current)) {
                leaveGroup();
                break;
            }
            current = current.groupLeader;
        }

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