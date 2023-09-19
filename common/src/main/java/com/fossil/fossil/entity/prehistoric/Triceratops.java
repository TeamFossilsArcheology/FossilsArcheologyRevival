package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("triceratops");
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final Entity[] parts;

    public Triceratops(EntityType<Triceratops> type, Level level) {
        super(type, level, true);
        var head = PrehistoricPart.get(this, 2.5f, 2.5f);
        var body = PrehistoricPart.get(this, 3.2f, 3.3f);
        var tail = PrehistoricPart.get(this, 2.2f, 2f);
        this.parts = new Entity[]{body, head, tail};
        this.hasFeatherToggle = true;
        this.featherToggle = FossilConfig.isEnabled(FossilConfig.QUILLED_TRICERATOPS);
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3[] vec3s = new Vec3[this.parts.length];
        for (int i = 0; i < this.parts.length; i++) {
            vec3s[i] = parts[i].getPosition(1.0f);
        }
        Vec3 offset = calculateViewVector(getXRot(), yBodyRot).reverse().scale(0.3);
        //Vec3 offset = new Vec3(1, 1, 1);
        //body.setPos(getX(), getY(), getZ());
        parts[0].setPos(getX() + offset.x, getY() + offset.y, getZ() + offset.z);

        Vec3 offsetHor = calculateViewVector(0, yBodyRot).scale(1.1 * getScale());
        Vec3 headOffset = calculateViewVector(0, yBodyRot).with(Direction.Axis.Y, 0).add(0, getScale(), 0);
        if (level.isClientSide) {
        } else {
            CompoundTag tag = entityData.get(DEBUG).copy();
            tag.putDouble("x", getX() + headOffset.x + offsetHor.x);
            tag.putDouble("y", getY() + headOffset.y);
            tag.putDouble("z", getZ() + headOffset.z + offsetHor.z);
            entityData.set(DEBUG, tag);
        }
        parts[1].setPos(getX() + headOffset.x + offsetHor.x, getY() + headOffset.y, getZ() + headOffset.z + offsetHor.z);
        CompoundTag tag = entityData.get(DEBUG);
        parts[1].setPos(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));

        offsetHor = offsetHor.yRot((float) Math.toRadians(180));
        Vec3 tailOffset = calculateViewVector(0, yBodyRot).scale(-1.5).add(0, 1.1D, 0);
        parts[2].setPos(getX() + tailOffset.x + offsetHor.x, getY() + tailOffset.y, getZ() + tailOffset.z + offsetHor.z);

        for (int i = 0; i < this.parts.length; i++) {
            this.parts[i].xo = vec3s[i].x;
            this.parts[i].yo = vec3s[i].y;
            this.parts[i].zo = vec3s[i].z;
            this.parts[i].xOld = vec3s[i].x;
            this.parts[i].yOld = vec3s[i].y;
            this.parts[i].zOld = vec3s[i].z;
        }
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
    public EntityDataManager.Data data() {
        return data;
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