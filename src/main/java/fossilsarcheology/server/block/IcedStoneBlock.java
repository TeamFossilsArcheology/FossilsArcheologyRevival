package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

public class IcedStoneBlock extends Block implements DefaultRenderedItem {
    public IcedStoneBlock() {
        super(Material.ROCK);
        this.setHarvestLevel("pickaxe", 1);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.GLASS);
        this.setTranslationKey("iced_stone");
        this.setRegistryName("iced_stone");
        this.setCreativeTab(FATabRegistry.BLOCKS);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - this.getDefaultState().getLightOpacity()) {
            this.turnIntoRock(worldIn, pos);
        }
    }

    protected void turnIntoRock(World worldIn, BlockPos pos) {
        if (worldIn.provider.doesWaterVaporize()) {
            worldIn.setBlockToAir(pos);
        } else {
            this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
            worldIn.setBlockState(pos, Blocks.STONE.getDefaultState());
            worldIn.notifyNeighborsOfStateChange(pos, Blocks.STONE, true);
        }
    }
}
