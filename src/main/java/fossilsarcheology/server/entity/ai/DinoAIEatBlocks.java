package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinoAIEatBlocks extends EntityAIBase {
    private static final int RADIUS = 8;

    private final EntityPrehistoric entity;
    private final BlockSorter targetSorter;
    private BlockPos targetBlock;

    public DinoAIEatBlocks(EntityPrehistoric entity) {
        super();
        this.entity = entity;
        this.targetSorter = new BlockSorter();
        this.setMutexBits(0);
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger()) {
            return false;
        }
        if (this.entity.isMovementBlocked()) {
            return false;
        }
        if(entity.ticksExisted % Revival.CONFIG_OPTIONS.dinosaurUpdateTick == 0){
            resetTarget();//expensive operation
        }
        if(targetBlock != null) {
            this.entity.shouldWander = false;
        }
        return true;
    }

    private void resetTarget(){
        List<BlockPos> allBlocks = new ArrayList<>();
        for (BlockPos pos : BlockPos.getAllInBox(this.entity.getPosition().add(-RADIUS, -RADIUS, -RADIUS), this.entity.getPosition().add(RADIUS, RADIUS, RADIUS))) {
            if (FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(pos).getBlock(), this.entity.type.diet) > 0 && this.entity.rayTraceFeeder(pos, true) && canReachBlock(entity, pos)) {
                allBlocks.add(pos);
            }
        }
        if (!allBlocks.isEmpty()) {
            allBlocks.sort(this.targetSorter);
            this.targetBlock = allBlocks.get(0);
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            return false;
        }
        return !this.entity.isMovementBlocked() && !entity.shouldWander && targetBlock != null;
    }

    public void resetTask(){
        resetTarget();
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            this.entity.shouldWander = true;
        }
    }

    @Override
    public void updateTask() {
        if (this.targetBlock != null) {
            this.entity.getNavigator().tryMoveToXYZ(this.targetBlock.getX() + 0.5D, this.targetBlock.getY(), this.targetBlock.getZ() + 0.5D, 1D);
            Block block = this.entity.world.getBlockState(this.targetBlock).getBlock();
            if (FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet) > 0) {
                double distance = this.getDistance(this.targetBlock);
                if (distance < Math.max(this.entity.getEntityBoundingBox().getAverageEdgeLength() * 2, 1.5F)) {
                    this.entity.setHunger(Math.min(this.entity.getMaxHunger(), this.entity.getHunger() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet)));
                    this.entity.setHealth(Math.min(this.entity.getMaxHealth(), (int) (this.entity.getHealth() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet) / 10)));
                    this.entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
                    this.entity.world.destroyBlock(this.targetBlock, false);
                    this.targetBlock = null;
                    this.resetTask();
                    return;
                }
                if(!this.entity.rayTraceFeeder(targetBlock, true) || FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(targetBlock).getBlock(), this.entity.type.diet) == 0 || !canReachBlock(this.entity, targetBlock)){
                    this.targetBlock = null;
                    this.resetTask();
                }

            }
        }
    }

    private double getDistance(BlockPos pos) {
        double deltaX = this.entity.posX - (pos.getX() + 0.5);
        double deltaY = this.entity.posY + this.entity.getEyeHeight() - (pos.getY() + 0.5);
        double deltaZ = this.entity.posZ - (pos.getZ() + 0.5);
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public class BlockSorter implements Comparator<BlockPos> {
        @Override
        public int compare(BlockPos pos1, BlockPos pos2) {
            double distance1 = DinoAIEatBlocks.this.getDistance(pos1);
            double distance2 = DinoAIEatBlocks.this.getDistance(pos2);
            return Double.compare(distance1, distance2);
        }
    }

    public boolean canReachBlock(Entity entity, BlockPos leafBlock){
        return entity.posY + entity.height >= leafBlock.getY();
    }
}
