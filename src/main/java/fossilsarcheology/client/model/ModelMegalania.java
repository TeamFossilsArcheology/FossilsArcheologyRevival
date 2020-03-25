package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityMegalania;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;

public class ModelMegalania extends ModelPrehistoric {
    public AdvancedModelRenderer Shoulders;
    public AdvancedModelRenderer Neck;
    public AdvancedModelRenderer Body;
    public AdvancedModelRenderer RightArm;
    public AdvancedModelRenderer LeftArm;
    public AdvancedModelRenderer Head;
    public AdvancedModelRenderer TopJaw;
    public AdvancedModelRenderer BottomJaw1;
    public AdvancedModelRenderer BottomJaw2;
    public AdvancedModelRenderer Hips;
    public AdvancedModelRenderer RightThigh;
    public AdvancedModelRenderer Tail1;
    public AdvancedModelRenderer LeftThigh;
    public AdvancedModelRenderer RightShin;
    public AdvancedModelRenderer Rightfoot;
    public AdvancedModelRenderer Tail2;
    public AdvancedModelRenderer Tail3;
    public AdvancedModelRenderer LeftShin;
    public AdvancedModelRenderer LeftFoot;
    public AdvancedModelRenderer RLowerArm;
    public AdvancedModelRenderer RHand;
    public AdvancedModelRenderer LLowerArm;
    public AdvancedModelRenderer LHand;
    private final ModelAnimator animator;

