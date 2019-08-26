package fossilsarcheology.server.message;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageUpdateDinoState extends AbstractMessage<MessageUpdateDinoState> {

    public int dragonId;
    public byte controlState;
    private double posX;
    private double posY;
    private double posZ;

    public MessageUpdateDinoState(int dragonId, byte controlState, double posX, double posY, double posZ) {
        this.dragonId = dragonId;
        this.controlState = controlState;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public MessageUpdateDinoState() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
        controlState = buf.readByte();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeByte(controlState);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageUpdateDinoState message, EntityPlayer player, MessageContext messageContext) {
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageUpdateDinoState message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = player.world.getEntityByID(message.dragonId);
        if (entity != null && entity instanceof EntityPrehistoric) {
            EntityPrehistoric dino = (EntityPrehistoric) entity;
            if (dino.isOwner(player)) {
                dino.setControlState(message.controlState);
            }
            dino.setPosition(message.posX, message.posY, message.posZ);
        }
    }
}
