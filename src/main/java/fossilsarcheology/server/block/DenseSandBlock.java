package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;

public class DenseSandBlock extends BlockFalling implements DefaultRenderedItem, IDinoUnbreakable {
	public DenseSandBlock() {
		this.setHardness(3.0F);
		this.setResistance(15F);
		this.setTranslationKey("dense_sand");
		this.setSoundType(SoundType.SAND);
		this.setCreativeTab(FATabRegistry.BLOCKS);
	}
}
