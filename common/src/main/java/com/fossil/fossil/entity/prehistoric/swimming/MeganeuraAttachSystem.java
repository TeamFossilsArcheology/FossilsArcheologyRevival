package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.prehistoric.base.AISystem;
import com.fossil.fossil.entity.prehistoric.base.OrderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

import static com.fossil.fossil.entity.prehistoric.swimming.Meganeura.*;

public class MeganeuraAttachSystem extends AISystem {
    private static final int MAX_TRY_TICKS = 300;//15 sec
    private static final int MAX_ATTACH_TICKS = 1200;//1 min
    private final Meganeura mob;
    private final SynchedEntityData entityData;
    private BlockPos targetBlockPos;
    private Vec3 targetLocation;
    private Direction targetFace;
    private int attachCooldown = 10;
    private int attachTicks = 0;
    private int tryTicks = 0;

    public MeganeuraAttachSystem(Meganeura mob) {
        super(mob);
        this.mob = mob;
        this.entityData = mob.getEntityData();
    }

    @Override
    public void serverTick() {
        if (mob.isBaby()) {
            attachCooldown = 150;
            return;
        }
        if (attachCooldown > 0) {
            attachCooldown--;
        }
        if (!isAttached() && targetLocation != null) {
            double dist = targetLocation.distanceTo(mob.position());
            if (dist < 1) {
                mob.getMoveControl().setWantedPosition(targetLocation.x, targetLocation.y, targetLocation.z, 0.9);
                mob.getNavigation().stop();
                if (!attachStarted()) {
                    approachAttachPos();
                }
                if (dist < 0.2) {
                    startAttaching();
                }
            }
            if (tryTicks > MAX_TRY_TICKS) {
                stopAttaching();
            }
        }
        if (isAttached()) {
            attachTicks++;
            mob.setDeltaMovement(Vec3.ZERO);
        } else {
        }
        if (attachStarted() && targetBlockPos != null) {
            mob.setYRot(0);
            mob.setXRot(0);
            mob.yBodyRot = 0;
            mob.yHeadRot = 0;
            if (!mob.level.getBlockState(targetBlockPos).isFaceSturdy(mob.level, targetBlockPos, getAttachmentFace())) {
                stopAttaching();
            }
            if (isAttached()) {
                if (!mob.isSleeping() && mob.getCurrentOrder() != OrderType.STAY && (attachTicks > MAX_ATTACH_TICKS && mob.getRandom().nextInt(123) == 0 || mob.getTarget() != null)) {
                    stopAttaching(1000 + mob.getRandom().nextInt(1500));
                }

                attachTicks++;
                mob.setDeltaMovement(Vec3.ZERO);
                if (mob.getCurrentOrder() == OrderType.FOLLOW) {
                    stopAttaching();
                }
            }
        }
    }

    @Override
    public void clientTick() {
        if (attachStarted()) {
            mob.setYRot(0);
            mob.setXRot(0);
            mob.yBodyRot = 0;
            mob.yHeadRot = 0;
        }
    }

    /**
     * Sync the target to the client to start approach
     */
    public void approachAttachPos() {
        attachCooldown = 150;
        tryTicks = MAX_TRY_TICKS - 40;
        setAttachmentPos(targetLocation);
        setAttachmentFace(targetFace);
    }

    /**
     * Set target but don't start the approach yet
     *
     * @param attachBlockPos
     * @param attachFace
     */
    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace) {
        //Pick a random point on the hit face, offset by half the mobs width
        float rad = mob.getBbWidth() / 2;
        if (!mob.usesAttachHitBox()) {
            rad *= mob.getAttachHitBoxScale();
        }
        //Offset perpendicular to face
        Vec3 pos = Vec3.atCenterOf(attachBlockPos).add((0.5 + rad) * attachFace.getStepX(), 0, (0.5 + rad) * attachFace.getStepZ());
        Random random = mob.getRandom();
        float randomOffset = (random.nextFloat(2) - 1) * 0.4f;
        //Offset parallel to face
        pos = pos.add(attachFace.getClockWise().getStepX() * randomOffset, (random.nextFloat(2) - 1) * 0.3f, attachFace.getClockWise().getStepZ() * randomOffset);
        setAttachTarget(attachBlockPos, attachFace, pos);
    }

    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace, Vec3 attachLocation) {
        targetBlockPos = attachBlockPos;
        targetFace = attachFace;
        targetLocation = attachLocation;
        tryTicks = 0;
    }

    /**
     * Sync the attached state to the client to finish the approach
     */
    public void startAttaching() {
        attachCooldown = 150;
        attachTicks = 0;
        entityData.set(ATTACHED, true);
        mob.setPos(targetLocation.x, mob.getY(), targetLocation.z);
        mob.setDeltaMovement(Vec3.ZERO);
    }

    public void stopAttaching(int attachCooldown) {
        targetBlockPos = null;
        targetFace = null;
        targetLocation = null;
        this.attachCooldown = attachCooldown;
        entityData.set(ATTACHED, false);
        setAttachmentPos(Vec3.ZERO);
        setAttachmentFace(Direction.UP);
    }

    public void stopAttaching() {
        stopAttaching(150);
    }

    public boolean isAttached() {
        return entityData.get(ATTACHED);
    }

    public void setAttached(boolean attached) {
        entityData.set(ATTACHED, attached);
    }

    public int getAttachCooldown() {
        return attachCooldown;
    }

    public void setAttachCooldown(int attachCooldown) {
        this.attachCooldown = attachCooldown;
    }

    public boolean attachStarted() {
        return getAttachmentFace() != Direction.UP;
    }

    public Vec3 getAttachmentPos() {
        return new Vec3(entityData.get(ATTACHED_X), entityData.get(ATTACHED_Y), entityData.get(ATTACHED_Z));
    }

    public void setAttachmentPos(Vec3 location) {
        entityData.set(ATTACHED_X, (float) location.x);
        entityData.set(ATTACHED_Y, (float) location.y);
        entityData.set(ATTACHED_Z, (float) location.z);
    }

    public Direction getAttachmentFace() {
        return entityData.get(ATTACHED_FACE);
    }

    public void setAttachmentFace(Direction direction) {
        entityData.set(ATTACHED_FACE, direction);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("AttachTicks", attachTicks);
        tag.putInt("AttachCooldown", attachCooldown);
        tag.putInt("AttachFace", getAttachmentFace().get3DDataValue());
        tag.putBoolean("Attached", isAttached());
        Vec3 attachPos = getAttachmentPos();
        tag.putDouble("AttachX", attachPos.x);
        tag.putDouble("AttachY", attachPos.y);
        tag.putDouble("AttachZ", attachPos.z);
        if (targetBlockPos != null) {
            tag.put("AttachPos", NbtUtils.writeBlockPos(targetBlockPos));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        attachTicks = tag.getInt("AttachTicks");
        attachCooldown = tag.getInt("AttachCooldown");
        setAttachmentFace(Direction.from3DDataValue(tag.getInt("AttachFace")));
        setAttached(tag.getBoolean("Attached"));
        setAttachmentPos(new Vec3(tag.getDouble("AttachX"), tag.getDouble("AttachY"), tag.getDouble("AttachZ")));
        if (tag.contains("AttachPos")) {
            targetBlockPos = NbtUtils.readBlockPos(tag.getCompound("AttachPos"));
        }
    }
}
