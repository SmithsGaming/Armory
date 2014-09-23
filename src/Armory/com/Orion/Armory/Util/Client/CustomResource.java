package com.Orion.Armory.Util.Client;
/*
/  CustomResource
/  Created by : Orion
/  Created on : 15/06/2014
*/

import net.minecraft.util.IIcon;

import java.util.ArrayList;

public class CustomResource {
    private String iInternalName;

    private ArrayList<String> iRescourceLocations = new ArrayList<String>();
    private IIcon iIcon;
    private Color iColor;

    public CustomResource(String pInternalName, String pIconLocation) {
        this(pInternalName, pIconLocation, "");
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation) {
        this(pInternalName, pIconLocation, pModelLocation, 255, 255, 255);
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, int pRed, int pGreen, int pBlue) {
        this(pInternalName, pIconLocation, pModelLocation, new Color(pRed, pGreen, pBlue));
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, Color pColor) {
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iRescourceLocations.add(pModelLocation);
        iColor = pColor;
    }

    public String getInternalName() {
        return this.iInternalName;
    }

    public String getIconLocation() {
        return iRescourceLocations.get(0);
    }

    public void addIcon(IIcon pIcon) {
        iIcon = pIcon;
    }

    public IIcon getIcon() {
        return iIcon;
    }

    public String getModelLocation() {
        return iRescourceLocations.get(1);
    }

    public int getColorInt(int pIndex) {
        switch (pIndex) {
            case 0:
                return iColor.getColorRedInt();
            case 1:
                return iColor.getColorGreenInt();
            case 2:
                return iColor.getColorBlueInt();
            default:
                return 255;
        }
    }

    public float getColorFloat(int pIndex) {
        switch (pIndex) {
            case 0:
                return iColor.getColorRedFloat();
            case 1:
                return iColor.getColorGreenFloat();
            case 2:
                return iColor.getColorBlueFloat();
            default:
                return 1F;
        }
    }

    public Color getColor() {
        return iColor;
    }
}