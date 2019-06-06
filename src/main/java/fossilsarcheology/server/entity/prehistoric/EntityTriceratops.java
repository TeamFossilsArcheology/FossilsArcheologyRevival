package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;


public class EntityTriceratops extends EntityPrehistoric {
	public static final double baseDamage = 1;
	public static final double maxDamage = 9;
	public static final double baseHealth = 12;
	public static final double maxHealth = 64;
	public static final double baseSpeed = 0.2D;
	public static final double maxSpeed = 0.35D;

	public EntityTriceratops(World world) {
		super(world, PrehistoricEntityType.TRICERATOPS, 1, 9, 12, 64, 0.2, 0.35, 5, 15);
		this.hasFeatherToggle = true;
		this.featherToggle = Revival.CONFIG_OPTIONS.quilledTriceratops;
		this.setActualSize(1.1F, 0.6F);
		this.nearByMobsAllowed = 7;
		minSize = 0.4F;
		maxSize = 5F;
		teenAge = 5;
		developsResistance = true;
		breaksBlocks = true;
		this.ridingY = 0.73F;
		this.ridingXZ = -0.05F;
		this.pediaScale = 55;
	}

	public void initEntityAI() {
		this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.tasks.addTask(3, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
		this.tasks.addTask(4, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	public int getAttackLength() {
		return 30;
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

		return this.isChild() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERITORIAL;
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
	public int getAdultAge() {
		return 12;
	}

	@Override
	public int getTailSegments() {
		return 3;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null) {
			doAttack();
			doAttackKnockback(0.5F);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (this.getAnimation() != ATTACK_ANIMATION) {
			this.setAnimation(ATTACK_ANIMATION);
		}
		return false;
	}


	@Override
	public int getMaxHunger() {
		return 175;
	}

	@Override
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
	}

	@Override
	public boolean canBeRidden() {
		return true;
	}
}
