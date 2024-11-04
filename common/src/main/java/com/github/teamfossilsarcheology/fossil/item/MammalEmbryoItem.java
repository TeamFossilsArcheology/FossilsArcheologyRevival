package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MammalEmbryoItem extends PrehistoricEntityItem {

    public MammalEmbryoItem(EntityInfo info) {
        super(info, "embryo");
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Animal animal && PrehistoricEntityInfo.isMammal(animal) && !animal.isBaby()) {
            if (ModCapabilities.hasEmbryo(animal)) {
                return InteractionResult.PASS;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                ModTriggers.IMPLANT_EMBRYO_TRIGGER.trigger(serverPlayer, stack);
                ModCapabilities.startPregnancy(animal, info);
                stack.shrink(1);
            }
            Random random = player.getRandom();
            for (int i = 0; i < 7; i++) {
                double x = animal.getX() + random.nextFloat() * animal.getBbWidth() * 2 - animal.getBbWidth();
                double y = animal.getY() + 0.5 + random.nextFloat() * animal.getBbHeight();
                double z = animal.getZ() + random.nextFloat() * animal.getBbWidth() * 2 - animal.getBbWidth();
                player.level.addParticle(ParticleTypes.SMOKE, x, y, z, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
            }
            return InteractionResult.sidedSuccess(player.level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
