package com.Orion.Armory.Common.Registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.Client.CreativeTab.ComponentsTab;
import com.Orion.Armory.Client.CreativeTab.MedievalTab;
import com.Orion.Armory.Client.CreativeTab.MedievalUpgradeTab;
import com.Orion.Armory.Common.Blocks.BlockArmorsAnvil;
import com.Orion.Armory.Common.Blocks.BlockFirePit;
import com.Orion.Armory.Common.Blocks.BlockHeater;
import com.Orion.Armory.Common.Item.*;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ItemUpgradeMedieval;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Properties;

public class GeneralRegistry {
    //iInstance, iLogger and dummy iArmorMaterial.
    protected static GeneralRegistry iInstance;
    public static Logger iLogger = LogManager.getLogger("Armory");
    public static ItemArmor.ArmorMaterial iArmorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy", 100, new int[]{0, 0, 0, 0}, 0);
    public static boolean iIsInDevEnvironment = false;

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

        Properties tSysProp = System.getProperties();
        iIsInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("Armory.Dev", "false"));
    }

    public static GeneralRegistry getInstance()
    {
        if (iInstance == null) iInstance = new GeneralRegistry();
        return iInstance;
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
