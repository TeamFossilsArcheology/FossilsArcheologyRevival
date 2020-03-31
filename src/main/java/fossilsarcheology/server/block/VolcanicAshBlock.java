package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class VolcanicAshBlock extends Block implements DefaultRenderedItem {
    public VolcanicAshBlock(String type) {
        super(type.equals("ash") ? Material.GROUND : Material.ROCK);
        if (type.equals("ash")) {
            this.blockParticleGravity = -0.15F;
            setHardness(0.2F);
            setSoundType(SoundType.GROUND);
        } else {
            setHardness(1.5F);
            setSoundType(SoundType.STONE);
        }
        this.setTranslationKey("volcanic_" + type);
        setCreativeTab(FATabRegistry.BLOCKS);
    }
}