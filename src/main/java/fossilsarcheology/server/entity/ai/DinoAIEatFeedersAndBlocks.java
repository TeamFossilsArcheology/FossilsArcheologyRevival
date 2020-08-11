package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DinoAIEatFeedersAndBlocks extends DinoAIMoveToBlock {
    private static final int RADIUS = 8;
    private final EntityPrehistoric entity;
    private int feedingTicks;

    public DinoAIEatFeedersAndBlocks(EntityPrehistoric entity) {
        super(entity, 1.0F, 35);
        this.entity = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger()) {
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
        return !this.entity.isMovementBlockedSoft() && destinationBlock != null && shouldTarget(destinationBlock);
    }

    @Override
    public void updateTask() {
        super.updateTask();
        boolean isFeeder = false;
        if (this.getIsAboveDestination() && this.destinationBlock != null) {
            BlockPos up = this.destinationBlock;
            Block block = this.entity.world.getBlockState(up).getBlock();
            if (shouldTarget(destinationBlock)) {
                double distance = this.getDistance(up);
                double check = Math.max(this.getRequiredDistance(1.5F), 1.5F);
                if (distance < check) {
                    if(entity.world.getBlockState(destinationBlock).getBlock() == FABlockRegistry.FEEDER){
                        isFeeder = true;
                        TileEntity entity = this.entity.world.getTileEntity(destinationBlock);
                        TileEntityFeeder feeder = (TileEntityFeeder)entity;
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
                    }else if(FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(destinationBlock).getBlock(), this.entity.type.diet) > 0){
                        this.entity.setHunger(Math.min(this.entity.getMaxHunger(), this.entity.getHunger() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet)));
                        this.entity.setHealth(Math.min(this.entity.getMaxHealth(), (int) (this.entity.getHealth() + FoodMappings.INSTANCE.getBlockFoodAmount(block, this.entity.type.diet) / 10)));
                        this.entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
                        this.entity.world.destroyBlock(up, false);
                        this.resetTask();
                    }else{
                        this.resetTask();
                    }

                    return;
                }
               if(!isFeeder){
                   if (!this.entity.rayTraceFeeder(up, true) || FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(up).getBlock(), this.entity.type.diet) == 0 || !canReachBlock(this.entity, up)) {
                       this.resetTask();
                   }
               }

            }
        }
    }

    private boolean shouldTarget(BlockPos pos){
        TileEntity entity = this.entity.world.getTileEntity(pos);
        if (entity instanceof TileEntityFeeder) {
            TileEntityFeeder feeder = (TileEntityFeeder) entity;
            return !feeder.isEmpty(this.entity.type) && this.entity.rayTraceFeeder(pos, false);
        }
        return FoodMappings.INSTANCE.getBlockFoodAmount(this.entity.world.getBlockState(pos).getBlock(), this.entity.type.diet) > 0 && this.entity.rayTraceFeeder(pos, true);
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos below) {
        BlockPos pos = below;
        return shouldTarget(pos) && canReachBlock(entity, pos);
    }

    public void resetTask() {
        this.entity.shouldWander = true;
        this.runDelay = 0;
        this.feedingTicks = 0;
    }

    private double getDistance(BlockPos pos) {
        double deltaX = this.entity.posX - (pos.getX() + 0.5);
        double deltaY = this.entity.posY - (pos.getY() + 0.5);
        double deltaZ = this.entity.posZ - (pos.getZ() + 0.5);
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }


    public boolean canReachBlock(Entity entity, BlockPos leafBlock) {
        return entity.posY + entity.height >= leafBlock.getY();
    }

    @Override
    protected boolean overrideDelay() {
        return this.entity.getHunger() <= 0.25 * this.entity.getMaxHunger();
    }
}
