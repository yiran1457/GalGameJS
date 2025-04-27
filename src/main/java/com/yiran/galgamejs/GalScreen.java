package com.yiran.galgamejs;

import com.yiran.galgamejs.component.GalButton;
import com.yiran.galgamejs.component.GalTextBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GalScreen extends Screen {
    public List<GalButton> buttons = new ArrayList<>();
    @Nullable
    public GalTextBox textBox;
    public GalScreen(Component title) {
        super(title);
    }

    public GalScreen setTextBox(GalTextBox textBox) {
        this.textBox = textBox;
        return this;
    }

    public GalScreen addGalButton(GalButton button) {
        buttons.add(button);
        return this;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        for (GalButton button : buttons) {
            if(textBox != null && !textBox.allOver)
                break;
            button.render(pGuiGraphics, pMouseX, pMouseY);
        }
        if (textBox != null) {
            textBox.render(pGuiGraphics, pMouseX, pMouseY);
        }
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            boolean clickButton = false;

            for (GalButton button : buttons) {
                if(textBox != null && !textBox.allOver)
                    break;
                if(button.isSelect((int) pMouseX, (int) pMouseY)){
                    button.action.run();
                    button.mc.player.playSound(SoundEvents.UI_BUTTON_CLICK.get(),1,1);
                    clickButton = true;
                    break;
                }
            }

            if (!clickButton) {
                if (textBox != null) {
                    textBox.click((int) pMouseX, (int) pMouseY);
                }
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void tick() {
        if (textBox != null) {
            textBox.tick();
        }
    }
}
