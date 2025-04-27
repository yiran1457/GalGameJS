package com.yiran.galgamejs.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
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

    public GalTextBox() {
        x = 0;
        y = 0;
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
        render(M4, texture);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(mc.getWindow().getGuiScaledWidth() / 2, mc.getWindow().getGuiScaledHeight() - 95, 1.5);
        pose.scale(1.2F, 1.2F, 1);
        guiGraphics.drawWordWrap(
                mc.font,
                Component.literal(textBuffer),
                -120,
                0,
                300,
                -1);
        pose.popPose();
    }
}
