package fossilsarcheology.server.dimension;

import fossilsarcheology.Revival;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TreasureTeleporter extends Teleporter {
    private final WorldServer worldServerInstance;
    private final Random random;

    public TreasureTeleporter(WorldServer worldserver) {
        super(worldserver);
        this.worldServerInstance = worldserver;
        this.random = new Random(worldserver.getSeed());
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        if (worldServerInstance.provider.getDimension() == Revival.CONFIG_OPTIONS.dimensionIDTreasure) {
            entity.setPositionAndRotation(0, 60, 0, 0, 0);
        }
        this.placeInPortal(entity);
        return false;
    }

    public void placeInPortal(Entity entity) {
        entity.motionX = entity.motionY = entity.motionZ = 0.0D;
    }

    @Override
    public boolean makePortal(Entity e) {
        return true;
    }
}