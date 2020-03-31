package fossilsarcheology.server.dimension;

import fossilsarcheology.Revival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;

public class AnuTeleporter implements ITeleporter {

    private int enteringDimension = 0;

    public AnuTeleporter(int enteringDimension) {
        this.enteringDimension = enteringDimension;
    }

    public void placeEntity(World world, Entity entity, float yaw) {
        if (enteringDimension == Revival.CONFIG_OPTIONS.dimensionIDDarknessLair) {
            entity.setPositionAndRotation(-74, 63, -115, entity.rotationYaw, 0.0F);
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posY);
            int k = MathHelper.floor(entity.posZ);
            byte b0 = 1;
            byte b1 = 0;
            for (int i1 = -2; i1 <= 2; ++i1) {
                for (int j1 = -2; j1 <= 2; ++j1) {
                    int k1 = i + i1;
                    int i2 = k + j1;
                    world.setBlockState(new BlockPos(k1, 60, i2), Blocks.OBSIDIAN.getDefaultState());
                }
            }
        } else if (enteringDimension == Revival.CONFIG_OPTIONS.homePortalExitDimension) {
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getBedLocation() != null) {
                BlockPos bedPos = ((EntityPlayer) entity).getBedLocation();
                entity.setLocationAndAngles(bedPos.getX() + 0.5D, bedPos.getY() + 1.5D, bedPos.getZ() + 0.5D, 0.0F, 0.0F);

            } else {
                BlockPos height = world.getHeight(entity.getPosition());
                entity.setLocationAndAngles(height.getX() + 0.5D, Math.max(height.getY(), 64) + 0.5D, height.getZ() + 0.5D, entity.rotationYaw, 0.0F);
            }

        }
        entity.motionX = entity.motionY = entity.motionZ = 0.0D;
    }

}