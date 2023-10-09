package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.Fossil;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FigurineBlock extends HorizontalDirectionalBlock {
    protected final FigurineVariant variant;

    public FigurineBlock(FigurineVariant variant) {
        super(Properties.of(Material.DECORATION).sound(SoundType.STONE));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
        this.variant = variant;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public abstract ResourceLocation getTexture();

    public enum FigurineVariant implements StringRepresentable {
        DESTROYED("destroyed"),
        RESTORED("restored"),
        PRISTINE("pristine");

        private final String name;
        private final ResourceLocation endermanTexture;
        private final ResourceLocation piglinTexture;
        private final ResourceLocation skeletonTexture;
        private final ResourceLocation steveTexture;
        private final ResourceLocation zombieTexture;

        FigurineVariant(String name) {
            this.name = name;
            this.endermanTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/figurines/figurine_enderman_" + name + ".png");
            this.piglinTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/figurines/figurine_piglin_" + name + ".png");
            this.skeletonTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/figurines/figurine_skeleton_" + name + ".png");
            this.steveTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/figurines/figurine_steve_" + name + ".png");
            this.zombieTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/figurines/figurine_zombie_" + name + ".png");
        }

        public ResourceLocation getEndermanTexture() {
            return endermanTexture;
        }

        public ResourceLocation getPiglinTexture() {
            return piglinTexture;
        }

        public ResourceLocation getSkeletonTexture() {
            return skeletonTexture;
        }

        public ResourceLocation getSteveTexture() {
            return steveTexture;
        }

        public ResourceLocation getZombieTexture() {
            return zombieTexture;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
