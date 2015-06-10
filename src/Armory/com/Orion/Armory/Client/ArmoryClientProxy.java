package com.Orion.Armory.Client;

import com.Orion.Armory.Armory;
import com.Orion.Armory.Client.Event.ClientDisconnectedFromServerEventHandler;
import com.Orion.Armory.Client.Event.ClientEventHandler;
import com.Orion.Armory.Client.Logic.ArmoryClientInitializer;
import com.Orion.Armory.Common.ArmoryCommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy
{
    @Override
    public void initializeArmory()
    {
        Armory.iSide = Side.CLIENT;
        ArmoryClientInitializer.InitializeClient();
    }

    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(new ClientDisconnectedFromServerEventHandler());
    }
}
