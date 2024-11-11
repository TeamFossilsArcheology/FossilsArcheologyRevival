package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.fossil.fossil.client.renderer.entity.DebugEntityRenderer;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoLeapAtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.mojang.math.Vector3d;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class Velociraptor extends PrehistoricLeaping {
    public static final String LEAP = "animation.velociraptor.leap";
    public static final String CLIMB = "animation.velociraptor.climb";
    public static final String LAND = "animation.velociraptor.land";

    public Velociraptor(EntityType<Velociraptor> entityType, Level level) {
        super(entityType, level, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DinoLeapAtTargetGoal(this));
        goalSelector.addGoal(Util.NEEDS + 4, new RestrictSunGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.VELOCIRAPTOR;
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        return !entityType.equals(ModEntities.DEINONYCHUS.get()) && super.canAttackType(entityType);
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public String getLandAnimationName() {
        return LAND;
    }

    @Override
    public String getLeapStartAnimationName() {
        return LEAP;
    }

    @Override
    public String getLeapAttackAnimationName() {
        return CLIMB;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.VELOCIRAPTOR_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.VELOCIRAPTOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.VELOCIRAPTOR_DEATH.get();
    }
}
