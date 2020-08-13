package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.util.FoodMappings;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDiplodocus extends EntityPrehistoric {

    public static final Animation ANIMATION_EATLEAVES = Animation.create(50);

    public EntityDiplodocus(World world) {
        super(world, PrehistoricEntityType.DIPLODOCUS, 2, 40, 15, 170, 0.2, 0.3, 0, 15);
        this.setActualSize(1.85F, 1.85F);
        this.nearByMobsAllowed = 8;
        minSize = 0.2F;
        maxSize = 2.9F;
        teenAge = 9;
        developsResistance = true;
        breaksBlocks = true;
        this.ridingY = 2.0F;
        this.ridingXZ = -0.2F;
        this.pediaScale = 30F;
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFleeBattle(this, 1.0D));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Override
    public int getAttackLength() {
        return 40;
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
        return PrehistoricEntityTypeAI.Attacking.TAILSWING;
    }

    @Override
    public PrehistoricEntityTypeAI.Climbing aiClimbType() {
        return PrehistoricEntityTypeAI.Climbing.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Following aiFollowType() {
        return PrehistoricEntityTypeAI.Following.NONE;
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
        return 20;
    }

    @Override
    public int getTailSegments() {
        return 4;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.isHungry() && !this.isMovementBlockedSoft() && this.getAnimation() == NO_ANIMATION && rand.nextInt(100) == 0 && this.posY < world.getHeight((int)posX, (int)posZ)){
            BlockPos pos = new BlockPos(this).up((int) height);
            float scale = this.getAgeScale() / maxSize;
            while(pos.getY() < 256 && pos.getY() - this.posY <= 19F * scale){
                if(world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos)){
                    break;
                }
                pos = pos.up();
            }
            if(world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos)){
                this.setAnimation(ANIMATION_EATLEAVES);
            }
        }
        if(this.getAnimation() == ANIMATION_EATLEAVES){
            this.motionX = 0.0D;
            this.motionZ = 0.0D;
        }
        if(this.getAnimation() == ANIMATION_EATLEAVES && this.getAnimationTick() == 16){
            BlockPos pos = new BlockPos(this).up((int) height);
            while(pos.getY() < 256 && pos.getY() - this.posY <= 19F){
                if(world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos)){
                    break;
                }
                pos = pos.up();
            }
            if(world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos)){
                int range = 2;
                for(int i = -range; i < range; i++){
                    for(int j = -range; j < range; j++){
                        for(int k = -range; k < range; k++){
                            BlockPos leafPos = pos.add(i, j, k);
                            if(world.getBlockState(leafPos).getBlock().isLeaves(world.getBlockState(leafPos), world, leafPos)) {
                                this.setHunger(Math.min(this.getMaxHunger(), this.getHunger() + FoodMappings.INSTANCE.getBlockFoodAmount(world.getBlockState(leafPos).getBlock(), this.type.diet)));
                                world.destroyBlock(leafPos, false);
                            }
                        }
                    }
                }
            }
        }
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 19 && this.getAttackTarget() != null) {
            doAttack();
            doAttackKnockback(0.5F);
        }
    }

    public AxisAlignedBB getAttackBounds() {
        return this.getEntityBoundingBox().grow(6.0F, 6.0F, 6.0F);
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
        return 275;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.DIPLODOCUS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.DIPLODOCUS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.DIPLODOCUS_DEATH;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, ANIMATION_EATLEAVES};
    }

}
