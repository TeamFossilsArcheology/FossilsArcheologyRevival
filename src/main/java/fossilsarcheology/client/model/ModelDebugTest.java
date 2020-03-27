package fossilsarcheology.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDebugTest extends ModelBase {
    final ModelRenderer center;

    public ModelDebugTest() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.center = new ModelRenderer(this, 0, 0);
        this.center.addBox(-16F, -16F, 0F, 32, 32, 0);
        this.center.setRotationPoint(0, 0, 0F);
        this.center.setTextureSize(32, 32);
        this.center.mirror = true;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        this.center.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
