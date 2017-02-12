package com.smithsmodding.armory;

import com.google.common.base.Stopwatch;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.ArmoryCommonProxy;
import com.smithsmodding.armory.common.command.CommandArmory;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * Base class for armory
 * <p>
 * Created by: Orion 25-3-2014
 */

@Mod(modid = References.General.MOD_ID, name = "Armory", version = References.General.VERSION,
        dependencies = "required-after:Forge@[11.15,);required-after:SmithsCore;")
public class Armory {
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(References.General.MOD_ID)
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.smithsmodding.armory.client.ArmoryClientProxy", serverSide = "com.smithsmodding.armory.common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;
    //Stored to get the loaded side when needed
    public static Side side;

    @Mod.EventHandler
    public void preInit(@Nonnull FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting pre init");

        side = event.getSide();

        ArmoryConfig.ConfigHandler.init(event.getSuggestedConfigurationFile());

        proxy.preInitializeArmory();
        proxy.registerEventHandlers();
        proxy.initializeStructures();

        ModLogger.getInstance().info("Finished pre init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting init");

        proxy.initializeArmory();

        ModLogger.getInstance().info("Finished init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting post init");

        ArmoryInitializer.postInitializeServer();

        ModLogger.getInstance().info("Finished post init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onIMCMessage(@Nonnull FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (!message.isStringMessage())
                continue;

            if (message.key.equalsIgnoreCase("registerapi")) {
                ModLogger.getInstance().info(String.format("Receiving registration request from [ %s ] for method %s", message.getSender(), message.getStringValue()));
                GeneralRegistry.getInstance().registerAPICallback(message.getStringValue(), message.getSender());
            }
        }
    }

    @Mod.EventHandler
    public void onLoadCompleted(FMLLoadCompleteEvent event) {
        proxy.callAPIRequests();
        proxy.onLoadComplete();
    }


    @Mod.EventHandler
    public void onServerStarting(@Nonnull FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandArmory());
    }
}
