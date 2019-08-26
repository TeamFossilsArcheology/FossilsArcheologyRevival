package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.monster.EntityFriendlyPigZombie;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelFriendlyPigZombie extends ModelZombie {

	public ModelFriendlyPigZombie() {
		this(0.0F, false);
	}

	public ModelFriendlyPigZombie(float par1, boolean par2) {
		super(par1, par2);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float f6 = MathHelper.sin(0 * (float) Math.PI);
		float f7 = MathHelper.sin((1.0F - (1.0F - 0) * (1.0F - 0)) * (float) Math.PI);
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
		this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
		this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		if (((EntityFriendlyPigZombie) entity).isSitting()) {
			this.bipedRightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			this.bipedLeftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 10F);
			this.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
		}
		if (this.swingProgress > 0.0F)
		{
			EnumHandSide enumhandside = this.getMainHand(entity);
			ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			float swingProg = this.swingProgress;
			if(entity instanceof EntityFriendlyPigZombie){
				swingProg = ((EntityFriendlyPigZombie) entity).getSwingProgress(f1);
			}
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(swingProg) * ((float)Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT)
			{
				this.bipedBody.rotateAngleY *= -1.0F;
			}

			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			swingProg = 1.0F - this.swingProgress;
			swingProg = swingProg * swingProg;
			swingProg = swingProg * swingProg;
			swingProg = 1.0F - swingProg;
			float f12 = MathHelper.sin(swingProg * (float)Math.PI);
			float f13 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f12 * 1.2D + (double)f13));
			modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}
	}
}