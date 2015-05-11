package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

/**
 * Created by Orion
 * Created on 11.05.2015
 * 15:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentTextbox extends GuiTextField implements IGUIComponent
{

    public ComponentTextbox(FontRenderer pFontRenderer, int pX, int pY, int pWidth, int pHeight) {
        super(pFontRenderer, pX, pY, pWidth, pHeight);

        setTextColor(-1);
        setDisabledTextColour(-1);
        setEnableBackgroundDrawing(false);
        setMaxStringLength(40);
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public String getInternalName() {
        return null;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void draw(int pX, int pY) {
        this.drawTextBox();
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawToolTips(int pMouseX, int pMouseY) {
        //NOOP
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        Rectangle tBox = new Rectangle(this.xPosition, this.yPosition, this.width, this.height);
        return tBox.contains(pTargetX, pTargetY);
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        this.mouseClicked(pMouseX, pMouseY, pMouseButton);
        return true;
    }
}
