package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.entity.CultureVatBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
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

import java.util.Arrays;
import java.util.List;

/**
 * @see CultureVatBlockEntity
 * @see com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu
 * @see com.github.teamfossilsarcheology.fossil.client.gui.CultureVatScreen
 */
public class CultureVatBlock extends CustomEntityBlock {

    public static final EnumProperty<EmbryoType> EMBRYO = EnumProperty.create("embryo", EmbryoType.class);

    public CultureVatBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(EMBRYO, EmbryoType.GENERIC));
    }

    public void onFailedCultivation(Level level, BlockPos pos) {
        List<Player> nearby = level.getEntitiesOfClass(Player.class, new AABB(pos.offset(-50, -50, -50), pos.offset(50, 50, 50)));
        for (Player player : nearby) {
            player.displayClientMessage(Component.translatable("culture_vat.outBreak"), false);
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!level.isClientSide && blockEntity instanceof BaseContainerBlockEntity container) {
            if (blockEntity.getBlockState().getValue(EMBRYO) == EmbryoType.PLANT) {
                BlockState blockState = PrehistoricPlantInfo.MUTANT_PLANT.getPlantBlock().defaultBlockState();
                level.setBlockAndUpdate(pos, blockState);
                if (level.getBlockState(pos.above()).canBeReplaced()) {
                    level.setBlockAndUpdate(pos.above(), blockState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                }
            } else if (blockEntity.getBlockState().getValue(EMBRYO) == EmbryoType.TREE) {
                level.setBlockAndUpdate(pos, ModBlocks.MUTANT_TREE_SAPLING.get().defaultBlockState());
            } else {
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
                    entity = ModEntities.FAILURESAURUS.get().create(level);
                    Item dnaItem = container.getItem(CultureVatMenu.INPUT_SLOT_ID).getItem();
                    PrehistoricEntityInfo inputEntity = Arrays.stream(PrehistoricEntityInfo.values()).filter(info -> info.dnaItem == dnaItem).findFirst().orElse(null);
                    if (inputEntity != null) {
                        if (inputEntity == PrehistoricEntityInfo.DODO) {
                            ((Failuresaurus) entity).setVariant(Failuresaurus.Variant.DODO.name());
                        } else if (inputEntity.mobType == PrehistoricMobType.BIRD) {
                            ((Failuresaurus) entity).setVariant(Failuresaurus.Variant.FLYING.name());
                        } else if (inputEntity.mobType == PrehistoricMobType.FISH || inputEntity.mobType == PrehistoricMobType.DINOSAUR_FISH) {
                            ((Failuresaurus) entity).setVariant(Failuresaurus.Variant.FISH.name());
                        } else if (EntityDataLoader.INSTANCE.getData(inputEntity.resourceName).diet().canEat(FoodType.MEAT)) {
                            //Let's ignore that this probably isn't scientifically accurate
                            ((Failuresaurus) entity).setVariant(Failuresaurus.Variant.THEROPOD.name());
                        } else {
                            ((Failuresaurus) entity).setVariant(Failuresaurus.Variant.SAUROPOD.name());
                        }
                    }
                }
                if (!level.dimensionType().ultraWarm()) {
                    level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                    level.neighborChanged(pos, Blocks.WATER, pos);
                } else {
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
                }
                entity.moveTo(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, level.random.nextFloat() * 360f, 0.0f);
                level.addFreshEntity(entity);
                level.destroyBlock(pos, false);
            }
            dropIron(level, pos);
            dropInventory(level, pos);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1);
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
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.CULTURE_VAT.get(), CultureVatBlockEntity::serverTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EMBRYO);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CultureVatBlockEntity(pos, state);
    }

    public enum EmbryoType implements StringRepresentable {
        GENERIC("generic"),
        PLANT("plant"),
        TREE("tree"),
        LIMBLESS("limbless"),
        INSECT("insect"),
        NONE("none");

        private final String name;

        EmbryoType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
