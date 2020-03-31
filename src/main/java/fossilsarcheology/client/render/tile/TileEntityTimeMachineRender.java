package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelTimeMachineClock;
import fossilsarcheology.server.block.entity.TileEntityTimeMachine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityTimeMachineRender extends TileEntitySpecialRenderer<TileEntityTimeMachine> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/entities/time_machine_clock.png");
    private static final ModelTimeMachineClock MODEL = new ModelTimeMachineClock();

    public final float RndRound = ((float) Math.PI * 2F);
    private final int TickReset = 600;
    private int UpdateTick = 0;
    private float Hour = 0.0F;
    private float Minute = 0.0F;

    @Override
    public void render(TileEntityTimeMachine entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y + 0.75F, z + 0.5F);
        float animationTime = (float) entity.clockCounter + partialTicks;
        GlStateManager.translate(0.0F, 0.5F + MathHelper.sin(animationTime * 0.1F) * 0.01F, 0.0F);
        float var10;

        for (var10 = entity.currectFacingAngle - entity.sendingCurrentFacing; var10 >= Math.PI; var10 -= Math.PI * 2F) {
        }

        while (var10 < -(float) Math.PI) {
            var10 += ((float) Math.PI * 2F);
        }

        float var11 = entity.sendingCurrentFacing + var10 * partialTicks;
        GlStateManager.rotate(-var11 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        this.bindTexture(TEXTURE);
        float var12 = 0.0F;
        float var13 = 0.0F;
        var12 = (var12 - (float) MathHelper.fastFloor(var12)) * 1.6F - 0.3F;
        var13 = (var13 - (float) MathHelper.fastFloor(var13)) * 1.6F - 0.3F;

        if (var12 < 0.0F) {
            var12 = 0.0F;
        }

        if (var13 < 0.0F) {
            var13 = 0.0F;
        }

        if (var12 > 1.0F) {
            var12 = 1.0F;
        }

        if (var13 > 1.0F) {
            var13 = 1.0F;
        }

        float var14 = 0.0F;

        this.showProgress(MODEL, entity);

        if (entity.playerClosing) {
            MODEL.EdgeRotate(animationTime, 0.5F, var13, var14, 0.0F, 0.0625F);
        } else {
            MODEL.EdgePullBack();
        }
        if (entity.isClockInPlace()) {
            MODEL.render(null, animationTime, 0.5F, var13, var14, 0.0F, 0.0625F);
        }
        GlStateManager.popMatrix();
    }

    private void showProgress(ModelTimeMachineClock clock, TileEntityTimeMachine entity) {
        float chargeLevel = (float) entity.getChargeLevel();
        clock.UpdateTime(0.0F, chargeLevel / 1000.0F * ((float) Math.PI * 2F));
    }
}
