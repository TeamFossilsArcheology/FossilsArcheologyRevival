package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.entity.Entity;

public abstract class ModelPrehistoric extends AdvancedModelBase {

    public ModelPrehistoric() {
    }

    //Whoever made this doesnt realize these are passed by value
    public boolean blockMovement(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        return entity instanceof EntityPrehistoric && ((EntityPrehistoric) entity).isMovementBlocked();
    }

    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void sitAnimationRotation(AdvancedModelRenderer modelRenderer, float sitProgress, float rotX, float rotY, float rotZ) {
        modelRenderer.rotateAngleX += sitProgress * rotX / 25.0F;
        modelRenderer.rotateAngleY += sitProgress * rotY / 25.0F;
        modelRenderer.rotateAngleZ += sitProgress * rotZ / 25.0F;
    }

    public void sitAnimationRotationPrev(AdvancedModelRenderer modelRenderer, float sitProgress, float rotX, float rotY, float rotZ) {
        modelRenderer.rotateAngleX += sitProgress * (rotX - modelRenderer.defaultRotationX) / 20.0F;
        modelRenderer.rotateAngleY += sitProgress * (rotY - modelRenderer.defaultRotationY) / 20.0F;
        modelRenderer.rotateAngleZ += sitProgress * (rotZ - modelRenderer.defaultRotationZ) / 20.0F;
    }

    public void sitAnimationRotationMinDistance(AdvancedModelRenderer from, float sitProgress, float rotX, float rotY, float rotZ) {
        from.rotateAngleX += ((distance(from.rotateAngleX - from.defaultRotationX, rotX - from.defaultRotationX)) / 20.0F) * sitProgress;
        from.rotateAngleY += ((distance(from.rotateAngleY - from.defaultRotationX, rotY - from.defaultRotationX)) / 20.0F) * sitProgress;
        from.rotateAngleZ += ((distance(from.rotateAngleZ - from.defaultRotationX, rotZ - from.defaultRotationX)) / 20.0F) * sitProgress;
    }

    public void sitAnimationPos(AdvancedModelRenderer modelRenderer, float sitProgress, float x, float y, float z) {
        modelRenderer.rotationPointX += sitProgress * x / 20.0F;
        modelRenderer.rotationPointY += sitProgress * y / 20.0F;
        modelRenderer.rotationPointZ += sitProgress * z / 20.0F;
    }

    private float distance(float rotateAngleFrom, float rotateAngleTo) {
        return (float) Math.atan2(Math.sin(rotateAngleTo - rotateAngleFrom), Math.cos(rotateAngleTo - rotateAngleFrom));
    }
}
