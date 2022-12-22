package fossilsarcheology.server.entity.prehistoric.base;

import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

public class DinosaurPathNavigator extends GroundPathNavigator {
    public DinosaurPathNavigator(EntityPrehistoric prehistoric, World worldIn) {
        super(prehistoric, worldIn);
    }
}
