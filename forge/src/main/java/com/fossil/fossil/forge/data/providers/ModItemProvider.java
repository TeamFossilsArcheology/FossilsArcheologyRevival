package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ToyBallItem;
import com.fossil.fossil.item.ToyScratchingPostItem;
import com.fossil.fossil.item.ToyTetheredLogItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

import static com.fossil.fossil.block.PrehistoricPlantInfo.MUTANT_PLANT;

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
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.dnaItem != null) {
                    dnaItem(Objects.requireNonNull(info.dnaItem.getRegistryName()));
                }
                if (info.foodItem != null) {
                    foodItem(Objects.requireNonNull(info.foodItem.getRegistryName()), info, "meat");
                }
                if (info.cookedFoodItem != null) {
                    foodItem(Objects.requireNonNull(info.cookedFoodItem.getRegistryName()), info, "cooked");
                }
                if (info.eggItem != null) {
                    eggItem(info.eggItem.getRegistryName());
                }
                if (info.birdEggItem != null) {
                    eggItem(info.birdEggItem.getRegistryName());
                }
                if (info.cultivatedBirdEggItem != null) {
                    eggItem(info.cultivatedBirdEggItem.getRegistryName());
                }
                if (info.embryoItem != null) {
                    embyroItem(info.embryoItem.getRegistryName());
                }
                if (info.spawnEggItem != null) {
                    spawnEggItem(info.spawnEggItem);
                }
                if (info.armBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.armBoneItem.getRegistryName()), info, "arm_bone");
                }
                if (info.footBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.footBoneItem.getRegistryName()), info, "foot");
                }
                if (info.legBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.legBoneItem.getRegistryName()), info, "leg_bone");
                }
                if (info.ribcageBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.ribcageBoneItem.getRegistryName()), info, "ribcage");
                }
                if (info.skullBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.skullBoneItem.getRegistryName()), info, "skull");
                }
                if (info.tailBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.tailBoneItem.getRegistryName()), info, "tail");
                }
                if (info.uniqueBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.uniqueBoneItem.getRegistryName()), info, "unique");
                }
                if (info.vertebraeBoneItem != null) {
                    boneItem(Objects.requireNonNull(info.vertebraeBoneItem.getRegistryName()), info, "vertebrae");
                }
            }
            for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
                if (info.dnaItem != null) {
                    dnaItem(Objects.requireNonNull(info.dnaItem.getRegistryName()));
                }
                if (info.eggItem != null) {
                    eggItem(info.eggItem.getRegistryName());
                }
                if (info.cultivatedBirdEggItem != null) {
                    eggItem(info.cultivatedBirdEggItem.getRegistryName());
                }
                if (info.embryoItem != null) {
                    embyroItem(info.embryoItem.getRegistryName());
                }
            }
            basicItem(ModItems.ALLIGATOR_GAR_BUCKET.get().getRegistryName());
            basicItem(ModItems.COELACANTH_BUCKET.get().getRegistryName());
            basicItem(ModItems.CRASSIGYRINUS_BUCKET.get().getRegistryName());
            basicItem(ModItems.DIPLOCAULUS_BUCKET.get().getRegistryName());
            basicItem(ModItems.ICHTHYOSAURUS_BUCKET.get().getRegistryName());
            basicItem(ModItems.MEGALOGRAPTUS_BUCKET.get().getRegistryName());
            basicItem(ModItems.NAUTILUS_BUCKET.get().getRegistryName());
            basicItem(ModItems.STURGEON_BUCKET.get().getRegistryName());

            spawnEggItem(ModItems.ANU_BOSS_SPAWN_EGG.get());
            spawnEggItem(ModItems.FAILURESAURUS_SPAWN_EGG.get());
            spawnEggItem(ModItems.SENTRY_PIGLIN_SPAWN_EGG.get());
            spawnEggItem(ModItems.TAR_SLIME_SPAWN_EGG.get());

            basicItem(ModItems.ELASMOTHERIUM_FUR.get());
            basicItem(ModItems.MAMMOTH_FUR.get());
            basicItem(ModItems.THERIZINOSAURUS_DOWN.get());
            basicItem(ModItems.MAGIC_CONCH.get());
        }
        if (plantItems) {
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
                if (info.berryItem != null) {
                    var resourceLocation = new ResourceLocation(info.berryItem.get().getRegistryName().getNamespace(), "item/" + info.berryItem.get().getRegistryName().getPath());
                    builder(resourceLocation, info.berryItem.get().getRegistryName());
                }
            }
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
                if (info != MUTANT_PLANT) {
                    plantSeedItem(info.getPlantSeedItem().getRegistryName());
                    plantSeedItem(info.getFossilizedPlantSeedItem().getRegistryName());
                }
            }
            basicItem(ModItems.CALAMITES_SAPLING_FOSSIL.get());
            basicItem(ModItems.CORDAITES_SAPLING_FOSSIL.get());
            basicItem(ModItems.PALM_SAPLING_FOSSIL.get());
            basicItem(ModItems.SIGILLARIA_SAPLING_FOSSIL.get());
            basicItem(ModItems.TEMPSKYA_SAPLING_FOSSIL.get());
        }
    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        return builder(new ResourceLocation(item.getNamespace(), "item/" + item.getPath()), item);
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

    public void boneItem(ResourceLocation item, PrehistoricEntityInfo info, String bone) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/bone/" + info.resourceName + "/" + bone);
        builder(resourceLocation, item);
    }

    public void foodItem(ResourceLocation item, PrehistoricEntityInfo info, String meat) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/meat/" + info.resourceName + "_" + meat);
        builder(resourceLocation, item);
    }

    public void eggItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/eggs/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void spawnEggItem(Item item) {
        getBuilder(item.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    public void embyroItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(item.getNamespace(), "item/embryo/" + item.getPath());
        builder(resourceLocation, item);
    }

    public void vaseItem(ResourceLocation blockLocation) {
        ResourceLocation resourceLocation = new ResourceLocation(blockLocation.getNamespace(), "item/vases/" + blockLocation.getPath());
        builder(resourceLocation, blockLocation);
    }

    public void simpleItem(ResourceLocation location, boolean block) {
        ResourceLocation resourceLocation = new ResourceLocation(location.getNamespace(), (block ? "block/" : "item/") + location.getPath());
        builder(resourceLocation, location);
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

    private ItemModelBuilder builder(ResourceLocation resourceLocation, ResourceLocation item) {
        existingFileHelper.trackGenerated(resourceLocation, TEXTURE);//hack because I cant find if and how architectury does --existing
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", resourceLocation);
    }
}
