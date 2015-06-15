package com.Orion.Armory.Util.Client.Color;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * Created by Orion
 * Created on 14.06.2015
 * 11:27
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ColorSampler
{

    private static HashMap<Color, EnumChatFormatting> iMappedColors;

    private static void initializeEnumChatFromattingColors()
    {
        iMappedColors = new HashMap<Color, EnumChatFormatting>();

        for (int tIndex = 0; tIndex < 16; tIndex ++)
        {
            if (EnumChatFormatting.values()[tIndex].name().equals("BLACK"))
                continue;

            iMappedColors.put(new Color(Minecraft.getMinecraft().fontRenderer.colorCode[tIndex]), EnumChatFormatting.values()[tIndex]);
        }
    }

    public static Color getColorSampleFromItemStack(ItemStack pStack)
    {
        if (pStack.getItem().getColorFromItemStack(pStack, 0) != 16777215)
        {
            return new Color(pStack.getItem().getColorFromItemStack(pStack, 0));
        }
        else
        {
            pStack.getItem().registerIcons(new PlaceHolderRegistrar());

            if (pStack.getItem() instanceof ItemBlock)
            {
                ((ItemBlock) pStack.getItem()).field_150939_a.registerBlockIcons(new BlockPlaceHolderRegistrar());
            }

            IconPlaceHolder pIcon = (IconPlaceHolder) pStack.getItem().getIcon(pStack, 0);

            try {
                Color tSample = calculateAverageColor(ImageIO.read((InputStream) Minecraft.getMinecraft().getResourceManager().getResource(pIcon.iIconLocation).getInputStream()));

                return tSample;
            } catch (IOException e) {
                return new Color(16777215);
            }
        }
    }

    public static Color calculateAverageColor(BufferedImage pBuffer) {
        long tSumR = 0, tSumG = 0, tSumB = 0;

        int tCountedPixels = 0;

        for (int tXPos = 0; tXPos < pBuffer.getWidth(); tXPos++) {
            for (int tYPos = 0; tYPos < pBuffer.getHeight(); tYPos++) {
                int tRGB = pBuffer.getRGB(tXPos, tYPos);

                Color tPixel = new Color(tRGB);

                if (tPixel.getAlphaInt() > 0) {
                    tSumR += tPixel.getColorRedInt();
                    tSumG += tPixel.getColorGreenInt();
                    tSumB += tPixel.getColorBlueInt();

                    tCountedPixels++;

                }
            }
        }

        if (tCountedPixels == 0)
        {
            GeneralRegistry.iLogger.info("No pixels counted!");
            return new Color(255,255,255);
        }

        return new Color((int) (tSumR / tCountedPixels),(int)  (tSumG / tCountedPixels),(int)  (tSumB / tCountedPixels));
    }

    public static EnumChatFormatting getChatColorSample(Color pSource) {
        if (iMappedColors == null)
            initializeEnumChatFromattingColors();

        double tCurrentDistance = -1D;
        EnumChatFormatting tCurrentFormatting = null;

        for(Color tColor : iMappedColors.keySet())
        {
            if ((tCurrentDistance < 0) || (colorDistance(pSource, tColor) < tCurrentDistance))
            {
                tCurrentDistance = colorDistance(pSource, tColor);
                tCurrentFormatting = iMappedColors.get(tColor);
            }
        }

        GeneralRegistry.iLogger.info("");
        return tCurrentFormatting;
    }

    private static double colorDistance(Color pColor1, Color pColor2)
    {
        int tRed1 = pColor1.getColorRedInt();
        int tRed2 = pColor2.getColorRedInt();
        int tRedMean = (tRed1 + tRed2) >> 1;
        int tRed = tRed1 - tRed2;
        int tGreen = pColor1.getColorGreenInt() - pColor2.getColorGreenInt();
        int tBlue = pColor1.getColorBlueInt() - pColor2.getColorBlueInt();
        return Math.sqrt((((512+tRedMean)*tRed*tRed)>>8) + 4*tGreen*tGreen + (((767-tRedMean)*tBlue*tBlue)>>8));
    }
}
