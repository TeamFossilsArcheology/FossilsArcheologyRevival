package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModItemProvider extends ItemModelProvider {
    public ModItemProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        boolean dinoItems = false;
        boolean plantItems = true;

        if (dinoItems) {
            for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
                if (type.dnaItem != null) {
                    dnaItem(Objects.requireNonNull(type.dnaItem.getRegistryName()));
                }
                if (type.foodItem != null) {
                    foodItem(Objects.requireNonNull(type.foodItem.getRegistryName()), type, "meat");
                }
                if (type.fishItem != null) {
                    fishItem(Objects.requireNonNull(type.fishItem.getRegistryName()), type);
                }
                if (type.cookedFoodItem != null) {
                    foodItem(Objects.requireNonNull(type.cookedFoodItem.getRegistryName()), type, "cooked");
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
        if (plantItems) {
            for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
                plantSeedItem(type.getPlantSeedItem().get().getRegistryName());
                plantSeedItem(type.getFossilPlantSeedItem().get().getRegistryName());
            }
        }
    }

    public void plantSeedItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/seeds/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void plantBlockItem(Block block, String suffix) {
        ResourceLocation resourceLocation = new ResourceLocation(block.getRegistryName().getNamespace(),
                "block/plants/plant_" + block.getRegistryName().getPath()+suffix);
        builder(resourceLocation, block.getRegistryName());
    }

    public void dnaItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/dna/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void boneItem(ResourceLocation item, PrehistoricEntityType type, String bone) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/bone/" + type.resourceName + "/" + bone);
        builder(resourceLocation, item);
    }

    public void foodItem(ResourceLocation item, PrehistoricEntityType type, String meat) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/meat/" + type.resourceName + "_" + meat);
        builder(resourceLocation, item);
    }

    public void fishItem(ResourceLocation item, PrehistoricEntityType type) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/meat/" + type.resourceName);
        builder(resourceLocation, item);
    }

    private void builder(ResourceLocation resourceLocation, ResourceLocation item) {
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);//hack because I cant find if and how architectury does --existing
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", resourceLocation);
    }
}
