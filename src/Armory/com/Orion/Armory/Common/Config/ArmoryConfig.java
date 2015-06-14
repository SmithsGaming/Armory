package com.Orion.Armory.Common.Config;

import net.minecraftforge.common.config.Configuration;

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

    public static class ConfigHandler
    {

        static Configuration iConfig;
        static String HARDMODECATERGORIE = "HardMode";

        public static void init (File pConfigFile)
        {
            iConfig = new Configuration(new File(pConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Global.cfg"), true);

            loadConfig();
        }

        public static void loadConfig()
        {
            ArmoryConfig.enableHardModeNuggetRemoval = iConfig.getBoolean("RemoveNuggets", HARDMODECATERGORIE, true, "Enables the removal of crafting recipes that make nuggets of materials registered to Armory. Forces the crafting of nuggets using the Blacksmiths Anvil.");
        }
    }
}
