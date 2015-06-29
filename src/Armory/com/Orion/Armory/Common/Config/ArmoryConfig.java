package com.Orion.Armory.Common.Config;

import com.Orion.Armory.Armory;
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
public class ArmoryConfig
{

    public static boolean enableHardModeNuggetRemoval = true;
    public static boolean materialPropertiesSet = true;

    public static class ConfigHandler
    {

        static Configuration iConfig;
        static String HARDMODECATERGORIE = "HardMode";
        static String MATERIALSCATEGORIE = "Materials";

        public static void init (File pConfigFile)
        {
            iConfig = new Configuration(new File(pConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Global.cfg"), true);

            loadConfig();
        }

        public static void loadConfig()
        {
            ArmoryConfig.enableHardModeNuggetRemoval = iConfig.getBoolean("RemoveNuggets", HARDMODECATERGORIE, true, "Enables the removal of crafting recipes that make nuggets of materials registered to Armory. Forces the crafting of nuggets using the Blacksmiths Anvil.");

            if (Armory.iSide == Side.CLIENT)
            {
                ArmoryConfig.materialPropertiesSet = iConfig.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }
            else
            {
                ArmoryConfig.materialPropertiesSet = iConfig.getBoolean("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }


            if (iConfig.hasChanged())
                iConfig.save();
        }

        public static void markMaterialPropetiesAsSeeded()
        {
            Property tMaterialSeededProperties;

            if (Armory.iSide == Side.CLIENT)
            {
                tMaterialSeededProperties = iConfig.get("MaterialPropertiesSet", MATERIALSCATEGORIE, true, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }
            else
            {
                tMaterialSeededProperties = iConfig.get("MaterialPropertiesSet", MATERIALSCATEGORIE, false, "This variable is used to negotiate the Material properties with dedicated servers. It is not used client side. If set to false, the server will attempt to sync the material properties with the first joining client.");
            }

            tMaterialSeededProperties.set(true);

            iConfig.save();
        }
    }
}
