package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.ai.FleeBattleGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Therizinosaurus extends Prehistoric {
    public static final String ANIMATIONS = "therizinosaurus.animation.json";
    public static final String ATTACK1 = "animation.therizinosaurus.attack1";
    public static final String ATTACK2 = "animation.therizinosaurus.attack2";
    public static final String EAT1 = "animation.therizinosaurus.eat1";
    public static final String EAT2 = "animation.therizinosaurus.eat2";
    public static final String EAT3 = "animation.therizinosaurus.eat3";
    public static final String IDLE = "animation.therizinosaurus.idle";
    public static final String RUN = "animation.therizinosaurus.run";
    public static final String RUN_BABY = "animation.therizinosaurus.run_baby";
    public static final String SIT = "animation.therizinosaurus.sit";
    public static final String SLEEP1 = "animation.therizinosaurus.sleep1";
    public static final String SLEEP2 = "animation.therizinosaurus.sleep2";
    public static final String SWIM = "animation.therizinosaurus.swim";
    public static final String WALK = "animation.therizinosaurus.walk";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Therizinosaurus(EntityType<Therizinosaurus> entityType, Level level) {
        super(entityType, level);
    }

    protected void tickCustomParts() {
        /*parts[0].setPos(position());
        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        float offsetVert = getPose() == Pose.SLEEPING ? getBbHeight() - parts[1].getBbHeight() / 1.9f : getBbHeight();
        parts[1].setPos(position().add(offsetHor.x, offsetVert, offsetHor.z));

        if (getPose() == Pose.SLEEPING) {
            offsetHor = view.scale(getBbWidth() - parts[2].getBbWidth() / 2).reverse();
            offsetVert = getBbHeight()- 0.6f * getScale() - parts[2].getBbHeight();
            parts[2].setPos(position().add(offsetHor.x, offsetVert, offsetHor.z));
        } else {
            offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
            offsetVert = getBbHeight() - 0.3f * getScale() - parts[2].getBbHeight();
            parts[2].setPos(position().add(offsetHor.x, offsetVert, offsetHor.z));
        }*/
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1.0D));
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1.0, true));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.THERIZINOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
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
        //TODO: Eat1 and Eat2 are for blocks on head height. Eat3 is for blocks on ground height
        return getAllAnimations().get(random.nextInt(2) == 0 ? EAT1 : EAT2);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }
    
    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SIT);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(isBaby() ? SLEEP1 : SLEEP2);
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
        return isBaby() ? getAllAnimations().get(RUN_BABY) : getAllAnimations().get(RUN);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.THERIZINOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.THERIZINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.THERIZINOSAURUS_DEATH.get();
    }
}