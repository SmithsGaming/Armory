package com.Orion.Armory.Client.GUI.Components.Core;

import codechicken.lib.vec.Rectangle4i;
import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import net.minecraft.client.gui.Gui;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class Component extends Gui {
    protected ArmoryBaseGui iGui;

    public int iHeight = 0;
    public int iWidth = 0;
    public int iLeft = 0;
    public int iTop = 0;

    public Component(ArmoryBaseGui pGui, int pHeight, int pWidth, int pLeft, int pTop)
    {
        super();

        iGui = pGui;

        iHeight = pHeight;
        iWidth = pWidth;
        iLeft = pLeft;
        iTop = pTop;
    }


    public abstract void onUpdate();

    public int getHeight()
    {
        return iHeight;
    }

    public int getWidth() {return iWidth;}

    public void draw(int pX, int pY)
    {
        drawBackGround(pX, pY);
        drawForeGround(pX, pY);
    }


    public abstract void drawForeGround(int pX, int pY);

    public abstract void drawBackGround(int pX, int pY);

    public abstract void drawToolTips(int pMouseX, int pMouseY);

    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY)
    {
        Rectangle4i tComponentBounds = new Rectangle4i(iLeft, iTop, iWidth, iHeight);

        return tComponentBounds.contains(pTargetX, pTargetY);
    }

    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);
}
