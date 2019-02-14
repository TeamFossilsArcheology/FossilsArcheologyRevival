package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCompsognathus extends EntityPrehistoric {

	public EntityCompsognathus(World world) {
		super(world, PrehistoricEntityType.COMPSOGNATHUS, 1, 1, 4, 12, 0.25, 0.4, 0, 0);
		this.setActualSize(1.1F, 1.1F);
		this.nearByMobsAllowed = 5;
		this.hasFeatherToggle = true;
		this.featherToggle = Revival.CONFIG.featheredCompsognathus;
		minSize = 0.2F;
		maxSize = 0.5F;
		teenAge = 2;
		developsResistance = false;
		breaksBlocks = false;
		this.pediaScale = 50F;
	}

	public void initEntityAI() {
		this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.tasks.addTask(3, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAILeapAtTarget(this));
		this.tasks.addTask(5, new EntityAIRestrictSun(this));
		this.tasks.addTask(6, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(7, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(8, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	public int getAttackLength() {
		return 35;
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

		return PrehistoricEntityTypeAI.Attacking.BASIC;
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

		return PrehistoricEntityTypeAI.Jumping.TWOBLOCKS;
	}

	@Override
	public PrehistoricEntityTypeAI.Response aiResponseType() {

		return PrehistoricEntityTypeAI.Response.SCARED;
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
	public int getAdultAge() {
		return 4;
	}

	@Override
	public String getOverlayTexture() {
		return "fossil:textures/blank.png";
	}

	@Override
	public int getTailSegments() {
		return 2;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getAttackTarget() != null && this.getAnimation() == ATTACK_ANIMATION && (this.getAnimationTick() <= 20 && this.getAnimationTick() >= 17) && this.onGround) {
			double d0 = this.getAttackTarget().posX - this.posX;
			double d1 = this.getAttackTarget().posZ - this.posZ;
			float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
			this.motionX += d0 / (double) f * 0.4D * 0.800000011920929D + this.motionX * 0.40000000298023224D;
			this.motionZ += d1 / (double) f * 0.4D * 0.800000011920929D + this.motionZ * 0.40000000298023224D;
			this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 10, 12);
			this.motionY = 0.4;
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (this.getAnimation() == NO_ANIMATION) {
			this.setAnimation(ATTACK_ANIMATION);
		}
		return false;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (this.getAttackTarget() != null) {
			if (this.getAttackTarget() == entity && this.getAnimation() == ATTACK_ANIMATION && !onGround && this.getRidingEntity() != entity) {
				IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
				this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FASoundRegistry.COMPSOGNATHUS_LIVNIG;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.COMPSOGNATHUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.COMPSOGNATHUS_DEATH;
	}

	@Override
	public int getMaxHunger() {
		return 50;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}
}
