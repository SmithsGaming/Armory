package com.smithsmodding.armory.common.handlers.config;

import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.api.events.common.config.ConfigSyncCompletedEvent;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.registry.AnvilRecipeRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:26
 *
 * Copyrighted according to Project specific license
 */
public class ConfigSyncCompletedEventHandler {

    @SubscribeEvent
    public void onMessage(ConfigSyncCompletedEvent event) {
        AnvilRecipeRegistry.getInstance().clearAllStoredRecipes();
        ArmoryInitializer.MedievalInitialization.initializeAnvilRecipes();

        ArmoryConfig.materialPropertiesSet = true;
        ArmoryConfig.ConfigHandler.markMaterialPropetiesAsSeeded();
    }
}
