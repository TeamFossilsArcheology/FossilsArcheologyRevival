package fossilsarcheology.server.entity.prehistoric;

import fossilsarcheology.server.entity.EntityFishBase;
import net.minecraft.world.World;

public class EntitySturgeon extends EntityFishBase {
    public EntitySturgeon(World par1World) {
        super(par1World, PrehistoricEntityType.STURGEON);
        this.setSize(1.9F, 0.95F);
    }

    @Override
    public String getTexture() {
        return "fossil:textures/model/fish/sturgeon.png";
    }

    @Override
    protected double getSwimSpeed() {
        return 0.35D;
    }
}
