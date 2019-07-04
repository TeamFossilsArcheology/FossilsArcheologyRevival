package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityCrassigyrinus;
import fossilsarcheology.server.entity.prehistoric.EntityDiplocaulus;
import fossilsarcheology.server.entity.prehistoric.EntityTiktaalik;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class ModelDiplocaulus extends ModelPrehistoric {
    public AdvancedModelRenderer Body;
    public AdvancedModelRenderer Tail1;
    public AdvancedModelRenderer LeftLeg;
    public AdvancedModelRenderer LeftArm;
    public AdvancedModelRenderer RightArm;
    public AdvancedModelRenderer RightLeg;
    public AdvancedModelRenderer Neck;
    public AdvancedModelRenderer Tail2;
    public AdvancedModelRenderer TailFin;
    public AdvancedModelRenderer LeftFoot;
    public AdvancedModelRenderer LeftHand;
    public AdvancedModelRenderer RightHand;
    public AdvancedModelRenderer RightFoot;
    public AdvancedModelRenderer Head;
    public AdvancedModelRenderer Mouth;
    public AdvancedModelRenderer LeftBoomerang;
    public AdvancedModelRenderer RightBoomerang;
    public AdvancedModelRenderer Meme;
    private final ModelAnimator animator;

    public ModelDiplocaulus() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Tail2 = new AdvancedModelRenderer(this, 12, 2);
        this.Tail2.setRotationPoint(0.0F, 0.3F, 5.5F);
        this.Tail2.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(Tail2, -0.091106186954104F, 0.0F, 0.0F);
        this.TailFin = new AdvancedModelRenderer(this, 40, -1);
        this.TailFin.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.TailFin.addBox(0.0F, -1.0F, 0.0F, 0, 4, 12, 0.0F);
        this.Head = new AdvancedModelRenderer(this, 0, 17);
        this.Head.setRotationPoint(0.0F, 0.0F, -7.0F);
        this.Head.addBox(-1.5F, 0.0F, -2.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(Head, -0.045553093477052F, 0.0F, 0.0F);
        this.LeftArm = new AdvancedModelRenderer(this, 60, 18);
        this.LeftArm.setRotationPoint(4.0F, 2.5F, 0.7F);
        this.LeftArm.addBox(0.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(LeftArm, 0.9560913642424937F, 0.0F, 0.0F);
        this.RightLeg = new AdvancedModelRenderer(this, 56, 23);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-4.0F, 2.5F, 10.5F);
        this.RightLeg.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RightLeg, -0.22759093446006054F, 0.0F, 0.0F);
        this.RightHand = new AdvancedModelRenderer(this, 51, 20);
        this.RightHand.mirror = true;
        this.RightHand.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.RightHand.addBox(-1.5F, 0.0F, -1.0F, 2, 0, 2, 0.0F);
        this.setRotateAngle(RightHand, 0.22759093446006054F, 0.0F, 0.0F);
        this.Neck = new AdvancedModelRenderer(this, 1, 22);
        this.Neck.setRotationPoint(0.0F, 0.2F, 1.0F);
        this.Neck.addBox(-2.0F, 0.0F, -7.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Neck, 0.045553093477052F, 0.0F, 0.0F);
        this.Meme = new AdvancedModelRenderer(this, -3, 0);
        this.Meme.setRotationPoint(0.0F, 0.5F, 3.0F);
        this.Meme.addBox(-5.0F, 0.0F, -0.5F, 10, 0, 4, 0.0F);
        this.LeftBoomerang = new AdvancedModelRenderer(this, 11, 16);
        this.LeftBoomerang.setRotationPoint(1.5F, 0.0F, -2.0F);
        this.LeftBoomerang.addBox(0.0F, 0.01F, 0.0F, 8, 2, 3, 0.0F);
        this.setRotateAngle(LeftBoomerang, 0.0F, -0.7740535232594852F, 0.0F);
        this.RightArm = new AdvancedModelRenderer(this, 60, 18);
        this.RightArm.mirror = true;
        this.RightArm.setRotationPoint(-4.0F, 2.5F, 0.7F);
        this.RightArm.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(RightArm, 0.9560913642424937F, 0.0F, 0.0F);
        this.LeftFoot = new AdvancedModelRenderer(this, 53, 23);
        this.LeftFoot.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.LeftFoot.addBox(-0.5F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
        this.setRotateAngle(LeftFoot, 0.4553564018453205F, 0.0F, 0.0F);
        this.RightFoot = new AdvancedModelRenderer(this, 53, 23);
        this.RightFoot.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.RightFoot.addBox(-1.5F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
        this.setRotateAngle(RightFoot, 0.4553564018453205F, 0.0F, 0.0F);
        this.LeftHand = new AdvancedModelRenderer(this, 51, 20);
        this.LeftHand.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.LeftHand.addBox(-0.5F, 0.0F, -1.0F, 2, 0, 2, 0.0F);
        this.setRotateAngle(LeftHand, 0.22759093446006054F, 0.0F, 0.0F);
        this.Tail1 = new AdvancedModelRenderer(this, 43, 0);
        this.Tail1.setRotationPoint(0.0F, 0.2F, 12.0F);
        this.Tail1.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 6, 0.0F);
        this.setRotateAngle(Tail1, -0.045553093477052F, 0.0F, 0.0F);
        this.Mouth = new AdvancedModelRenderer(this, 17, 23);
        this.Mouth.setRotationPoint(0.0F, 3.0F, -7.0F);
        this.Mouth.addBox(-1.0F, -1.0F, -2.0F, 2, 1, 2, 0.0F);
        this.setRotateAngle(Mouth, -0.091106186954104F, 0.0F, 0.0F);
        this.RightBoomerang = new AdvancedModelRenderer(this, 11, 16);
        this.RightBoomerang.mirror = true;
        this.RightBoomerang.setRotationPoint(-1.5F, 0.0F, -2.0F);
        this.RightBoomerang.addBox(-8.0F, 0.01F, 0.0F, 8, 2, 3, 0.0F);
        this.setRotateAngle(RightBoomerang, 0.0F, 0.7740535232594852F, 0.0F);
        this.LeftLeg = new AdvancedModelRenderer(this, 56, 23);
        this.LeftLeg.setRotationPoint(4.0F, 2.5F, 10.5F);
        this.LeftLeg.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LeftLeg, -0.22759093446006054F, 0.0F, 0.0F);
        this.Body = new AdvancedModelRenderer(this, 24, 16);
        this.Body.setRotationPoint(0.0F, 22.0F, -4.0F);
        this.Body.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 12, 0.0F);
        this.Tail1.addChild(this.Tail2);
        this.Tail2.addChild(this.TailFin);
        this.Neck.addChild(this.Head);
        this.Body.addChild(this.LeftArm);
        this.Body.addChild(this.RightLeg);
        this.RightArm.addChild(this.RightHand);
        this.Body.addChild(this.Neck);
        this.Head.addChild(this.Meme);
        this.Head.addChild(this.LeftBoomerang);
        this.Body.addChild(this.RightArm);
        this.LeftLeg.addChild(this.LeftFoot);
        this.RightLeg.addChild(this.RightFoot);
        this.LeftArm.addChild(this.LeftHand);
        this.Body.addChild(this.Tail1);
        this.Neck.addChild(this.Mouth);
        this.Head.addChild(this.RightBoomerang);
        this.Body.addChild(this.LeftLeg);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Body.offsetX, this.Body.offsetY, this.Body.offsetZ);
        GlStateManager.translate(this.Body.rotationPointX * f5, this.Body.rotationPointY * f5, this.Body.rotationPointZ * f5);
        GlStateManager.scale(0.4D, 0.4D, 0.4D);
        GlStateManager.translate(-this.Body.offsetX, -this.Body.offsetY, -this.Body.offsetZ);
        GlStateManager.translate(-this.Body.rotationPointX * f5, -this.Body.rotationPointY * f5, -this.Body.rotationPointZ * f5);
        this.Body.render(f5);
        GlStateManager.popMatrix();
    }
    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityDiplocaulus prehistoric = (EntityDiplocaulus) entity;
        animator.update(entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, Mouth, 40, 0, 0);
        ModelUtils.rotate(animator, Neck, -20, 0, 0);
        animator.move(Mouth, 0, 0, 0.75F);
        animator.endKeyframe();
        animator.resetKeyframe(5);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, Mouth, 30, 0, 0);
        ModelUtils.rotate(animator, Neck, -10, 0, 0);
        animator.move(Mouth, 0, 0, 0.5F);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        float speed_swim = 0.9F;
        float speed_idle = 0.1F;
        float degree_idle = 0.2F;
        float degree_swim = 0.5F;
        AdvancedModelRenderer[] lowerBodyParts = {Neck , Body, Tail1, Tail2};
        AdvancedModelRenderer[] tailParts = {Tail1, Tail2};
        EntityDiplocaulus prehistoric = (EntityDiplocaulus) entity;
        if (prehistoric.isSkeleton() || prehistoric.isAIDisabled()) {
            return;
        }
        this.faceTarget(f3, f4, 2, Neck);
        this.chainSwing(lowerBodyParts, speed_swim, degree_swim, -3, f, f1);
        this.chainSwing(tailParts, speed_idle, degree_idle, -3, entity.ticksExisted, 1);
        float sleepProgress = prehistoric.sleepProgress;
        this.sitAnimationRotation(Body, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail1, sleepProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(Tail2, sleepProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Neck, sleepProgress, 0, (float)Math.toRadians(-15), (float)Math.toRadians(5));
        float sitProgress = prehistoric.sitProgress;
        this.sitAnimationRotation(Body, sitProgress, 0, (float)Math.toRadians(25), 0);
        this.sitAnimationRotation(Tail1, sitProgress, 0, (float)Math.toRadians(35), 0);
        this.sitAnimationRotation(Tail2, sitProgress, 0, (float)Math.toRadians(25), 0);
    }

}
