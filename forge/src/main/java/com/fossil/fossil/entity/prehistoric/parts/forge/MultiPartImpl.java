package com.fossil.fossil.entity.prehistoric.parts.forge;

import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.AnimationOverride;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

public class MultiPartImpl<T extends Prehistoric> extends PartEntity<T> implements MultiPart {
    private final EntityDimensions size;
    private final Vec3 offset;
    private final String name;
    private AnimationOverride animationOverride;

    public MultiPartImpl(T parent, EntityHitboxManager.Hitbox hitbox) {
        super(parent);
        this.size = EntityDimensions.scalable(hitbox.width(), hitbox.height());
        this.offset = hitbox.pos();
        this.name = hitbox.name();
    }

    public static <T extends Prehistoric> MultiPart get(T entity, EntityHitboxManager.Hitbox hitbox) {
        return new MultiPartImpl<>(entity, hitbox);
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public Vec3 getOffset() {
        return offset;
    }

    @Override
    public void setOverride(AnimationOverride newOverride) {
        if (animationOverride != null && (animationOverride.scaleH() != newOverride.scaleH() || animationOverride.scaleW() != newOverride.scaleW())) {
            animationOverride = newOverride;
            refreshDimensions();
        } else {
            animationOverride = newOverride;
        }
    }

    @Override
    public AnimationOverride getOverride() {
        return animationOverride;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        return getParent().interact(player, hand);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return false;
        }
        return getParent().hurt(this, source, amount);
    }

    @Override
    public void remove(RemovalReason reason) {
        setRemoved(reason);
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || getParent() == entity;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (animationOverride != null) {
            return size.scale(getParent().getScale()).scale(animationOverride.scaleW(), animationOverride.scaleH());
        }
        return size.scale(getParent().getScale());
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void tick() {
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }
}
