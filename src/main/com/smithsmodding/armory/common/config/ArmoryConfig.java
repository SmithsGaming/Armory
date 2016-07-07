package com.smithsmodding.armory.common.config;

import com.smithsmodding.armory.Armory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

/**
 * Created by Orion
 * Created on 14.06.2015
 * 10:34
 *
 * Copyrighted according to Project specific license
 */
public class ArmoryConfig {

    public static boolean enableHardModeNuggetRemoval = true;
    public static boolean materialPropertiesSet = true;
    public static boolean enableTemperatureDecay = true;

    public static class ConfigHandler {

        static Configuration config;
        static String HARDMODECATERGORIE = "HardMode";
        static String MATERIALSCATEGORIE = "Materials";
        static String GLOBALCATEGORIE = "Global";

        public static void init(File configFile) {
            config = new Configuration(new File(configFile.getParentFile().getAbsolutePath() + "/Armory/Global.cfg"), true);

            ArmorDataConfigHandler.init(config.getConfigFile());
            loadConfig();
        }

        public static void loadConfig() {
            ArmoryConfig.enableHardModeNuggetRemoval = config.getBoolean("RemoveNuggets", HARDMODECATERGORIE, true, "Enables the removal of crafting recipes that make nuggets of materials registered to Armory. Forces the crafting of nuggets using the Blacksmiths Anvil.");

            if (Armory.side == Side.CLIENT) {
                ArmoryConfig.materialPropertiesSet = config.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            } else {
                ArmoryConfig.materialPropertiesSet = config.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }

            ArmoryConfig.enableTemperatureDecay = config.getBoolean("TemperatureDecay", GLOBALCATEGORIE, true, "Enables the decrementation of the temperature of Heated Items. Also enables the Fire Damage when not held in tendem with a pair of tongs.");

            if (config.hasChanged())
                config.save();
        }

        public static void markMaterialPropetiesAsSeeded() {
            Property tMaterialSeededProperties;

            if (Armory.side == Side.CLIENT) {
                tMaterialSeededProperties = config.get("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            } else {
                tMaterialSeededProperties = config.get("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }

            tMaterialSeededProperties.set(true);

            config.save();
        }

        public Configuration getConfig() {
            return config;
        }
    }
}
