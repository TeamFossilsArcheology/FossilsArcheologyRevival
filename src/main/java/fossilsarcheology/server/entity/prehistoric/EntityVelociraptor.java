package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.utility.EntityToyBase;
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


public class EntityVelociraptor extends EntityPrehistoric implements IScaryDinosaur {

	public EntityVelociraptor(World world) {
		super(world, PrehistoricEntityType.VELOCIRAPTOR, 1, 4, 4, 22, 0.25, 0.3, 0, 0);
		this.hasFeatherToggle = true;
		this.pediaScale = 45F;
		this.featherToggle = Revival.CONFIG_OPTIONS.featheredVelociraptor;
		this.setActualSize(1.5F, 1.5F);
		minSize = 0.2F;
		maxSize = 0.6F;
		teenAge = 3;
		developsResistance = false;
		breaksBlocks = false;
		this.nearByMobsAllowed = 9;
	}

	public void initEntityAI() {
		this.tasks.addTask(0, new DinoAILeapAtTarget(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.tasks.addTask(3, new DinoAIEatItems(this));
		this.tasks.addTask(4, new EntityAIRestrictSun(this));
		this.tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(8, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	public boolean canAttackClass(Class clazz) {
		return (clazz != EntityDeinonychus.class) && super.canAttackClass(clazz);
	}

	@Override
	public int getAttackLength() {
		return 35;
	}

	@Override
	public void setSpawnValues() {
	}

	@Override
	public int getAdultAge() {
		return 6;
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

		return PrehistoricEntityTypeAI.Jumping.BASIC;
	}

	@Override
	public PrehistoricEntityTypeAI.Response aiResponseType() {

		return isChild() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERITORIAL;
	}

	@Override
	public PrehistoricEntityTypeAI.Stalking aiStalkType() {

		return PrehistoricEntityTypeAI.Stalking.STEALTH;
	}

	@Override
	public PrehistoricEntityTypeAI.Taming aiTameType() {

		return PrehistoricEntityTypeAI.Taming.FEEDING;
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
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getAttackTarget() != null && this.getRidingEntity() != null) {
			if (this.getRidingEntity() == this.getAttackTarget() && this.ticksExisted % 20 == 0) {
				IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
				this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
			}
		}
		if (this.getAttackTarget() != null && this.getAnimation() == ATTACK_ANIMATION && (this.getAnimationTick() >= 17 && this.getAnimationTick() <= 20) && this.onGround) {
			double d0 = this.getAttackTarget().posX - this.posX;
			double d1 = this.getAttackTarget().posZ - this.posZ;
			double d2 = this.getAttackTarget().posY - this.posY + this.getAttackTarget().height;
			float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
			this.motionX += d0 / (double) f * 0.7D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
			this.motionZ += d1 / (double) f * 0.7D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
			this.motionY += d2 / (double) f * 0.7D * 0.800000011920929D + this.motionY * 0.20000000298023224D;
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return false;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (this.getAttackTarget() != null) {
			if (this.getAttackTarget() == entity && this.getAnimation() == ATTACK_ANIMATION && !onGround && this.getRidingEntity() != entity && (entity instanceof EntityToyBase)) {
				knockBackMob(entity, 0.1F, 0.1F, 0.1F);
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
			} else if (this.getAttackTarget() == entity && this.getAnimation() == ATTACK_ANIMATION && !onGround && this.getRidingEntity() != entity) {
				this.startRiding(entity);
			}
		}
	}

	public float getTargetScale(){
		return 2.0F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FASoundRegistry.VELOCIRAPTOR_LIVING;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.VELOCIRAPTOR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.VELOCIRAPTOR_DEATH;
	}

	@Override
	public int getTailSegments() {
		return 3;
	}

	@Override
	public int getMaxHunger() {
		return 75;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}
}
