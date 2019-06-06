package fossilsarcheology.server.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.Diet;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.pathfinding.PathNavigateSwimmer;

public class FoodHelper {

    public static int getMobFoodPoints(EntityLivingBase entity, Diet diet){
        int mappingPoints = FoodMappings.INSTANCE.getEntityFoodAmount(entity.getClass(), diet);
        int widthPoints =  Math.round(entity.width * entity.height * 10);
        if(mappingPoints == 0 && Revival.CONFIG_OPTIONS.dinoEatModdedMobs){
            if(entity instanceof EntityAnimal && !isAquaticMob(entity)){
                if(diet == Diet.OMNIVORE || diet == Diet.CARNIVORE || diet == Diet.CARNIVORE || diet == Diet.PISCCARNIVORE){
                    return widthPoints;
                }
            }
            if(diet == Diet.PISCIVORE || diet == Diet.PISCCARNIVORE){
                return isAquaticMob(entity) ? widthPoints : 0;
            }
        }
        return mappingPoints;
    }

    private static boolean isAquaticMob(EntityLivingBase entity){
        return entity.canBreatheUnderwater() || entity instanceof EntityWaterMob || entity instanceof EntityLiving && ((EntityLiving) entity).getNavigator() instanceof PathNavigateSwimmer;
    }
}
