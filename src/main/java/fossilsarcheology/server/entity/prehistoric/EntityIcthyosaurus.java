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
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityIcthyosaurus extends EntityPrehistoricSwimming {

	public EntityIcthyosaurus(World world) {
		super(world, PrehistoricEntityType.ICTHYOSAURUS, 1, 4, 10, 30, 0.1, 0.1, 0, 0);
		FISH_ANIMATION = Animation.create(40);
		this.setActualSize(1.2F, 1.0F);
		minSize = 0.3F;
		maxSize = 0.95F;
		teenAge = 3;
		developsResistance = true;
		pediaScale = 40;
	}

	public void initEntityAI() {
		this.tasks.addTask(0, new DinoAIFindWaterTarget(this, 10, true));
		this.tasks.addTask(0, new DinoAIFollowOwner(this, 1, 10, 2));
		this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new DinoAIEatBlocks(this));
		this.tasks.addTask(3, new DinoAIEatFeeders(this));
		this.tasks.addTask(3, new DinoAIEatItems(this));
		this.tasks.addTask(4, new DinoAIMakeFish(this));
		this.tasks.addTask(5, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new DinoAILookIdle(this));
		this.targetTasks.addTask(1, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
		this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
	}

	@Override
	public void setSpawnValues() {
	}

	@Override
	public PrehistoricEntityTypeAI.Activity aiActivityType() {
		return PrehistoricEntityTypeAI.Activity.BOTH;
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
		return PrehistoricEntityTypeAI.Following.NORMAL;
	}

	@Override
	public PrehistoricEntityTypeAI.Jumping aiJumpType() {
		return PrehistoricEntityTypeAI.Jumping.BASIC;
	}

	@Override
	public PrehistoricEntityTypeAI.Response aiResponseType() {
		return PrehistoricEntityTypeAI.Response.WATERCALM;
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
		return PrehistoricEntityTypeAI.Untaming.NONE;
	}

	@Override
	public PrehistoricEntityTypeAI.Moving aiMovingType() {
		return PrehistoricEntityTypeAI.Moving.AQUATIC;
	}

	@Override
	public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {
		return PrehistoricEntityTypeAI.WaterAbility.ATTACK;
	}

	@Override
	public boolean doesFlock() {
		return false;
	}

	@Override
	public Item getOrderItem() {
		return FAItemRegistry.SHELL;
	}

	@Override
	public int getAdultAge() {
		return 7;
	}

	@Override
	public String getTexture() {
		if (isSkeleton()) {
			return "fossil:textures/model/icthyosaurus_0/icthyosaurus_skeleton.png";
		}
		String gender = this.isChild() ? "_baby" : (this.getGender() == 0 ? "_female" : "_male");
		return "fossil:textures/model/icthyosaurus_0/icthyosaurus" + gender + ".png";
	}

	@Override
	public double swimSpeed() {
		return 0.75;
	}

	@Override
	public int getMaxHunger() {
		return 125;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, FISH_ANIMATION};
	}


	@Override
	protected SoundEvent getAmbientSound() {
		return this.isInWater() ? FASoundRegistry.ICTHYOSAURUS_LIVING : FASoundRegistry.ICTHYOSAURUS_OUTSIDE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FASoundRegistry.ICTHYOSAURUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FASoundRegistry.ICTHYOSAURUS_DEATH;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}

	public int getAttackLength() {
		return 10;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 5 && this.getAttackTarget() != null) {
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
