package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
    public static final String IDLE = "fa.therizinosaurus.idle";
    public static final String WALK = "fa.therizinosaurus.walk";
    public static final String SLEEP = "fa.therizinosaurus.sleep";
    public static final String SLEEP_BABY = "fa.therizinosaurus.sleep_baby";
    public static final String THREAT = "fa.therizinosaurus.threat";
    public static final String ATTACK1 = "fa.therizinosaurus.attack1";
    public static final String ATTACK2 = "fa.therizinosaurus.attack2";
    public static final String EAT = "fa.therizinosaurus.eat";
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
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1.0D));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1.0, true));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(5, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, 1.0));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(2, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.THERIZINOSAURUS;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return this.isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public float getFemaleScale() {
        return 1.12F;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
        return isBaby() ? getAllAnimations().get(SLEEP_BABY) : getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return nextMovingAnimation();
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        String key;

        if (getRandom().nextBoolean()) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }

        return getAllAnimations().get(key);
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