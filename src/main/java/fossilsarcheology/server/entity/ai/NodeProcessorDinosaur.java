package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.world.IBlockAccess;

public class NodeProcessorDinosaur extends WalkNodeProcessor {

    public void init(IBlockAccess sourceIn, EntityLiving mob) {
        super.init(sourceIn, mob);
        if(!Revival.CONFIG_OPTIONS.intensivePathfinding){
            this.entitySizeX = 1;
            this.entitySizeY = 1;
            this.entitySizeZ = 1;
        }
    }
}
