package com.yiran.galgamejs.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public abstract class AbstractGalComponent<T> {
    public Mode mode;
    public Minecraft mc;
    public int x;
    public int y;
    public int width;
    public int height;

    public T setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public T setCenteredPosition(int x, int y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
        return (T) this;
    }

    public T scale(float scale) {
        this.width = (int) (this.width * scale);
        this.height = (int) (this.height * scale);
        return (T) this;
    }

    public T setMode(GalButton.Mode mode) {
        this.mode = mode;
        return (T) this;
    }

    public int[] getCurrentPosition() {
        int GSWH = mc.getWindow().getGuiScaledWidth() / 2;
        int GSHH = mc.getWindow().getGuiScaledHeight() / 2;
        return switch (mode) {
            case TOP -> new int[]{this.x + GSWH, this.y};
            case BOTTOM -> new int[]{this.x + GSWH, this.y + 2 * GSHH};
            case CENTER -> new int[]{this.x + GSWH, this.y + GSHH};
            case LEFT -> new int[]{this.x, this.y + GSHH};
            case RIGHT -> new int[]{this.x + 2 * GSWH, this.y + GSHH};
            default -> new int[]{this.x, this.y};
        };
    }

    public abstract void render(GuiGraphics guiGraphics, int mouseX, int mouseY);

    public void render(Matrix4f matrix4f, ResourceLocation texture) {
        int[] pos = getCurrentPosition();
        int x = pos[0];
        int y = pos[1];
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        bufferBuilder.vertex(matrix4f, x, y, 0).uv(0, 0).endVertex();
        bufferBuilder.vertex(matrix4f, x, y + this.height, 0).uv(0, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + this.width, y + this.height, 0).uv(1, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + this.width, y, 0).uv(1, 0).endVertex();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    public enum Mode {
        TOP,
        BOTTOM,
        CENTER,
        DEFAULT,
        LEFT,
        RIGHT
    }

}
