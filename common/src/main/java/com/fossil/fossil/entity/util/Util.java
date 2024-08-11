package com.fossil.fossil.entity.util;

import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Util {
    public static final int IMMOBILE = 0;
    public static final int ATTACK = 5;
    public static final int SLEEP = 10;
    public static final int NEEDS = 15;
    public static final int WANDER = 20;
    public static final int LOOK = 20;
    public static final float SWING_ANIM_THRESHOLD = 0.08f;

    public static void spawnParticles(Level level, ParticleOptions particleOptions, int count, AABB aabb) {
        for (int i = 0; i < count; i++) {
            double motionX = level.getRandom().nextGaussian() * 0.07;
            double motionY = level.getRandom().nextGaussian() * 0.07;
            double motionZ = level.getRandom().nextGaussian() * 0.07;
            float x = (float) (level.getRandom().nextFloat() * (aabb.maxX - aabb.minX) + aabb.minX);
            float y = (float) (level.getRandom().nextFloat() * (aabb.maxY - aabb.minY) + aabb.minY);
            float z = (float) (level.getRandom().nextFloat() * (aabb.maxZ - aabb.minZ) + aabb.minZ);
            level.addParticle(particleOptions, x, y, z, motionX, motionY, motionZ);
        }
    }

    public static void spawnParticles(Entity entity, ParticleOptions particleOptions, int count) {
        spawnParticles(entity.level, particleOptions, count, entity.getBoundingBoxForCulling());
    }

    public static void spawnItemParticles(Entity entity, Item item, int count) {
        spawnItemParticles(entity.level, item, count, entity.getBoundingBoxForCulling());
    }

    public static void spawnItemParticles(Level level, Item item, int count, AABB aabb) {
        spawnParticles(level, new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), count, aabb);
    }

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.getBbWidth() <= size;
        } else {
            return entity.getBbWidth() <= size;
        }
    }

    public static boolean canBreak(Level level, BlockPos targetPos, float maxHardness) {
        BlockState state = level.getBlockState(targetPos);
        if (state.getBlock() instanceof IDinoUnbreakable || state.getDestroySpeed(level, targetPos) >= maxHardness || state.getDestroySpeed(level, targetPos) < 0) {
            return false;
        }
        return !state.getCollisionShape(level, targetPos).isEmpty() && state.getFluidState().isEmpty();
    }

    public static boolean canReachPrey(Prehistoric prehistoric, Entity target) {
        return prehistoric.getAttackBounds().intersects(target.getBoundingBox()) && prehistoric.getSensing().hasLineOfSight(target);
    }

    public static boolean canSeeFood(Prehistoric dino, BlockPos position) {
        Vec3 target = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        BlockHitResult rayTrace = dino.getLevel().clip(new ClipContext(dino.position(), target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, dino));
        return rayTrace.getType() != HitResult.Type.MISS;
    }
}
