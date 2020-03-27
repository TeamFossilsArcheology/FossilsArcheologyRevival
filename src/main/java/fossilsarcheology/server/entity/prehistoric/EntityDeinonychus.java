package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDeinonychus extends EntityPrehistoric implements IScaryDinosaur{

	public static Animation ANIMATION_JUMPAT = Animation.create(35);
	private boolean attackDecision = true;

	public EntityDeinonychus(World world) {
		super(world, PrehistoricEntityType.DEINONYCHUS, 2, 6, 10, 32, 0.23, 0.35, 0, 2);
		this.nearByMobsAllowed = 9;
		this.hasFeatherToggle = true;
		this.featherToggle = Revival.CONFIG_OPTIONS.featheredDeinonychus;
		this.setActualSize(1.8F, 1.25F);
		minSize = 0.2F;
		maxSize = 0.85F;
		teenAge = 4;
		this.ridingY = 2;
		developsResistance = false;
		breaksBlocks = false;
		this.pediaScale = 34F;
	}

	public void initEntityAI() {
		this.tasks.addTask(0, new DinoMeleeAttackAI(this, 1.0F, false));
		this.tasks.addTask(0, new DinoAILeapAtTarget(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.targetTasks.addTask(0, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
		this.tasks.addTask(4, new DinoAILeapAtTarget(this));
		this.tasks.addTask(5, new EntityAIRestrictSun(this));
		this.tasks.addTask(6, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(7, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(10, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	public int getAttackLength() {
		return 20;
	}

	@Override
	public void setSpawnValues() {
	}

	@Override
	public PrehistoricEntityTypeAI.Activity aiActivityType() {

		return PrehistoricEntityTypeAI.Activity.NOCTURNAL;
	}

	@Override
	public PrehistoricEntityTypeAI.Attacking aiAttackType() {

		return PrehistoricEntityTypeAI.Attacking.JUMP;
	}

	@Override
	public PrehistoricEntityTypeAI.Climbing aiClimbType() {

		return PrehistoricEntityTypeAI.Climbing.ARTHROPOD;
	}

	@Override
	public PrehistoricEntityTypeAI.Following aiFollowType() {

		return PrehistoricEntityTypeAI.Following.AGRESSIVE;
	}

	@Override
	public PrehistoricEntityTypeAI.Jumping aiJumpType() {

		return PrehistoricEntityTypeAI.Jumping.TWOBLOCKS;
	}

	@Override
	public PrehistoricEntityTypeAI.Response aiResponseType() {

		return PrehistoricEntityTypeAI.Response.TERITORIAL;
	}

	@Override
	public PrehistoricEntityTypeAI.Stalking aiStalkType() {

		return PrehistoricEntityTypeAI.Stalking.STEALTH;
	}

	@Override
	public PrehistoricEntityTypeAI.Taming aiTameType() {

		return PrehistoricEntityTypeAI.Taming.IMPRINTING;
	}

	@Override
	public PrehistoricEntityTypeAI.Untaming aiUntameType() {

		return PrehistoricEntityTypeAI.Untaming.ATTACK;
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

		return Items.BONE;
	}

	@Override
	public int getAdultAge() {
		return 10;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(attackDecision){
			if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 6) {
				doAttack();
				attackDecision = rand.nextBoolean();
				this.getAttackTarget().knockBack(this, (float)0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
			}
		}else {
			if (this.getAttackTarget() != null){
				if( this.getRidingEntity() != null) {
					if (this.getRidingEntity() == this.getAttackTarget() && this.ticksExisted % 20 == 0) {
						IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
						this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
					}
				}
				double d0 = this.getDistanceSq(this.getAttackTarget());
				if(d0 < 20 && this.getAnimation() != this.getExtraAnimation(0)){
					this.setAnimation(this.getExtraAnimation(0));
				}
			}

			if (this.getAttackTarget() != null && this.getAttackTarget().isEntityAlive() && this.getAnimation() == ANIMATION_JUMPAT && (this.getAnimationTick() >= 17 && this.getAnimationTick() <= 20) && this.onGround) {
				double d0 = this.getAttackTarget().posX - this.posX;
				double d1 = this.getAttackTarget().posZ - this.posZ;
				double d2 = this.getAttackTarget().posY - this.posY + this.getAttackTarget().height;
				float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
				this.motionX += d0 / (double) f * 0.800000011920929D + this.motionX * 0.20000000298023224D;
				this.motionZ += d1 / (double) f * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
				this.motionY += d2 / (double) f * 0.800000011920929D + this.motionY * 0.20000000298023224D;
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if(attackDecision){
			if (this.getAnimation() != ATTACK_ANIMATION) {
				this.setAnimation(ATTACK_ANIMATION);
			}
		}
		return false;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (this.getAttackTarget() != null) {
			if (this.getAttackTarget() == entity && this.getAnimation() == ATTACK_ANIMATION && !onGround && this.getRidingEntity() != entity && (entity instanceof EntityToyBase)) {
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
			} else if (this.getAttackTarget() == entity && this.getAnimation() == ANIMATION_JUMPAT && !onGround && this.getRidingEntity() != entity) {
				attackDecision = rand.nextBoolean();
				this.startRiding(entity);
			}
		}
	}

	@Override
	public boolean canAttackClass(Class clazz) {
		return (clazz != EntityVelociraptor.class) && super.canAttackClass(clazz);
	}

	@Override
	public boolean attackEntityFrom(DamageSource dmg, float i) {
		if (this.getRidingEntity() != null) {
			if (this.getLastAttackedEntity() != null) {
				if (this.getLastAttackedEntity() == this.getRidingEntity()) {
					if (this.getRNG().nextInt(2) == 0) {
						this.dismountRidingEntity();
					}
				}
			}
		}
		return super.attackEntityFrom(dmg, i);
	}

	public Animation getExtraAnimation(int i) {
		return ANIMATION_JUMPAT;
	}

	public boolean useSpecialAttack(){
		return !attackDecision;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, ANIMATION_JUMPAT};
	}

	@Override
	public int getMaxHunger() {
		return 100;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FASoundRegistry.DEINONYCHUS_LIVNIG;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.DEINONYCHUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.DEINONYCHUS_DEATH;
	}

	public float getTargetScale(){
		return 2.0F;
	}
}
