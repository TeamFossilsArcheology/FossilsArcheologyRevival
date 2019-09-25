package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinoAIEatBlocks extends DinoAIMoveToBlock {
    private static final int RADIUS = 8;
    private final EntityPrehistoric entity;

    public DinoAIEatBlocks(EntityPrehistoric entity) {
        super(entity, 1.0F, 35);
        this.entity = entity;
        this.setMutexBits(0);
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            return false;
        }
        if (this.entity.isMovementBlocked()) {
            return false;
        }
        this.distanceCheck = Math.max(this.entity.getEntityBoundingBox().getAverageEdgeLength() * 2.5F, 2.5F);
        boolean execute = super.shouldExecute();
        if(execute){
            entity.shouldWander = false;
        }
        return execute;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            return false;
        }
        return !this.entity.isMovementBlocked() && destinationBlock != null && FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(destinationBlock.up()).getBlock(), this.entity.type.diet) > 0 ;
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (this.getIsAboveDestination() && this.destinationBlock != null) {
            BlockPos up = this.destinationBlock.up();
            Block block = this.entity.world.getBlockState(up).getBlock();
            if (FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet) > 0) {
                double distance = this.getDistance(up);
                if (distance < Math.max(this.entity.getEntityBoundingBox().getAverageEdgeLength() * 2, 1.5F)) {
                    this.entity.setHunger(Math.min(this.entity.getMaxHunger(), this.entity.getHunger() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet)));
                    this.entity.setHealth(Math.min(this.entity.getMaxHealth(), (int) (this.entity.getHealth() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet) / 10)));
                    this.entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
                    this.entity.world.destroyBlock(up, false);
                    this.resetTask();
                    return;
                }
                if(!this.entity.rayTraceFeeder(up, true) || FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(up).getBlock(), this.entity.type.diet) == 0 || !canReachBlock(this.entity, up)){
                    this.resetTask();
                }

            }
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos below) {
        BlockPos pos = below.up();
        return FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(pos).getBlock(), this.entity.type.diet) > 0 && this.entity.rayTraceFeeder(pos, true) && canReachBlock(entity, pos);
    }

    public void resetTask(){
        this.entity.shouldWander = true;
    }

    private double getDistance(BlockPos pos) {
        double deltaX = this.entity.posX - (pos.getX() + 0.5);
        double deltaY = this.entity.posY + this.entity.getEyeHeight() - (pos.getY() + 0.5);
        double deltaZ = this.entity.posZ - (pos.getZ() + 0.5);
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }


    public boolean canReachBlock(Entity entity, BlockPos leafBlock){
        return entity.posY + entity.height >= leafBlock.getY();
    }

    @Override
    protected boolean overrideDelay() {
        return this.entity.getHunger() <= 0.25 * this.entity.getMaxHunger();
    }
}
