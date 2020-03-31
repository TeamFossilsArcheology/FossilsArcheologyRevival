package fossilsarcheology.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class FossilPlanksSlabBlock extends FossilSlabBlock {
    private String name;

    public FossilPlanksSlabBlock(Block parent, String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType, Material.WOOD, parent);
        this.name = name;
    }

    protected BlockSlab singleSlab() {
        if (name.contains("palm")) {
            return FABlockRegistry.PALM_PLANKS_SINGLESLAB;
        }
        if (name.contains("calamites")) {
            return FABlockRegistry.CALAMITES_PLANKS_SINGLESLAB;
        }
        if (name.contains("sigillaria")) {
            return FABlockRegistry.SIGILLARIA_PLANKS_SINGLESLAB;
        }
        if (name.contains("cordaites")) {
            return FABlockRegistry.CORDAITES_PLANKS_SINGLESLAB;
        }
        return FABlockRegistry.CALAMITES_PLANKS_SINGLESLAB;
    }

    protected BlockSlab doubleSlab() {
        if (name.contains("palm")) {
            return FABlockRegistry.PALM_PLANKS_DOUBLESLAB;
        }
        if (name.contains("calamites")) {
            return FABlockRegistry.CALAMITES_PLANKS_DOUBLESLAB;
        }
        if (name.contains("sigillaria")) {
            return FABlockRegistry.SIGILLARIA_PLANKS_DOUBLESLAB;
        }
        if (name.contains("cordaites")) {
            return FABlockRegistry.CORDAITES_PLANKS_DOUBLESLAB;
        }
        return FABlockRegistry.CALAMITES_PLANKS_SINGLESLAB;
    }


    @Override
    public ItemBlock getItemBlock() {
        return new FossilSlabBlockItem(this, singleSlab(), doubleSlab());
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    public static class Double extends FossilPlanksSlabBlock {
        public Double(Block parent, String name, float hardness, float resistance, SoundType soundType) {
            super(parent, name, hardness, resistance, soundType);
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, singleSlab(), doubleSlab());
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(singleSlab());
        }
    }

    public static class Half extends FossilPlanksSlabBlock {
        public Half(Block parent, String name, float hardness, float resistance, SoundType soundType) {
            super(parent, name, hardness, resistance, soundType);
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, singleSlab(), doubleSlab());
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(singleSlab());
        }
    }
}
