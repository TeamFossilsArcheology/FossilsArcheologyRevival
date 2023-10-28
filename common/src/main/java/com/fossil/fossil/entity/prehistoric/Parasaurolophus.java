package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Parasaurolophus extends Prehistoric {
    public static final String ANIMATIONS = "parasaurolophus.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(Parasaurolophus.class, EntityDataSerializers.BOOLEAN);
    
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int ticksStanding;
    private final Entity[] parts = new Entity[3];

    public Parasaurolophus(EntityType<Parasaurolophus> entityType, Level level) {
        super(entityType, level, false);
        var head = PrehistoricPart.get(this, 1.1f, 0.9f);
        var body = PrehistoricPart.get(this, 1.7f, 1.9f);
        var tail = PrehistoricPart.get(this, 0.8f, 0.9f);
        this.parts[0] = body;
        this.parts[1] = head;
        this.parts[2] = tail;
    }

    @Override
    protected void tickCustomParts() {
        parts[0].setPos(position());

        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        parts[1].setPos(getX() + offsetHor.x, getY() + getScale(), getZ() + offsetHor.z);

        offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
        parts[2].setPos(getX() + offsetHor.x, getY() + getScale(), getZ() + offsetHor.z);
    }

    @Override
    public Entity[] getCustomParts() {
        return parts;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STANDING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Standing", isStanding());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setStanding(compound.getBoolean("Standing"));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.PARASAUROLOPHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isStanding()) {
            ticksStanding++;
        } else {
            ticksStanding = 0;
        }
        if (!level.isClientSide) {
            if (!isStanding() && !isImmobile() && random.nextInt(100) == 0) {
                setStanding(true);
            }
            if (isStanding() && ticksStanding > 800 && random.nextInt(800) == 0) {
                setStanding(false);
            }
        }
    }

    public boolean isStanding() {
        return entityData.get(STANDING);
    }

    public void setStanding(boolean standing) {
        entityData.set(STANDING, standing);
    }

    @Override
    public void setSleeping(boolean sleeping) {
        if (sleeping) {
            setStanding(false);
        }
        super.setSleeping(sleeping);
    }

    @Override
    public void setOrderedToSit(boolean orderedToSit) {
        if (orderedToSit) {
            setStanding(false);
        }
        super.setOrderedToSit(orderedToSit);
    }

    @Override
    protected float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PARASAUROLOPHUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PARASAUROLOPHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PARASAUROLOPHUS_DEATH.get();
    }
}