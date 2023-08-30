package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.custom_blocks.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            BushBlock flower = type.getPlantBlock();
            if (flower instanceof ShortFlowerBlock shortFlower) {
                shortFlowerBlock(shortFlower);
            } else if (flower instanceof TallFlowerBlock tallFlower) {
                tallFlowerBlock(tallFlower);
            } else if (flower instanceof FourTallFlowerBlock tallFlower) {
                fourTallFlowerBlock(tallFlower);
            }
        }
        ResourceLocation amphoraTemplate = new ResourceLocation(Fossil.MOD_ID, "block/template_vase_amphora");
        models().registerExistingModel(amphoraTemplate);
        for (RegistrySupplier<VaseBlock> vaseReg : ModBlocks.VASES) {
            VaseBlock block = vaseReg.get();
            if (block instanceof AmphoraVaseBlock) {
                vaseBlock(block, amphoraTemplate);
            }
        }
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
