package com.smithsmodding.armory.common.handlers.config;

import com.smithsmodding.armory.api.events.common.config.ConfigSyncCompletedEvent;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.common.config.ArmorDataSynchronizer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:15
 * <p>
 * Copyrighted according to Project specific license
 */
public class ArmoryDataSyncerEventHandler {

    @SubscribeEvent
    public void HandlePlayerLoggedInEvent(@NotNull PlayerEvent.PlayerLoggedInEvent event) {
        ModLogger.getInstance().info("Started sending properties to the Client.");
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
        ModLogger.getInstance().info("Ended property sync.");
    }

}
