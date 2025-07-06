package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class EggItem extends PrehistoricEntityItem {
    public EggItem(EntityInfo info, String category) {
        super(new Properties().stacksTo(8), info, category);
    }

    /**
     * @param aquatic if the position is in water
     * @return if the entity has been spawned
     */
    protected abstract boolean spawnMob(ServerPlayer player, ServerLevel level, double x, double y, double z, boolean aquatic);

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        Vec3 clickedLocation = context.getClickLocation();
        Direction direction = context.getClickedFace();
        if (direction != Direction.UP) {
            clickedLocation = clickedLocation.add(Vec3.atLowerCornerOf(direction.getNormal()).multiply(0.5, 1, 0.5));
        }
        BlockPos clickedPos = BlockPos.containing(clickedLocation);
        ServerPlayer player = (ServerPlayer) context.getPlayer();
        if (spawnMob(player, (ServerLevel) level, clickedLocation.x, clickedLocation.y, clickedLocation.z, level.getBlockState(clickedPos).getBlock() instanceof LiquidBlock)) {
            if (!player.getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.CONSUME;
        }
        return super.useOn(context);
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
        Vec3 location = hitResult.getLocation();
        if (spawnMob((ServerPlayer) player, (ServerLevel) level, location.x, blockPos.getY(), location.z, true)) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
