package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;

import java.util.Comparator;
import java.util.List;

public class DinoAIEatItems extends EntityAIBase {
    private final EntityPrehistoric prehistoric;
    private final ItemsSorter targetSorter;
    private EntityItem targetItem;

    public DinoAIEatItems(EntityPrehistoric prehistoric) {
        super();
        this.prehistoric = prehistoric;
        this.targetSorter = new ItemsSorter(prehistoric);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (this.prehistoric.getHunger() >= this.prehistoric.getMaxHunger()) {
            return false;
        } else if (this.prehistoric.isMovementBlockedSoft()) {
            return false;
        }
        this.targetItem = this.getNearestItem(16);
        return this.targetItem != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.targetItem == null || !this.targetItem.isEntityAlive()) {
            return false;
        } else if (this.prehistoric.getHunger() >= this.prehistoric.getMaxHunger()) {
            return false;
        }
        return !this.prehistoric.isMovementBlockedSoft();
    }

    @Override
    public void updateTask() {
        double distance = Math.sqrt(Math.pow(this.prehistoric.posX - this.targetItem.posX, 2.0D) + Math.pow(this.prehistoric.posZ - this.targetItem.posZ, 2.0D));
        this.prehistoric.getNavigator().tryMoveToXYZ(this.targetItem.posX, this.targetItem.posY, this.targetItem.posZ, 1D);
        if (distance < Math.max(this.prehistoric.getEntityBoundingBox().getAverageEdgeLength(), 2.5D)) {
            if (this.targetItem != null) {
                this.prehistoric.eatItem(this.targetItem.getItem());
                this.targetItem.getItem().shrink(1);
            }
        }
        if (this.prehistoric.getNavigator().noPath()) {
            resetTask();
        }
    }

    private EntityItem getNearestItem(int range) {
        List<EntityItem> nearbyItems = this.prehistoric.world.getEntitiesWithinAABB(EntityItem.class, this.prehistoric.getEntityBoundingBox().grow(range, range, range));
        nearbyItems.sort(this.targetSorter);

        for (EntityItem currentItem : nearbyItems) {
            if (!currentItem.getItem().isEmpty()) {
                int foodValue = FoodMappings.INSTANCE.getItemFoodAmount(currentItem.getItem(), this.prehistoric.type.diet);
                if (foodValue > 0 && !this.prehistoric.isPreyBlocked(currentItem)) {
                    return currentItem;
                }
            }
        }

        return null;
    }

    public class ItemsSorter implements Comparator<Entity> {
        private final Entity entity;

        public ItemsSorter(Entity entity) {
            this.entity = entity;
        }

        @Override
        public int compare(Entity entity1, Entity entity2) {
            double distance1 = this.entity.getDistanceSq(entity1);
            double distance2 = this.entity.getDistanceSq(entity2);
            return Double.compare(distance1, distance2);
        }
    }
}
