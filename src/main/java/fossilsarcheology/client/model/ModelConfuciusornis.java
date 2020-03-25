package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricFlying;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelConfuciusornis extends ModelPrehistoric {
	public final AdvancedModelRenderer rightLeg;
	public final AdvancedModelRenderer rightFoot;
	public final AdvancedModelRenderer leftLeg;
	public final AdvancedModelRenderer body;
	public final AdvancedModelRenderer leftFoot;
	public final AdvancedModelRenderer RightWing;
	public final AdvancedModelRenderer tailFeathers;
	public final AdvancedModelRenderer neck;
	public final AdvancedModelRenderer leftWing;
	public final AdvancedModelRenderer RightWing2;
	public final AdvancedModelRenderer RightWing3;
	public final AdvancedModelRenderer head;
	public final AdvancedModelRenderer headFeathers;
	public final AdvancedModelRenderer upperBeak;
	public final AdvancedModelRenderer lowerBeak;
	public final AdvancedModelRenderer leftTail2;
	public final AdvancedModelRenderer leftTail3;
	public final AdvancedModelRenderer leftTailFan;
	public final AdvancedModelRenderer rightTail2;
	public final AdvancedModelRenderer rightTail3;
	public final AdvancedModelRenderer rightTailFan;
	public final AdvancedModelRenderer leftWing2;
	public final AdvancedModelRenderer leftWing3;
	public AdvancedModelRenderer HeadPivot;
	private final ModelAnimator animator;

	public ModelConfuciusornis() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.RightWing3 = new AdvancedModelRenderer(this, 50, 15);
		this.RightWing3.setRotationPoint(0.0F, 5.1F, 0.0F);
		this.RightWing3.addBox(-0.8F, 1.5F, 0.0F, 1, 6, 3, 0.0F);
		this.setRotateAngle(RightWing3, 0.05235987755982988F, 0.0F, 3.141592653589793F);
		this.body = new AdvancedModelRenderer(this, 0, 7);
		this.body.setRotationPoint(0.0F, 19.2F, 0.5F);
		this.body.addBox(-2.0F, -2.0F, -7.0F, 4, 4, 8, 0.0F);
		this.setRotateAngle(body, -0.2638937829015426F, 0.0F, 0.0F);
		this.rightTail2 = new AdvancedModelRenderer(this, 0, 25);
		this.rightTail2.setRotationPoint(-1.51F, 2.0F, 1.2F);
		this.rightTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(rightTail2, -1.48352986419518F, -0.0F, 0.0F);
		this.leftLeg = new AdvancedModelRenderer(this, 16, 6);
		this.leftLeg.setRotationPoint(1.5F, 21.0F, -1.0F);
		this.leftLeg.addBox(-0.4F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(leftLeg, 0.0F, -0.0F, 0.017453292519943295F);
		this.head = new AdvancedModelRenderer(this, 25, 1);
		this.head.setRotationPoint(0.0F, -1.5F, -0.5F);
		this.head.addBox(-1.5F, -3.0F, -2.0F, 3, 3, 3, 0.0F);
		this.setRotateAngle(head, -0.3141592653589793F, -0.0F, 0.0F);
		this.rightFoot = new AdvancedModelRenderer(this, 22, 12);
		this.rightFoot.setRotationPoint(0.0F, 2.8F, 0.5F);
		this.rightFoot.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 2, 0.0F);
		this.leftTail2 = new AdvancedModelRenderer(this, 0, 25);
		this.leftTail2.setRotationPoint(1.51F, 2.0F, 1.2F);
		this.leftTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.setRotateAngle(leftTail2, -1.48352986419518F, 0.0F, 0.0F);
		this.tailFeathers = new AdvancedModelRenderer(this, 15, 20);
		this.tailFeathers.setRotationPoint(0.0F, -0.5F, 0.4F);
		this.tailFeathers.addBox(-2.0F, 0.0F, 0.2F, 4, 4, 1, 0.0F);
		this.setRotateAngle(tailFeathers, 1.3658946726107624F, 0.0F, 0.0F);
		this.leftWing3 = new AdvancedModelRenderer(this, 50, 15);
		this.leftWing3.mirror = true;
		this.leftWing3.setRotationPoint(0.0F, 5.1F, 0.0F);
		this.leftWing3.addBox(-0.2F, 1.5F, 0.0F, 1, 6, 3, 0.0F);
		this.setRotateAngle(leftWing3, 0.03490658503988659F, 0.0F, 3.141592653589793F);
		this.upperBeak = new AdvancedModelRenderer(this, 49, 4);
		this.upperBeak.setRotationPoint(0.0F, -1.0F, -0.8F);
		this.upperBeak.addBox(-1.0F, -1.0F, -3.5F, 2, 1, 3, 0.0F);
		this.setRotateAngle(upperBeak, 0.14870205226991687F, -0.0F, 0.0F);
		this.rightLeg = new AdvancedModelRenderer(this, 16, 6);
		this.rightLeg.setRotationPoint(-1.5F, 21.0F, -1.0F);
		this.rightLeg.addBox(-0.6F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rightLeg, 0.0F, -0.0F, 0.017453292519943295F);
		this.leftTailFan = new AdvancedModelRenderer(this, 18, 29);
		this.leftTailFan.setRotationPoint(0.5F, 0.1F, 6.2F);
		this.leftTailFan.addBox(-1.0F, -0.1F, 2.8F, 2, 1, 3, 0.0F);
		this.setRotateAngle(leftTailFan, 0.017453292519943295F, -0.0F, 0.0F);
		this.rightTailFan = new AdvancedModelRenderer(this, 18, 29);
		this.rightTailFan.mirror = true;
		this.rightTailFan.setRotationPoint(-0.5F, 0.1F, 6.2F);
		this.rightTailFan.addBox(-1.0F, -0.1F, 2.8F, 2, 1, 3, 0.0F);
		this.setRotateAngle(rightTailFan, 0.017453292519943295F, -0.0F, 0.0F);
		this.neck = new AdvancedModelRenderer(this, 15, 1);
		this.neck.setRotationPoint(0.0F, -0.5F, -6.5F);
		this.neck.addBox(-1.0F, -2.5F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(neck, 0.5846852994181004F, -0.0F, 0.0F);
		this.lowerBeak = new AdvancedModelRenderer(this, 38, 4);
		this.lowerBeak.setRotationPoint(0.0F, -0.9F, -0.8F);
		this.lowerBeak.addBox(-1.0F, 0.0F, -3.5F, 2, 1, 3, 0.0F);
		this.leftTail3 = new AdvancedModelRenderer(this, 0, 20);
		this.leftTail3.setRotationPoint(0.0F, 0.0F, 2.9F);
		this.leftTail3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 9, 0.0F);
		this.setRotateAngle(leftTail3, 0.17453292519943295F, -0.0F, 0.0F);
		this.RightWing2 = new AdvancedModelRenderer(this, 27, 15);
		this.RightWing2.setRotationPoint(0.0F, 3.0F, -0.5F);
		this.RightWing2.addBox(-0.9F, 0.5F, -0.5F, 1, 6, 4, 0.0F);
		this.setRotateAngle(RightWing2, 0.13962634015954636F, 0.0F, 0.0F);
		this.headFeathers = new AdvancedModelRenderer(this, 35, 0);
		this.headFeathers.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.headFeathers.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(headFeathers, -1.1897909510845344F, -0.0F, 0.0F);
		this.leftFoot = new AdvancedModelRenderer(this, 22, 12);
		this.leftFoot.mirror = true;
		this.leftFoot.setRotationPoint(0.0F, 2.8F, 0.5F);
		this.leftFoot.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 2, 0.0F);
		this.rightTail3 = new AdvancedModelRenderer(this, 0, 20);
		this.rightTail3.setRotationPoint(0.0F, 0.0F, 2.9F);
		this.rightTail3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 9, 0.0F);
		this.setRotateAngle(rightTail3, 0.17453292519943295F, 0.0F, 0.0F);
		this.leftWing = new AdvancedModelRenderer(this, 38, 16);
		this.leftWing.mirror = true;
		this.leftWing.setRotationPoint(2.0F, 0.9F, -5.9F);
		this.leftWing.addBox(0.0F, -1.0F, -1.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(leftWing, 1.3613568165555772F, 0.0F, 0.0F);
		this.RightWing = new AdvancedModelRenderer(this, 38, 16);
		this.RightWing.setRotationPoint(-2.0F, 0.9F, -5.9F);
		this.RightWing.addBox(-1.0F, -1.0F, -1.0F, 1, 5, 4, 0.0F);
		this.setRotateAngle(RightWing, 1.3613568165555772F, 0.0F, 0.0F);
		this.leftWing2 = new AdvancedModelRenderer(this, 27, 15);
		this.leftWing2.mirror = true;
		this.leftWing2.setRotationPoint(0.0F, 3.0F, -0.5F);
		this.leftWing2.addBox(-0.1F, 0.5F, -0.5F, 1, 6, 4, 0.0F);
		this.setRotateAngle(leftWing2, 0.13962634015954636F, 0.0F, 0.0F);
		this.RightWing2.addChild(this.RightWing3);
		this.tailFeathers.addChild(this.rightTail2);
		this.neck.addChild(this.head);
		this.rightLeg.addChild(this.rightFoot);
		this.tailFeathers.addChild(this.leftTail2);
		this.body.addChild(this.tailFeathers);
		this.leftWing2.addChild(this.leftWing3);
		this.head.addChild(this.upperBeak);
		this.leftTail3.addChild(this.leftTailFan);
		this.rightTail3.addChild(this.rightTailFan);
		this.body.addChild(this.neck);
		this.head.addChild(this.lowerBeak);
		this.leftTail2.addChild(this.leftTail3);
		this.RightWing.addChild(this.RightWing2);
		this.head.addChild(this.headFeathers);
		this.leftLeg.addChild(this.leftFoot);
		this.rightTail2.addChild(this.rightTail3);
		this.body.addChild(this.leftWing);
		this.body.addChild(this.RightWing);
		this.leftWing.addChild(this.leftWing2);
		this.updateDefaultPose();
		animator = ModelAnimator.create();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
		this.rightLeg.render(f5);
		this.leftLeg.render(f5);
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
		ModelUtils.rotate(animator, lowerBeak, 29, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(10);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		AdvancedModelRenderer[] neckParts = {this.neck, this.head};
		AdvancedModelRenderer[] rightWingParts = {this.RightWing, this.RightWing2, this.RightWing3};
		AdvancedModelRenderer[] leftWingParts = {this.leftWing, this.leftWing2, this.leftWing3};
		AdvancedModelRenderer[] leftTailParts = {this.leftTail2, this.leftTail3, this.leftTailFan};
		AdvancedModelRenderer[] rightTailParts = {this.rightTail2, this.rightTail3, this.rightTailFan};
		if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
			return;
		}
		ModelUtils.faceTargetMod(neck, f3, f4, 0.5F);
		ModelUtils.faceTargetMod(head, f3, f4, 0.5F);
		float speed = 0.1F;
		float speed2 = 0.6F;
		float speed3 = 0.2F;
		this.bob(body, speed, -0.3F, false, f2, 1);
		this.chainWave(neckParts, speed, 0.15F, 3, f2, 1);
		{
			float sitProgress = ((EntityPrehistoric) entity).sitProgress;
			sitAnimationRotationPrev(leftLeg, sitProgress, 0, 0, (float) Math.toRadians(1.0D));
			sitAnimationRotationPrev(leftWing2, sitProgress, (float) Math.toRadians(93.91D), 0, 0);
			sitAnimationRotationPrev(RightWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, 0);
			sitAnimationRotationPrev(leftWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, 0);
			sitAnimationRotationPrev(RightWing2, sitProgress, (float) Math.toRadians(93.91D), 0, 0);
			sitAnimationRotationPrev(leftTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(upperBeak, sitProgress, (float) Math.toRadians(8.52D), 0, 0);
			sitAnimationRotationPrev(neck, sitProgress, (float) Math.toRadians(33.5D), 0, 0);
			sitAnimationRotationPrev(headFeathers, sitProgress, -((float) Math.toRadians(68.17D)), 0, 0);
			sitAnimationRotationPrev(rightTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(body, sitProgress, -((float) Math.toRadians(7.83D)), 0, 0);
			sitAnimationRotationPrev(tailFeathers, sitProgress, (float) Math.toRadians(78.26D), 0, 0);
			sitAnimationRotationPrev(rightTail3, sitProgress, (float) Math.toRadians(5.0D), 0, 0);
			sitAnimationRotationPrev(leftWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
			sitAnimationRotationPrev(head, sitProgress, -((float) Math.toRadians(18.0D)), 0, 0);
			sitAnimationRotationPrev(leftTail3, sitProgress, (float) Math.toRadians(5.0D), 0, 0);
			sitAnimationRotationPrev(rightLeg, sitProgress, 0, 0, (float) Math.toRadians(1.0D));
			sitAnimationRotationPrev(RightWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
		}
		{
			float sitProgress = ((EntityPrehistoric) entity).sleepProgress;
			sitAnimationRotationPrev(tailFeathers, sitProgress, (float) Math.toRadians(78.26D), 0, 0);
			sitAnimationRotationPrev(neck, sitProgress, (float) Math.toRadians(62.61D), (float) Math.toRadians(15.65D), (float) Math.toRadians(2.61D));
			sitAnimationRotationPrev(RightWing2, sitProgress, (float) Math.toRadians(93.91D), 0, 0);
			sitAnimationRotationPrev(leftWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
			sitAnimationRotationPrev(leftTail3, sitProgress, (float) Math.toRadians(5.0D), 0, 0);
			sitAnimationRotationPrev(leftTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(rightTail3, sitProgress, (float) Math.toRadians(5.0D), 0, 0);
			sitAnimationRotationPrev(body, sitProgress, -((float) Math.toRadians(7.83D)), 0, 0);
			sitAnimationRotationPrev(upperBeak, sitProgress, (float) Math.toRadians(8.52D), 0, 0);
			sitAnimationRotationPrev(rightTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(headFeathers, sitProgress, -((float) Math.toRadians(68.17D)), 0, 0);
			sitAnimationRotationPrev(RightWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, (float) Math.toRadians(7.83D));
			sitAnimationRotationPrev(leftLeg, sitProgress, 0, 0, (float) Math.toRadians(1.0D));
			sitAnimationRotationPrev(leftWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, -((float) Math.toRadians(15.65D)));
			sitAnimationRotationPrev(RightWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
			sitAnimationRotationPrev(leftWing2, sitProgress, (float) Math.toRadians(93.91D), 0, 0);
			sitAnimationRotationPrev(rightLeg, sitProgress, 0, 0, (float) Math.toRadians(1.0D));
			sitAnimationRotationPrev(head, sitProgress, -((float) Math.toRadians(18.0D)), -((float) Math.toRadians(52.17D)), (float) Math.toRadians(78.26D));
		}
		if(((EntityPrehistoricFlying) entity).isFlying()){
			float sitProgress = ((EntityPrehistoricFlying) entity).flyProgress;
			sitAnimationRotationPrev(rightTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(RightWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, (float) Math.toRadians(90.0D));
			sitAnimationRotationPrev(neck, sitProgress, (float) Math.toRadians(80.0D), 0, 0);
			sitAnimationRotationPrev(leftWing, sitProgress, -((float) Math.toRadians(8.0D)), 0, -((float) Math.toRadians(90.0D)));
			sitAnimationRotationPrev(tailFeathers, sitProgress, (float) Math.toRadians(78.26D), 0, 0);
			sitAnimationRotationPrev(upperBeak, sitProgress, (float) Math.toRadians(8.52D), 0, 0);
			sitAnimationRotationPrev(head, sitProgress, -((float) Math.toRadians(70.0D)), 0, 0);
			sitAnimationRotationPrev(leftTailFan, sitProgress, (float) Math.toRadians(1.0D), 0, 0);
			sitAnimationRotationPrev(headFeathers, sitProgress, -((float) Math.toRadians(68.17D)), 0, 0);
			sitAnimationRotationMinDistance(RightWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
			sitAnimationRotationPrev(leftLeg, sitProgress, (float) Math.toRadians(65.0D), 0, (float) Math.toRadians(1.0D));
			sitAnimationRotationPrev(leftWing2, sitProgress, (float) Math.toRadians(8.0D), 0, 0);
			sitAnimationRotationPrev(RightWing2, sitProgress, (float) Math.toRadians(8.0D), 0, 0);
			sitAnimationRotationPrev(leftWing3, sitProgress, (float) Math.toRadians(5.22D), 0, 0);
			sitAnimationRotationPrev(rightLeg, sitProgress, (float) Math.toRadians(65.0D), 0, (float) Math.toRadians(1.0D));
			if (sitProgress >= 10) {
				this.chainFlap(rightWingParts, speed3, 0.6F, 0, f, f1);
				this.chainFlap(leftWingParts, speed3, -0.6F, 0, f, f1);
				this.swing(RightWing, speed3, 0.3F, false, 0F, 0F, f, f1);
				this.swing(leftWing, speed3, 0.3F, true, 0F, 0F, f, f1);
			} else {
				this.walk(leftLeg, speed2, 1.9F, false, 0F, 0F, f, f1);
				this.walk(rightLeg, speed2, 1.9F, true, 0F, 0F, f, f1);
				this.walk(leftFoot, speed2, 1.9F, true, 0.3F, 0F, f, f1);
				this.walk(rightFoot, speed2, 1.9F, false, 0.3F, 0F, f, f1);
				this.chainWave(rightTailParts, speed2, 0.05F, -3, f, f1);
				this.chainSwing(rightTailParts, speed2, 0.15F, -3, f, f1);
				this.chainWave(leftTailParts, speed2, 0.05F, -3, f, f1);
				this.chainSwing(leftTailParts, speed2, 0.15F, -3, f, f1);
				this.chainWave(neckParts, speed2, 0.1F, -3, f, f1);
				this.chainWave(neckParts, speed, 0.4F, -3, f, f1);
			}
		}
		((EntityPrehistoric) entity).chainBuffer.applyChainSwingBuffer((ModelRenderer[]) rightTailParts);
		((EntityPrehistoric) entity).chainBuffer.applyChainSwingBuffer((ModelRenderer[]) leftTailParts);
	}
}
