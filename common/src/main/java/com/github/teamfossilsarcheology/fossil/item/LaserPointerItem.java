package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.client.particle.ModParticles;
import com.github.teamfossilsarcheology.fossil.entity.LaserPointEntity;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class LaserPointerItem extends Item {
    private static final double LASER_RANGE = 50;
    private static final String TAG_LASER_ENTITY_UUID = "LaserEntityUUID";
    private static final String TAG_USE_START_TIME = "UseStartTime";


    private static final float LASER_ACTIVE_LIFESPAN_MINUTES = 15f;


    public LaserPointerItem(Properties properties) {
        super(properties.durability((int)(LASER_ACTIVE_LIFESPAN_MINUTES * 60)));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            LaserPointEntity laserPoint = new LaserPointEntity(ModEntities.LASER_POINT.get(), level);
            laserPoint.setOwner(player);
            laserPoint.setPos(player.getX(), player.getEyeY(), player.getZ());
            level.addFreshEntity(laserPoint);

            stack.getOrCreateTag().putUUID(TAG_LASER_ENTITY_UUID, laserPoint.getUUID());
            stack.getOrCreateTag().putLong(TAG_USE_START_TIME, level.getGameTime());
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeCharged) {
        if (!level.isClientSide() && living instanceof Player) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.hasUUID(TAG_LASER_ENTITY_UUID)) {
                UUID entityUUID = tag.getUUID(TAG_LASER_ENTITY_UUID);
                Entity entity = ((ServerLevel) level).getEntity(entityUUID);
                if (entity instanceof LaserPointEntity) {
                    entity.discard();
                }
                tag.remove(TAG_LASER_ENTITY_UUID);
            }

            // apply durability
            // we are only applying durability after release so that the item doesn't keep bobbing
            // every time the durability decreases
            if (tag != null && tag.contains(TAG_USE_START_TIME)) {
                long startTime = tag.getLong(TAG_USE_START_TIME);
                long useDuration = level.getGameTime() - startTime;
                int damageToApply = (int) (useDuration / 20); // 1 damage per second

                if (damageToApply > 0) {
                    stack.hurtAndBreak(damageToApply, living, (p) -> p.broadcastBreakEvent(living.getUsedItemHand()));
                }

                tag.remove(TAG_USE_START_TIME);
            }
        }
    }


    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration) {
        if (!(living instanceof Player player)) return;

        // check if the laser should have broken by now (since we are only applying durability after release)
        // this is to prevent players from cheating by just not releasing the laser
        if (!level.isClientSide() && level.getGameTime() % 20 == 0) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains(TAG_USE_START_TIME)) {
                long startTime = tag.getLong(TAG_USE_START_TIME);
                long useDuration = level.getGameTime() - startTime;
                int damageToApply = (int) (useDuration / 20);

                if (stack.getDamageValue() + damageToApply >= stack.getMaxDamage()) {
                    player.stopUsingItem();
                    player.broadcastBreakEvent(player.getUsedItemHand());
                    stack.shrink(1);

                    // Clean up laser entity
                    if (tag.hasUUID(TAG_LASER_ENTITY_UUID)) {
                        UUID entityUUID = tag.getUUID(TAG_LASER_ENTITY_UUID);
                        Entity entity = ((ServerLevel) level).getEntity(entityUUID);
                        if (entity instanceof LaserPointEntity) {
                            entity.discard();
                        }
                    }
                    return;
                }
            }
        }


        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getLookAngle();
        Vec3 endVec = eyePos.add(lookVec.scale(LASER_RANGE));

        BlockHitResult hit = level.clip(new ClipContext(
                eyePos,
                endVec,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 hitPos = hit.getLocation();

        // move the laser point entity to raycast hit on the server side
        if (!level.isClientSide()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.hasUUID(TAG_LASER_ENTITY_UUID)) {
                Entity entity = ((ServerLevel) level).getEntity(tag.getUUID(TAG_LASER_ENTITY_UUID));
                if (entity instanceof LaserPointEntity laserPoint) {
                    laserPoint.updatePosition(hitPos.x(), hitPos.y(), hitPos.z());
                }
            }
        }

        if (level.isClientSide) {
            if (hit.getType() == HitResult.Type.BLOCK) {
                // using the normal so that it doesn't spawn inside the block :)
                Vec3 normal = Vec3.atLowerCornerOf(hit.getDirection().getNormal());

                spawnLaserParticles(level, hitPos.add(normal.scale(0.06f)), 3);
            }
        }
    }

    private void spawnLaserParticles(Level level, Vec3 pos, int count) {
        for (int i = 0; i < count; i++) {
            double x = pos.x + (level.random.nextDouble() - 0.5) * 0.05;
            double y = pos.y + (level.random.nextDouble() - 0.5) * 0.05;
            double z = pos.z + (level.random.nextDouble() - 0.5) * 0.05;

            level.addParticle(
                    ModParticles.LASER_PARTICLE.get(),
                    true,
                    x, y, z,
                    0, 0, 0
            );
        }
    }
}
