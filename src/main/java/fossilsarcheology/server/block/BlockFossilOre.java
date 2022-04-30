package fossilsarcheology.server.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockFossilOre extends OreBlock {

    public BlockFossilOre() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 5.0F).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE));
        this.setRegistryName("fossil:fossil_ore");

    }
}
