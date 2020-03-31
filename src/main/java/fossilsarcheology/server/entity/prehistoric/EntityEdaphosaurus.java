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

public class EntityEdaphosaurus extends EntityPrehistoric {

    public EntityEdaphosaurus(World world) {
        super(world, PrehistoricEntityType.EDAPHOSAURUS, 1, 5, 8, 30, 0.2, 0.3, 0,4);
        this.teenAge = 6;
        this.setActualSize(1.3F, 1.7F);
        minSize = 0.2F;
        maxSize = 1.0F;
        teenAge = 2;
        developsResistance = false;
        breaksBlocks = false;
        hasTeenTexture = false;
        pediaScale = 40;
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFleeBattle(this, 1.0D));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(3, new DinoAIEatBlocks(this));
        this.tasks.addTask(3, new DinoAIEatFeeders(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIPanic(this, 1.5D));
        this.tasks.addTask(6, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new DinoAILookIdle(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Override
    public int getAttackLength() {
        return 25;
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

        return PrehistoricEntityTypeAI.Attacking.BASIC;
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

        return PrehistoricEntityTypeAI.Response.TERITORIAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {

        return PrehistoricEntityTypeAI.Stalking.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {

        return PrehistoricEntityTypeAI.Taming.FEEDING;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {

        return PrehistoricEntityTypeAI.Untaming.NONE;
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
        return 8;
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
        if (this.getAnimation() != ATTACK_ANIMATION) {
            this.setAnimation(ATTACK_ANIMATION);
        }
        return false;
    }

    @Override
    public int getMaxHunger() {
        return 80;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.EDAPHOSAURUS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.EDAPHOSAURUS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.EDAPHOSAURUS_DEATH;
    }
}
