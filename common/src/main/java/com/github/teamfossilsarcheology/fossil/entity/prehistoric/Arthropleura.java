package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class Arthropleura extends Prehistoric {
    public static final EntityDataAccessor<Boolean> IS_BEE = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.BOOLEAN);
    private MoveControl oldMoveControl;
    private LookControl oldLookControl;
    private final TemptGoal temptGoal = new TemptGoal(this, 1.25, Ingredient.of(ItemTags.FLOWERS), false);
    private final BeeWanderGoal wanderGoal = new BeeWanderGoal();

    public Arthropleura(EntityType<Arthropleura> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_BEE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Bee", isBee());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(IS_BEE, compound.getBoolean("Bee"));
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        if (name != null && Objects.equals(ChatFormatting.stripFormatting(name.getString()), "Bee")) {
            entityData.set(IS_BEE, true);
            navigation.stop();
            setDeltaMovement(Vec3.ZERO);
            oldMoveControl = moveControl;
            oldLookControl = lookControl;
            moveControl = new FlyingMoveControl(this, 20, true);
            lookControl = new LookControl(this);
            FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level()) {

                @Override
                public boolean isStableDestination(BlockPos pos) {
                    return !level.getBlockState(pos.below()).isAir();
                }
            };
            flyingPathNavigation.setCanOpenDoors(false);
            flyingPathNavigation.setCanFloat(false);
            flyingPathNavigation.setCanPassDoors(true);
            navigation = flyingPathNavigation;
            goalSelector.addGoal(3, temptGoal);
            goalSelector.addGoal(Util.WANDER - 1, wanderGoal);
        } else if (Objects.equals(ChatFormatting.stripFormatting(getName().getString()), "Bee")) {
            navigation.stop();
            setDeltaMovement(Vec3.ZERO);
            entityData.set(IS_BEE, false);
            moveControl = oldMoveControl;
            lookControl = oldLookControl;
            navigation = createNavigation(level());
            goalSelector.removeGoal(temptGoal);
            goalSelector.removeGoal(wanderGoal);
        }
        super.setCustomName(name);
    }

    /**
     * Bee
     */
    public boolean isBee() {
        return entityData.get(IS_BEE);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (isBee()) {
            if (level.getBlockState(pos).isAir()) {
                return 10.0f;
            }
            return 0.0f;
        }
        return super.getWalkTargetValue(pos, level);
    }

    @Override
    public void refreshTexturePath() {
        if (!level().isClientSide) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/arthropleura/arthropleura");
        if (isBaby()) builder.append("_baby");
        if (!hasTeenTexture() && isTeen() || isAdult()) {
            if (getGender() == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        builder.append(".png");
        textureLocation = FossilMod.location(builder.toString());
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.ARTHROPLEURA;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (isBee()) {
            return false;
        } else {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isBee() ? SoundEvents.BEE_LOOP : ModSounds.ARTHROPLEURA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return isBee() ? SoundEvents.BEE_HURT : ModSounds.ARTHROPLEURA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return isBee() ? SoundEvents.BEE_DEATH : ModSounds.ARTHROPLEURA_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return isBee() ? super.getSoundVolume() * 2 : super.getSoundVolume() * 0.75f;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!isBee()) {
            playSound(ModSounds.ARTHROPLEURA_WALK.get(), 0.15f, 1);
        }
    }

    class BeeWanderGoal extends Goal {

        BeeWanderGoal() {
            setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return Arthropleura.this.navigation.isDone() && Arthropleura.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return Arthropleura.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3 = findPos();
            if (vec3 != null) {
                Arthropleura.this.navigation.moveTo(Arthropleura.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec32 = Arthropleura.this.getViewVector(0);
            int i = 8;
            Vec3 vec33 = HoverRandomPos.getPos(Arthropleura.this, i, 7, vec32.x, vec32.z, Mth.HALF_PI, 3, 1);
            if (vec33 != null) {
                return vec33;
            }
            return AirAndWaterRandomPos.getPos(Arthropleura.this, i, 4, -2, vec32.x, vec32.z, Mth.HALF_PI);
        }
    }
}