/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ComponentButton extends AbstractGUIComponent {

    Integer iUpdateTicksSinceLastClick = 0;
    String iInputID;
    boolean iIsEnabled = true;
    boolean iForceDisabled = false;

    CustomResource iForegroundResource;

    String iForegroundText;
    Color iTextColor;

    public ComponentButton(IComponentHost pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight, String pInputID, CustomResource pForegroundResource) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

        iInputID = pInputID;

        iForegroundResource = pForegroundResource;
    }

    public ComponentButton(IComponentHost pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight, String pInputID, String pForegroundText, Color pTextColor) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

        iInputID = pInputID;

        iForegroundText = pForegroundText;
        iTextColor = pTextColor;
    }

    @Override
    public void onUpdate() {
        if (iUpdateTicksSinceLastClick > 0) {
            if (!org.lwjgl.input.Mouse.isButtonDown(0)) {
                iUpdateTicksSinceLastClick--;
            } else {
                iUpdateTicksSinceLastClick = 5;
            }
        }

        if (iForceDisabled) {
            iIsEnabled = false;
        } else {
            iIsEnabled = iHost.getComponentRelatedObject(getInternalName() + ".enabled") != null && (Integer) iHost.getComponentRelatedObject(getInternalName() + ".enabled") > 0;

            iIsEnabled = true;
        }
    }

    @Override
    public void drawForeGround(int pX, int pY) {

    }

    @Override
    public void drawBackGround(int pX, int pY) {
        if (!iIsEnabled) {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Disabled.TEXTURE, getWidth(), getHeight(), new Coordinate(iLeft, iTop, (int) zLevel));
        } else if (iUpdateTicksSinceLastClick > 0) {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Clicked.TEXTURE, getWidth(), getHeight(), new Coordinate(iLeft, iTop, (int) zLevel));
        } else {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Standard.TEXTURE, getWidth(), getHeight(), new Coordinate(iLeft, iTop, (int) zLevel));
        }

        if (iForegroundResource != null) {
            int tX = (iWidth - iForegroundResource.getWidth()) / 2;
            int tY = (iHeight - iForegroundResource.getHeigth()) / 2;

            GL11.glPushMatrix();
            iForegroundResource.getColor().performGLColor();
            GuiHelper.bindTexture(iForegroundResource.getPrimaryLocation());
            GuiHelper.drawTexturedModalRect(iLeft + tX, iTop + tY, 0, iForegroundResource.getU(), iForegroundResource.getV(), iForegroundResource.getWidth(), iForegroundResource.getHeigth());
            Color.resetGLColor();
            GL11.glPopMatrix();
        } else {
            int tX = (iWidth - Minecraft.getMinecraft().fontRenderer.getStringWidth(iForegroundText)) / 2;
            int tY = (iHeight - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2;

            GL11.glPushMatrix();
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(iForegroundText, iLeft + tX, iTop + tY, iTextColor.getColor());
            Color.resetGLColor();
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        if (pMouseButton == 0 && iIsEnabled) {
            iHost.getContainer().HandleCustomInput(iInputID, "Clicked");
            iUpdateTicksSinceLastClick = 5;

            return true;
        }

        return false;
    }

    public boolean IsClicked() {
        return iUpdateTicksSinceLastClick == 5;
    }

    public void setForceDisabledState(boolean pState) {
        iForceDisabled = pState;
        iIsEnabled = !pState;
    }
}
