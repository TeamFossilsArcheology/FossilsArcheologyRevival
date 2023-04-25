package com.fossil.fossil.entity;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ai.anu.AnuAvoidEntityGoal;
import com.fossil.fossil.entity.ai.anu.AnuMeleeAttackGoal;
import com.fossil.fossil.entity.ai.anu.FireballAttackGoal;
import com.fossil.fossil.entity.ai.anu.FlyNearTargetGoal;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.world.feature.structures.AnuDefenseHut;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Anu extends PathfinderMob implements RangedAttackMob {
    private static final TranslatableComponent SPAWN_1 = new TranslatableComponent("entity.fossil.anu.hello");
    private static final TranslatableComponent SPAWN_2 = new TranslatableComponent("entity.fossil.anu.fewBeaten");
    private static final TranslatableComponent ANU_COMBAT_BRUTES = new TranslatableComponent("entity.fossil.anu.brutes");
    private static final TranslatableComponent ANU_COMBAT_ARCHERS = new TranslatableComponent("entity.fossil.anu.archers");
    private static final TranslatableComponent ANU_COMBAT_BLAZES = new TranslatableComponent("entity.fossil.anu.blazes");
    private static final TranslatableComponent ANU_COMBAT_DRAW = new TranslatableComponent("entity.fossil.anu.draw");
    private static final TranslatableComponent ANU_COMBAT_COWARD = new TranslatableComponent("entity.fossil.anu.coward");
    private static final TranslatableComponent ANU_COMBAT_ANCIENT = new TranslatableComponent("entity.fossil.anu.ancient");
    private static final TranslatableComponent ANU_DEATH = new TranslatableComponent("entity.fossil.anu.death");
    private final ServerBossEvent bossEvent = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.FOLLOW_RANGE, 40).add(Attributes.MAX_HEALTH, 600).add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    public static TranslatableComponent getRandomGreeting(Random random) {
        return random.nextInt(2) == 0 ? SPAWN_1 : SPAWN_2;
    }

    public Anu(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        setPersistenceRequired();
        xpReward = 50;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AnuAvoidEntityGoal<>(this, Player.class, 5, 0.8, 1.33));
        goalSelector.addGoal(3, new FlyNearTargetGoal(this));
        goalSelector.addGoal(4, new FireballAttackGoal(this, 1, 15));
        goalSelector.addGoal(4, new AnuMeleeAttackGoal(this, 1.2, false));
        goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        //TODO: Music
        super.aiStep();
        if (getAttackMode() == AttackMode.FLIGHT) {
            if (!onGround && getDeltaMovement().y < 0) {
                setDeltaMovement(getDeltaMovement().multiply(1, 0.6, 1));
            }
            if (!level.isClientSide) {
                Player player = level.getNearestPlayer(this, 100);
                if (player != null && tickCount % 100 == 0 && getSensing().hasLineOfSight(player) && !player.isCreative()) {
                    performRangedAttack(player, 1);
                }
            }
            for (int i = 0; i < 2; ++i) {
                level.addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
            }
        }
        if (getAttackMode() == AttackMode.DEFENSE) {
            boolean summonSpikes = random.nextInt(250) == 0;
            boolean summonDefenses = random.nextInt(500) == 0;
            boolean summonPiglin = random.nextInt(250) == 0;
            boolean summonWitherSkeleton = random.nextInt(350) == 0;
            boolean summonBlaze = random.nextInt(300) == 0;
            if (summonSpikes) {
                playSound(SoundEvents.STONE_HIT, 1, 1);
                BlockPos.MutableBlockPos blockPos = blockPosition().mutable();
                BlockState blockState = level.getBlockState(blockPos);
                while ((blockState.isAir() || blockState.is(BlockTags.LEAVES)) && blockPos.getY() > 0) {
                    blockPos.move(Direction.DOWN);
                }
                for (int i = 0; i < 4; ++i) {
                    blockPos.move(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                    if (level.isEmptyBlock(blockPos) && ModBlocks.OBSIDIAN_SPIKES.get().canSurvive(level.getBlockState(blockPos.below()), level, blockPos)) {
                        level.setBlock(blockPos, ModBlocks.OBSIDIAN_SPIKES.get().defaultBlockState(), 2);
                    }
                }
            }
            if (summonDefenses) {
                playSound(SoundEvents.STONE_HIT, 1, 1);
                if (!level.isClientSide) {
                    AnuDefenseHut.generateDefenseHutP1(level, new BlockPos(getX(), getY(), getZ()));
                    AnuDefenseHut.generateDefenseHutP2(level, new BlockPos(getX(), getY(), getZ()));
                    AnuDefenseHut.generateDefenseHutP2(level, new BlockPos(getX(), getY() + 1, getZ()));
                    AnuDefenseHut.generateDefenseHutP2(level, new BlockPos(getX(), getY() + 2, getZ()));
                    AnuDefenseHut.generateDefenseHutP1(level, new BlockPos(getX(), getY() + 4, getZ()));
                }
            }
            Player player = level.getNearestPlayer(this, 50);
            if (summonPiglin) {
                playSound(SoundEvents.STONE_HIT, 1, 1);
                if (player != null && level.isClientSide) {
                    player.displayClientMessage(ANU_COMBAT_BRUTES, false);
                }
                //TODO: Spawn Sentry
            }
            if (summonWitherSkeleton) {
                WitherSkeleton witherSkeleton = EntityType.WITHER_SKELETON.create(level);
                witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                witherSkeleton.moveTo(getX() + random.nextInt(4), getY(), getZ() + random.nextInt(4), getYRot(), getXRot());
                level.addFreshEntity(witherSkeleton);
                if (player != null && level.isClientSide) {
                    player.displayClientMessage(ANU_COMBAT_ARCHERS, false);
                }
            }
            if (summonBlaze) {
                Blaze blaze = EntityType.BLAZE.create(level);
                blaze.moveTo(getX() + random.nextInt(4), getY(), getZ() + random.nextInt(4), getYRot(), getXRot());
                level.addFreshEntity(blaze);
                if (player != null && level.isClientSide) {
                    player.displayClientMessage(ANU_COMBAT_BLAZES, false);
                }
            }
            for (int i = 0; i < 2; ++i) {
                level.addParticle(ParticleTypes.DRIPPING_LAVA, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity trueSource = source.getEntity();
        if (trueSource instanceof Ghast) {
            return false;
        }
        if (level.isClientSide && trueSource instanceof Player player && random.nextInt(10) == 0) {
            ItemStack itemStack = player.getInventory().getSelected();
            if (itemStack.is(ModItems.ANCIENT_SWORD.get())) {
                player.displayClientMessage(ANU_COMBAT_ANCIENT, false);
            } else if (itemStack.getItem() instanceof SwordItem) {
                player.displayClientMessage(ANU_COMBAT_DRAW, false);
            } else if (source.isProjectile()) {
                player.displayClientMessage(ANU_COMBAT_COWARD, false);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource damageSource) {
        //TODO: Dead Anu
        super.die(damageSource);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (random.nextInt(4) == 0) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
            lightningBolt.moveTo(Vec3.atBottomCenterOf(target.blockPosition()));
            level.addFreshEntity(lightningBolt);
        }
        return super.doHurtTarget(target);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        double x = target.getX() - getX();
        double y = target.getBoundingBox().minY + (target.getBbHeight() / 2) - (getY() + (getBbHeight() / 2));
        double z = target.getZ() - getZ();
        playSound(SoundEvents.GHAST_SHOOT, 1, 1);
        LargeFireball largeFireball = new LargeFireball(level, this, x, y, z, 2);
        Vec3 look = getLookAngle();
        largeFireball.setPos(getX() + look.x * 4, getY() + (getBbHeight() / 2) + 0.5, getZ() + look.z * 4);
        level.addFreshEntity(largeFireball);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        populateDefaultEquipmentSlots(difficulty);
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.ANCIENT_SWORD.get()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (this.hasCustomName()) {
            this.bossEvent.setName(getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    public AttackMode getAttackMode() {
        return AttackMode.byFraction(getHealth() / getMaxHealth());
    }

    public enum AttackMode {
        MELEE(1), FLIGHT(0.66f), DEFENSE(0.33f);

        private static final List<AttackMode> BY_DAMAGE;
        private final float fraction;

        AttackMode(float f) {
            this.fraction = f;
        }

        public static AttackMode byFraction(float fraction) {
            for (AttackMode mode : BY_DAMAGE) {
                if (!(fraction < mode.fraction)) continue;
                return mode;
            }
            return MELEE;
        }

        static {
            BY_DAMAGE = Stream.of(AttackMode.values()).sorted(Comparator.comparingDouble(mode -> mode.fraction)).collect(ImmutableList.toImmutableList());
        }
    }
}
