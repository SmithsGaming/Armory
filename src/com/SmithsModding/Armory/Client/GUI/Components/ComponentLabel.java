/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.GUI.Components;

import com.SmithsModding.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Util.Client.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

public class ComponentLabel extends AbstractGUIComponent {

    String iRenderedString;
    int iMaxWidth;

    public ComponentLabel(IComponentHost pGui, String pInternalName, int pLeft, int pTop, String pUntranslatedID, boolean pTranslated) {
        super(pGui, pInternalName, pLeft, pTop, Minecraft.getMinecraft().fontRenderer.getStringWidth(StatCollector.translateToLocal(pUntranslatedID)), Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        if (!pTranslated) {
            this.iRenderedString = StatCollector.translateToLocal(pUntranslatedID);
        } else {
            this.iRenderedString = pUntranslatedID;
        }

        iMaxWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(iRenderedString);
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(iRenderedString, iLeft, iTop, Colors.DEFAULT.getColor());
    }

    @Override
    public void drawBackGround(int pX, int pY) {

    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
