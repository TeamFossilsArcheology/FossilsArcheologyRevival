package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownBirdEgg extends ThrowableItemProjectile {
    private EntityInfo info;
    private boolean cultivated;

    public ThrownBirdEgg(EntityType<? extends ThrownBirdEgg> entityType, Level level) {
        super(entityType, level);
    }

    private ThrownBirdEgg(Player player, Level level) {
        super(ModEntities.THROWN_BIRD_EGG.get(), player, level);
    }

    private ThrownBirdEgg(Level level, double x, double y, double z, boolean cultivated) {
        super(ModEntities.THROWN_BIRD_EGG.get(), x, y, z, level);
        this.cultivated = cultivated;
    }

    public static AbstractProjectileDispenseBehavior getProjectile(EntityInfo info, boolean cultivated) {
        return new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(Level level, Position position, ItemStack stack) {
                return Util.make(new ThrownBirdEgg(level, position.x(), position.y(), position.z(), cultivated), thrownEgg -> {
                    thrownEgg.setItem(stack);
                    thrownEgg.setType(info);
                });
            }
        };
    }

    public static ThrownBirdEgg get(Player player, Level level) {
        return new ThrownBirdEgg(player, level);
    }

    public void setType(EntityInfo info) {
        this.info = info;
    }

    public void setCultivated(boolean cultivated) {
        this.cultivated = cultivated;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().thrown(this, getOwner()), 0);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            if (cultivated) {
                spawnAnimal(1);
            } else if (random.nextInt(8) == 0) {
                int amount = 1;
                if (random.nextInt(32) == 0) {
                    amount = 4;
                }
                spawnAnimal(amount);
            }
            discard();
        } else {
            for (int i = 0; i < 8; ++i) {
                level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, getItem()), getX(), getY(), getZ(), (random.nextDouble() - 0.5) * 0.08, (this.random.nextDouble() - 0.5) * 0.08, (this.random.nextDouble() - 0.5) * 0.08);
            }
        }
    }

    private void spawnAnimal(int amount) {
        if (info.mobType() != PrehistoricMobType.VANILLA_BIRD) {
            for (int i = 0; i < amount; ++i) {
                Prehistoric entity = (Prehistoric) info.entityType().create(level());
                entity.finalizeSpawn((ServerLevel) level(), level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                entity.moveTo(getX(), getY(), getZ(), getYRot(), 0);
                level().addFreshEntity(entity);
                if (getOwner() instanceof ServerPlayer player) {
                    ModTriggers.INCUBATE_EGG_TRIGGER.trigger(player, entity);
                }
                Player nearestPlayer = level().getNearestPlayer(entity, 5);
                if (nearestPlayer != null) {
                    entity.tame(nearestPlayer);
                }
            }
        } else {
            for (int i = 0; i < amount; ++i) {
                AgeableMob entity;
                if (info == VanillaEntityInfo.PARROT) {
                    entity = EntityType.PARROT.create(level());
                } else {
                    entity = EntityType.CHICKEN.create(level());
                }
                entity.finalizeSpawn((ServerLevel) level(), level().getCurrentDifficultyAt(blockPosition()), MobSpawnType.BREEDING, null, null);
                entity.setAge(-24000);
                entity.moveTo(getX(), getY(), getZ(), getYRot(), 0);
                level().addFreshEntity(entity);
            }
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.EGG;
    }
}
