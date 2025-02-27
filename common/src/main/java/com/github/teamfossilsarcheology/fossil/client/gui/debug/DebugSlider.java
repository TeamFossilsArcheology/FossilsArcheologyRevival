package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;

public class DebugSlider extends AbstractSliderButton {

    private final DecimalFormat format;
    private final Component prefix;
    private final Component suffix;
    protected final double stepSize;
    protected final boolean drawString;
    protected double minValue;
    protected double maxValue;

    DebugSlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue,
                double stepSize, int precision, boolean drawString) {
        super(x, y, width, height, Component.literal(""), 0D);
        this.prefix = prefix;
        this.suffix = suffix;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepSize = Math.abs(stepSize);
        this.value = this.snapToNearest((currentValue - minValue) / (maxValue - minValue));
        this.drawString = drawString;

        if (stepSize == 0D) {
            precision = Math.min(precision, 4);

            StringBuilder builder = new StringBuilder("0");

            if (precision > 0) {
                builder.append('.');
            }

            while (precision-- > 0) {
                builder.append('0');
            }

            this.format = new DecimalFormat(builder.toString());
        } else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
            this.format = new DecimalFormat("0");
        } else {
            this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
        }

        this.updateMessage();
    }

    public double getValue() {
        return this.value * (maxValue - minValue) + minValue;
    }

    public void setValue(double value) {
        this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
        this.updateMessage();
    }

    public String getValueString() {
        return this.format.format(this.getValue());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        this.setValueFromMouse(mouseX);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean flag = keyCode == GLFW.GLFW_KEY_LEFT;
        if (flag || keyCode == GLFW.GLFW_KEY_RIGHT) {
            if (this.minValue > this.maxValue) {
                flag = !flag;
            }
            float f = flag ? -1F : 1F;
            if (stepSize <= 0D) {
                this.setSliderValue(this.value + (f / (this.width - 8)), false);
            } else {
                this.setValue(this.getValue() + f * this.stepSize);
            }
        }

        return false;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        setSliderValue(value, true);
    }

    private void setValueFromMouse(double mouseX) {
        this.setSliderValue((mouseX - (this.getX() + 4)) / (this.width - 8), false);
    }

    public void setSliderValue(double value, boolean force) {
        double oldValue = this.value;
        this.value = this.snapToNearest(value);
        if (force || !Mth.equal(oldValue, this.value)) {
            this.applyValue();
        }

        this.updateMessage();
    }

    public double snapToNearest(double value) {
        if (stepSize <= 0D) {
            return Mth.clamp(value, 0D, 1D);
        }

        value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.minValue, this.maxValue);

        value = (stepSize * Math.round(value / stepSize));

        if (this.minValue > this.maxValue) {
            value = Mth.clamp(value, this.maxValue, this.minValue);
        } else {
            value = Mth.clamp(value, this.minValue, this.maxValue);
        }

        return Mth.map(value, this.minValue, this.maxValue, 0D, 1D);
    }

    @Override
    protected void updateMessage() {
        if (this.drawString) {
            this.setMessage(Component.literal("").append(prefix).append(this.getValueString()).append(suffix));
        } else {
            this.setMessage(Component.literal(""));
        }
    }

    @Override
    protected void applyValue() {
    }
}
