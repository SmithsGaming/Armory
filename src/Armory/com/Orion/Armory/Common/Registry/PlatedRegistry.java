package com.Orion.Armory.Common.Registry;
/*
 *   PlatedRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.Common.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Armor.TierPlated.ArmorMaterialPlated;
import com.Orion.Armory.Common.Armor.TierPlated.ArmorPlated;
import com.Orion.Armory.Common.Armor.TierPlated.Modifiers.ArmorModifierPlated;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlatedRegistry {
    protected static PlatedRegistry iInstance;
    protected static GeneralRegistry iGeneralRegistry = GeneralRegistry.getInstance();

    //Hashmap for storing all the materials
    protected HashMap<String, ArmorMaterialPlated> iArmorMaterials = new HashMap<String, ArmorMaterialPlated>();

    //Hashmap for storing all the basic tool mappings
    protected HashMap<String, ArmorPlated> iArmorMappings = new HashMap<String, ArmorPlated>();

    //ArrayList for storing all the modifiers and upgrades
    protected HashMap<String, ArmorModifierPlated> iArmorUpgrades = new HashMap<String, ArmorModifierPlated>();

    public static PlatedRegistry getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new PlatedRegistry();
        }

        return iInstance;
    }

    //ArmorMappings
    public HashMap<String, ArmorPlated> getAllRegisteredArmors()
    {
        return iArmorMappings;
    }

    public void registerNewArmor(ArmorPlated pArmor)
    {
        iArmorMappings.put(pArmor.getInternalName(), pArmor);
    }

    public ArmorPlated getArmor(String pInternalName)
    {
        return iArmorMappings.get(pInternalName);
    }

    //ArmorMaterials
    public HashMap<String, ArmorMaterialPlated> getArmorMaterials()
    {
        return iArmorMaterials;
    }

    public void registerMaterial(ArmorMaterialPlated pMaterial)
    {
        iArmorMaterials.put(pMaterial.iInternalName, pMaterial);
    }

    public ArmorMaterialPlated getMaterial(String pInternalName)
    {
        return iArmorMaterials.get(pInternalName);
    }

    public ArmorMaterialPlated getBaseMaterialOfItemStack(ItemStack pArmorStack)
    {
        return iArmorMaterials.get(pArmorStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));
    }

    public void changeUpgradeStateOnMaterial(String pMaterialInternalName, String pUpgradeInternalName, boolean pPartState)
    {
        iArmorMaterials.get(pMaterialInternalName).modifyPartState(pUpgradeInternalName, pPartState);
    }

    //Upgrades
    public HashMap<String, ArmorModifierPlated> getUpgrades()
    {
        HashMap<String, ArmorModifierPlated> tPlatedUpgrades = new HashMap<String, ArmorModifierPlated>();

        for (ArmorModifierPlated tAddon : this.iArmorUpgrades.values())
        {
            if (tAddon instanceof ArmorModifierPlated)
            {
                tPlatedUpgrades.put(tAddon.getInternalName(), tAddon);
            }
        }

        return tPlatedUpgrades;
    }

    public void registerUpgrade(ArmorModifierPlated pUpgrade)
    {
        iArmorUpgrades.put(pUpgrade.getInternalName(), pUpgrade);
    }

    public ArmorModifierPlated getUpgrade(String pUpgradeInternalName)
    {
        return (ArmorModifierPlated) this.iArmorUpgrades.get(pUpgradeInternalName);
    }

    //Functions to get installed upgrades
    //TODO: Move this to UTIL class
    public HashMap<ArmorModifierPlated, Integer> getInstalledArmorPlatedUpgradesOnItemStack(ItemStack pBaseArmor)
    {
        HashMap<MLAAddon, Integer> tInstalledAddons = NBTHelper.getAddonMap(pBaseArmor);
        HashMap<ArmorModifierPlated, Integer> tInstalledUpgrades = new HashMap<ArmorModifierPlated, Integer>();
        Iterator tIterator = tInstalledAddons.entrySet().iterator();

        while(tIterator.hasNext())
        {
            Map.Entry<MLAAddon, Integer> tEntry = (Map.Entry<MLAAddon, Integer>) tIterator.next();
            if (tEntry.getKey() instanceof ArmorModifierPlated)
            {
                tInstalledUpgrades.put((ArmorModifierPlated) tEntry.getKey(), tEntry.getValue());
            }
        }

        return tInstalledUpgrades;
    }
}
