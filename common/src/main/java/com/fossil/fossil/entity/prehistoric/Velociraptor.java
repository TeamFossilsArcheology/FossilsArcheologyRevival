package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.*;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Velociraptor extends Prehistoric implements PrehistoricLeaping, PrehistoricScary {
    public static final String ANIMATIONS = "velociraptor.animation.json";
    public static final String IDLE = "animation.velociraptor.idle";
    public static final String WALK = "animation.velociraptor.walk";
    public static final String RUN = "animation.velociraptor.run";
    public static final String SPEAK = "animation.velociraptor.speak";
    public static final String CALL = "animation.velociraptor.call";
    public static final String ATTACK1 = "animation.velociraptor.attack1";
    public static final String DISPLAY = "animation.velociraptor.display";
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("velociraptor");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Velociraptor(EntityType<Velociraptor> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(0, new DinoLeapAtTargetGoal<>(this));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(4, new RestrictSunGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean useLeapAttack() {
        return true;//TODO: Implement
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.VELOCIRAPTOR;
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
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(DISPLAY);
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
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public @NotNull Animation nextLeapAnimation() {
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
