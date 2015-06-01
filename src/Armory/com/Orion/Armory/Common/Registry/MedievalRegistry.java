package com.Orion.Armory.Common.Registry;
/*
 *   MedievalRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMaterialMedieval;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMedieval;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorUpgradeMedieval;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MedievalRegistry {
    protected static MedievalRegistry iInstance;
    protected static GeneralRegistry iGeneralRegistry = GeneralRegistry.getInstance();

    //Hashmap for storing all the materials
    protected HashMap<String, ArmorMaterialMedieval> iArmorMaterials = new HashMap<String, ArmorMaterialMedieval>();
    //Hashmap for storing all the basic tool mappings
    protected HashMap<String, ArmorMedieval> iArmorMappings = new HashMap<String, ArmorMedieval>();
    //ArrayList for storing all the modifiers and upgrades
    protected HashMap<String, ArmorUpgradeMedieval> iArmorUpgrades = new HashMap<String, ArmorUpgradeMedieval>();

    public static MedievalRegistry getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new MedievalRegistry();
        }

        return iInstance;
    }

    //ArmorMappings
    public HashMap<String, ArmorMedieval> getAllRegisteredArmors()
    {
        return iArmorMappings;
    }

    public void registerNewArmor(ArmorMedieval pArmor)
    {
        iArmorMappings.put(pArmor.getInternalName(), pArmor);
    }

    public ArmorMedieval getArmor(String pInternalName)
    {
        return iArmorMappings.get(pInternalName);
    }

    //ArmorMaterials
    public HashMap<String, ArmorMaterialMedieval> getArmorMaterials()
    {
        return iArmorMaterials;
    }

    public void registerMaterial(ArmorMaterialMedieval pMaterial)
    {
        iArmorMaterials.put(pMaterial.iInternalName, pMaterial);
    }

    public ArmorMaterialMedieval getMaterial(String pInternalName)
    {
        return iArmorMaterials.get(pInternalName);
    }

    public ArmorMaterialMedieval getBaseMaterialOfItemStack(ItemStack pArmorStack)
    {
        return iArmorMaterials.get(pArmorStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));
    }

    public void changeUpgradeStateOnMaterial(String pMaterialInternalName, String pUpgradeInternalName, boolean pPartState)
    {
        iArmorMaterials.get(pMaterialInternalName).modifyPartState(pUpgradeInternalName, pPartState);
    }

    //Upgrades
    public HashMap<String, ArmorUpgradeMedieval> getUpgrades()
    {
        HashMap<String, ArmorUpgradeMedieval> tMedievalUpgrades = new HashMap<String, ArmorUpgradeMedieval>();

        for (ArmorUpgradeMedieval tAddon : this.iArmorUpgrades.values())
        {
            if (tAddon instanceof ArmorUpgradeMedieval)
            {
                tMedievalUpgrades.put(tAddon.getInternalName(), tAddon);
            }
        }

        return tMedievalUpgrades;
    }

    public void registerUpgrade(ArmorUpgradeMedieval pUpgrade)
    {
        iArmorUpgrades.put(pUpgrade.getInternalName(), pUpgrade);

        getArmor(pUpgrade.getParentName()).registerAddon(pUpgrade);
    }

    public ArmorUpgradeMedieval getUpgrade(String pUpgradeInternalName)
    {
        return (ArmorUpgradeMedieval) this.iArmorUpgrades.get(pUpgradeInternalName);
    }

    //Functions to get installed upgrades
    //TODO: Move this to UTIL class
    public HashMap<ArmorUpgradeMedieval, Integer> getInstalledArmorMedievalUpgradesOnItemStack(ItemStack pBaseArmor)
    {
        HashMap<MLAAddon, Integer> tInstalledAddons = NBTHelper.getAddonMap(pBaseArmor);
        HashMap<ArmorUpgradeMedieval, Integer> tInstalledUpgrades = new HashMap<ArmorUpgradeMedieval, Integer>();
        Iterator tIterator = tInstalledAddons.entrySet().iterator();

        while(tIterator.hasNext())
        {
            Map.Entry<MLAAddon, Integer> tEntry = (Map.Entry<MLAAddon, Integer>) tIterator.next();
            if (tEntry.getKey() instanceof ArmorUpgradeMedieval)
            {
                tInstalledUpgrades.put((ArmorUpgradeMedieval) tEntry.getKey(), tEntry.getValue());
            }
        }

        return tInstalledUpgrades;
    }
}
