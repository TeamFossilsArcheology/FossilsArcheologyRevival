package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.stream.IntStream;

public class ModBlockStateProvider extends BlockStateProvider {
    private final ModBlockModelProvider blockModels;
    private final ModItemProvider itemModels;

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Fossil.MOD_ID, exFileHelper);
        blockModels = new ModBlockModelProvider(gen, exFileHelper);
        itemModels = new ModItemProvider(gen, exFileHelper);
    }

    @Override
    public ModBlockModelProvider models() {
        return blockModels;
    }

    @Override
    public ModItemProvider itemModels() {
        return itemModels;
    }

    @Override
    protected void registerStatesAndModels() {
        boolean plantBlocks = true;
        boolean vases = true;
        boolean buildingWood = true;
        if (plantBlocks) {
            for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
                if (type == PrehistoricPlantType.DIPTERIS || type == PrehistoricPlantType.ZAMITES) {
                    continue;
                }
                BushBlock flower = type.getPlantBlock();
                if (flower instanceof ShortBerryBushBlock shortBerry) {
                    shortBerryBlock(type, shortBerry);
                } else if (flower instanceof TallBerryBushBlock tallBerry) {
                    tallBerryBlock(type, tallBerry);
                } else if (flower instanceof ShortFlowerBlock shortFlower) {
                    shortFlowerBlock(shortFlower);
                } else if (flower instanceof TallFlowerBlock tallFlower) {
                    tallFlowerBlock(tallFlower);
                } else if (flower instanceof FourTallFlowerBlock tallFlower) {
                    fourTallFlowerBlock(tallFlower);
                }
            }
            var blockState = getVariantBuilder(ModBlocks.FERNS.get());
            ResourceLocation[] textures = IntStream.rangeClosed(0, FernsBlock.UPPER_MAX_AGE).mapToObj(age -> new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_ferns_stage" + age)).toArray(ResourceLocation[]::new);
            models().registerExistingTexture(textures);
            ResourceLocation fernsCrop = new ResourceLocation(Fossil.MOD_ID, "block/plant_ferns_template");
            models().registerExistingModel(fernsCrop);
            for (int i = 0; i <= FernsBlock.UPPER_MAX_AGE; i++) {
                blockState.partialState().with(FernsBlock.AGE, i).setModels(new ConfiguredModel(models().singleTexture("block/plants/plant_ferns_stage" + i, fernsCrop, "crop", textures[i])));
            }
        }
        if (vases) {
            ResourceLocation amphoraTemplate = new ResourceLocation(Fossil.MOD_ID, "block/template_vase_amphora");
            ResourceLocation kylixTemplate = new ResourceLocation(Fossil.MOD_ID, "block/template_vase_kylix");
            ResourceLocation voluteTemplate = new ResourceLocation(Fossil.MOD_ID, "block/template_vase_volute");
            models().registerExistingModel(amphoraTemplate, kylixTemplate, voluteTemplate);
            for (RegistrySupplier<VaseBlock> vaseReg : ModBlocks.VASES) {
                VaseBlock block = vaseReg.get();
                if (block instanceof AmphoraVaseBlock) {
                    vaseBlock(block, amphoraTemplate);
                } else if (block instanceof KylixVaseBlock) {
                    vaseBlock(block, kylixTemplate);
                } else if (block instanceof VoluteVaseBlock) {
                    vaseBlock(block, voluteTemplate);
                }
            }
        }
        ModBlocks.BLOCKS.forEach(supplier -> supplier.ifPresent(this::registerExistingTextures));
        if (buildingWood) {
            ResourceLocation ancientStone = blockTexture(ModBlocks.ANCIENT_STONE_BRICKS.get());
            simpleBlock(ModBlocks.ANCIENT_STONE_BRICKS.get());
            slabBlock(ModBlocks.ANCIENT_STONE_SLAB.get(), ancientStone);
            stairsBlock(ModBlocks.ANCIENT_STONE_STAIRS.get(), ancientStone);
            wallBlock(ModBlocks.ANCIENT_STONE_WALL.get(), ancientStone);

            ResourceLocation ancientWood = blockTexture(ModBlocks.ANCIENT_WOOD_PLANKS.get());
            simpleBlock(ModBlocks.ANCIENT_WOOD_PLANKS.get());
            slabBlock(ModBlocks.ANCIENT_WOOD_SLAB.get(), ancientWood);
            stairsBlock(ModBlocks.ANCIENT_WOOD_STAIRS.get(), ancientWood);
            logBlock(ModBlocks.ANCIENT_WOOD_PILLAR.get());

            ResourceLocation volcanicBrick = blockTexture(ModBlocks.VOLCANIC_BRICKS.get());
            simpleBlock(ModBlocks.VOLCANIC_BRICKS.get());
            slabBlock(ModBlocks.VOLCANIC_BRICK_SLAB.get(), volcanicBrick);
            stairsBlock(ModBlocks.VOLCANIC_BRICK_STAIRS.get(), volcanicBrick);
            wallBlock(ModBlocks.VOLCANIC_BRICK_WALL.get(), volcanicBrick);

            ResourceLocation volcanicTile = blockTexture(ModBlocks.VOLCANIC_TILES.get());
            simpleBlock(ModBlocks.VOLCANIC_TILES.get());
            slabBlock(ModBlocks.VOLCANIC_TILE_SLAB.get(), volcanicTile);
            stairsBlock(ModBlocks.VOLCANIC_TILE_STAIRS.get(), volcanicTile);
            wallBlock(ModBlocks.VOLCANIC_TILE_WALL.get(), volcanicTile);

            ResourceLocation calamites = blockTexture(ModBlocks.CALAMITES_PLANKS.get());
            simpleBlock(ModBlocks.CALAMITES_PLANKS.get());
            stairsBlock(ModBlocks.CALAMITES_STAIRS.get(), calamites);
            slabBlock(ModBlocks.CALAMITES_SLAB.get(), calamites);
            fenceBlock(ModBlocks.CALAMITES_FENCE.get(), calamites);
            fenceGateBlock(ModBlocks.CALAMITES_FENCE_GATE.get(), calamites);
            doorBlock(ModBlocks.CALAMITES_DOOR.get());
            trapdoorBlock(ModBlocks.CALAMITES_TRAPDOOR.get());
            buttonBlock(ModBlocks.CALAMITES_BUTTON.get(), calamites);
            pressurePlateBlock(ModBlocks.CALAMITES_PRESSURE_PLATE.get(), calamites);
            logBlock(ModBlocks.CALAMITES_LOG.get());
            woodBlock(ModBlocks.CALAMITES_WOOD.get(), ModBlocks.CALAMITES_LOG.get());
            logBlock(ModBlocks.STRIPPED_CALAMITES_LOG.get());
            woodBlock(ModBlocks.STRIPPED_CALAMITES_WOOD.get(), ModBlocks.STRIPPED_CALAMITES_LOG.get());
            leavesBlock(ModBlocks.CALAMITES_LEAVES.get());
            crossBlock(ModBlocks.CALAMITES_SAPLING.get());

            ResourceLocation cordaites = blockTexture(ModBlocks.CORDAITES_PLANKS.get());
            simpleBlock(ModBlocks.CORDAITES_PLANKS.get());
            stairsBlock(ModBlocks.CORDAITES_STAIRS.get(), cordaites);
            slabBlock(ModBlocks.CORDAITES_SLAB.get(), cordaites);
            fenceBlock(ModBlocks.CORDAITES_FENCE.get(), cordaites);
            fenceGateBlock(ModBlocks.CORDAITES_FENCE_GATE.get(), cordaites);
            doorBlock(ModBlocks.CORDAITES_DOOR.get());
            trapdoorBlock(ModBlocks.CORDAITES_TRAPDOOR.get());
            buttonBlock(ModBlocks.CORDAITES_BUTTON.get(), cordaites);
            pressurePlateBlock(ModBlocks.CORDAITES_PRESSURE_PLATE.get(), cordaites);
            logBlock(ModBlocks.CORDAITES_LOG.get());
            woodBlock(ModBlocks.CORDAITES_WOOD.get(), ModBlocks.CORDAITES_LOG.get());
            logBlock(ModBlocks.STRIPPED_CORDAITES_LOG.get());
            woodBlock(ModBlocks.STRIPPED_CORDAITES_WOOD.get(), ModBlocks.STRIPPED_CORDAITES_LOG.get());
            leavesBlock(ModBlocks.CORDAITES_LEAVES.get());
            crossBlock(ModBlocks.CORDAITES_SAPLING.get());

            ResourceLocation mutantTree = blockTexture(ModBlocks.MUTANT_TREE_PLANKS.get());
            simpleBlock(ModBlocks.MUTANT_TREE_PLANKS.get());
            stairsBlock(ModBlocks.MUTANT_TREE_STAIRS.get(), mutantTree);
            slabBlock(ModBlocks.MUTANT_TREE_SLAB.get(), mutantTree);
            fenceBlock(ModBlocks.MUTANT_TREE_FENCE.get(), mutantTree);
            fenceGateBlock(ModBlocks.MUTANT_TREE_FENCE_GATE.get(), mutantTree);
            doorBlock(ModBlocks.MUTANT_TREE_DOOR.get());
            trapdoorBlock(ModBlocks.MUTANT_TREE_TRAPDOOR.get());
            buttonBlock(ModBlocks.MUTANT_TREE_BUTTON.get(), mutantTree);
            pressurePlateBlock(ModBlocks.MUTANT_TREE_PRESSURE_PLATE.get(), mutantTree);
            logBlock(ModBlocks.MUTANT_TREE_LOG.get());
            woodBlock(ModBlocks.MUTANT_TREE_WOOD.get(), ModBlocks.MUTANT_TREE_LOG.get());
            logBlock(ModBlocks.STRIPPED_MUTANT_TREE_LOG.get());
            woodBlock(ModBlocks.STRIPPED_MUTANT_TREE_WOOD.get(), ModBlocks.STRIPPED_MUTANT_TREE_LOG.get());
            leavesBlock(ModBlocks.MUTANT_TREE_LEAVES.get());
            crossBlock(ModBlocks.MUTANT_TREE_SAPLING.get());

            ResourceLocation palm = blockTexture(ModBlocks.PALM_PLANKS.get());
            simpleBlock(ModBlocks.PALM_PLANKS.get());
            stairsBlock(ModBlocks.PALM_STAIRS.get(), palm);
            slabBlock(ModBlocks.PALM_SLAB.get(), palm);
            fenceBlock(ModBlocks.PALM_FENCE.get(), palm);
            fenceGateBlock(ModBlocks.PALM_FENCE_GATE.get(), palm);
            doorBlock(ModBlocks.PALM_DOOR.get());
            trapdoorBlock(ModBlocks.PALM_TRAPDOOR.get());
            buttonBlock(ModBlocks.PALM_BUTTON.get(), palm);
            pressurePlateBlock(ModBlocks.PALM_PRESSURE_PLATE.get(), palm);
            logBlock(ModBlocks.PALM_LOG.get());
            woodBlock(ModBlocks.PALM_WOOD.get(), ModBlocks.PALM_LOG.get());
            logBlock(ModBlocks.STRIPPED_PALM_LOG.get());
            woodBlock(ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_LOG.get());
            leavesBlock(ModBlocks.PALM_LEAVES.get());
            crossBlock(ModBlocks.PALM_SAPLING.get());

            ResourceLocation sigillaria = blockTexture(ModBlocks.SIGILLARIA_PLANKS.get());
            simpleBlock(ModBlocks.SIGILLARIA_PLANKS.get());
            stairsBlock(ModBlocks.SIGILLARIA_STAIRS.get(), sigillaria);
            slabBlock(ModBlocks.SIGILLARIA_SLAB.get(), sigillaria);
            fenceBlock(ModBlocks.SIGILLARIA_FENCE.get(), sigillaria);
            fenceGateBlock(ModBlocks.SIGILLARIA_FENCE_GATE.get(), sigillaria);
            doorBlock(ModBlocks.SIGILLARIA_DOOR.get());
            trapdoorBlock(ModBlocks.SIGILLARIA_TRAPDOOR.get());
            buttonBlock(ModBlocks.SIGILLARIA_BUTTON.get(), sigillaria);
            pressurePlateBlock(ModBlocks.SIGILLARIA_PRESSURE_PLATE.get(), sigillaria);
            logBlock(ModBlocks.SIGILLARIA_LOG.get());
            woodBlock(ModBlocks.SIGILLARIA_WOOD.get(), ModBlocks.SIGILLARIA_LOG.get());
            logBlock(ModBlocks.STRIPPED_SIGILLARIA_LOG.get());
            woodBlock(ModBlocks.STRIPPED_SIGILLARIA_WOOD.get(), ModBlocks.STRIPPED_SIGILLARIA_LOG.get());
            leavesBlock(ModBlocks.SIGILLARIA_LEAVES.get());
            crossBlock(ModBlocks.SIGILLARIA_SAPLING.get());

            ResourceLocation tempskya = blockTexture(ModBlocks.TEMPSKYA_PLANKS.get());
            simpleBlock(ModBlocks.TEMPSKYA_PLANKS.get());
            stairsBlock(ModBlocks.TEMPSKYA_STAIRS.get(), tempskya);
            slabBlock(ModBlocks.TEMPSKYA_SLAB.get(), tempskya);
            fenceBlock(ModBlocks.TEMPSKYA_FENCE.get(), tempskya);
            fenceGateBlock(ModBlocks.TEMPSKYA_FENCE_GATE.get(), tempskya);
            doorBlock(ModBlocks.TEMPSKYA_DOOR.get());
            trapdoorBlock(ModBlocks.TEMPSKYA_TRAPDOOR.get());
            buttonBlock(ModBlocks.TEMPSKYA_BUTTON.get(), tempskya);
            pressurePlateBlock(ModBlocks.TEMPSKYA_PRESSURE_PLATE.get(), tempskya);
            logBlock(ModBlocks.TEMPSKYA_LOG.get());
            woodBlock(ModBlocks.TEMPSKYA_WOOD.get(), ModBlocks.TEMPSKYA_LOG.get());
            logBlock(ModBlocks.STRIPPED_TEMPSKYA_LOG.get());
            woodBlock(ModBlocks.STRIPPED_TEMPSKYA_WOOD.get(), ModBlocks.STRIPPED_TEMPSKYA_LOG.get());
            crossBlock(ModBlocks.TEMPSKYA_SAPLING.get());
        }
    }

    public void registerExistingTextures(Block... blocks) {
        for (Block block : blocks) {
            models().registerExistingTexture(blockTexture(block));
        }
    }

    public void woodBlock(RotatedPillarBlock block, RotatedPillarBlock log) {
        itemModels().blockItem(block.getRegistryName());
        ModelFile wood = models().cubeColumn(block.getRegistryName().getPath(), blockTexture(log), blockTexture(log));
        axisBlock(block, wood, wood);
    }

    public void logBlock(RotatedPillarBlock block) {
        itemModels().blockItem(block.getRegistryName());
        models().registerExistingTexture(new ResourceLocation(Fossil.MOD_ID, blockTexture(block).getPath() + "_top"));
        super.logBlock(block);
    }

    public void simpleBlock(Block block) {
        itemModels().blockItem(block.getRegistryName());
        super.simpleBlock(block);
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName());
        super.stairsBlock(block, texture);
    }


    public void slabBlock(SlabBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName());
        getVariantBuilder(block)
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(models().slab(block.getRegistryName().getPath(), texture, texture, texture)))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(models().slabTop(block.getRegistryName().getPath() + "_top", texture, texture, texture)))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(models().getExistingFile(texture)));
    }

    public void wallBlock(WallBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName(), "_inventory");
        models().singleTexture("block/" + block.getRegistryName().getPath() + "_inventory", mcLoc("wall_inventory"), "wall", texture);
        super.wallBlock(block, texture);
    }

    @Override
    public void fenceBlock(FenceBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName(), "_inventory");
        models().singleTexture("block/" + block.getRegistryName().getPath() + "_inventory", mcLoc("fence_inventory"), texture);
        //getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
        super.fenceBlock(block, texture);
    }

    @Override
    public void fenceGateBlock(FenceGateBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName());
        super.fenceGateBlock(block, texture);
    }

    private ResourceLocation expand(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    public void doorBlock(DoorBlock block) {
        itemModels().simpleItem(block.getRegistryName(), false);
        ResourceLocation bottom = expand(blockTexture(block), "_bottom");
        ResourceLocation top = expand(blockTexture(block), "_top");
        models().registerExistingTexture(bottom, top);
        super.doorBlock(block, bottom, top);
    }

    public void trapdoorBlock(TrapDoorBlock block) {
        itemModels().blockItem(block.getRegistryName(), "_bottom");
        models().registerExistingTexture(blockTexture(block));
        super.trapdoorBlock(block, blockTexture(block), false);
    }

    @Override
    public void buttonBlock(ButtonBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName(), "_inventory");
        models().singleTexture("block/" + block.getRegistryName().getPath() + "_inventory", mcLoc("button_inventory"), texture);
        //getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
        super.buttonBlock(block, texture);
    }

    @Override
    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        itemModels().blockItem(block.getRegistryName());
        super.pressurePlateBlock(block, texture);
    }

    public void crossBlock(Block block) {
        itemModels().simpleItem(block.getRegistryName(), true);
        ResourceLocation texture = blockTexture(block);
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture("block/" + block.getRegistryName().getPath(), mcLoc("cross"), "cross", texture);
        getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
    }

    public void leavesBlock(LeavesBlock block) {
        itemModels().blockItem(block.getRegistryName());
        ResourceLocation texture = blockTexture(block);
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture("block/" + block.getRegistryName().getPath(), mcLoc("leaves"), "all", texture);
        getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
    }

    public void vaseBlock(VaseBlock block, ResourceLocation template) {
        itemModels().vaseItem(block.getRegistryName());
        ResourceLocation texture = new ResourceLocation(Fossil.MOD_ID, "block/vases/" + block.getRegistryName().getPath());
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture("block/vases/" + block.getRegistryName().getPath(), template, texture);
        getVariantBuilder(block)
                .partialState().with(VaseBlock.FACING, Direction.NORTH).setModels(ConfiguredModel.builder()
                        .modelFile(file).buildLast())
                .partialState().with(VaseBlock.FACING, Direction.EAST).setModels(ConfiguredModel.builder()
                        .modelFile(file).rotationY(90).buildLast())
                .partialState().with(VaseBlock.FACING, Direction.SOUTH).setModels(ConfiguredModel.builder()
                        .modelFile(file).rotationY(180).buildLast())
                .partialState().with(VaseBlock.FACING, Direction.WEST).setModels(ConfiguredModel.builder()
                        .modelFile(file).rotationY(270).buildLast());
    }

    private void shortBerryBlock(PrehistoricPlantType type, ShortBerryBushBlock block) {
        itemModels().plantBlockItem(block, "_stage" + type.maxAge);
        String name = block.getRegistryName().getPath();
        var blockState = getVariantBuilder(block);
        ResourceLocation[] textures = IntStream.rangeClosed(0, type.maxAge).mapToObj(age -> new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + name + "_stage" + age)).toArray(ResourceLocation[]::new);
        models().registerExistingTexture(textures);
        for (int i = 0; i <= type.maxAge; i++) {
            blockState.partialState().with(block.ageProperty(), i).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_stage" + i, textures[i])));
        }
    }

    private void tallBerryBlock(PrehistoricPlantType type, TallBerryBushBlock block) {
        itemModels().plantBlockItem(block, "_2_stage" + type.maxAge);
        String name = block.getRegistryName().getPath();
        var blockState = getVariantBuilder(block);
        ResourceLocation[] lower = IntStream.rangeClosed(0, type.maxAge).mapToObj(age -> new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + name + "_1_stage" + age)).toArray(ResourceLocation[]::new);
        ResourceLocation[] upper = IntStream.rangeClosed(0, type.maxAge).mapToObj(age -> new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + name + "_2_stage" + age)).toArray(ResourceLocation[]::new);
        models().registerExistingTexture(lower);
        models().registerExistingTexture(upper);
        for (int i = 0; i <= type.maxAge; i++) {
            blockState.partialState().with(block.ageProperty(), i).with(TallBerryBushBlock.HALF, DoubleBlockHalf.LOWER).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_1_stage" + i, lower[i])))
                    .partialState().with(block.ageProperty(), i).with(TallBerryBushBlock.HALF, DoubleBlockHalf.UPPER).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_2_stage" + i, upper[i])));
        }
    }


    public void shortFlowerBlock(ShortFlowerBlock block) {
        itemModels().plantBlockItem(block, "");
        ResourceLocation flower = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath());
        models().registerExistingTexture(flower);
        getVariantBuilder(block).partialState().setModels(
                new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath(), flower)));
    }

    public void tallFlowerBlock(TallFlowerBlock block) {
        itemModels().plantBlockItem(block, "_2");
        ResourceLocation lower = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_1");
        ResourceLocation upper = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_2");
        models().registerExistingTexture(lower, upper);
        getVariantBuilder(block)
                .partialState().with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_1", lower)))
                .partialState().with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_2", upper)));
    }

    public void fourTallFlowerBlock(FourTallFlowerBlock block) {
        itemModels().plantBlockItem(block, "_1");
        ResourceLocation first = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_1");
        ResourceLocation second = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_2");
        ResourceLocation third = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_3");
        ResourceLocation fourth = new ResourceLocation(Fossil.MOD_ID, "block/plants/plant_" + block.getRegistryName().getPath() + "_4");
        models().registerExistingTexture(first, second, third, fourth);
        getVariantBuilder(block)
                .partialState().with(FourTallFlowerBlock.LAYER, 0).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_1", first)))
                .partialState().with(FourTallFlowerBlock.LAYER, 1).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_2", second)))
                .partialState().with(FourTallFlowerBlock.LAYER, 2).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_3", third)))
                .partialState().with(FourTallFlowerBlock.LAYER, 3).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + block.getRegistryName().getPath() + "_4", fourth)));
    }
}
