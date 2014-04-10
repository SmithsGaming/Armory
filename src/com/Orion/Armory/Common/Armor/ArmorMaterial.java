package com.Orion.Armory.Common.Armor;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import net.minecraft.item.ItemArmor;
import scala.Int;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ArmorMaterial
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public boolean iBaseArmorMaterial;
    public ArrayList<Integer> iBaseDamageAbsorption;
    public ArrayList<Integer> iBaseDurability;
    public ArrayList<Integer> iPartModifiers;
    public ArrayList<Boolean> iActiveParts = new ArrayList<Boolean>();

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pVisibleNameColor, boolean pBaseArmorMaterial, ArrayList<Integer> pBaseDamageAbsorption,ArrayList<Integer> pBaseDurability, ArrayList<Integer> pPartModifiers, ArrayList<Boolean> pActiveParts)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iBaseDamageAbsorption = pBaseDamageAbsorption;
        iBaseDurability = pBaseDurability;
        iPartModifiers = pPartModifiers;
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

    public void setBaseDamageAbsorption(int pTargetArmorID, int pBaseDamageAbsorption)
    {
        iBaseDamageAbsorption.set(pTargetArmorID, pBaseDamageAbsorption);
    }

    public int getBaseDamageAbsorption(int pTargetArmorID)
    {
        return iBaseDamageAbsorption.get(pTargetArmorID);
    }

    public void setBaseDurability(int pTargetArmorID, int pBaseDurability)
    {
        iBaseDurability.set(pTargetArmorID, pBaseDurability);
    }

    public int getBaseDurability(int pTargetArmorID)
    {
        return iBaseDurability.get(pTargetArmorID);
    }

    public void setMaxModifiersOnPart(int pTargetArmorID, int pMaxModifiers)
    {
        iPartModifiers.set(pTargetArmorID, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(int pTargetArmorID) { return iPartModifiers.get(pTargetArmorID);}

}
