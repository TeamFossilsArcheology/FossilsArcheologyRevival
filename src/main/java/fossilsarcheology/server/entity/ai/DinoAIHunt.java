package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.FoodHelper;
import fossilsarcheology.server.entity.prehistoric.*;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;

import java.util.function.Predicate;

public class DinoAIHunt<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
    private final EntityPrehistoric dino;

    public DinoAIHunt(EntityPrehistoric entityIn, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector) {
        super(entityIn, classTarget, 0, checkSight, true, targetSelector::test);
        this.dino = entityIn;
    }

    @Override
    public boolean shouldExecute() {
        if (this.dino.isBeingRidden() || this.dino.isMovementBlockedSoft()) {
            return false;
        }
        if (super.shouldExecute() && this.targetEntity != null && !this.targetEntity.getClass().equals(this.dino.getClass())) {
            if(targetEntity instanceof EntityToyBase && dino.ticksTillPlay == 0){
                return true;
            }
            double targetWidth = this.targetEntity.width;
            if(this.targetEntity.getEntityBoundingBox() != null){
                targetWidth = this.targetEntity.getEntityBoundingBox().getAverageEdgeLength();
            }
            if (this.dino.getEntityBoundingBox().getAverageEdgeLength() * dino.getTargetScale() >= targetWidth || (dino.getMoodFace() == PrehistoricMoodType.ANGRY || dino.getMoodFace() == PrehistoricMoodType.SAD) && this.targetEntity instanceof EntityPlayer) {
                if (!dino.isMovementBlockedSoft()) {
                    if (targetEntity instanceof EntityPlayer && ((EntityPlayer) targetEntity).isCreative()) {
                        return false;
                    }
                    if (targetEntity instanceof EntityPlayer) {
                        if (dino.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                            return false;
                        }
                        if (dino.getMoodFace() == PrehistoricMoodType.ANGRY || dino.getMoodFace() == PrehistoricMoodType.SAD && !dino.isOwner(targetEntity)) {
                            return true;
                        } else if (dino.getMood() > 25 && dino.getMoodFace() != PrehistoricMoodType.CALM) {
                            return false;
                        } else {
                            return !dino.isOwner(targetEntity) && dino.canDinoHunt(targetEntity, true);
                        }
                    }

                    if (FoodHelper.getMobFoodPoints(targetEntity, dino.type.diet) > 0 || dino.aiResponseType() == PrehistoricEntityTypeAI.Response.AGRESSIVE) {
                        return !dino.isOwner(targetEntity) && dino.canDinoHunt(targetEntity, true);
                    }
					return targetEntity instanceof EntityToyBase && dino.ticksTillPlay == 0 && dino.getMood() < 100;
                }
            }
        }
        return false;
    }

    @Override
    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        double yDist = 4.0D;
        if(dino instanceof EntityPrehistoricFlying){
            yDist = targetDistance;
        }else if(dino instanceof EntityPrehistoricSwimming){
            yDist = ((EntityPrehistoricSwimming) dino).doesBreachAttack() ? 50 : targetDistance;
        }
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance, yDist, targetDistance);
    }
}
