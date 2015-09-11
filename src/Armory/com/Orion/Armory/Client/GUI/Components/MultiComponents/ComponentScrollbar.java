/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.Client.GUI.Components.ComponentButton;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.ToolTips.ToolTipRenderer;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Core.Rectangle;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ComponentScrollbar extends AbstractGUIMultiComponent implements IComponentHost {

    int iMinScrollValue;
    int iMaxScrolValue;
    float iScrollValuePerPixel;
    int iPixelPerClick = 4;
    Rectangle iScrollWatchRetangle;
    private ComponentButton iUpButton;
    private ComponentButton iDownButton;
    private ComponentButton iScrollButton;

    public ComponentScrollbar(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pHeight, int pMinScrollValue, int pMaxScrollValue) {
        this(pHost, pInternalName, pLeft, pTop, pHeight, pMinScrollValue, pMaxScrollValue, new Rectangle(pLeft, 0, pTop, 7, pHeight));
    }

    public ComponentScrollbar(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pHeight, int pMinScrollValue, int pMaxScrollValue, Rectangle pScrollWatchRectangle) {
        this(pHost, pInternalName, pLeft, pTop, pHeight, pMinScrollValue, pMaxScrollValue, pScrollWatchRectangle, pMinScrollValue);
    }


    public ComponentScrollbar(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pHeight, int pMinScrollValue, int pMaxScrollValue, Rectangle pScrollWatchRectangle, int pInitializedScrollValue) {
        super(pHost, pInternalName, pLeft, pTop, 7, pHeight);

        iSubComponents = new StandardComponentManager(this);

        iUpButton = new ComponentButton(pHost, pInternalName + ".Up", 0, 0, 7, 10, References.InternalNames.InputHandlers.Components.BUTTONCLICK, Textures.Gui.Basic.Components.Button.UPARROW);
        iScrollButton = new ComponentButton(pHost, pInternalName + ".Scroll", 0, iUpButton.getHeight(), 7, 10, References.InternalNames.InputHandlers.Components.BUTTONCLICK, Textures.Gui.Basic.Components.Button.SCROLLBAR);
        iDownButton = new ComponentButton(pHost, pInternalName + ".Down", 0, pHeight - 10, 7, 10, References.InternalNames.InputHandlers.Components.BUTTONCLICK, Textures.Gui.Basic.Components.Button.DOWNARROW);

        calculateRange(pMinScrollValue, pMaxScrollValue);

        iScrollWatchRetangle = pScrollWatchRectangle;
    }

    public void calculateRange(int pMinvalue, int pMaxvalue) {
        iMinScrollValue = pMinvalue;
        iMaxScrolValue = pMaxvalue;

        int tCurrentDeltaValueFromMin = 0;
        if (iScrollButton.getHeight() - iUpButton.getHeight() > 0 && iScrollValuePerPixel > 0) {
            tCurrentDeltaValueFromMin = (int) (iScrollButton.getHeight() - iUpButton.getHeight() / iScrollValuePerPixel);
        }

        iScrollValuePerPixel = (float) (pMaxvalue - pMinvalue) / (float) (iHeight - iUpButton.iHeight - iScrollButton.iHeight - iDownButton.iHeight);

        if (iScrollValuePerPixel == 0.0) {
            iUpButton.setForceDisabledState(true);
            iDownButton.setForceDisabledState(true);
            iScrollButton.setForceDisabledState(true);
            iScrollButton.iTop = iUpButton.getHeight();
        } else {
            iUpButton.setForceDisabledState(false);
            iDownButton.setForceDisabledState(false);
            iScrollButton.setForceDisabledState(false);
            iScrollButton.iHeight = (int) (iUpButton.getHeight() + (tCurrentDeltaValueFromMin / iScrollValuePerPixel));
        }
    }

    public void setDeltaValuePerClick(int pDelta) {
        iPixelPerClick = (int) (pDelta / iScrollValuePerPixel);
    }

    @Override
    public boolean drawComponentToolTips(int pMouseX, int pMouseY) {
        if (ToolTipRenderer.renderToolTip(iUpButton, pMouseX, pMouseY))
            return true;

        if (ToolTipRenderer.renderToolTip(iDownButton, pMouseX, pMouseY))
            return true;

        return ToolTipRenderer.renderToolTip(iScrollButton, pMouseX, pMouseY);

    }

    @Override
    public void draw(int pX, int pY) {
        drawBackGround(pX, pY);
        drawForeGround(pX, pY);
    }

    @Override
    public void onUpdate() {
        iUpButton.onUpdate();
        iScrollButton.onUpdate();
        iDownButton.onUpdate();

        boolean tCanScroll = false;
        int tNewScrollPos = 0;
        int tMouseY = Minecraft.getMinecraft().currentScreen.height - Mouse.getY() / GuiHelper.GUISCALE - 1 - iHost.getYOrigin();
        int tMouseX = Mouse.getX() / GuiHelper.GUISCALE - iHost.getXOrigin();
        int tMouseScroll = Mouse.getDWheel();

        if (iScrollButton.IsClicked()) {
            tNewScrollPos = tMouseY - iTop;
            tCanScroll = true;
        } else if (iScrollWatchRetangle.contains(tMouseX, tMouseY)) {
            if (tMouseScroll < 0) {
                tNewScrollPos = iScrollButton.iTop + 4;
                tCanScroll = true;
            } else if (tMouseScroll > 0) {
                tNewScrollPos = iScrollButton.iTop - 4;
                tCanScroll = true;
            }
        }

        if (iScrollValuePerPixel == 0.0)
            tCanScroll = false;

        if (tCanScroll && tNewScrollPos != iScrollButton.iTop && tNewScrollPos >= iUpButton.getHeight() && tNewScrollPos <= iHeight - iDownButton.getHeight()) {
            if (tNewScrollPos <= iTop + iUpButton.getHeight()) {
                iScrollButton.iTop = iUpButton.getHeight();
            } else if (tNewScrollPos >= iTop + iHeight - iDownButton.getHeight() - iScrollButton.getHeight()) {
                iScrollButton.iTop = iHeight - iDownButton.getHeight() - iScrollButton.getHeight();
            } else {
                iScrollButton.iTop = tNewScrollPos - iTop;
            }

            updateComponentResult(iScrollButton, References.InternalNames.InputHandlers.Components.BUTTONCLICK, String.valueOf(iScrollButton.iTop));

        }
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        if (iUpButton.checkIfPointIsInComponent(pMouseX - iLeft, pMouseY - iTop) && iUpButton.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton)) {
            int tCurrentXScroll = iScrollButton.iTop - iPixelPerClick;
            if (tCurrentXScroll < iUpButton.getHeight())
                tCurrentXScroll = iUpButton.getHeight();

            iScrollButton.iTop = tCurrentXScroll;
            updateComponentResult(iScrollButton, References.InternalNames.InputHandlers.Components.BUTTONCLICK, String.valueOf(tCurrentXScroll));

            return true;
        } else if (iDownButton.checkIfPointIsInComponent(pMouseX - iLeft, pMouseY - iTop) && iDownButton.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton)) {
            int tCurrentXScroll = iScrollButton.iTop + iPixelPerClick;
            if (tCurrentXScroll > iHeight - iDownButton.getHeight() - iScrollButton.getHeight())
                tCurrentXScroll = iHeight - iDownButton.getHeight() - iScrollButton.getHeight();

            iScrollButton.iTop = tCurrentXScroll;
            updateComponentResult(iScrollButton, References.InternalNames.InputHandlers.Components.BUTTONCLICK, String.valueOf(tCurrentXScroll));

            return true;
        } else if (iScrollButton.checkIfPointIsInComponent(pMouseX - iLeft, pMouseY - iTop) && iScrollButton.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton)) {
            return true;
        } else if ((new Rectangle(iLeft, 0, iTop, iWidth, iHeight)).contains(pMouseX, pMouseY)) {
            iScrollButton.iTop = pMouseY - iTop - iUpButton.getHeight();
            updateComponentResult(iScrollButton, References.InternalNames.InputHandlers.Components.BUTTONCLICK, String.valueOf(pMouseY - iTop - iUpButton.getHeight()));

            return true;
        }

        return false;
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0);

        GL11.glPushMatrix();
        iUpButton.drawBackGround(0, 0);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GuiHelper.drawColoredRect(new Rectangle(0, 0, iUpButton.getHeight(), iWidth, iHeight - iUpButton.getHeight() - iDownButton.getHeight()), Colors.General.GRAY);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        iDownButton.drawBackGround(pX, pY);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        iScrollButton.drawBackGround(pX, pY);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public ContainerArmory getContainer() {
        return iHost.getContainer();
    }

    @Override
    public Object getComponentRelatedObject(String pComponentID) {
        return iHost.getComponentRelatedObject(pComponentID);
    }

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        return iHost.getProgressBarValue(pProgressBarID);
    }

    @Override
    public ItemStack getItemStackInSlot(int pSlotIndex) {
        return iHost.getItemStackInSlot(pSlotIndex);
    }

    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        if (pComponentID.equals(References.InternalNames.InputHandlers.Components.BUTTONCLICK)) {
            iHost.updateComponentResult(this, References.InternalNames.InputHandlers.Components.SCROLL, String.valueOf((int) (iMinScrollValue + (Integer.parseInt(pNewValue) - iUpButton.getHeight()) * iScrollValuePerPixel)));
        }

        iHost.updateComponentResult(pComponent, pComponentID, pNewValue);
    }

    @Override
    public int getXOrigin() {
        return iHost.getXOrigin() + iLeft;
    }

    @Override
    public int getYOrigin() {
        return iHost.getYOrigin() + iTop;
    }

    @Override
    public int getXSize() {
        return iWidth;
    }

    @Override
    public int getYSize() {
        return iHeight;
    }

    @Override
    public void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer) {
        iHost.drawHoveringText(pToolTipLines, pX, pY, pFontRenderer);
    }
}
