package com.Orion.Armory.Client.GUI.Components.ToolTips;

import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 22.06.2015
 * 12:16
 * <p/>
 * Copyrighted according to Project specific license
 */
public final class ToolTipRenderer {

    public static boolean renderToolTip(IToolTipProvider pProvider, int pMouseX, int pMouseY)
    {
        int tScaledMouseX = pMouseX / GuiHelper.GUISCALE;
        int tScaledMouseY = pMouseY / GuiHelper.GUISCALE;

        if (!(pProvider.getToolTipVisibileArea().contains(pMouseX - pProvider.getBaseGui().guiLeft, pMouseY - pProvider.getBaseGui().guiTop)))
            return false;

        ArrayList<String> tLines = new ArrayList<String>();
        for (int tLineIndex = 0; tLineIndex < pProvider.getToolTipLines().size(); tLineIndex ++)
        {
            tLines.add(pProvider.getToolTipLines().get(tLineIndex).getToolTipLine());
        }

        pProvider.getBaseGui().drawHoveringText(tLines, pMouseX - 110, pMouseY + 8, Minecraft.getMinecraft().fontRenderer);

        return true;
    }
}
