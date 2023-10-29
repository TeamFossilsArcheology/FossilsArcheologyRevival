package com.fossil.fossil.entity.prehistoric.parts.fabric;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class PrehistoricPartImpl<T extends Prehistoric> extends Entity {
    public final T parent;
    private final EntityDimensions size;
    private final boolean body;

    public PrehistoricPartImpl(T parent, float f, float g, boolean body) {
        super(parent.getType(), parent.level);
        this.parent = parent;
        this.size = EntityDimensions.scalable(f, g);
        this.body = body;
        this.refreshDimensions();
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g) {
        return new PrehistoricPartImpl<>(entity, f, g, false);
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g, boolean body) {
        return new PrehistoricPartImpl<>(entity, f, g, body);
    }

    public static boolean isMultiPart(Object object) {
        return object instanceof PrehistoricPartImpl<?>;
    }

    public static Prehistoric getParent(Object object) {
        return ((PrehistoricPartImpl<?>) object).parent;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        return parent.interact(player, hand);
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
        return parent.hurt(this, source, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || parent == entity;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (body) {
            return parent.getDimensions(pose);
        }
        return size.scale(parent.getScale());
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void tick() {
        xOld = getX();
        yOld = getY();
        zOld = getZ();
        yRotO = getYRot();
        xRotO = getXRot();
        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compound) {

    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }
}
