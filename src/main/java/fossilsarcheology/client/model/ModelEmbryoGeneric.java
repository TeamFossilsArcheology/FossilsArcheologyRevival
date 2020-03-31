package fossilsarcheology.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelEmbryoGeneric extends ModelBase {
    // fields
    final ModelRenderer head;
    final ModelRenderer body;
    final ModelRenderer neck2;
    final ModelRenderer neck1;
    final ModelRenderer tail1;
    final ModelRenderer tail2;
    final ModelRenderer tail3;
    final ModelRenderer leftarm;
    final ModelRenderer left_foot;
    final ModelRenderer rightfoot;
    final ModelRenderer rightarm;

    public ModelEmbryoGeneric() {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1.5F, -1.5F, -3F, 3, 3, 3);
        head.setRotationPoint(0F, 13F, -0.2F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0.3141593F, 0F, 0F);
        body = new ModelRenderer(this, 0, 15);
        body.addBox(-2F, 0F, -1F, 4, 4, 2);
        body.setRotationPoint(0F, 13.9F, 1.8F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, -0.0349066F, 0F, 0F);
        neck2 = new ModelRenderer(this, 0, 6);
        neck2.addBox(-1F, -1F, -1F, 2, 3, 2);
        neck2.setRotationPoint(0F, 13F, -0.5F);
        neck2.setTextureSize(64, 32);
        neck2.mirror = true;
        setRotation(neck2, 1.413717F, 0F, 0F);
        neck1 = new ModelRenderer(this, 0, 11);
        neck1.addBox(-1.5F, 0F, -1F, 3, 2, 2);
        neck1.setRotationPoint(0F, 12.9F, 0.8F);
        neck1.setTextureSize(64, 32);
        neck1.mirror = true;
        setRotation(neck1, 0.5585054F, 0F, 0F);
        tail1 = new ModelRenderer(this, 0, 21);
        tail1.addBox(-1.5F, 0F, -1F, 3, 2, 2);
        tail1.setRotationPoint(0F, 17.3F, 1.7F);
        tail1.setTextureSize(64, 32);
        tail1.mirror = true;
        setRotation(tail1, -0.715585F, 0F, 0F);
        tail2 = new ModelRenderer(this, 0, 25);
        tail2.addBox(-1F, -0.5F, -2F, 2, 1, 2);
        tail2.setRotationPoint(0F, 18.8F, 1F);
        tail2.setTextureSize(64, 32);
        tail2.mirror = true;
        setRotation(tail2, -0.122173F, 0F, 0F);
        tail3 = new ModelRenderer(this, 0, 28);
        tail3.addBox(-0.5F, -0.5F, -2F, 1, 1, 2);
        tail3.setRotationPoint(0F, 18.6F, -0.8F);
        tail3.setTextureSize(64, 32);
        tail3.mirror = true;
        setRotation(tail3, -0.5235988F, 0F, 0F);
        leftarm = new ModelRenderer(this, 60, 0);
        leftarm.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        leftarm.setRotationPoint(1.4F, 14.6F, 2F);
        leftarm.setTextureSize(64, 32);
        leftarm.mirror = true;
        setRotation(leftarm, -1.204277F, -0.2094395F, 0F);
        left_foot = new ModelRenderer(this, 60, 3);
        left_foot.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        left_foot.setRotationPoint(1F, 17.4F, 1.4F);
        left_foot.setTextureSize(64, 32);
        left_foot.mirror = true;
        setRotation(left_foot, -1.204277F, -0.9773844F, 0F);
        rightfoot = new ModelRenderer(this, 60, 3);
        rightfoot.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        rightfoot.setRotationPoint(-1F, 17.4F, 1.4F);
        rightfoot.setTextureSize(64, 32);
        rightfoot.mirror = true;
        setRotation(rightfoot, -1.204277F, 0.9773844F, 0F);
        rightarm = new ModelRenderer(this, 60, 0);
        rightarm.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        rightarm.setRotationPoint(-1.4F, 14.6F, 2F);
        rightarm.setTextureSize(64, 32);
        rightarm.mirror = true;
        setRotation(rightarm, -1.204277F, 0.2094395F, 0F);
    }

    public void render(float f5) {
        head.render(f5);
        body.render(f5);
        neck2.render(f5);
        neck1.render(f5);
        tail1.render(f5);
        tail2.render(f5);
        tail3.render(f5);
        leftarm.render(f5);
        left_foot.render(f5);
        rightfoot.render(f5);
        rightarm.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
