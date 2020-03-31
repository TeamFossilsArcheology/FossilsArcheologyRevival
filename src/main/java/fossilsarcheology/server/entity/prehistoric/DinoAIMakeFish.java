package fossilsarcheology.server.entity.prehistoric;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DinoAIMakeFish extends EntityAIBase {

    private final EntityPrehistoricSwimming dinosaur;

    public DinoAIMakeFish(EntityPrehistoricSwimming dinosaur) {
        this.dinosaur = dinosaur;
        World world = dinosaur.world;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.dinosaur.isHungry()) {
            return false;
        }
        if (this.dinosaur.getRNG().nextInt(205) != 0) {
            return false;
        }
        if (!this.dinosaur.isInWater()) {
            return false;
        }
        return this.dinosaur.FISH_ANIMATION != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (!this.dinosaur.isHungry()) {
            return false;
        }
        return this.dinosaur.isInWater();
    }

    @Override
    public void startExecuting() {
        if (this.dinosaur.isInWater()) {
            if (this.dinosaur.getAnimation() != this.dinosaur.FISH_ANIMATION) {
                this.dinosaur.setAnimation(this.dinosaur.FISH_ANIMATION);
            }
        }
    }

    @Override
    public void updateTask() {
        if (this.dinosaur.getAnimation() == this.dinosaur.FISH_ANIMATION) {
            if (this.dinosaur.getAnimationTick() == 20) {
                ItemStack stack2 = new ItemStack(Items.FISH, 1 + this.dinosaur.getRNG().nextInt(2), this.dinosaur.getRNG().nextInt(2));
                this.dinosaur.entityDropItem(stack2, 1);
                resetTask();
            }
        }
    }
}
