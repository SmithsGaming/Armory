package com.SmithsModding.Armory.Common.Addons;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MaterialDependentMLAAddon;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Orion on 27-3-2014.
 */
public class ArmorUpgradeMedieval extends MaterialDependentMLAAddon {
    public String iMaterialInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public float iProtection;
    public int iExtraDurability;

    //Constructors
    public ArmorUpgradeMedieval (String pInternalName, String pParentID, String pArmorPositionID, String pMaterialInternalName, String pVisibleName, String pVisibleNameColor, float pProtection, int pExtraDurability, int pMaxUpgrades, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation) {
        super(pInternalName, pMaterialInternalName, pParentID, pArmorPositionID, pMaxUpgrades, itemTextureWhole, modelTextureLocation, 1);
        this.iMaterialInternalName = pMaterialInternalName;
        this.iVisibleName = pVisibleName;
        this.iVisibleNameColor = pVisibleNameColor;
        this.iProtection = pProtection;
        this.iExtraDurability = pExtraDurability;
    }

    @Override
    public boolean validateCrafting(String pAddonIDToCheckAgainst, boolean pInstalled) {
        MLAAddon tUpgrade = MedievalAddonRegistry.getInstance().getUpgrade(pAddonIDToCheckAgainst);
        return !((this.getAddonPositionID() == tUpgrade.getAddonPositionID()) && (this.getUniqueID() != pAddonIDToCheckAgainst));

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
