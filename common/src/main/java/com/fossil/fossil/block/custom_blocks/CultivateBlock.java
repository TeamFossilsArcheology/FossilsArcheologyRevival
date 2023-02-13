package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.entity.CultivateBlockEntity;
import com.fossil.fossil.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CultivateBlock extends CustomEntityBlock {

    public static final EnumProperty<EmbryoType> EMBRYO = EnumProperty.create("embryo", EmbryoType.class);

    public CultivateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(EMBRYO, EmbryoType.GENERIC));
    }

    public void onFailedCultivation(Level level, BlockPos pos) {
        List<Player> nearby = level.getEntitiesOfClass(Player.class, new AABB(pos.offset(-50, -50, -50), pos.offset(50, 50, 50)));
        for (Player player : nearby) {
            player.displayClientMessage(new TranslatableComponent("cultivate.outBreak"), false);
        }
        dropIron(level, pos);
        dropInventory(level, pos);

        CultivateBlockEntity blockEntity = (CultivateBlockEntity) level.getBlockEntity(pos);
        if (!level.isClientSide && blockEntity != null) {
            level.destroyBlock(pos, false);
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1, false);
            if (blockEntity.getBlockState().getValue(EMBRYO) == EmbryoType.PLANT) {
                BlockState blockState = PrehistoricPlantType.MUTANT_PLANT.getPlantBlock().defaultBlockState();
                level.setBlockAndUpdate(pos, blockState);
                if (level.getBlockState(pos.above()).getMaterial().isReplaceable()) {
                    level.setBlockAndUpdate(pos.above(), blockState.setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER));
                }
            } else {
                if (!level.dimensionType().ultraWarm()) {
                    level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                    level.neighborChanged(pos, Blocks.WATER, pos);
                } else {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1, 1, false);
                }
                int random = level.random.nextInt(100);
                LivingEntity entity;
                if (random < 5) {
                    entity = EntityType.CREEPER.create(level);
                } else if (random < 10) {
                    entity = EntityType.PIGLIN.create(level);
                } else if (random < 15) {
                    entity = EntityType.ZOMBIE_HORSE.create(level);
                } else if (random < 20) {
                    entity = EntityType.MOOSHROOM.create(level);
                } else {
                    entity = EntityType.CHICKEN.create(level);//TODO: Failuresaurus
                }
                entity.moveTo(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, level.random.nextFloat() * 360f, 0.0f);
                level.addFreshEntity(entity);
            }
            level.removeBlockEntity(pos);
        }
    }

    private void dropIron(Level level, BlockPos pos) {
        ItemStack stack = new ItemStack(Items.IRON_INGOT, 1 + level.random.nextInt(2));
        float posX = pos.getX() + level.random.nextFloat() * 0.8f + 0.1f;
        float posY = pos.getY() + level.random.nextFloat() * 0.8f + 0.1f;
        float posZ = pos.getZ() + level.random.nextFloat() * 0.8f + 0.1f;
        ItemEntity item = new ItemEntity(level, posX, posY, posZ, stack);
        item.setDeltaMovement(level.random.nextGaussian() * 0.05f, level.random.nextGaussian() * 0.05f + 0.2f, level.random.nextGaussian() * 0.05f);
        level.addFreshEntity(item);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.CULTIVATE.get(), CultivateBlockEntity::serverTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EMBRYO);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CultivateBlockEntity(pos, state);
    }

    public enum EmbryoType implements StringRepresentable {
        GENERIC("generic"),
        PLANT("plant"),
        LIMBLESS("limbless"),
        INSECT("insect");

        private final String name;

        EmbryoType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
