package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;

import java.util.function.Consumer;

public class DinoBoneItem extends PrehistoricEntityItem {

    public DinoBoneItem(PrehistoricEntityInfo info, String boneType) {
        super(info, "bone_unique".equals(boneType) ? "bone_unique_" + info.resourceName : boneType);
    }

    public static void registerItem(String boneType, PrehistoricEntityInfo info, Consumer<DinoBoneItem> listener) {
        ModItems.ITEMS.register(boneType + "_" + info.resourceName, () -> new DinoBoneItem(info, boneType)).listen(listener);
    }
}
