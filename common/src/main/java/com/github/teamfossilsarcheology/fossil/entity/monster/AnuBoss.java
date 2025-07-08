package com.github.teamfossilsarcheology.fossil.entity.monster;

import com.github.teamfossilsarcheology.fossil.block.entity.AnuBarrierBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.AnuDead;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.ai.anu.AnuMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.anu.AnuPhase;
import com.github.teamfossilsarcheology.fossil.entity.ai.anu.AnuPhaseSystem;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.sounds.MusicHandler;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnuBoss extends PathfinderMob implements RangedAttackMob {
    public static final int ARENA_RADIUS = 25;
    public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(AnuBoss.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> WEAK = SynchedEntityData.defineId(AnuBoss.class, EntityDataSerializers.BOOLEAN);
    private static final Component SPAWN_1 = Component.translatable("entity.fossil.anu.hello");
    private static final Component SPAWN_2 = Component.translatable("entity.fossil.anu.fewBeaten");
    private static final Component ANU_COMBAT_SWORD = Component.translatable("entity.fossil.anu.draw");
    private static final Component ANU_COMBAT_BOW = Component.translatable("entity.fossil.anu.coward");
    private static final Component ANU_COMBAT_FIST = Component.translatable("entity.fossil.anu.fist");
    private static final Component ANU_COMBAT_ANCIENT = Component.translatable("entity.fossil.anu.ancient");
    private static final Component ANU_DEATH = Component.translatable("entity.fossil.anu.death");
    private static final int SONG_LENGTH = 4041;
    private final ServerBossEvent bossEvent = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);
    private Vec3 spawnPosition = Vec3.ZERO;
    public final AnuPhaseSystem phaseSystem = new AnuPhaseSystem(this);
    private int songTick;

    public AnuBoss(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        moveControl = new AnuMoveControl(this);
        xpReward = 50;
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 4);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0);
        setPathfindingMalus(BlockPathTypes.LAVA, 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.FOLLOW_RANGE, 40).add(Attributes.MAX_HEALTH, 600).add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.ATTACK_DAMAGE);
    }

    public static Component getRandomGreeting(RandomSource random) {
        return random.nextInt(2) == 0 ? SPAWN_1 : SPAWN_2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_PHASE, AnuPhase.MELEE.ordinal());
        entityData.define(WEAK, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_PHASE.equals(key) && level().isClientSide) {
            phaseSystem.setPhase(AnuPhase.values()[(entityData.get(DATA_PHASE))]);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    public Vec3 getSpawnPos() {
        return spawnPosition;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (level().isClientSide) {
            if (songTick < SONG_LENGTH) {
                songTick++;
            }
            if (songTick == SONG_LENGTH - 1) {
                songTick = 0;
            }
            if (songTick == 1) {
                MusicHandler.startMusic(ModSounds.MUSIC_ANU.get());
            }
            if (!isAlive()) {
                MusicHandler.stopMusic(ModSounds.MUSIC_ANU.get());
            }
            if (lastHurtByPlayer != null && !lastHurtByPlayer.isAlive()) {
                MusicHandler.stopMusic(ModSounds.MUSIC_ANU.get());
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            phaseSystem.getCurrentPhase().doClientTick();
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        phaseSystem.getCurrentPhase().doServerTick();
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }

    public boolean isWeak() {
        return entityData.get(WEAK);
    }

    public void setWeak(boolean weak) {
        entityData.set(WEAK, weak);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity trueSource = source.getEntity();
        if (source == damageSources().inWall() || source.is(DamageTypeTags.IS_EXPLOSION) && trueSource == null) {
            return false;
        }
        phaseSystem.getCurrentPhase().onHurt(source, amount);
        if (source.getDirectEntity() instanceof LargeFireball && source.getEntity() instanceof Player) {
            super.hurt(source, 20.0f);
            return true;
        }
        if (source == damageSources().fellOutOfWorld() && getY() < level().getMinBuildHeight()) {
            moveTo(spawnPosition);
            return false;
        }
        if (level().isClientSide && trueSource instanceof Player player && random.nextInt(10) == 0) {
            ItemStack itemStack = player.getInventory().getSelected();
            if (itemStack.is(ModItems.ANCIENT_SWORD.get())) {
                player.displayClientMessage(ANU_COMBAT_ANCIENT, false);
            } else if (itemStack.getItem() instanceof SwordItem) {
                player.displayClientMessage(ANU_COMBAT_SWORD, false);
            } else if (source.is(DamageTypeTags.IS_PROJECTILE)) {
                player.displayClientMessage(ANU_COMBAT_BOW, false);
            } else if (itemStack.isEmpty()) {
                player.displayClientMessage(ANU_COMBAT_FIST, false);
            }
        }
        return super.hurt(source, isWeak() ? amount * 1.25f : amount);
    }

    private void removeBarriers() {
        if (!level().isClientSide && level().dimension() == ModDimensions.ANU_LAIR) {
            AnuLair anuLair = ((ServerLevel) level()).getDataStorage().get(AnuLair::load, "anu_lair");
            if (anuLair != null) {
                for (BlockPos barrierPosition : anuLair.barrierPositions) {
                    if (level().getBlockEntity(barrierPosition) instanceof AnuBarrierBlockEntity blockEntity) {
                        blockEntity.disable();
                    }
                }
            }
            ((ServerLevel) level()).getDataStorage().set("anu_lair", AnuLair.killed());
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        removeBarriers();
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!level().isClientSide) {
            AnuDead anuDead = ModEntities.ANU_DEAD.get().create(level());
            anuDead.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
            level().addFreshEntity(anuDead);
            removeBarriers();
        } else {
            MusicHandler.stopMusic(ModSounds.MUSIC_ANU.get());
            List<Player> players = level().getNearbyPlayers(TargetingConditions.forCombat(), this, getBoundingBox().inflate(30, 15, 30));
            for (Player player : players) {
                player.displayClientMessage(ANU_DEATH, false);
            }
        }
        super.die(damageSource);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int looting, boolean hitByPlayer) {
        super.dropCustomDeathLoot(damageSource, looting, hitByPlayer);
        ItemEntity itemEntity = spawnAtLocation(ModItems.ANCIENT_KEY.get());
        if (itemEntity != null) {
            itemEntity.setExtendedLifetime();
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (random.nextInt(4) == 0) {
            LightningBolt lightningBolt = ModEntities.ANCIENT_LIGHTNING_BOLT.get().create(level());
            lightningBolt.moveTo(target.position());
            level().addFreshEntity(lightningBolt);
        }
        return super.doHurtTarget(target);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        double x = target.getX() - getX();
        double y = target.getBoundingBox().minY - (getY() + (getBbHeight() / 2));
        double z = target.getZ() - getZ();
        playSound(SoundEvents.GHAST_SHOOT, 1, 1);
        LargeFireball largeFireball = new LargeFireball(level(), this, x, y, z, 2);
        largeFireball.setPos(getX() + x * 0.1, getY() + (getBbHeight() / 2) + 0.5, getZ() + z * 0.1);
        level().addFreshEntity(largeFireball);
    }

    @Override
    public void checkDespawn() {
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return phaseSystem.getCurrentPhase().getAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ITEM_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        spawnPosition = position();
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.ANCIENT_SWORD.get()));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putDouble("SpawnPosX", spawnPosition.x);
        compound.putDouble("SpawnPosY", spawnPosition.y);
        compound.putDouble("SpawnPosZ", spawnPosition.z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        spawnPosition = new Vec3(compound.getDouble("SpawnPosX"), compound.getDouble("SpawnPosY"), compound.getDouble("SpawnPosZ"));
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

    public static class AnuLair extends SavedData {
        private boolean anuKilled;
        private final Set<BlockPos> barrierPositions = new HashSet<>();

        public static AnuLair killed() {
            AnuLair anuLair = new AnuLair();
            anuLair.anuKilled = true;
            anuLair.setDirty();
            return anuLair;
        }

        public static AnuLair spawned(Set<BlockPos> barriers) {
            AnuLair anuLair = new AnuLair();
            anuLair.barrierPositions.addAll(barriers);
            anuLair.setDirty();
            return anuLair;
        }

        public static AnuLair load(CompoundTag compoundTag) {
            AnuLair anuLair = new AnuLair();
            anuLair.anuKilled = compoundTag.getBoolean("AnuKilled");
            ListTag barriers = compoundTag.getList("Barriers", Tag.TAG_COMPOUND);
            anuLair.barrierPositions.clear();
            for (int i = 0; i < barriers.size(); i++) {
                CompoundTag tag = barriers.getCompound(i);
                anuLair.barrierPositions.add(new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z")));
            }
            return anuLair;
        }

        @Override
        public @NotNull CompoundTag save(CompoundTag compoundTag) {
            compoundTag.putBoolean("AnuKilled", anuKilled);
            ListTag barriers = new ListTag();
            for (BlockPos blockPos : barrierPositions) {
                CompoundTag tag = new CompoundTag();
                tag.putInt("X", blockPos.getX());
                tag.putInt("Y", blockPos.getY());
                tag.putInt("Z", blockPos.getZ());
                barriers.add(tag);
            }
            compoundTag.put("Barriers", barriers);
            return compoundTag;
        }

        public boolean isAnuKilled() {
            return anuKilled;
        }
    }

}
