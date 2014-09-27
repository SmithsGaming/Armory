package com.Orion.Armory.Common.Armor.TierPlated;
/*
 *   PlatedMaterial
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.Client.Util.Colors;
import com.Orion.Armory.Common.Registry.PlatedRegistry;
import com.Orion.Armory.Util.Client.Color;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class ArmorMaterialPlated {
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public boolean iBaseArmorMaterial;
    public Color iColor = Colors.IRON;
    public HashMap<String, Boolean> iActiveParts = new HashMap<String, Boolean>();
    public HashMap<String, Float> iBaseDamageAbsorption = new HashMap<String, Float>();
    public HashMap<String, Integer> iBaseDurability = new HashMap<String, Integer>();
    public HashMap<String, Integer> iPartModifiers = new HashMap<String, Integer>();

    //Constructor
    public ArmorMaterialPlated(String pInternalName, String pVisibleName, String pVisibleNameColor, boolean pBaseArmorMaterial, HashMap<String, Float> pBaseDamageAbsorption, HashMap<String, Integer> pBaseDurability, HashMap<String, Integer> pPartModifiers, HashMap<String, Boolean> pActiveParts, Color pColor)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iBaseDamageAbsorption = pBaseDamageAbsorption;
        iBaseDurability = pBaseDurability;
        iPartModifiers = pPartModifiers;
        iActiveParts = pActiveParts;
        iColor = pColor;
    }

    public void registerNewActivePart(String pUpgradeInternalName, boolean pPartState)
    {
        if ((iActiveParts.size() != 0) && (iActiveParts.get(pUpgradeInternalName) != null))
        {
            throw new InvalidParameterException("The given upgrade: " + PlatedRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public void modifyPartState(String pUpgradeInternalName, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeInternalName) == null)
        {
            throw new InvalidParameterException("The given upgrade: " + PlatedRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is not registered for the following material: "+ iVisibleName + ". Something went wrong as this should have happened when registering the material!");
        }
        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption)
    {
        iBaseDamageAbsorption.put(pTargetArmorInternalName, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption(String pTargetArmorInternalName)
    {
        return iBaseDamageAbsorption.get(pTargetArmorInternalName);
    }

    public void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability)
    {
        iBaseDurability.put(pTargetArmorInternalName, pBaseDurability);
    }

    public int getBaseDurability(String pTargetArmorInternalName)
    {
        if (iBaseDurability.containsKey(pTargetArmorInternalName)) return iBaseDurability.get(pTargetArmorInternalName);

        return 100;
    }

    public void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers)
    {
        iPartModifiers.put(pTargetArmorInternalName, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(String pTargetArmorInternalName) { return iPartModifiers.get(pTargetArmorInternalName);}

    public void setColor(Color pColor)
    {
        this.iColor = pColor;
    }

    public Color getColor()
    {
        return this.iColor;
    }
}
