package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import org.jetbrains.annotations.NotNull;

public class FlowerSeedsItem extends Item {
    private final RegistrySupplier<? extends BushBlock> flower;

    public FlowerSeedsItem(Properties properties, RegistrySupplier<? extends BushBlock> flower) {
        super(properties);
        this.flower = flower;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPlaceContext blockPlaceContext  = new BlockPlaceContext(context);
        if (!blockPlaceContext.canPlace()) {
            return InteractionResult.FAIL;
        }
        int clearance = 1;
        if (flower.get() instanceof TallFlowerBlock) {
            clearance = 2;
        } else if (flower.get() instanceof FourTallFlowerBlock) {
            clearance = 4;
        }
        BlockPos pos = blockPlaceContext.getClickedPos();
        Level level = context.getLevel();
        for (int i = 0; i < clearance; i++) {
            BlockPos up = pos.above(i);
            if (!level.isEmptyBlock(up)) {
                return InteractionResult.PASS;
            }
        }
        if (flower.get().defaultBlockState().canSurvive(level, pos)) {
            level.setBlock(pos, flower.get().defaultBlockState(), 3);
            if (flower.get() instanceof DoublePlantBlock tall) {
                tall.setPlacedBy(level, pos, null, null, null);
            } else if (flower.get() instanceof FourTallFlowerBlock tall) {
                tall.setPlacedBy(level, pos, null, null, null);
            }
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
