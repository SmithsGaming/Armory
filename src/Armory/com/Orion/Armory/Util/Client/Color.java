package com.Orion.Armory.Util.Client;
/*
/  Color
/  Created by : Orion
/  Created on : 27/06/2014
*/

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

        if (iAlpha > 255) {
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
        iColorRed = pColor >> 16 & 255;
        iColorGreen = pColor >> 8 & 255;
        iColorBlue = pColor & 255;
    }

    public int getColorRedInt() {
        return this.iColorRed;
    }

    public float getColorRedFloat() {
        return (float) (this.iColorRed / 255F);
    }

    public int getColorGreenInt() {
        return this.iColorGreen;
    }

    public float getColorGreenFloat() {
        return (float) (this.iColorGreen / 255F);
    }

    public int getColorBlueInt() {
        return this.iColorBlue;
    }

    public float getColorBlueFloat() {
        return (float) (this.iColorBlue / 255F);
    }

    public int getAlphaInt() {
        return this.iAlpha;
    }

    public float getAlphaFloat() {
        return (float) (this.iAlpha / 255F);
    }

    public int getColor() {
        int tReturnValue = iColorRed;

        tReturnValue = (tReturnValue << 8) + iColorGreen;
        tReturnValue = (tReturnValue << 8) + iColorBlue;

        return tReturnValue;
    }

    public void setColorRed(int pRed) {
        this.iColorRed = pRed;
    }

    public void setColorRed(float pRed) {
        this.setColorRed((int) (pRed * 255F));
    }

    public void setColorBlue(int pBlue) {
        this.iColorBlue = pBlue;
    }

    public void setColorBlue(float pBlue) {
        this.setColorBlue((int) (pBlue * 255F));
    }

    public void setColorGreen(int pGreen) {
        this.iColorGreen = pGreen;
    }

    public void setColorGreen(float pGreen) {
        this.setColorGreen((int) (pGreen * 255F));
    }

    public void setAlpha(int pRed) {
        this.iAlpha = pRed;
    }

    public void setAlpha(float pRed) {
        this.setAlpha((int) (pRed * 255F));
    }
}
