package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.client.DinopediaBioManager;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.Quagga;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.MoodSystem;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.Version;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DinopediaScreen extends Screen {
    private static final ResourceLocation DINOPEDIA_BACKGROUND = Fossil.location("textures/gui/dinopedia.png");
    private static final ResourceLocation MOODS = Fossil.location("textures/gui/dinopedia_mood.png");
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
        super(new TextComponent(""));
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
        poseStack2.translate(0.0, 0.0, 1000.0);
        int scale = 15;
        if (entity instanceof Prehistoric) {
            scale = (int) (35 / entity.getBbWidth());
        } else if (entity instanceof DinosaurEgg) {
            scale = 110;
        }
        poseStack2.scale(scale, scale, scale);
        poseStack2.mulPose(Vector3f.ZP.rotationDegrees(180));
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadow(false);
        //Look at mob from above
        poseStack2.mulPose(Vector3f.XP.rotationDegrees(-30));
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
        if (Version.debugEnabled()) {
            addRenderableWidget(CycleOption.create("options.guiScale", () -> IntStream.rangeClosed(0,
                            Minecraft.getInstance().getWindow().calculateScale(0, Minecraft.getInstance().isEnforceUnicode())).boxed().collect(
                            Collectors.toList()),
                    integer -> integer == 0 ? new TranslatableComponent("options.guiScale.auto") : new TextComponent(Integer.toString(integer)),
                    options -> options.guiScale, (options, option, integer) -> {
                        options.guiScale = integer;
                        minecraft.resizeDisplay();
                    }).createButton(Minecraft.getInstance().options, (width - 200) / 2, 10, 200));
        }
        backButton = addRenderableWidget(new DinopediaPageButton(leftPos + 10, topPos + ySize - 45, 200, 100, false, button -> pageBack()));
        if (entity instanceof Prehistoric) {
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
        if (currentPage < MAX_PAGES - 1) {
            currentPage++;
        }
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        if (forwardButton != null) {
            forwardButton.visible = currentPage < MAX_PAGES - 1;
        }
        backButton.visible = currentPage > 0;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        renderBackgroundLayer(poseStack, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderForegroundLayer(poseStack, mouseX, mouseY, partialTick);
        if (!toolTipList.isEmpty()) {
            renderComponentTooltip(poseStack, toolTipList, mouseX, mouseY);
            toolTipList.clear();
        }
    }

    /**
     * Renders the background texture and the entity
     */
    private void renderBackgroundLayer(PoseStack poseStack, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, DINOPEDIA_BACKGROUND);
        blit(poseStack, leftPos, topPos, 0, 0, xSize, ySize, 390, 390);
        if (currentPage == PAGE_1) {
            renderEntityInDinopedia(leftPos + 100, topPos + 80, entity);
        }
    }

    /**
     * Renders the information about the entity
     */
    private void renderForegroundLayer(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (currentPage == PAGE_1) {
            renderFirstPage(poseStack, mouseX, mouseY);
        } else if (currentPage == PAGE_2) {
            if (currentBio == null) {
                currentBio = loadBio(entity);
            }
            if (!currentBio.isEmpty()) {
                renderPrehistoricBio(poseStack);
            }
        }
    }

    /**
     * Returns the x position so that the scaled element will be centered on the left or right page
     */
    private float getScaledX(boolean left, int width, float scale) {
        return (leftPos + (left ? 0 : xSize / 2f) + (xSize / 2f - width * scale) / 2) / scale;
    }

    private static float roundToHalf(double value) {
        return Math.round(value * 2) / 2f;
    }

    private void renderFirstPage(PoseStack poseStack, int mouseX, int mouseY) {
        int col = (157 << 16) | (126 << 8) | 103;
        if (entity instanceof Animal animal) {
            float embryoProgress = ModCapabilities.getEmbryoProgress(animal);
            if (embryoProgress > 0) {
                float quot = roundToHalf(embryoProgress / (FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION) + 1) * 100);
                var progress = new TranslatableComponent("pedia.fossil.pregnantTime", quot);
                font.draw(poseStack, progress, getScaledX(true, font.width(progress), 1), topPos + 135, col);
                poseStack.pushPose();
                float scale = 1.5f;
                poseStack.scale(scale, scale, scale);
                TranslatableComponent name = new TranslatableComponent("pedia.fossil.pregnant", entity.getType().getDescription());
                font.draw(poseStack, name, getScaledX(true, font.width(name), scale), (topPos + 85) / scale, (66 << 16) | (48 << 8) | 36);
                poseStack.popPose();
            }
        }
        renderFirstPageRight(poseStack, mouseX, mouseY);
        if (entity instanceof Prehistoric dino) {
            poseStack.pushPose();
            float scale = 1.5f;
            poseStack.scale(scale, scale, scale);
            Component name = entity.getType().getDescription();
            font.draw(poseStack, name, getScaledX(true, font.width(name), scale), (topPos + 85) / scale, (66 << 16) | (48 << 8) | 36);
            poseStack.popPose();
            int x = leftPos + 30;
            int y = topPos + 85;
            font.draw(poseStack, new TranslatableComponent("pedia.fossil.age", dino.getAgeInDays()), x, y + 20, col);
            font.draw(poseStack, new TranslatableComponent("pedia.fossil.health", entity.getHealth() + "/" + entity.getMaxHealth()), x, y + 30, col);
            font.draw(poseStack, new TranslatableComponent("pedia.fossil.hunger", dino.getHunger() + "/" + dino.getMaxHunger()), x, y + 40, col);
            var dietText = dino.data().diet().getName();
            renderHoverInfo(poseStack, x, y + 50, mouseX, mouseY, dietText, dino.data().diet().getDescription());
            var tempText = dino.aiResponseType().getName();
            renderHoverInfo(poseStack, x, y + 60, mouseX, mouseY, tempText, dino.aiResponseType().getDescription());
            font.draw(poseStack, dino.getGender().getName(), x, y + 70, col);
            if (dino.getOwner() == null) {
                font.draw(poseStack, new TranslatableComponent("pedia.fossil.untamed"), x, y + 80, col);
            } else {
                font.draw(poseStack, new TranslatableComponent("pedia.fossil.owner", dino.getOwner().getName()), x, y + 80, col);
            }
            var order = dino.getCurrentOrder();
            renderHoverInfo(poseStack, x, y + 90, mouseX, mouseY, order.getName(), order.getDescription());

            font.draw(poseStack, new TranslatableComponent("pedia.fossil.order.item", new TranslatableComponent(dino.getOrderItem().getDescriptionId())), x, y + 100, col);

            var activity = dino.aiActivityType();
            renderHoverInfo(poseStack, x, y + 110, mouseX, mouseY, activity.getName(), activity.getDescription());

            font.draw(poseStack, new TranslatableComponent("pedia.fossil.population", dino.data().maxPopulation()), x, y + 120, col);
        } else if (entity instanceof DinosaurEgg egg) {
            poseStack.pushPose();
            float scale = 1.5f;
            poseStack.scale(scale, scale, scale);
            var name = new TranslatableComponent("pedia.fossil.egg", egg.getPrehistoricEntityInfo().displayName.get());
            font.draw(poseStack, name, getScaledX(true, font.width(name), scale), (topPos + 85) / scale, (66 << 16) | (48 << 8) | 36);
            poseStack.popPose();
            int time = Mth.floor((float) egg.getHatchingTime() / egg.getTotalHatchingTime() * 100);
            var progress = new TranslatableComponent("pedia.fossil.egg.time", Math.max(time, 0));
            font.draw(poseStack, progress, getScaledX(true, font.width(progress), 1), topPos + 120, (157 << 16) | (126 << 8) | 103);

            Component status;
            if (egg.isInWater()) {
                status = new TranslatableComponent("pedia.fossil.egg.status.wet").withStyle(style -> style.withColor(ChatFormatting.AQUA));
            } else {
                if (egg.isTooCold()) {
                    status = new TranslatableComponent("pedia.fossil.egg.status.cold").withStyle(style -> style.withColor(ChatFormatting.BLUE));
                } else {
                    status = new TranslatableComponent("pedia.fossil.egg.status.warm").withStyle(style -> style.withColor(ChatFormatting.GOLD));
                }
            }
            status = new TranslatableComponent("pedia.fossil.egg.status", status);
            font.draw(poseStack, status, getScaledX(true, font.width(status), 1), topPos + 140, (157 << 16) | (126 << 8) | 103);
        } else if (entity instanceof PrehistoricFish || entity instanceof Quagga) {
            poseStack.pushPose();
            float scale = 1.5f;
            poseStack.scale(scale, scale, scale);
            Component name = entity.getType().getDescription();
            font.draw(poseStack, name, getScaledX(true, font.width(name), scale), (topPos + 85) / scale, (66 << 16) | (48 << 8) | 36);
            poseStack.popPose();
        }
    }

    /**
     * Used to render dino info and potentially render its tooltip
     */
    private void renderHoverInfo(PoseStack poseStack, int x, int y, int mouseX, int mouseY, Component text, Component hoverText) {
        font.draw(poseStack, text, x, y, (157 << 16) | (126 << 8) | 103);
        if (mouseX >= x && mouseY >= y && mouseX < x + font.width(text) && mouseY < y + font.lineHeight) {
            toolTipList.add(hoverText);
        }
    }

    /**
     * Renders the mood bar and food list
     */
    private void renderFirstPageRight(PoseStack poseStack, int mouseX, int mouseY) {
        if (entity instanceof Prehistoric dino) {
            RenderSystem.setShaderTexture(0, MOODS);
            poseStack.pushPose();
            float scale = 1.75f;
            poseStack.scale(scale, scale, scale);
            int x = (int) getScaledX(false, MOOD_FACE_WIDTH, scale);
            int y = (int) ((topPos + 16) / scale);
            MoodSystem moodSystem = dino.moodSystem;
            blit(poseStack, x, y, moodSystem.getMoodFace().uOffset, 10, MOOD_FACE_WIDTH, MOOD_FACE_HEIGHT);
            poseStack.popPose();
            x = (int) (x * scale);
            y = (int) (y * scale);
            if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + MOOD_FACE_WIDTH * scale && mouseY < y + MOOD_FACE_HEIGHT * scale) {
                toolTipList.add(moodSystem.getMoodFace().getName());
                toolTipList.add(moodSystem.getMoodFace().getDescription());
            }

            poseStack.pushPose();
            scale = 0.75f;
            poseStack.scale(scale, scale, scale);
            x = (int) getScaledX(false, MOOD_BAR_WIDTH, scale);
            y = (int) ((topPos + 49) / scale);
            blit(poseStack, x, y, 0, 0, MOOD_BAR_WIDTH, MOOD_BAR_HEIGHT);
            poseStack.popPose();
            x = (int) (x * scale);
            y = (int) (y * scale);
            if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + MOOD_BAR_WIDTH * scale && mouseY < y + MOOD_BAR_HEIGHT * scale) {
                var mood = new TextComponent(String.valueOf(moodSystem.getMood())).withStyle(style -> style.withColor(moodSystem.getMoodFace().color));
                toolTipList.add(new TranslatableComponent("pedia.fossil.mood_status", mood));
            }

            poseStack.pushPose();
            x = (int) getScaledX(false, 4, 1);
            y = topPos + 9 + 38;
            blit(poseStack, x - moodSystem.getMoodPosition(), y, 0, 26, 4, 10);
            poseStack.popPose();

            var foodMap = FoodMappings.getFoodRenderList(dino.data().diet());
            var keys = foodMap.keySet().stream().filter(itemLike -> itemLike instanceof Item).sorted(
                    Comparator.comparingInt(item -> Item.getId(item.asItem()))).limit(64).toList();
            int itemCount = 0;
            for (ItemLike itemLike : keys) {
                int renderSize = 16;
                x = (leftPos + xSize / 2 + (xSize / 2 - renderSize * 8) / 2) + renderSize * (itemCount % 8);
                y = topPos + 65 + renderSize * (itemCount / 8);
                itemCount++;
                ItemStack itemStack = new ItemStack(itemLike);
                itemRenderer.renderAndDecorateItem(itemStack, x, y);
                if (toolTipList.isEmpty() && mouseX >= x && mouseY >= y && mouseX < x + renderSize && mouseY < y + renderSize) {
                    toolTipList.addAll(getTooltipFromItem(itemStack));
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
        } else {
            return List.of();
        }
        String bio = DinopediaBioManager.DINOPEDIA.getDinopediaBio(name);
        StringSplitter stringSplitter = font.getSplitter();
        List<String> list = new ArrayList<>();
        stringSplitter.splitLines(bio, xSize / 2, Style.EMPTY, true, (style, i, j) -> {
            String string2 = bio.substring(i, j);
            list.add(StringUtils.stripEnd(string2, " \n"));
        });
        return list;
    }

    private void renderPrehistoricBio(PoseStack poseStack) {
        poseStack.pushPose();
        float scale = 0.75f;
        poseStack.scale(scale, scale, scale);
        int right = 0;
        int left = 0;
        for (int i = 0; i < currentBio.size(); i++) {
            if (i <= 20) {
                font.draw(poseStack, currentBio.get(i), getScaledX(true, xSize / 2, scale), (topPos + 10 + font.lineHeight * ++left) / scale, 0x9D7E67);
            } else {
                font.draw(poseStack, currentBio.get(i), getScaledX(false, xSize / 2, scale), (topPos + 10 + font.lineHeight * ++right) / scale, 0x9D7E67);
            }
        }
        poseStack.popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class DinopediaPageButton extends Button {
        private final boolean isForward;

        public DinopediaPageButton(int x, int y, int width, int height, boolean isForward, OnPress onPress) {
            super(x, y, width, height, new TextComponent(""), onPress);
            this.isForward = isForward;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, DINOPEDIA_BACKGROUND);
            blit(poseStack, x, y, isForward ? 0 : 34, 223, 34, 30);
        }
    }
}
