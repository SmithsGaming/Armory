package com.Orion.Armory.Common;


import com.Orion.Armory.Armory;
import com.Orion.Armory.Common.Event.ArmoryDataSyncerEventHandler;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion on 26-4-2014
 *
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy
{
    public void preInitializeArmory()
    {
        Armory.iSide = Side.SERVER;
    }

    public void initializeArmory()
    {
        ArmoryInitializer.InitializeServer();
    }

    public void registerEventHandlers(){
        FMLCommonHandler.instance().bus().register(new ArmoryDataSyncerEventHandler());
    }

}
