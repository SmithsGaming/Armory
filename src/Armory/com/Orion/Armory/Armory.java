package com.Orion.Armory;

import com.Orion.Armory.Common.ArmoryCommonProxy;
import com.Orion.Armory.Common.Command.CommandArmory;
import com.Orion.Armory.Common.Config.ArmorDataConfigHandler;
import com.Orion.Armory.Common.Config.ArmoryConfig;
import com.Orion.Armory.Common.Handlers.GuiHandler;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Network.ConfigNetworkManager;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Network.StructureNetworkManager;
import com.Orion.Armory.Util.References;
import com.google.common.base.Stopwatch;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import java.util.concurrent.TimeUnit;

/**
 * Base class for Armory
 * <p/>
 * Created by: Orion 25-3-2014
 */

@Mod(modid = References.General.MOD_ID, name = "Armory", version = References.General.VERSION,
        dependencies = "required-after:Forge@[10.13,);after:TConstruct")
public class Armory {
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(References.General.MOD_ID)
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.Orion.Armory.Client.ArmoryClientProxy", serverSide = "com.Orion.Armory.Common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;

    //Stored to get the loaded side when needed
    public static Side iSide;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        GeneralRegistry.iLogger.info("Starting pre init");

        proxy.preInitializeArmory();
        proxy.registerEventHandlers();

        ArmoryConfig.ConfigHandler.init(event.getSuggestedConfigurationFile());
        ArmorDataConfigHandler.init(event.getSuggestedConfigurationFile());

        GeneralRegistry.iLogger.info("Finished pre init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        GeneralRegistry.iLogger.info("Starting init");

        NetworkManager.Init();
        ConfigNetworkManager.Init();
        StructureNetworkManager.Init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        proxy.initializeArmory();

        GeneralRegistry.iLogger.info("Finished init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        GeneralRegistry.iLogger.info("Starting post init");

        ArmoryInitializer.postInitializeServer();

        GeneralRegistry.iLogger.info("Finished post init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandArmory());
    }

}
