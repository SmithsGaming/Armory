package com.Orion.Armory.Client.GUI.Components.Core;

import codechicken.lib.vec.Rectangle4i;
import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class AbstractGUIComponent extends Gui implements IGUIComponent
{
    protected ArmoryBaseGui iGui;

    public int iHeight = 0;
    public int iWidth = 0;
    public int iLeft = 0;
    public int iTop = 0;

    String iInternalName;

    public AbstractGUIComponent(ArmoryBaseGui pGui, String pInternalName, int pHeight, int pWidth, int pLeft, int pTop)
    {
        super();

        iGui = pGui;

        iInternalName = pInternalName;

        iHeight = pHeight;
        iWidth = pWidth;
        iLeft = pLeft;
        iTop = pTop;
    }

    public String getInternalName()
    {
        return iInternalName;
    }

    public abstract void onUpdate();

    public int getHeight()
    {
        return iHeight;
    }

    public int getWidth() {return iWidth;}

    public void bindTexture(String pTextureAddress)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(pTextureAddress));
    }

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
        Rectangle tComponentBounds = new Rectangle(iLeft, iTop, iWidth, iHeight);

        return tComponentBounds.contains(pTargetX, pTargetY);
    }

    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);
}
