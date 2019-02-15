package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.EnumDifficulty;

public class DinoMeleeAttackAI extends EntityAIBase {
    private final EntityPrehistoric entity;
    private final double speed;
    private final boolean memory;
    private int attackTick;
    private Path currentPath;

    public DinoMeleeAttackAI(EntityPrehistoric entity, double speed, boolean memory) {
        this.entity = entity;
        this.speed = speed;
        this.memory = memory;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.entity.getAttackTarget();
        if (target == null || !target.isEntityAlive()) {
            return false;
        } else if (this.entity.isMovementBlocked()) {
            return false;
        } else if (this.entity.world.getDifficulty() == EnumDifficulty.PEACEFUL && target instanceof EntityPlayer) {
            return false;
        }
        this.currentPath = this.entity.getNavigator().getPathToEntityLiving(target);
        return this.currentPath != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        EntityLivingBase entity = this.entity.getAttackTarget();
        return entity != null && (entity.isEntityAlive() && (!this.memory ? !this.entity.getNavigator().noPath() : this.entity.isWithinHomeDistanceFromPosition(entity.getPosition())));
    }

    @Override
    public void startExecuting() {
        if (this.entity.getControllingPassenger() == null) {
            this.entity.getNavigator().setPath(this.currentPath, this.speed);
        }
    }

    @Override
    public void resetTask() {
        this.entity.getNavigator().clearPath();
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.entity.getAttackTarget();
        if (target == null) {
            return;
        }
        if (target instanceof EntityToyBase && this.entity.ticksTillPlay > 0) {
            this.entity.setAttackTarget(null);
            return;
        }
        if (this.entity.getControllingPassenger() == null) {
            this.entity.getNavigator().tryMoveToEntityLiving(target, this.speed);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (this.entity.getAttackBounds().intersects(target.getEntityBoundingBox())) {
            this.entity.swingArm(EnumHand.MAIN_HAND);
            this.attackTick = 20;
            this.entity.attackEntityAsMob(target);
            if(target instanceof EntityToyBase && this.entity.ATTACK_ANIMATION != null){
                this.entity.setAnimation(this.entity.ATTACK_ANIMATION);
            }
        } else {
            this.entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        }
    }
}
