package fossilsarcheology.server.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class VolcanicSlabBlock extends FossilSlabBlock {
    public VolcanicSlabBlock(String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.VOLCANIC_BRICK);
    }

    @Override
    public ItemBlock getItemBlock() {
        return new FossilSlabBlockItem(this, FABlockRegistry.VOLCANIC_SINGLESLAB, FABlockRegistry.VOLCANIC_DOUBLESLAB);
    }

    public static class Double extends FossilSlabBlock {
        public Double(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.VOLCANIC_BRICK);
            this.setHarvestLevel("pickaxe", 0);
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, FABlockRegistry.VOLCANIC_SINGLESLAB, FABlockRegistry.VOLCANIC_DOUBLESLAB);
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(FABlockRegistry.VOLCANIC_SINGLESLAB);
        }

        @Override
        public boolean isDouble() {
            return true;
        }
    }

    public static class Half extends FossilSlabBlock {
        public Half(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.VOLCANIC_BRICK);
            this.setHarvestLevel("pickaxe", 0);
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, FABlockRegistry.VOLCANIC_SINGLESLAB, FABlockRegistry.VOLCANIC_DOUBLESLAB);
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(FABlockRegistry.VOLCANIC_SINGLESLAB);
        }

        @Override
        public boolean isDouble() {
            return false;
        }
    }
}
