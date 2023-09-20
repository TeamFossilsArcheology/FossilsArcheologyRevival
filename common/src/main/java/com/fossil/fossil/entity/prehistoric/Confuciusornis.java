package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DinoFollowOwnerGoal;
import com.fossil.fossil.entity.ai.DinoLookAroundGoal;
import com.fossil.fossil.entity.ai.DinoMeleeAttackGoal;
import com.fossil.fossil.entity.ai.DinoWanderGoal;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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

public class Confuciusornis extends PrehistoricFlying {
    public static final String ANIMATIONS = "confuciusornis.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("confuciusornis");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Confuciusornis(EntityType<Confuciusornis> entityType, Level level) {
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
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(7, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.CONFUCIUSORNIS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    protected float getGenderedScale() {
        return getGender() == Gender.MALE ? 0.8f : super.getGenderedScale();
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

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CONFUCIUSORNIS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.CONFUCIUSORNIS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CONFUCIUSORNIS_DEATH.get();
    }
}