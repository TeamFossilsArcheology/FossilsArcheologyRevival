package fossilsarcheology.server.message;

import fossilsarcheology.server.block.entity.TileEntityFeeder;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageUpdateFeeder extends AbstractMessage<MessageUpdateFeeder> {

    public long blockPos;
    public int meat;
    public int plant;

    public MessageUpdateFeeder(long blockPos, int meat, int plant) {
        this.blockPos = blockPos;
        this.meat = meat;
        this.plant = plant;

    }

    public MessageUpdateFeeder() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        blockPos = buf.readLong();
        meat = buf.readInt();
        plant = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(blockPos);
        buf.writeInt(meat);
        buf.writeInt(plant);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageUpdateFeeder message, EntityPlayer player, MessageContext messageContext) {
        BlockPos pos = BlockPos.fromLong(message.blockPos);
        if (client.world.getTileEntity(pos) != null && client.world.getTileEntity(pos) instanceof TileEntityFeeder) {
            TileEntityFeeder feeder = (TileEntityFeeder) client.world.getTileEntity(pos);
            feeder.setField(0, message.meat);
            feeder.setField(1, message.plant);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageUpdateFeeder message, EntityPlayer player, MessageContext messageContext) {
        BlockPos pos = BlockPos.fromLong(message.blockPos);
        if (player.world.getTileEntity(pos) != null && player.world.getTileEntity(pos) instanceof TileEntityFeeder) {
            TileEntityFeeder feeder = (TileEntityFeeder) player.world.getTileEntity(pos);
            feeder.setField(0, message.meat);
            feeder.setField(1, message.plant);
        }
    }
}