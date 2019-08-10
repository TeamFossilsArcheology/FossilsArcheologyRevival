package fossilsarcheology.client.gui.dinopedia;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.EntityDinosaurEgg;
import fossilsarcheology.server.entity.EntityFishBase;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.EntityQuagga;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.utility.FossilsMammalProperties;
import fossilsarcheology.server.util.FoodMappings;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ItemModelMesherForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiPedia extends GuiScreen {
    private static final ResourceLocation background_image = new ResourceLocation(Revival.MODID, "textures/gui/dinopedia.png");
    private static final ResourceLocation moods = new ResourceLocation(Revival.MODID, "textures/gui/dinopedia_mood.png");
    public final int xGui = 390;
    public final int yGui = 320;
    public ButtonDinopediaPage buttonNextPage;
    public ButtonDinopediaPage buttonPreviousPage;
    public int bookPages;
    public final int bookPagesTotal = 1;
    public ButtonDinopedia buttonIcon;
    protected int xSize = 176;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;
    int left;// counter for text added on the left side
    int right;// same for the right side
    int items;// counter for the minipics down
    private RenderItem itemRender;
    private float mouseX;
    private float mouseY;
    private final FoodSorter sorter;
    private String renderText = "";
    public GuiPedia() {
        super();
        left = 0;
        right = 0;
        items = 0;
        xSize = 390;
        ySize = 245;
        sorter = new FoodSorter();
    }


    public static void renderDinosaur(int posX, int posY, int scaleValue, float renderPitch, EntityLivingBase mob) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        scaleValue -= scaleValue * 0.25F;
        if (mob instanceof EntityPrehistoric) {
            GlStateManager.scale((float) -scaleValue, -(float) scaleValue, (float) scaleValue);
        } else {
            GlStateManager.scale((float) scaleValue, (float) scaleValue, (float) scaleValue);
        }
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (renderPitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, (float) mob.getYOffset(), 0.0F);
        Minecraft.getMinecraft().getRenderManager().playerViewY = 180.0F;
        if (mob instanceof EntityPrehistoric) {
            EntityPrehistoric prehistoric = (EntityPrehistoric) mob;
            float scale = prehistoric.getGender() == 1 ? prehistoric.getMaleSize() * prehistoric.getAgeScale() : 1 * prehistoric.getAgeScale();
            GlStateManager.scale(1 / scale, -1 / scale, -1 / scale);
        }
        GlStateManager.rotate(-45.0F, 0.0F, 1.0F, -0.1F);
        float partialTicks = LLibrary.PROXY.getPartialTicks();
        GlStateManager.rotate(ClientUtils.interpolate(mob.prevRenderYawOffset, mob.renderYawOffset, partialTicks), 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getRenderManager().renderEntity(mob, 0.0D, 0.0D, 0, 0.0F, partialTicks, false);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void renderEgg(int posX, int posY, int scaleValue, float renderYaw, float renderPitch, Entity mob) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (scaleValue), (float) scaleValue, (float) scaleValue);
        float f3 = mob.rotationYaw;
        float f4 = mob.rotationPitch;
        GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(renderPitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        mob.rotationYaw = (float) Math.atan((double) (renderYaw / 40.0F)) * 40.0F;
        mob.rotationPitch = -((float) Math.atan((double) (renderPitch / 40.0F))) * 20.0F;
        GlStateManager.translate(0.0F, (float) mob.getYOffset(), 0.0F);
        GlStateManager.rotate(mob.ticksExisted, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getRenderManager().playerViewY = 180.0F;
        Minecraft.getMinecraft().getRenderManager().renderEntity(mob, 0.0D, 0.0D, 0.0D, 0.0F, 0F, false);
        mob.rotationYaw = f3;
        mob.rotationPitch = f4;
        GlStateManager.disableDepth();

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    public void initGui() {
        buttonList.clear();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        int centerX = (this.width - this.xGui) / 2;
        int centerY = (this.height - this.yGui) / 2;
        this.buttonList.add(this.buttonNextPage = new ButtonDinopediaPage(0, centerX + 350, centerY + 240, true, bookPages));
        this.buttonList.add(this.buttonPreviousPage = new ButtonDinopediaPage(1, centerX + 7, centerY + 240, false, bookPages));
        this.itemRender = Minecraft.getMinecraft().getRenderItem();
        addButtonByPage(bookPages);
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.player.isEntityAlive() || this.mc.player.isDead) {
            this.mc.player.closeScreen();
        }
        renderText = "";
    }

    public void reset() {
        this.left = 0;
        this.right = 0;
        this.items = 0;
    }

    public void addStringLR(String string, boolean left0) {
        this.fontRenderer.drawString(string, 30 + (left0 ? 0 : 121), 12 * ((left0 ? this.left++ : this.right++) + 1), 4210752);
    }

    public void addStringLR(String string, int marginOffset, boolean left0) {
        this.fontRenderer.drawString(string, 30 + (left0 ? marginOffset : 121 + marginOffset), 12 * ((left0 ? this.left++ : this.right++) + 1), 0X9D7E67);
    }

    public void addStringLR(String string, boolean left0, int r, int g, int b) {
        int col = (r << 16) | (g << 8) | b;
        this.fontRenderer.drawString(string, 30 + (left0 ? 0 : 121), 12 * ((left0 ? this.left++ : this.right++) + 1), col);
    }

    public void printStringXY(String str0, int x0, int y0, int r, int g, int b) {
        int col = (r << 16) | (g << 8) | b;
        this.fontRenderer.drawString(str0, x0, y0, col);
    }

    public boolean printItemXY(ItemStack item, int x, int y, int zoom) {
        if (item.getItem() instanceof ItemBlock) {

        } else {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            final int width = scaledResolution.getScaledWidth();
            final int height = scaledResolution.getScaledHeight();
            final int guiLeft = (width - this.xSize) / 2;
            final int guiTop = (height - this.ySize) / 2;
            final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
            final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;

            int drawSize = zoom * 16;

            if (!item.isEmpty()) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                net.minecraft.client.gui.FontRenderer font = null;
                ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
                if(mesher instanceof ItemModelMesherForge && ((ItemModelMesherForge) mesher).getLocation(item) == ModelBakery.MODEL_MISSING){
                    return false;
                }
                this.itemRender.renderItemAndEffectIntoGUI(item, x, y);
                this.itemRender.renderItemOverlayIntoGUI(font, item, x, y, null);
                if (mouseX > x && mouseX < x + drawSize) {
                    if (mouseY > y && mouseY < y + drawSize) {
                        renderText = item.getDisplayName();
                    }
                }
                return true;
            }

            if (drawSize < 0) {
                drawSize = 4;
            }

            if (drawSize == 0) {
                drawSize = 8;
            }

            if (drawSize > 160) {
                drawSize = 160;
            }
        }
        return false;
    }

    public void addMiniItem(ItemStack item) {
        if (this.printItemXY(item, 230 + 16 * (items % 8), 70 + 16 * (items / 8), 1)) {
            items++;
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        if(mc.world != null) {
            this.drawDefaultBackground();
        }
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawGuiContainerBackgroundLayer(p_73863_3_, p_73863_1_, p_73863_2_);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) k, (float) l, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        this.drawGuiContainerForegroundLayer(p_73863_1_, p_73863_2_);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        if(!renderText.isEmpty()){
            this.drawHoveringText(Lists.newArrayList(renderText), p_73863_1_, p_73863_2_, fontRenderer);
        }
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        boolean flag = false;
        if (Revival.PEDIA_OBJECT instanceof EntityPrehistoric || Revival.PEDIA_OBJECT instanceof EntityFishBase || Revival.PEDIA_OBJECT instanceof EntityQuagga) {
            this.buttonNextPage.enabled = true;
        }
        if (bookPages == 0) {
            if (Revival.PEDIA_OBJECT instanceof EntityAnimal) {
                FossilsMammalProperties properties = EntityPropertiesHandler.INSTANCE.getProperties((EntityAnimal) Revival.PEDIA_OBJECT, FossilsMammalProperties.class);
                if (properties != null) {
                    if(properties.isPregnant()){
                        flag = true;
                        EntityAnimal entity = (EntityAnimal) Revival.PEDIA_OBJECT;
                        String s1 = I18n.format(entity.getName());
                        String s2 = "prehistoric.pregnant";
                        int quot = (int) Math.floor(((float) properties.embryoProgress / (float) (Revival.CONFIG_OPTIONS.pregnancyTime + 1) * 100.0F));
                        String s3 = I18n.format("prehistoric.pregnantTime") + " " + String.valueOf(quot) + "%";
                        printStringXY(s3, (-this.fontRenderer.getStringWidth(s3) / 2) + 100, 110, 157, 126, 103);
                        GlStateManager.scale(1.5F, 1.5F, 1.5F);
                        printStringXY(I18n.format(s2) + " " + entity.getName(), (-this.fontRenderer.getStringWidth(I18n.format(s2) + entity.getName()) / 2) + 65, 60, 66, 48, 36);
                        return;
                    }
                }
            }
            if (Revival.PEDIA_OBJECT instanceof EntityLivingBase) {
                renderFirstPage((EntityLivingBase) Revival.PEDIA_OBJECT);
            } else if (Revival.PEDIA_OBJECT instanceof EntityDinosaurEgg) {
                renderFirstPage((EntityDinosaurEgg) Revival.PEDIA_OBJECT);
            } else if (Revival.PEDIA_OBJECT instanceof EntityFishBase) {
                renderFirstPage((EntityFishBase) Revival.PEDIA_OBJECT);
            }
        }
        /*
         * if (Revival.PEDIA_OBJECT instanceof EntityDinoEgg) { ((EntityDinoEgg)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityPregnantCow) { ((EntityPregnantCow)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityPregnantPig) { ((EntityPregnantPig)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityPregnantHorse) { ((EntityPregnantHorse)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityPregnantSheep) { ((EntityPregnantSheep)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityNewPrehistoric) { ((EntityNewPrehistoric)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityFishBase) { ((EntityFishBase)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityQuagga) { ((EntityQuagga)
         * Revival.PEDIA_OBJECT).showPedia(this); } else if (Revival.PEDIA_OBJECT
         * instanceof EntityTerrorBird) { ((EntityTerrorBird)
         * Revival.PEDIA_OBJECT).showPedia(this); }
         */
        else {
            if (Revival.PEDIA_OBJECT instanceof EntityPrehistoric) {
                showPrehistoricBio(((EntityPrehistoric) Revival.PEDIA_OBJECT).type.resourceName);
            } else if (Revival.PEDIA_OBJECT instanceof EntityFishBase) {
                showPrehistoricBio(((EntityFishBase) Revival.PEDIA_OBJECT).selfType.resourceName);
            } else if (Revival.PEDIA_OBJECT instanceof EntityQuagga) {
                showPrehistoricBio("quagga");
            }
        }

    }

    public void showPrehistoricBio(String mobName) {
        this.reset();
        this.addStringLR("", 150, false);
        String translatePath = "dinopedia/" + Minecraft.getMinecraft().gameSettings.language.toLowerCase() + "/";
        String bioFile = mobName + ".txt";
        try {
            IResource resource = this.mc.getResourceManager().getResource(new ResourceLocation("fossil",translatePath + bioFile));
            InputStream fileReader = resource.getInputStream();
            if (getClass().getClassLoader().getResourceAsStream(translatePath) == null) {
                translatePath = "assets/fossil/dinopedia/en_us/";
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader));
            String line;
            int linenumber = 0;
            this.addStringLR("", 250, true);
            while ((line = bufferedReader.readLine()) != null) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.75F, 0.75F, 0.75F);
                if (linenumber <= 20) {
                    this.addStringLR(line, -125, false);
                } else {
                    this.addStringLR(line, 250, true);
                }
                linenumber++;
                GlStateManager.popMatrix();
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            this.addStringLR("File not found.", -125, false);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            this.addStringLR(translatePath + bioFile, 0, false);
            GlStateManager.popMatrix();
        }

    }

    public void renderFirstPage(Entity entity) {
        reset();
        int wordLength = 90;
        if (entity instanceof EntityPrehistoric) {
            EntityPrehistoric dino = (EntityPrehistoric) entity;
            renderFirstPageRight(dino);
            GlStateManager.pushMatrix();
            String s = I18n.format(entity.getName());
            GlStateManager.scale(1.5F, 1.5F, 1.5F);
            printStringXY(I18n.format(entity.getName()), (-this.fontRenderer.getStringWidth(s) / 2) + 65, 60, 66, 48, 36);
            GlStateManager.popMatrix();
            {
                String s1 = I18n.format("pedia.age") + " " + dino.getAgeInDays();
                printStringXY(s1, wordLength / 2, 110, 157, 126, 103);
            }
            {
                String s1 = I18n.format("pedia.health") + " " + Math.min(Math.round(dino.getHealth()), dino.getMaxHealth()) + "/" + dino.getMaxHealth();
                printStringXY(s1, wordLength / 2, 120, 157, 126, 103);
            }
            {
                String s1 = I18n.format("pedia.hunger") + " " + dino.getHunger() + "/" + dino.getMaxHunger();
                printStringXY(s1, wordLength / 2, 130, 157, 126, 103);
            }
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                final int width = scaledResolution.getScaledWidth();
                final int height = scaledResolution.getScaledHeight();
                final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
                final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
                String s1 = I18n.format("pedia.diet") + " " + I18n.format("pedia.diet." + dino.type.diet.toString().toLowerCase());
                int x = wordLength / 2;
                int y = 140;
                printStringXY(s1, x, y, 157, 126, 103);
                if (mouseX > x && mouseX < x + this.fontRenderer.getStringWidth(s1)) {
                    if (mouseY > y && mouseY < y + 10) {
                        List<String> text = new ArrayList<>();
                        text.add(I18n.format("pedia.diet." + dino.type.diet.toString().toLowerCase() + ".desc"));
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }
            }
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                final int width = scaledResolution.getScaledWidth();
                final int height = scaledResolution.getScaledHeight();
                final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
                final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
                String s1 = I18n.format("pedia.temperament") + " " + I18n.format(dino.getTempermentString());
                int x = wordLength / 2;
                int y = 150;
                printStringXY(s1, x, y, 157, 126, 103);
                if (mouseX > x && mouseX < x + this.fontRenderer.getStringWidth(s1)) {
                    if (mouseY > y && mouseY < y + 10) {
                        List<String> text = new ArrayList<>();
                        text.add(I18n.format(dino.getTempermentString() + ".desc"));
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }
            }
            {
                String s1 = I18n.format("pedia.gender") + " " + (dino.getGender() == 0 ? I18n.format("pedia.gender.female") : I18n.format("pedia.gender.male"));
                printStringXY(s1, wordLength / 2, 160, 157, 126, 103);
            }
            {
                String name = dino.getOwner() == null ? "" : dino.getOwner().getName();
                String s1 = I18n.format("pedia.untame");
                String s2 = I18n.format("pedia.owner") + " " + name;
                printStringXY(!name.equals("") ? s2 : s1, wordLength / 2, 170, 157, 126, 103);
            }
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                final int width = scaledResolution.getScaledWidth();
                final int height = scaledResolution.getScaledHeight();
                final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
                final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
                String s1 = I18n.format("pedia.order") + " " + I18n.format("pedia.order." + dino.currentOrder.toString().toLowerCase());
                int x = wordLength / 2;
                int y = 180;
                printStringXY(s1, x, y, 157, 126, 103);
                if (mouseX > x && mouseX < x + this.fontRenderer.getStringWidth(s1)) {
                    if (mouseY > y && mouseY < y + 10) {
                        List<String> text = new ArrayList<>();
                        text.add(I18n.format(I18n.format("pedia.order." + dino.currentOrder.toString().toLowerCase() + ".desc")));
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }
            }
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                final int width = scaledResolution.getScaledWidth();
                final int height = scaledResolution.getScaledHeight();
                final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
                final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
                String s1 = I18n.format("pedia.order.item") + ": " + new ItemStack(dino.getOrderItem()).getDisplayName();
                int x = wordLength / 2;
                int y = 190;
                printStringXY(s1, x, y, 157, 126, 103);
            }
            {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                final int width = scaledResolution.getScaledWidth();
                final int height = scaledResolution.getScaledHeight();
                final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
                final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
                String s1 = I18n.format("pedia.activity") + " " + I18n.format("pedia.activity." + dino.aiActivityType().toString().toLowerCase());
                int x = wordLength / 2;
                int y = 200;
                printStringXY(s1, x, y, 157, 126, 103);
                if (mouseX > x && mouseX < x + this.fontRenderer.getStringWidth(s1)) {
                    if (mouseY > y && mouseY < y + 10) {
                        List<String> text = new ArrayList<>();
                        text.add(I18n.format(I18n.format("pedia.activity." + dino.aiActivityType().toString().toLowerCase() + ".desc")));
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
        if (entity instanceof EntityDinosaurEgg) {
            EntityDinosaurEgg egg = (EntityDinosaurEgg) entity;
            GlStateManager.pushMatrix();
            String s = I18n.format(egg.selfType.friendlyName + " " + I18n.format("pedia.egg"));
            GlStateManager.scale(1.5F, 1.5F, 1.5F);
            printStringXY(s, (-this.fontRenderer.getStringWidth(s) / 2) + 65, 60, 66, 48, 36);
            GlStateManager.popMatrix();
            {
                int time = (int) Math.floor(((float) egg.getBirthTick() / (float) egg.totalHatchTime * 100.0F));
                String s1 = I18n.format("pedia.egg.time") + " " + Math.max(time, 0) + "%";
                printStringXY(s1, wordLength / 2, 120, 157, 126, 103);
            }
            {
                String s1;
                if (egg.isInWater()) {
                    s1 = I18n.format("pedia.egg.status" + " " + ChatFormatting.AQUA + I18n.format("pedia.egg.status.wet"));
                } else {
                    if (!egg.isCold()) {
                        s1 = I18n.format("pedia.egg.status") + " " + ChatFormatting.GOLD + I18n.format("pedia.egg.status.warm");
                    } else {
                        s1 = I18n.format("pedia.egg.status") + " " + ChatFormatting.BLUE + I18n.format("pedia.egg.status.cold");
                    }
                }
                printStringXY(s1, wordLength / 2, 140, 157, 126, 103);
            }
        }
        if (entity instanceof EntityFishBase) {
            String s1 = I18n.format(entity.getName());
            GlStateManager.scale(1.5F, 1.5F, 1.5F);
            printStringXY(I18n.format(entity.getName()), (-this.fontRenderer.getStringWidth(s1) / 2) + 65, 60, 66, 48, 36);
        }
        if (entity instanceof EntityQuagga) {
            String s1 = I18n.format(entity.getName());
            GlStateManager.scale(1.5F, 1.5F, 1.5F);
            printStringXY(I18n.format(entity.getName()), (-this.fontRenderer.getStringWidth(s1) / 2) + 65, 60, 66, 48, 36);
        }
    }

    private void renderFirstPageRight(EntityLivingBase entity) {
        if (entity instanceof EntityPrehistoric) {
            EntityPrehistoric dino = (EntityPrehistoric) entity;
            {

                float scale = 1.75F;
                GlStateManager.pushMatrix();
                GlStateManager.translate(1F, 0, 0);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.renderEngine.bindTexture(moods);
                this.drawTexturedModalRect(160, 7, dino.getMoodFace().uv, 10, 16, 15);
                GlStateManager.popMatrix();
            }
            {
                float scale = 0.75F;
                GlStateManager.pushMatrix();
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.renderEngine.bindTexture(moods);
                this.drawTexturedModalRect(290, 60, 0, 0, 206, 9);
                GlStateManager.popMatrix();
            }
            {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.renderEngine.bindTexture(moods);
                this.drawTexturedModalRect(293 - dino.getScaledMood(), 43, 0, 26, 4, 10);
                GlStateManager.popMatrix();
            }
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            final int width = scaledResolution.getScaledWidth();
            final int height = scaledResolution.getScaledHeight();
            final int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
            final int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;
            {

                int x = 218;
                int y = 40;
                if (mouseX > x && mouseX < x + 154) {
                    if (mouseY > y && mouseY < y + 13) {
                        List<String> text = new ArrayList<>();
                        text.add(I18n.format("pedia.moodstatus") + " " + dino.getMoodFace().color + dino.getMood());
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }
            }
            {
                int x = 280;
                int y = 10;
                if (mouseX > x && mouseX < x + 28) {
                    if (mouseY > y && mouseY < y + 28) {
                        List<String> text = new ArrayList<>();
                        text.add(dino.getMoodFace().color + I18n.format(I18n.format("pedia.") + dino.getMoodFace().toString().toLowerCase()));
                        text.add(ChatFormatting.GRAY + I18n.format(I18n.format("pedia.") + dino.getMoodFace().toString().toLowerCase() + I18n.format(".desc")));
                        GlStateManager.pushMatrix();
                        this.drawHoveringText(text, mouseX, mouseY, fontRenderer);
                        GlStateManager.popMatrix();
                    }
                }

                Map<ItemStack, Integer> foodMap = FoodMappings.INSTANCE.getFoodRenderList(dino.type.diet);
                List<ItemStack> keys = Collections.list(Collections.enumeration(foodMap.keySet()));
                List<ItemStack> displayedKeys = new ArrayList<ItemStack>();
                keys.sort(this.sorter);
                ItemStack[] keyArray = keys.toArray(new ItemStack[0]);
                for (ItemStack item : keyArray) {
                    if (!item.isEmpty() && shouldShowItem(item, displayedKeys)) {
                        if (items < 64) {
                            addMiniItem(item);
                            displayedKeys.add(item);
                        }
                    }
                }
            }
        }
    }

    private boolean shouldShowItem(ItemStack item, List<ItemStack> keys){
        for(ItemStack stack : keys){
            if(stack.getItem() == item.getItem() && stack.getMetadata() == item.getMetadata()){
                return false;
            }
        }
        return true;
    }

    public void renderSecondPage(EntityLivingBase entity) {

    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(background_image);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawModalRectWithCustomSizedTexture(k, l, 0, 0, this.xSize, this.ySize, (390.625F), (390.625F));
        if (bookPages == 0) {
            GlStateManager.pushMatrix();
            if (Revival.PEDIA_OBJECT instanceof EntityDinosaurEgg) {
                renderEgg(k + 100, l + 80, 150, 0, 0, (EntityDinosaurEgg) Revival.PEDIA_OBJECT);
            } else if (Revival.PEDIA_OBJECT instanceof EntityLivingBase) {
                if (Revival.PEDIA_OBJECT instanceof EntityPrehistoric) {
                    renderDinosaur(k + 100, l + 80 - ((EntityPrehistoric) Revival.PEDIA_OBJECT).pediaY, Math.round(2 * ((EntityPrehistoric) Revival.PEDIA_OBJECT).pediaScale), 0, (EntityLivingBase) Revival.PEDIA_OBJECT);
                } else {
                    if (Revival.PEDIA_OBJECT instanceof AbstractHorse) {
                        renderDinosaur(k + 100, l + 80, 60, 0, (EntityLivingBase) Revival.PEDIA_OBJECT);
                    } else {
                        renderDinosaur(k + 100, l + 80, 80, 0, (EntityLivingBase) Revival.PEDIA_OBJECT);
                    }
                }
            }

            GlStateManager.popMatrix();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public void addButtonByPage(int page) {
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;

        if (page == 0) {
            this.buttonList.add(this.buttonIcon = new ButtonDinopedia(2, centerX + 35, centerY + 55, 0));
            this.buttonList.add(this.buttonIcon = new ButtonDinopedia(3, centerX + 75, centerY + 55, 2));
            this.buttonList.add(this.buttonIcon = new ButtonDinopedia(4, centerX + 115, centerY + 55, 4));
            this.buttonList.add(this.buttonIcon = new ButtonDinopedia(5, centerX + 55, centerY + 95, 6));
            this.buttonList.add(this.buttonIcon = new ButtonDinopedia(6, centerX + 95, centerY + 95, 8));
        }
    }

    public void triggerButtons(GuiButton button) {
        switch (button.id) {
            case 2:
                this.bookPages = 1;
                break;

            case 3:
                this.bookPages = 2;
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0 && bookPages < bookPagesTotal) {
            bookPages += 1;
        } else if (button.id == 1 && bookPages > 0) {
            bookPages -= 1;
        }

        this.triggerButtons(button);
        this.initGui();
        this.updateScreen();
    }

    public class FoodSorter implements Comparator<ItemStack> {
        @Override
        public int compare(ItemStack stack1, ItemStack stack2) {
            int stackId1 = Item.getIdFromItem(stack1.getItem());
            int stackId2 = Item.getIdFromItem(stack2.getItem());
            if (stackId1 == stackId2) {
                return Double.compare(stack1.getItemDamage(), stack2.getItemDamage());
            } else {
                return Integer.compare(stackId1, stackId2);
            }
        }
    }
}
