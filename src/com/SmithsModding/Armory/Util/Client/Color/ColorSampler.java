package com.SmithsModding.Armory.Util.Client.Color;

import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by Orion
 * Created on 14.06.2015
 * 11:27
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ColorSampler {

    private static HashMap<Color, EnumChatFormatting> iMappedColors;

    private static void initializeEnumChatFromattingColors() {
        iMappedColors = new HashMap<Color, EnumChatFormatting>();

        for (int tIndex = 0; tIndex < 16; tIndex++) {
            if (EnumChatFormatting.values()[tIndex].name().equals("BLACK"))
                continue;

            Color tMappedColor = new Color(Minecraft.getMinecraft().fontRenderer.colorCode[tIndex]);
            GeneralRegistry.iLogger.info("Generated Color Code : " + tMappedColor.getColorRedInt() + "-" + tMappedColor.getColorGreenInt() + "-" + tMappedColor.getColorBlueInt() + " for the following EnumChatFormatting: " + EnumChatFormatting.values()[tIndex].name() + ".");

            iMappedColors.put(tMappedColor, EnumChatFormatting.values()[tIndex]);
        }
    }

    public static Color getColorSampleFromItemStack(ItemStack pStack) {
        if (pStack.getItem().getColorFromItemStack(pStack, 0) != 16777215) {
            return new Color(pStack.getItem().getColorFromItemStack(pStack, 0));
        } else {
            pStack.getItem().registerIcons(new PlaceHolderRegistrar());

            if (pStack.getItem() instanceof ItemBlock) {
                ((ItemBlock) pStack.getItem()).field_150939_a.registerBlockIcons(new BlockPlaceHolderRegistrar());
            }

            IconPlaceHolder pIcon = (IconPlaceHolder) pStack.getItem().getIcon(pStack, 0);

            try {
                Color tSample = calculateAverageColor(ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(pIcon.iIconLocation).getInputStream()));

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

        if (tCountedPixels == 0) {
            GeneralRegistry.iLogger.info("No pixels counted!");
            return new Color(255, 255, 255);
        }

        return new Color((int) (tSumR / tCountedPixels), (int) (tSumG / tCountedPixels), (int) (tSumB / tCountedPixels));
    }

    public static EnumChatFormatting getChatColorSample(Color pSource) {
        if (iMappedColors == null)
            initializeEnumChatFromattingColors();

        double tCurrentDistance = -1D;
        EnumChatFormatting tCurrentFormatting = null;

        for (Color tColor : iMappedColors.keySet()) {
            if (colorDistance(pSource, tColor) < tCurrentDistance) {
                tCurrentDistance = colorDistance(pSource, tColor);
                tCurrentFormatting = iMappedColors.get(tColor);
            } else if (tCurrentDistance < 0) {
                tCurrentDistance = colorDistance(pSource, tColor);
                tCurrentFormatting = iMappedColors.get(tColor);
            }
        }
        return tCurrentFormatting;
    }

    public static EnumChatFormatting getSimpleChatColor(Color pSource) {
        String tFormat = "\u00a7";

        if (pSource.getColor() == -1) {
            tFormat = tFormat + "7";
        } else {
            tFormat = tFormat + Integer.toHexString(pSource.getColor());
        }

        for (EnumChatFormatting tFormatting : EnumChatFormatting.values())
            if (tFormatting.toString().toLowerCase().equals(tFormat.toLowerCase()))
                return tFormatting;

        return EnumChatFormatting.RESET;
    }

    private static double colorDistance(Color pColor1, Color pColor2) {
        if ((pColor1.getColorRedInt() > pColor1.getColorBlueInt() * 2) && (pColor1.getColorRedInt() > pColor1.getColorGreenInt() * 2)) {
            if ((pColor1.getColorRedInt() > pColor2.getColorRedInt()))
                return pColor1.getColorRedInt() - pColor2.getColorRedInt();

            return pColor2.getColorRedInt() - pColor1.getColorRedInt();
        }

        if ((pColor1.getColorBlueInt() > pColor1.getColorRedInt() * 2) && (pColor1.getColorBlueInt() > pColor1.getColorGreenInt() * 2)) {
            if ((pColor1.getColorBlueInt() > pColor2.getColorBlueInt()))
                return pColor1.getColorBlueInt() - pColor2.getColorBlueInt();

            return pColor2.getColorBlueInt() - pColor1.getColorBlueInt();
        }

        if ((pColor1.getColorGreenInt() > pColor1.getColorBlueInt() * 2) && (pColor1.getColorGreenInt() > pColor1.getColorRedInt() * 2)) {
            if ((pColor1.getColorGreenInt() > pColor2.getColorGreenInt()))
                return pColor1.getColorGreenInt() - pColor2.getColorGreenInt();

            return pColor2.getColorGreenInt() - pColor1.getColorGreenInt();
        }

        return Math.abs(pColor1.getAngleInDegrees() - pColor2.getAngleInDegrees());

        /*
        if ((pColor1.getColorRedInt() > pColor1.getColorBlueInt() * 2) && (pColor1.getColorRedInt() > pColor1.getColorGreenInt() * 2))
        {
            if((pColor1.getColorRedInt() > pColor2.getColorRedInt()))
                return  pColor1.getColorRedInt() - pColor2.getColorRedInt();

            return pColor2.getColorRedInt() - pColor1.getColorRedInt();
        }

        if ((pColor1.getColorBlueInt() > pColor1.getColorRedInt() * 2) && (pColor1.getColorBlueInt() > pColor1.getColorGreenInt() * 2))
        {
            if((pColor1.getColorBlueInt() > pColor2.getColorBlueInt()))
                return  pColor1.getColorBlueInt() - pColor2.getColorBlueInt();

            return pColor2.getColorBlueInt() - pColor1.getColorBlueInt();
        }

        if ((pColor1.getColorGreenInt() > pColor1.getColorBlueInt() * 2) && (pColor1.getColorGreenInt() > pColor1.getColorRedInt() * 2))
        {
            if((pColor1.getColorGreenInt() > pColor2.getColorGreenInt()))
                return  pColor1.getColorGreenInt() - pColor2.getColorGreenInt();

            return pColor2.getColorGreenInt() - pColor1.getColorGreenInt();
        }


        int tRed1 = pColor1.getColorRedInt();
        int tRed2 = pColor2.getColorRedInt();
        int tRedMean = (tRed1 + tRed2) >> 1;
        int tRed = tRed1 - tRed2;
        int tGreen = pColor1.getColorGreenInt() - pColor2.getColorGreenInt();
        int tBlue = pColor1.getColorBlueInt() - pColor2.getColorBlueInt();
        return Math.sqrt((((512+tRedMean)*tRed*tRed)>>8) + 4*tGreen*tGreen + (((767-tRedMean)*tBlue*tBlue)>>8));
        */
    }
}
