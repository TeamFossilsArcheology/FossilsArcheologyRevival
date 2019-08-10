package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinoAIEatFeeders extends EntityAIBase {
    private static final int RADIUS = 8;

    private BlockPos targetBlock = null;
    private final EntityPrehistoric entity;
    private final BlockSorter targetSorter;
    private int feedingTicks;

    public DinoAIEatFeeders(EntityPrehistoric entity) {
        super();
        this.entity = entity;
        this.targetSorter = new BlockSorter(entity);
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
        if(targetBlock != null){
            this.entity.shouldWander = false;
        }
        return true;
    }

    private void resetTarget() {
        List<BlockPos> allBlocks = new ArrayList<>();
        for (BlockPos pos : BlockPos.getAllInBox(this.entity.getPosition().add(-RADIUS, -RADIUS, -RADIUS), this.entity.getPosition().add(RADIUS, RADIUS, RADIUS))) {
            TileEntity entity = this.entity.world.getTileEntity(pos);
            if (entity instanceof TileEntityFeeder) {
                TileEntityFeeder feeder = (TileEntityFeeder) entity;
                if (!feeder.isEmpty(this.entity.type) && this.entity.rayTraceFeeder(pos, false)) {
                    allBlocks.add(pos);

                }
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
            TileEntity entity = this.entity.world.getTileEntity(this.targetBlock);
            this.entity.getNavigator().tryMoveToXYZ(this.targetBlock.getX() + 0.5D, this.targetBlock.getY(), this.targetBlock.getZ() + 0.5D, 1D);
            if (entity instanceof TileEntityFeeder) {
                TileEntityFeeder feeder = (TileEntityFeeder) entity;
                double distance = this.entity.getDistance(this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ());
                if (distance < Math.max(this.entity.getEntityBoundingBox().getAverageEdgeLength() * 2.5, 2.5F)) {
                    if (this.feedingTicks < 30 && !feeder.isEmpty(this.entity.type)) {
                        this.feedingTicks++;
                        feeder.feedDinosaur(this.entity);
                        this.entity.setHealth(Math.min(this.entity.getMaxHealth(), (int) (this.entity.getHealth() + this.feedingTicks / 4)));
                        this.entity.doFoodEffect();
                    } else {
                        this.feedingTicks = 0;
                        this.targetBlock = null;
                        this.resetTask();
                        return;
                    }
                } else {
                    this.feedingTicks = 0;
                    this.targetBlock = null;
                    this.resetTask();
                    return;
                }
            }
            if (!this.entity.rayTraceFeeder(targetBlock, false)) {
                this.targetBlock = null;
                this.resetTask();
                return;
            }

        }
    }

    public class BlockSorter implements Comparator<BlockPos> {
        private final Entity entity;

        public BlockSorter(Entity entity) {
            this.entity = entity;
        }

        @Override
        public int compare(BlockPos pos1, BlockPos pos2) {
            double distance1 = this.getDistance(pos1);
            double distance2 = this.getDistance(pos2);
            return Double.compare(distance1, distance2);
        }

        private double getDistance(BlockPos pos) {
            double deltaX = this.entity.posX - (pos.getX() + 0.5);
            double deltaY = this.entity.posY + this.entity.getEyeHeight() - (pos.getY() + 0.5);
            double deltaZ = this.entity.posZ - (pos.getZ() + 0.5);
            return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
        }
    }
}
