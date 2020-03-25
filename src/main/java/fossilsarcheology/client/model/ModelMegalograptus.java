package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityMegalograptus;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;

public class ModelMegalograptus extends ModelPrehistoric {
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer leftLeg3_1;
    public AdvancedModelRenderer leftLeg1;
    public AdvancedModelRenderer leftLeg2;
    public AdvancedModelRenderer rightLeg3_1;
    public AdvancedModelRenderer rightPincer;
    public AdvancedModelRenderer leftPincer;
    public AdvancedModelRenderer leftPincer1_1;
    public AdvancedModelRenderer rightPincer1_1;
    public AdvancedModelRenderer rightLeg1;
    public AdvancedModelRenderer rightLeg2;
    public AdvancedModelRenderer bodyArmor;
    public AdvancedModelRenderer tail;
    public AdvancedModelRenderer tailPincer;
    public AdvancedModelRenderer leftLeg3_2;
    public AdvancedModelRenderer rightLeg3_2;
    public AdvancedModelRenderer leftPincer1_2;
    public AdvancedModelRenderer leftPincerProngs1_1;
    public AdvancedModelRenderer leftPincerProngs1_2;
    public AdvancedModelRenderer rightPincer1_2;
    public AdvancedModelRenderer rightPincerProngs1_1;
    public AdvancedModelRenderer rightPincerProngs1_2;
    private final ModelAnimator animator;

