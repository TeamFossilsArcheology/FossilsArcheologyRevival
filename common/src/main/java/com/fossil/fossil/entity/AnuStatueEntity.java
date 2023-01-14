package com.fossil.fossil.entity;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AnuStatueEntity extends Mob {
    public AnuStatueEntity(EntityType<? extends AnuStatueEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD) {
            return super.hurt(source, amount);
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().add(0, 0.095f, 0).multiply(1, 0.6f, 1));
        if (tickCount > 200) {
            level.explode(this, position().x, position().y, position().z, 5f, false, this.level.getGameRules()
                    .getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            createPortal();
        }
    }

    private void createPortal() {
        level.setBlock(blockPosition().below(), Blocks.OBSIDIAN.defaultBlockState(), 3);
        level.setBlock(blockPosition(), ModBlocks.ANU_PORTAL.get().defaultBlockState(), 18);
        level.setBlock(blockPosition().above(), ModBlocks.ANU_PORTAL.get().defaultBlockState(), 18);
        level.setBlock(blockPosition().above(2), Blocks.OBSIDIAN.defaultBlockState(), 3);
        remove(RemovalReason.DISCARDED);
    }
}
