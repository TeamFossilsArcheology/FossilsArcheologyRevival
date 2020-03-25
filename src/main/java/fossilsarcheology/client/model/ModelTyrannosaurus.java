package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.EntityTyrannosaurus;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTyrannosaurus extends ModelPrehistoric {
	public final AdvancedModelRenderer lowerBody;
	public final AdvancedModelRenderer rightThigh;
	public final AdvancedModelRenderer leftThigh;
	public final AdvancedModelRenderer tail1;
	public final AdvancedModelRenderer upperBody;
	public final AdvancedModelRenderer tail2;
	public final AdvancedModelRenderer tail3;
	public final AdvancedModelRenderer tail4;
	public final AdvancedModelRenderer neck;
	public final AdvancedModelRenderer leftUpperArm;
	public final AdvancedModelRenderer rightUpperArm;
	public final AdvancedModelRenderer head;
	public final AdvancedModelRenderer headPivot;
	public final AdvancedModelRenderer upperJaw;
	public final AdvancedModelRenderer lowerJaw;
	public final AdvancedModelRenderer leftLowerArm;
	public final AdvancedModelRenderer rightLowerArm;
	public final AdvancedModelRenderer rightLeg;
	public final AdvancedModelRenderer rightFoot;
	public final AdvancedModelRenderer leftLeg;
	public final AdvancedModelRenderer leftFoot;
	private final ModelAnimator animator;

	public ModelTyrannosaurus() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.rightLowerArm = new AdvancedModelRenderer(this, 34, 33);
		this.rightLowerArm.mirror = true;
		this.rightLowerArm.setRotationPoint(-1.01F, 2.0F, -0.8F);
		this.rightLowerArm.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(rightLowerArm, -0.5235987755982988F, -0.0F, 0.0F);

		this.head = new AdvancedModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-3.0F, -3.0F, -6.0F, 6, 7, 6, 0.0F);
		this.setRotateAngle(head, 0.8213519459885316F, -0.0F, 0.0F);

		this.headPivot = new AdvancedModelRenderer(this, 0, 0);
		this.headPivot.setRotationPoint(0.0F, -0.9F, -6.7F);

		this.upperBody = new AdvancedModelRenderer(this, 85, 0);
		this.upperBody.setRotationPoint(0.0F, 3.9F, 2.5F);
		this.upperBody.addBox(-4.0F, -4.0F, -10.0F, 8, 10, 10, 0.0F);
		this.setRotateAngle(upperBody, 0.09564404300928926F, -0.0F, 0.0F);
		this.rightUpperArm = new AdvancedModelRenderer(this, 35, 26);
		this.rightUpperArm.mirror = true;
		this.rightUpperArm.setRotationPoint(-3.0F, 2.3F, -9.0F);
		this.rightUpperArm.addBox(-2.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(rightUpperArm, 0.5323254218582705F, -0.0F, 0.0F);
		this.upperJaw = new AdvancedModelRenderer(this, 28, 0);
		this.upperJaw.setRotationPoint(0.0F, -0.5F, -5.7F);
		this.upperJaw.addBox(-2.0F, -2.0F, -6.0F, 4, 4, 6, 0.0F);
		this.setRotateAngle(upperJaw, 0.03490658503988659F, -0.0F, 0.0F);
		this.leftThigh = new AdvancedModelRenderer(this, 2, 17);
		this.leftThigh.setRotationPoint(3.0F, 4.7F, 7.5F);
		this.leftThigh.addBox(0.0F, -3.0F, -3.0F, 3, 10, 6, 0.0F);
		this.setRotateAngle(leftThigh, 0.03490658503988659F, -0.0F, 0.0F);
		this.leftUpperArm = new AdvancedModelRenderer(this, 35, 26);
		this.leftUpperArm.setRotationPoint(3.0F, 2.3F, -9.0F);
		this.leftUpperArm.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(leftUpperArm, 0.5323254218582705F, -0.0F, 0.0F);
		this.rightFoot = new AdvancedModelRenderer(this, 3, 45);
		this.rightFoot.mirror = true;
		this.rightFoot.setRotationPoint(0.5F, 8.0F, 1.0F);
		this.rightFoot.addBox(-1.5F, 0.0F, -3.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(rightFoot, 0.33161255787892263F, -0.0F, 0.0F);
		this.tail3 = new AdvancedModelRenderer(this, 74, 49);
		this.tail3.setRotationPoint(0.0F, 2.3F, 7.5F);
		this.tail3.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 9, 0.0F);
		this.setRotateAngle(tail3, -0.03490658503988659F, 0.0F, 0.0F);
		this.rightThigh = new AdvancedModelRenderer(this, 2, 17);
		this.rightThigh.mirror = true;
		this.rightThigh.setRotationPoint(-3.0F, 4.7F, 7.5F);
		this.rightThigh.addBox(-3.0F, -3.0F, -3.0F, 3, 10, 6, 0.0F);
		this.setRotateAngle(rightThigh, 0.03490658503988659F, -0.0F, 0.0F);
		this.lowerBody = new AdvancedModelRenderer(this, 57, 20);
		this.lowerBody.setRotationPoint(0.0F, 4.6F, -1.0F);
		this.lowerBody.addBox(-3.5F, 0.0F, 0.0F, 7, 11, 12, 0.0F);
		this.setRotateAngle(lowerBody, -0.03490658503988659F, -0.0F, 0.0F);
		this.tail4 = new AdvancedModelRenderer(this, 103, 49);
		this.tail4.setRotationPoint(0.0F, -0.8F, 8.0F);
		this.tail4.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
		this.setRotateAngle(tail4, -0.06981317007977318F, -0.0F, 0.0F);
		this.neck = new AdvancedModelRenderer(this, 52, 0);
		this.neck.setRotationPoint(0.0F, -0.1F, -7.5F);
		this.neck.addBox(-2.5F, -3.0F, -9.0F, 5, 7, 9, 0.0F);
		this.setRotateAngle(neck, -0.41887902047863906F, -0.0F, 0.0F);
		this.tail1 = new AdvancedModelRenderer(this, 15, 47);
		this.tail1.setRotationPoint(0.0F, 0.2F, 11.0F);
		this.tail1.addBox(-2.5F, 0.0F, 0.0F, 5, 8, 8, 0.0F);
		this.setRotateAngle(tail1, -0.03490658503988659F, -0.0F, 0.0F);
		this.tail2 = new AdvancedModelRenderer(this, 45, 48);
		this.tail2.setRotationPoint(0.0F, 0.3F, 7.5F);
		this.tail2.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 8, 0.0F);
		this.setRotateAngle(tail2, 0.07208209810736581F, -0.0F, 0.0F);
		this.leftFoot = new AdvancedModelRenderer(this, 3, 45);
		this.leftFoot.setRotationPoint(0.0F, 8.0F, 1.0F);
		this.leftFoot.addBox(-1.5F, 0.0F, -3.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(leftFoot, 0.33161255787892263F, -0.0F, 0.0F);
		this.lowerJaw = new AdvancedModelRenderer(this, 27, 12);
		this.lowerJaw.setRotationPoint(0.0F, 1.5F, -6.0F);
		this.lowerJaw.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(lowerJaw, 0.02199114857512855F, -0.0F, 0.0F);
		this.leftLeg = new AdvancedModelRenderer(this, 98, 31);
		this.leftLeg.setRotationPoint(1.5F, 4.5F, 0.5F);
		this.leftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
		this.setRotateAngle(leftLeg, -0.3270747018237373F, -0.0F, 0.0F);
		this.leftLowerArm = new AdvancedModelRenderer(this, 34, 33);
		this.leftLowerArm.setRotationPoint(1.01F, 2.0F, -0.8F);
		this.leftLowerArm.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(leftLowerArm, -0.5061454830783556F, -0.0F, 0.0F);
		this.rightLeg = new AdvancedModelRenderer(this, 98, 31);
		this.rightLeg.mirror = true;
		this.rightLeg.setRotationPoint(-2.0F, 4.5F, 0.5F);
		this.rightLeg.addBox(-0.5F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
		this.setRotateAngle(rightLeg, -0.3270747018237373F, -0.0F, 0.0F);
		this.rightUpperArm.addChild(this.rightLowerArm);
		this.neck.addChild(this.headPivot);
		this.headPivot.addChild(this.head);
		this.lowerBody.addChild(this.upperBody);
		this.upperBody.addChild(this.rightUpperArm);
		this.head.addChild(this.upperJaw);
		this.lowerBody.addChild(this.leftThigh);
		this.upperBody.addChild(this.leftUpperArm);
		this.rightLeg.addChild(this.rightFoot);
		this.tail2.addChild(this.tail3);
		this.lowerBody.addChild(this.rightThigh);
		this.tail3.addChild(this.tail4);
		this.upperBody.addChild(this.neck);
		this.lowerBody.addChild(this.tail1);
		this.tail1.addChild(this.tail2);
		this.leftLeg.addChild(this.leftFoot);
		this.head.addChild(this.lowerJaw);
		this.leftThigh.addChild(this.leftLeg);
		this.leftUpperArm.addChild(this.leftLowerArm);
		this.rightThigh.addChild(this.rightLeg);
		this.updateDefaultPose();
		animator = ModelAnimator.create();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
		this.lowerBody.render(f5);


	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		EntityPrehistoric prehistoric = (EntityPrehistoric) entity;
		animator.update(entity);
		blockMovement(f, f1, f2, f3, f4, f5, (Entity) entity);
		this.resetToDefaultPose();
		setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
		animator.setAnimation(prehistoric.SPEAK_ANIMATION);
		animator.startKeyframe(10);
		ModelUtils.rotate(animator, neck, 15, 0, 0);
		ModelUtils.rotate(animator, head, -20, 0, 0);
		ModelUtils.rotate(animator, lowerJaw, 24, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(10);
		animator.setAnimation(prehistoric.ATTACK_ANIMATION);
		animator.startKeyframe(10);
		ModelUtils.rotate(animator, neck, -31, 0, 0);
		ModelUtils.rotate(animator, head, 37, 0, -20);
		ModelUtils.rotate(animator, lowerJaw, 25, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		ModelUtils.rotate(animator, neck, 6, 0, 0);
		ModelUtils.rotate(animator, head, -14, 0, 20);
		ModelUtils.rotate(animator, lowerJaw, 25, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(10);
		animator.setAnimation(EntityTyrannosaurus.ROAR_ANIMATION);
		animator.startKeyframe(20);
		ModelUtils.rotate(animator, lowerBody, -5.22, 0, 0);
		ModelUtils.rotate(animator, neck, -41, 0, 0);
		ModelUtils.rotate(animator, rightThigh, -15, 0, 0);
		ModelUtils.rotate(animator, rightLeg, -31.3, 0, 0);
		ModelUtils.rotate(animator, rightFoot, 46, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(20);
		ModelUtils.rotate(animator, lowerBody, -13.04, 0, 0);
		animator.move(rightThigh, 0F, 1.4F, -1F);
		ModelUtils.rotate(animator, lowerJaw, 39.13, 0, 0);
		EntityTyrannosaurus mob = ((EntityTyrannosaurus) animator.getEntity());
		if (mob.getAnimation() == EntityTyrannosaurus.ROAR_ANIMATION && mob.getAnimationTick() >= 20 && mob.getAnimationTick() <= 60) {
			this.flap(head, 0.4F, 0.1F, false, 0, 0, ((Entity) entity).ticksExisted, 1);
			this.flap(neck, 0.4F, 0.1F, false, 0, 0, ((Entity) entity).ticksExisted, 1);
		}
		ModelUtils.rotate(animator, tail1, -13.04, 0, 0);
		ModelUtils.rotate(animator, tail2, 7.83, 0, 0);
		ModelUtils.rotate(animator, tail3, -2.61, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(20);
		ModelUtils.rotateToInit(animator, lowerBody);
		ModelUtils.rotateToInit(animator, tail1);
		ModelUtils.rotateToInit(animator, tail2);
		ModelUtils.rotateToInit(animator, tail3);
		ModelUtils.rotateToInit(animator, neck);
		ModelUtils.rotateToInit(animator, head);
		animator.endKeyframe();
		animator.resetKeyframe(20);
		if (((EntityPrehistoric) entity).isSkeleton()) {
			return;
		}
		if (mob.getAnimation() == EntityTyrannosaurus.NO_ANIMATION && !mob.isActuallyWeak()) {
			ModelUtils.faceTargetMod(neck, f3, f4, 0.5F);
			ModelUtils.faceTargetMod(head, f3, f4, 0.5F);

		}

	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		AdvancedModelRenderer[] tailParts = {this.tail1, this.tail2, this.tail3};
		AdvancedModelRenderer[] neckParts = {this.neck, this.head};
		AdvancedModelRenderer[] leftArmParts = {this.leftUpperArm, this.leftLowerArm};
		AdvancedModelRenderer[] rightArmParts = {this.rightUpperArm, this.rightLowerArm};
		if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
			return;
		}
		float speed = 0.7F;
		float speed2 = 0.2F;
		float speedIdle = 0.1F;
		float degree = 1F;
		{
			float sitProgress = ((EntityPrehistoric) (entity)).sitProgress;
			sitAnimationRotation(lowerJaw, sitProgress, 0.02199114857512855F, -0.0F, 0.0F);
			sitAnimationRotation(upperJaw, sitProgress, 0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(leftLeg, sitProgress, -0.9948376736367678F, 0.0F, 0.0F);
			sitAnimationRotation(rightThigh, sitProgress, -0.3490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(rightFoot, sitProgress, 1.5707963267948966F, -0.0F, 0.0F);
			sitAnimationRotation(tail3, sitProgress, -0.017453292519943295F, 0.0F, 0.0F);
			sitAnimationRotation(upperBody, sitProgress, 0.09564404300928926F, -0.0F, 0.0F);
			sitAnimationRotation(leftLowerArm, sitProgress, -0.5061454830783556F, -0.0F, 0.0F);
			sitAnimationRotation(tail2, sitProgress, 0.07208209810736581F, -0.0F, 0.0F);
			sitAnimationRotation(head, sitProgress, 0.8213519459885316F, -0.0F, 0.0F);
			sitAnimationRotation(rightUpperArm, sitProgress, 0.2775073510670984F, -0.0F, 0.0F);
			sitAnimationRotation(leftFoot, sitProgress, 1.5707963267948966F, -0.0F, 0.0F);
			sitAnimationRotation(leftUpperArm, sitProgress, 0.27052603405912107F, -0.0F, 0.0F);
			sitAnimationRotation(rightLeg, sitProgress, -0.9948376736367678F, -0.0F, 0.0F);
			sitAnimationRotation(tail4, sitProgress, 0.06981317007977318F, -0.0F, 0.0F);
			sitAnimationRotation(leftThigh, sitProgress, -0.3490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(lowerBody, sitProgress, -0.22689280275926282F, -0.0F, 0.0F);
			sitAnimationRotation(neck, sitProgress, -0.41887902047863906F, -0.0F, 0.0F);
			sitAnimationRotation(rightLowerArm, sitProgress, -0.5235987755982988F, -0.0F, 0.0F);
			sitAnimationRotation(tail1, sitProgress, -0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationPos(rightThigh, sitProgress, 0F, 1.4F, 0F);
			sitAnimationPos(lowerBody, sitProgress, 0F, 10.80F - ModelUtils.getDefaultPositionY(lowerBody), 0F);
			sitAnimationPos(leftThigh, sitProgress, 0F, 1.4F, 0F);
		}
		{
			float sitProgress = ((EntityPrehistoric) (entity)).sleepProgress;

			sitAnimationRotation(tail1, sitProgress, -0.30176742766981957F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(leftLowerArm, sitProgress, -0.5061454830783556F, -0.0F, 0.0F);
			sitAnimationRotation(upperBody, sitProgress, 0.2527236756887789F, -0.09896016858807849F, -0.2734930937875114F);
			sitAnimationRotation(rightThigh, sitProgress, -0.8726646259971648F, -0.0F, 0.0F);
			sitAnimationRotation(lowerBody, sitProgress, -0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(leftLeg, sitProgress, -0.6517059426946827F, -0.0F, 0.0F);
			sitAnimationRotation(tail4, sitProgress, 0.024877906674081626F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(lowerJaw, sitProgress, 0.02199114857512855F, -0.0F, 0.0F);
			sitAnimationRotation(rightLowerArm, sitProgress, -0.5235987755982988F, -0.0F, 0.0F);
			sitAnimationRotation(rightFoot, sitProgress, 1.5707963267948966F, -0.0F, 0.0F);
			sitAnimationRotation(leftFoot, sitProgress, 1.5707963267948966F, -0.0F, 0.0F);
			sitAnimationRotation(tail3, sitProgress, 0.1371828792067543F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(tail2, sitProgress, 0.04625122517784973F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(neck, sitProgress, 0.03944444109507184F, 0.24434609527920614F, -1.313360262125733F);
			sitAnimationRotation(leftUpperArm, sitProgress, 0.8813912722571364F, -0.0F, 0.0F);
			sitAnimationRotation(rightLeg, sitProgress, -0.6517059426946827F, -0.0F, 0.0F);
			sitAnimationRotation(leftThigh, sitProgress, -0.8726646259971648F, 0.0F, 0.0F);
			sitAnimationRotation(rightUpperArm, sitProgress, 0.8813912722571364F, -0.0F, 0.0F);
			sitAnimationRotation(head, sitProgress, 0.40247292550989233F, -0.41992621802983565F, 0.06230825429619756F);
			sitAnimationRotation(upperJaw, sitProgress, 0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationPos(rightThigh, sitProgress, 0F, 1.4F, 0F);
			sitAnimationPos(lowerBody, sitProgress, 0F, 10.80F - ModelUtils.getDefaultPositionY(lowerBody), 0F);
			sitAnimationPos(leftThigh, sitProgress, 0F, 1.4F, 0F);
		}
		{
			float sitProgress = ((EntityPrehistoric) (entity)).weakProgress;
			sitAnimationRotation(leftLowerArm, sitProgress, -0.5061454830783556F, -0.0F, 0.0F);
			sitAnimationRotation(upperBody, sitProgress, 0.2527236756887789F, -0.09896016858807849F, -0.2734930937875114F);
			sitAnimationRotation(lowerBody, sitProgress, -0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(rightLowerArm, sitProgress, -0.5235987755982988F, -0.0F, 0.0F);
			sitAnimationRotation(rightThigh, sitProgress, -0.24434609527920614F, -0.0F, 0.6609561877302526F);
			sitAnimationRotation(neck, sitProgress, 0.03944444109507184F, 0.24434609527920614F, -1.313360262125733F);
			sitAnimationRotation(rightUpperArm, sitProgress, 0.7068583470577035F, -0.08726646259971647F, 0.20943951023931953F);
			sitAnimationRotation(lowerJaw, sitProgress, 0.02199114857512855F, -0.0F, 0.0F);
			sitAnimationRotation(tail4, sitProgress, 0.024877906674081626F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(leftThigh, sitProgress, -0.24643333186202032F, 0.0F, -0.6610219215870975F);
			sitAnimationRotation(leftUpperArm, sitProgress, 0.7068583470577035F, 0.08726646259971647F, -0.20943951023931953F);
			sitAnimationRotation(leftLeg, sitProgress, -0.40823251204147365F, 0.0F, -0.5223770451219029F);
			sitAnimationRotation(tail3, sitProgress, 0.1371828792067543F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(tail1, sitProgress, -0.30176742766981957F, 0.20943951023931953F, 0.0F);
			sitAnimationRotation(leftFoot, sitProgress, 0.33161255787892263F, -0.0F, 0.0F);
			sitAnimationRotation(head, sitProgress, 0.40247292550989233F, -0.41992621802983565F, 0.06230825429619756F);
			sitAnimationRotation(upperJaw, sitProgress, 0.03490658503988659F, -0.0F, 0.0F);
			sitAnimationRotation(rightLeg, sitProgress, -0.40823251204147365F, -0.0F, 0.5223770451219029F);
			sitAnimationRotation(rightFoot, sitProgress, 0.33161255787892263F, -0.0F, 0.0F);
			sitAnimationRotation(tail2, sitProgress, 0.04625122517784973F, 0.20943951023931953F, 0.0F);
			sitAnimationPos(rightThigh, sitProgress, 0F, 1.4F, 0F);
			sitAnimationPos(lowerBody, sitProgress, 0F, 10.80F - ModelUtils.getDefaultPositionY(lowerBody), 0F);
			sitAnimationPos(leftThigh, sitProgress, 0F, 1.4F, 0F);
		}
		this.bob(lowerBody, speedIdle * 0.5F, degree * 0.4F, false, f2, 1);
		this.bob(rightThigh, speedIdle * 0.5F, degree * -0.4F, false, f2, 1);
		this.bob(leftThigh, speedIdle * 0.5F, degree * -0.4F, false, f2, 1);
		this.walk(upperBody, speedIdle * 0.5F, degree * 0.05F, false, 1F, 0F, f2, 1);
		this.walk(lowerBody, speed2, degree * 0.15F, true, 0F, 0F, f, f1);
		this.walk(rightThigh, speed2, degree * -0.15F, true, 0F, 0F, f, f1);
		this.walk(leftThigh, speed2, degree * -0.15F, true, 0F, 0F, f, f1);
		this.walk(leftThigh, speed2, 0.6F, false, 0F, 0.4F, f, f1);
		this.walk(leftLeg, speed2, 0.2F, false, 0F, -0.6F, f, f1);
		this.walk(leftFoot, speed2, -0.6F, true, 2.5F, -0.4F, f, f1);
		this.walk(rightThigh, speed2, 0.6F, true, 0F, -0.4F, f, f1);
		this.walk(rightLeg, speed2, 0.2F, true, 0F, 0.6F, f, f1);
		this.walk(rightFoot, speed2, -0.6F, false, 2.5F, 0.4F, f, f1);
		this.chainWave(tailParts, speedIdle, degree * 0.05F, -3, f2, 1);
		this.chainWave(leftArmParts, speedIdle, degree * 0.05F, -3, f2, 1);
		this.chainWave(rightArmParts, speedIdle, degree * 0.05F, -3, f2, 1);
		this.chainSwing(tailParts, speedIdle, degree * 0.15F, -3, f2, 1);
		this.chainWave(neckParts, speedIdle, degree * 0.05F, -3, f2, 1);
		this.chainWave(neckParts, speedIdle, degree * 0.05F, 3, f, f1);
		this.chainWave(tailParts, speedIdle, degree * 0.15F, -3, f, f1);
		((EntityPrehistoric) entity).chainBuffer.applyChainSwingBuffer((ModelRenderer[]) tailParts);
	}
}
