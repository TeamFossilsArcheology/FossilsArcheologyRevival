package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.gui.debug.InstructionTab;
import com.fossil.fossil.client.model.PrehistoricGeoModel;
import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.AnimationOverride;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.entity.prehistoric.swimming.Meganeura;
import com.fossil.fossil.entity.util.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PrehistoricGeoRenderer<T extends Prehistoric> extends GeoEntityRenderer<T> {
    private final Function<ResourceLocation, RenderType> renderType;

    public PrehistoricGeoRenderer(EntityRendererProvider.Context renderManager, String model, String animation, Function<ResourceLocation, RenderType> renderType) {
        super(renderManager, new PrehistoricGeoModel<>(model, animation));
        this.renderType = renderType;
    }

    public static List<BlockPos> pathTargets = new ArrayList<>();
    public static BlockPos entityTarget = null;

    public static void showPath(List<BlockPos> targets, BlockPos target) {
        pathTargets = targets;
        entityTarget = target;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.getBbWidth() * 0.45F;
        if (entity instanceof Meganeura meganeura && meganeura.getAttachmentPos() != null) {
            poseStack.pushPose();
            Vec3i normal = meganeura.getAttachmentFace().getNormal();
            double x = normal.getX() * 0.2;
            double y = normal.getY() * 0.2 + 0.2;
            double z = normal.getZ() * 0.2;
            poseStack.translate(x, y, z);
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
            poseStack.popPose();
        } else {
            double rx = entity.xo + (entity.getX() - entity.xo) * partialTick;
            double ry = entity.yo + (entity.getY() - entity.yo) * partialTick;
            double rz = entity.zo + (entity.getZ() - entity.zo) * partialTick;
            if (!pathTargets.isEmpty()) {
                for (int i = 0; i < pathTargets.size(); i++) {
                    AABB targetArea = new AABB(pathTargets.get(i)).move(-rx, -ry, -rz);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 1, (float) i / (pathTargets.size() - 1), 1, 0.25f);
                }
            }
            if (entityTarget != null) {
                AABB targetArea = new AABB(entityTarget.getX() - 0.25, entityTarget.getY() - 0.25, entityTarget.getZ() - 0.25, entityTarget.getX() + 0.25, entityTarget.getY() + 0.25, entityTarget.getZ() + 0.25);
                targetArea = targetArea.move(-rx, -ry, -rz);
                LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0, 1, 1, 1);
            }
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        }
    }

    @Override
    public Color getRenderColor(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight) {
        if (InstructionTab.highlightEntityList != null && InstructionTab.highlightEntityList.getId() == animatable.getId()) {
            return Color.RED;
        } else if (InstructionTab.highlightInstructionList != null && InstructionTab.highlightInstructionList.getId() == animatable.getId()) {
            return Color.RED;
        }
        if (InstructionTab.activeEntity != null && InstructionTab.activeEntity.getId() == animatable.getId()) {
            return Color.YELLOW;
        }
        return super.getRenderColor(animatable, partialTick, poseStack, bufferSource, buffer, packedLight);
    }

    @Override
    protected float getSwingMotionAnimThreshold() {
        return Util.SWING_ANIM_THRESHOLD;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTick, RenderType type, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //Unchanged code
        setCurrentRTB(bufferSource);
        renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight,
                packedOverlay, red, green, blue, alpha);

        if (bufferSource != null)
            buffer = bufferSource.getBuffer(type);

        renderLate(animatable, poseStack, partialTick, bufferSource, buffer, packedLight,
                packedOverlay, red, green, blue, alpha);
        // Render all top level bones
        for (GeoBone group : model.topLevelBones) {
            renderRecursively(animatable, group, poseStack, buffer, packedLight, packedOverlay, red, green, blue,
                    alpha);
        }
        // Since we rendered at least once at this point, let's set the cycle to
        // repeated
        setCurrentModelRenderCycle(EModelRenderCycle.REPEATED);
        //Changed code
        updateTickForEntity(animatable);
    }

    //TODO: Find a better way to do this. Maybe in geckolib 4, Edit: RenderPerBone
    private final Map<Integer, Integer> tickForEntity = new HashMap<>();

    private boolean entityTickMatchesRenderTick(T animatable) {
        return getTickForEntity(animatable) == animatable.tickCount;
    }

    private int getTickForEntity(Entity entity) {
        return tickForEntity.computeIfAbsent(entity.getId(), integer -> entity.tickCount + 1);
    }

    public void removeTickForEntity(Entity entity) {
        tickForEntity.remove(entity.getId());
    }

    private void updateTickForEntity(Entity entity) {
        if (getTickForEntity(entity) <= entity.tickCount) {
            tickForEntity.put(entity.getId(), entity.tickCount);
        }
    }

    private void customBoneStuff(GeoBone bone, T animatable) {
        //Only update position once per tick
        if (entityTickMatchesRenderTick(animatable)) {
            MultiPart part = animatable.getCustomPart(bone.name);
            if (part != null) {
                //Tick hitboxes
                Vector3d localPos = bone.getLocalPosition();
                part.setOverride(new AnimationOverride(new Vec3(localPos.x, localPos.y, localPos.z), bone.getScaleX(), bone.getScaleY()));
            }
            EntityHitboxManager.Hitbox hitbox = animatable.attackBoxes.get(bone.name);
            if (hitbox != null) {
                //Tick attack boxes
                if (animatable.activeAttackBoxes.containsKey(hitbox)) {
                    Vector3d worldPos = bone.getWorldPosition();
                    animatable.activeAttackBoxes.put(hitbox, new Vec3(worldPos.x, worldPos.y, worldPos.z));
                }
            }
            if ("eat_pos".equals(bone.name)) {
                Vector3d worldPos = bone.getWorldPosition();
                animatable.eatPos = new Vec3(worldPos.x, worldPos.y, worldPos.z);
            }
        }
    }

    private void renderRecursively(T animatable, GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //Unchanged code
        poseStack.pushPose();
        RenderUtils.translateMatrixToBone(poseStack, bone);
        RenderUtils.translateToPivotPoint(poseStack, bone);

        boolean rotOverride = bone.rotMat != null;

        if (rotOverride) {
            poseStack.last().pose().multiply(bone.rotMat);
            poseStack.last().normal().mul(new Matrix3f(bone.rotMat));
        } else {
            RenderUtils.rotateMatrixAroundBone(poseStack, bone);
        }

        RenderUtils.scaleMatrixForBone(poseStack, bone);

        if (bone.isTrackingXform()) {
            Matrix4f poseState = poseStack.last().pose().copy();
            Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);

            bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
            localMatrix.translate(new Vector3f(getRenderOffset(this.animatable, 1)));
            bone.setLocalSpaceXform(localMatrix);

            Matrix4f worldState = localMatrix.copy();

            worldState.translate(new Vector3f(this.animatable.position()));
            bone.setWorldSpaceXform(worldState);
        }

        RenderUtils.translateAwayFromPivotPoint(poseStack, bone);
        renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        //Changed code
        customBoneStuff(bone, animatable);
        //Unchanged code
        if (!bone.isHidden) {
            for (GeoBone childBone : bone.childBones) {
                renderRecursively(animatable, childBone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }

        poseStack.popPose();
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return renderType.apply(texture);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f - rotationYaw));
        if (animatable.deathTime > 0) {
            float deathRotation = (animatable.deathTime + partialTick - 1f) / 20f * 1.6f;

            poseStack.mulPose(Vector3f.ZP.rotationDegrees(Math.min(Mth.sqrt(deathRotation), 1) * getDeathMaxRotation(animatable)));
        } else if (animatable.hasCustomName()) {
            String name = ChatFormatting.stripFormatting(animatable.getName().getString());

            if (name != null && (name.equals("Dinnerbone") || name.equalsIgnoreCase("Grumm"))) {
                poseStack.translate(0, animatable.getBbHeight() + 0.1f, 0);
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180f));
            }
        }
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return animatable.hasCustomName() && (animatable == entityRenderDispatcher.crosshairPickEntity || animatable.shouldShowName());
    }

    @Override
    public float getWidthScale(T animatable) {
        return animatable.getScale();
    }

    @Override
    public float getHeightScale(T entity) {
        return animatable.getScale();
    }
}
