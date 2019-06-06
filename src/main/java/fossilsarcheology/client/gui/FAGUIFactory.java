package fossilsarcheology.client.gui;

import fossilsarcheology.Revival;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.Set;

public class FAGUIFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiFossilConfig(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    private class GuiFossilConfig  extends GuiConfig {
        public GuiFossilConfig(GuiScreen parent) {
            super(parent, new ConfigElement(Revival.config.getCategory("all")).getChildElements(), Revival.MODID, false, false, "Fossils Archeology Confg");
            titleLine2 = Revival.config.getConfigFile().getAbsolutePath();
        }

        @Override
        public void onGuiClosed(){
            super.onGuiClosed();
        }
    }
}
