package com.Orion.Armory.Common;

import com.Orion.Armory.Client.CreativeTab.ArmorTab;
import com.Orion.Armory.Client.CreativeTab.ComponentsTab;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Orion on 26-3-2014
 * Based off: TConstructRegistry
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

    //Arraylist for storing all the materials
    ArrayList<ArmorMaterial> iArmorMaterials = new ArrayList<ArmorMaterial>();

    //Arraylist for storing all the basic tool mappings
    ArrayList<ArmorCore> iArmorMappings = new ArrayList<ArmorCore>();

    //ArrayList for storing all the modifiers and upgrades
    ArrayList<ArmorUpgrade> iArmorUpgrades = new ArrayList<ArmorUpgrade>();
    ArrayList<ArmorModifier> iArmorModifiers = new ArrayList<ArmorModifier>();

    public ARegistry()
    {
        iTabArmoryArmor = new ArmorTab();
        iTabArmoryComponents = new ComponentsTab();
    }

    public ArrayList<ArmorCore> getAllArmorMappings()
    {
        return iArmorMappings;
    }

    public void addArmorMapping(ArmorCore pCore)
    {
        iArmorMappings.add(pCore);
    }

    public int getArmorID(String pArmorName)
    {
        for (ArmorCore tCore:iArmorMappings)
        {
            if (tCore.iInternalName == pArmorName)
            {
                return iArmorMappings.indexOf(tCore);
            }
        }

        return -1;
    }

    public int getArmorID(ArmorCore pCore)
    {
        return iArmorMappings.indexOf(pCore);
    }

    public ArmorCore getArmorMapping(int pArmorID)
    {
        return iArmorMappings.get(pArmorID);
    }

    public ArmorCore getArmorMapping(String pArmorName)
    {
        return iArmorMappings.get(getArmorID(pArmorName));
    }

    public ArrayList<ArmorMaterial> getArmorMaterials()
    {
        return iArmorMaterials;
    }

    public int registerMaterial(ArmorMaterial pMaterial, boolean pDefaultPartState)
    {
        for (ArmorUpgrade tUpgrade: iArmorUpgrades)
        {
            pMaterial.registerNewActivePart(this.getUpgradeID(tUpgrade), pDefaultPartState);
        }

        iArmorMaterials.add(pMaterial);

        return this.getMaterialID(pMaterial);
    }

    public int getMaterialID(String pMaterialName)
    {
        for(ArmorMaterial tMaterial: iArmorMaterials)
        {
            if (tMaterial.iInternalName.equals(pMaterialName))
            {
                return iArmorMaterials.indexOf(tMaterial);
            }
        }

        return -1;
    }

    public int getMaterialID(ArmorMaterial pMaterial)
    {
        return iArmorMaterials.indexOf(pMaterial);
    }

    public ArmorMaterial getMaterial(int pMaterialID)
    {
        return iArmorMaterials.get(pMaterialID);
    }

    public ArmorMaterial getMaterial(String pMaterialName)
    {
        return this.getMaterial(this.getMaterialID(pMaterialName));
    }

    public void changeUpgradeStateOnMaterial(int pMaterialID,int pUpgradeID, boolean pPartState)
    {
        iArmorMaterials.get(pMaterialID).modifyPartState(pUpgradeID, pPartState);
    }

    public void changeUpgradeStateOnMaterial(ArmorMaterial pMaterial, int pUpgradeID, boolean pPartState)
    {
        iArmorMaterials.get(this.getMaterialID(pMaterial)).modifyPartState(pUpgradeID, pPartState);
    }

    public void changeUpgradeStateOnMaterial(int pMaterialID, ArmorUpgrade pUpgrade,boolean pPartState)
    {
        iArmorMaterials.get(pMaterialID).modifyPartState(this.getUpgradeID(pUpgrade), pPartState);
    }

    public void changeUpgradeStateOnMaterial(ArmorMaterial pMaterial, ArmorUpgrade pUpgrade, boolean pPartState)
    {
        iArmorMaterials.get(this.getMaterialID(pMaterial)).modifyPartState(this.getUpgradeID(pUpgrade), pPartState);
    }

    public ArrayList<ArmorModifier> getModifiers() {return iArmorModifiers;}

    public void registerModifier(ArmorModifier pModifier)
    {
        iArmorModifiers.add(pModifier);
    }

    public int getModifierID(String pModifierName)
    {
        for(ArmorModifier tModifier : iArmorModifiers)
        {
            if (tModifier.iInternalName.equals(pModifierName))
            {
                return iArmorModifiers.indexOf(tModifier);
            }
        }

        return -1;
    }

    public int getModifierID(ArmorModifier pModifier)
    {
        return iArmorModifiers.indexOf(pModifier);
    }

    public ArmorModifier getModifier(int pModifierID)
    {
        return iArmorModifiers.get(pModifierID);
    }

    public ArmorModifier getModifier(String pModifierName)
    {
        return this.getModifier(this.getModifierID(pModifierName));
    }

    public ArrayList<ArmorUpgrade> getUpgrades() {return iArmorUpgrades;}

    public void registerUpgrade(ArmorUpgrade pUpgrade)
    {
        iArmorUpgrades.add(pUpgrade);
    }

    public int getUpgradeID(String pUpgradeName)
    {
        for(ArmorUpgrade tUpgrade: iArmorUpgrades)
        {
            if (tUpgrade.iInternalName.equals(pUpgradeName))
            {
                return iArmorUpgrades.indexOf(tUpgrade);
            }
        }

        return -1;
    }

    public int getUpgradeID(ArmorUpgrade pUpgrade)
    {
        return iArmorUpgrades.indexOf(pUpgrade);
    }

    public ArmorUpgrade getUpgrade(int pUpgradeID)
    {
        return iArmorUpgrades.get(pUpgradeID);
    }

    public ArmorUpgrade getUpgrade(String pUpgradeName)
    {
        return this.getUpgrade(this.getUpgradeID(pUpgradeName));
    }

    public int getUpgradeTextureID(String pMaterialName, String pUpgradeName)
    {
        return (this.getMaterialID(pMaterialName)* (iArmorUpgrades.size()+1)) + this.getUpgradeID(pUpgradeName)+1;
    }

    public int getModifierTextureID(String pModifierName)
    {
        return ((iArmorMaterials.size() * iArmorUpgrades.size()) + 1) + this.getModifierID(pModifierName)+1;
    }

    public int getMaterialTextureID(String pMaterialName)
    {
        return (this.getMaterialID(pMaterialName) * (iArmorUpgrades.size()+1));
    }

    public ArrayList<ArmorUpgrade> getInstalledArmorUpgradesOnItemStack(ItemStack pBaseArmor)
    {
        NBTTagCompound tBaseCompound  = pBaseArmor.getTagCompound();
        int tInstalledUpgrades = tBaseCompound.getInteger("InstalledUpgrades");
        ArrayList<ArmorUpgrade> tUpgrades = new ArrayList<ArmorUpgrade>();

        for (int iter = 1; iter <= tInstalledUpgrades; iter ++)
        {
            int tUpgradeID = tBaseCompound.getCompoundTag("Upgrades").getCompoundTag("Upgrade - "+iter).getInteger("UpgradeID");
            ArmorUpgrade tUpgrade = ARegistry.iInstance.getUpgrade(tUpgradeID);

            tUpgrades.add(tUpgrade);
        }

        return tUpgrades;
    }

    public ArrayList<ArmorModifier> getInstalledModifiersOnItemStack(ItemStack pBaseArmor)
    {
        NBTTagCompound tBaseCompound = pBaseArmor.getTagCompound();
        int tInstalledModifiers = tBaseCompound.getInteger("InstalledModifiers");
        ArrayList<ArmorModifier> tModifiers = new ArrayList<ArmorModifier>();

        for (int iter = 1; iter <= tInstalledModifiers; iter++)
        {
            int tModifierID = tBaseCompound.getCompoundTag("Modifiers").getCompoundTag("Modifier - "+iter).getInteger("ModifierID");
            int tInstalledAmount = tBaseCompound.getCompoundTag("Modifiers").getCompoundTag("Modifier - " + iter).getInteger("Amount");

            ArmorModifier tModifier = ARegistry.iInstance.getModifier(tModifierID);
            tModifier.setInstalledAmount(tInstalledAmount);

            tModifiers.add(tModifier);
        }

        return tModifiers;
    }

}







//Arrays for testing, these contain all the basic upgrades and modifiers
//public String[] iArmorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
//public String[] iArmorModifiers = {"helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
//        "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
//        "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
//        "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};
