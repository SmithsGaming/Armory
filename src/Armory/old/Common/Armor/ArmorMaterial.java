package old.Common.Armor;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.Orion.OrionsBelt.Client.Util.CustomResource;
import old.Common.ARegistry;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class ArmorMaterial
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public boolean iBaseArmorMaterial;
    public HashMap<Integer, CustomResource> iResources = new HashMap<Integer, CustomResource>();
    public HashMap<Integer, Boolean> iActiveParts = new HashMap<Integer, Boolean>();
    public HashMap<Integer, Float> iBaseDamageAbsorption = new HashMap<Integer, Float>();
    public HashMap<Integer, Integer> iBaseDurability = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> iPartModifiers = new HashMap<Integer, Integer>();

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pVisibleNameColor, boolean pBaseArmorMaterial, HashMap<Integer, Float> pBaseDamageAbsorption, HashMap<Integer, Integer> pBaseDurability, HashMap<Integer, Integer> pPartModifiers, HashMap<Integer, Boolean> pActiveParts)
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
        if ((iActiveParts.size() != 0) && (iActiveParts.get(pUpgradeID) != null))
        {
            throw new InvalidParameterException("The given upgrade: " + ARegistry.iInstance.getUpgrade(pUpgradeID).iVisibleName + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.put(pUpgradeID, pPartState);
    }

    public void modifyPartState(int pUpgradeID, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeID) == null)
        {
            throw new InvalidParameterException("The given upgrade: " + ARegistry.iInstance.getUpgrade(pUpgradeID).iVisibleName + ", is not registered for the following material: "+ iVisibleName + ". Something went wrong as this should have happened when registering the material!");
        }

        iActiveParts.remove(pUpgradeID);
        iActiveParts.put(pUpgradeID, pPartState);
    }

    public void setBaseDamageAbsorption(int pTargetArmorID, Float pBaseDamageAbsorption)
    {
        iBaseDamageAbsorption.put(pTargetArmorID, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption(int pTargetArmorID)
    {
        return iBaseDamageAbsorption.get(pTargetArmorID);
    }

    public void setBaseDurability(int pTargetArmorID, int pBaseDurability)
    {
        iBaseDurability.put(pTargetArmorID, pBaseDurability);
    }

    public int getBaseDurability(int pTargetArmorID)
    {
        return iBaseDurability.get(pTargetArmorID);
    }

    public void setMaxModifiersOnPart(int pTargetArmorID, int pMaxModifiers)
    {
        iPartModifiers.put(pTargetArmorID, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(int pTargetArmorID) { return iPartModifiers.get(pTargetArmorID);}

    public void registerResource(CustomResource pResource)
    {
        iResources.put(iResources.size(), pResource);
    }

    public CustomResource getResource(int pTargetArmorID)
    {
        return iResources.get(pTargetArmorID);
    }

}
