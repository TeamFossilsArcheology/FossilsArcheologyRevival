package fossilsarcheology.server.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class AncientStoneSlabBlock extends FossilSlabBlock {
    public AncientStoneSlabBlock(String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.ANCIENT_STONE_BRICK);
    }

    @Override
    public ItemBlock getItemBlock() {
        return new FossilSlabBlockItem(this, FABlockRegistry.ANCIENT_STONE_SINGLESLAB, FABlockRegistry.ANCIENT_STONE_DOUBLESLAB);
    }


    public static class Double extends FossilSlabBlock {
        public Double(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.ANCIENT_STONE_BRICK);
            this.setHarvestLevel("pickaxe", 0);
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(FABlockRegistry.ANCIENT_STONE_SINGLESLAB);
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, FABlockRegistry.ANCIENT_STONE_SINGLESLAB, FABlockRegistry.ANCIENT_STONE_DOUBLESLAB);
        }
    }

    public static class Half extends FossilSlabBlock {
        public Half(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, Material.ROCK, FABlockRegistry.ANCIENT_STONE_SINGLESLAB);
            this.setHarvestLevel("pickaxe", 0);
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Override
        public Item getSlabItem() {
            return Item.getItemFromBlock(FABlockRegistry.ANCIENT_STONE_SINGLESLAB);
        }

        @Override
        public ItemBlock getItemBlock() {
            return new FossilSlabBlockItem(this, FABlockRegistry.ANCIENT_STONE_SINGLESLAB, FABlockRegistry.ANCIENT_STONE_DOUBLESLAB);
        }
    }
}
