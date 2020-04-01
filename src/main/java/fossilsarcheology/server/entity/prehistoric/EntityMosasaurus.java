package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI.*;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import fossilsarcheology.server.item.FAItemRegistry;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMosasaurus extends EntityPrehistoricSwimming {

    public static final Animation SHAKE_ANIMATION = Animation.create(50);

    public EntityMosasaurus(World world) {
        super(world, PrehistoricEntityType.MOSASAURUS, 2, 9, 12, 70, 0.3, 0.35, 4, 10);
        this.hasBabyTexture = false;
        this.setActualSize(1.5F, 0.6F);
        minSize = 0.6F;
        maxSize = 3.4F;
        teenAge = 5;
        developsResistance = true;
        breaksBlocks = true;
        hasBabyTexture = false;
        pediaScale = 30;
        this.ridingY = 0.8F;
    }

    public float getTargetScale() {
        return 2.0F;
    }

    public void initEntityAI() {
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new DinoAIGetInWater(this, 1.0D));
        this.tasks.addTask(0, new DinoAIFindWaterTarget(this, 10, true));
        this.tasks.addTask(0, new DinoAIFollowOwner(this, 1, 10, 2));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIEatBlocks(this));
        this.tasks.addTask(3, new DinoAIEatFeeders(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(5, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Override
    public void setSpawnValues() {
    }

    @Override
    public Activity aiActivityType() {

        return Activity.DIURINAL;
    }

    @Override
    public Attacking aiAttackType() {

        return Attacking.DROWN;
    }

    @Override
    public Climbing aiClimbType() {

        return Climbing.NONE;
    }

    @Override
    public Following aiFollowType() {

        return Following.AGRESSIVE;
    }

    @Override
    public Jumping aiJumpType() {

        return Jumping.BASIC;
    }

    @Override
    public Response aiResponseType() {

        return Response.AGRESSIVE;
    }

    @Override
    public Stalking aiStalkType() {

        return Stalking.NONE;
    }

    @Override
    public Taming aiTameType() {

        return Taming.BLUEGEM;
    }

    @Override
    public Untaming aiUntameType() {

        return Untaming.NONE;
    }

    @Override
    public Moving aiMovingType() {

        return Moving.AQUATIC;
    }

    @Override
    public WaterAbility aiWaterAbilityType() {

        return WaterAbility.ATTACK;
    }

    @Override
    public boolean doesFlock() {
        return false;
    }

    @Override
    public Item getOrderItem() {

        return FAItemRegistry.SKULL_STICK;
    }

    @Override
    public int getAdultAge() {
        return 11;
    }

    @Override
    public double swimSpeed() {
        return 1.2D;
    }

    public boolean breaksBoats() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAttackTarget() != null) {
            if (canReachPrey()) {
                this.attackEntityAsMob(this.getAttackTarget());
                if (!isEntitySmallerThan(this.getAttackTarget(), 2F * (this.getAgeScale() / this.maxSize)) || this.getRNG().nextInt(5) != 0) {
                    if (this.getAnimation() != ATTACK_ANIMATION && this.getAnimation() != SHAKE_ANIMATION) {
                        this.setAnimation(ATTACK_ANIMATION);
                    }
                    this.faceEntity(this.getAttackTarget(), 30, 30);
                    if (this.getAnimation() == ATTACK_ANIMATION && (this.getAnimationTick() == 5 || this.getAnimationTick() == 6)) {
                        this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                        destroyBoat(this.getAttackTarget());
                    }
                } else if (this.getAnimation() != ATTACK_ANIMATION) {
                    destroyBoat(this.getAttackTarget());
                    this.getAttackTarget().startRiding(this);
                    if (this.getAnimation() != SHAKE_ANIMATION) {
                        this.setAnimation(SHAKE_ANIMATION);
                    }
                }
            }
        }

    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof EntityLivingBase && this.getRidingPlayer() == null || this.getRidingPlayer() != null && passenger != this.getRidingPlayer()) {
            Entity riddenByEntity = passenger;
            if ((this.getAnimationTick() > 55 || this.getAnimation() == NO_ANIMATION)) {
                if (riddenByEntity instanceof EntityToyBase) {
                    riddenByEntity.dismountRidingEntity();
                    this.setAttackTarget(null);
                    this.doPlayBonus(((EntityToyBase) riddenByEntity).toyBonus);
                    return;
                } else {
                    if (passenger instanceof EntityLivingBase) {
                        riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getStrongAttackPower());
                    }
                    this.onKillEntity((EntityLivingBase) riddenByEntity);
                }
            }

            float modTick_1 = 5 * MathHelper.sin((float) (Math.PI + (this.getAnimationTick() * 0.275F)));
            rotationYaw = renderYawOffset;
            float radius = 0.5F * (0.7F * getAgeScale()) * -3;
            float angle = (0.01745329251F * this.renderYawOffset) + 3.15F + (modTick_1 * 1.75F) * 0.05F;
            double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
            double extraZ = radius * MathHelper.cos(angle);
            double extraY = 0.05F * (getAgeScale());
            riddenByEntity.setPosition(this.posX + extraX, this.posY + extraY, this.posZ + extraZ);
        }
    }

    @Override
    public void updateRidden() {
        if (this.getRidingPlayer() != null && this.isOwner(this.getRidingPlayer())) {
            super.updateRidden();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    @Override
    public int getMaxHunger() {
        return 125;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, SHAKE_ANIMATION};
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? FASoundRegistry.MOSASAURUS_LIVING_INSIDE : FASoundRegistry.MOSASAURUS_LIVING_OUTSIDE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.MOSASAURUS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.MOSASAURUS_DEATH;
    }
}
