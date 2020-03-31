package fossilsarcheology.server.block.entity;

import fossilsarcheology.server.entity.monster.EntityAnubite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityAnubiteStatue extends TileEntity implements ITickable {

    @Override
    public void update() {
        EntityPlayer player = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
        if (player != null) {
            if (!player.capabilities.isCreativeMode) {
                world.newExplosion(null, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5, 5F, true, true);
                EntityAnubite newMob = new EntityAnubite(world);
                if (!world.isRemote) {
                    newMob.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                    world.spawnEntity(newMob);
                    world.removeTileEntity(pos);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, net.minecraft.network.play.server.SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

}
