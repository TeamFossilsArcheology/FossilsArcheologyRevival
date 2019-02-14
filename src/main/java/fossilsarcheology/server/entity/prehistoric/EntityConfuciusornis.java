package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI.*;
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

public class EntityConfuciusornis extends EntityPrehistoricFlying {

	public EntityConfuciusornis(World world) {
		super(world, PrehistoricEntityType.CONFUCIUSORNIS, 1, 1, 4, 12, 0.15, 0.25, 0, 0);
			this.setActualSize(0.7F, 0.8F);
		minSize = 0.2F;
		maxSize = 0.55F;
		teenAge = 2;
		developsResistance = false;
		breaksBlocks = false;
		pediaScale = 47;
	}

	public void initEntityAI() {
		this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.tasks.addTask(3, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAIFindAirTarget(this));
		this.tasks.addTask(5, new DinoAILeapAtTarget(this));
		this.tasks.addTask(6, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new DinoAILookIdle(this));
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
		return Attacking.BASIC;
	}

	@Override
	public Climbing aiClimbType() {
		return Climbing.NONE;
	}

	@Override
	public Following aiFollowType() {
		return Following.SKITTISH;
	}

	@Override
	public Jumping aiJumpType() {
		return Jumping.BASIC;
	}

	@Override
	public Response aiResponseType() {
		return Response.SCARED;
	}

	@Override
	public Stalking aiStalkType() {
		return Stalking.STEALTH;
	}

	@Override
	public Taming aiTameType() {
		return Taming.FEEDING;
	}

	@Override
	public Untaming aiUntameType() {
		return Untaming.NONE;
	}

	@Override
	public Moving aiMovingType() {
		return Moving.FLIGHT;
	}

	@Override
	public WaterAbility aiWaterAbilityType() {
		return WaterAbility.NONE;
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
		return 4;
	}

	@Override
	protected double getFlySpeed() {
		return 1;
	}

	@Override
	public int getMaxHunger() {
		return 25;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FASoundRegistry.CONFUSCIUSORNIS_LIVNIG;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.CONFUSCIUSORNIS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.CONFUSCIUSORNIS_DEATH;
	}

	@Override
 	public float getMaleSize() {
  		return 0.8F;
 	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null) {
			doAttack();
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (this.getAnimation() == NO_ANIMATION) {
			this.setAnimation(ATTACK_ANIMATION);
		}
		return false;
	}
}
