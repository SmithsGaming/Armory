package com.Orion.Armory.Util.Client;
/*
/  StringUtils
/  Created by : Orion
/  Created on : 23/01/2015
*/

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;

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
        if (pToSplit == "")
        {
            return new String[]{""};
        }

        int tCurrentChar = -1;
        int tCurrentLine = 0;
        int tLastKnownSplitPoint = -1;
        int tLastKnownSplitPointLength = 0;
        int tLastCutPoint = 0;
        int tCurrentLength = 0;
        String tCurrentString;
        ArrayList<String> tResultingStrings = new ArrayList<String>();

        for(Character c : pToSplit.toCharArray())
        {
            tCurrentChar++;

            tCurrentLength += Minecraft.getMinecraft().fontRenderer.getCharWidth(c);
            if (c.equals(' ') || c.equals('-') || c.equals('.') || c.equals('\n') || tCurrentChar == pToSplit.length() - 1)
            {
                tLastKnownSplitPoint = tCurrentChar;
                tLastKnownSplitPointLength = tCurrentLength;
            }

            if (tCurrentLength > pMaxWidth || tCurrentChar == pToSplit.length() - 1)
            {
                if (tCurrentChar == pToSplit.length() - 1)
                {
                    tLastKnownSplitPoint++;
                }

                System.out.println("Splitting from: " + tLastCutPoint + " to: " + tLastKnownSplitPoint);

                String tNewLine = pToSplit.substring(tLastCutPoint, tLastKnownSplitPoint);
                tResultingStrings.add(tNewLine);

                tLastCutPoint = tLastKnownSplitPoint + 1;
                tCurrentLength -= tLastKnownSplitPointLength;
            }
        }

        String[] tReturnArray = new String[tResultingStrings.size()];

        for(String component : tResultingStrings)
        {
            tReturnArray[tResultingStrings.indexOf(component)] = component;
        }

        return tReturnArray;
    }
}
