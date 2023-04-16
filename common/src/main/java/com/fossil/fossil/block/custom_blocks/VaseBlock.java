package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.entity.VaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class VaseBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected final VaseVariant variant;

    protected VaseBlock(VaseVariant variant) {
        super(Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops().noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.variant = variant;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public abstract ResourceLocation getVariantTexture();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VaseBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public enum VaseVariant implements StringRepresentable {
        DAMAGED("damaged"),
        RESTORED("restored"),
        RED_FIGURE("red_figure"),
        BLACK_FIGURE("black_figure"),
        PORCELAIN("porcelain");

        private final String name;
        private final ResourceLocation voluteTexture;
        private final ResourceLocation amphoraTexture;
        private final ResourceLocation kylixTexture;

        VaseVariant(String name) {
            this.name = name;
            this.voluteTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/vases/vase_" + name + "_volute.png");
            this.amphoraTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/vases/vase_" + name + "_amphora.png");
            this.kylixTexture = new ResourceLocation(Fossil.MOD_ID, "textures/block/vases/vase_" + name + "_kylix.png");
        }

        public ResourceLocation getVoluteTexture() {
            return this.voluteTexture;
        }

        public ResourceLocation getAmphoraTexture() {
            return this.amphoraTexture;
        }

        public ResourceLocation getKylixTexture() {
            return this.kylixTexture;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
