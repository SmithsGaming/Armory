package com.smithsmodding.armory.common.logic;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */
@Mod.EventBusSubscriber
public class ArmoryInitializer {

    @SubscribeEvent
    public static void handleInitializationRegistration(RegistryEvent.Register<IInitializationComponent> initializationComponentRegisterEvent) {
        Armory.proxy.registerInitializationComponents(initializationComponentRegisterEvent.getRegistry());
    }


}
