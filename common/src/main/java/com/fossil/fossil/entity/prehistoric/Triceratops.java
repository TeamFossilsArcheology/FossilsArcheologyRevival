package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.DinoAIFleeBattle;
import com.fossil.fossil.entity.ai.DinoAIWander;
import com.fossil.fossil.entity.ai.DinoMeleeAttackAI;
import com.fossil.fossil.entity.ai.EatFeedersAndBlocksGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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

    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        var file = GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Fossil.MOD_ID, "animations/" + ANIMATIONS));
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        file.animations().forEach((key, value) -> {
            ServerAnimationInfo info;
            switch (key) {
                case ATTACK1, ATTACK2 -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 12);
                case IDLE ->  info = new ServerAnimationInfo(value, IDLE_PRIORITY);
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
        this.hasFeatherToggle = true;
        this.featherToggle = Fossil.CONFIG_OPTIONS.quilledTriceratops;
        this.nearByMobsAllowed = 7;
        breaksBlocks = true;
        this.ridingY = 0.73F;
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
        this.maxUpStep = 1.5F;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        this.goalSelector.addGoal(0, new DinoAIFleeBattle(this, 1.2 * speed));
        this.goalSelector.addGoal(1, new DinoMeleeAttackAI(this, speed, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(7, new DinoAIWander(this, speed));
        this.goalSelector.addGoal(3, new EatFeedersAndBlocksGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        /*this.goalSelector.addGoal(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetSelector.addGoal(3, new DinoAIEatItems(this));
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
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    @NotNull
    public ServerAnimationInfo nextMovingAnimation() {
        String key;
        if (isInWater()) key = SWIM;
        else key = WALK;
        return getAllAnimations().get(key);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        String key;
        if (isInWater()) key = SWIM;
        else key = RUN;
        return getAllAnimations().get(key);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
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
