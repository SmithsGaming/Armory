package com.Orion.Armory.Util.Client;
/*
/  StringUtils
/  Created by : Orion
/  Created on : 23/01/2015
*/

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class StringUtils
{
    public static int GetMininumWidth(String[] pStrings, FontRenderer pCurrentFont)
    {
        int tCurrentMaximum = 0;

        for(int tRule = 0; tRule < pStrings.length; tRule++)
        {
            String tCurrentLine = pStrings[tRule];

            if (pCurrentFont.getStringWidth(tCurrentLine) > tCurrentMaximum)
            {
                tCurrentMaximum = pCurrentFont.getStringWidth(tCurrentLine);
            }
        }

        return tCurrentMaximum;
    }

    public static String[] SplitString(String pToSplit, int pMaxWidth)
    {
        return (String[]) Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(pToSplit, pMaxWidth).toArray();
    }
}
