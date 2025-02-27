package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.IntStream;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;

public class ModBlockStateProvider extends BlockStateProvider {

    public static final String BLOCK_FOLDER2 = "block/";
    private final ModBlockModelProvider blockModels;
    private final ModItemProvider itemModels;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FossilMod.MOD_ID, exFileHelper);
        blockModels = new ModBlockModelProvider(output, exFileHelper);
        itemModels = new ModItemProvider(output, exFileHelper);
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
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
                if (info == PrehistoricPlantInfo.DIPTERIS || info == PrehistoricPlantInfo.ZAMITES) {
                    continue;
                }
                BushBlock flower = info.getPlantBlock();
                if (flower instanceof ShortBerryBushBlock shortBerry) {
                    shortBerryBlock(info, shortBerry);
                } else if (flower instanceof TallBerryBushBlock tallBerry) {
                    tallBerryBlock(info, tallBerry);
                } else if (flower instanceof ShortFlowerBlock shortFlower) {
                    shortFlowerBlock(shortFlower);
                } else if (flower instanceof TallFlowerBlock tallFlower) {
                    tallFlowerBlock(tallFlower);
                } else if (flower instanceof FourTallFlowerBlock tallFlower) {
                    fourTallFlowerBlock(tallFlower);
                }
            }
            var blockState = getVariantBuilder(FERNS.get());
            ResourceLocation[] textures = IntStream.rangeClosed(0, FernsBlock.UPPER_MAX_AGE).mapToObj(age -> FossilMod.location("block/plants/plant_ferns_stage" + age)).toArray(ResourceLocation[]::new);
            models().registerExistingTexture(textures);
            ResourceLocation fernsCrop = FossilMod.location("block/plant_ferns_template");
            models().registerExistingModel(fernsCrop);
            for (int i = 0; i <= FernsBlock.UPPER_MAX_AGE; i++) {
                blockState.partialState().with(FernsBlock.AGE, i).setModels(new ConfiguredModel(models().singleTexture("block/plants/plant_ferns_stage" + i, fernsCrop, "crop", textures[i])));
            }
        }
        if (vases) {
            ResourceLocation amphoraTemplateBase = FossilMod.location("block/template_vase_amphora_base");
            ResourceLocation amphoraTemplateTop = FossilMod.location("block/template_vase_amphora_top");
            ResourceLocation kylixTemplate = FossilMod.location("block/template_vase_kylix");
            ResourceLocation voluteTemplate = FossilMod.location("block/template_vase_volute");
            models().registerExistingModel(amphoraTemplateBase, amphoraTemplateTop, kylixTemplate, voluteTemplate);
            for (RegistrySupplier<VaseBlock> vaseReg : ModBlocks.VASES) {
                VaseBlock block = vaseReg.get();
                if (block instanceof AmphoraVaseBlock) {
                    amphora(block, amphoraTemplateBase, amphoraTemplateTop);
                } else if (block instanceof KylixVaseBlock) {
                    vaseBlock(block, kylixTemplate);
                } else if (block instanceof VoluteVaseBlock) {
                    vaseBlock(block, voluteTemplate);
                }
            }
        }
        ModBlocks.BLOCKS.forEach(supplier -> supplier.ifPresent(this::registerExistingTextures));
        if (buildingWood) {
            ResourceLocation ancientStone = blockTexture(ANCIENT_STONE_BRICKS.get());
            simpleBlock(ANCIENT_STONE_BRICKS.get());
            slabBlock(ANCIENT_STONE_SLAB.get(), ancientStone);
            stairsBlock(ANCIENT_STONE_STAIRS.get(), ancientStone);
            wallBlock(ANCIENT_STONE_WALL.get(), ancientStone);

            ResourceLocation ancientWood = blockTexture(ANCIENT_WOOD_PLANKS.get());
            simpleBlock(ANCIENT_WOOD_PLANKS.get());
            slabBlock(ANCIENT_WOOD_SLAB.get(), ancientWood);
            stairsBlock(ANCIENT_WOOD_STAIRS.get(), ancientWood);
            logBlock(ANCIENT_WOOD_LOG.get());

            ResourceLocation volcanicBrick = blockTexture(VOLCANIC_BRICKS.get());
            simpleBlock(VOLCANIC_BRICKS.get());
            slabBlock(VOLCANIC_BRICK_SLAB.get(), volcanicBrick);
            stairsBlock(VOLCANIC_BRICK_STAIRS.get(), volcanicBrick);
            wallBlock(VOLCANIC_BRICK_WALL.get(), volcanicBrick);

            ResourceLocation volcanicTile = blockTexture(VOLCANIC_TILES.get());
            simpleBlock(VOLCANIC_TILES.get());
            slabBlock(VOLCANIC_TILE_SLAB.get(), volcanicTile);
            stairsBlock(VOLCANIC_TILE_STAIRS.get(), volcanicTile);
            wallBlock(VOLCANIC_TILE_WALL.get(), volcanicTile);

            ResourceLocation calamites = blockTexture(CALAMITES_PLANKS.get());
            simpleBlock(CALAMITES_PLANKS.get());
            stairsBlock(CALAMITES_STAIRS.get(), calamites);
            slabBlock(CALAMITES_SLAB.get(), calamites);
            fenceBlock(CALAMITES_FENCE.get(), calamites);
            fenceGateBlock(CALAMITES_FENCE_GATE.get(), calamites);
            doorBlock(CALAMITES_DOOR.get());
            trapdoorBlock(CALAMITES_TRAPDOOR.get(), true);
            buttonBlock(CALAMITES_BUTTON.get(), calamites);
            pressurePlateBlock(CALAMITES_PRESSURE_PLATE.get(), calamites);
            logBlock(CALAMITES_LOG.get());
            woodBlock(CALAMITES_WOOD.get(), CALAMITES_LOG.get());
            logBlock(STRIPPED_CALAMITES_LOG.get());
            woodBlock(STRIPPED_CALAMITES_WOOD.get(), STRIPPED_CALAMITES_LOG.get());
            leavesBlock(CALAMITES_LEAVES.get());
            crossBlock(CALAMITES_SAPLING.get());

            ResourceLocation cordaites = blockTexture(CORDAITES_PLANKS.get());
            simpleBlock(CORDAITES_PLANKS.get());
            stairsBlock(CORDAITES_STAIRS.get(), cordaites);
            slabBlock(CORDAITES_SLAB.get(), cordaites);
            fenceBlock(CORDAITES_FENCE.get(), cordaites);
            fenceGateBlock(CORDAITES_FENCE_GATE.get(), cordaites);
            doorBlock(CORDAITES_DOOR.get());
            trapdoorBlock(CORDAITES_TRAPDOOR.get(), true);
            buttonBlock(CORDAITES_BUTTON.get(), cordaites);
            pressurePlateBlock(CORDAITES_PRESSURE_PLATE.get(), cordaites);
            logBlock(CORDAITES_LOG.get());
            woodBlock(CORDAITES_WOOD.get(), CORDAITES_LOG.get());
            logBlock(STRIPPED_CORDAITES_LOG.get());
            woodBlock(STRIPPED_CORDAITES_WOOD.get(), STRIPPED_CORDAITES_LOG.get());
            leavesBlock(CORDAITES_LEAVES.get());
            crossBlock(CORDAITES_SAPLING.get());

            ResourceLocation mutantTree = blockTexture(MUTANT_TREE_PLANKS.get());
            simpleBlock(MUTANT_TREE_PLANKS.get());
            stairsBlock(MUTANT_TREE_STAIRS.get(), mutantTree);
            slabBlock(MUTANT_TREE_SLAB.get(), mutantTree);
            fenceBlock(MUTANT_TREE_FENCE.get(), mutantTree);
            fenceGateBlock(MUTANT_TREE_FENCE_GATE.get(), mutantTree);
            doorBlock(MUTANT_TREE_DOOR.get());
            trapdoorBlock(MUTANT_TREE_TRAPDOOR.get(), true);
            buttonBlock(MUTANT_TREE_BUTTON.get(), mutantTree);
            pressurePlateBlock(MUTANT_TREE_PRESSURE_PLATE.get(), mutantTree);
            logBlock(MUTANT_TREE_LOG.get());
            woodBlock(MUTANT_TREE_WOOD.get(), MUTANT_TREE_LOG.get());
            logBlock(STRIPPED_MUTANT_TREE_LOG.get());
            woodBlock(STRIPPED_MUTANT_TREE_WOOD.get(), STRIPPED_MUTANT_TREE_LOG.get());
            leavesBlock(MUTANT_TREE_LEAVES.get());
            crossBlock(MUTANT_TREE_SAPLING.get());

            ResourceLocation palm = blockTexture(PALM_PLANKS.get());
            simpleBlock(PALM_PLANKS.get());
            stairsBlock(PALM_STAIRS.get(), palm);
            slabBlock(PALM_SLAB.get(), palm);
            fenceBlock(PALM_FENCE.get(), palm);
            fenceGateBlock(PALM_FENCE_GATE.get(), palm);
            doorBlock(PALM_DOOR.get());
            trapdoorBlock(PALM_TRAPDOOR.get(), true);
            buttonBlock(PALM_BUTTON.get(), palm);
            pressurePlateBlock(PALM_PRESSURE_PLATE.get(), palm);
            logBlock(PALM_LOG.get());
            woodBlock(PALM_WOOD.get(), PALM_LOG.get());
            logBlock(STRIPPED_PALM_LOG.get());
            woodBlock(STRIPPED_PALM_WOOD.get(), STRIPPED_PALM_LOG.get());
            leavesBlock(PALM_LEAVES.get());
            crossBlock(PALM_SAPLING.get());

            ResourceLocation sigillaria = blockTexture(SIGILLARIA_PLANKS.get());
            simpleBlock(SIGILLARIA_PLANKS.get());
            stairsBlock(SIGILLARIA_STAIRS.get(), sigillaria);
            slabBlock(SIGILLARIA_SLAB.get(), sigillaria);
            fenceBlock(SIGILLARIA_FENCE.get(), sigillaria);
            fenceGateBlock(SIGILLARIA_FENCE_GATE.get(), sigillaria);
            doorBlock(SIGILLARIA_DOOR.get());
            trapdoorBlock(SIGILLARIA_TRAPDOOR.get(), true);
            buttonBlock(SIGILLARIA_BUTTON.get(), sigillaria);
            pressurePlateBlock(SIGILLARIA_PRESSURE_PLATE.get(), sigillaria);
            logBlock(SIGILLARIA_LOG.get());
            woodBlock(SIGILLARIA_WOOD.get(), SIGILLARIA_LOG.get());
            logBlock(STRIPPED_SIGILLARIA_LOG.get());
            woodBlock(STRIPPED_SIGILLARIA_WOOD.get(), STRIPPED_SIGILLARIA_LOG.get());
            leavesBlock(SIGILLARIA_LEAVES.get());
            crossBlock(SIGILLARIA_SAPLING.get());

            ResourceLocation tempskya = blockTexture(TEMPSKYA_PLANKS.get());
            simpleBlock(TEMPSKYA_PLANKS.get());
            stairsBlock(TEMPSKYA_STAIRS.get(), tempskya);
            slabBlock(TEMPSKYA_SLAB.get(), tempskya);
            fenceBlock(TEMPSKYA_FENCE.get(), tempskya);
            fenceGateBlock(TEMPSKYA_FENCE_GATE.get(), tempskya);
            doorBlock(TEMPSKYA_DOOR.get());
            trapdoorBlock(TEMPSKYA_TRAPDOOR.get(), true);
            buttonBlock(TEMPSKYA_BUTTON.get(), tempskya);
            pressurePlateBlock(TEMPSKYA_PRESSURE_PLATE.get(), tempskya);
            logBlock(TEMPSKYA_LOG.get());
            woodBlock(TEMPSKYA_WOOD.get(), TEMPSKYA_LOG.get());
            logBlock(STRIPPED_TEMPSKYA_LOG.get());
            woodBlock(STRIPPED_TEMPSKYA_WOOD.get(), STRIPPED_TEMPSKYA_LOG.get());
            crossBlock(TEMPSKYA_SAPLING.get());
        }

        amberChunkBlock(AMBER_CHUNK.get());
        amberChunkBlock(AMBER_CHUNK_DOMINICAN.get());
        amberChunkBlock(AMBER_CHUNK_MOSQUITO.get());
        simpleBlock(CALCITE_FOSSIL.get());
        simpleBlock(DEEPSLATE_FOSSIL.get());
        simpleBlock(DRIPSTONE_FOSSIL.get());
        simpleBlock(RED_SANDSTONE_FOSSIL.get());
        simpleBlock(SANDSTONE_FOSSIL.get());
        simpleBlock(STONE_FOSSIL.get());
        simpleBlock(TUFF_FOSSIL.get());

        ResourceLocation shell = blockTexture(SHELL.get());
        models().registerExistingModel(shell);
        horizontalBlock(SHELL.get(), models().getExistingFile(shell));
    }

    public void registerExistingTextures(Block... blocks) {
        for (Block block : blocks) {
            models().registerExistingTexture(blockTexture(block));
        }
    }

    public void woodBlock(RotatedPillarBlock block, RotatedPillarBlock log) {
        itemModels().blockItem(key(block));
        ModelFile wood = models().cubeColumn(key(block).getPath(), blockTexture(log), blockTexture(log));
        axisBlock(block, wood, wood);
    }

    @Override
    public void logBlock(RotatedPillarBlock block) {
        itemModels().blockItem(key(block));
        models().registerExistingTexture(FossilMod.location(blockTexture(block).getPath() + "_top"));
        super.logBlock(block);
    }

    @Override
    public void simpleBlock(Block block) {
        itemModels().blockItem(key(block));
        super.simpleBlock(block);
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block));
        super.stairsBlock(block, texture);
    }


    public void slabBlock(SlabBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block));
        getVariantBuilder(block)
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(models().slab(key(block).getPath(), texture, texture, texture)))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(models().slabTop(key(block).getPath() + "_top", texture, texture, texture)))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(models().getExistingFile(texture)));
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block), "_inventory");
        models().singleTexture(BLOCK_FOLDER2 + key(block).getPath() + "_inventory", mcLoc("wall_inventory"), "wall", texture);
        super.wallBlock(block, texture);
    }

    @Override
    public void fenceBlock(FenceBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block), "_inventory");
        models().singleTexture(BLOCK_FOLDER2 + key(block).getPath() + "_inventory", mcLoc("fence_inventory"), texture);
        //getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
        super.fenceBlock(block, texture);
    }

    @Override
    public void fenceGateBlock(FenceGateBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block));
        super.fenceGateBlock(block, texture);
    }

    private ResourceLocation expand(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    public void doorBlock(DoorBlock block) {
        itemModels().simpleItem(key(block), false);
        ResourceLocation bottom = expand(blockTexture(block), "_bottom");
        ResourceLocation top = expand(blockTexture(block), "_top");
        models().registerExistingTexture(bottom, top);
        super.doorBlock(block, bottom, top);
    }

    public void trapdoorBlock(TrapDoorBlock block, boolean orientable) {
        itemModels().blockItem(key(block), "_bottom");
        models().registerExistingTexture(blockTexture(block));
        super.trapdoorBlock(block, blockTexture(block), orientable);
    }

    @Override
    public void buttonBlock(ButtonBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block), "_inventory");
        models().singleTexture(BLOCK_FOLDER2 + key(block).getPath() + "_inventory", mcLoc("button_inventory"), texture);
        //getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
        super.buttonBlock(block, texture);
    }

    @Override
    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        itemModels().blockItem(key(block));
        super.pressurePlateBlock(block, texture);
    }

    public void crossBlock(Block block) {
        itemModels().simpleItem(key(block), true);
        ResourceLocation texture = blockTexture(block);
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture(BLOCK_FOLDER2 + key(block).getPath(), mcLoc("cross"), "cross", texture);
        getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
    }

    public void leavesBlock(LeavesBlock block) {
        itemModels().blockItem(key(block));
        ResourceLocation texture = blockTexture(block);
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture(BLOCK_FOLDER2 + key(block).getPath(), mcLoc("leaves"), "all", texture);
        getVariantBuilder(block).partialState().setModels(ConfiguredModel.builder().modelFile(file).buildLast());
    }

    public void amphora(VaseBlock block, ResourceLocation templateBase, ResourceLocation templateTop) {
        itemModels().vaseItem(key(block));
        ResourceLocation base = FossilMod.location("block/vases/vase_amphora_base");
        if (block == AMPHORA_VASE_DAMAGED.get()) {
            base = FossilMod.location("block/vases/vase_amphora_base_damaged");
        }
        ResourceLocation color = FossilMod.location("block/vases/" + key(block).getPath());
        models().registerExistingTexture(base, color);
        ModelFile fileBase = models().withExistingParent("block/vases/" + key(block).getPath() + "_base", templateBase)
                .texture("color", color)
                .texture("base", base);
        ModelFile fileTop = models().withExistingParent("block/vases/" + key(block).getPath() + "_top", templateTop)
                .texture("color", color)
                .texture("base", base);
        getVariantBuilder(block).forAllStates(state ->
                ConfiguredModel.builder().modelFile(state.getValue(AmphoraVaseBlock.HALF) == DoubleBlockHalf.UPPER ? fileTop : fileBase)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build());
    }

    public void vaseBlock(VaseBlock block, ResourceLocation template) {
        itemModels().vaseItem(key(block));
        ResourceLocation texture = FossilMod.location("block/vases/" + key(block).getPath());
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture("block/vases/" + key(block).getPath(), template, texture);
        horizontalBlock(block, file);
    }

    public void amberChunkBlock(Block block) {
        ResourceLocation template = FossilMod.location("block/amber_chunk_template");
        models().registerExistingModel(template);
        itemModels().basicItem(key(block));
        ResourceLocation texture = FossilMod.location(BLOCK_FOLDER2 + key(block).getPath());
        models().registerExistingTexture(texture);
        ModelFile file = models().singleTexture(BLOCK_FOLDER2 + key(block).getPath(), template, "all", texture);
        horizontalBlock(block, file, 90);
    }

    private void shortBerryBlock(PrehistoricPlantInfo info, ShortBerryBushBlock block) {
        itemModels().plantBlockItem(block, "_stage" + info.maxAge());
        String name = key(block).getPath();
        var blockState = getVariantBuilder(block);
        ResourceLocation[] textures = IntStream.rangeClosed(0, info.maxAge()).mapToObj(age -> FossilMod.location("block/plants/plant_" + name + "_stage" + age)).toArray(ResourceLocation[]::new);
        models().registerExistingTexture(textures);
        for (int i = 0; i <= info.maxAge(); i++) {
            blockState.partialState().with(block.ageProperty(), i).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_stage" + i, textures[i])));
        }
    }

    private void tallBerryBlock(PrehistoricPlantInfo info, TallBerryBushBlock block) {
        itemModels().plantBlockItem(block, "_2_stage" + info.maxAge());
        String name = key(block).getPath();
        var blockState = getVariantBuilder(block);
        ResourceLocation[] lower = IntStream.rangeClosed(0, info.maxAge()).mapToObj(age -> FossilMod.location("block/plants/plant_" + name + "_1_stage" + age)).toArray(ResourceLocation[]::new);
        ResourceLocation[] upper = IntStream.rangeClosed(0, info.maxAge()).mapToObj(age -> FossilMod.location("block/plants/plant_" + name + "_2_stage" + age)).toArray(ResourceLocation[]::new);
        models().registerExistingTexture(lower);
        models().registerExistingTexture(upper);
        for (int i = 0; i <= info.maxAge(); i++) {
            blockState.partialState().with(block.ageProperty(), i).with(TallBerryBushBlock.HALF, DoubleBlockHalf.LOWER).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_1_stage" + i, lower[i])))
                    .partialState().with(block.ageProperty(), i).with(TallBerryBushBlock.HALF, DoubleBlockHalf.UPPER).setModels(new ConfiguredModel(models().cross("block/plants/" + name + "_2_stage" + i, upper[i])));
        }
    }


    public void shortFlowerBlock(ShortFlowerBlock block) {
        itemModels().plantBlockItem(block, "");
        ResourceLocation flower = FossilMod.location("block/plants/plant_" + key(block).getPath());
        models().registerExistingTexture(flower);
        getVariantBuilder(block).partialState().setModels(
                new ConfiguredModel(models().cross("block/plants/" + key(block).getPath(), flower)));
    }

    public void tallFlowerBlock(TallFlowerBlock block) {
        itemModels().plantBlockItem(block, "_2");
        ResourceLocation lower = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_1");
        ResourceLocation upper = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_2");
        models().registerExistingTexture(lower, upper);
        getVariantBuilder(block)
                .partialState().with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_1", lower)))
                .partialState().with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_2", upper)));
    }

    public void fourTallFlowerBlock(FourTallFlowerBlock block) {
        itemModels().plantBlockItem(block, "_1");
        ResourceLocation first = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_1");
        ResourceLocation second = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_2");
        ResourceLocation third = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_3");
        ResourceLocation fourth = FossilMod.location("block/plants/plant_" + key(block).getPath() + "_4");
        models().registerExistingTexture(first, second, third, fourth);
        getVariantBuilder(block)
                .partialState().with(FourTallFlowerBlock.LAYER, 0).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_1", first)))
                .partialState().with(FourTallFlowerBlock.LAYER, 1).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_2", second)))
                .partialState().with(FourTallFlowerBlock.LAYER, 2).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_3", third)))
                .partialState().with(FourTallFlowerBlock.LAYER, 3).setModels(
                        new ConfiguredModel(models().cross("block/plants/" + key(block).getPath() + "_4", fourth)));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
