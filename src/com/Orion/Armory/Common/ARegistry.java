package com.Orion.Armory.Common;

import java.util.*;

import com.Orion.Armory.Common.Armor.ArmorCore;

import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Orion.Armory.Client.CreativeTab.ArmorTab;
import org.omg.CORBA.PUBLIC_MEMBER;
import tconstruct.library.armor.ArmorMod;

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

    //Arraylist for storing all the materials
    ArrayList<ArmorMaterial> iArmorMaterials = new ArrayList<ArmorMaterial>();

    //Arraylist for storing all the basic tool mappings
    ArrayList<ArmorCore> iArmorMappings = new ArrayList<ArmorCore>();

    //ArrayList for storing all the modifiers and upgrades
    ArrayList<ArmorUpgrade> iArmorUpgrades = new ArrayList<ArmorUpgrade>();
    ArrayList<ArmorModifier> iArmorModifiers = new ArrayList<ArmorModifier>();

    public ARegistry()
    {
        this.initializeMaterials();
    }

    protected void initializeMaterials()
    {
        /*
        this.registerMaterial("Iron", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Steel", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Alumite", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Bronze", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Ardite", new boolean[]{false, true, true, true, true, true, true, true, true});
        this.registerMaterial("Cobalt", new boolean[]{false, true, true, false, true, true, true, true, false});
        this.registerMaterial("Obsidian", new boolean[]{false, false, true, true, false, false, false, false, false});
        this.registerMaterial("Manyullyn", new boolean[]{false, true, false, false, true, true, true, true, false});
        */
    }


    public ArrayList<ArmorCore> getAllArmorMappings()
    {
        return iArmorMappings;
    }

    public void addArmorMapping(ArmorCore pCore)
    {
        iArmorMappings.add(pCore);
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
        int tUpgradeID = Arrays.asList(iArmorUpgrades).indexOf(pUpgradeName)+1;
        int tMaterialID = getMaterialID(pMaterialName);

        if ((tUpgradeID == -1) || (tMaterialID == -1)) {
            return -1;
        }

        return ((tMaterialID) * this.iArmorUpgrades.size() + tUpgradeID);
    }

    public int getModifierTextureID(String pMaterialName, String pModifierName)
    {
        int tModifierID = Arrays.asList(iArmorModifiers).indexOf(pModifierName)+1;
        int tMaterialID = getMaterialID(pMaterialName);

        if ((tModifierID == -1) || (tMaterialID == -1)) {
            return -1;
        }

        return ((tMaterialID)*this.iArmorModifiers.size() + tModifierID);
    }


}







//Arrays for testing, these contain all the basic upgrades and modifiers
//public String[] iArmorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
//public String[] iArmorModifiers = {"helmetAquaAffinity", "helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
//        "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
//        "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
//        "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};
