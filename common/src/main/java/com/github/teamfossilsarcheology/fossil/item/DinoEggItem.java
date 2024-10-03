package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class DinoEggItem extends PrehistoricEntityItem {
    public DinoEggItem(PrehistoricEntityInfo info) {
        super(new Properties().stacksTo(8), info, info.mobType == PrehistoricMobType.DINOSAUR_AQUATIC ? "fish_egg" : "egg");
    }

    public static boolean spawnEgg(Level level, PrehistoricEntityInfo info, double x, double y, double z, Player player) {
        if (player == null) {
            return false;
        }
        if (level.isClientSide) {
            return true;
        }
        if (!info.isViviparousAquatic()) {
            DinosaurEgg egg = ModEntities.DINOSAUR_EGG.get().create(level);
            if (egg == null) {
                return false;
            }
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.getRecipeBook().contains(DinosaurEgg.GOLDEN_EGG_RECIPE)) {
                egg.setGoldenEgg(true);
            }
            egg.moveTo(x, y, z, 0, 0);
            egg.setPrehistoricEntityInfo(info);
            level.addFreshEntity(egg);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, egg);
        } else {
            DinosaurEgg.hatchEgg(level, x, y, z, (ServerPlayer) player, info, false);
        }
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos offset = context.getClickedPos().relative(context.getClickedFace());
        if (!level.isEmptyBlock(offset) && !level.getBlockState(offset).canBeReplaced(new BlockPlaceContext(context))) {
            return InteractionResult.FAIL;
        }
        if (spawnEgg(level, (PrehistoricEntityInfo) info, offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, context.getPlayer())) {
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (level.isClientSide) {
            return InteractionResultHolder.success(itemStack);
        }
        BlockPos blockPos = hitResult.getBlockPos();
        if (!(level.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (!level.mayInteract(player, blockPos) || !player.mayUseItemAt(blockPos, hitResult.getDirection(), itemStack)) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (spawnEgg(level, (PrehistoricEntityInfo) info, blockPos.getX(), blockPos.getY(), blockPos.getZ(), player)) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.gameEvent(GameEvent.ENTITY_PLACE, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
