package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.sounds.ModSounds;
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

public class Stegosaurus extends Prehistoric {
    public static final String ANIMATIONS = "stegosaurus.animation.json";
    public static final String IDLE = "animation.stegosaurus.idle";
    public static final String SIT1 = "animation.stegosaurus.sit1";
    public static final String SIT2 = "animation.stegosaurus.sit2";
    public static final String SLEEP1 = "animation.stegosaurus.sleep1";
    public static final String SLEEP2 = "animation.stegosaurus.sleep2";
    public static final String WALK = "animation.stegosaurus.walk";
    public static final String RUN = "animation.stegosaurus.run";
    public static final String JUMP_FALL = "animation.stegosaurus.jump/fall";
    public static final String SWIM = "animation.stegosaurus.swim";
    public static final String EAT = "animation.stegosaurus.eat";
    public static final String TURN_RIGHT = "animation.stegosaurus.turn_right";
    public static final String TURN_LEFT = "animation.stegosaurus.turn_left";
    public static final String SPEAK = "animation.stegosaurus.speak";
    public static final String CALL1 = "animation.stegosaurus.call1";
    public static final String ATTACK_FRONT1 = "animation.stegosaurus.attack_front1";
    public static final String ATTACK_FRONT2 = "animation.stegosaurus.attack_front2";
    
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final Entity[] parts = new Entity[3];

    public Stegosaurus(EntityType<Stegosaurus> entityType, Level level) {
        super(entityType, level, false);
        var head = PrehistoricPart.get(this, 0.9f, 0.7f);
        var body = PrehistoricPart.get(this, 1.4f, 1.8f);
        var tail = PrehistoricPart.get(this, 1, 0.6f);
        this.parts[0] = body;
        this.parts[1] = head;
        this.parts[2] = tail;
    }

    @Override
    protected void tickCustomParts() {
        parts[0].setPos(position());

        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        parts[1].setPos(getX() + offsetHor.x, getY() + (getBbHeight() - 0.1f * getScale()- parts[1].getBbHeight()), getZ() + offsetHor.z);

        offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
        parts[2].setPos(getX() + offsetHor.x, getY() + (getBbHeight() - 0.1f * getScale() - parts[2].getBbHeight()), getZ() + offsetHor.z);
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
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.STEGOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
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
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(RUN);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK_FRONT1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.STEGOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.STEGOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.STEGOSAURUS_DEATH.get();
    }
}