    public ModelMegalania() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.BottomJaw1 = new AdvancedModelRenderer(this, 48, 0);
        this.BottomJaw1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.BottomJaw1.addBox(-3.0F, 0.0F, -7.0F, 6, 2, 7, 0.0F);
        this.Tail2 = new AdvancedModelRenderer(this, 91, 20);
        this.Tail2.setRotationPoint(0.0F, 1.0F, 12.0F);
        this.Tail2.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 12, 0.0F);
        this.setRotateAngle(Tail2, -0.136659280431156F, 0.0F, 0.0F);
        this.Rightfoot = new AdvancedModelRenderer(this, 0, 46);
        this.Rightfoot.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.Rightfoot.addBox(-3.0F, 0.0F, -0.5F, 7, 2, 5, 0.0F);
        this.Body = new AdvancedModelRenderer(this, 8, 37);
        this.Body.setRotationPoint(0.0F, 0.2F, 7.0F);
        this.Body.addBox(-5.5F, 0.0F, 0.0F, 11, 10, 17, 0.0F);
        this.setRotateAngle(Body, -0.091106186954104F, 0.0F, 0.0F);
        this.Tail1 = new AdvancedModelRenderer(this, 87, 0);
        this.Tail1.setRotationPoint(0.0F, 0.5F, 9.0F);
        this.Tail1.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 12, 0.0F);
        this.setRotateAngle(Tail1, -0.22759093446006054F, 0.0F, 0.0F);
        this.Hips = new AdvancedModelRenderer(this, 90, 39);
        this.Hips.setRotationPoint(0.0F, 0.5F, 17.0F);
        this.Hips.addBox(-5.0F, 0.0F, 0.0F, 10, 9, 9, 0.0F);
        this.BottomJaw2 = new AdvancedModelRenderer(this, 26, 0);
        this.BottomJaw2.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.BottomJaw2.addBox(-1.5F, 0.0F, -6.0F, 3, 2, 8, 0.0F);
        this.setRotateAngle(BottomJaw2, -0.045553093477052F, 0.0F, 0.0F);
        this.RightShin = new AdvancedModelRenderer(this, 0, 33);
        this.RightShin.setRotationPoint(-8.0F, 0.5F, -4.5F);
        this.RightShin.addBox(0.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(RightShin, 0.045553093477052F, 0.0F, 0.136659280431156F);
        this.RLowerArm = new AdvancedModelRenderer(this, 27, 19);
        this.RLowerArm.setRotationPoint(-9.0F, 0.5F, 0.5F);
        this.RLowerArm.addBox(0.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(RLowerArm, 0.0F, 0.0F, 0.18203784098300857F);
        this.LHand = new AdvancedModelRenderer(this, 29, 10);
        this.LHand.mirror = true;
        this.LHand.setRotationPoint(-4.6F, 9.0F, 0.0F);
        this.LHand.addBox(0.0F, 0.0F, -3.0F, 5, 2, 7, 0.0F);
        this.RightArm = new AdvancedModelRenderer(this, 0, 12);
        this.RightArm.setRotationPoint(-6.0F, 3.7F, 1.5F);
        this.RightArm.addBox(-9.0F, 0.0F, 0.0F, 9, 5, 5, 0.0F);
        this.setRotateAngle(RightArm, -0.22759093446006054F, 0.7740535232594852F, -0.27314402793711257F);
        this.LeftShin = new AdvancedModelRenderer(this, 0, 33);
        this.LeftShin.mirror = true;
        this.LeftShin.setRotationPoint(8.0F, 0.5F, -4.5F);
        this.LeftShin.addBox(-4.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(LeftShin, 0.045553093477052F, 0.0F, -0.136659280431156F);
        this.Shoulders = new AdvancedModelRenderer(this, 48, 28);
        this.Shoulders.setRotationPoint(0.0F, 7.5F, -15.0F);
        this.Shoulders.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 8, 0.0F);
        this.setRotateAngle(Shoulders, 0.045553093477052F, 0.0F, 0.0F);
        this.RightThigh = new AdvancedModelRenderer(this, 0, 22);
        this.RightThigh.setRotationPoint(-5.0F, 2.8F, 6.5F);
        this.RightThigh.addBox(-8.0F, 0.0F, -5.0F, 8, 5, 5, 0.0F);
        this.setRotateAngle(RightThigh, 0.136659280431156F, -0.6829471563053812F, -0.22759093446006054F);
        this.Head = new AdvancedModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.2F, -9.0F);
        this.Head.addBox(-3.0F, 0.0F, -7.0F, 6, 4, 7, 0.0F);
        this.setRotateAngle(Head, 0.045553093477052F, 0.0F, 0.0F);
        this.Tail3 = new AdvancedModelRenderer(this, 66, 49);
        this.Tail3.setRotationPoint(0.0F, 1.0F, 12.0F);
        this.Tail3.addBox(-2.0F, 0.0F, -1.0F, 4, 4, 10, 0.0F);
        this.setRotateAngle(Tail3, 0.18203784098300857F, 0.0F, 0.0F);
        this.LeftFoot = new AdvancedModelRenderer(this, 0, 46);
        this.LeftFoot.mirror = true;
        this.LeftFoot.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.LeftFoot.addBox(-4.0F, 0.0F, -0.5F, 7, 2, 5, 0.0F);
        this.LeftArm = new AdvancedModelRenderer(this, 0, 12);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(6.0F, 3.7F, 1.5F);
        this.LeftArm.addBox(0.0F, 0.0F, 0.0F, 9, 5, 5, 0.0F);
        this.setRotateAngle(LeftArm, -0.22759093446006054F, -0.7740535232594852F, 0.27314402793711257F);
        this.Neck = new AdvancedModelRenderer(this, 46, 9);
        this.Neck.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.Neck.addBox(-3.5F, 0.0F, -10.0F, 7, 8, 11, 0.0F);
        this.setRotateAngle(Neck, -0.045553093477052F, 0.0F, 0.0F);
        this.TopJaw = new AdvancedModelRenderer(this, 74, 0);
        this.TopJaw.setRotationPoint(0.0F, 4.0F, -6.0F);
        this.TopJaw.addBox(-2.0F, -3.0F, -6.5F, 4, 3, 8, 0.0F);
        this.RHand = new AdvancedModelRenderer(this, 29, 10);
        this.RHand.setRotationPoint(4.0F, 9.0F, 0.0F);
        this.RHand.addBox(-4.5F, 0.0F, -3.0F, 5, 2, 7, 0.0F);
        this.LeftThigh = new AdvancedModelRenderer(this, 0, 22);
        this.LeftThigh.mirror = true;
        this.LeftThigh.setRotationPoint(5.0F, 2.8F, 6.5F);
        this.LeftThigh.addBox(0.0F, 0.0F, -5.0F, 8, 5, 5, 0.0F);
        this.setRotateAngle(LeftThigh, 0.136659280431156F, 0.6829471563053812F, 0.22759093446006054F);
        this.LLowerArm = new AdvancedModelRenderer(this, 27, 19);
        this.LLowerArm.mirror = true;
        this.LLowerArm.setRotationPoint(9.0F, 0.5F, 0.4F);
        this.LLowerArm.addBox(-4.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(LLowerArm, 0.0F, 0.0F, -0.18203784098300857F);
        this.Head.addChild(this.BottomJaw1);
        this.Tail1.addChild(this.Tail2);
        this.RightShin.addChild(this.Rightfoot);
        this.Shoulders.addChild(this.Body);
        this.Hips.addChild(this.Tail1);
        this.Body.addChild(this.Hips);
        this.BottomJaw1.addChild(this.BottomJaw2);
        this.RightThigh.addChild(this.RightShin);
        this.RightArm.addChild(this.RLowerArm);
        this.LLowerArm.addChild(this.LHand);
        this.Shoulders.addChild(this.RightArm);
        this.LeftThigh.addChild(this.LeftShin);
        this.Hips.addChild(this.RightThigh);
        this.Neck.addChild(this.Head);
        this.Tail2.addChild(this.Tail3);
        this.LeftShin.addChild(this.LeftFoot);
        this.Shoulders.addChild(this.LeftArm);
        this.Shoulders.addChild(this.Neck);
        this.Head.addChild(this.TopJaw);
        this.RLowerArm.addChild(this.RHand);
        this.Hips.addChild(this.LeftThigh);
        this.LeftArm.addChild(this.LLowerArm);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.Shoulders.render(f5);
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animator.update(entity);
        EntityMegalania prehistoric = (EntityMegalania) entity;
        blockMovement(f, f1, f2, f3, f4, f5, (Entity) entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, Head, -20, 0, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ANIMATION_FIGHT);
        animator.startKeyframe(10);
        standPos();
        ModelUtils.rotate(animator, Neck, 23, 36, 0);
        ModelUtils.rotate(animator, Head, 23, 10, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        standPos();
        ModelUtils.rotate(animator, Neck, 23, -36, 0);
        ModelUtils.rotate(animator, Head, 23, -10, 0);
        ModelUtils.rotate(animator, LeftArm, -30, -25, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        standPos();
        ModelUtils.rotate(animator, Neck, 23, 36, 0);
        ModelUtils.rotate(animator, Head, 23, 10, 0);
        ModelUtils.rotate(animator, RightArm, -30, 25, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        standPos();
        ModelUtils.rotate(animator, Neck, 23, -36, 0);
        ModelUtils.rotate(animator, Head, 23, -10, 0);
        ModelUtils.rotate(animator, LeftArm, -30, -25, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        standPos();
        ModelUtils.rotate(animator, Neck, 23, 36, 0);
        ModelUtils.rotate(animator, Head, 23, 10, 0);
        ModelUtils.rotate(animator, RightArm, -30, 25, 0);
        ModelUtils.rotate(animator, BottomJaw1, 15, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, Neck, -15, 0, 0);
        ModelUtils.rotate(animator, Head, -20, 0, 0);
        ModelUtils.rotate(animator, BottomJaw1, 60, 0, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, Neck, 6, 0, 0);
        ModelUtils.rotate(animator, Head, 14, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    private void standPos(){
        ModelUtils.rotate(animator, Shoulders, -60, 0, 0);
        ModelUtils.rotate(animator, Body, 7, 0, 0);
        ModelUtils.rotate(animator, Hips, 25, 0, 0);
        ModelUtils.rotate(animator, Tail1, 20, 0, 0);
        ModelUtils.rotate(animator, Tail2, 17, 0, 0);
        ModelUtils.rotate(animator, Tail3, 10, 0, 0);
        ModelUtils.rotate(animator, LeftThigh, 30, 10, 15);
        ModelUtils.rotate(animator, RightThigh, 30, -10, -15);
        ModelUtils.rotate(animator, LeftFoot, 0, 0, 15);
        ModelUtils.rotate(animator, Rightfoot, 0, 0, -15);
        ModelUtils.rotate(animator, LHand, 90, 0, 0);
        ModelUtils.rotate(animator, RHand, 90, 0, 0);
        animator.move(Hips, 0, 0, -3);
        animator.move(Tail1, 0, 0, -1.4F);
        animator.move(Tail2, 0, 0, -0.9F);
        animator.move(LHand, 0, 1, 1);
        animator.move(RHand, 0, 1, 1);
        animator.move(Shoulders, 0, -17.5F, 7);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        AdvancedModelRenderer[] tailParts = {this.Tail1, this.Tail2, this.Tail3};
        AdvancedModelRenderer[] neckParts = {this.Neck, this.Head};

        if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
            return;
        }
        float speed = 0.05F;
        float speed2 = 0.3F;
        float degree = 0.5F;
        if (!((EntityPrehistoric) entity).isSleeping()) {
            ModelUtils.faceTargetMod(Neck, f3, f4, 0.5F);
            ModelUtils.faceTargetMod(Head, f3, f4, 0.5F);
        }

        this.chainWave(tailParts, speed, degree * 0.075F, -3, f2, 1);
        this.chainSwing(tailParts, speed, degree * 0.25F, -2, f2, 1);
        this.chainWave(neckParts, speed, degree * 0.075F, 3, f2, 1);
        this.bob(Shoulders, speed, degree * 0.5F, false, f2, 1);

        this.bob(LeftArm, speed, degree * -0.5F, false, f2, 1);
        this.bob(RightArm, speed, degree * -0.5F, false, f2, 1);
        this.bob(LeftThigh, speed, degree * -0.5F, false, f2, 1);
        this.bob(RightThigh, speed, degree * -0.5F, false, f2, 1);

        this.swing(LeftArm, speed2, degree * 1.2F, true, 0F, -0.5F, f, f1);
        this.swing(RightArm, speed2, degree * 1.2F, true, 0F, 0.5F, f, f1);

        this.swing(LeftThigh, speed2, degree * 1.2F, false, 0F, -0.5F, f, f1);
        this.swing(RightThigh, speed2, degree * 1.2F, false, 0F, 0.5F, f, f1);

       this.walk(LeftArm, speed2, degree * 0.7F, false, 0F, 0F, f, f1);
       this.walk(RightArm, speed2, degree * 0.7F, true, 0F, 0F, f, f1);
       this.walk(LeftThigh, speed2, degree * 0.7F, true, 0F, 0F, f, f1);
       this.walk(RightThigh, speed2, degree * 0.7F, false, 0F, 0F, f, f1);

        this.walk(LHand, speed2, degree * 0.7F, false, 1F, 0.25F, f, f1);
        this.walk(RHand, speed2, degree * 0.7F, true, 1F, -0.25F, f, f1);
        this.flap(LeftFoot, speed2, degree * 0.7F, true, 1F, -0.15F, f, f1);
        this.flap(Rightfoot, speed2, degree * 0.7F, false, 1F, -0.15F, f, f1);
        this.bob(Shoulders, speed2, degree * 2.5F, true, f, f1);
        this.chainSwing(tailParts, speed2, degree * 0.75F, -2, f, f1);


        EntityMegalania prehistoric = (EntityMegalania) entity;
        {
            float sitProgress = prehistoric.sitProgress;
            sitAnimationRotationPrev(RightArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(8.0D)), ((float) Math.toRadians(-15.0D)));
            sitAnimationRotationPrev(LeftArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(-8.0D)), ((float) Math.toRadians(15.0D)));
            sitAnimationRotationPrev(RLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(10)));
            sitAnimationRotationPrev(LLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(-10)));
            sitAnimationRotationPrev(LHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RightThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(40.0D)), ((float) Math.toRadians(-13.0D)));
            sitAnimationRotationPrev(LeftThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(-40.0D)), ((float) Math.toRadians(13.0D)));
            sitAnimationRotationPrev(RightShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(20.0D)), ((float) Math.toRadians(90.0D)));
            sitAnimationRotationPrev(LeftShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(-20.0D)), ((float) Math.toRadians(-90.0D)));
            sitAnimationRotationPrev(Rightfoot, sitProgress,0, 0,  ((float) Math.toRadians(-80)));
            sitAnimationRotationPrev(LeftFoot, sitProgress,0, 0,  ((float) Math.toRadians(80)));
            sitAnimationRotationPrev(Tail1, sitProgress,0, 0,  0);
            sitAnimationPos(RightThigh, sitProgress, 2.1F, 0F, 0F);
            sitAnimationPos(LeftThigh, sitProgress, -2.1F, 0F, 0F);
            sitAnimationPos(Shoulders, sitProgress, 0F, 6F, 0F);
        }
        {
            float sitProgress = prehistoric.sleepProgress;
            sitAnimationRotationPrev(RightArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(8.0D)), ((float) Math.toRadians(-15.0D)));
            sitAnimationRotationPrev(LeftArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(-8.0D)), ((float) Math.toRadians(15.0D)));
            sitAnimationRotationPrev(RLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(10)));
            sitAnimationRotationPrev(LLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(-10)));
            sitAnimationRotationPrev(LHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RightThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(40.0D)), ((float) Math.toRadians(-13.0D)));
            sitAnimationRotationPrev(LeftThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(-40.0D)), ((float) Math.toRadians(13.0D)));
            sitAnimationRotationPrev(RightShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(20.0D)), ((float) Math.toRadians(90.0D)));
            sitAnimationRotationPrev(LeftShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(-20.0D)), ((float) Math.toRadians(-90.0D)));
            sitAnimationRotationPrev(Rightfoot, sitProgress,0, 0,  ((float) Math.toRadians(-80)));
            sitAnimationRotationPrev(LeftFoot, sitProgress,0, 0,  ((float) Math.toRadians(80)));
            sitAnimationRotationPrev(Neck, sitProgress, ((float) Math.toRadians(18)), 0, 0);
            sitAnimationRotationPrev(Head, sitProgress, ((float) Math.toRadians(-20)), 0, 0);

            sitAnimationRotationPrev(Tail1, sitProgress,0, 0,  0);
            sitAnimationPos(RightThigh, sitProgress, 2.1F, 0F, 0F);
            sitAnimationPos(LeftThigh, sitProgress, -2.1F, 0F, 0F);
            sitAnimationPos(Shoulders, sitProgress, 0F, 6F, 0F);
        }
        {
            float sitProgress = prehistoric.weakProgress;
            sitAnimationRotationPrev(RightArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(8.0D)), ((float) Math.toRadians(-15.0D)));
            sitAnimationRotationPrev(LeftArm, sitProgress, ((float) Math.toRadians(-31.0D)), ((float) Math.toRadians(-8.0D)), ((float) Math.toRadians(15.0D)));
            sitAnimationRotationPrev(RLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(10)));
            sitAnimationRotationPrev(LLowerArm, sitProgress, ((float) Math.toRadians(-50)), 0, ((float) Math.toRadians(-10)));
            sitAnimationRotationPrev(LHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RHand, sitProgress, ((float) Math.toRadians(78)), 0, 0);
            sitAnimationRotationPrev(RightThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(40.0D)), ((float) Math.toRadians(-13.0D)));
            sitAnimationRotationPrev(LeftThigh, sitProgress, ((float) Math.toRadians(7.0D)), ((float) Math.toRadians(-40.0D)), ((float) Math.toRadians(13.0D)));
            sitAnimationRotationPrev(RightShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(20.0D)), ((float) Math.toRadians(90.0D)));
            sitAnimationRotationPrev(LeftShin, sitProgress, ((float) Math.toRadians(15.0D)), ((float) Math.toRadians(-20.0D)), ((float) Math.toRadians(-90.0D)));
            sitAnimationRotationPrev(Rightfoot, sitProgress,0, 0,  ((float) Math.toRadians(-80)));
            sitAnimationRotationPrev(LeftFoot, sitProgress,0, 0,  ((float) Math.toRadians(80)));
            sitAnimationRotationPrev(Neck, sitProgress, ((float) Math.toRadians(18)), 0, 0);
            sitAnimationRotationPrev(Head, sitProgress, ((float) Math.toRadians(-20)), 0, 0);

            sitAnimationRotationPrev(Tail1, sitProgress,0, 0,  0);
            sitAnimationPos(RightThigh, sitProgress, 2.1F, 0F, 0F);
            sitAnimationPos(LeftThigh, sitProgress, -2.1F, 0F, 0F);
            sitAnimationPos(Shoulders, sitProgress, 0F, 6F, 0F);
        }
    }
}
