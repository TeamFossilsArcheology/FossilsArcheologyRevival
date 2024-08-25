package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.PrehistoricSkeleton;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricDebug;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SDisableAIMessage;
import com.fossil.fossil.network.debug.C2SMoveMessage;
import com.fossil.fossil.network.debug.C2STameMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DebugScreen extends Screen {
    private static final List<PathInfo> pathTargets = new ArrayList<>();
    public static CycleButton<Boolean> disableAI;
    public static boolean showPaths;
    private static PathInfo currentVision;
    public final Entity entity;
    private final List<DebugTab> tabs = new ArrayList<>();
    private DebugTab currentTab;

    public DebugScreen(Entity entity) {
        super(entity == null ? new TextComponent("Debug Screen") : entity.getDisplayName());
        this.entity = entity;
    }

    public static Entity getHitResult(Minecraft mc) {
        Entity camera = mc.getCameraEntity();
        Vec3 view = camera.getViewVector(1.0f);
        double range = 30;
        Vec3 end = camera.getEyePosition().add(view.x * range, view.y * range, view.z * range);
        AABB aabb = camera.getBoundingBox().expandTowards(view.scale(range)).inflate(1);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(camera, camera.getEyePosition(), end, aabb,
                Entity::isPickable, range * range);
        return (entityHitResult != null) ? (Entity) entityHitResult.getEntity() : null;
    }

    public static void showPath(Player player, List<BlockPos> targets, List<BlockState> blocks, boolean below) {
        if (showPaths) {
            for (int i = 0; i < targets.size(); i++) {
                if (below) {
                    pathTargets.add(new PathInfo(targets.get(i), player.level.getBlockState(targets.get(i).below()), true));
                    //Minecraft.getInstance().debugRenderer.gameTestDebugRenderer.addMarker(targets.get(i).below(), -2147418368, "", 2000);
                    player.level.setBlock(targets.get(i).below(), blocks.get(i), 3);
                } else {
                    pathTargets.add(new PathInfo(targets.get(i), player.level.getBlockState(targets.get(i)), false));
                    //Minecraft.getInstance().debugRenderer.gameTestDebugRenderer.addMarker(targets.get(i), -2147418368, "", 2000);
                    player.level.setBlock(targets.get(i), blocks.get(i), 3);
                }
            }
        }
    }

    public static void showVision(Player player, BlockPos target, BlockState block) {
        if (currentVision != null) {
            player.level.setBlock(currentVision.targetPos, currentVision.blockState, 3);
        }
        currentVision = new PathInfo(target, player.level.getBlockState(target), false);
        player.level.setBlock(target, block, 3);
    }

    public static void clearPaths() {
        for (int i = pathTargets.size() - 1; i >= 0; i--) {
            boolean below = pathTargets.get(i).below;
            Minecraft.getInstance().level.setBlock(below ? pathTargets.get(i).targetPos.below() : pathTargets.get(i).targetPos, pathTargets.get(i).blockState, 3);
        }
        pathTargets.clear();
        if (currentVision != null) {
            Minecraft.getInstance().level.setBlock(currentVision.targetPos, currentVision.blockState, 3);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void clearWidgets() {
        super.clearWidgets();
        tabs.clear();
    }

    @Override
    protected void init() {
        var builder = CycleButton.booleanBuilder(new TextComponent("On"), new TextComponent("Off")).withValues(
                List.of(Boolean.TRUE, Boolean.FALSE));
        if (entity instanceof Prehistoric prehistoric) {
            tabs.add(new InfoTab(this, prehistoric));
            this.addRenderableWidget(new Button(460, height - 40, 50, 20, new TextComponent("Tame"), button -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2STameMessage(entity.getId()));
            }));
        } else if (entity instanceof PrehistoricSkeleton skeleton) {
            tabs.add(new SkeletonEditTab(this, skeleton));
        }
        if (entity instanceof Mob mob && entity instanceof PrehistoricDebug prehistoric) {
            builder.withInitialValue(mob.isNoAi());
            disableAI = builder.create(20, height - 130, width / 6, 20, new TextComponent("Disable AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SDisableAIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 0));
            });
            this.addRenderableWidget(disableAI);
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableGoalAI"));
            this.addRenderableWidget(builder.create(20, height - 100, width / 6, 20, new TextComponent("Disable Goal AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SDisableAIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 1));
            }));
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableMoveAI"));
            this.addRenderableWidget(builder.create(20, height - 70, width / 6, 20, new TextComponent("Disable Move AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SDisableAIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 2));
            }));
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableLookAI"));
            this.addRenderableWidget(builder.create(20, height - 40, width / 6, 20, new TextComponent("Disable Look AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SDisableAIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 3));
            }));
            this.addRenderableWidget(new Button(240, height - 70, 70, 20, new TextComponent("Move Left"), button -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SMoveMessage(entity.getId(), entity.blockPosition().getX() - 10, minecraft.level.getHeight(Heightmap.Types.MOTION_BLOCKING, entity.blockPosition().getX() - 10, entity.blockPosition().getZ()), entity.blockPosition().getZ()));
            }));
            this.addRenderableWidget(new Button(340, height - 70, 70, 20, new TextComponent("Move Right"), button -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SMoveMessage(entity.getId(), entity.blockPosition().getX() + 10, minecraft.level.getHeight(Heightmap.Types.MOTION_BLOCKING, entity.blockPosition().getX() - 10, entity.blockPosition().getZ()), entity.blockPosition().getZ()));
            }));
        }
        if (entity instanceof PrehistoricAnimatable) {
            tabs.add(new AnimationTab(this, entity));
        }
        builder.withInitialValue(showPaths);
        addRenderableWidget(builder.create(240, height - 40, 100, 20, new TextComponent("Show Paths"), (cycleButton, object) -> {
            showPaths = (boolean) cycleButton.getValue();
        }));
        addRenderableWidget(new Button(340, height - 40, 100, 20, new TextComponent("Clear Paths"), button -> clearPaths()));
        if (!tabs.isEmpty()) {
            tabs.forEach(tab -> tab.init(width, height));
            currentTab = addWidget(tabs.get(0));
            addRenderableWidget(CycleOption.create("Tab", () -> tabs, debugTab -> new TextComponent(debugTab.getClass().getSimpleName()),
                    options -> currentTab, (options, option, tab) -> {
                        addWidget(tab);
                        removeWidget(currentTab);
                        currentTab = tab;
                    }).createButton(Minecraft.getInstance().options, width / 2, 60, 100));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (currentTab != null) {
            currentTab.render(poseStack, mouseX, mouseY, partialTick);
        }
        if (entity instanceof Sheep sheep) {
            drawString(poseStack, minecraft.font, new TextComponent("yRot: " + sheep.getYRot()), 275, 15, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("yRotHead: " + sheep.getYHeadRot()), 275, 35, 16777215);
        }
        /*Player player = Minecraft.getInstance().player;
        float x = 1;
        float y = 1;
        float z = 1;
        Vec3 offset = new Vec3(x, y, z);
        float yRotCopy = Mth.wrapDegrees(player.getYRot());
        float yawCopy = Mth.wrapDegrees(player.getYRot() + 90);
        float targetYaw = (float) Mth.wrapDegrees(Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG);
        float newYaw = Mth.approachDegrees(yawCopy, targetYaw, 4);
        float targetPitch = (float) (Mth.atan2(-offset.y, offset.horizontalDistance()) * Mth.RAD_TO_DEG);
        var a = Mth.cos(newYaw * Mth.DEG_TO_RAD);
        var b = Mth.sin(-targetPitch * Mth.DEG_TO_RAD);
        var c = Mth.sin(newYaw * Mth.DEG_TO_RAD);
        var targetA = Mth.cos((targetYaw)* Mth.DEG_TO_RAD);
        var targetB = 0;
        var targetC = Mth.sin((targetYaw)* Mth.DEG_TO_RAD);
        drawString(poseStack, minecraft.font, new TextComponent("xRot: " + player.getXRot()), 175, 15, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("yRotCopy: " + yRotCopy), 175, 35, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("yawCopy: " + yawCopy), 175, 65, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("targetYaw: " + targetYaw), 175, 95, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("a: " + a), 175, 125, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("b: " + b), 175, 155, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("c: " + c), 175, 185, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("targetA: " + (targetA - a)), 175, 215, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("newYaw: " + (targetA - a)), 175, 245, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("targetC: " + (targetC - c)), 175, 275, 16777215);*/
    }

    record PathInfo(BlockPos targetPos, BlockState blockState, boolean below) {
    }
}
