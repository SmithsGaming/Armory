package com.Orion.Armory.Util.Client.Color;
/*
/  Color
/  Created by : Orion
/  Created on : 27/06/2014
*/

import org.lwjgl.opengl.GL11;

public class Color {
    private int iColorRed = 255;
    private int iColorGreen = 255;
    private int iColorBlue = 255;
    private int iAlpha = 255;

    public Color(int pRed, int pGreen, int pBlue) {
        this(pRed, pGreen, pBlue, 255);
    }

    public Color(float pRed, float pGreen, float pBlue) {
        this(pRed, pGreen, pBlue, 1F);
    }

    public Color(int pRed, int pGreen, int pBlue, int pAlpha) {
        if (pRed > 255) {
            pRed = 255;
        }

        if (pGreen > 255) {
            pGreen = 255;
        }

        if (pBlue > 255) {
            pBlue = 255;
        }

        if (pAlpha > 255) {
            pAlpha = 255;
        }

        this.iColorRed = pRed;
        this.iColorGreen = pGreen;
        this.iColorBlue = pBlue;
        this.iAlpha = pAlpha;
    }

    public Color(float pRed, float pGreen, float pBlue, float pAlpha) {
        this((int) (pRed * 255), (int) (pGreen * 255), (int) (pBlue * 255), (int) (pAlpha * 255));
    }

    public Color(int pColor) {
        iAlpha = pColor >> 24 & 255;
        iColorRed = pColor >> 16 & 255;
        iColorGreen = pColor >> 8 & 255;
        iColorBlue = pColor & 255;
    }

    public int getColorRedInt() {
        return this.iColorRed;
    }

    public float getColorRedFloat() {
        return (this.iColorRed / 255F);
    }

    public int getColorGreenInt() {
        return this.iColorGreen;
    }

    public float getColorGreenFloat() {
        return (this.iColorGreen / 255F);
    }

    public int getColorBlueInt() {
        return this.iColorBlue;
    }

    public float getColorBlueFloat() {
        return  (this.iColorBlue / 255F);
    }

    public int getAlphaInt() {
        return this.iAlpha;
    }

    public float getAlphaFloat() {
        return (this.iAlpha / 255F);
    }

    public int getColor() {
        if (iColorRed > 255) {
            iColorRed = 255;
        }

        if (iColorGreen > 255) {
            iColorGreen = 255;
        }

        if (iColorBlue > 255) {
            iColorBlue = 255;
        }

        if (iAlpha > 255) {
            iAlpha = 255;
        }


        int tReturnValue = iAlpha;

        tReturnValue = (tReturnValue << 8) + iColorRed;
        tReturnValue = (tReturnValue << 8) + iColorGreen;
        tReturnValue = (tReturnValue << 8) + iColorBlue;

        return tReturnValue;
    }

    public void setColorRed(int pRed) {
        if (pRed > 255)
        {
            pRed = 255;
        }
        this.iColorRed = pRed;
    }

    public void setColorRed(float pRed) {
        this.setColorRed((int) (pRed * 255F));
    }

    public void setColorBlue(int pBlue) {
        if (pBlue > 255)
        {
            pBlue = 255;
        }
        this.iColorBlue = pBlue;
    }

    public void setColorBlue(float pBlue) {
        this.setColorBlue((int) (pBlue * 255F));
    }

    public void setColorGreen(int pGreen) {
        if(pGreen > 255)
        {
            pGreen = 255;
        }

        this.iColorGreen = pGreen;
    }

    public void setColorGreen(float pGreen) {
        this.setColorGreen((int) (pGreen * 255F));
    }

    public void setAlpha(int pAlpha) {
        if(pAlpha > 255)
        {
            pAlpha = 255;
        }
        this.iAlpha = pAlpha;
    }

    public void setAlpha(float pAlpha) {
        this.setAlpha((int) (pAlpha * 255F));
    }

    public Color Combine(Color pMixed, float pMixingScale)
    {
        return Combine(this, pMixed, pMixingScale);
    }

    public double getAngleInDegrees()
    {
        ColorVector tRedVec = new ColorVector(iColorRed * Math.cos(Math.toRadians(0)), iColorRed * Math.sin(Math.toRadians(0)));
        ColorVector tGreenVec = new ColorVector(iColorGreen * Math.cos(Math.toRadians(120)), iColorGreen * Math.sin(Math.toRadians(120)));
        ColorVector tBlueVec = new ColorVector(iColorBlue * Math.cos(Math.toRadians(240)), iColorBlue * Math.sin(Math.toRadians(240)));

        ColorVector tColorVec = new ColorVector(tRedVec.iColorX + tBlueVec.iColorX + tGreenVec.iColorX, tRedVec.iColorY + tBlueVec.iColorY + tGreenVec.iColorY);

        if (tColorVec.iColorY == 0)
        {
            if (tColorVec.iColorX < -10)
            {
                return 90;
            }
            else if (tColorVec.iColorX > 10)
            {
                return 270;
            }
            else
            {
                return 0;
            }
        }

        if (tColorVec.iColorX == 0)
        {
            if (tColorVec.iColorY < -10)
            {
                return 180;
            }
            else if (tColorVec.iColorY > 10)
            {
                return 0;
            }
            else
            {
                return 0;
            }
        }

        return 360 - (Math.atan((((float) tColorVec.iColorX) / ((float) tColorVec.iColorY))) * (180 / Math.PI));
    }

    public void performGLColor()
    {
        GL11.glColor4f(getColorRedFloat(), getColorGreenFloat(), getColorBlueFloat(), getAlphaFloat());
    }

    public static void resetGLColor()
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }

    public static Color Combine( Color pOriginal, Color pMixed, float pMixingScale)
    {
        int tAlpha = (int) ((1 - pMixingScale) * pOriginal.iAlpha + pMixingScale * pMixed .iAlpha);
        int tRedColor = (int) ((1 - pMixingScale) * pOriginal.iColorRed + pMixingScale * pMixed .iColorRed);
        int tGreenColor = (int) ((1 - pMixingScale) * pOriginal.iColorGreen + pMixingScale * pMixed .iColorGreen);
        int tBlueColor = (int) ((1 - pMixingScale) * pOriginal.iColorBlue + pMixingScale * pMixed .iColorBlue);

        return new Color(tRedColor, tGreenColor, tBlueColor, tAlpha);
    }

    @Override
    public String toString() {
        return "R" + iColorRed + "-G" + iColorGreen + "-B" + iColorBlue;
    }
}
