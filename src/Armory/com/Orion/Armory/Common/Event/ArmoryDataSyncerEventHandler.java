package com.Orion.Armory.Common.Event;

import com.Orion.Armory.Common.Config.ArmorDataSynchronizer;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Network.ConfigNetworkManager;
import com.Orion.Armory.Network.Messages.Config.MessageConfigSyncCompleted;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:15
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmoryDataSyncerEventHandler
{

    @SubscribeEvent
    public void HandlePlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent pEvent)
    {
        GeneralRegistry.iLogger.info("Started sending properties to the Client.");
        ArmorDataSynchronizer tSynchronizer = new ArmorDataSynchronizer();

        tSynchronizer.loadBaseDurability(pEvent.player);
        tSynchronizer.loadPartModifiers(pEvent.player);
        tSynchronizer.loadBaseDamageAbsorptions(pEvent.player);
        tSynchronizer.loadActiveParts(pEvent.player);
        tSynchronizer.loadIsBaseArmorMaterial(pEvent.player);
        tSynchronizer.loadMeltingPoint(pEvent.player);
        tSynchronizer.loadTemperatureCoefficient(pEvent.player);

        ConfigNetworkManager.INSTANCE.sendTo(new MessageConfigSyncCompleted(), (EntityPlayerMP) pEvent.player);
        GeneralRegistry.iLogger.info("Ended property sync.");
    }

}
