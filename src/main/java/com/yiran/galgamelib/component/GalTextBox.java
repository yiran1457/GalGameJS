package com.yiran.galgamelib.component;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class GalTextBox extends AbstractGalComponent<GalTextBox> {
    public ResourceLocation texture = new ResourceLocation("galgamejs", "textures/gal/gal_dialog_box.png");
    public boolean thisOver = false;
    public boolean allOver = false;
    public int index = 0;
    public int maxIndex;
    public String[] texts;
    public String textBuffer = "";
    public int tickCount = 0;
    public int textStartX;
    public int textStartY;
    public int textEndX;
    public float textScale;

    public GalTextBox() {
        x = 0;
        y = 0;
        textStartX = 0;
        textStartY = -20;
        textEndX = 390;
        textScale = 1.2F;
        width = 500;
        height = 120;
        mode = Mode.BOTTOM;
        mc = Minecraft.getInstance();
    }

    public GalTextBox setTexts(String text) {
        this.texts = text.split("¿");
        maxIndex = texts.length - 1;
        return this;
    }

    public GalTextBox setTextStartX(int textStartX) {
        this.textStartX = textStartX;
        return this;
    }

    public GalTextBox setTextStartY(int textStartY) {
        this.textStartY = textStartY;
        return this;
    }

    public GalTextBox setTextEndX(int textEndX) {
        this.textEndX = textEndX;
        return this;
    }

    public GalTextBox setTextScale(float textScale) {
        this.textScale = textScale;
        return this;
    }

    public void click(int mouseX, int mouseY) {
        tickCount = 0;
        if (!thisOver) {
            textBuffer = texts[index];
            thisOver = true;
        } else {
            if (index == maxIndex) {
                allOver = true;
            } else {
                index++;
                textBuffer = "";

            }
            thisOver = false;
        }
    }

    public void tick() {
        if (!thisOver) {
            if (textBuffer == texts[index]) {
                thisOver = true;
            }else {
                textBuffer = texts[index].substring(0, Math.min(tickCount, texts[index].length()));
                tickCount++;
            }
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Matrix4f M4 = guiGraphics.pose().last().pose();
        int[] pos = getCurrentPosition();
        int x = pos[0];
        int y = pos[1];
        render(M4, texture);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.scale(textScale, textScale, 1);
        guiGraphics.drawWordWrap(
                mc.font,
                Component.literal(textBuffer),
                x + textStartX,
                y + textStartY,
                textEndX - textStartX,
                -1);
        pose.popPose();
    }
}
