package com.fossil.fossil.entity.prehistoric.parts.forge;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.entity.PartEntity;

public class PrehistoricPartImpl<T extends Prehistoric> extends PartEntity<T> {
    private final EntityDimensions size;

    public PrehistoricPartImpl(T parent, float f, float g) {
        super(parent);
        this.size = EntityDimensions.scalable(f, g);
        this.refreshDimensions();
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g) {
        return new PrehistoricPartImpl<>(entity, f, g);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
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
    public boolean is(Entity entity) {
        return this == entity || getParent() == entity;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return size;
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
