package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.github.teamfossilsarcheology.fossil.client.ClientInit;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.PathingScreen;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.InstructionRenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.object.Color;

import static com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug.*;

public class PathingRenderer {
    public static int pathIndex;

    public static void reverseIndex() {
        if (PathingScreen.currentNav.getPath() != null) {
            int max = PathingScreen.currentNav.getPath().getNodeCount();
            if (pathIndex > 0) {
                pathIndex--;
            } else if (pathIndex == 0) {
                pathIndex = max - 1;
            }
        }
        if (pathNavigation4 != null && pathNavigation4.sweepStartPos != null) pathNavigation4.followThePath();
        if (pathNavigation5 != null && pathNavigation5.sweepStartPos != null) pathNavigation5.followThePath();
    }

    public static void advanceIndex() {
        if (PathingScreen.currentNav.getPath() != null) {
            int max = PathingScreen.currentNav.getPath().getNodeCount();
            if (pathIndex < max - 1) {
                pathIndex++;
            } else if (pathIndex == max - 1) {
                pathIndex = 0;
            }
        }
        if (pathNavigation4 != null && pathNavigation4.sweepStartPos != null) pathNavigation4.followThePath();
        if (pathNavigation5 != null && pathNavigation5.sweepStartPos != null) pathNavigation5.followThePath();
    }

    public static void render(PoseStack poseStack, MultiBufferSource buffer, float partialTicks, long finishNanoTime) {
        if (pos1 != null && pos2 != null) {
            poseStack.pushPose();
            renderPath(PathingScreen.currentNav, poseStack, buffer, partialTicks);
            poseStack.popPose();
        }
        if (pos1 != null) PathingRenderUtil.renderLineBox(poseStack, buffer, pos1);
        if (pos2 != null) PathingRenderUtil.renderLineBox(poseStack, buffer, pos2);

        /*
        //Climbing stuff debugging
        Vec3 pos = getOffsetHitResult(Minecraft.getInstance()).getLocation();
        double w = PathingScreen.bbWidth;
        double h = PathingScreen.bbHeight;
        AABB bounding = new AABB(pos.x - w/2, pos.y, pos.z -  w/2, pos.x +  w/2, pos.y + h, pos.z +  w/2);
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), bounding, 1, 1, 1, 1);
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), new AABB(pos.x - 0.05, pos.y, pos.z - 0.05, pos.x + 0.05, pos.y + 0.05, pos.z + 0.05), 1, 0, 1, 1);

        AABB aabb = bounding.move(Vec3.atBottomCenterOf(new BlockPos(pos)).scale(-1)).move(pos);
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), aabb, 0, 1, 0, 1);

        Pair<Direction, Double> dir = Util.getClosestSide(bounding, new BlockPos(pos));
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Vec3 add = pos.add(direction.getStepX(), direction.getStepY()+1, direction.getStepZ());
            if (dir.key() == direction) {
                InstructionRenderUtil.renderFloatingText(poseStack, Minecraft.getInstance(), direction.name(), add);
            }
        }*/
        if (showHelpMenu) {
            BlockPos targetPos = PathingDebug.getBlockHitResult(Minecraft.getInstance());
            if (pos1 != null) {
                InstructionRenderUtil.renderWholeBox(poseStack, targetPos, Color.ofRGBA(0, 0, 1, 0.5f), finishNanoTime);
            } else {
                InstructionRenderUtil.renderWholeBox(poseStack, targetPos, Color.ofRGBA(0, 1, 0, 0.5f), finishNanoTime);
            }
            InstructionRenderUtil.renderFloatingText(poseStack, Minecraft.getInstance(), String.valueOf(PathingDebug.pickBlockOffset), Vec3.atCenterOf(targetPos).add(0, 0.5, 0));
        }
    }

