package com.Orion.Armory.Common.Armor.TierMedieval;

import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Common.Armor.Core.MLAAddon;

/**
 * Created by Orion on 27-3-2014.
 */
public class ArmorUpgradeMedieval extends MLAAddon
{
    public String iMaterialInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public float iProtection;
    public int iExtraDurability;

    //Constructors
    public ArmorUpgradeMedieval(String pInternalName, String pParentID, String pArmorPositionID, String pMaterialInternalName, String pVisibleName, String pVisibleNameColor, float pProtection, int pExtraDurability, int pMaxUpgrades)
    {
        super(pInternalName + "-" + pMaterialInternalName, pParentID, pArmorPositionID, pMaxUpgrades);
        this.iMaterialInternalName = pMaterialInternalName;
        this.iVisibleName = pVisibleName;
        this.iVisibleNameColor = pVisibleNameColor;
        this.iProtection = pProtection;
        this.iExtraDurability = pExtraDurability;
    }

    @Override
    public boolean validateCrafting(String pAddonIDToCheckAgainst, boolean pInstalled) {
        ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MedievalRegistry.getInstance().getUpgrade(pAddonIDToCheckAgainst);
        if ((this.getAddonPositionID() == tUpgrade.getAddonPositionID()) && (this.getInternalName() != pAddonIDToCheckAgainst))
        {
            return false;
        }

        return true;
    }
}




/*
Old validation code. Might still need it.

public boolean validateCraftingForThisUpgrade(ArrayList<ArmorUpgrade> pInstalledUpgrades, ArrayList<ArmorUpgrade> pNewUpgrades)
    {
        int tInstalledAmount = 0;
        for (ArmorUpgrade tUpgrade : pInstalledUpgrades)
        {
            if (tUpgrade.iType.equals(this.iType))
            {
                tInstalledAmount++;
            }
        }

        if (tInstalledAmount == iMaxUpgrades)
        {
            return false;
        }

        return true;
    }

 */
