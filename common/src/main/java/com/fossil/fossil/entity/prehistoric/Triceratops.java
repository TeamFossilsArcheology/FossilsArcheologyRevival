package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class Triceratops extends Prehistoric {
    public static final String ANIMATIONS = "triceratops.animation.json";
    public static final String IDLE = "animation.triceratops.idle";
    public static final String WALK = "animation.triceratops.walk";
    public static final String RUN = "animation.triceratops.run";
    public static final String SWIM = "animation.triceratops.swim";
    public static final String DRINK = "animation.triceratops.drink";
    public static final String EAT = "animation.triceratops.eat";
    public static final String SIT = "animation.triceratops.sit";
    public static final String SLEEP1 = "animation.triceratops.sleep1";
    public static final String SLEEP2 = "animation.triceratops.sleep2";
    public static final String RAM = "animation.triceratops.ram";
    public static final String RAM_WINDUP = "animation.triceratops.ram_windup";
    public static final String TURN_RIGHT = "animation.triceratops.turn_right";
    public static final String TURN_LEFT = "animation.triceratops.turn_left";
    public static final String SPEAK = "animation.triceratops.speak";
    public static final String CALL = "animation.triceratops.call";
    public static final String ATTACK1 = "animation.triceratops.attack1";
    public static final String ATTACK2 = "animation.triceratops.attack2";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final Entity[] parts = new Entity[3];

    public Triceratops(EntityType<Triceratops> type, Level level) {
        super(type, level, true);
        this.hasFeatherToggle = true;
        this.featherToggle = FossilConfig.isEnabled(FossilConfig.QUILLED_TRICERATOPS);
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
        var head = PrehistoricPart.get(this, 1.8f, 1.8f);
        var body = PrehistoricPart.get(this, 2.3f, 2.4f);
        var tail = PrehistoricPart.get(this, 1.6f, 1.4f);
        this.parts[0] = body;
        this.parts[1] = head;
        this.parts[2] = tail;
    }

    @Override
    protected void tickCustomParts() {
        Vec3 offset = calculateViewVector(getXRot(), yBodyRot).reverse().scale(0.4 * getScale());
        parts[0].setPos(getX() + offset.x, getY() + offset.y, getZ() + offset.z);

        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        parts[1].setPos(getX() + offset.x + offsetHor.x, getY() + getScale(), getZ() + offset.z + offsetHor.z);

        offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
        parts[2].setPos(getX() + offset.x + offsetHor.x, getY() + getScale(), getZ() + offset.z + offsetHor.z);
    }

    @Override
    public Entity[] getCustomParts() {
        return parts;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1.5 * speed));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, speed * 1.5, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, speed));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.TRICERATOPS;
    }

    @Override
    public float getModelScale() {
        return super.getModelScale() * 3;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {

        return isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.5;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackGoal);

        if (isInWater()) {
            key = SWIM;
        } else if (isChasing) {
            key = RUN;
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        String key;
        if (isInWater()) {
            key = SWIM;
        } else {
            key = RUN;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        int random = getRandom().nextInt(2);
        String key;
        if (random == 0) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
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