    private static void renderPath(PlayerPathNavigation pathNav, PoseStack poseStack, MultiBufferSource buffer, float partialTicks) {
        PlayerPath path = pathNav.getPath();
        Minecraft mc = Minecraft.getInstance();
        boolean renderNeigbours = false;
        boolean renderPath = true;
        boolean renderLine = true;
        boolean renderOpenSet = false;
        boolean renderClosedSet = true;
        boolean renderSweep = true;
        boolean renderWantedPos = true;
        if (path != null) {
            PathingRenderer.pathIndex = PathingRenderer.pathIndex >= path.getNodeCount() ? path.getNodeCount() - 1 : PathingRenderer.pathIndex;
            //Path nodes
            if (renderPath) {
                for (int i = 0; i < path.nodes.size(); i++) {
                    Node node = path.getNode(i);
                    AABB targetArea = new AABB(new BlockPos(node.x, node.y, node.z));
                    if (i == PathingRenderer.pathIndex) {
                        //Highlight current node
                        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0, (float) i / (path.nodes.size() - 1), 1, 0.25f);
                    } else {
                        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 1, (float) i / (path.nodes.size() - 1), 1, 0.25f);
                    }
                }
            }
            if (renderOpenSet) {
                for (Node node : path.getOpenSet()) {
                    double maxDistanceToWaypoint = 0.5;
                    AABB targetArea = new AABB(node.x + 0.5F - maxDistanceToWaypoint / 2.0F,
                            node.y + 0.01F, node.z + 0.5F - maxDistanceToWaypoint / 2.0F,
                            node.x + 0.5F + maxDistanceToWaypoint / 2.0F,
                            node.y + 0.1, node.z + 0.5F + maxDistanceToWaypoint / 2.0F);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0.8f, 1, 1, 0.5f);
                }
            }

            if (renderLine && path.getNodeCount() == 2) {
                PathingRenderUtil.renderLine(poseStack, path.getNode(0), path.getNode(1));
            }
            if (renderClosedSet && path.getClosedSet().length > 0) {
                //RenderUtil.renderTextBatch(poseStack, mc, path.getClosedSet());
                //long l = (mc.level.getGameTime() / 10) % path.getClosedSet().length;
                if (path.getClosedSet().length != PathingScreen.baseTick) {
                    PathingScreen.baseTick = path.getClosedSet().length;
                    if (PathingScreen.tickSlider != null) PathingScreen.tickSlider.setMaxValue(PathingScreen.baseTick);
                    if (PathingScreen.baseTick < PathingScreen.tick) {
                        PathingScreen.tick = PathingScreen.baseTick;
                    }
                }
                for (int i = 0; i < PathingScreen.tick; i++) {
                    Node node2 = path.getClosedSet()[i];
                    AABB targetArea = new AABB(node2.x + 0.25, node2.y + 0.25, node2.z + 0.25, node2.x + 0.75, node2.y + 0.75, node2.z + 0.75);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 1, i == PathingScreen.tick ? 1 : 0, 0, 0.5f);
                }
                PathingRenderUtil.renderTextBatch(poseStack, mc, path.getClosedSet(), PathingScreen.tick);
            }

            if (renderSweep) {
                //Wanted pos for sweep logic
                if (pathNav.sweepStartPos != null) {
                    Vec3 wantedPos = pathNav.sweepStartPos;
                    AABB targetArea = new AABB(wantedPos.x - 0.25, wantedPos.y - 0.25, wantedPos.z - 0.25, wantedPos.x + 0.25, wantedPos.y + 0.25, wantedPos.z + 0.25);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 1, 1, 0, 1);
                }
                //Wanted pos for sweep logic
                if (pathNav.sweepWantedPos != null) {
                    Vec3 wantedPos = pathNav.sweepWantedPos;
                    poseStack.pushPose();
                    poseStack.translate(wantedPos.x, wantedPos.y, wantedPos.z);
                    mc.getBlockRenderer().renderSingleBlock(Blocks.RED_CARPET.defaultBlockState(), poseStack, buffer, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks), OverlayTexture.NO_OVERLAY);
                    poseStack.popPose();
                }
                if (WaterPathFinder.sweeped != null) {
                    for (BlockPos blockPos : WaterPathFinder.sweeped) {
                        AABB targetArea = new AABB(blockPos.getX() - 0.25, blockPos.getY() - 0.25, blockPos.getZ() - 0.25, blockPos.getX() + 0.25, blockPos.getY() + 0.25, blockPos.getZ() + 0.25);
                        targetArea = targetArea.move(-pos1.getX(), -pos1.getY(), -pos1.getZ());
                        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0, 1, 0, 1);
                    }
                }
            }
            if (renderWantedPos) {
                //Wanted pos from movecontrol
                if (pathNav.wantedPos != null) {
                    Vec3 wantedPos = pathNav.wantedPos.add(0, 1, 0);
                    AABB targetArea = new AABB(wantedPos.x - 0.25, wantedPos.y - 0.25, wantedPos.z - 0.25, wantedPos.x + 0.25, wantedPos.y + 0.25, wantedPos.z + 0.25);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0, 1, 1, 1);
                }
            }
            //Render current hitbox
            Vec3 pos = path.getEntityPosAtNode(PathingRenderer.pathIndex);
            AABB aABB = PathingRenderer.getBigHitbox().move(pos.x, pos.y, pos.z);
            LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), aABB, 0, 1, 0, 1);

            //Highlight blocked positions around the current node
            if (renderNeigbours) {
                Node[] neighbors = new Node[6400];
                BlockPos[] neighborsX = new BlockPos[6400];
                PathNavigationRegion pathNavigationRegion = new PathNavigationRegion(mc.level, pos1.offset(-24, -24, -24), pos1.offset(24, 24, 24));
                pathNav.nodeEvaluator.prepare(pathNavigationRegion, mc.player);
                pathNav.nodeEvaluator.getBlockedNeighbors(neighbors, path.getNode(PathingRenderer.pathIndex), neighborsX);
                pathNav.nodeEvaluator.done();
                for (Node neighbor : neighbors) {
                    if (neighbor != null) {
                        poseStack.pushPose();
                        poseStack.translate(neighbor.x, neighbor.y, neighbor.z);
                        if (neighbor.type == BlockPathTypes.BLOCKED) {
                            mc.getBlockRenderer().renderSingleBlock(Blocks.IRON_BLOCK.defaultBlockState(), poseStack, buffer, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks), OverlayTexture.NO_OVERLAY);
                        } else {
                            mc.getBlockRenderer().renderSingleBlock(Blocks.COBWEB.defaultBlockState(), poseStack, buffer, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks), OverlayTexture.NO_OVERLAY);
                        }
                        poseStack.popPose();
                    }
                }
                for (BlockPos neighbor : neighborsX) {
                    if (neighbor != null) {
                        poseStack.pushPose();
                        poseStack.translate(neighbor.getX(), neighbor.getY(), neighbor.getZ());
                        mc.getBlockRenderer().renderSingleBlock(Blocks.GLASS.defaultBlockState(), poseStack, buffer, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks), OverlayTexture.NO_OVERLAY);
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    public static void renderOverlay(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        int yPosition = (int) (0.2 * mc.getWindow().getGuiScaledHeight());
        /*if (PathingDebug.pathNavigation1 != null) {
            guiGraphics.drawString(mc.font, Component.literal("zza: " + PathingDebug.pathNavigation1.moveControl.zza), 2, yPosition, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("yRot: " + PathingDebug.pathNavigation1.moveControl.yRot), 2, yPosition + 20, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("jump: " + PathingDebug.pathNavigation1.moveControl.jump), 2, yPosition + 40, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("speed: " + PathingDebug.pathNavigation1.moveControl.speed), 2, yPosition + 60, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("op: " + PathingDebug.pathNavigation1.moveControl.operation.name()), 2, yPosition + 80, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("move: " + PathingDebug.pathNavigation1.moveControl.move.toString()), 2, yPosition + 100, 0xEEEBF0);
        }*/
        if (showHelpMenu) {
            int xPos = (int) (0.7 * mc.getWindow().getGuiScaledWidth());
            guiGraphics.drawString(mc.font, Component.translatable("Open Screen: %s", ClientInit.pathingScreenKey.getTranslatedKeyMessage()), xPos, yPosition + 120, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("Set pos 1: Left click"), xPos, yPosition + 140, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("Set pos 2: Right click"), xPos, yPosition + 160, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.literal("Set sweep pos: Middle mouse"), xPos, yPosition + 180, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.translatable("Repath: %s", ClientInit.debugRepathKey.getTranslatedKeyMessage()), xPos, yPosition + 200, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.translatable("Advance: %s", ClientInit.debugAdvanceKey.getTranslatedKeyMessage()), xPos, yPosition + 220, 0xEEEBF0);
            guiGraphics.drawString(mc.font, Component.translatable("Reverse: %s", ClientInit.debugReverseKey.getTranslatedKeyMessage()), xPos, yPosition + 240, 0xEEEBF0);
        }
    }

    public static AABB getBigHitbox() {
        float width = getBbWidth() / 2;
        float height = getBbHeight();
        return new AABB(-width, 0, -width, width, height, width);
    }

    public static float getBbWidth() {
        return (float) PathingScreen.bbWidth;
    }

    public static float getBbHeight() {
        return (float) PathingScreen.bbHeight;
    }
}