    public ModelMegalograptus() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.rightLeg1 = new AdvancedModelRenderer(this, 26, 0);
        this.rightLeg1.mirror = true;
        this.rightLeg1.setRotationPoint(-1.0F, -0.1F, -3.0F);
        this.rightLeg1.addBox(-4.0F, 0.0F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg1, 0.0F, -0.06981317007977318F, 0.0F);
        this.rightLeg2 = new AdvancedModelRenderer(this, 32, 5);
        this.rightLeg2.mirror = true;
        this.rightLeg2.setRotationPoint(-2.0F, -0.1F, -1.9F);
        this.rightLeg2.addBox(-5.0F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, -0.017453292519943295F, 0.0F);
        this.body = new AdvancedModelRenderer(this, 0, 8);
        this.body.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.body.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 5, 0.0F);
        this.rightLeg3_1 = new AdvancedModelRenderer(this, 51, 13);
        this.rightLeg3_1.mirror = true;
        this.rightLeg3_1.setRotationPoint(-1.5F, -0.6F, -0.4F);
        this.rightLeg3_1.addBox(-3.0F, 0.0F, -1.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(rightLeg3_1, 0.0F, 0.24434609527920614F, -6.981317007977319E-4F);
        this.leftPincerProngs1_2 = new AdvancedModelRenderer(this, 25, 24);
        this.leftPincerProngs1_2.setRotationPoint(2.9F, 0.5F, 0.0F);
        this.leftPincerProngs1_2.addBox(-2.0F, 0.0F, -3.0F, 4, 0, 3, 0.0F);
        this.rightPincer = new AdvancedModelRenderer(this, 24, 3);
        this.rightPincer.setRotationPoint(-0.3F, 0.0F, -4.0F);
        this.rightPincer.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(rightPincer, 0.0F, 2.007128639793479F, 0.0F);
        this.leftLeg3_2 = new AdvancedModelRenderer(this, 36, 18);
        this.leftLeg3_2.setRotationPoint(2.7F, -0.03F, -0.1F);
        this.leftLeg3_2.addBox(0.0F, 0.0F, -1.0F, 5, 1, 2, 0.0F);
        this.setRotateAngle(leftLeg3_2, 0.0F, -0.2617993877991494F, 0.0F);
        this.rightPincerProngs1_1 = new AdvancedModelRenderer(this, 33, 28);
        this.rightPincerProngs1_1.mirror = true;
        this.rightPincerProngs1_1.setRotationPoint(-3.5F, 0.4F, -1.0F);
        this.rightPincerProngs1_1.addBox(-1.5F, 0.0F, -4.0F, 3, 0, 4, 0.0F);
        this.leftLeg3_1 = new AdvancedModelRenderer(this, 51, 18);
        this.leftLeg3_1.setRotationPoint(1.5F, -0.6F, -0.4F);
        this.leftLeg3_1.addBox(0.0F, 0.0F, -1.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(leftLeg3_1, 0.0F, -0.24434609527920614F, 6.981317007977319E-4F);
        this.leftPincer1_2 = new AdvancedModelRenderer(this, 15, 27);
        this.leftPincer1_2.setRotationPoint(4.8F, 0.02F, -0.3F);
        this.leftPincer1_2.addBox(0.0F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.setRotateAngle(leftPincer1_2, 0.0F, 0.4363323129985824F, 0.0F);
        this.leftLeg1 = new AdvancedModelRenderer(this, 15, 0);
        this.leftLeg1.setRotationPoint(1.0F, -0.1F, -3.0F);
        this.leftLeg1.addBox(0.0F, 0.0F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg1, 0.0F, 0.06981317007977318F, 0.0F);
        this.rightPincer1_1 = new AdvancedModelRenderer(this, 0, 29);
        this.rightPincer1_1.mirror = true;
        this.rightPincer1_1.setRotationPoint(-1.0F, -0.02F, -4.0F);
        this.rightPincer1_1.addBox(-5.0F, 0.0F, -1.0F, 5, 1, 2, 0.0F);
        this.setRotateAngle(rightPincer1_1, 0.0F, -0.47123889803846897F, 0.0F);
        this.bodyArmor = new AdvancedModelRenderer(this, 16, 8);
        this.bodyArmor.setRotationPoint(0.0F, 0.5F, 0.02F);
        this.bodyArmor.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 5, 0.0F);
        this.leftLeg2 = new AdvancedModelRenderer(this, 19, 5);
        this.leftLeg2.setRotationPoint(2.0F, -0.1F, -1.9F);
        this.leftLeg2.addBox(0.0F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, 0.017453292519943295F, 0.0F);
        this.head = new AdvancedModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 23.0F, -2.1F);
        this.head.addBox(-2.0F, -1.0F, -5.0F, 4, 2, 5, 0.0F);
        this.leftPincer1_1 = new AdvancedModelRenderer(this, 0, 26);
        this.leftPincer1_1.setRotationPoint(1.0F, -0.02F, -4.0F);
        this.leftPincer1_1.addBox(0.0F, 0.0F, -1.0F, 5, 1, 2, 0.0F);
        this.setRotateAngle(leftPincer1_1, 0.0F, 0.47123889803846897F, 0.0F);
        this.leftPincer = new AdvancedModelRenderer(this, 15, 3);
        this.leftPincer.setRotationPoint(0.3F, 0.0F, -4.0F);
        this.leftPincer.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(leftPincer, 0.0F, 1.0471975511965976F, 0.0F);
        this.rightLeg3_2 = new AdvancedModelRenderer(this, 36, 13);
        this.rightLeg3_2.mirror = true;
        this.rightLeg3_2.setRotationPoint(-2.7F, -0.03F, -0.1F);
        this.rightLeg3_2.addBox(-5.0F, 0.0F, -1.0F, 5, 1, 2, 0.0F);
        this.setRotateAngle(rightLeg3_2, 0.0F, 0.2617993877991494F, 0.0F);
        this.rightPincer1_2 = new AdvancedModelRenderer(this, 15, 30);
        this.rightPincer1_2.mirror = true;
        this.rightPincer1_2.setRotationPoint(-4.8F, 0.02F, -0.3F);
        this.rightPincer1_2.addBox(-5.0F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.setRotateAngle(rightPincer1_2, 0.0F, -0.4363323129985824F, 0.0F);
        this.leftPincerProngs1_1 = new AdvancedModelRenderer(this, 33, 23);
        this.leftPincerProngs1_1.setRotationPoint(3.5F, 0.4F, -1.0F);
        this.leftPincerProngs1_1.addBox(-1.5F, 0.0F, -4.0F, 3, 0, 4, 0.0F);
        this.tail = new AdvancedModelRenderer(this, 0, 15);
        this.tail.setRotationPoint(0.0F, 0.1F, 4.8F);
        this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 7, 0.0F);
        this.rightPincerProngs1_2 = new AdvancedModelRenderer(this, 25, 29);
        this.rightPincerProngs1_2.mirror = true;
        this.rightPincerProngs1_2.setRotationPoint(-2.9F, 0.5F, 0.0F);
        this.rightPincerProngs1_2.addBox(-2.0F, 0.0F, -3.0F, 4, 0, 3, 0.0F);
        this.tailPincer = new AdvancedModelRenderer(this, 38, 0);
        this.tailPincer.setRotationPoint(0.0F, 0.5F, 6.5F);
        this.tailPincer.addBox(-2.5F, 0.0F, 0.0F, 5, 0, 5, 0.0F);
        this.head.addChild(this.rightLeg1);
        this.head.addChild(this.rightLeg2);
        this.head.addChild(this.body);
        this.head.addChild(this.rightLeg3_1);
        this.leftPincer1_2.addChild(this.leftPincerProngs1_2);
        this.head.addChild(this.rightPincer);
        this.leftLeg3_1.addChild(this.leftLeg3_2);
        this.rightPincer1_1.addChild(this.rightPincerProngs1_1);
        this.head.addChild(this.leftLeg3_1);
        this.leftPincer1_1.addChild(this.leftPincer1_2);
        this.head.addChild(this.leftLeg1);
        this.head.addChild(this.rightPincer1_1);
        this.body.addChild(this.bodyArmor);
        this.head.addChild(this.leftLeg2);
        this.head.addChild(this.leftPincer1_1);
        this.head.addChild(this.leftPincer);
        this.rightLeg3_1.addChild(this.rightLeg3_2);
        this.rightPincer1_1.addChild(this.rightPincer1_2);
        this.leftPincer1_1.addChild(this.leftPincerProngs1_1);
        this.body.addChild(this.tail);
        this.rightPincer1_2.addChild(this.rightPincerProngs1_2);
        this.tail.addChild(this.tailPincer);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.head.render(f5);
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityMegalograptus prehistoric = (EntityMegalograptus) entity;
        animator.update(entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, rightPincer1_1, 0, 34, 0);
        ModelUtils.rotate(animator, leftPincer1_1, 0, -34, 0);
        ModelUtils.rotate(animator, rightPincer, 0, 35, 0);
        ModelUtils.rotate(animator, leftPincer, 0, -35, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, rightPincer1_1, 0, -40, 0);
        ModelUtils.rotate(animator, leftPincer1_1, 0, 40, 0);
        ModelUtils.rotate(animator, rightPincer, 0, 5, 0);
        ModelUtils.rotate(animator, leftPincer, 0, -5, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        AdvancedModelRenderer[] tailParts = {body, tail, tailPincer};
        AdvancedModelRenderer[] leftLegs = {leftLeg1, leftLeg2, leftLeg3_1, leftLeg3_2};
        AdvancedModelRenderer[] rightLegs = {rightLeg1, rightLeg2, rightLeg3_1, rightLeg3_2};
        if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
            return;
        }
        EntityMegalograptus prehistoric = (EntityMegalograptus)entity;
        float speed_swim = 0.9F;
        float speed_walk = 1.7F;
        float speed_idle = prehistoric.isSleeping() ? 0.2F : 0.4F;
        float degree_idle = prehistoric.isSleeping() ? 0.2F : 0.7F;
        float degree_swim = 0.5F;
        float degree_walk = 0.75F;
        if(prehistoric.swimProgress > 0) {
            this.chainWave(tailParts, speed_swim, degree_swim * 0.35F, 0, f, f1);
            this.walk(head, speed_swim, degree_swim * 0.15F, false, 0, 0, f, f1);
            this.chainFlap(leftLegs, speed_swim, degree_swim * 0.35F, 3, f, f1);
            this.chainFlap(rightLegs, speed_swim, degree_swim * -0.35F, 3, f, f1);
        }else{
            this.chainSwing(leftLegs, speed_walk, degree_walk * 0.35F, 3, f, f1);
            this.chainSwing(rightLegs, speed_walk, degree_walk * -0.35F, 3, f, f1);

        }
        this.swing(leftPincer1_1, speed_idle, degree_idle * 0.15F, false, 0, 0, f2, 1);
        this.swing(rightPincer1_1, speed_idle, degree_idle * 0.15F, true, 0, 0, f2, 1);
        this.swing(leftPincer, speed_idle, degree_idle * 0.15F, true, 1, 0, f2, 1);
        this.swing(rightPincer, speed_idle, degree_idle * 0.15F, false, 1, 0, f2, 1);
        this.chainSwing(tailParts, speed_idle * 0.5F, degree_idle * 0.15F, 1, f2, 1);
        float sleepProgress = prehistoric.sleepProgress;
        this.sitAnimationRotation(tail, sleepProgress, 0, (float)Math.toRadians(16), 0);
        this.sitAnimationRotation(tail, sleepProgress, 0, (float)Math.toRadians(21), 0);
        this.sitAnimationRotation(rightLeg3_1, sleepProgress, 0, (float)Math.toRadians(41), 0);
        this.sitAnimationRotation(leftLeg3_1, sleepProgress, 0, (float)Math.toRadians(-41), 0);
        this.sitAnimationRotation(rightPincer1_1, sleepProgress, 0, (float)Math.toRadians(-55), 0);
        this.sitAnimationRotation(leftPincer1_1, sleepProgress, 0, (float)Math.toRadians(55), 0);

    }
}
