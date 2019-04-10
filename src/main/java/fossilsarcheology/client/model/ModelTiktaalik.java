package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityTiktaalik;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;

public class ModelTiktaalik extends ModelPrehistoric {
    public AdvancedModelRenderer BodyFront;
    public AdvancedModelRenderer BodyBack;
    public AdvancedModelRenderer Neck;
    public AdvancedModelRenderer LeftWrist;
    public AdvancedModelRenderer RightWrist;
    public AdvancedModelRenderer Tail;
    public AdvancedModelRenderer LeftPelvicFin;
    public AdvancedModelRenderer RightPelvicFin;
    public AdvancedModelRenderer TailFin;
    public AdvancedModelRenderer Head;
    public AdvancedModelRenderer LowerJaw;
    public AdvancedModelRenderer UpperJaw;
    public AdvancedModelRenderer Eyes;
    public AdvancedModelRenderer LeftFrontFin;
    public AdvancedModelRenderer RightFrontFin;
    private final ModelAnimator animator;

    public ModelTiktaalik() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Tail = new AdvancedModelRenderer(this, 38, 0);
        this.Tail.setRotationPoint(0.0F, 0.0F, 10.3F);
        this.Tail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 8, 0.0F);
        this.Head = new AdvancedModelRenderer(this, 0, 38);
        this.Head.setRotationPoint(0.0F, -0.3F, -1.7F);
        this.Head.addBox(-4.0F, -2.0F, -2.5F, 8, 4, 4, 0.0F);
        this.setRotateAngle(Head, -0.04241150082346221F, 0.0F, 0.0F);
        this.TailFin = new AdvancedModelRenderer(this, 43, 11);
        this.TailFin.setRotationPoint(0.0F, 0.0F, 6.8F);
        this.TailFin.addBox(-0.5F, -2.0F, 0.0F, 1, 4, 7, 0.0F);
        this.Neck = new AdvancedModelRenderer(this, 26, 0);
        this.Neck.setRotationPoint(0.0F, -0.2F, -4.5F);
        this.Neck.addBox(-3.0F, -2.0F, -2.5F, 6, 4, 4, 0.0F);
        this.UpperJaw = new AdvancedModelRenderer(this, 29, 26);
        this.UpperJaw.setRotationPoint(0.0F, -0.6F, -1.8F);
        this.UpperJaw.addBox(-3.5F, -1.0F, -7.0F, 7, 2, 7, 0.0F);
        this.setRotateAngle(UpperJaw, 0.04241150082346221F, 0.0F, 0.0F);
        this.BodyBack = new AdvancedModelRenderer(this, 0, 17);
        this.BodyBack.setRotationPoint(0.0F, 0.0F, 7.0F);
        this.BodyBack.addBox(-3.0F, -2.0F, -1.0F, 6, 4, 12, 0.0F);
        this.setRotateAngle(BodyBack, -0.04241150082346221F, 0.0F, 0.0F);
        this.RightWrist = new AdvancedModelRenderer(this, 15, 35);
        this.RightWrist.mirror = true;
        this.RightWrist.setRotationPoint(-3.0F, 0.8F, -3.5F);
        this.RightWrist.addBox(-3.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(RightWrist, 0.0F, 0.33964107243809655F, -0.40980330836826856F);
        this.LeftWrist = new AdvancedModelRenderer(this, 15, 35);
        this.LeftWrist.setRotationPoint(3.0F, 0.8F, -3.5F);
        this.LeftWrist.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(LeftWrist, 0.0F, -0.33964107243809655F, 0.40980330836826856F);
        this.LeftPelvicFin = new AdvancedModelRenderer(this, 1, 18);
        this.LeftPelvicFin.setRotationPoint(2.5F, 1.0F, 10.0F);
        this.LeftPelvicFin.addBox(-0.5F, 0.0F, -1.0F, 1, 2, 4, 0.0F);
        this.setRotateAngle(LeftPelvicFin, -0.5094616086571448F, 0.16982053621904827F, -0.8066911802717791F);
        this.LeftFrontFin = new AdvancedModelRenderer(this, 15, 33);
        this.LeftFrontFin.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.LeftFrontFin.addBox(-0.5F, 0.0F, 0.0F, 5, 1, 3, 0.0F);
        this.setRotateAngle(LeftFrontFin, 0.0F, 0.0F, -0.22759093446006054F);
        this.RightFrontFin = new AdvancedModelRenderer(this, 15, 33);
        this.RightFrontFin.mirror = true;
        this.RightFrontFin.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.RightFrontFin.addBox(-4.5F, 0.0F, 0.0F, 5, 1, 3, 0.0F);
        this.setRotateAngle(RightFrontFin, 0.0F, 0.0F, 0.22759093446006054F);
        this.LowerJaw = new AdvancedModelRenderer(this, 24, 17);
        this.LowerJaw.setRotationPoint(0.0F, 1.1F, -2.1F);
        this.LowerJaw.addBox(-3.0F, -1.0F, -6.0F, 6, 2, 7, 0.0F);
        this.setRotateAngle(LowerJaw, -0.04241150082346221F, 0.0F, 0.0F);
        this.BodyFront = new AdvancedModelRenderer(this, 0, 0);
        this.BodyFront.setRotationPoint(0.0F, 20.7F, 0.0F);
        this.BodyFront.addBox(-3.5F, -2.5F, -4.0F, 7, 5, 12, 0.0F);
        this.RightPelvicFin = new AdvancedModelRenderer(this, 0, 0);
        this.RightPelvicFin.setRotationPoint(-2.5F, 1.0F, 10.0F);
        this.RightPelvicFin.addBox(-0.5F, 0.0F, -1.0F, 1, 2, 4, 0.0F);
        this.setRotateAngle(RightPelvicFin, -0.5094616086571448F, -0.16982053621904827F, 0.8066911802717791F);
        this.Eyes = new AdvancedModelRenderer(this, 0, 7);
        this.Eyes.setRotationPoint(0.0F, -1.3F, -3.3F);
        this.Eyes.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(Eyes, -0.12740903539558604F, 0.0F, 0.0F);
        this.BodyBack.addChild(this.Tail);
        this.Neck.addChild(this.Head);
        this.Tail.addChild(this.TailFin);
        this.BodyFront.addChild(this.Neck);
        this.Head.addChild(this.UpperJaw);
        this.BodyFront.addChild(this.BodyBack);
        this.BodyFront.addChild(this.RightWrist);
        this.BodyFront.addChild(this.LeftWrist);
        this.BodyBack.addChild(this.LeftPelvicFin);
        this.LeftWrist.addChild(this.LeftFrontFin);
        this.RightWrist.addChild(this.RightFrontFin);
        this.Head.addChild(this.LowerJaw);
        this.BodyBack.addChild(this.RightPelvicFin);
        this.Head.addChild(this.Eyes);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }


    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.BodyFront.render(f5);
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityTiktaalik prehistoric = (EntityTiktaalik) entity;
        animator.update(entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, UpperJaw, -15, 0, 0);
        ModelUtils.rotate(animator, Eyes, -15, 0, 0);
        ModelUtils.rotate(animator, LowerJaw, 30, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(5);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, UpperJaw, -5, 0, 0);
        ModelUtils.rotate(animator, Eyes, -5, 0, 0);
        ModelUtils.rotate(animator, LowerJaw, 20, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        float speed_swim = 0.6F;
        float speed_walk = 0.9F;
        float speed_idle = 0.1F;
        float degree_idle = 0.2F;
        float degree_swim = 0.5F;
        float degree_walk = 0.25F;
        AdvancedModelRenderer[] tailParts = {Tail, TailFin};
        AdvancedModelRenderer[] lowerBodyParts = {BodyBack, BodyFront, Tail, TailFin};
        EntityTiktaalik prehistoric = (EntityTiktaalik)entity;
        if (prehistoric.isSkeleton() || prehistoric.isAIDisabled()) {
            return;
        }
        this.faceTarget(f3, f4, 2, Head);
        if(prehistoric.swimProgress > 0) {
            this.chainSwing(lowerBodyParts, speed_swim, degree_swim, 5, f, f1);
            this.swing(Neck, speed_swim, -degree_swim, false, 0, 0, f, f1);
            this.flap(LeftWrist, speed_swim, -degree_swim, false, 0, 0, f, f1);
            this.flap(RightWrist, speed_swim, -degree_swim, true, 0, 0, f, f1);
            this.flap(LeftPelvicFin, speed_swim, -degree_swim, false, 1, 0, f, f1);
            this.flap(RightPelvicFin, speed_swim, -degree_swim, true, 1, 0, f, f1);
        }else{
            this.chainSwing(lowerBodyParts, speed_walk, degree_walk * 0.5F, 5, f, f1);
            this.swing(LeftWrist, speed_walk, 1.5F * degree_walk, false, 0, 0, f, f1);
            this.swing(LeftFrontFin, speed_walk, 1.5F * degree_walk, false, 0, 0, f, f1);
            this.swing(RightWrist, speed_walk, 1.5F * degree_walk, true, 0, 0, f, f1);
            this.swing(RightFrontFin, speed_walk, 1.5F * degree_walk, true, 0, 0, f, f1);
            this.walk(LeftPelvicFin, speed_walk, 1.5F * degree_walk, false, 0, 0, f, f1);
            this.walk(RightPelvicFin, speed_walk, 1.5F * degree_walk, false, 0, 0, f, f1);
            this.chainSwing(tailParts, speed_idle, degree_idle, 5, entity.ticksExisted, 1);

        }
        float sleepProgress = prehistoric.sleepProgress;
        this.sitAnimationRotation(BodyBack, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail, sleepProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(TailFin, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Head, sleepProgress, 0, (float)Math.toRadians(-35), (float)Math.toRadians(10));
        float sitProgress = prehistoric.sitProgress;
        this.sitAnimationRotation(BodyBack, sitProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail, sitProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(TailFin, sitProgress, 0, (float)Math.toRadians(25), 0);

    }

}
