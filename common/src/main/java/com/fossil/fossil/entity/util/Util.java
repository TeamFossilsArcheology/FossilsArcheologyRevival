package com.fossil.fossil.entity.util;

import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.getBbWidth() <= size;
        } else {
            return entity.getBbWidth() <= size;
        }
    }

    public static boolean canBreak(Block block) {
        //TODO: Big break Test
        if (block instanceof IDinoUnbreakable) return false;
        BlockState state = block.defaultBlockState();
        if (!state.requiresCorrectToolForDrops()) return false;
        return !state.is(BlockTags.NEEDS_DIAMOND_TOOL);
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
