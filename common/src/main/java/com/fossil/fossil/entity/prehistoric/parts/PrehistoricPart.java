package com.fossil.fossil.entity.prehistoric.parts;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PrehistoricPart extends Entity implements EntitySpawnExtension {

    private Prehistoric parent;
    private EntityDimensions size;
    private Vec3 offset;
    private String name;

    //TODO: Teleport dimension, riding stuff
    //TODO: Maybe mixin trackingstart und füge die parts so hin zu.
    public PrehistoricPart(EntityType<PrehistoricPart> entityType, Level level) {
        super(entityType, level);
        this.size = entityType.getDimensions();
    }

    public PrehistoricPart(Prehistoric parent, EntityHitboxManager.Hitbox hitbox) {
        super(ModEntities.MULTIPART.get(), parent.level);
        this.parent = parent;
        this.size = EntityDimensions.scalable(hitbox.width(), hitbox.height());
        this.offset = hitbox.pos();
        name = hitbox.name();
    }

    public PrehistoricPart(Prehistoric parent, float f, float g) {
        super(ModEntities.MULTIPART.get(), parent.level);
        this.parent = parent;
        this.size = EntityDimensions.scalable(f, g);
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g, int idx) {
        return new PrehistoricPart(entity, f, g);
    }

    public static <T extends Prehistoric> Entity get(T entity, float f, float g) {
        return new PrehistoricPart(entity, f, g);
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
    public boolean is(@NotNull Entity entity) {
        return this == entity || parent == entity;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (parent != null) {
            return size.scale(parent.getScale());
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
        super.tick();
        if (parent != null) {
            updatePosition();
        }
        //TODO: Persistence. Vlt flag basierend auf größe das entscheidet ob entity zu level hinzugefügt werden soll
    }

    public void updatePosition() {
        setPos(parent.position().add(new Vec3(offset.x, offset.y, offset.z).yRot(-parent.yBodyRot * Mth.DEG_TO_RAD).scale(parent.getScale())));
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
        buf.writeInt(parent.getId());
        buf.writeFloat(size.width);
        buf.writeFloat(size.height);
        buf.writeDouble(offset.x);
        buf.writeDouble(offset.y);
        buf.writeDouble(offset.z);
        buf.writeUtf(name);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        int id = buf.readInt();
        if (level.getEntity(id) instanceof Prehistoric prehistoric) {
            //TODO: Add failsafe?
            parent = prehistoric;
            prehistoric.addPart(this);
        }
        size = EntityDimensions.scalable(buf.readFloat(), buf.readFloat());
        offset = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        name = buf.readUtf();
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    public Prehistoric getParent() {
        return parent;
    }
}
