package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.List;

public class FossilBlock extends Block {
    public static final ResourceLocation LEG_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_leg");
    public static final ResourceLocation ARM_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_arm");
    public static final ResourceLocation FOOT_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_foot");
    public static final ResourceLocation SKULL_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_skull");
    public static final ResourceLocation RIBCAGE_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_ribcage");
    public static final ResourceLocation VERTEBRAE_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_vertebrae");
    public static final ResourceLocation UNIQUE_BONES = new ResourceLocation(Fossil.MOD_ID, "bones_unique");

    public FossilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        int i = builder.getLevel().random.nextInt(PrehistoricEntityType.entitiesWithBones().size());
        PrehistoricEntityType type = PrehistoricEntityType.entitiesWithBones().get(i);
        builder = builder.withDynamicDrop(LEG_BONES, (a, c) -> c.accept(new ItemStack(type.legBoneItem)))
                .withDynamicDrop(ARM_BONES, (arg2, consumer) -> consumer.accept(new ItemStack(type.armBoneItem)))
                .withDynamicDrop(FOOT_BONES, (a, c) -> c.accept(new ItemStack(type.footBoneItem)))
                .withDynamicDrop(SKULL_BONES, (a, c) -> c.accept(new ItemStack(type.skullBoneItem)))
                .withDynamicDrop(RIBCAGE_BONES, (a, c) -> c.accept(new ItemStack(type.ribcageBoneItem)))
                .withDynamicDrop(VERTEBRAE_BONES, (a, c) -> c.accept(new ItemStack(type.vertebraeBoneItem)))
                .withDynamicDrop(UNIQUE_BONES, (a, c) -> c.accept(new ItemStack(type.uniqueBoneItem)));
        return super.getDrops(state, builder);
    }

    @Override
    public SoundType getSoundType(BlockState p_49963_) {
        return SoundType.STONE;
    }
}
