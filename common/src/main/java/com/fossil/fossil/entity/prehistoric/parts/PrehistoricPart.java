package com.fossil.fossil.entity.prehistoric.parts;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PrehistoricPart extends Entity implements EntitySpawnExtension {

    private Prehistoric parent;
    private EntityDimensions size;
    private boolean body;
    private int idx;
    //TODO: Load via data. Init offset and then calculate based on rotation
    //TODO: Teleport dimension, riding stuff
    public PrehistoricPart(EntityType<PrehistoricPart> entityType, Level level) {
        super(entityType, level);
        this.size = entityType.getDimensions();
    }

    public PrehistoricPart(Prehistoric parent, float f, float g, boolean body, int idx) {
        super(ModEntities.MULTIPART.get(), parent.level);
        this.parent = parent;
        this.size = EntityDimensions.scalable(f, g);
        this.body = body;
        this.idx = idx;
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g, int idx) {
        return new PrehistoricPart(entity, f, g, false, idx);
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g) {
        return new PrehistoricPart(entity, f, g, false, 0);
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
        if (parent != null) {
            if (body) {
                return parent.getDimensions(pose);
            }
            return size.scale(parent.getScale() * parent.getGenderedScale());
        }
        return super.getDimensions(pose);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void baseTick() {

    }

    @Override
    public void tick() {
        if (tickCount > 10 && (parent == null || parent.isRemoved())) {
            remove(RemovalReason.DISCARDED);
        }
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
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeInt(idx);
        buf.writeInt(parent.getId());
        buf.writeFloat(size.width);
        buf.writeFloat(size.height);
        buf.writeBoolean(body);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        idx = buf.readInt();
        if (level.getEntity(buf.readInt()) instanceof Prehistoric prehistoric) {
            parent = prehistoric;
            prehistoric.addPart(this, idx);
        }
        size = EntityDimensions.scalable(buf.readFloat(), buf.readFloat());
        body = buf.readBoolean();
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    public Prehistoric getParent() {
        return parent;
    }
}
