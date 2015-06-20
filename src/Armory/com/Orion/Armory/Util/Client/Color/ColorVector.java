package com.Orion.Armory.Util.Client.Color;

/**
 * Created by Orion
 * Created on 16.06.2015
 * 17:50
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ColorVector
{
    public int iColorX;
    public int iColorY;

    public ColorVector(int pColorX, int pColorY)
    {
        iColorX = pColorX;
        iColorY = pColorY;
    }


    public ColorVector(double pColorX, double pColorY) {
        this((int) pColorX, (int) pColorY);
    }
}
