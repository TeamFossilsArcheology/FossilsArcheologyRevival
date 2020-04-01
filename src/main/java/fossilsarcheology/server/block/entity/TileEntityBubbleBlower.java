package fossilsarcheology.server.block.entity;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityBubbleBlower extends TileEntity implements ITickable {

    @Override
    public void update() {
        if(this.world.getRedstonePowerFromNeighbors(this.getPos()) > 0){
            float i = this.getPos().getX() + 0.5F;
            float j = this.getPos().getY() + 0.5F;
            float k = this.getPos().getZ() + 0.5F;
            float d0 = 1.5F;
            for (EntityPrehistoric dino : world.getEntitiesWithinAABB(EntityPrehistoric.class, new AxisAlignedBB((double) i - d0, (double) j - d0, (double) k - d0, (double) i + d0, (double) j + d0, (double) k + d0))) {
                dino.doPlayBonus(15);
            }
        }

    }
}
