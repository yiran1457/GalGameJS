package com.yiran.galgamejs.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class GalButton extends AbstractGalComponent<GalButton> {
    public ResourceLocation texture = new ResourceLocation("galgamejs", "textures/gal/gal_no_checked_option.png");
    public ResourceLocation selectTexture = new ResourceLocation("galgamejs", "textures/gal/gal_checked_option.png");
    public Runnable action = () -> {
    };
    public Component text = Component.literal("");

    public GalButton() {
        mc = Minecraft.getInstance();
        width = 616;
        height = 60;
        x = 0;
        y = 0;
    }
    public GalButton setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public GalButton setSelectTexture(ResourceLocation selectTexture) {
        this.selectTexture = selectTexture;
        return this;
    }

    public GalButton setAction(Runnable action) {
        this.action = action;
        return this;
    }

    public GalButton setText(Component text) {
        this.text = text;
        return this;
    }

    public boolean isSelect(int mouseX, int mouseY) {
        int[] pos = getCurrentPosition();
        int x = pos[0];
        int y = pos[1];
        return mouseX > x && mouseX < x + this.width && mouseY > y && mouseY < y + this.height;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Matrix4f M4 = guiGraphics.pose().last().pose();
        int[] pos = getCurrentPosition();
        int x = pos[0];
        int y = pos[1];

        render(M4,isSelect(mouseX, mouseY) ? selectTexture : texture);

        guiGraphics.drawCenteredString(mc.font, text, x + width / 2, y + height / 2 - 4, -1);
    }

}
