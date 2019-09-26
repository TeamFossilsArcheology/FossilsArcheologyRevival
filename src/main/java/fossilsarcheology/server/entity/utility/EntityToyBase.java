package fossilsarcheology.server.entity.utility;


import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class EntityToyBase extends EntityLiving {

	public final int toyBonus;
	private boolean spawnedItem = false;
	public EntityToyBase(World world, int toyBonus) {
		super(world);
		this.toyBonus = toyBonus;

	}

	@Override
    public boolean isAIDisabled() {
		return false;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
    public boolean attackEntityFrom(DamageSource dmg, float f) {
		if (dmg.getTrueSource() != null) {
			if (dmg.getTrueSource() instanceof EntityPlayer) {
				this.playSound(getAttackNoise(), 1, this.getSoundPitch());
				if (!this.world.isRemote && !spawnedItem){
					this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItem()));
					spawnedItem = true;
				}
				this.setDead();
				return true;
			}
			if (dmg.getTrueSource() instanceof EntityPrehistoric) {
				((EntityPrehistoric) dmg.getTrueSource()).doPlayBonus(toyBonus);
				if (getAttackNoise() != null) {
					this.playSound(getAttackNoise(), 1, this.getSoundPitch());
				}
				return false;
			}
		}
		return dmg != DamageSource.OUT_OF_WORLD;
	}

	@Override
    public boolean canBreatheUnderwater() {
		return true;
	}

	protected abstract ItemStack getItem();

	protected abstract SoundEvent getAttackNoise();

}
