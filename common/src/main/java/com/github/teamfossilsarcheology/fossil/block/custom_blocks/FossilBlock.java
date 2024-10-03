package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
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
        int i = builder.getLevel().random.nextInt(PrehistoricEntityInfo.entitiesWithBones().size());
        PrehistoricEntityInfo info = PrehistoricEntityInfo.entitiesWithBones().get(i);
        builder = builder
                .withDynamicDrop(ModItemTags.ARM_BONES.location(), (a, c) -> c.accept(new ItemStack(info.armBoneItem)))
                .withDynamicDrop(ModItemTags.FOOT_BONES.location(), (a, c) -> c.accept(new ItemStack(info.footBoneItem)))
                .withDynamicDrop(ModItemTags.LEG_BONES.location(), (a, c) -> c.accept(new ItemStack(info.legBoneItem)))
                .withDynamicDrop(ModItemTags.RIBCAGE_BONES.location(), (a, c) -> c.accept(new ItemStack(info.ribcageBoneItem)))
                .withDynamicDrop(ModItemTags.SKULL_BONES.location(), (a, c) -> c.accept(new ItemStack(info.skullBoneItem)))
                .withDynamicDrop(ModItemTags.TAIL_BONES.location(), (a, c) -> c.accept(new ItemStack(info.tailBoneItem)))
                .withDynamicDrop(ModItemTags.UNIQUE_BONES.location(), (a, c) -> c.accept(new ItemStack(info.uniqueBoneItem)))
                .withDynamicDrop(ModItemTags.VERTEBRAE_BONES.location(), (a, c) -> c.accept(new ItemStack(info.vertebraeBoneItem)));
        return super.getDrops(state, builder);
    }

    @Override
    public @NotNull SoundType getSoundType(BlockState blockState) {
        return SoundType.STONE;
    }
}
