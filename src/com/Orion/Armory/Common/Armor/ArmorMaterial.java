package com.Orion.Armory.Common.Armor;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import net.minecraft.item.ItemArmor;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ArmorMaterial
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public boolean iBaseArmorMaterial;
    public ArrayList<Boolean> iActiveParts = new ArrayList<Boolean>();

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pVisibleNameColor, boolean pBaseArmorMaterial,ArrayList<Boolean> pActiveParts)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iActiveParts = pActiveParts;
    }

    public void registerNewActivePart(int pUpgradeID, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeID) != null)
        {
            throw new InvalidParameterException("The given upgrade: " + ARegistry.iInstance.getUpgrade(pUpgradeID).iVisibleName + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.add(pUpgradeID, pPartState);
    }

    public void modifyPartState(int pUpgradeID, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeID) == null)
        {
            throw new InvalidParameterException("The given upgrade: " + ARegistry.iInstance.getUpgrade(pUpgradeID).iVisibleName + ", is not registered for the following material: "+ iVisibleName + ". Something went wrong as this should have happened when registering the material!");
        }

        iActiveParts.set(pUpgradeID, pPartState);
    }

}
