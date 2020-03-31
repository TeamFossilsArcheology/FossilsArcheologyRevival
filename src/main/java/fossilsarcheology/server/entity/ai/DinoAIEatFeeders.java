package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinoAIEatFeeders extends DinoAIMoveToBlock {
    private static final int RADIUS = 8;
    private final EntityPrehistoric entity;
    private int feedingTicks;

    public DinoAIEatFeeders(EntityPrehistoric entity) {
        super(entity, 1.0F, 35);
        this.entity = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            return false;
        }
        if (this.entity.isMovementBlockedSoft()) {
            return false;
        }
        boolean execute = super.shouldExecute();
        if (execute) {
            entity.shouldWander = false;
        }
        return execute;
    }


    @Override
    public boolean shouldContinueExecuting() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger() * 0.75F) {
            return false;
        }
        return !this.entity.isMovementBlockedSoft() && destinationBlock != null && this.entity.world.getTileEntity(destinationBlock.up()) instanceof TileEntityFeeder;
    }

    public void resetTask() {
        this.entity.shouldWander = true;
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (this.getIsAboveDestination() && this.destinationBlock != null) {
            BlockPos targetBlock = this.destinationBlock.up();
            TileEntity entity = this.entity.world.getTileEntity(targetBlock);
            if (entity instanceof TileEntityFeeder) {
                TileEntityFeeder feeder = (TileEntityFeeder) entity;
                double distance = this.entity.getDistance(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
                if (distance < Math.max(this.entity.getEntityBoundingBox().getAverageEdgeLength() * 2.5, 2.5F)) {
                    if (this.entity.getHunger() < this.entity.getMaxHunger() && !feeder.isEmpty(this.entity.type)) {
                        this.feedingTicks++;
                        feeder.feedDinosaur(this.entity);
                        this.entity.setHealth(Math.min(this.entity.getMaxHealth(), (int) (this.entity.getHealth() + this.feedingTicks / 4)));
                        this.entity.doFoodEffect();
                    } else {
                        this.feedingTicks = 0;
                        this.resetTask();
                        return;
                    }
                } else {
                    this.feedingTicks = 0;
                    this.resetTask();
                    return;
                }
            }
            if (!this.entity.rayTraceFeeder(targetBlock, false)) {
                this.resetTask();
                return;
            }

        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos below) {
        BlockPos pos = below.up();
        TileEntity entity = this.entity.world.getTileEntity(pos);
        if (entity instanceof TileEntityFeeder) {
            TileEntityFeeder feeder = (TileEntityFeeder) entity;
            return !feeder.isEmpty(this.entity.type) && this.entity.rayTraceFeeder(pos, false);
        }
        return false;
    }

    @Override
    protected boolean overrideDelay() {
        return this.entity.getHunger() <= 0.25 * this.entity.getMaxHunger();
    }
}
