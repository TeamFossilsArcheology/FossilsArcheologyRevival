package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Triceratops extends Prehistoric {
    public static final String ANIMATIONS = "triceratops.animation.json";
    public static final String ATTACK1 = "animation.triceratops.attack1";
    public static final String ATTACK2 = "animation.triceratops.attack2";
    public static final String EAT = "animation.triceratops.eat";
    public static final String FALL = "animation.triceratops.jump/fall";
    public static final String IDLE = "animation.triceratops.idle";
    public static final String RUN = "animation.triceratops.run";
    public static final String SLEEP1 = "animation.triceratops.sleep1";
    public static final String SLEEP2 = "animation.triceratops.sleep2";
    public static final String SWIM = "animation.triceratops.swim";
    public static final String WALK = "animation.triceratops.walk";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Triceratops(EntityType<Triceratops> type, Level level) {
        super(type, level);
        this.hasFeatherToggle = true;
        this.featherToggle = FossilConfig.isEnabled(FossilConfig.QUILLED_TRICERATOPS);
        this.pediaScale = 55;
    }

    protected void tickCustomParts() {
        /*Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offset = view.reverse().scale(0.4 * getScale());
        parts[0].setPos(getX() + offset.x, getY() + offset.y, getZ() + offset.z);

        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        float offsetVert = getPose() == Pose.SLEEPING ? 0 : getScale();
        parts[1].setPos(position().add(offset.x + offsetHor.x, offsetVert, offset.z + offsetHor.z));

        offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
        parts[2].setPos(position().add(offset.x + offsetHor.x, offsetVert, offset.z + offsetHor.z));*/
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        if (poseIn == Pose.SLEEPING) {
            return super.getDimensions(poseIn).scale(1, 0.65f);
        }
        return super.getDimensions(poseIn);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1.5 * speed));
        goalSelector.addGoal(1, new DelayedAttackGoal(this, speed * 1.5, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, speed));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TRICERATOPS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.15;
    }

    @Override
    public PrehistoricEntityInfoAI.Response aiResponseType() {

        return isBaby() ? PrehistoricEntityInfoAI.Response.SCARED : PrehistoricEntityInfoAI.Response.TERRITORIAL;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK1 : ATTACK2);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? SLEEP1 : SLEEP2);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(RUN);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TRICERATOPS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TRICERATOPS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TRICERATOPS_DEATH.get();
    }
}