package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.client.DinopediaBioLoader;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricShearable;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.MoodSystem;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DinopediaScreen extends Screen {
    private static final ResourceLocation DINOPEDIA_BACKGROUND = FossilMod.location("textures/gui/dinopedia.png");
    private static final ResourceLocation MOODS = FossilMod.location("textures/gui/dinopedia_mood.png");
    private static final LoadingCache<UUID, Component> USERNAMES = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS).build(new CacheLoader<>() {
                @Override
                public @NotNull Component load(@NotNull UUID key) {
                    Player player = Minecraft.getInstance().level.getPlayerByUUID(key);
                    if (player != null) {
                        return player.getName();
                    }
                    GameProfile gameProfile = new GameProfile(key, null);
                    gameProfile = Minecraft.getInstance().getMinecraftSessionService().fillProfileProperties(gameProfile, true);
                    if (gameProfile.isComplete()) {
                        return Component.literal(gameProfile.getName());
                    }
                    return Component.literal("Invalid User");
                }
            });
    private static final Component STUNTED_GROWTH = Component.translatable("pedia.fossil.condition.stunted");
    private static final Component SHEARED = Component.translatable("pedia.fossil.condition.sheared");
    private static final Component NOT_SHEARED = Component.translatable("pedia.fossil.condition.not_sheared");
    private static final int MOOD_FACE_WIDTH = 16;
    private static final int MOOD_FACE_HEIGHT = 15;
    private static final int MOOD_BAR_WIDTH = 206;
    private static final int MOOD_BAR_HEIGHT = 9;
    private static final int PAGE_1 = 0;
    private static final int PAGE_2 = 1;
    private static final int MAX_PAGES = 2;
    private final LivingEntity entity;
    private List<String> currentBio;
    private final List<Component> toolTipList = new ArrayList<>();
    private final int xSize = 390;
    private final int ySize = 245;
    private int leftPos;
    private int topPos;
    private DinopediaPageButton backButton;
    private DinopediaPageButton forwardButton;
    private int currentPage;

    public DinopediaScreen(LivingEntity entity) {
        super(Component.literal(""));
        this.entity = entity;
    }

    /**
     * Renders the entity in the dinopedia with a fixed y-rotation and a scale
     */
    public static void renderEntityInDinopedia(int posX, int posY, LivingEntity entity) {
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(posX, posY, 1050);
        poseStack.scale(1, 1, -1);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack2 = new PoseStack();
        poseStack2.translate(0.0, -10, 1000.0);
        int scale = 25;
        if (entity instanceof Prehistoric) {
            scale = (int) (35 / entity.getBbWidth());
        } else if (entity instanceof DinosaurEgg) {
            scale = 110;
        } else if (entity instanceof Quagga) {
            scale = 35;
        }
        poseStack2.scale(scale, scale, scale);
        poseStack2.mulPose(Axis.ZP.rotationDegrees(180));
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadow(false);
        //Look at mob from above
        poseStack2.mulPose(Axis.XP.rotationDegrees(-30));
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            float yRotO = entity.yBodyRot;
            //Turn mob to the right
            entity.yBodyRot = 110;
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0, 1, poseStack2, bufferSource, 0xF000F0);
            entity.yBodyRot = yRotO;
        });
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    @Override
    protected void init() {
        leftPos = (width - xSize) / 2;
        topPos = (height - ySize) / 2;
        backButton = addRenderableWidget(new DinopediaPageButton(leftPos + 10, topPos + ySize - 45, 200, 100, false, button -> pageBack()));
        if (entity instanceof Prehistoric || entity instanceof Quagga || entity instanceof PrehistoricFish) {
            forwardButton = addRenderableWidget(
                    new DinopediaPageButton(leftPos + xSize - 43, topPos + ySize - 45, 200, 100, true, button -> pageForward()));
        }
        updateButtonVisibility();
    }

    /**
     * Moves the display back one page
     */
    protected void pageBack() {
        if (currentPage > 0) {
            currentPage--;
        }
        updateButtonVisibility();
    }

    /**
     * Moves the display forward one page
     */
    protected void pageForward() {
        if (currentPage < getMaxPages() - 1) {
            currentPage++;
        }
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        if (forwardButton != null) {
            forwardButton.visible = currentPage < getMaxPages() - 1;
        }
        backButton.visible = currentPage > 0;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        renderBackgroundLayer(guiGraphics, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderForegroundLayer(guiGraphics, mouseX, mouseY, partialTick);
        if (!toolTipList.isEmpty()) {
            guiGraphics.renderComponentTooltip(font, toolTipList, mouseX, mouseY);
            toolTipList.clear();
        }
    }

    /**
     * Renders the background texture and the entity
     */
    private void renderBackgroundLayer(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.blit(DINOPEDIA_BACKGROUND, leftPos, topPos, 0, 0, xSize, ySize, 390, 390);
        if (currentPage == PAGE_1) {
            renderEntityInDinopedia(leftPos + 100, topPos + 80, entity);
        }
    }

    /**
     * Renders the information about the entity
     */
    private void renderForegroundLayer(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (currentPage == PAGE_1) {
            renderFirstPage(guiGraphics, mouseX, mouseY);
        } else if (currentPage >= PAGE_2) {
            if (currentBio == null) {
                currentBio = loadBio(entity);
                updateButtonVisibility();
            }
            if (!currentBio.isEmpty()) {
                renderPrehistoricBio(guiGraphics);
            }
        }
    }

    private int getMaxPages() {
        if (currentBio == null) {
            return MAX_PAGES;
        }
        return 2 + currentBio.size() / 42;
    }

    /**
     * Returns the x position so that the scaled element will be centered on the left or right page
     */
    private int getScaledX(boolean left, int width, float scale) {
        return (int) ((leftPos + (left ? 0 : xSize / 2f) + (xSize / 2f - width * scale) / 2) / scale);
    }

    private static float roundToHalf(double value) {
        return Math.round(value * 2) / 2f;
    }

    /**
     * Renders the first page usually containing direct info about the mob/egg
     */
    private void renderFirstPage(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int col = (157 << 16) | (126 << 8) | 103;
        if (entity instanceof Animal animal) {
            float embryoProgress = ModCapabilities.getEmbryoProgress(animal);
            if (embryoProgress > 0) {
                float quot = roundToHalf(embryoProgress / (FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION) + 1) * 100);
                var progress = Component.translatable("pedia.fossil.pregnantTime", quot);
                guiGraphics.drawString(font, progress, getScaledX(true, font.width(progress), 1), topPos + 135, col, false);
                guiGraphics.pose().pushPose();
                float scale = 1.5f;
                guiGraphics.pose().scale(scale, scale, scale);
                MutableComponent name = Component.translatable("pedia.fossil.pregnant", entity.getType().getDescription());
                guiGraphics.drawString(font, name, getScaledX(true, font.width(name), scale), (int) ((topPos + 85) / scale), (66 << 16) | (48 << 8) | 36, false);
                guiGraphics.pose().popPose();
            }
        }
        renderFirstPageRight(guiGraphics, mouseX, mouseY);
        if (entity instanceof Prehistoric dino) {
            guiGraphics.pose().pushPose();
            float scale = 1.5f;
            guiGraphics.pose().scale(scale, scale, scale);
            Component name = entity.getType().getDescription();
            guiGraphics.drawString(font, name, getScaledX(true, font.width(name), scale), (int) ((topPos + 85) / scale), (66 << 16) | (48 << 8) | 36, false);
            guiGraphics.pose().popPose();
            int x = leftPos + 30;
            int y = topPos + 85;
            guiGraphics.drawString(font, Component.translatable("pedia.fossil.age", dino.getAgeInDays()), x, y + 20, col, false);
            guiGraphics.drawString(font, Component.translatable("pedia.fossil.health", entity.getHealth() + "/" + entity.getMaxHealth()), x, y + 30, col, false);
            guiGraphics.drawString(font, Component.translatable("pedia.fossil.hunger", dino.getHunger() + "/" + dino.getMaxHunger()), x, y + 40, col, false);
            var dietText = dino.data().diet().getName();
            renderHoverInfo(guiGraphics, x, y + 50, mouseX, mouseY, dietText, dino.data().diet().getDescription());
            var tempText = dino.aiResponseType().getName();
            renderHoverInfo(guiGraphics, x, y + 60, mouseX, mouseY, tempText, dino.aiResponseType().getDescription());
            guiGraphics.drawString(font, dino.getGender().getName(), x, y + 70, col, false);
            if (dino.getOwnerUUID() == null) {
                guiGraphics.drawString(font, Component.translatable("pedia.fossil.untamed"), x, y + 80, col, false);
            } else {
                try {
                    guiGraphics.drawString(font, Component.translatable("pedia.fossil.owner", USERNAMES.get(dino.getOwnerUUID())), x, y + 80, col, false);
                } catch (ExecutionException e) {
                    guiGraphics.drawString(font, Component.translatable("pedia.fossil.owner", "Invalid User"), x, y + 80, col, false);
                }
            }
            var order = dino.getCurrentOrder();
            renderHoverInfo(guiGraphics, x, y + 90, mouseX, mouseY, order.getName(), order.getDescription());

            guiGraphics.drawString(font, Component.translatable("pedia.fossil.order.item", Component.translatable(dino.getOrderItem().getDescriptionId())), x, y + 100, col, false);

            var activity = dino.aiActivityType();
            renderHoverInfo(guiGraphics, x, y + 110, mouseX, mouseY, activity.getName(), activity.getDescription());

            guiGraphics.drawString(font, Component.translatable("pedia.fossil.population", dino.data().maxPopulation()), x, y + 120, col, false);
        } else if (entity instanceof DinosaurEgg egg) {
            guiGraphics.pose().pushPose();
            float scale = 1.5f;
            guiGraphics.pose().scale(scale, scale, scale);
            var name = Component.translatable("pedia.fossil.egg", egg.getPrehistoricEntityInfo().displayName.get());
            guiGraphics.drawString(font, name, getScaledX(true, font.width(name), scale), (int) ((topPos + 85) / scale), (66 << 16) | (48 << 8) | 36, false);
            guiGraphics.pose().popPose();
            int time = Mth.floor((float) egg.getHatchingTime() / egg.getTotalHatchingTime() * 100);
            var progress = Component.translatable("pedia.fossil.egg.time", Math.max(time, 0));
            guiGraphics.drawString(font, progress, getScaledX(true, font.width(progress), 1), topPos + 120, (157 << 16) | (126 << 8) | 103, false);

            Component status;
            if (egg.isInWater()) {
                status = Component.translatable("pedia.fossil.egg.status.wet").withStyle(style -> style.withColor(ChatFormatting.AQUA));
            } else {
                if (egg.isTooCold()) {
                    status = Component.translatable("pedia.fossil.egg.status.cold").withStyle(style -> style.withColor(ChatFormatting.BLUE));
                } else {
                    status = Component.translatable("pedia.fossil.egg.status.warm").withStyle(style -> style.withColor(ChatFormatting.GOLD));
                }
            }
            status = Component.translatable("pedia.fossil.egg.status", status);
            guiGraphics.drawString(font, status, getScaledX(true, font.width(status), 1), topPos + 140, (157 << 16) | (126 << 8) | 103, false);
        } else if (entity instanceof PrehistoricFish || entity instanceof Quagga) {
            guiGraphics.pose().pushPose();
            float scale = 1.5f;
            guiGraphics.pose().scale(scale, scale, scale);
            Component name = entity.getType().getDescription();
            guiGraphics.drawString(font, name, getScaledX(true, font.width(name), scale), (int) ((topPos + 85) / scale), (66 << 16) | (48 << 8) | 36, false);
            guiGraphics.pose().popPose();
        }
    }

    /**
     * Used to render dino info and potentially render its tooltip
     */
    private void renderHoverInfo(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, Component text, Component hoverText) {
        guiGraphics.drawString(font, text, x, y, (157 << 16) | (126 << 8) | 103, false);
        if (mouseX >= x && mouseY >= y && mouseX < x + font.width(text) && mouseY < y + font.lineHeight) {
            toolTipList.add(hoverText);
        }
    }

    /**
     * Renders the mood bar and food list
     */
    private void renderFirstPageRight(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (entity instanceof Prehistoric dino) {
            guiGraphics.pose().pushPose();
            float scale = 1.75f;
            guiGraphics.pose().scale(scale, scale, scale);
            int x = (int) getScaledX(false, MOOD_FACE_WIDTH, scale);
            int y = (int) ((topPos + 16) / scale);
            MoodSystem moodSystem = dino.moodSystem;
            guiGraphics.blit(MOODS, x, y, moodSystem.getMoodFace().uOffset, 10, MOOD_FACE_WIDTH, MOOD_FACE_HEIGHT);
            guiGraphics.pose().popPose();
            x = (int) (x * scale);
            y = (int) (y * scale);
            if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + MOOD_FACE_WIDTH * scale && mouseY < y + MOOD_FACE_HEIGHT * scale) {
                toolTipList.add(moodSystem.getMoodFace().getName());
                toolTipList.add(moodSystem.getMoodFace().getDescription());
            }

            guiGraphics.pose().pushPose();
            scale = 0.75f;
            guiGraphics.pose().scale(scale, scale, scale);
            x = (int) getScaledX(false, MOOD_BAR_WIDTH, scale);
            y = (int) ((topPos + 49) / scale);
            guiGraphics.blit(MOODS, x, y, 0, 0, MOOD_BAR_WIDTH, MOOD_BAR_HEIGHT);
            guiGraphics.pose().popPose();
            x = (int) (x * scale);
            y = (int) (y * scale);
            if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + MOOD_BAR_WIDTH * scale && mouseY < y + MOOD_BAR_HEIGHT * scale) {
                var mood = Component.literal(String.valueOf(moodSystem.getMood())).withStyle(style -> style.withColor(moodSystem.getMoodFace().color));
                toolTipList.add(Component.translatable("pedia.fossil.mood_status", mood));
            }

            guiGraphics.pose().pushPose();
            x = (int) getScaledX(false, 4, 1);
            y = topPos + 9 + 38;
            guiGraphics.blit(MOODS, x - moodSystem.getMoodPosition(), y, 0, 26, 4, 10);
            guiGraphics.pose().popPose();

            var foodMap = FoodMappingsManager.INSTANCE.getItemCache().get(dino.data().diet());
            var keys = foodMap.stream().limit(64).toList();
            int itemCount = 0;
            int renderSize = 16;
            for (ItemLike itemLike : keys) {
                x = (leftPos + xSize / 2 + (xSize / 2 - renderSize * 8) / 2) + renderSize * (itemCount % 8);
                y = topPos + 65 + renderSize * (itemCount / 8);
                itemCount++;
                ItemStack itemStack = new ItemStack(itemLike);
                guiGraphics.renderItem(itemStack, x, y);
                if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                    toolTipList.addAll(getTooltipFromItem(minecraft, itemStack));
                }
            }
            x = leftPos + 4 + xSize / 2;
            y = topPos + 70 + renderSize * (itemCount / 8);
            if (dino.isAgingDisabled()) {
                x += 16;
                guiGraphics.renderItem(new ItemStack(Items.POISONOUS_POTATO), x, y);
                if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                    toolTipList.add(STUNTED_GROWTH);
                }
            }
            if (dino instanceof PrehistoricShearable shearable) {
                x += 16;
                guiGraphics.renderItem(new ItemStack(Items.SHEARS), x, y);
                if (shearable.isSheared()) {
                    guiGraphics.renderItem(new ItemStack(Items.BARRIER), x, y);
                }
                if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                    toolTipList.add(shearable.isSheared() ? SHEARED : NOT_SHEARED);
                }
            }
            if (Minecraft.getInstance().player.isCreative()) {
                var tag = dino.getDebugTag();
                if (dino.isNoAi() || tag.getBoolean("disableGoalAI") || tag.getBoolean("disableMoveAI") || tag.getBoolean("disableLookAI")) {
                    x += 16;
                    guiGraphics.renderItem(new ItemStack(Items.DEBUG_STICK), x, y);
                    if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                        toolTipList.add(Component.literal(String.format("Disabled AI: %b, Goal: %b, Move: %b, Look: %b", dino.isNoAi(), tag.getBoolean("disableGoalAI"), tag.getBoolean("disableMoveAI"), tag.getBoolean("disableLookAI"))));
                    }
                }
                if (!dino.getVariantId().isBlank()) {
                    x += 16;
                    guiGraphics.renderItem(new ItemStack(Items.RED_DYE), x, y);
                    if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                        toolTipList.add(Component.literal("Variant: " + dino.getVariantId()));
                    }
                }
            }
        }
    }

    private List<String> loadBio(LivingEntity entity) {
        String name;
        if (entity instanceof Prehistoric) {
            name = ((Prehistoric) entity).info().resourceName;
        } else if (entity instanceof PrehistoricFish) {
            name = ((PrehistoricFish) entity).info().resourceName;
        } else if (entity instanceof Quagga) {
            name = "quagga";
        } else {
            return List.of();
        }
        final String bio = DinopediaBioLoader.INSTANCE.getDinopediaBio(name);
        StringSplitter stringSplitter = font.getSplitter();
        List<String> list = new ArrayList<>();
        stringSplitter.splitLines(bio, xSize / 2, Style.EMPTY, true, (style, i, j) -> {
            String string2 = bio.substring(i, j);
            list.add(StringUtils.stripEnd(string2, " \n"));
        });
        return list;
    }

    private void renderPrehistoricBio(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        float scale = 0.75f;
        guiGraphics.pose().scale(scale, scale, scale);
        int right = 0;
        int left = 0;
        int offset = currentPage - 1;
        List<String> currentLines = currentBio.stream().skip(offset * 42L).limit(42).toList();

        for (int i = 0; i < currentLines.size(); i++) {
            if (i <= 20) {//1344, 32 per line
                guiGraphics.drawString(font, currentLines.get(i), getScaledX(true, xSize / 2, scale), (int) ((topPos + 10 + font.lineHeight * ++left) / scale), 0x9D7E67, false);
            } else {
                guiGraphics.drawString(font, currentLines.get(i), getScaledX(false, xSize / 2, scale), (int) ((topPos + 10 + font.lineHeight * ++right) / scale), 0x9D7E67, false);
            }
        }
        guiGraphics.pose().popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class DinopediaPageButton extends Button {
        private final boolean isForward;

        public DinopediaPageButton(int x, int y, int width, int height, boolean isForward, OnPress onPress) {
            super(x, y, width, height, Component.literal(""), onPress, DEFAULT_NARRATION);
            this.isForward = isForward;
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.blit(DINOPEDIA_BACKGROUND, getX(), getY(), isForward ? 0 : 34, 223, 34, 30);
        }
    }
}
