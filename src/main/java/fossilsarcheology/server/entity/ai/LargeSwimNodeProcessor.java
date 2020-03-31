package fossilsarcheology.server.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class LargeSwimNodeProcessor extends SwimNodeProcessor {

    @Override
    public void init(IBlockAccess sourceIn, EntityLiving mob) {
        this.blockaccess = sourceIn;
        this.entity = mob;
        this.pointMap.clearMap();
        this.entitySizeX = MathHelper.floor(mob.width + 1.0F);
        this.entitySizeY = 1;
        this.entitySizeZ = MathHelper.floor(mob.width + 1.0F);
    }

}
