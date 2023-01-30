package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemProvider extends ItemModelProvider {
    public ItemProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.dnaItem != null) {
                dnaItem(Objects.requireNonNull(type.dnaItem.getRegistryName()));
            }
        }
        for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithBones()) {
            boneItem(Objects.requireNonNull(type.armBoneItem.getRegistryName()), type, "arm_bone");
            boneItem(Objects.requireNonNull(type.legBoneItem.getRegistryName()), type, "leg_bone");
            boneItem(Objects.requireNonNull(type.footBoneItem.getRegistryName()), type, "foot");
            boneItem(Objects.requireNonNull(type.skullBoneItem.getRegistryName()), type, "skull");
            boneItem(Objects.requireNonNull(type.ribcageBoneItem.getRegistryName()), type, "ribcage");
            boneItem(Objects.requireNonNull(type.vertebraeBoneItem.getRegistryName()), type, "vertebrae");
            boneItem(Objects.requireNonNull(type.uniqueBoneItem.getRegistryName()), type, "unique_item");
        }
    }

    public void dnaItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/dna/" + item.getPath());
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);//hack because I cant find if and how architectury does --existing
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", resourceLocation);
    }

    public void boneItem(ResourceLocation item, PrehistoricEntityType type, String bone) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/bone/" + type.resourceName + "/" + bone);
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", resourceLocation);
    }
}
