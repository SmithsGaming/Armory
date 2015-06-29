package com.Orion.Armory.Client.Event;

import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:47
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ClientDisconnectedFromServerEventHandler
{
    @SubscribeEvent
    public void ClientDisconnectedFromServerEventHandler(FMLNetworkEvent.ClientDisconnectionFromServerEvent pEvent) {
        ArmoryInitializer.SystemInit.loadMaterialConfig();

        TileEntityArmorsAnvil.clearAllStoredRecipes();
        ArmoryInitializer.MedievalInitialization.initializeAnvilRecipes();
    }
}
