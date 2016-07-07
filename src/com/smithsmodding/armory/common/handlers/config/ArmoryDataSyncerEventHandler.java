package com.smithsmodding.armory.common.handlers.config;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.common.config.ArmorDataSynchronizer;
import com.smithsmodding.armory.common.event.config.ConfigSyncCompletedEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:15
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmoryDataSyncerEventHandler {

    @SubscribeEvent
    public void HandlePlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Armory.getLogger().info("Started sending properties to the Client.");
        ArmorDataSynchronizer synchronizer = new ArmorDataSynchronizer();

        synchronizer.loadIDs(event.player);
        synchronizer.loadBaseDurability(event.player);
        synchronizer.loadPartModifiers(event.player);
        synchronizer.loadBaseDamageAbsorptions(event.player);
        synchronizer.loadActiveParts(event.player);
        synchronizer.loadIsBaseArmorMaterial(event.player);
        synchronizer.loadMeltingPoint(event.player);
        synchronizer.loadTemperatureCoefficient(event.player);

        new ConfigSyncCompletedEvent().handleServerToClient((EntityPlayerMP) event.player);
        Armory.getLogger().info("Ended property sync.");
    }

}
