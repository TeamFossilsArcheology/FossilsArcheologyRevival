package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.render.ArthropleuraBuffer;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityArthropleura extends EntityPrehistoric {

    @SideOnly(Side.CLIENT)
    public ArthropleuraBuffer bodyBuffer;

    public EntityArthropleura(World world) {
        super(world, PrehistoricEntityType.ARTHROPLEURA, 1, 4, 10, 34, 0.15, 0.2, 5, 15);
        this.teenAge = 4;
        this.setActualSize(3F, 1.0F);
        minSize = 0.1F;
        maxSize = 0.4F;
        developsResistance = true;
        hasTeenTexture = true;
        pediaScale = 40;
        if (FMLCommonHandler.instance().getSide().isClient()) {
            bodyBuffer = new ArthropleuraBuffer();
        }
    }

    protected EntityBodyHelper createBodyHelper() {
        return new EntityArthropleura.BodyHelper(this);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.BONELESS_LOOT;
    }

    @Override
    public String getTexture() {
        String gender = this.isChild() ? "_baby" : this.getGender() == 0 ? "_female" : "_male";
        if (this.isTeen()) {
            gender = "_teen";
        }
        if (this.isSkeleton()) {
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + "_skeleton.png";
        }
        return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + gender + ".png";
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFleeBattle(this, 1.0D));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIWander(this, 1.0D, 5));
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

        return PrehistoricEntityTypeAI.Response.SCARED;
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
        return 7;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote && !this.isAIDisabled()) {
            bodyBuffer.calculateChainSwingBuffer(120, 10, 2.5F, this);
        }
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
        return 125;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.ARTHROPLEURA_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.ARTHROPLEURA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.ARTHROPLEURA_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(FASoundRegistry.ARTHROPLEURA_WALK, 0.15F, 1.0F);
    }

    public boolean isMoving() {
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        return d0 * d0 + d1 * d1 > 0.500000277905201E-7D;
    }

    class MoveHelper extends EntityMoveHelper {

        public MoveHelper(EntityLiving entitylivingIn) {
            super(entitylivingIn);
        }

        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.STRAFE) {
                float f = (float) this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                float f1 = (float) this.speed * f;
                float f2 = this.moveForward;
                float f3 = this.moveStrafe;
                float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

                if (f4 < 1.0F) {
                    f4 = 1.0F;
                }

                f4 = f1 / f4;
                f2 = f2 * f4;
                f3 = f3 * f4;
                float f5 = MathHelper.sin(this.entity.rotationYaw * 0.017453292F);
                float f6 = MathHelper.cos(this.entity.rotationYaw * 0.017453292F);
                float f7 = f2 * f6 - f3 * f5;
                float f8 = f3 * f6 + f2 * f5;
                PathNavigate pathnavigate = this.entity.getNavigator();

                if (pathnavigate != null) {
                    NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();

                    if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.entity.world, MathHelper.floor(this.entity.posX + (double) f7), MathHelper.floor(this.entity.posY), MathHelper.floor(this.entity.posZ + (double) f8)) != PathNodeType.WALKABLE) {
                        this.moveForward = 1.0F;
                        this.moveStrafe = 0.0F;
                        f1 = f;
                    }
                }

                this.entity.setAIMoveSpeed(f1);
                this.entity.setMoveForward(this.moveForward);
                this.entity.setMoveStrafing(this.moveStrafe);
                this.action = EntityMoveHelper.Action.WAIT;
            } else if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                this.action = EntityMoveHelper.Action.WAIT;
                double d0 = this.posX - this.entity.posX;
                double d1 = this.posZ - this.entity.posZ;
                double d2 = this.posY - this.entity.posY;
                double d3 = d0 * d0 + d2 * d2 + d1 * d1;

                if (d3 < 2.500000277905201E-7D) {
                    this.entity.setMoveForward(0.0F);
                    return;
                }

                float f9 = (float) (MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f9, 90.0F);
                this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                if (d2 > (double) this.entity.stepHeight && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.entity.width)) {
                    this.entity.getJumpHelper().setJumping();
                    this.action = EntityMoveHelper.Action.JUMPING;
                }
            } else if (this.action == EntityMoveHelper.Action.JUMPING) {
                this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                if (this.entity.onGround) {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            } else {
                this.entity.setMoveForward(0.0F);
            }
        }

    }

    public class BodyHelper extends EntityBodyHelper {
        private EntityArthropleura living;
        /**
         * Used to progressively ajust the rotation of the body to the rotation of the head
         */
        private int rotationTickCounter;
        private float prevRenderYawHead;
        public BodyHelper(EntityArthropleura arthropleura) {
            super(arthropleura);
            this.living = arthropleura;
        }

        public void updateRenderAngles() {
            double d0 = this.living.posX - this.living.prevPosX;
            double d1 = this.living.posZ - this.living.prevPosZ;

            if (d0 * d0 + d1 * d1 > 1.500000277905201E-7D) {
                this.living.renderYawOffset = this.living.rotationYaw;
                this.living.rotationYawHead = this.computeAngleWithBound(this.living.renderYawOffset, this.living.rotationYawHead, 100.0F);
                this.prevRenderYawHead = this.living.rotationYawHead;
                this.rotationTickCounter = 0;
            } else {
                if (this.living.getPassengers().isEmpty() || !(this.living.getPassengers().get(0) instanceof EntityLiving)) {
                    float f = 75.0F;

                    if (Math.abs(this.living.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
                        this.rotationTickCounter = 0;
                        this.prevRenderYawHead = this.living.rotationYawHead;
                    } else {
                        ++this.rotationTickCounter;
                        int i = 10;

                        if (this.rotationTickCounter > 10) {
                            f = Math.max(1.0F - (float) (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
                        }
                    }

                    this.living.renderYawOffset = this.computeAngleWithBound(this.living.rotationYawHead, this.living.renderYawOffset, f);
                }
            }
        }

        private float computeAngleWithBound(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
            float f = MathHelper.wrapDegrees(p_75665_1_ - p_75665_2_);

            if (f < -p_75665_3_) {
                f = -p_75665_3_;
            }

            if (f >= p_75665_3_) {
                f = p_75665_3_;
            }

            return p_75665_1_ - f;
        }
    }
}
