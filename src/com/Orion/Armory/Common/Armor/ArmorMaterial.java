package com.Orion.Armory.Common.Armor;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import net.minecraft.item.ItemArmor;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ArmorMaterial
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public ArrayList<Boolean> iActiveParts = new ArrayList<Boolean>();

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pVisibleNameColor, ArrayList<Boolean> pActiveParts)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iActiveParts = pActiveParts;
    }

    public void registerNewActivePart(int pUpgradeID, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeID) != null)
        {
            throw new InvalidParameterException("The given UpgradeID is already registered  for this material. Please use a different one.");
        }

        iActiveParts.add(pUpgradeID, pPartState);
    }

}
