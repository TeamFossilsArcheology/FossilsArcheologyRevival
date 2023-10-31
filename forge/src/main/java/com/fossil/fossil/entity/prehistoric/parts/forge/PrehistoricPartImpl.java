package com.fossil.fossil.entity.prehistoric.parts.forge;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

public class PrehistoricPartImpl<T extends Prehistoric> extends PartEntity<T> {
    private final EntityDimensions size;
    private final boolean body;

    public PrehistoricPartImpl(T parent, float f, float g, boolean body) {
        super(parent);
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
        return ((PrehistoricPartImpl<?>) object).getParent();
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
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || getParent() == entity;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (body) {
            return getParent().getDimensions(pose);
        }
        return size.scale(getParent().getScale() * getParent().getGenderedScale());
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
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }
}
