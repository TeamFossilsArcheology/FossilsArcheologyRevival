package fossilsarcheology.server.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.ai.FishAIFindWaterTarget;
import fossilsarcheology.server.entity.prehistoric.EntityNautilus;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.item.FAItemRegistry;
import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class EntityFishBase extends EntityTameable {
    public final PrehistoricEntityType selfType;
    public BlockPos currentTarget;
    @SideOnly(Side.CLIENT)
    public ChainBuffer chainBuffer;

    public EntityFishBase(World world, PrehistoricEntityType selfType) {
        super(world);
        this.spawnableBlock = Blocks.WATER;
        this.moveHelper = new EntityFishBase.SwimmingMoveHelper();
        this.navigator = new PathNavigateSwimmer(this, world);
        this.selfType = selfType;
        if (FMLCommonHandler.instance().getSide().isClient()) {
            this.chainBuffer = new ChainBuffer();
        }
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new FishAIFindWaterTarget(this));
        this.tasks.addTask(1, new EntityAILookIdle(this));
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    public abstract String getTexture();

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    private void setPedia() {
        Revival.PEDIA_OBJECT = this;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.FISH_LOOT;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public void swimTowardsTarget() {
        if (currentTarget != null && isTargetInWater() && this.inWater) {
            double targetX = currentTarget.getX() + 0.5D - posX;
            double targetY = currentTarget.getY() + 1D - posY;
            double targetZ = currentTarget.getZ() + 0.5D - posZ;
            motionX += (Math.signum(targetX) * 0.5D - motionX) * 0.100000000372529 * getSwimSpeed(); // 0.10000000149011612D
            // /
            // 2;
            motionY += (Math.signum(targetY) * 0.5D - motionY) * 0.100000000372529 * getSwimSpeed();// 0.10000000149011612D
            // /
            // 2;
            motionZ += (Math.signum(targetZ) * 0.5D - motionZ) * 0.100000000372529 * getSwimSpeed(); // 0.10000000149011612D
            // /
            // 2;
            float angle = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
            float rotation = MathHelper.wrapDegrees(angle - rotationYaw);
            moveForward = 0.5F;
            rotationYaw += rotation;
        }
    }

    protected abstract double getSwimSpeed();

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.height != 0.95F) {
            this.height = 0.95F;
        }
        Revival.PROXY.calculateChainBuffer(this);
        if (this.isInWater() && this.getClosestMate() != null && this.getGrowingAge() == 0 && this.getClosestMate().getGrowingAge() == 0 && !this.world.isRemote) {
            this.setGrowingAge(12000);
            this.getClosestMate().setGrowingAge(12000);
            this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.selfType.eggItem)));
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setGrowingAge(12000);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public EntityFishBase getClosestMate() {
        EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(this);
        List<EntityFishBase> list = world.getEntitiesWithinAABB(EntityFishBase.class, this.getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D), null);
        list.sort(theNearestAttackableTargetSorter);

        if (list.isEmpty()) {
            return null;
        } else {
            for (EntityFishBase entity : list) {
                if (entity != this) {
                    return entity.selfType == this.selfType ? entity : null;
                }
            }
            return null;
        }
    }

    public boolean isInsideNautilusShell() {
        return this instanceof EntityNautilus && ((EntityNautilus) this).isInShell();
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() || this.isInsideOfMaterial(Material.WATER) || this.isInsideOfMaterial(Material.CORAL);
    }

    protected boolean isTargetInWater() {
        return currentTarget != null && (world.getBlockState(new BlockPos(currentTarget.getX(), currentTarget.getY(), currentTarget.getZ())).getMaterial() == Material.WATER && world.getBlockState(new BlockPos(currentTarget.getX(), currentTarget.getY() + 1, currentTarget.getZ())).getMaterial() == Material.WATER);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack != ItemStack.EMPTY && FMLCommonHandler.instance().getSide().isClient() && stack.getItem() == FAItemRegistry.DINOPEDIA) {
            this.setPedia();
            player.openGui(Revival.INSTANCE, 6, this.world, (int) this.posX, (int) this.posY, (int) this.posZ);
            return true;
        }
        if (this.isInsideNautilusShell()) {
            if (stack != ItemStack.EMPTY && stack.getItem() == Items.FLINT) {
                ((EntityNautilus) this).setInShell(false);
                ((EntityNautilus) this).ticksToShell = 60;
            } else if (stack.getItem() != FAItemRegistry.DINOPEDIA) {
                this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, this.getRNG().nextFloat() + 0.8F);
                return false;
            }
        }
        if (stack.isEmpty() && this.getGrowingAge() > 0) {
            ItemStack var3 = new ItemStack(this.selfType.fishItem, 1);
            if (!player.inventory.addItemStackToInventory(var3)) {
                player.dropItem(var3, false);
            }
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1, this.getRNG().nextFloat() + 0.8F);
            this.setDead();
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.posY < (double) this.world.getSeaLevel() && this.isInWater();
    }

    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();
        if (!this.isInsideNautilusShell()) {
            if (this.isEntityAlive() && !this.isInWater()) {
                --i;
                this.setAir(i);

                if (this.getAir() == -20) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAir(300);
            }
        }
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.renderYawOffset = this.rotationYaw;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        return null;
    }

    public boolean isDirectPathBetweenPoints(Vec3d vec1, Vec3d vec2) {
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y, vec2.z), false, true, false);
        return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        float f4;
        if (this.isSitting() || this.isInsideNautilusShell()) {
            super.travel(0, 0, 0);
            return;
        }
        if (this.isBeingRidden() && this.canBeSteered()) {
            EntityLivingBase controller = (EntityLivingBase) this.getControllingPassenger();
            if (controller != null) {
                strafe = controller.moveStrafing * 0.5F;
                forward = controller.moveForward;
                if (forward <= 0.0F) {
                    forward *= 0.25F;
                }
                this.fallDistance = 0;
                if (this.isInWater()) {
                    this.moveRelative(strafe, vertical, forward, 1F);
                    f4 = 0.8F;
                    float d0 = 3;
                    if (!this.onGround) {
                        d0 *= 0.5F;
                    }
                    if (d0 > 0.0F) {
                        f4 += (0.54600006F - f4) * d0 / 3.0F;
                    }
                    //this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= (double) f4;
                    this.motionX *= 0.900000011920929D;
                    this.motionY *= 0.900000011920929D;
                    this.motionY *= (double) f4;
                    this.motionZ *= 0.900000011920929D;
                    this.motionZ *= (double) f4;
                    motionY += 0.01185D;
                } else {
                    forward = controller.moveForward * 0.25F;
                    strafe = controller.moveStrafing * 0.125F;

                    this.setAIMoveSpeed(2);
                    super.travel(strafe, vertical, forward);
                    return;
                }
                this.setAIMoveSpeed(2);
                super.travel(strafe, vertical = 0, forward);
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double deltaX = this.posX - this.prevPosX;
                double deltaZ = this.posZ - this.prevPosZ;
                double deltaY = this.posY - this.prevPosY;
                float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
                if (delta > 1.0F) {
                    delta = 1.0F;
                }
                this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
                return;
            }
        }
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveRelative(strafe, vertical, forward, 0.1F);
                f4 = 0.8F;
                float speedModifier = (float) EnchantmentHelper.getDepthStriderModifier(this);
                if (speedModifier > 3.0F) {
                    speedModifier = 3.0F;
                }
                if (!this.onGround) {
                    speedModifier *= 0.5F;
                }
                if (speedModifier > 0.0F) {
                    f4 += (0.54600006F - f4) * speedModifier / 3.0F;
                }
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= (double) f4;
                this.motionX *= 0.9;
                this.motionY *= 0.9;
                this.motionY *= (double) f4;
                this.motionZ *= 0.9;
                this.motionZ *= (double) f4;
            } else {
                super.travel(strafe, vertical, forward);
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double deltaX = this.posX - this.prevPosX;
        double deltaZ = this.posZ - this.prevPosZ;
        double deltaY = this.posY - this.prevPosY;
        float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
        if (delta > 1.0F) {
            delta = 1.0F;
        }
        this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    class SwimmingMoveHelper extends EntityMoveHelper {
        private final EntityFishBase dinosaur = EntityFishBase.this;

        public SwimmingMoveHelper() {
            super(EntityFishBase.this);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.dinosaur.getNavigator().noPath() && !this.dinosaur.isBeingRidden()) {
                if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.dinosaur.getNavigator().noPath()) {
                    double distanceX = this.posX - this.dinosaur.posX;
                    double distanceY = this.posY - this.dinosaur.posY;
                    double distanceZ = this.posZ - this.dinosaur.posZ;
                    double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                    distance = (double) MathHelper.sqrt(distance);
                    distanceY /= distance;
                    float angle = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                    this.dinosaur.rotationYaw = this.limitAngle(this.dinosaur.rotationYaw, angle, 30.0F);
                    this.dinosaur.setAIMoveSpeed(0.65F);
                    this.dinosaur.motionY += (double) this.dinosaur.getAIMoveSpeed() * distanceY * 0.1D;
                } else {
                    this.dinosaur.setAIMoveSpeed(0.0F);
                }
            }
        }
    }
}
