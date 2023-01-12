package com.fossil.fossil.client.renderer;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.custom_blocks.AncientChestBlock;
import com.fossil.fossil.block.custom_blocks.AnuStatueBlock;
import com.fossil.fossil.block.custom_blocks.AnubiteStatueBlock;
import com.fossil.fossil.block.custom_blocks.SarcophagusBlock;
import com.fossil.fossil.block.entity.AncientChestBlockEntity;
import com.fossil.fossil.block.entity.AnuStatueBlockEntity;
import com.fossil.fossil.block.entity.AnubiteStatueBlockEntity;
import com.fossil.fossil.block.entity.SarcophagusBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CustomItemRenderer extends BlockEntityWithoutLevelRenderer {

    public static CustomItemRenderer INSTANCE = new CustomItemRenderer();
    private final AnuStatueBlockEntity anuStatue = new AnuStatueBlockEntity(BlockPos.ZERO, ModBlocks.ANU_STATUE.get().defaultBlockState());
    private final AnubiteStatueBlockEntity anubiteStatue = new AnubiteStatueBlockEntity(BlockPos.ZERO,
            ModBlocks.ANUBITE_STATUE.get().defaultBlockState());
    private final AncientChestBlockEntity ancientChest = new AncientChestBlockEntity(BlockPos.ZERO,
            ModBlocks.ANCIENT_CHEST.get().defaultBlockState());
    private final SarcophagusBlockEntity sarcophagus = new SarcophagusBlockEntity(BlockPos.ZERO, ModBlocks.SARCOPHAGUS.get().defaultBlockState());
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();

    protected CustomItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer,
                             int packedLight, int packedOverlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {

            Block block = ((BlockItem) item).getBlock();
            if (block instanceof AnuStatueBlock) {
                blockEntityRenderDispatcher.renderItem(anuStatue, poseStack, buffer, packedLight, packedOverlay);
            } else if (block instanceof AnubiteStatueBlock) {
                blockEntityRenderDispatcher.renderItem(anubiteStatue, poseStack, buffer, packedLight, packedOverlay);
            } else if (block instanceof AncientChestBlock) {
                blockEntityRenderDispatcher.renderItem(ancientChest, poseStack, buffer, packedLight, packedOverlay);
            } else if (block instanceof SarcophagusBlock) {
                blockEntityRenderDispatcher.renderItem(sarcophagus, poseStack, buffer, packedLight, packedOverlay);
            }
            return;
        }
        super.renderByItem(stack, transformType, poseStack, buffer, packedLight, packedOverlay);
    }
}
