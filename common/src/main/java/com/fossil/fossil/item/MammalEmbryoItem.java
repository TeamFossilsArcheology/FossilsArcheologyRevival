package com.fossil.fossil.item;

import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MammalEmbryoItem extends Item {
    private final PrehistoricEntityType type;

    public MammalEmbryoItem(PrehistoricEntityType type) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.type = type;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Animal animal && PrehistoricEntityType.isMammal(animal) && !animal.isBaby()) {
            if (ModCapabilities.getEmbryoProgress(animal) > 0) {
                return InteractionResult.PASS;
            }
            if (!player.level.isClientSide) {
                ModCapabilities.startEmbryo(animal, type);
                stack.shrink(1);
            }
            //TODO: Particles
            return InteractionResult.sidedSuccess(player.level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
