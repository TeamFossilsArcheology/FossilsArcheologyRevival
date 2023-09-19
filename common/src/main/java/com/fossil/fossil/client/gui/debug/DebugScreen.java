package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricDebug;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.AIMessage;
import com.fossil.fossil.network.debug.MovementMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.block.state.BlockState;
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
    private final LivingEntity entity;
    private final List<DebugTab> tabs = new ArrayList<>();
    private DebugTab currentTab;

    public DebugScreen(LivingEntity entity) {
        super(entity == null ? new TextComponent("Debug Screen") : entity.getDisplayName());
        this.entity = entity;
    }

    public static LivingEntity getHitResult(Minecraft mc) {
        Entity camera = mc.getCameraEntity();
        Vec3 view = camera.getViewVector(1.0f);
        double range = 30;
        Vec3 end = camera.getEyePosition().add(view.x * range, view.y * range, view.z * range);
        AABB aabb = camera.getBoundingBox().expandTowards(view.scale(range)).inflate(1);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(camera, camera.getEyePosition(), end, aabb,
                entity1 -> entity1 instanceof LivingEntity, range * range);
        return (entityHitResult != null) ? (LivingEntity) entityHitResult.getEntity() : null;
    }

    public static void showPath(Player player, List<BlockPos> targets, List<BlockState> blocks, boolean below) {
        if (showPaths) {
            for (int i = 0; i < targets.size(); i++) {
                if (below) {
                    pathTargets.add(new PathInfo(targets.get(i), player.level.getBlockState(targets.get(i).below()), true));
                    player.level.setBlock(targets.get(i).below(), blocks.get(i), 3);
                } else {
                    pathTargets.add(new PathInfo(targets.get(i), player.level.getBlockState(targets.get(i)), false));
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
    protected void init() {
        var builder = CycleButton.booleanBuilder(new TextComponent("On"), new TextComponent("Off")).withValues(
                List.of(Boolean.TRUE, Boolean.FALSE));
        if (entity instanceof Prehistoric prehistoric) {
            tabs.add(addWidget(new InfoTab(this, prehistoric)));
           /* xPosInput = this.addRenderableWidget(new EditBox(this.font, 280, height - 40, 50, 20, new TextComponent("")));
            xPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getX()));
            yPosInput = this.addRenderableWidget(new EditBox(this.font, 340, height - 40, 50, 20, new TextComponent("")));
            yPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getY()));
            zPosInput = this.addRenderableWidget(new EditBox(this.font, 400, height - 40, 50, 20, new TextComponent("")));
            zPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getZ()));
            this.addRenderableWidget(new Button(210, height - 40, 130, 20, new TextComponent("Move to Target"), button -> {
                Player player = Minecraft.getInstance().player;
            }));*/
            this.addRenderableWidget(new Button(470, height - 40, 150, 20, new TextComponent("Move to player"), button -> {
                Player player = Minecraft.getInstance().player;
                MessageHandler.DEBUG_CHANNEL.sendToServer(new MovementMessage(entity.getId(), player.getX(), player.getY(), player.getZ()));
            }));
        }
        if (entity instanceof Mob mob && entity instanceof PrehistoricDebug prehistoric) {
            builder.withInitialValue(mob.isNoAi());
            disableAI = builder.create(20, height - 130, width / 6, 20, new TextComponent("Disable AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 0));
            });
            this.addRenderableWidget(disableAI);
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableGoalAI"));
            this.addRenderableWidget(builder.create(20, height - 100, width / 6, 20, new TextComponent("Disable Goal AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 1));
            }));
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableMoveAI"));
            this.addRenderableWidget(builder.create(20, height - 70, width / 6, 20, new TextComponent("Disable Move AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 2));
            }));
            builder.withInitialValue(prehistoric.getDebugTag().getBoolean("disableLookAI"));
            this.addRenderableWidget(builder.create(20, height - 40, width / 6, 20, new TextComponent("Disable Look AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 3));
            }));
        }
        if (entity instanceof PrehistoricAnimatable) {
            tabs.add(addWidget(new AnimationTab(this, entity)));
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
    }

    record PathInfo(BlockPos targetPos, BlockState blockState, boolean below) {
    }
}
