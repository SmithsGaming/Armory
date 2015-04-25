package com.Orion.Armory.Client.GUI.Components.Ledgers;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 13:05
 * <p/>
 * Copyrighted according to Project specific license
 */
public class InfoLedger extends Ledger
{
    final String[] iTranslatedInfoText;

    public InfoLedger(ArmoryBaseGui pGui, String pTitel, String[] pUntranslatedInfotext, IIcon pIcon) {
        super(pGui);

        iHeader = StatCollector.translateToLocal(pTitel);
        iHeaderIcon = pIcon;
        iBackgroundColor = Colors.Ledgers.RED;
        ArrayList<String> tTranslationsWithSplit = new ArrayList<String>();

        int tMaxWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(iHeader);
        if ( tMaxWidth < 120)
        {
            tMaxWidth = 120;
        }


        for(int tRule = 0; tRule < pUntranslatedInfotext.length; tRule++)
        {
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString((StatCollector.translateToLocal(pUntranslatedInfotext[tRule])), tMaxWidth));
        }

        iMaxWidthOpen = 48 + tMaxWidth;
        iMaxHeightOpen = 24 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) * tTranslationsWithSplit.size() + 8;
        iTranslatedInfoText = tTranslationsWithSplit.toArray(new String[0]);
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        for (int tRule = 0; tRule < iTranslatedInfoText.length; tRule++)
        {
            int iDrawingX = pX + 24 + getOriginOffSet();
            int iDrawingY = pY + 24 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) * tRule;

            if ((iDrawingY + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) <= (pY + iCurrentYExtension - 8))
            {
                drawString(Minecraft.getMinecraft().fontRenderer, iTranslatedInfoText[tRule],iDrawingX, iDrawingY, Colors.Ledgers.BLACK.getColor());
            }
        }
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
