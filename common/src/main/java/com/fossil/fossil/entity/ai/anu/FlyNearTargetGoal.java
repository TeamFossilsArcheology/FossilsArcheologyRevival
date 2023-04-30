package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;

import java.util.Random;

public class FlyNearTargetGoal extends Goal {
    private final AnuBoss anu;
    private Entity target;

    public FlyNearTargetGoal(AnuBoss anu) {
        this.anu = anu;
    }

    @Override
    public boolean canUse() {
        return anu.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        //TODO: Level height should not be above 10 to prevent falling into the void
        return anu.getTarget() != null;
    }

    @Override
    public void start() {
        target = anu.getTarget();
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        Random random = anu.getRandom();
        Vec3 motion = new Vec3(target.getX() + random.nextInt(20) - random.nextInt(10) + 0.5 - anu.getX(),
                target.getY() + target.getEyeHeight() + random.nextInt(20) - random.nextInt(10) + 1 - anu.getY(),
                target.getZ() + random.nextInt(40) - random.nextInt(10) + 0.5 - anu.getZ());
        if (motion.y < 1) {
            motion = new Vec3(random.nextInt(50) - 25, anu.getY() + random.nextInt(30) - 2, random.nextInt(50) - 25);
        }
        flyTowardsTarget(motion);
    }

    private void flyTowardsTarget(Vec3 motion) {
        Vec3 current = anu.getDeltaMovement();
        double newX = (Math.signum(motion.x) * 0.5 - current.x) * 0.10000000149011612;
        double newY = (Math.signum(motion.y) * 0.699999988079071 - current.y) * 0.10000000149011612;
        double newZ = (Math.signum(motion.z) * 0.5 - current.z) * 0.10000000149011612;
        Vec3 next = current.add(newX, newY, newZ);
        anu.setDeltaMovement(next);
        float angle = (float) (Math.atan2(next.z, next.x) * 180 / Math.PI) - 90;
        float rotation = MathHelper.wrapDegrees(angle - anu.getYRot());
        anu.setYRot(anu.getYRot() + rotation);
        anu.zza = 0.5F;
    }
}
