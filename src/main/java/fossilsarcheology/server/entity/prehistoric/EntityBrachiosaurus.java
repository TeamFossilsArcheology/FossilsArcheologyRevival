package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI.*;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityBrachiosaurus extends EntityPrehistoric {

    public EntityBrachiosaurus(World world) {
        super(world, PrehistoricEntityType.BRACHIOSAURUS, 2, 46, 20, 200, 0.3, 0.45);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(3, new DinoAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(5, new DinoAIEatBlocks(this, 1));
        this.tasks.addTask(5, new DinoAIEatFeeders(this, 1));
        this.tasks.addTask(5, new DinoAIEatItems(this, 1));
        this.tasks.addTask(6, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, false, new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                return entity instanceof EntityLivingBase;
            }
        }));
        this.setActualSize(1.5F, 1.7F);
        this.nearByMobsAllowed = 10;
        minSize = 1;
        maxSize = 5.8F;
        teenAge = 9;
        developsResistance = true;
        breaksBlocks = true;
        this.ridingY = 1.5F;
        this.ridingXZ = -0.2F;
        ATTACK_ANIMATION = Animation.create(30);
        this.pediaScale = 35F;
    }

    @Override
    public int getAttackLength() {
        return 30;
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

        return Attacking.STOMP;
    }

    @Override
    public Climbing aiClimbType() {

        return Climbing.NONE;
    }

    @Override
    public Following aiFollowType() {

        return Following.NONE;
    }

    @Override
    public Jumping aiJumpType() {

        return Jumping.BASIC;
    }

    @Override
    public Response aiResponseType() {

        return Response.NONE;
    }

    @Override
    public Stalking aiStalkType() {

        return Stalking.NONE;
    }

    @Override
    public Taming aiTameType() {

        return Taming.IMPRINTING;
    }

    @Override
    public Untaming aiUntameType() {

        return Untaming.STARVE;
    }

    @Override
    public Moving aiMovingType() {

        return Moving.WALK;
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
        return 20;
    }

    @Override
    public int getTailSegments() {
        return 3;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() > 19 && this.getAttackTarget() != null && this.getAttackBounds().intersects(this.getAttackTarget().getEntityBoundingBox())) {
            this.attackEntityAsMob(this.getAttackTarget());
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (this.getAnimation() != ATTACK_ANIMATION) {
            this.setAnimation(ATTACK_ANIMATION);
            return false;
        }
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() > 19) {
            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
            if (entity.getRidingEntity() != null) {
                if (entity.isRidingOrBeingRiddenBy(this)) {
                    entity.dismountRidingEntity();
                }
            }
            entity.motionY -= 0.4000000059604645D;
            knockbackEntity(entity, -2F, -0.1F);
            return flag;
        }
        return false;
    }

    public int getMaxHunger() {
        return 250;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.BRACHIOSAURUS_LIVNIG;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.BRACHIOSAURUS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.BRACHIOSAURUS_DEATH;
    }
}
