package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class NautilusMeatItem extends MeatItem {

    public NautilusMeatItem(PrehistoricEntityInfo info) {
        super(info, false);
    }

    public NautilusMeatItem(EntityInfo info, float saturation) {
        super(info, true, saturation, "cooked_nautilus");
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemStack = super.finishUsingItem(stack, level, livingEntity);
        if (itemStack.isEmpty()) {
            return new ItemStack(Items.NAUTILUS_SHELL);
        } else {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack shell = new ItemStack(Items.NAUTILUS_SHELL);
                if (!player.getInventory().add(shell)) {
                    player.drop(shell, false);
                }
            }

            return itemStack;
        }
    }
}
