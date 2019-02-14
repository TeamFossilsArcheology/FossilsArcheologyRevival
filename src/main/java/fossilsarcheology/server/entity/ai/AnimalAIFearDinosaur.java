package fossilsarcheology.server.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityTameable;

public class AnimalAIFearDinosaur extends EntityAIAvoidEntity<EntityLivingBase> {

    public AnimalAIFearDinosaur(EntityCreature entityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
    }

    public AnimalAIFearDinosaur(EntityCreature entityIn, Class<EntityLivingBase> classToAvoidIn, Predicate<EntityLivingBase> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
    }

    public boolean shouldExecute(){
        boolean should = super.shouldExecute();
        if(should && this.closestLivingEntity != null){
            if(closestLivingEntity instanceof EntityTameable && ((EntityTameable) closestLivingEntity).isTamed()){
                return false;
            }
        }
        return should;
    }
}
