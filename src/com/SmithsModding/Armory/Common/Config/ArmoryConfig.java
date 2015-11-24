package com.SmithsModding.Armory.Common.Config;

import com.SmithsModding.Armory.Armory;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * Created by Orion
 * Created on 14.06.2015
 * 10:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmoryConfig {

    public static boolean enableHardModeNuggetRemoval = true;
    public static boolean materialPropertiesSet = true;
    public static boolean enableTemperatureDecay = true;
    public static boolean enableDebugOutput = false;
    public static float basicBlueprintDeteriation = 0.000001F;
    public static float hardBlueprintDeteriation = 0.00001F;
    public static float easyBlueprintDeteriation = 0.0000001F;
    public static float materialBlueprintDeteriation = 0.00000001F;
    public static float medievalArmorUpgradeBlueprintDeteriation = 0.00000001F;
    public static float basicBlueprintDeteriationInInventory = 0.0001F;
    public static float hardBlueprintDeteriationInInventory = 0.001F;
    public static float easyBlueprintDeteriationInInventory = 0.00001F;
    public static float materialBlueprintDeteriationinInventory = 0.000001F;
    public static float medievalArmorUpgradeBlueprintDeteriationInInventory = 0.000001F;

    public static class ConfigHandler {

        static Configuration iConfig;
        static String HARDMODECATERGORIE = "HardMode";
        static String MATERIALSCATEGORIE = "Materials";
        static String GLOBALCATEGORIE = "Global";
        static String BLUEPRINTDETERIATION = "Blueprint-Deteriation";

        public static void init(File pConfigFile) {
            iConfig = new Configuration(new File(pConfigFile.getParentFile().getAbsolutePath() + "/Armory/Global.cfg"), true);

            loadConfig();
        }

        public static void loadConfig() {
            ArmoryConfig.enableHardModeNuggetRemoval = iConfig.getBoolean("RemoveNuggets", HARDMODECATERGORIE, true, "Enables the removal of crafting recipes that make nuggets of materials registered to Armory. Forces the crafting of nuggets using the Blacksmiths Anvil.");

            if (Armory.iSide == Side.CLIENT) {
                ArmoryConfig.materialPropertiesSet = iConfig.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            } else {
                ArmoryConfig.materialPropertiesSet = iConfig.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }

            if (Armory.iSide == Side.CLIENT) {
                ArmoryConfig.enableDebugOutput = iConfig.getBoolean("Debug output", GLOBALCATEGORIE, false, "This variable should be set to true to get Debug output.");
            }

            ArmoryConfig.enableTemperatureDecay = iConfig.getBoolean("TemperatureDecay", GLOBALCATEGORIE, true, "Enables the decrementation of the temperature of Heated Items. Also enables the Fire Damage when not held in tendem with a pair of tongs.");

            ArmoryConfig.basicBlueprintDeteriation = iConfig.getFloat("Basic", BLUEPRINTDETERIATION, 0.000001F, 0F, 1F, "Sets with how much a basic Blueprints quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.hardBlueprintDeteriation = iConfig.getFloat("Hard", BLUEPRINTDETERIATION, 0.00001F, 0F, 1F, "Sets with how much a hard Blueprints quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.easyBlueprintDeteriation = iConfig.getFloat("Easy", BLUEPRINTDETERIATION, 0.0000001F, 0F, 1F, "Sets with how much a easy Blueprints quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.materialBlueprintDeteriation = iConfig.getFloat("Material", BLUEPRINTDETERIATION, 0.00000001F, 0F, 1F, "Sets with how much a material Blueprints quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.medievalArmorUpgradeBlueprintDeteriation = iConfig.getFloat("Medieval-Upgrade", BLUEPRINTDETERIATION, 0.00000001F, 0F, 1F, "Sets with how much the quality of a Blueprint describing how to upgrade medieval armor of a certain material decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");


            ArmoryConfig.basicBlueprintDeteriationInInventory = iConfig.getFloat("Basic-Inventory", BLUEPRINTDETERIATION, 0.0001F, 0F, 1F, "Sets with how much a basic Blueprints (while out in your inventory) quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.hardBlueprintDeteriationInInventory = iConfig.getFloat("Hard-Inventory", BLUEPRINTDETERIATION, 0.001F, 0F, 1F, "Sets with how much a hard Blueprints (while out in your inventory) quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.easyBlueprintDeteriationInInventory = iConfig.getFloat("Easy-Inventory", BLUEPRINTDETERIATION, 0.00001F, 0F, 1F, "Sets with how much a easy Blueprints (while signled out in your inventory) quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.materialBlueprintDeteriationinInventory = iConfig.getFloat("Material-Inventory", BLUEPRINTDETERIATION, 0.000001F, 0F, 1F, "Sets with how much a material Blueprints (while out in your inventory) quality decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");
            ArmoryConfig.medievalArmorUpgradeBlueprintDeteriationInInventory = iConfig.getFloat("Medieval-Upgrade-Inventory", BLUEPRINTDETERIATION, 0.000001F, 0F, 1F, "Sets with how much the quality of a Blueprint describing how to upgrade medieval armor of a certain material (while out in your inventory) decreases every tick. Where 0F means no deteriation and 1F means that the Blueprint is destroyed on crafting");

            if (iConfig.hasChanged())
                iConfig.save();
        }

        public static void markMaterialPropetiesAsSeeded() {
            Property tMaterialSeededProperties;

            if (Armory.iSide == Side.CLIENT) {
                tMaterialSeededProperties = iConfig.get("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            } else {
                tMaterialSeededProperties = iConfig.get("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }

            tMaterialSeededProperties.set(true);

            iConfig.save();
        }

        public Configuration getConfig() {
            return iConfig;
        }
    }
}
