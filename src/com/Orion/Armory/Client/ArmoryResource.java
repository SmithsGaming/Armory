package com.Orion.Armory.Client;
/*
/  ArmoryResource
/  Created by : Orion
/  Created on : 23/04/2014
*/

import net.minecraft.util.IIcon;

import java.util.ArrayList;

public class ArmoryResource
{
    private String iInternalName;

    private ArrayList<String> iRescourceLocations = new ArrayList<String>();
    private IIcon iIcon;
    private ArrayList<Integer> iColoredOverlays = new ArrayList<Integer>();

    public ArmoryResource(String pInternalName, String pIconLocation)
    {
        this(pInternalName, pIconLocation, "");
    }

    public ArmoryResource(String pInternalName, String pIconLocation, String pModelLocation)
    {
        this(pInternalName, pIconLocation, pModelLocation, 255, 255, 255);
    }

    public ArmoryResource(String pInternalName, String pIconLocation, String pModelLocation, int pRed, int pGreen, int pBlue)
    {
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iRescourceLocations.add(pModelLocation);
        iColoredOverlays.add(pRed);
        iColoredOverlays.add(pGreen);
        iColoredOverlays.add(pBlue);
    }

    public String getIconLocation()
    {
        return iRescourceLocations.get(0);
    }

    public void addIcon(IIcon pIcon)
    {
        iIcon = pIcon;
    }

    public IIcon getIcon()
    {
        return iIcon;
    }

    public String getModelLocation()
    {
        return iRescourceLocations.get(1);
    }

    public int getColor(int pIndex)
    {
        return iColoredOverlays.get(pIndex);
    }


}
