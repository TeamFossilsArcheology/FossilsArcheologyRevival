package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CrataegusBushBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.EphedraBushBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VacciniumBushBlock;
import com.github.teamfossilsarcheology.fossil.item.FlowerSeedsItem;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.item.ModTabs;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum PrehistoricPlantInfo {

    BENNETTITALES_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 28, 14)),
    BENNETTITALES_SMALL(Size.SINGLE_GROWABLE, BENNETTITALES_LARGE, "bennettitales", Block.box(2, 0, 2, 14, 16, 14)),
    CEPHALOTAXUS(Size.SINGLE, Block.box(2, 0, 2, 14, 10, 14)),
    CRATAEGUS(Size.DOUBLE_BERRY, Shapes.empty(), 2, 3),
    CYATHEA(Size.FOUR, Block.box(5, 0, 5, 11, 16, 11)),
    DICTYOPHYLLUM(Size.SINGLE, Block.box(3, 0, 3, 13, 16, 13)),
    DILLHOFFIA(Size.SINGLE, Block.box(2, 0, 2, 14, 13, 14)),
    DIPTERIS(Size.DOUBLE, Block.box(1, 0, 1, 15, 22, 15)),
    DUISBERGIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 32, 13)),
    EPHEDRA(Size.SINGLE_BERRY, Block.box(0, 0, 0, 16, 10, 16), 1, 1),
    FLORISSANTIA(Size.SINGLE, Block.box(4, 0, 4, 12, 11, 12)),
    FOOZIA(Size.DOUBLE, Block.box(2, 0, 2, 14, 32, 14)),
    HORSETAIL_LARGE(Size.DOUBLE_GROWABLE, Block.box(2, 0, 2, 14, 32, 14)),
    HORSETAIL_SMALL(Size.SINGLE_GROWABLE, HORSETAIL_LARGE, "horsetail", Block.box(3, 0, 3, 13, 16, 13)),
    LICOPODIOPHYTA(Size.SINGLE, Block.box(2, 0, 2, 14, 10, 14)),
    MUTANT_PLANT(Size.DOUBLE, Block.box(2, 0, 2, 14, 32, 14)),
    OSMUNDA(Size.SINGLE, Block.box(2, 0, 2, 14, 15, 14)),
    SAGENOPTERIS(Size.SINGLE, Block.box(4, 0, 4, 12, 14, 12)),
    //SARRACENIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 12, 13)),
    SARRACENIA(Size.DOUBLE, Block.box(3, 0, 3, 13, 28, 13)),
    VACCINIUM(Size.SINGLE_BERRY, Shapes.empty(), 2, 3),
    WELWITSCHIA(Size.SINGLE, Block.box(3, 0, 3, 13, 5, 13)),
    ZAMITES(Size.DOUBLE, Block.box(3, 0, 3, 13, 32, 13));

    private static List<PrehistoricPlantInfo> seedsCache;
    private final Size size;
    private final String resourceName;
    private final VoxelShape shape;
    private PrehistoricPlantInfo tallPlant;
    private int berryAge;
    private int maxAge;
    private RegistrySupplier<Item> berryItem;
    private String commonName;
    private RegistrySupplier<? extends BushBlock> plantBlock;
    private RegistrySupplier<Item> fossilizedPlantSeedItem;
    private RegistrySupplier<FlowerSeedsItem> plantSeedItem;

    PrehistoricPlantInfo(Size size, VoxelShape shape) {
        this.size = size;
        this.shape = shape;
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
    }

    PrehistoricPlantInfo(Size size, PrehistoricPlantInfo tallPlant, String commonName, VoxelShape shape) {
        this.size = size;
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
        this.tallPlant = tallPlant;
        this.commonName = commonName;
        this.shape = shape;
    }

    PrehistoricPlantInfo(Size size, VoxelShape shape, int berryAge, int maxAge) {
        this.size = size;
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
        this.berryAge = berryAge;
        this.maxAge = maxAge;
        this.shape = shape;
    }

    public static void register() {
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            if (info == CRATAEGUS) {
                info.plantBlock = ModBlocks.registerBlock(info.resourceName, () -> new CrataegusBushBlock(info));
                info.berryItem = ModItems.ITEMS.register("berry_" + info.resourceName, () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).food(Foods.SWEET_BERRIES)));
                info.registerPlantSeed(info.resourceName);
            } else if (info == EPHEDRA) {
                info.plantBlock = ModBlocks.registerBlock(info.resourceName, () -> new EphedraBushBlock(info.shape, info));
                info.berryItem = ModItems.ITEMS.register("berry_" + info.resourceName, () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).food(Foods.SWEET_BERRIES)));
                info.registerPlantSeed(info.resourceName);
            } else if (info == VACCINIUM) {
                info.plantBlock = ModBlocks.registerBlock(info.resourceName, () -> new VacciniumBushBlock(info.shape, info));
                info.berryItem = ModItems.ITEMS.register("berry_" + info.resourceName, () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).food(Foods.SWEET_BERRIES)));
                info.registerPlantSeed(info.resourceName);
            } else if (info.size == Size.SINGLE) {
                info.plantBlock = ModBlocks.registerShortFlower(info.resourceName, info.shape);
                info.registerPlantSeed(info.resourceName);
            } else if (info.size == Size.DOUBLE) {
                info.plantBlock = ModBlocks.registerTallFlower(info.resourceName, info.shape);
                if (info != MUTANT_PLANT) {
                    info.registerPlantSeed(info.resourceName);
                }
            } else if (info.size == Size.SINGLE_GROWABLE) {
                info.plantBlock = ModBlocks.registerGrowableFlower(info.resourceName, info.tallPlant.plantBlock,
                        info.shape);
                info.registerPlantSeed(info.commonName);
            } else if (info.size == Size.DOUBLE_GROWABLE || info == MUTANT_PLANT) {
                info.plantBlock = ModBlocks.registerTallFlower(info.resourceName, info.shape);
            } else if (info.size == Size.FOUR) {
                info.plantBlock = ModBlocks.registerFourTallFlower(info.resourceName, info.shape);
                info.registerPlantSeed(info.resourceName);
            }
        }
    }

    public static List<PrehistoricPlantInfo> plantsWithSeeds() {
        if (seedsCache == null) {
            seedsCache = Arrays.stream(values()).filter(type -> type.plantSeedItem != null).toList();
        }
        return seedsCache;
    }

    private void registerPlantSeed(String name) {
        this.fossilizedPlantSeedItem = ModItems.ITEMS.register("fossil_seed_" + name,
                () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
        this.plantSeedItem = ModItems.ITEMS.register("seed_" + name,
                () -> new FlowerSeedsItem(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB), this.plantBlock));
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

    public int berryAge() {
        return berryAge;
    }

    public int maxAge() {
        return maxAge;
    }

    public RegistrySupplier<Item> berryItem() {
        return berryItem;
    }

    enum Size {
        SINGLE, DOUBLE, SINGLE_GROWABLE, DOUBLE_GROWABLE, FOUR, SINGLE_BERRY, DOUBLE_BERRY
    }
}
