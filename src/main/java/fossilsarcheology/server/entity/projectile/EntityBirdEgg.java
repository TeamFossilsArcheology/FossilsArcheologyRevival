package fossilsarcheology.server.entity.projectile;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class EntityBirdEgg extends EntityThrowable {
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityBirdEgg.class, DataSerializers.VARINT);
    final boolean cultivated;

    public EntityBirdEgg(World par1World) {
        super(par1World);
        this.cultivated = false;
    }

    public EntityBirdEgg(boolean cultivated, World par1World) {
        super(par1World);
        this.cultivated = cultivated;
    }

    public EntityBirdEgg(boolean cultivated, World par1World, double posX, double posY, double posZ) {
        super(par1World);
        this.cultivated = cultivated;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public EntityBirdEgg(World par1World, EntityLivingBase par2EntityLivingBase, boolean cultivated) {
        super(par1World, par2EntityLivingBase);
        this.cultivated = cultivated;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, PrehistoricEntityType.CHICKEN.ordinal());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Type", this.getType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setType(compound.getInteger("Type"));

    }

    public int getType() {
        return this.dataManager.get(TYPE);
    }

    public void setType(int ordinal) {
        this.dataManager.set(TYPE, ordinal);
    }

    public PrehistoricEntityType getEnumType() {
        return PrehistoricEntityType.values()[MathHelper.clamp(getType(), 0, PrehistoricEntityType.values().length - 1)];
    }

    public void setEnumType(PrehistoricEntityType type) {
        this.setType(type.ordinal());
    }

    public String getTexture() {
        return cultivated ? "fossil/items/prehistoric/birdEggs/Egg_" + getEnumType().toString() + ".png" : "fossil/items/prehistoric/birdEggs/Egg_Cultivated" + getEnumType().toString() + ".png";
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
        }
        if (this.cultivated) {
            this.spawnAnimal();

        } else {
            if (!this.world.isRemote && this.rand.nextInt(8) == 0) {
                byte b0 = 1;

                if (this.rand.nextInt(32) == 0) {
                    b0 = 4;
                }

                for (int i = 0; i < b0; ++i) {
                    this.spawnAnimal();

                }
                for (int j = 0; j < 8; ++j) {
                    this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (!this.world.isRemote) {
            this.setDead();
        }

    }

    private void spawnAnimal() {
        if (getEnumType().mobType != MobType.CHICKEN) {
            EntityPrehistoric mob = (EntityPrehistoric) getEnumType().invokeClass(world);
            if (!world.isRemote && mob != null) {
                mob.setAgeInDays(0);
                mob.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                this.world.spawnEntity(mob);
                mob.setTamed(true);
                if (world.getClosestPlayerToEntity(mob, 5) != null) {
                    mob.setOwnerId(world.getClosestPlayerToEntity(mob, 5).getUniqueID());
                }
                mob.setGender(new Random().nextBoolean() ? 0 : 1);
            }
        } else {
            EntityAgeable mob = null;
            switch (getEnumType()) {
                case PARROT:
                    mob = new EntityParrot(world);
                    mob.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
                    break;
                default:
                    mob = new EntityChicken(world);
                    break;
            }
            if (!world.isRemote && mob != null) {
                mob.setGrowingAge(-24000);
                mob.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                this.world.spawnEntity(mob);
            }
        }
    }

}
