package com.fossil.fossil.block;

import com.fossil.fossil.block.custom_blocks.CrataegusBushBlock;
import com.fossil.fossil.block.custom_blocks.EphedraBushBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.VacciniumBushBlock;
import com.fossil.fossil.item.FlowerSeedsItem;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ModTabs;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum PrehistoricPlantType {

    BENNETTITALES_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 32, 14)),
    BENNETTITALES_SMALL(Size.SINGLE_GROWABLE, BENNETTITALES_LARGE, "bennettitales", Block.box(3, 0, 3, 13, 14, 13)),
    CEPHALOTAXUS(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    CRATAEGUS(Size.DOUBLE_BERRY, Block.box(1, 0, 1, 15, 16, 15), 2, 3),
    CYATHEA(Size.FOUR, Block.box(5, 0, 5, 11, 16, 11)),
    DICTYOPHYLLUM(Size.SINGLE, Block.box(3, 0, 3, 13, 14, 13)),
    DILLHOFFIA(Size.SINGLE, Block.box(5, 0, 5, 11, 14, 11)),
    DIPTERIS(Size.DOUBLE, Block.box(1, 0, 1, 15, 22, 15)),
    DUISBERGIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 32, 13)),
    EPHEDRA(Size.SINGLE_BERRY, Block.box(0, 0, 0, 16, 10, 16), 1, 1),
    FLORISSANTIA(Size.SINGLE, Block.box(4, 0, 4, 12, 11, 12)),
    FOOZIA(Size.DOUBLE, Block.box(2, 0, 2, 14, 32, 14)),
    HORSETAIL_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 32, 14)),
    HORSETAIL_SMALL(Size.SINGLE_GROWABLE, HORSETAIL_LARGE, "horsetail", Block.box(5, 0, 5, 11, 10, 11)),
    LICOPODIOPHYTA(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    MUTANT_PLANT(Size.DOUBLE, Block.box(2, 0, 2, 14, 32, 14)),
    OSMUNDA(Size.SINGLE, Block.box(4, 0, 4, 12, 11, 12)),
    SAGENOPTERIS(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    //SARRACENIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 12, 13)),
    SARRACENIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 28, 13)),
    VACCINIUM(Size.SINGLE_BERRY, Block.box(0, 0, 0, 16, 14, 16), 2, 3),
    WELWITSCHIA(Size.SINGLE, Block.box(3, 0, 3, 13, 5, 13)),
    ZAMITES(Size.DOUBLE, Block.box(3, 0, 3, 13, 32, 13));

    private static List<PrehistoricPlantType> seedsCache;
    private final Size size;
    private final String resourceName;
    private final VoxelShape shape;
    private PrehistoricPlantType tallPlant;
    public int berryAge;
    public int maxAge;
    public RegistrySupplier<Item> berryItem;
    private String commonName;
    private RegistrySupplier<? extends BushBlock> plantBlock;
    private RegistrySupplier<Item> fossilizedPlantSeedItem;
    private RegistrySupplier<FlowerSeedsItem> plantSeedItem;

    PrehistoricPlantType(Size size, VoxelShape shape) {
        this.size = size;
        this.shape = shape;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
    }

    PrehistoricPlantType(Size size, PrehistoricPlantType tallPlant, String commonName, VoxelShape shape) {
        this.size = size;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.tallPlant = tallPlant;
        this.commonName = commonName;
        this.shape = shape;
    }

    PrehistoricPlantType(Size size, VoxelShape shape, int berryAge, int maxAge) {
        this.size = size;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.berryAge = berryAge;
        this.maxAge = maxAge;
        this.shape = shape;
    }

    public static void register() {
        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            if (type == CRATAEGUS) {
                type.plantBlock = ModBlocks.registerBlock(type.resourceName, () -> new CrataegusBushBlock(type.shape, type));
                type.berryItem = ModItems.ITEMS.register("berry_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(Foods.SWEET_BERRIES)));
                type.registerPlantSeed(type.resourceName);
            } else if (type == EPHEDRA) {
                type.plantBlock = ModBlocks.registerBlock(type.resourceName, () -> new EphedraBushBlock(type.shape,  type));
                type.berryItem = ModItems.ITEMS.register("berry_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(Foods.SWEET_BERRIES)));
                type.registerPlantSeed(type.resourceName);
            } else if (type == VACCINIUM) {
                type.plantBlock = ModBlocks.registerBlock(type.resourceName, () -> new VacciniumBushBlock(type.shape,  type));
                type.berryItem = ModItems.ITEMS.register("berry_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(Foods.SWEET_BERRIES)));
                type.registerPlantSeed(type.resourceName);
            } else if (type == MUTANT_PLANT) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shape);
            } else if (type.size == Size.SINGLE) {
                type.plantBlock = ModBlocks.registerShortFlower(type.resourceName, type.shape);
                type.registerPlantSeed(type.resourceName);
            } else if (type.size == Size.DOUBLE) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shape);
                type.registerPlantSeed(type.resourceName);
            } else if (type.size == Size.SINGLE_GROWABLE) {
                type.plantBlock = ModBlocks.registerGrowableFlower(type.resourceName, (RegistrySupplier<TallFlowerBlock>) type.tallPlant.plantBlock,
                        type.shape);
                type.registerPlantSeed(type.commonName);
            } else if (type.size == Size.DOUBLE_GROWABLE) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shape);
            } else if (type.size == Size.FOUR) {
                type.plantBlock = ModBlocks.registerFourTallFlower(type.resourceName, type.shape);
                type.registerPlantSeed(type.resourceName);
            }
        }
    }

    public static List<PrehistoricPlantType> plantsWithSeeds() {
        if (seedsCache == null) {
            seedsCache = Arrays.stream(values()).filter(type -> type.plantSeedItem != null).toList();
        }
        return seedsCache;
    }

    private void registerPlantSeed(String name) {
        this.fossilizedPlantSeedItem = ModItems.ITEMS.register("fossil_seed_" + name,
                () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
        this.plantSeedItem = ModItems.ITEMS.register("seed_" + name,
                () -> new FlowerSeedsItem(new Item.Properties().tab(ModTabs.FAITEMTAB), this.plantBlock));
    }

    public BushBlock getPlantBlock() {
        return plantBlock.get();
    }

    public Item getFossilizedPlantSeedItem() {
        return fossilizedPlantSeedItem.get();
    }

    public FlowerSeedsItem getPlantSeedItem() {
        return plantSeedItem.get();
    }

    enum Size {
        SINGLE, DOUBLE, SINGLE_GROWABLE, DOUBLE_GROWABLE, FOUR, SINGLE_BERRY, DOUBLE_BERRY;
    }
}
