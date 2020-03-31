package fossilsarcheology.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFigurine extends TileEntity {

    private int figurineType;
    private int figurineRotation;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("FigurineType", figurineType);
        par1NBTTagCompound.setInteger("Rot", figurineRotation);
        return par1NBTTagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.figurineType = par1NBTTagCompound.getInteger("FigurineType");
        this.figurineRotation = par1NBTTagCompound.getInteger("Rot");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        return new SPacketUpdateTileEntity(pos, 0, this.writeToNBT(tag));
    }

    @Override
    public void onDataPacket(NetworkManager netManager, net.minecraft.network.play.server.SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }


    public int getFigurineType() {
        return this.figurineType;
    }

    public void setFigurineType(int par1) {
        this.figurineType = par1;
    }

    public int getFigurineRotation() {
        return this.figurineRotation;
    }

    public void setFigurineRotation(int par1) {
        this.figurineRotation = par1;
    }


}
