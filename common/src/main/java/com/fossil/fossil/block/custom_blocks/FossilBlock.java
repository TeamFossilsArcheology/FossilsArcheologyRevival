package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.tags.ModItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FossilBlock extends Block {

    public FossilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        int i = builder.getLevel().random.nextInt(PrehistoricEntityType.entitiesWithBones().size());
        PrehistoricEntityType type = PrehistoricEntityType.entitiesWithBones().get(i);
        builder = builder
                .withDynamicDrop(ModItemTags.ARM_BONES.location(), (a, c) -> c.accept(new ItemStack(type.armBoneItem)))
                .withDynamicDrop(ModItemTags.FOOT_BONES.location(), (a, c) -> c.accept(new ItemStack(type.footBoneItem)))
                .withDynamicDrop(ModItemTags.LEG_BONES.location(), (a, c) -> c.accept(new ItemStack(type.legBoneItem)))
                .withDynamicDrop(ModItemTags.RIBCAGE_BONES.location(), (a, c) -> c.accept(new ItemStack(type.ribcageBoneItem)))
                .withDynamicDrop(ModItemTags.SKULL_BONES.location(), (a, c) -> c.accept(new ItemStack(type.skullBoneItem)))
                .withDynamicDrop(ModItemTags.TAIL_BONES.location(), (a, c) -> c.accept(new ItemStack(type.tailBoneItem)))
                .withDynamicDrop(ModItemTags.UNIQUE_BONES.location(), (a, c) -> c.accept(new ItemStack(type.uniqueBoneItem)))
                .withDynamicDrop(ModItemTags.VERTEBRAE_BONES.location(), (a, c) -> c.accept(new ItemStack(type.vertebraeBoneItem)));
        return super.getDrops(state, builder);
    }

    @Override
    public @NotNull SoundType getSoundType(BlockState blockState) {
        return SoundType.STONE;
    }
}
