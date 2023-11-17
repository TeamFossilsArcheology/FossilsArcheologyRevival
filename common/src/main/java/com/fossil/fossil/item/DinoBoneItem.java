package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DinoBoneItem extends PrehistoricEntityItem {
    private final Component name;

    public DinoBoneItem(PrehistoricEntityType type, String boneType) {
        super(type);
        if ("bone_unique".equals(boneType)) {
            this.name = new TranslatableComponent("item." + Fossil.MOD_ID + ".bone_unique_" + type.resourceName, type.displayName.get());
        } else {
            this.name =  new TranslatableComponent("item." + Fossil.MOD_ID + "." + boneType, type.displayName.get());
        }
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }

    public static void registerItem(String boneType, PrehistoricEntityType type, Consumer<DinoBoneItem> listener){
        ModItems.ITEMS.register(boneType + "_" + type.resourceName, () -> new DinoBoneItem(type, boneType)).listen(listener);
    }
}
