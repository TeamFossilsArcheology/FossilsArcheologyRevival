package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import fossilsarcheology.server.item.FAItemRegistry;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpinosaurus extends EntityPrehistoricSwimming implements IScaryDinosaur {
	private static final DataParameter<Boolean> SWIMMING = EntityDataManager.createKey(EntitySpinosaurus.class, DataSerializers.BOOLEAN);

	public final Animation SHAKE_ANIMATION = Animation.create(65);
	public float swimProgress;
	private boolean isSwimming;

	public EntitySpinosaurus(World world) {
		super(world, PrehistoricEntityType.SPINOSAURUS, 2, 14, 15, 86, 0.25, 0.35, 10, 20);
		this.setActualSize(1.5F, 1.0F);
		isAmphibious = true;
		FISH_ANIMATION = Animation.create(40);
		minSize = 0.25F;
		maxSize = 3.1F;
		teenAge = 5;
		developsResistance = true;
		breaksBlocks = true;
		this.hasBabyTexture = true;
		this.ridingY = 1.21F;
		this.ridingXZ = -0.3F;
		this.pediaScale = 26F;
	}

	public float getTargetScale(){
		return 1.5F;
	}

	public void initEntityAI() {
		this.tasks.addTask(0, new DinoAIFindWaterTarget(this, 10, true));
		this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
		this.tasks.addTask(1, new DinoAIGetInWater(this, 1.0D));
		this.tasks.addTask(1, new DinoAILeaveWater(this, 1.0D));
		this.tasks.addTask(1, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.targetTasks.addTask(0, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAIRiding(this, 1.5D));
		this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SWIMMING, false);
	}


	@Override
	public void setSpawnValues() {
	}

	@Override
	public PrehistoricEntityTypeAI.Activity aiActivityType() {

		return PrehistoricEntityTypeAI.Activity.DIURINAL;
	}

	@Override
	public PrehistoricEntityTypeAI.Attacking aiAttackType() {

		return PrehistoricEntityTypeAI.Attacking.DROWN;
	}

	@Override
	public PrehistoricEntityTypeAI.Climbing aiClimbType() {

		return PrehistoricEntityTypeAI.Climbing.NONE;
	}

	@Override
	public PrehistoricEntityTypeAI.Following aiFollowType() {

		return PrehistoricEntityTypeAI.Following.AGRESSIVE;
	}

	@Override
	public PrehistoricEntityTypeAI.Jumping aiJumpType() {

		return PrehistoricEntityTypeAI.Jumping.BASIC;
	}

	@Override
	public PrehistoricEntityTypeAI.Response aiResponseType() {

		return PrehistoricEntityTypeAI.Response.AGRESSIVE;
	}

	@Override
	public PrehistoricEntityTypeAI.Stalking aiStalkType() {

		return PrehistoricEntityTypeAI.Stalking.NONE;
	}

	@Override
	public PrehistoricEntityTypeAI.Taming aiTameType() {

		return PrehistoricEntityTypeAI.Taming.BLUEGEM;
	}

	@Override
	public PrehistoricEntityTypeAI.Untaming aiUntameType() {

		return PrehistoricEntityTypeAI.Untaming.NONE;
	}

	@Override
	public PrehistoricEntityTypeAI.Moving aiMovingType() {

		return PrehistoricEntityTypeAI.Moving.SEMIAQUATIC;
	}

	@Override
	public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {

		return PrehistoricEntityTypeAI.WaterAbility.ATTACK;
	}

	@Override
	public boolean doesFlock() {
		return false;
	}

	public boolean breaksBoats() {
		return true;
	}

	@Override
	public Item getOrderItem() {
		return FAItemRegistry.SKULL_STICK;
	}

	@Override
	public int getAdultAge() {
		return 12;
	}

	@Override
	public int getTailSegments() {
		return 3;
	}

	@Override
	public int getMaxHunger() {
		return 175;
	}

	@Override
	public double swimSpeed() {
		return 1;
	}

	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		if (passenger instanceof EntityLivingBase && this.getRidingPlayer() == null || this.getRidingPlayer() != null && passenger != this.getRidingPlayer()) {
			Entity riddenByEntity = passenger;
			if ((this.getAnimationTick() > 55 || this.getAnimation() == NO_ANIMATION)) {
				if (riddenByEntity instanceof EntityToyBase) {
					((EntityToyBase) riddenByEntity).dismountRidingEntity();
					this.setAttackTarget(null);
					this.doPlayBonus(((EntityToyBase) riddenByEntity).toyBonus);
					riddenByEntity.setPosition(posX, posY, posZ);
					riddenByEntity = null;
					return;
				} else {
					if (passenger instanceof EntityLivingBase) {
						riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)getStrongAttackPower());
						riddenByEntity.dismountRidingEntity();
						riddenByEntity.setPosition(posX, posY, posZ);
					}
					this.onKillEntity((EntityLivingBase) riddenByEntity);
				}
			}

			riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + riddenByEntity.getYOffset(), this.posZ);
			float modTick_0 = this.getAnimationTick() - 15;
			float modTick_1 = this.getAnimationTick() > 15 ? 6 * MathHelper.sin((float) (Math.PI + (modTick_0 * 0.275F))) : 0;
			float modTick_2 = this.getAnimationTick() > 15 ? 15 : this.getAnimationTick();
			this.rotationYaw *= 0;
			riddenByEntity.rotationYaw = this.rotationYaw + this.rotationYawHead + 180;
			rotationYaw = renderYawOffset;
			float radius = 0.6F * (0.7F * getAgeScale()) * -3;
			float angle = (0.01745329251F * this.renderYawOffset) + 3.15F + (modTick_1 * 1.75F) * 0.05F;
			double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
			double extraZ = (double) (radius * MathHelper.cos(angle));
			double extraY = 0.8F * (getAgeScale() + (modTick_1 * 0.05) + (modTick_2 * 0.15) - 2.5F);
			double waterMinus = getAgeScale() * (this.swimProgress / 20) * 0.5F;
			super.updateRidden();
			riddenByEntity.setPosition(this.posX + extraX, this.posY + extraY - waterMinus, this.posZ + extraZ);
		}
	}

	@Override
	public void updateRidden() {
		if (this.getRidingPlayer() != null && this.isOwner(this.getRidingPlayer())) {
			super.updateRidden();
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		boolean swimming = this.isSwimming();
		if (swimming && swimProgress < 20.0F) {
			swimProgress += 0.5F;
			if (sitProgress != 0)
				sitProgress = sleepProgress = 0F;
		} else if (!swimming && swimProgress > 0.0F) {
			swimProgress -= 0.5F;
			if (sitProgress != 0)
				sitProgress = sleepProgress = 0F;
		}
		if (this.isSwimming() && (this.isSitting() || this.isSleeping())) {
			this.setSitting(false);
			this.setSleeping(false);
		}
		if (this.getAttackTarget() != null) {
			if (canReachPrey()) {
				this.attackEntityAsMob(this.getAttackTarget());
				if (!isEntitySmallerThan(this.getAttackTarget(), 2F * (this.getAgeScale() / this.maxSize)) || this.getRNG().nextInt(5) != 0) {
					if (this.getAnimation() != ATTACK_ANIMATION && this.getAnimation() != SHAKE_ANIMATION) {
						this.setAnimation(ATTACK_ANIMATION);
					}
					this.faceEntity(this.getAttackTarget(), 30, 30);
					if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() > 10) {
						this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
						destroyBoat(this.getAttackTarget());
					}
				} else if(this.getAnimation() != ATTACK_ANIMATION){
					destroyBoat(this.getAttackTarget());
					this.getAttackTarget().startRiding(this);
					if (this.getAnimation() != SHAKE_ANIMATION) {
						this.setAnimation(SHAKE_ANIMATION);
					}
				}
			}
		}
		if (this.isInWaterMaterial()) {
			this.setSwimming(true);
		} else {
			this.setSwimming(false);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return !this.isInWater();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("Swimming", this.isSwimming);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSwimming(compound.getBoolean("Swimming"));

	}

	public boolean isSwimming() {
		if (world.isRemote) {
			boolean isSwimming = this.dataManager.get(SWIMMING);
			this.isSwimming = isSwimming;
			return isSwimming;
		}

		return isSwimming;
	}

	public void setSwimming(boolean swimming) {
		this.dataManager.set(SWIMMING, swimming);
		if (!world.isRemote) {
			this.isSwimming = swimming;
		}
	}

	@Override
	public int getAttackLength() {
		return 25;
	}


	@Override
	public Animation[] getAnimations() {
		return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, SHAKE_ANIMATION, FISH_ANIMATION};
	}

	@Override
	public boolean canBeRidden() {
		return true;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FASoundRegistry.SPINOSAURUS_LIVING;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.SPINOSAURUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.SPINOSAURUS_DEATH;
	}

	public float getProximityToNextPathSkip(){
		return (float)this.getEntityBoundingBox().getAverageEdgeLength() * 0.8F;
	}
}
