package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Tyrannosaurus extends Prehistoric {
    public static final String ATTACK_NORMAL1 = "animation.tyrannosaurus.attack_normal_1";
    public static final String ATTACK_NORMAL2 = "animation.tyrannosaurus.attack_normal_2";

    public Tyrannosaurus(EntityType<Tyrannosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TYRANNOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public AnimationInfo getAnimation(AnimationCategory category) {
        if (category == AnimationCategory.ATTACK) {
            return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK_NORMAL1 : ATTACK_NORMAL2);
        }
        return super.getAnimation(category);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isWeak() ? ModSounds.TYRANNOSAURUS_WEAK.get() : ModSounds.TYRANNOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TYRANNOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TYRANNOSAURUS_DEATH.get();
    }
}
