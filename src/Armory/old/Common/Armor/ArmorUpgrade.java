package old.Common.Armor;

import com.Orion.OrionsBelt.Client.Util.CustomResource;
import net.minecraft.inventory.InventoryCrafting;

import java.util.ArrayList;

/**
 * Created by Orion on 27-3-2014.
 */
public class ArmorUpgrade
{
    public int iMaterialID;
    public int iTargetArmorID;
    public int iUpgradeLocation;
    public String iInternalName;
    public String iType;
    public String iVisibleName;
    public String iVisibleNameColor;
    public CustomResource iResource;
    public float iProtection;
    public int iExtraDurability;
    public int iMaxUpgrades;
    public InventoryCrafting iCrafingInventory;

    //Constructors
    public ArmorUpgrade(int pMaterialID, int pTargetArmorID, int pUpgradeLocation, String pInternalName, String pType, String pVisibleName, String pVisibleNameColor, CustomResource pResource, float pProtection, int pExtraDurability, int pMaxUpgrades, InventoryCrafting pCraftingInventory)
    {
        iMaterialID = pMaterialID;
        iTargetArmorID = pTargetArmorID;
        iUpgradeLocation = pUpgradeLocation;
        iInternalName = pInternalName;
        iType = pType;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iResource = pResource;
        iProtection = pProtection;
        iExtraDurability = pExtraDurability;
        iMaxUpgrades = pMaxUpgrades;
        iCrafingInventory = pCraftingInventory;
    }

    public ArmorUpgrade getCopy()
    {
        return this;
    }

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

    public CustomResource getResource()
    {
        return iResource;
    }
}
