package fossilsarcheology.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

public class ModelAncientHelmet extends BipedModel {
    public final ModelRenderer Snout;
    public final ModelRenderer Ear1;
    public final ModelRenderer Ear2;
    public final ModelRenderer Tooth1;
    public final ModelRenderer Tooth2;

    public ModelAncientHelmet(float f) {
        super(f, 0, 64, 64);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Ear2 = new ModelRenderer(this, 0, 32);
        this.Ear2.setRotationPoint(-3.0F, 0, 3.5F);
        this.Ear2.addBox(-1.0F, -6.0F, -0.5F, 2, 6, 1, f);
        this.Tooth2 = new ModelRenderer(this, 0, 0);
        this.Tooth2.setRotationPoint(-1.5F, 0.9F, -7.5F);
        this.Tooth2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, f);
        this.Ear1 = new ModelRenderer(this, 0, 32);
        this.Ear1.setRotationPoint(3.0F, 0, 3.5F);
        this.Ear1.addBox(-1.0F, -6.0F, -0.5F, 2, 6, 1, f);
        this.Tooth1 = new ModelRenderer(this, 0, 0);
        this.Tooth1.setRotationPoint(1.5F, 0.9F, -7.5F);
        this.Tooth1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, f);
        this.Snout = new ModelRenderer(this, 0, 32);
        this.Snout.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Snout.addBox(-2.0F, -3.0F, -8.0F, 4, 3, 12, f);
        this.Snout.addChild(this.Ear2);
        this.Snout.addChild(this.Tooth2);
        this.Snout.addChild(this.Ear1);
        this.Snout.addChild(this.Tooth1);
        this.bipedHead.addChild(this.Snout);
    }

    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity entityarmorstand = (ArmorStandEntity) entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            this.bipedHeadwear.copyModelAngles(this.bipedHead);
        } else {
            super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }
}
