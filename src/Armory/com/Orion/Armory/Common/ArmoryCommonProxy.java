package com.Orion.Armory.Common;


import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion on 26-4-2014
 *
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy
{
    public void initializeArmory()
    {
        ArmoryInitializer.InitializeServer();
    }
}
