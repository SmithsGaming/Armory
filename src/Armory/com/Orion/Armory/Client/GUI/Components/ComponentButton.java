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

public class ComponentButton extends AbstractGUIComponent {

    Integer iUpdateTicksSinceLastClick = 0;
    String iInputID;
    boolean iIsEnabled = true;

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

        /*if (iHost.getComponentRelatedObject(getInternalName() + ".enabled") != null && (Integer) iHost.getComponentRelatedObject(getInternalName() + ".enabled") > 0)
            iIsEnabled = true;
        else
            iIsEnabled = false;
*/

        iIsEnabled = true;
    }

    @Override
    public void drawForeGround(int pX, int pY) {

    }

    @Override
    public void drawBackGround(int pX, int pY) {
        if (!iIsEnabled) {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Disabled.TEXTURE, getWidth(), getHeight(), new Coordinate(pX + iLeft, pY + iTop, (int) zLevel));
        } else if (iUpdateTicksSinceLastClick > 0) {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Clicked.TEXTURE, getWidth(), getHeight(), new Coordinate(pX + iLeft, pY + iTop, (int) zLevel));
        } else {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Standard.TEXTURE, getWidth(), getHeight(), new Coordinate(pX + iLeft, pY + iTop, (int) zLevel));
        }
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        if (pMouseButton == 0) {
            iHost.getContainer().HandleCustomInput(iInputID, "Clicked");
            iUpdateTicksSinceLastClick = 5;

            return true;
        }

        return false;
    }
}
