package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityMeganeura;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelMeganeura extends ModelPrehistoric {
    public AdvancedModelRenderer Head;
    public AdvancedModelRenderer LeftEye;
    public AdvancedModelRenderer LeftPincer;
    public AdvancedModelRenderer RightEye;
    public AdvancedModelRenderer RightPincer;
    public AdvancedModelRenderer Body;
    public AdvancedModelRenderer Tail;
    public AdvancedModelRenderer RightTopWing;
    public AdvancedModelRenderer LeftTopWing;
    public AdvancedModelRenderer RightBottomWing;
    public AdvancedModelRenderer LeftBottomWing;
    public AdvancedModelRenderer LeftLeg1;
    public AdvancedModelRenderer LeftLeg2;
    public AdvancedModelRenderer LeftLeg3;
    public AdvancedModelRenderer RightLeg1;
    public AdvancedModelRenderer RightLeg2;
    public AdvancedModelRenderer RightLeg3;
    public AdvancedModelRenderer nymphBack;
    public AdvancedModelRenderer RightTailEnd;
    public AdvancedModelRenderer LeftTailEnd;
    public AdvancedModelRenderer LeftFoot1;
    public AdvancedModelRenderer LeftFoot2;
    public AdvancedModelRenderer LeftFoot3;
    public AdvancedModelRenderer RightFoot1;
    public AdvancedModelRenderer RightFoot2;
    public AdvancedModelRenderer RightFoot3;
    private final ModelAnimator animator;

    public ModelMeganeura() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.LeftPincer = new AdvancedModelRenderer(this, 19, 19);
        this.LeftPincer.setRotationPoint(0.8F, 2.0F, -4.0F);
        this.LeftPincer.addBox(0.0F, 0.0F, -1.5F, 1, 1, 2, 0.0F);
        this.setRotateAngle(LeftPincer, 0.0F, 0.27314402793711257F, 0.0F);
        this.LeftBottomWing = new AdvancedModelRenderer(this, 63, 11);
        this.LeftBottomWing.mirror = true;
        this.LeftBottomWing.setRotationPoint(3.0F, 1.5F, 6.0F);
        this.LeftBottomWing.addBox(0.0F, 0.0F, 0.0F, 22, 0, 8, 0.0F);
        this.LeftEye = new AdvancedModelRenderer(this, 18, 12);
        this.LeftEye.setRotationPoint(2.0F, 0.5F, 0.0F);
        this.LeftEye.addBox(0.0F, -0.5F, -3.0F, 1, 3, 3, 0.0F);
        this.LeftLeg3 = new AdvancedModelRenderer(this, 7, 0);
        this.LeftLeg3.setRotationPoint(3.0F, 3.5F, 5.5F);
        this.LeftLeg3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LeftLeg3, 0.0F, 0.0F, 0.22759093446006054F);
        this.Body = new AdvancedModelRenderer(this, 30, 5);
        this.Body.setRotationPoint(0.0F, 0.0F, 1.9F);
        this.Body.addBox(-3.0F, -1.0F, 0.0F, 6, 6, 7, 0.0F);
        this.RightLeg2 = new AdvancedModelRenderer(this, 7, 0);
        this.RightLeg2.mirror = true;
        this.RightLeg2.setRotationPoint(-3.0F, 3.5F, 3.0F);
        this.RightLeg2.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RightLeg2, 0.0F, 0.0F, -0.22759093446006054F);
        this.RightBottomWing = new AdvancedModelRenderer(this, 63, 11);
        this.RightBottomWing.setRotationPoint(-3.0F, 1.5F, 6.0F);
        this.RightBottomWing.addBox(-22.0F, 0.0F, 0.0F, 22, 0, 8, 0.0F);
        this.RightEye = new AdvancedModelRenderer(this, 18, 12);
        this.RightEye.mirror = true;
        this.RightEye.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.RightEye.addBox(-1.0F, 0.0F, -3.0F, 1, 3, 3, 0.0F);
        this.LeftFoot3 = new AdvancedModelRenderer(this, 0, 0);
        this.LeftFoot3.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.LeftFoot3.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(LeftFoot3, 0.0F, 0.0F, -0.045553093477052F);
        this.RightFoot3 = new AdvancedModelRenderer(this, 0, 0);
        this.RightFoot3.mirror = true;
        this.RightFoot3.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.RightFoot3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(RightFoot3, 0.0F, 0.0F, 0.045553093477052F);
        this.Head = new AdvancedModelRenderer(this, 15, 0);
        this.Head.setRotationPoint(0.0F, 17.0F, -6.0F);
        this.Head.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 6, 0.0F);
        this.RightTopWing = new AdvancedModelRenderer(this, 60, 1);
        this.RightTopWing.setRotationPoint(-1.5F, 0.0F, 2.0F);
        this.RightTopWing.addBox(-25.0F, 0.0F, 0.0F, 25, 0, 8, 0.0F);
        this.setRotateAngle(RightTopWing, 0.0F, -0.36425021489121656F, 0.0F);
        this.RightTailEnd = new AdvancedModelRenderer(this, 0, 19);
        this.RightTailEnd.mirror = true;
        this.RightTailEnd.setRotationPoint(0.0F, 2.5F, 21.0F);
        this.RightTailEnd.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(RightTailEnd, 0.0F, -0.36425021489121656F, 0.0F);
        this.RightPincer = new AdvancedModelRenderer(this, 19, 19);
        this.RightPincer.mirror = true;
        this.RightPincer.setRotationPoint(-0.8F, 2.0F, -3.5F);
        this.RightPincer.addBox(-1.0F, 0.0F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(RightPincer, 0.0F, -0.27314402793711257F, 0.0F);
        this.LeftTopWing = new AdvancedModelRenderer(this, 60, 1);
        this.LeftTopWing.mirror = true;
        this.LeftTopWing.setRotationPoint(1.5F, 0.0F, 2.0F);
        this.LeftTopWing.addBox(0.0F, 0.0F, 0.0F, 25, 0, 8, 0.0F);
        this.setRotateAngle(LeftTopWing, 0.0F, 0.36425021489121656F, 0.0F);
        this.LeftTailEnd = new AdvancedModelRenderer(this, 0, 19);
        this.LeftTailEnd.setRotationPoint(0.0F, 2.5F, 21.0F);
        this.LeftTailEnd.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(LeftTailEnd, 0.0F, 0.36425021489121656F, 0.0F);
        this.LeftLeg1 = new AdvancedModelRenderer(this, 7, 0);
        this.LeftLeg1.setRotationPoint(3.0F, 3.5F, 0.5F);
        this.LeftLeg1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LeftLeg1, 0.0F, 0.0F, 0.22759093446006054F);
        this.LeftFoot2 = new AdvancedModelRenderer(this, 0, 0);
        this.LeftFoot2.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.LeftFoot2.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(LeftFoot2, 0.0F, 0.0F, -0.045553093477052F);
        this.RightFoot2 = new AdvancedModelRenderer(this, 0, 0);
        this.RightFoot2.mirror = true;
        this.RightFoot2.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.RightFoot2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(RightFoot2, 0.0F, 0.0F, 0.045553093477052F);
        this.RightLeg1 = new AdvancedModelRenderer(this, 7, 0);
        this.RightLeg1.mirror = true;
        this.RightLeg1.setRotationPoint(-3.0F, 3.5F, 0.5F);
        this.RightLeg1.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RightLeg1, 0.0F, 0.0F, -0.22759093446006054F);
        this.LeftFoot1 = new AdvancedModelRenderer(this, 0, 0);
        this.LeftFoot1.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.LeftFoot1.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(LeftFoot1, 0.0F, 0.0F, -0.045553093477052F);
        this.RightLeg3 = new AdvancedModelRenderer(this, 7, 0);
        this.RightLeg3.setRotationPoint(-3.0F, 3.5F, 5.5F);
        this.RightLeg3.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RightLeg3, 0.0F, 0.0F, -0.22759093446006054F);
        this.LeftLeg2 = new AdvancedModelRenderer(this, 7, 0);
        this.LeftLeg2.setRotationPoint(3.0F, 3.5F, 3.0F);
        this.LeftLeg2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LeftLeg2, 0.0F, 0.0F, 0.22759093446006054F);
        this.RightFoot1 = new AdvancedModelRenderer(this, 0, 0);
        this.RightFoot1.mirror = true;
        this.RightFoot1.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.RightFoot1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(RightFoot1, 0.0F, 0.0F, 0.045553093477052F);
        this.Tail = new AdvancedModelRenderer(this, 37, 0);
        this.Tail.setRotationPoint(0.0F, 0.0F, 7.0F);
        this.Tail.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 21, 0.0F);
        this.setRotateAngle(Tail, -0.045553093477052F, 0.0F, 0.0F);
        this.nymphBack = new AdvancedModelRenderer(this, 42, 26);
        this.nymphBack.setRotationPoint(0.0F, 0.0F, 7.0F);
        this.nymphBack.addBox(-3.0F, -1.0F, 0.0F, 6, 6, 15, 0.0F);
        this.Head.addChild(this.LeftPincer);
        this.Body.addChild(this.LeftBottomWing);
        this.Head.addChild(this.LeftEye);
        this.Body.addChild(this.LeftLeg3);
        this.Head.addChild(this.Body);
        this.Body.addChild(this.RightLeg2);
        this.Body.addChild(this.RightBottomWing);
        this.Head.addChild(this.RightEye);
        this.LeftLeg3.addChild(this.LeftFoot3);
        this.RightLeg3.addChild(this.RightFoot3);
        this.Body.addChild(this.RightTopWing);
        this.Tail.addChild(this.RightTailEnd);
        this.Head.addChild(this.RightPincer);
        this.Body.addChild(this.LeftTopWing);
        this.Tail.addChild(this.LeftTailEnd);
        this.Body.addChild(this.LeftLeg1);
        this.LeftLeg2.addChild(this.LeftFoot2);
        this.RightLeg2.addChild(this.RightFoot2);
        this.Body.addChild(this.RightLeg1);
        this.LeftLeg1.addChild(this.LeftFoot1);
        this.Body.addChild(this.RightLeg3);
        this.Body.addChild(this.LeftLeg2);
        this.RightLeg1.addChild(this.RightFoot1);
        this.Body.addChild(this.Tail);
        this.Body.addChild(this.nymphBack);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.Head.render(f5);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityMeganeura prehistoric = (EntityMeganeura) entity;
        animator.update(entity);
        blockMovement(f, f1, f2, f3, f4, f5, (Entity) entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, LeftPincer, 0, -43, 0);
        ModelUtils.rotate(animator, RightPincer, 0, 43, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, LeftPincer, 0, 43, 0);
        ModelUtils.rotate(animator, RightPincer, 0, -43, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, LeftPincer, 0, -43, 0);
        ModelUtils.rotate(animator, RightPincer, 0, 43, 0);
        animator.endKeyframe();
        animator.resetKeyframe(5);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, LeftPincer, 0, -43, 0);
        ModelUtils.rotate(animator, RightPincer, 0, 43, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        AdvancedModelRenderer[] leftWing = {LeftTopWing, LeftBottomWing};
        AdvancedModelRenderer[] rightWing = {RightTopWing, RightBottomWing};
        AdvancedModelRenderer[] leftLegs = {LeftLeg1, LeftLeg2, LeftLeg3};
        AdvancedModelRenderer[] rightLegs = {RightLeg1, RightLeg2, RightLeg3};
        EntityMeganeura meganeura = (EntityMeganeura)entity;
        float speed_fly = 1.1F;
        float speed_walk = 0.4F;
        float degree_fly = 1F;
        float degree_walk = 0.75F;
        if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
            return;
        }
        if(meganeura.flyProgress == 0){
            this.chainWave(leftLegs, speed_walk, degree_walk, 1, f, f1);
            this.chainWave(rightLegs, speed_walk, degree_walk, 1, f, f1);
        }else{
            this.chainFlap(leftWing, speed_fly, -degree_fly * 0.75F, 1, f2, 1);
            this.chainFlap(rightWing, speed_fly, degree_fly * 0.75F, 1, f2, 1);
            this.chainWave(leftLegs, speed_fly * 0.5F, degree_fly * -0.5F, 1, f2, 1);
            this.chainWave(rightLegs, speed_fly * 0.5F, degree_fly * -0.5F, 1, f2, 1);
        }
        sitAnimationRotation(RightLeg1, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(-50.0D));
        sitAnimationRotation(RightLeg2, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(-50.0D));
        sitAnimationRotation(RightLeg3, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(-50.0D));
        sitAnimationRotation(LeftLeg1, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(50.0D));
        sitAnimationRotation(LeftLeg2, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(50.0D));
        sitAnimationRotation(LeftLeg3, meganeura.flyProgress, (float) Math.toRadians(60.0D), 0, (float) Math.toRadians(50.0D));

    }
}
