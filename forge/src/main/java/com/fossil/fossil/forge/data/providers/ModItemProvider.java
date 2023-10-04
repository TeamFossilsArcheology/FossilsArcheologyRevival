package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ToyBallItem;
import com.fossil.fossil.item.ToyScratchingPostItem;
import com.fossil.fossil.item.ToyTetheredLogItem;
import dev.architectury.registry.registries.RegistrySupplier;
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
        boolean dinoItems = true;
        boolean plantItems = true;
        boolean toyItems = true;

        if (toyItems) {
            for (RegistrySupplier<ToyBallItem> toy : ModItems.TOY_BALLS.values()) {
                var item = toy.get();
                var resourceLocation = new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath());
                builder(resourceLocation, item.getRegistryName());
            }
            for (RegistrySupplier<ToyScratchingPostItem> toy : ModItems.TOY_SCRATCHING_POSTS.values()) {
                var item = toy.get();
                var resourceLocation = new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath());
                builder(resourceLocation, item.getRegistryName());
            }
            for (RegistrySupplier<ToyTetheredLogItem> toy : ModItems.TOY_TETHERED_LOGS.values()) {
                var item = toy.get();
                var resourceLocation = new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath());
                builder(resourceLocation, item.getRegistryName());
            }
        }


        if (dinoItems) {
            for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
                if (type.dnaItem != null) {
                    dnaItem(Objects.requireNonNull(type.dnaItem.getRegistryName()));
                }
                if (type.foodItem != null) {
                    foodItem(Objects.requireNonNull(type.foodItem.getRegistryName()), type, "meat");
                }
                if (type.cookedFoodItem != null) {
                    foodItem(Objects.requireNonNull(type.cookedFoodItem.getRegistryName()), type, "cooked");
                }
                if (type.eggItem != null) {
                    eggItem(type.eggItem.getRegistryName());
                }
                if (type.birdEggItem != null) {
                    eggItem(type.birdEggItem.getRegistryName());
                }
                if (type.cultivatedBirdEggItem != null) {
                    eggItem(type.cultivatedBirdEggItem.getRegistryName());
                }
                if (type.embryoItem != null) {
                    embyroItem(type.embryoItem.getRegistryName());
                }
                if (type.spawnEggItem != null) {
                    spawnEggItem(type.resourceName);
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
                plantSeedItem(type.getPlantSeedItem().getRegistryName());
                plantSeedItem(type.getFossilizedPlantSeedItem().getRegistryName());
            }
        }
    }

    public void plantSeedItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/seeds/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void plantBlockItem(Block block, String suffix) {
        ResourceLocation resourceLocation = new ResourceLocation(block.getRegistryName().getNamespace(),
                "block/plants/plant_" + block.getRegistryName().getPath() + suffix);
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

    public void eggItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/eggs/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void spawnEggItem(String item) {
        getBuilder("spawn_egg_" + item).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    public void embyroItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/embryo/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void vaseItem(ResourceLocation blockLocation) {
        ResourceLocation resourceLocation = new ResourceLocation(blockLocation.getNamespace(), "item/vases/" + blockLocation.getPath());
        builder(resourceLocation, blockLocation);
    }

    public void simpleItem(ResourceLocation blockLocation, boolean block) {
        ResourceLocation resourceLocation = new ResourceLocation(blockLocation.getNamespace(), (block ? "block/" : "item/") + blockLocation.getPath());
        builder(resourceLocation, blockLocation);
    }

    public void blockItem(ResourceLocation blockLocation) {
        blockItem(blockLocation, "");
    }

    public void blockItem(ResourceLocation blockLocation, String suffix) {
        ResourceLocation resourceLocation = new ResourceLocation(blockLocation.getNamespace(), "item/" + blockLocation.getPath());
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);
        getBuilder(blockLocation.toString())
                .parent(new ModelFile.UncheckedModelFile(blockLocation.getNamespace() + ":block/" + blockLocation.getPath() + suffix));
    }

    private void builder(ResourceLocation resourceLocation, ResourceLocation item) {
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);//hack because I cant find if and how architectury does --existing
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", resourceLocation);
    }
}
