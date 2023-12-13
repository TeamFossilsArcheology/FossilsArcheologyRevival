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

public class Brachiosaurus extends Prehistoric {
    public static final String ANIMATIONS = "brachiosaurus.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final Entity[] parts = new Entity[6];

    public Brachiosaurus(EntityType<Brachiosaurus> entityType, Level level) {
        super(entityType, level, false);
        if (!level.isClientSide) {
            var head = new PrehistoricPart(this, 0.6f, 0.9f, false, 1);
            var body = new PrehistoricPart(this, 0.95f, 0.65f, false, 0);
            var tail = new PrehistoricPart(this, 0.5f, 0.5f, false, 2);
            var tail2 = new PrehistoricPart(this, 0.5f, 0.3f, false, 4);
            var head2 = new PrehistoricPart(this, 0.6f, 0.7f, false, 3);
            var body2 = new PrehistoricPart(this, 0.95f, 0.65f, false, 5);
            this.parts[0] = body;
            this.parts[1] = head;
            this.parts[2] = tail;
            this.parts[3] = head2;
            this.parts[4] = tail2;
            this.parts[5] = body2;
            for (Entity part : parts) {
                level.addFreshEntity(part);
            }
        }
    }

    @Override
    protected void tickCustomParts() {
        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 reversedView = view.reverse();
        Vec3 offset = reversedView.scale(-0.05f * getScale());
        parts[0].setPos(position().add(offset));

        parts[5].setPos(position().add(offset).add(0, parts[0].getBbHeight(), 0));

        Vec3 offsetHor = view.scale(getBbWidth() / 2 + parts[1].getBbWidth() / 2);
        Vec3 headPos = position().add(offset.x + offsetHor.x, (getBbHeight() - 0.3f * parts[1].getBbHeight()), offset.z + offsetHor.z);
        parts[1].setPos(headPos);

        offsetHor = view.scale(parts[1].getBbWidth() / 2 + parts[3].getBbWidth() / 2);
        parts[3].setPos(headPos.add(offsetHor.x, 0.8f * parts[1].getBbHeight(), offsetHor.z));

        offsetHor = reversedView.scale(getBbWidth() / 2 + parts[2].getBbWidth() / 2);
        Vec3 tailPos = position().add(offset.x + offsetHor.x, (getBbHeight() - 0.4f * getScale() - parts[2].getBbHeight()), offset.z + offsetHor.z);
        parts[2].setPos(tailPos);

        offsetHor = reversedView.scale(parts[2].getBbWidth() / 2 + parts[4].getBbWidth() / 2);
        parts[4].setPos(tailPos.add(offsetHor.x, 0, offsetHor.z));
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
        return PrehistoricEntityType.BRACHIOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
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
        return ModSounds.BRACHIOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.BRACHIOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BRACHIOSAURUS_DEATH.get();
    }
}