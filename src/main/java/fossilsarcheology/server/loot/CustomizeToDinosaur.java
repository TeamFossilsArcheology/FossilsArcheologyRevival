package fossilsarcheology.server.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.item.DinosaurBoneItem;
import fossilsarcheology.server.item.ItemDinoMeat;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class CustomizeToDinosaur extends LootFunction {

    public CustomizeToDinosaur(LootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (!stack.isEmpty()  && context.getLootedEntity() instanceof EntityPrehistoric) {
            EntityPrehistoric dino = (EntityPrehistoric)context.getLootedEntity();
            PrehistoricEntityType type = dino.type;
            if(stack.getItem() instanceof ItemDinoMeat){
                stack.setCount(Math.min(dino.getAgeInDays(), dino.getAdultAge()));
                return new ItemStack( dino.isBurning() ? type.cookedFoodItem : type.foodItem, stack.getCount(), stack.getMetadata());
            }
            if(stack.getItem() instanceof DinosaurBoneItem){
                return new ItemStack(stack.getItem(), stack.getCount(), getMetaForDino(type));
            }

        }
        return stack;
    }

    public static int getMetaForDino(PrehistoricEntityType type){
        if(DinosaurBoneType.get(type) != null){
            return  DinosaurBoneType.get(type).ordinal();
        }else{
            return 0;
        }
    }

    public static class Serializer extends LootFunction.Serializer<CustomizeToDinosaur> {
        public Serializer() {
            super(new ResourceLocation("fossil:customize_to_dinosaur"), CustomizeToDinosaur.class);
        }

        public void serialize(JsonObject object, CustomizeToDinosaur functionClazz, JsonSerializationContext serializationContext) {
        }

        public CustomizeToDinosaur deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            return new CustomizeToDinosaur(conditionsIn);
        }
    }
}