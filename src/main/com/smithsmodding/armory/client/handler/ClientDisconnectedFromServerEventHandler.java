package com.smithsmodding.armory.client.handler;

import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:47
 * <p>
 * Copyrighted according to Project specific license
 */
public class ClientDisconnectedFromServerEventHandler {

    @SubscribeEvent
    public void ClientDisconnectedFromServerEventHandler(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ArmoryInitializer.SystemInit.loadMaterialConfig();

        AnvilRecipeRegistry.getInstance().clearAllStoredRecipes();
        ArmoryInitializer.MedievalInitialization.initializeAnvilRecipes();
    }
}
