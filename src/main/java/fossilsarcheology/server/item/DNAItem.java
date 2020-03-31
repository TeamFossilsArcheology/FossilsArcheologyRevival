package fossilsarcheology.server.item;

import fossilsarcheology.server.advancement.DNATrigger;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DNAItem extends PrehistoricEntityItem implements DefaultRenderedItem {
    public static final DNATrigger DNA_TRIGGER = CriteriaTriggers.register(new DNATrigger());

    public DNAItem(PrehistoricEntityType type) {
        super("dna", type);
    }

    public boolean isBugDNA() {
        return type == PrehistoricEntityType.NAUTILUS || type == PrehistoricEntityType.MEGANEURA || type == PrehistoricEntityType.ARTHROPLEURA;
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote && entityIn instanceof EntityPlayerMP) {
            DNA_TRIGGER.trigger((EntityPlayerMP) entityIn);
        }
    }
}
