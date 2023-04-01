package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;

public class Triceratops extends Prehistoric {
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

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
    private final Entity[] parts;

    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        var file = GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Fossil.MOD_ID, "animations/" + ANIMATIONS));
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        file.animations().forEach((key, value) -> {
            ServerAnimationInfo info;
            switch (key) {
                case ATTACK1, ATTACK2 -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 12);
                case IDLE -> info = new ServerAnimationInfo(value, IDLE_PRIORITY);
                case WALK, RUN, SWIM -> info = new ServerAnimationInfo(value, MOVING_PRIORITY);
                default -> info = new ServerAnimationInfo(value, DEFAULT_PRIORITY);
            }
            newMap.put(key, info);
        });
        return newMap;
    });

    public Triceratops(EntityType<Triceratops> type, Level level) {
        super(
                type, PrehistoricEntityType.TRICERATOPS,
                level,
                true,
                false,
                0.4F,
                1.4F,
                0.5F,
                2F,
                5,
                12,
                1,
                12,
                12,
                64,
                0.1,
                0.25,
                5,
                15
        );
        var head = PrehistoricPart.get(this, 2.5f, 2.5f);
        var body = PrehistoricPart.get(this, 3.2f, 3.3f);
        var tail = PrehistoricPart.get(this, 2.2f, 2f);
        this.parts = new Entity[]{body, head, tail};
        this.hasFeatherToggle = true;
        this.featherToggle = Fossil.CONFIG_OPTIONS.quilledTriceratops;
        this.nearByMobsAllowed = 7;
        breaksBlocks = true;
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
        this.maxUpStep = 1.5F;
    }

    @Override
    public void tick() {
        super.tick();

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
        this.goalSelector.addGoal(0, new FleeBattleGoal(this, 1.5 * speed));
        this.goalSelector.addGoal(1, new DinoMeleeAttackAI(this, speed * 1.5, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new EatFromFeederGoal(this));
        this.goalSelector.addGoal(4, new EatItemEntityGoal(this));
        this.goalSelector.addGoal(5, new EatPlantGoal(this));
        goalSelector.addGoal(4, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, speed));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
        /*
        this.goalSelector.addGoal(4, new DinoAIRiding(this, 1.0F));
        this.goalSelector.addGoal(4, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.goalSelector.addGoal(7, new DinoAILookIdle(this));

        this.targetSelector.addGoal(1, new DinoAIOwnerHurtByTarget(this));
        this.targetSelector.addGoal(2, new DinoAIOwnerHurtTarget(this));
        this.targetSelector.addGoal(3, new DinoAIHurtByTarget(this));
        this.targetSelector.addGoal(4, new DinoAIHunt(this, LivingEntity.class, true, entity -> entity instanceof LivingEntity));*/
    }

    @Override
    public float getModelScale() {
        return super.getModelScale() * 3;
    }

    @Override
    public PrehistoricEntityTypeAI.Activity aiActivityType() {

        return PrehistoricEntityTypeAI.Activity.DIURINAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Attacking aiAttackType() {

        return PrehistoricEntityTypeAI.Attacking.KNOCKUP;
    }

    @Override
    public PrehistoricEntityTypeAI.Climbing aiClimbType() {

        return PrehistoricEntityTypeAI.Climbing.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Following aiFollowType() {

        return PrehistoricEntityTypeAI.Following.NORMAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {

        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {

        return this.isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERITORIAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {

        return PrehistoricEntityTypeAI.Stalking.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {

        return PrehistoricEntityTypeAI.Taming.IMPRINTING;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {

        return PrehistoricEntityTypeAI.Untaming.STARVE;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {

        return PrehistoricEntityTypeAI.Moving.WALK;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {

        return PrehistoricEntityTypeAI.WaterAbility.NONE;
    }

    @Override
    public boolean doesFlock() {
        return false;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public int getMaxHunger() {
        return 175;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }


   /* @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.TRICERATOPS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.TRICERATOPS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.TRICERATOPS_DEATH;
    }*/

    @Override
    public boolean canBeRidden() {
        return true;
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
    @NotNull
    public ServerAnimationInfo nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackAI);

        if (isInWater()) {
            key = SWIM;
        } else if (isChasing) {
            key = RUN;
        }

        return getAllAnimations().get(key);
    }

    @Override
    @NotNull
    public ServerAnimationInfo nextChasingAnimation() {
        String key;
        if (isInWater()) {
            key = SWIM;
        } else {
            key = RUN;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    @NotNull
    public ServerAttackAnimationInfo nextAttackAnimation() {
        int random = getRandom().nextInt(2);
        String key;
        if (random == 0) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }

        return (ServerAttackAnimationInfo) getAllAnimations().get(key);
    }

    @Override
    @NotNull
    public ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }
}
