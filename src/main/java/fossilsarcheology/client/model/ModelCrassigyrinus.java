package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityCrassigyrinus;
import fossilsarcheology.server.entity.prehistoric.EntityTiktaalik;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;

public class ModelCrassigyrinus extends ModelPrehistoric {
    public AdvancedModelRenderer Body;
    public AdvancedModelRenderer Tail1;
    public AdvancedModelRenderer LeftArm;
    public AdvancedModelRenderer RightArm;
    public AdvancedModelRenderer Head;
    public AdvancedModelRenderer Tail2;
    public AdvancedModelRenderer LeftLeg;
    public AdvancedModelRenderer RightLeg;
    public AdvancedModelRenderer TailFin;
    public AdvancedModelRenderer shape22;
    public AdvancedModelRenderer shape23;
    public AdvancedModelRenderer LeftFoot;
    public AdvancedModelRenderer RightFoot;
    public AdvancedModelRenderer Jaw;
    public AdvancedModelRenderer Snout1;
    public AdvancedModelRenderer shape31;
    public AdvancedModelRenderer Snout2;
    private final ModelAnimator animator;

    public ModelCrassigyrinus() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightArm = new AdvancedModelRenderer(this, 20, 19);
        this.RightArm.mirror = true;
        this.RightArm.setRotationPoint(-2.5F, 3.7F, 1.0F);
        this.RightArm.addBox(-0.2F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(RightArm, 0.7285004297824331F, 0.0F, 0.0F);
        this.RightLeg = new AdvancedModelRenderer(this, 52, 17);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-1.5F, 3.0F, 6.5F);
        this.RightLeg.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(RightLeg, -0.22759093446006054F, -0.27314402793711257F, 0.0F);
        this.LeftFoot = new AdvancedModelRenderer(this, 20, 17);
        this.LeftFoot.setRotationPoint(0.0F, 1.0F, 4.0F);
        this.LeftFoot.addBox(-0.5F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
        this.setRotateAngle(LeftFoot, 0.4553564018453205F, 0.0F, 0.0F);
        this.Body = new AdvancedModelRenderer(this, 32, 15);
        this.Body.setRotationPoint(0.0F, 20.0F, -3.0F);
        this.Body.addBox(-2.5F, 0.0F, 0.0F, 5, 5, 8, 0.0F);
        this.setRotateAngle(Body, -0.045553093477052F, 0.0F, 0.0F);
        this.Head = new AdvancedModelRenderer(this, 0, 10);
        this.Head.setRotationPoint(0.0F, 0.1F, 0.5F);
        this.Head.addBox(-2.0F, 0.0F, -3.8F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, 0.091106186954104F, 0.0F, 0.0F);
        this.Snout2 = new AdvancedModelRenderer(this, 12, 7);
        this.Snout2.setRotationPoint(0.0F, 0.0F, -3.5F);
        this.Snout2.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 4, 0.0F);
        this.setRotateAngle(Snout2, -0.5113814708343385F, 0.0F, 0.0F);
        this.LeftArm = new AdvancedModelRenderer(this, 20, 19);
        this.LeftArm.setRotationPoint(2.0F, 3.7F, 1.0F);
        this.LeftArm.addBox(-0.2F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftArm, 0.7285004297824331F, 0.0F, 0.0F);
        this.Snout1 = new AdvancedModelRenderer(this, 12, 0);
        this.Snout1.setRotationPoint(0.0F, 0.3F, -3.4F);
        this.Snout1.addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(Snout1, 0.5113814708343385F, 0.0F, 0.0F);
        this.shape31 = new AdvancedModelRenderer(this, 22, 0);
        this.shape31.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.shape31.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 0, 0.0F);
        this.Tail2 = new AdvancedModelRenderer(this, 13, 17);
        this.Tail2.setRotationPoint(0.0F, 0.3F, 7.0F);
        this.Tail2.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 12, 0.0F);
        this.setRotateAngle(Tail2, -0.045553093477052F, 0.0F, 0.0F);
        this.shape23 = new AdvancedModelRenderer(this, 0, 12);
        this.shape23.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.shape23.addBox(0.0F, -1.5F, 0.0F, 0, 5, 12, 0.0F);
        this.TailFin = new AdvancedModelRenderer(this, 0, 9);
        this.TailFin.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.TailFin.addBox(0.0F, -1.5F, 0.0F, 0, 6, 9, 0.0F);
        this.Tail1 = new AdvancedModelRenderer(this, 33, 0);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 6.5F);
        this.Tail1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 8, 0.0F);
        this.setRotateAngle(Tail1, 0.091106186954104F, 0.0F, 0.0F);
        this.Jaw = new AdvancedModelRenderer(this, 0, 0);
        this.Jaw.setRotationPoint(0.0F, 3.7F, -1.0F);
        this.Jaw.addBox(-1.0F, 0.0F, -5.0F, 2, 1, 6, 0.0F);
        this.setRotateAngle(Jaw, -0.091106186954104F, 0.0F, 0.0F);
        this.LeftLeg = new AdvancedModelRenderer(this, 52, 17);
        this.LeftLeg.setRotationPoint(1.5F, 3.0F, 6.5F);
        this.LeftLeg.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(LeftLeg, -0.22759093446006054F, 0.27314402793711257F, 0.0F);
        this.shape22 = new AdvancedModelRenderer(this, 16, 4);
        this.shape22.setRotationPoint(0.0F, 0.5F, 11.0F);
        this.shape22.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(shape22, -0.045553093477052F, 0.0F, 0.0F);
        this.RightFoot = new AdvancedModelRenderer(this, 20, 17);
        this.RightFoot.mirror = true;
        this.RightFoot.setRotationPoint(0.0F, 1.0F, 4.0F);
        this.RightFoot.addBox(-1.5F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
        this.setRotateAngle(RightFoot, 0.4553564018453205F, 0.0F, 0.0F);
        this.Body.addChild(this.RightArm);
        this.Tail1.addChild(this.RightLeg);
        this.LeftLeg.addChild(this.LeftFoot);
        this.Body.addChild(this.Head);
        this.Snout1.addChild(this.Snout2);
        this.Body.addChild(this.LeftArm);
        this.Head.addChild(this.Snout1);
        this.Head.addChild(this.shape31);
        this.Tail1.addChild(this.Tail2);
        this.shape22.addChild(this.shape23);
        this.Tail2.addChild(this.TailFin);
        this.Body.addChild(this.Tail1);
        this.Head.addChild(this.Jaw);
        this.Tail1.addChild(this.LeftLeg);
        this.Tail2.addChild(this.shape22);
        this.RightLeg.addChild(this.RightFoot);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, -0.3F);

        GlStateManager.translate(this.Body.offsetX, this.Body.offsetY, this.Body.offsetZ);
        GlStateManager.translate(this.Body.rotationPointX * f5, this.Body.rotationPointY * f5, this.Body.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 0.7D);
        GlStateManager.translate(-this.Body.offsetX, -this.Body.offsetY, -this.Body.offsetZ);
        GlStateManager.translate(-this.Body.rotationPointX * f5, -this.Body.rotationPointY * f5, -this.Body.rotationPointZ * f5);
        this.Body.render(f5);
        GlStateManager.popMatrix();
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityCrassigyrinus prehistoric = (EntityCrassigyrinus) entity;
        animator.update(entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, Jaw, 50, 0, 0);
        ModelUtils.rotate(animator, Head, -20, 0, 0);
        animator.move(Head, 0, 0, 1);
        animator.endKeyframe();
        animator.resetKeyframe(5);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, Jaw, 30, 0, 0);
        ModelUtils.rotate(animator, Head, -10, 0, 0);
        animator.move(Head, 0, 0, 0.5F);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        float speed_swim = 0.6F;
        float speed_idle = 0.1F;
        float degree_idle = 0.2F;
        float degree_swim = 0.35F;
        AdvancedModelRenderer[] lowerBodyParts = {Head, Body, Tail1, Tail2, shape22};
        AdvancedModelRenderer[] tailParts = {Tail2, shape22};
        EntityCrassigyrinus prehistoric = (EntityCrassigyrinus) entity;
        if (prehistoric.isSkeleton() || prehistoric.isAIDisabled()) {
            return;
        }
        this.faceTarget(f3, f4, 2, Head);
        this.chainSwing(lowerBodyParts, speed_swim, degree_swim, -3, f, f1);
        this.chainSwing(tailParts, speed_idle, degree_idle, -3, entity.ticksExisted, 1);
        float sleepProgress = prehistoric.sleepProgress;
        this.sitAnimationRotation(Body, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail1, sleepProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(Tail2, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Head, sleepProgress, 0, (float)Math.toRadians(-35), (float)Math.toRadians(10));
        float sitProgress = prehistoric.sitProgress;
        this.sitAnimationRotation(Body, sitProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail1, sitProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(Tail2, sitProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(shape22, sitProgress, 0, (float)Math.toRadians(25), 0);
    }
}
