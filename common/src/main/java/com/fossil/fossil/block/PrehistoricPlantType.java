package com.fossil.fossil.block;

import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.item.FlowerSeedsItem;
import com.fossil.fossil.item.FossilFlowerSeedsItem;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ModTabs;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum PrehistoricPlantType {

    BENNETTITALES_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 16, 14)),
    BENNETTITALES_SMALL(Size.SINGLE_GROWABLE, BENNETTITALES_LARGE, "bennettitales", Block.box(3, 0, 3, 13, 14, 13)),
    CEPHALOTAXUS(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    CRATAEGUS(Size.DOUBLE, Block.box(1, 0, 1, 15, 16, 15), Block.box(1, 0, 1, 15, 7, 15)),
    CYATHEA(Size.FOUR, Block.box(5, 0, 5, 11, 16, 11)),
    DICTYOPHYLLUM(Size.SINGLE, Block.box(3, 0, 3, 13, 14, 13)),
    DILLHOFFIA(Size.SINGLE, Block.box(5, 0, 5, 11, 14, 11)),
    DIPTERIS(Size.DOUBLE, Block.box(1, 0, 1, 15, 16, 15)),
    DUISBERGIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 16, 13)),
    EPENDRA(Size.SINGLE, Block.box(3, 0, 3, 13, 8, 13)),
    FLORISSANTIA(Size.SINGLE, Block.box(4, 0, 4, 12, 11, 12)),
    FOOZIA(Size.DOUBLE, Block.box(2, 0, 2, 14, 16, 14)),
    HORSETAIL_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 16, 14)),
    HORSETAIL_SMALL(Size.SINGLE_GROWABLE, HORSETAIL_LARGE, "horsetail", Block.box(5, 0, 5, 11, 10, 11)),
    LICOPODIOPHYTA(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    MUTANT_PLANT(Size.DOUBLE, Block.box(2, 0, 2, 14, 16, 14)),
    OSMUNDA(Size.SINGLE, Block.box(4, 0, 4, 12, 11, 12)),
    SAGENOPTERIS(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    SARRACENIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 7, 13)),
    TEMPSKYA(Size.FOUR, Block.box(2, 0, 2, 14, 16, 14)),
    VACCINIUM(Size.SINGLE, Block.box(4, 0, 4, 12, 10, 12)),
    WELWITSCHIA(Size.SINGLE, Block.box(3, 0, 3, 13, 5, 13)),
    ZAMITES(Size.SINGLE, Block.box(5, 0, 5, 11, 10, 11));

    private final Size size;
    private final String resourceName;
    private final VoxelShape[] shapes;
    private PrehistoricPlantType tallPlant;
    private String commonName;
    private RegistrySupplier<? extends BushBlock> plantBlock;
    private RegistrySupplier<FossilFlowerSeedsItem> fossilPlantSeedItem;
    private RegistrySupplier<FlowerSeedsItem> plantSeedItem;

    PrehistoricPlantType(Size size, VoxelShape... shapes) {
        this.size = size;
        this.shapes = shapes;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
    }

    PrehistoricPlantType(Size size, PrehistoricPlantType tallPlant, String commonName, VoxelShape shape) {
        this.size = size;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.tallPlant = tallPlant;
        this.commonName = commonName;
        this.shapes = new VoxelShape[]{shape};
    }

    public static void register() {
        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            if (type == MUTANT_PLANT) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shapes);
            } else if (type.size == Size.SINGLE) {
                type.plantBlock = ModBlocks.registerShortFlower(type.resourceName, type.shapes[0]);
                type.registerPlantSeed(type.resourceName);
            } else if (type.size == Size.DOUBLE) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shapes);
                type.registerPlantSeed(type.resourceName);
            } else if (type.size == Size.SINGLE_GROWABLE) {
                type.plantBlock = ModBlocks.registerGrowableFlower(type.resourceName, (RegistrySupplier<TallFlowerBlock>) type.tallPlant.plantBlock,
                        type.shapes[0]);
                type.registerPlantSeed(type.commonName);
            } else if (type.size == Size.DOUBLE_GROWABLE) {
                type.plantBlock = ModBlocks.registerTallFlower(type.resourceName, type.shapes);
            } else if (type.size == Size.FOUR) {
                type.plantBlock = ModBlocks.registerFourTallFlower(type.resourceName, type.shapes[0]);
                type.registerPlantSeed(type.resourceName);
            }
        }
    }

    private void registerPlantSeed(String name) {
        this.fossilPlantSeedItem = ModItems.ITEMS.register("fossil_seed_" + name,
                () -> new FossilFlowerSeedsItem(new Item.Properties().tab(ModTabs.FAITEMTAB)));
        this.plantSeedItem = ModItems.ITEMS.register("seed_" + name,
                () -> new FlowerSeedsItem(new Item.Properties().tab(ModTabs.FAITEMTAB), this.plantBlock));
    }

    public BushBlock getPlantBlock() {
        return plantBlock.get();
    }

    public FossilFlowerSeedsItem getFossilPlantSeedItem() {
        return fossilPlantSeedItem.get();
    }

    public FlowerSeedsItem getPlantSeedItem() {
        return plantSeedItem.get();
    }

    private static List<PrehistoricPlantType> seedsCache;

    public static List<PrehistoricPlantType> plantsWithSeeds() {
        if (seedsCache == null) {
            seedsCache = Arrays.stream(values()).filter(type -> type.plantSeedItem != null).toList();
        }
        return seedsCache;
    }

    enum Size {
        SINGLE, DOUBLE, SINGLE_GROWABLE, DOUBLE_GROWABLE, FOUR
    }
}
