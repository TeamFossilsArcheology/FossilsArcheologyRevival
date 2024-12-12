package com.github.teamfossilsarcheology.fossil.entity.monster;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Collections;

public class Failuresaurus extends Monster implements IAnimatable {
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(Failuresaurus.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Failuresaurus.class, EntityDataSerializers.STRING);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Failuresaurus(EntityType<Failuresaurus> entityType, Level level) {
        super(entityType, level);
        xpReward = 4;
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CLIMBING, (byte) 0);
        entityData.define(VARIANT, Variant.DODO.name());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            setClimbing(horizontalCollision);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        for (int i = 0; i < 4; i++) {
            int x = Mth.floor(position().x + (i % 2 * 2 - 1) * 0.025);
            int y = Mth.floor(position().y);
            int z = Mth.floor(position().z + (i / 2 * 2 - 1) * 0.25);
            BlockPos blockPos = new BlockPos(x, y, z);
            BlockState slime = ModBlocks.SLIME_TRAIL.get().defaultBlockState();
            if (level.getBlockState(blockPos).isAir() && slime.canSurvive(level, blockPos)) {
                level.setBlockAndUpdate(blockPos, slime);
            }
        }
    }

    @Override
    public boolean onClimbable() {
        return isClimbing();
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING) == (byte) 1;
    }

    public void setClimbing(boolean climbing) {
        entityData.set(CLIMBING, (byte) (climbing ? 1 : 0));
    }

    public void setVariant(String variant) {
        entityData.set(VARIANT, variant);
    }

    public String getVariant() {
        return entityData.get(VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        String variant = compound.getString("Variant");
        if (variant.isBlank()) {
            variant = Variant.DODO.name();
        }
        setVariant(variant);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        setVariant(Util.getRandom(Variant.values(), random).name());
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    protected int getCurrentSwingDuration() {
        return 18;
    }

    @Override
    protected void jumpFromGround() {

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 0, event -> {
            if (swinging) {
                if (swingTime == 0) {
                    event.getController().markNeedsReload();
                }
                event.getController().setAnimation(new AnimationBuilder().playOnce("attack"));
            } else if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().loop("walk"));
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public enum Variant {
        DODO, FISH, FLYING, SAUROPOD, THEROPOD
    }
}
