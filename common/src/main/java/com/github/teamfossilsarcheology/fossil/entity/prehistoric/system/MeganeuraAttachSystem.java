package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura.*;

/**
 * This system handles the AI for the meganeura attach process
 * <p>
 * See {@link com.github.teamfossilsarcheology.fossil.client.renderer.entity.MeganeuraRenderer MeganeuraRenderer} for most of the client side logic
 */
public class MeganeuraAttachSystem extends AISystem {
    private static final int MAX_TRY_TICKS = 300;//15 sec
    private static final int MAX_ATTACH_TICKS = 1200;//1 min
    private final Meganeura mob;
    private final SynchedEntityData entityData;
    private BlockPos targetBlockPos;
    private Vec3 targetLocation;
    private Direction targetFace;
    private int attachCooldown = 150;
    private int attachTicks = 0;
    private int tryTicks = 0;

    public MeganeuraAttachSystem(Meganeura mob) {
        super(mob);
        this.mob = mob;
        this.entityData = mob.getEntityData();
    }

    @Override
    public void serverTick() {
        if (tryTicks > 0) {
            tryTicks++;
        }
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
        }
        if (isAttached()) {
            attachTicks++;
            mob.setDeltaMovement(Vec3.ZERO);
        }
        if (attachStarted() && targetBlockPos != null) {
            mob.setYRot(0);
            mob.setXRot(0);
            mob.yBodyRot = 0;
            mob.yHeadRot = 0;
            if (!mob.level().getBlockState(targetBlockPos).isFaceSturdy(mob.level(), targetBlockPos, getAttachmentFace())) {
                stopAttaching();
            }
            if (isAttached()) {
                if (!mob.isSleeping() && mob.getCurrentOrder() != OrderType.STAY && (attachTicks > MAX_ATTACH_TICKS && mob.getRandom().nextInt(123) == 0 || mob.getTarget() != null)) {
                    stopAttaching(1000 + mob.getRandom().nextInt(1500));
                }

                if (mob.getCurrentOrder() == OrderType.FOLLOW) {
                    stopAttaching();
                }
            }
        }
        if (tryTicks > MAX_TRY_TICKS) {
            stopAttaching();
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
     * Starts the approach. Syncs the target to the client
     */
    public void approachAttachPos() {
        attachCooldown = 150;
        tryTicks = MAX_TRY_TICKS - 40;
        setAttachmentPos(targetLocation);
        setAttachmentFace(targetFace);
    }

    /**
     * This will set the target but will not yet start the approach. A random offset will be added to the target
     *
     * @param attachBlockPos the target block position
     * @param attachFace     the {@code Direction} of the target block face
     */
    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace) {
        //Pick a random point on the hit face, offset by half the mobs width
        double rad = mob.getBbWidth() / 2;
        //Offset perpendicular to face
        Vec3 pos = Vec3.atCenterOf(attachBlockPos).add((0.5 + rad) * attachFace.getStepX(), 0, (0.5 + rad) * attachFace.getStepZ());
        RandomSource random = mob.getRandom();
        //Offset parallel to face while staying inside block bounds
        double randomOffset = (random.nextDouble() * 2 - 1) * (0.5 - rad - Mth.EPSILON);
        pos = pos.add(attachFace.getClockWise().getStepX() * randomOffset, (random.nextFloat() * 2 - 1) * 0.3f, attachFace.getClockWise().getStepZ() * randomOffset);
        setAttachTarget(attachBlockPos, attachFace, pos);
    }

    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace, Vec3 attachLocation) {
        targetBlockPos = attachBlockPos;
        targetFace = attachFace;
        targetLocation = attachLocation;
        tryTicks = 0;
    }

    /**
     * Finishes the approach. Syncs the attached state to the client
     */
    public void startAttaching() {
        attachCooldown = 150;
        attachTicks = 0;
        tryTicks = 0;
        setAttached(true);
        mob.setPos(targetLocation.x, mob.getY(), targetLocation.z);
        mob.setDeltaMovement(Vec3.ZERO);
    }

    /**
     * @param attachCooldown the new attachment cooldown
     */
    public void stopAttaching(int attachCooldown) {
        targetBlockPos = null;
        targetFace = null;
        targetLocation = null;
        tryTicks = 0;
        this.attachCooldown = attachCooldown;
        setAttached(false);
        setAttachmentPos(Vec3.ZERO);
        setAttachmentFace(Direction.UP);
    }

    public void stopAttaching() {
        stopAttaching(150);
    }

    /**
     * Returns {@code true} if the mob has finished the attachment process and is currently attached
     */
    public boolean isAttached() {
        return entityData.get(ATTACHED);
    }

    private void setAttached(boolean attached) {
        entityData.set(ATTACHED, attached);
    }

    public int getAttachCooldown() {
        return attachCooldown;
    }

    public void setAttachCooldown(int attachCooldown) {
        this.attachCooldown = attachCooldown;
    }

    /**
     * Returns {@code true} if the mob has started the attachment process or is currently attached
     */
    public boolean attachStarted() {
        return getAttachmentFace() != Direction.UP;
    }

    /**
     * The initial attachment target used for rotation. Actual target might be different: See {@link #startAttaching()}
     */
    public Vec3 getAttachmentPos() {
        return new Vec3(entityData.get(ATTACHED_X), entityData.get(ATTACHED_Y), entityData.get(ATTACHED_Z));
    }

    private void setAttachmentPos(Vec3 location) {
        entityData.set(ATTACHED_X, (float) location.x);
        entityData.set(ATTACHED_Y, (float) location.y);
        entityData.set(ATTACHED_Z, (float) location.z);
    }

    /**
     * Returns the {@code Direction} of the target block face
     */
    public Direction getAttachmentFace() {
        return entityData.get(ATTACHED_FACE);
    }

    private void setAttachmentFace(Direction direction) {
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
