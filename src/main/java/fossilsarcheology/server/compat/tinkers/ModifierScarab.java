package fossilsarcheology.server.compat.tinkers;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModifierScarab extends ToolModifier {

    public ModifierScarab() {
        super("scarab", 0x620000);

        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ToolNBT data = TagUtil.getToolStats(rootCompound);
        data.durability += 1000;
        while (data.harvestLevel < HarvestLevels.COBALT) {
            data.harvestLevel++;

        }
        data.attack += 5f;
        data.speed += 2f;

        TagUtil.setToolTag(rootCompound, data.get());
    }
}