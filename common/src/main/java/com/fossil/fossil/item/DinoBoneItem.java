package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DinoBoneItem extends PrehistoricEntityItem {
    private final Component name;

    public DinoBoneItem(PrehistoricEntityInfo info, String boneType) {
        super(info);
        if ("bone_unique".equals(boneType)) {
            this.name = new TranslatableComponent("item." + Fossil.MOD_ID + ".bone_unique_" + info.resourceName, info.displayName.get());
        } else {
            this.name =  new TranslatableComponent("item." + Fossil.MOD_ID + "." + boneType, info.displayName.get());
        }
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }

    public static void registerItem(String boneType, PrehistoricEntityInfo info, Consumer<DinoBoneItem> listener){
        ModItems.ITEMS.register(boneType + "_" + info.resourceName, () -> new DinoBoneItem(info, boneType)).listen(listener);
    }
}
