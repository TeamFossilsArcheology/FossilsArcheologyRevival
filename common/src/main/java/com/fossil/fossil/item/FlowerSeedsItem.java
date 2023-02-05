package com.fossil.fossil.item;

import com.fossil.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class FlowerSeedsItem extends Item {
    private final RegistrySupplier<? extends BushBlock> flower;

    public FlowerSeedsItem(Properties properties, RegistrySupplier<? extends BushBlock> flower) {
        super(properties);
        this.flower = flower;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        int clearance = 1;
        if (flower.get() instanceof TallFlowerBlock) {
            clearance = 2;
        } else if (flower.get() instanceof FourTallFlowerBlock) {
            clearance = 4;
        }
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        for (int i = 0; i < clearance; i++) {
            BlockPos up = pos.above(1 + i);
            if (!level.isEmptyBlock(up)) {
                return InteractionResult.PASS;
            }
        }
        if (flower.get().defaultBlockState().canSurvive(level, pos)) {
            level.setBlock(pos.above(), flower.get().defaultBlockState(), 3);
            if (flower.get() instanceof TallFlowerBlock tall) {
                level.setBlock(pos.above(2), tall.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 2);
            } else if (flower.get() instanceof FourTallFlowerBlock tall) {
                level.setBlock(pos.above(2), tall.defaultBlockState().setValue(FourTallFlowerBlock.LAYER, 1), 2);
                level.setBlock(pos.above(3), tall.defaultBlockState().setValue(FourTallFlowerBlock.LAYER, 2), 2);
                level.setBlock(pos.above(4), tall.defaultBlockState().setValue(FourTallFlowerBlock.LAYER, 3), 2);
            }
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
