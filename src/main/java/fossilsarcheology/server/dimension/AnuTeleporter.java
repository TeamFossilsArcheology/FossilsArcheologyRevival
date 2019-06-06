package fossilsarcheology.server.dimension;

import fossilsarcheology.Revival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class AnuTeleporter extends Teleporter {
	private final WorldServer worldServerInstance;
	private final Random random;

	public AnuTeleporter(WorldServer worldserver) {
		super(worldserver);
		this.worldServerInstance = worldserver;
		this.random = new Random(worldserver.getSeed());
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
		this.placeInPortal(entity);
		return true;
	}

	public void placeInPortal(Entity entity) {
		if (worldServerInstance.provider.getDimension() == Revival.CONFIG_OPTIONS.dimensionIDDarknessLair) {
			entity.setLocationAndAngles((double) -74, (double) 63, (double) -115, entity.rotationYaw, 0.0F);
			int i = MathHelper.floor(entity.posX);
			int j = MathHelper.floor(entity.posY) - 2;
			int k = MathHelper.floor(entity.posZ);
			byte b0 = 1;
			byte b1 = 0;
			for (int l = -2; l <= 2; ++l) {
				for (int i1 = -2; i1 <= 2; ++i1) {
					for (int j1 = -1; j1 < 3; ++j1) {
						int k1 = i + i1 * b0 + l * b1;
						int l1 = j + j1;
						int i2 = k + i1 * b1 - l * b0;
						boolean flag = j1 < 0;
						this.worldServerInstance.setBlockState(new BlockPos(k1, 62, i2), Blocks.OBSIDIAN.getDefaultState());
					}
				}
			}
		}else if(worldServerInstance.provider.getDimension() == Revival.CONFIG_OPTIONS.homePortalExitDimension){
			if(entity instanceof EntityPlayer && ((EntityPlayer) entity).getBedLocation() != null){
				BlockPos bedPos = ((EntityPlayer) entity).getBedLocation();
				entity.setLocationAndAngles(bedPos.getX() + 0.5D, bedPos.getY() + 1.5D, bedPos.getZ() + 0.5D, 0.0F, 0.0F);

			}else{
				BlockPos height = worldServerInstance.getHeight(entity.getPosition());
				entity.setLocationAndAngles(height.getX() + 0.5D, Math.max(height.getY(), 64) + 0.5D, height.getZ() + 0.5D, entity.rotationYaw, 0.0F);
			}

		}
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
	}

	@Override
	public boolean makePortal(Entity e) {
		return true;
	}
}