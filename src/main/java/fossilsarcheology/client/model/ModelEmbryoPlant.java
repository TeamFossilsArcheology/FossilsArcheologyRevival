package fossilsarcheology.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelEmbryoPlant extends ModelBase {
    // fields
    final ModelRenderer Shape1;
    final ModelRenderer Shape2;
    final ModelRenderer Shape3;
    final ModelRenderer Shape4;
    final ModelRenderer Shape5;

    public ModelEmbryoPlant() {
        textureWidth = 64;
        textureHeight = 32;

        Shape1 = new ModelRenderer(this, 0, 0);
        Shape1.addBox(-1.5F, -1F, -1F, 3, 3, 3);
        Shape1.setRotationPoint(0F, 13F, 0F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, -0.418879F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 0, 6);
        Shape2.addBox(-0.5F, -3F, -1F, 1, 3, 2);
        Shape2.setRotationPoint(0F, 13F, 0F);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 0, 11);
        Shape3.addBox(-1F, -6F, -2F, 2, 5, 4);
        Shape3.setRotationPoint(0F, 11.2F, 0F);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, -0.1745329F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 0, 20);
        Shape4.addBox(-0.5F, -2F, -1F, 1, 2, 2);
        Shape4.setRotationPoint(0F, 5.6F, 0F);
        Shape4.setTextureSize(64, 32);
        Shape4.mirror = true;
        setRotation(Shape4, 0.3141593F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 0, 24);
        Shape5.addBox(-0.5F, -2F, -1F, 1, 2, 2);
        Shape5.setRotationPoint(0F, 6F, 2F);
        Shape5.setTextureSize(64, 32);
        Shape5.mirror = true;
        setRotation(Shape5, -0.4886922F, 0F, 0F);
    }

    public void render(float f5) {
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape5.render(f5);
        Shape4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
