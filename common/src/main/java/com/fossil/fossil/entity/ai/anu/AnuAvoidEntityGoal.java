package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class AnuAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final AnuBoss anu;
    private final double sprintSpeedModifier;
    public AnuAvoidEntityGoal(AnuBoss anu, Class<T> clazz, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
        super(anu, clazz, maxDist, walkSpeedModifier, sprintSpeedModifier);
        this.anu = anu;
        this.sprintSpeedModifier = sprintSpeedModifier;
    }

    @Override
    public boolean canUse() {
        if (anu.getAttackMode() == AnuBoss.AttackMode.DEFENSE) {
            if (super.canUse()) {
                return true;
            }
            if (toAvoid != null) {
                float targetX = Mth.randomBetween(anu.getRandom(), -30, 30);
                float targetZ = Mth.randomBetween(anu.getRandom(), -30, 30);
                BlockPos targetPos = new BlockPos(anu.getSpawnPos().add(targetX, -6, targetZ));
                path = pathNav.createPath(targetPos, 0);
                if (path == null) {
                    anu.getMoveControl().setWantedPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ(), sprintSpeedModifier);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return anu.getAttackMode() == AnuBoss.AttackMode.DEFENSE && super.canContinueToUse();
    }
}
