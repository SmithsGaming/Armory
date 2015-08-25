/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Util.Client.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

public class ComponentLabel extends AbstractGUIComponent {

    String iRenderedString;

    public ComponentLabel(IComponentHost pGui, String pInternalName, int pLeft, int pTop, String pUntranslatedID) {
        super(pGui, pInternalName, pLeft, pTop, Minecraft.getMinecraft().fontRenderer.getStringWidth(StatCollector.translateToLocal(pUntranslatedID)), Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        this.iRenderedString = StatCollector.translateToLocal(pUntranslatedID);
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(iRenderedString, pX + iLeft, pY + iTop, Colors.DEFAULT.getColor());
    }

    @Override
    public void drawBackGround(int pX, int pY) {

    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
