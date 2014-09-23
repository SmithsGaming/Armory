package com.Orion.Armory.Common;

import com.Orion.Armory.Client.CreativeTab.ArmorTab;
import com.Orion.Armory.Client.CreativeTab.ComponentsTab;
import com.Orion.Armory.Common.Armor.Core.ArmorMaterial;
import com.Orion.Armory.Common.Armor.Core.MultiLayeredArmor;
import com.Orion.Armory.Common.Armor.TierChain.ArmorChain;
import com.Orion.Armory.Common.Armor.TierChain.ArmorChainUpgrade;
import com.Orion.Armory.Common.Armor.TierChain.Modifiers.ArmorChainModifier;
import com.Orion.Armory.Common.Armor.Core.MLAAddon;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion on 26-3-2014
 */
public class ARegistry
{
    //iInstance, iLogger and dummy iArmorMaterial.
    public static ARegistry iInstance = new ARegistry();
    public static Logger iLogger = LogManager.getLogger("Armory");
    public static ItemArmor.ArmorMaterial iArmorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy",100, new int[]{0,0,0,0}, 0);

    // Tabs for the creative inventory
    public static ArmorTab iTabArmoryArmor;
    public static ComponentsTab iTabArmoryComponents;

    //Hashmap for storing all the materials
    protected HashMap<String, ArmorMaterial> iArmorMaterials = new HashMap<String, ArmorMaterial>();

    //Hashmap for storing all the basic tool mappings
    protected HashMap<String, MultiLayeredArmor> iArmorMappings = new HashMap<String, MultiLayeredArmor>();

    //ArrayList for storing all the modifiers and upgrades
    protected HashMap<String, MLAAddon> iArmorUpgrades = new HashMap<String, MLAAddon>();
    protected HashMap<String, MLAAddon> iArmorModifiers = new HashMap<String, MLAAddon>();

    public ARegistry()
    {
        iTabArmoryArmor = new ArmorTab();
        //iTabArmoryComponents = new ComponentsTab();
    }

    //ArmorMappings
    public HashMap<String, MultiLayeredArmor> getAllArmorMappings()
    {
        return iArmorMappings;
    }

    public void addArmorMapping(MultiLayeredArmor pArmor)
    {
        iArmorMappings.put(pArmor.getInternalName(), pArmor);
    }

    public MultiLayeredArmor getArmorMapping(String pInternalName)
    {
        return iArmorMappings.get(pInternalName);
    }

    //ArmorMaterials
    public HashMap<String, ArmorMaterial> getArmorMaterials()
    {
        return iArmorMaterials;
    }

    public void registerMaterial(ArmorMaterial pMaterial)
    {
        iArmorMaterials.put(pMaterial.iInternalName, pMaterial);
    }

    public ArmorMaterial getMaterial(String pInternalName)
    {
        return iArmorMaterials.get(pInternalName);
    }

    public ArmorMaterial getBaseMaterialOfItemStack(ItemStack pArmorStack)
    {
        return iArmorMaterials.get(pArmorStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));
    }

    public void changeUpgradeStateOnMaterial(String pMaterialInternalName, String pUpgradeInternalName, boolean pPartState)
    {
        iArmorMaterials.get(pMaterialInternalName).modifyPartState(pUpgradeInternalName, pPartState);
    }

    //GeneralAddonList
    public MLAAddon getMLAAddonFromID(String pAddonId)
    {
        if(iArmorUpgrades.containsKey(pAddonId))
        {
            return iArmorUpgrades.get(pAddonId);
        }

        return iArmorModifiers.get(pAddonId);
    }

    //ChainModifiers
    public HashMap<String, ArmorChainModifier> getChainModifiers()
    {
        HashMap<String, ArmorChainModifier> tChainAddons = new HashMap<String, ArmorChainModifier>();

        for (MLAAddon tAddon : this.iArmorModifiers.values())
        {
            if (tAddon instanceof ArmorChainModifier)
            {
                tChainAddons.put(tAddon.getInternalName(), (ArmorChainModifier) tAddon);
            }
        }

        return tChainAddons;
    }

    public void registerChainModifier(ArmorChainModifier pModifier)
    {
        iArmorModifiers.put(pModifier.getInternalName(), pModifier);
    }

    public ArmorChainModifier getChainModifier(String pModifierInternalName)
    {
        return (ArmorChainModifier) iArmorModifiers.get(pModifierInternalName);
    }

    //ChainUpgrades
    public HashMap<String, ArmorChainUpgrade> getUpgrades()
    {
        HashMap<String, ArmorChainUpgrade> tChainAddons = new HashMap<String, ArmorChainUpgrade>();

        for (MLAAddon tAddon : this.iArmorModifiers.values())
        {
            if (tAddon instanceof ArmorChainUpgrade)
            {
                tChainAddons.put(tAddon.getInternalName(), (ArmorChainUpgrade) tAddon);
            }
        }

        return tChainAddons;
    }

    public void registerUpgrade(ArmorChainUpgrade pUpgrade)
    {
        iArmorUpgrades.put(pUpgrade.getInternalName(), pUpgrade);
    }

    public ArmorChainUpgrade getUpgrade(String pUpgradeInternalName)
    {
        return (ArmorChainUpgrade) this.iArmorUpgrades.get(pUpgradeInternalName);
    }

    //Functions for chain armor
    public HashMap<ArmorChainUpgrade, Integer> getInstalledArmorChainUpgradesOnItemStack(ItemStack pBaseArmor)
    {
        HashMap<MLAAddon, Integer> tInstalledAddons = NBTHelper.getAddonMap(pBaseArmor);
        HashMap<ArmorChainUpgrade, Integer> tInstalledUpgrades = new HashMap<ArmorChainUpgrade, Integer>();
        Iterator tIterator = tInstalledAddons.entrySet().iterator();

        while(tIterator.hasNext())
        {
            Map.Entry<MLAAddon, Integer> tEntry = (Map.Entry<MLAAddon, Integer>) tIterator.next();
            if (tEntry.getKey() instanceof ArmorChainUpgrade)
            {
                tInstalledUpgrades.put((ArmorChainUpgrade) tEntry.getKey(), tEntry.getValue());
            }
        }

        return tInstalledUpgrades;
    }

    public HashMap<ArmorChainModifier, Integer> getInstalledChainModifiersOnItemStack(ItemStack pBaseArmor)
    {
        HashMap<MLAAddon, Integer> tInstalledAddons = NBTHelper.getAddonMap(pBaseArmor);
        HashMap<ArmorChainModifier, Integer> tInstalledModifiers = new HashMap<ArmorChainModifier, Integer>();
        Iterator tIterator = tInstalledAddons.entrySet().iterator();

        while(tIterator.hasNext())
        {
            Map.Entry<MLAAddon, Integer> tEntry = (Map.Entry<MLAAddon, Integer>) tIterator.next();
            if (tEntry.getKey() instanceof ArmorChainModifier)
            {
                tInstalledModifiers.put((ArmorChainModifier) tEntry.getKey(), tEntry.getValue());
            }
        }

        return tInstalledModifiers;
    }

}







//Arrays for testing, these contain all the basic upgrades and modifiers
//public String[] iArmorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
//public String[] iArmorModifiers = {"helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
//        "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
//        "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
//        "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};
