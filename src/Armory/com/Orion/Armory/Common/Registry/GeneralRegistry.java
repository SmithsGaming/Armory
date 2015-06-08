package com.Orion.Armory.Common.Registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.Client.CreativeTab.ComponentsTab;
import com.Orion.Armory.Client.CreativeTab.MedievalTab;
import com.Orion.Armory.Client.CreativeTab.MedievalUpgradeTab;
import com.Orion.Armory.Common.Addons.MedievalAddonRegistry;
import com.Orion.Armory.Common.Blocks.BlockArmorsAnvil;
import com.Orion.Armory.Common.Blocks.BlockFirePit;
import com.Orion.Armory.Common.Blocks.BlockHeater;
import com.Orion.Armory.Common.Item.*;
import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ItemUpgradeMedieval;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class GeneralRegistry {
    //iInstance, iLogger and dummy iArmorMaterial.
    protected static GeneralRegistry iInstance;
    public static Logger iLogger = LogManager.getLogger("Armory");
    public static ItemArmor.ArmorMaterial iArmorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy", 100, new int[]{0, 0, 0, 0}, 0);
    private static HashMap<String, Float> iMeltingPoints = new HashMap<String, Float>();
    private static HashMap<String, Float> iHeatCoefficients = new HashMap<String, Float>();

    // Tabs for the creative inventory
    public static MedievalTab iTabMedievalArmor;
    //public static PlatedTab iTabPlatedArmor;
    //public static QuantumTab iTabQuantumArmor;
    public static ComponentsTab iTabArmoryComponents;
    public static MedievalUpgradeTab iTabMedievalUpgrades;

    public GeneralRegistry()
    {
        this.iTabMedievalArmor = new MedievalTab();
        this.iTabArmoryComponents = new ComponentsTab();
        this.iTabMedievalUpgrades = new MedievalUpgradeTab();
        //TODO: Initialize all creative tabs once implemented properly
    }

    public static GeneralRegistry getInstance()
    {
        if (iInstance == null) iInstance = new GeneralRegistry();
        return iInstance;
    }

    public MLAAddon getMLAAddon(String pAddonID, String pTier)
    {
        if (pTier.equals(References.InternalNames.Tiers.MEDIEVAL)) {
            return MedievalAddonRegistry.getInstance().getUpgrade(pAddonID);
        } else {
            return null;
        }
    }

    public float getMeltingPoint(String pMaterialName)
    {
        if (!(iMeltingPoints.containsKey(pMaterialName))) {return -1;}
        return iMeltingPoints.get(pMaterialName);
    }

    public void setMeltingPoint(String pMaterialPoint, float pMeltingPoint)
    {
        iMeltingPoints.put(pMaterialPoint, pMeltingPoint);
    }

    public float getHeatCoefficient(String pMaterialName)
    {
        if (!(iHeatCoefficients.containsKey(pMaterialName))) {return -1;}
        return iHeatCoefficients.get(pMaterialName);
    }

    public void setHeatCoefficient(String pMaterialName, float pCoefficient)
    {
        iHeatCoefficients.put(pMaterialName, pCoefficient);
    }

    public static class Blocks
    {
        public static BlockFirePit iBlockFirePit = null;
        public static BlockHeater iBlockHeater = null;
        public static BlockArmorsAnvil iBlockAnvil = null;
    }

    public static class Items
    {
        public static ItemHeatedItem iHeatedIngot = null;
        public static ItemMetalRing iMetalRing = null;
        public static ItemMetalChain iMetalChain = null;
        public static ItemNugget iNugget = null;
        public static ItemPlate iPlate = null;
        public static ItemFan iFan = null;
        public static ItemHammer iHammer = null;
        public static ItemTongs iTongs = null;
        public static ItemUpgradeMedieval iMedievalUpgrades = null;
    }
}
