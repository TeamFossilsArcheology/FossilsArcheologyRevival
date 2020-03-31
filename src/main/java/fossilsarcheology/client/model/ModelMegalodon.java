package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityMegalodon;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMegalodon extends ModelPrehistoric {

    private final ModelAnimator animator;
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer upperBody;
    public AdvancedModelRenderer tail1;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer dorsalFin;
    public AdvancedModelRenderer leftBodyFin;
    public AdvancedModelRenderer rightBodyFin;
    public AdvancedModelRenderer tail2;
    public AdvancedModelRenderer leftPelvicFin;
    public AdvancedModelRenderer rightPelvicFin;
    public AdvancedModelRenderer tail3;
    public AdvancedModelRenderer dorsalFin2;
    public AdvancedModelRenderer analFin;
    public AdvancedModelRenderer tailFin1;
    public AdvancedModelRenderer tailFin2;
    public AdvancedModelRenderer lowerJaw;
    public AdvancedModelRenderer nose;

    public ModelMegalodon() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.tail3 = new AdvancedModelRenderer(this, 43, 54);
        this.tail3.mirror = true;
        this.tail3.setRotationPoint(0.0F, 1.0F, 6.0F);
        this.tail3.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 5, 0.0F);
        this.setRotateAngle(tail3, 0.04241150082346221F, 0.0F, 0.0F);
        this.tail2 = new AdvancedModelRenderer(this, 42, 42);
        this.tail2.mirror = true;
        this.tail2.setRotationPoint(0.0F, 1.0F, 7.6F);
        this.tail2.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 7, 0.0F);
        this.setRotateAngle(tail2, 0.08482300164692443F, 0.0F, 0.0F);
        this.head = new AdvancedModelRenderer(this, 0, 0);
        this.head.mirror = true;
        this.head.setRotationPoint(0.0F, 1.0F, -8.0F);
        this.head.addBox(-3.0F, 0.0F, -7.0F, 6, 5, 7, 0.0F);
        this.setRotateAngle(head, 0.08482300164692443F, 0.0F, 0.0F);
        this.nose = new AdvancedModelRenderer(this, 1, 13);
        this.nose.mirror = true;
        this.nose.setRotationPoint(0.0F, 0.25F, -5.3F);
        this.nose.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 3, 0.0F);
        this.setRotateAngle(nose, 0.017453292519943295F, 0.0F, 0.0F);
        this.upperBody = new AdvancedModelRenderer(this, 0, 20);
        this.upperBody.mirror = true;
        this.upperBody.setRotationPoint(0.0F, 0.0F, 1.6F);
        this.upperBody.addBox(-3.5F, 0.0F, -9.0F, 7, 8, 9, 0.0F);
        this.setRotateAngle(upperBody, 0.08482300164692443F, 0.0F, 0.0F);
        this.body = new AdvancedModelRenderer(this, 0, 37);
        this.body.mirror = true;
        this.body.setRotationPoint(0.0F, 14.0F, -6.0F);
        this.body.addBox(-4.0F, 0.0F, 0.0F, 8, 9, 13, 0.0F);
        this.rightPelvicFin = new AdvancedModelRenderer(this, 54, 0);
        this.rightPelvicFin.mirror = true;
        this.rightPelvicFin.setRotationPoint(0.0F, 5.5F, 2.0F);
        this.rightPelvicFin.addBox(-5.0F, -0.5F, -2.0F, 5, 1, 4, 0.0F);
        this.setRotateAngle(rightPelvicFin, 0.0F, 0.6792821448761931F, -0.6792821448761931F);
        this.dorsalFin = new AdvancedModelRenderer(this, 64, 13);
        this.dorsalFin.mirror = true;
        this.dorsalFin.setRotationPoint(0.0F, 2.5F, 6.0F);
        this.dorsalFin.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 8, 0.0F);
        this.setRotateAngle(dorsalFin, 0.8066911802717791F, 0.0F, 0.0F);
        this.analFin = new AdvancedModelRenderer(this, 0, 37);
        this.analFin.mirror = true;
        this.analFin.setRotationPoint(0.0F, 4.0F, 3.0F);
        this.analFin.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(analFin, 0.8915141819187036F, 0.0F, 0.0F);
        this.tailFin1 = new AdvancedModelRenderer(this, 25, 1);
        this.tailFin1.setRotationPoint(0.0F, 1.3F, 3.0F);
        this.tailFin1.addBox(-1.0F, -2.0F, 0.0F, 2, 4, 11, 0.0F);
        this.setRotateAngle(tailFin1, 0.8915141819187036F, 0.0F, 0.0F);
        this.tail1 = new AdvancedModelRenderer(this, 32, 25);
        this.tail1.mirror = true;
        this.tail1.setRotationPoint(0.0F, 1.0F, 12.0F);
        this.tail1.addBox(-3.0F, 0.0F, 0.0F, 6, 7, 9, 0.0F);
        this.setRotateAngle(tail1, -0.12740903539558604F, 0.0F, 0.0F);
        this.lowerJaw = new AdvancedModelRenderer(this, 23, 17);
        this.lowerJaw.setRotationPoint(0.0F, 5.0F, -0.5F);
        this.lowerJaw.addBox(-2.5F, 0.0F, -6.0F, 5, 2, 6, 0.0F);
        this.setRotateAngle(lowerJaw, -0.16982053621904827F, 0.0F, 0.0F);
        this.leftBodyFin = new AdvancedModelRenderer(this, 90, 6);
        this.leftBodyFin.mirror = true;
        this.leftBodyFin.setRotationPoint(2.5F, 6.0F, -0.5F);
        this.leftBodyFin.addBox(0.0F, 0.0F, -2.5F, 8, 1, 5, 0.0F);
        this.setRotateAngle(leftBodyFin, 0.0F, -0.12740903539558604F, 0.5094616086571448F);
        this.rightBodyFin = new AdvancedModelRenderer(this, 63, 6);
        this.rightBodyFin.mirror = true;
        this.rightBodyFin.setRotationPoint(-2.5F, 6.0F, -0.5F);
        this.rightBodyFin.addBox(-8.0F, 0.0F, -2.5F, 8, 1, 5, 0.0F);
        this.setRotateAngle(rightBodyFin, 0.0F, 0.12740903539558604F, -0.5094616086571448F);
        this.leftPelvicFin = new AdvancedModelRenderer(this, 54, 0);
        this.leftPelvicFin.mirror = true;
        this.leftPelvicFin.setRotationPoint(0.0F, 5.5F, 2.0F);
        this.leftPelvicFin.addBox(0.0F, -0.5F, -2.0F, 5, 1, 4, 0.0F);
        this.setRotateAngle(leftPelvicFin, 0.0F, -0.6792821448761931F, 0.6792821448761931F);
        this.tailFin2 = new AdvancedModelRenderer(this, 41, 0);
        this.tailFin2.mirror = true;
        this.tailFin2.setRotationPoint(0.0F, 1.5F, 2.5F);
        this.tailFin2.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 9, 0.0F);
        this.setRotateAngle(tailFin2, -1.103746218961214F, 0.0F, 0.0F);
        this.dorsalFin2 = new AdvancedModelRenderer(this, 0, 43);
        this.dorsalFin2.setRotationPoint(0.0F, 1.1F, 2.0F);
        this.dorsalFin2.addBox(-0.5F, -3.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(dorsalFin2, -0.8915141819187036F, 0.0F, 0.0F);
        this.tail2.addChild(this.tail3);
        this.tail1.addChild(this.tail2);
        this.upperBody.addChild(this.head);
        this.head.addChild(this.nose);
        this.body.addChild(this.upperBody);
        this.tail1.addChild(this.rightPelvicFin);
        this.body.addChild(this.dorsalFin);
        this.tail2.addChild(this.analFin);
        this.tail3.addChild(this.tailFin1);
        this.body.addChild(this.tail1);
        this.head.addChild(this.lowerJaw);
        this.upperBody.addChild(this.leftBodyFin);
        this.upperBody.addChild(this.rightBodyFin);
        this.tail1.addChild(this.leftPelvicFin);
        this.tail3.addChild(this.tailFin2);
        this.tail2.addChild(this.dorsalFin2);
        animator = ModelAnimator.create();
        this.updateDefaultPose();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.body.render(f5);
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityPrehistoric prehistoric = (EntityPrehistoric) entity;
        animator.update(entity);
        blockMovement(f, f1, f2, f3, f4, f5, (Entity) entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, lowerJaw, 25, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(EntityMegalodon.SHAKE_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, upperBody, 0, -23F, 0);
        ModelUtils.rotate(animator, head, -10, -23F, 0);
        ModelUtils.rotate(animator, lowerJaw, 35F, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, upperBody, 0, 23F, 0);
        ModelUtils.rotate(animator, head, -10, 23F, 0);
        ModelUtils.rotate(animator, lowerJaw, 35F, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, upperBody, 0, -23F, 0);
        ModelUtils.rotate(animator, head, -10, -23F, 0);
        ModelUtils.rotate(animator, lowerJaw, 35F, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, upperBody, 0, 23F, 0);
        ModelUtils.rotate(animator, head, -10, 23F, 0);
        ModelUtils.rotate(animator, lowerJaw, 35F, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, head, 0, -10F, 0);
        ModelUtils.rotate(animator, lowerJaw, 35F, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        AdvancedModelRenderer[] tailParts = {this.tail1, this.tail2, this.tail3, this.tailFin1};
        AdvancedModelRenderer[] neckParts = {this.upperBody, this.head};
        if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
            return;
        }
        {
            float sitProgress = ((EntityPrehistoric) (entity)).weakProgress;
            sitAnimationPos(body, sitProgress, 0, 6, 0);
            sitAnimationRotationPrev(body, sitProgress, 0, 0, (float) Math.toRadians(107));
            sitAnimationRotationPrev(upperBody, sitProgress, (float) Math.toRadians(4), (float) Math.toRadians(-13), 0);
            sitAnimationRotationPrev(head, sitProgress, (float) Math.toRadians(4), 0, 0);
            sitAnimationRotationPrev(lowerJaw, sitProgress, (float) Math.toRadians(40), 0, 0);
            sitAnimationRotationPrev(dorsalFin, sitProgress, (float) Math.toRadians(45), (float) Math.toRadians(7), -((float) Math.toRadians(15)));
            sitAnimationRotationPrev(leftBodyFin, sitProgress, (float) Math.toRadians(-13), (float) Math.toRadians(23), ((float) Math.toRadians(70)));
            sitAnimationRotationPrev(rightBodyFin, sitProgress, 0, (float) Math.toRadians(7), ((float) Math.toRadians(-30)));
            sitAnimationRotationPrev(leftPelvicFin, sitProgress, 0, (float) Math.toRadians(-23), ((float) Math.toRadians(65)));
            sitAnimationRotationPrev(tail1, sitProgress, (float) Math.toRadians(-23), 0, 0);
            sitAnimationRotationPrev(tail2, sitProgress, (float) Math.toRadians(-26), (float) Math.toRadians(26), 0);
            sitAnimationRotationPrev(tail3, sitProgress, (float) Math.toRadians(-20), 0, 0);
        }
        float speed = 0.1F;
        float speed2 = 0.4F;
        this.chainSwing(tailParts, speed2, 0.3F, -3, f, f1);
        this.swing(upperBody, speed2, 0.1F, true, 0, 0, f, f1);
        this.chainSwing(neckParts, speed2, -0.1F, 2, f, f1);
        this.flap(rightBodyFin, speed2, 0.6F, true, 0, 0, f, f1);
        this.flap(leftBodyFin, speed2, 0.6F, false, 0, 0, f, f1);
        ((EntityPrehistoric) entity).chainBuffer.applyChainSwingBuffer(tailParts);
        if (entity.getPassengers().isEmpty()) {
            ModelUtils.faceTargetMod(upperBody, f3, f4, 0.5F);
        }
    }

}
