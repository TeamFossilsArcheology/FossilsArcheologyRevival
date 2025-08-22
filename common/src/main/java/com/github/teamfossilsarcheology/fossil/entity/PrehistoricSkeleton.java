package com.github.teamfossilsarcheology.fossil.entity;

import com.github.darkpred.morehitboxes.api.HitboxData;
import com.github.darkpred.morehitboxes.internal.HitboxDataLoader;
import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.util.TimePeriod;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;

public class PrehistoricSkeleton extends Entity implements GeoEntity, EntitySpawnExtension {
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(PrehistoricSkeleton.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> INFO_TYPE = SynchedEntityData.defineId(PrehistoricSkeleton.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean droppedBiofossil;
    private float frustumWidthRadius;
    private float frustumHeight;
    private AABB cullingBounds = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public ResourceLocation textureLocation;
    public ResourceLocation modelLocation;

    public PrehistoricSkeleton(EntityType<?> entityType, Level level) {
        super(entityType, level);
        refreshTexturePath();//Won't be correct but should prevent crash in rare cases
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(AGE, 0);
        entityData.define(INFO_TYPE, TRICERATOPS.name());
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeInt(getAge());
        buf.writeUtf(getInfoType());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        setAge(buf.readInt());
        setInfoType(buf.readUtf());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        setAge(compound.getInt("Age"));
        setInfoType(compound.getString("Type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Age", getAge());
        compound.putString("Type", getInfoType());
    }

    @Override
    public float getYHeadRot() {
        return getYRot();
    }

    @Override
    public void setYHeadRot(float yHeadRot) {
        setYRot(yHeadRot);
    }

    @Override
    protected @NotNull AABB makeBoundingBox() {
        return info().entityType().getDimensions().scale(getScale()).makeBoundingBox(position());
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        return info().entityType().getDimensions().scale(getScale());
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            if (player.isShiftKeyDown()) {
                teleportTo(getX() + (player.getX() - getX()) * 0.01, getY(), getZ() + (player.getZ() - getZ()) * 0.01);
            } else {
                double d0 = player.getX() - this.getX();
                double d2 = player.getZ() - this.getZ();
                setYRot(Util.yawToYRot(Mth.atan2(d2, d0) * Mth.RAD_TO_DEG));
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        } else if (stack.is(Items.BONE) && !isAdult()) {
            if (!level().isClientSide) {
                playSound(SoundEvents.SKELETON_AMBIENT, 0.8f, 1);
                setAge(getAge() + 1);
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.interact(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0) {
            level().playSound(null, blockPosition(), SoundEvents.SKELETON_HURT, SoundSource.NEUTRAL, 1, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.5f);
            if (!level().isClientSide) {
                if (!droppedBiofossil) {
                    if (info().timePeriod == TimePeriod.CENOZOIC) {
                        spawnAtLocation(ModItems.TAR_FOSSIL.get(), 1);
                    } else if (info().timePeriod == TimePeriod.PALEOZOIC) {
                        spawnAtLocation(ModItems.SHALE_FOSSIL.get(), 1);
                    } else {
                        spawnAtLocation(ModItems.BIO_FOSSIL.get(), 1);
                    }
                    spawnAtLocation(new ItemStack(Items.BONE, Math.min(getAge(), data().adultAgeDays())), 1);
                    droppedBiofossil = true;
                }
                discard();
            }
            return true;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (AGE.equals(key) || INFO_TYPE.equals(key)) {
            refreshDimensions();
            refreshTexturePath();
        }
        if (INFO_TYPE.equals(key) && level().isClientSide) {
            List<HitboxData> hitboxesData = HitboxDataLoader.HITBOX_DATA.getHitboxes(FossilMod.location(info().resourceName));
            if (hitboxesData != null) {
                float maxFrustumWidthRadius = 0;
                float maxFrustumHeight = 0;
                for (HitboxData hitboxData : hitboxesData) {
                    float w = hitboxData.getFrustumWidthRadius();
                    if (w > maxFrustumWidthRadius) {
                        maxFrustumWidthRadius = w;
                    }
                    float h = hitboxData.getFrustumHeight();
                    if (h > maxFrustumHeight) {
                        maxFrustumHeight = h;
                    }
                }
                frustumWidthRadius = maxFrustumWidthRadius;
                frustumHeight = maxFrustumHeight;
            } else {
                frustumWidthRadius = getBbWidth();
                frustumHeight = getBbHeight();
            }
            makeBoundingBoxForCulling(frustumWidthRadius, frustumHeight);
        }
    }

    public void refreshTexturePath() {
        if (!level().isClientSide) {
            return;
        }
        String name = info().resourceName;
        textureLocation = FossilMod.location("textures/entity/" + name + "/" + name + "_skeleton.png");
        if (info() == DICRANURUS || info() == LONCHODOMAS || info() == SCOTOHARPES || info() == WALLISEROPS) {
            modelLocation = FossilMod.location("geo/entity/trilobite.geo.json");
        } else {
            modelLocation = FossilMod.location("geo/entity/" + name + ".geo.json");
        }
    }

    private void makeBoundingBoxForCulling(float frustumWidthRadius, float frustumHeight) {
        float x = frustumWidthRadius * getScale();
        float y = frustumHeight * getScale();
        Vec3 pos = position();
        cullingBounds = new AABB(pos.x - x, pos.y, pos.z - x, pos.x + x, pos.y + y, pos.z + x);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return cullingBounds;
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        makeBoundingBoxForCulling(frustumWidthRadius, frustumHeight);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public float getScale() {
        if (isAdult()) {
            return data().maxScale();
        }
        float step = (data().maxScale() - data().minScale()) / data().adultAgeDays();
        return data().minScale() + step * getAge();
    }

    public boolean isAdult() {
        return getAge() >= data().adultAgeDays();
    }

    public int getAge() {
        return entityData.get(AGE);
    }

    public void setAge(int age) {
        entityData.set(AGE, age);
    }

    public void setInfoType(String type) {
        entityData.set(INFO_TYPE, type);
    }

    public String getInfoType() {
        return entityData.get(INFO_TYPE);
    }

    public void setInfoType(PrehistoricEntityInfo info) {
        setInfoType(info.name());
    }

    public PrehistoricEntityInfo info() {
        return valueOf(getInfoType());
    }


    public EntityDataLoader.Data data() {
        return EntityDataLoader.INSTANCE.getData(info().resourceName);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
