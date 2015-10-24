package com.SmithsModding.Armory.Client.GUI.Components.ToolTips;

import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 22.06.2015
 * 12:16
 * <p/>
 * Copyrighted according to Project specific license
 */
public final class ToolTipRenderer {

    public static boolean renderToolTip(IToolTipProvider pProvider, int pMouseX, int pMouseY) {
        return renderToolTipAt(pProvider, pMouseX, pMouseY, pMouseX - 8, pMouseY + 20);
    }

    public static boolean renderToolTipAt(IToolTipProvider pProvider, int pMouseX, int pMouseY, int pX, int pY) {
        if (pProvider instanceof IComponentManager)
            return ((IComponentManager) pProvider).drawComponentToolTips(pMouseX, pMouseY);

        if (!(pProvider.getToolTipVisibileArea().contains(pMouseX, pMouseY)))
            return false;

        ArrayList<String> tLines = new ArrayList<String>();

        if (tLines == null)
            return false;

        for (int tLineIndex = 0; tLineIndex < pProvider.getToolTipLines().size(); tLineIndex++) {
            tLines.add(pProvider.getToolTipLines().get(tLineIndex).getToolTipLine());
        }

        pProvider.getComponentHost().drawHoveringText(tLines, pX, pY, Minecraft.getMinecraft().fontRenderer);
        RenderHelper.enableGUIStandardItemLighting();

        return true;
    }
}
