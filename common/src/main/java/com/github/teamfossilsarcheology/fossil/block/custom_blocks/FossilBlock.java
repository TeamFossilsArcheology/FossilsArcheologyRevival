package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FossilBlock extends Block {

    public FossilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> items = new ArrayList<>();
        int idx = builder.getLevel().random.nextInt(PrehistoricEntityInfo.entitiesWithBones().size());
        PrehistoricEntityInfo info = PrehistoricEntityInfo.entitiesWithBones().get(idx);
        builder = builder
                .withDynamicDrop(ModItemTags.ARM_BONES.location(), c -> c.accept(new ItemStack(info.armBoneItem)))
                .withDynamicDrop(ModItemTags.FOOT_BONES.location(), c -> c.accept(new ItemStack(info.footBoneItem)))
                .withDynamicDrop(ModItemTags.LEG_BONES.location(), c -> c.accept(new ItemStack(info.legBoneItem)))
                .withDynamicDrop(ModItemTags.RIBCAGE_BONES.location(), c -> c.accept(new ItemStack(info.ribcageBoneItem)))
                .withDynamicDrop(ModItemTags.SKULL_BONES.location(), c -> c.accept(new ItemStack(info.skullBoneItem)))
                .withDynamicDrop(ModItemTags.TAIL_BONES.location(), c -> c.accept(new ItemStack(info.tailBoneItem)))
                .withDynamicDrop(ModItemTags.UNIQUE_BONES.location(), c -> c.accept(new ItemStack(info.uniqueBoneItem)))
                .withDynamicDrop(ModItemTags.VERTEBRAE_BONES.location(), c -> c.accept(new ItemStack(info.vertebraeBoneItem)));
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments( builder.getParameter(LootContextParams.TOOL));
        int count = 1;
        int fortune = enchantments.getOrDefault(Enchantments.BLOCK_FORTUNE, 0);
        if (fortune > 0) {
            count += builder.getLevel().random.nextInt(fortune);
        }
        for (int i = 0; i < count; i++) {
            items.addAll(super.getDrops(state, builder));
        }
        return items;
    }

    @Override
    public @NotNull SoundType getSoundType(BlockState blockState) {
        return SoundType.STONE;
    }
}
