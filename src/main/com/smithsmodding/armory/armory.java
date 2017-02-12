package com.smithsmodding.armory;

import com.google.common.base.Stopwatch;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.ArmoryCommonProxy;
import com.smithsmodding.armory.common.api.APICallbackManager;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.armory.common.registries.CommonRegistryInitializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * Base class for armory
 * <p>
 * Created by: Orion 25-3-2014
 */

@Mod(modid = References.General.MOD_ID, name = "Armory", version = References.General.VERSION,
        dependencies = "required-after:forge@[13.19,);required-after:smithscore;")
public class Armory {
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(References.General.MOD_ID)
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.smithsmodding.armory.client.ArmoryClientProxy", serverSide = "com.smithsmodding.armory.common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;

    //Registry initializer
    @SidedProxy(clientSide = "com.smithsmodding.armory.common.registries.ClientRegistryInitializer", serverSide = "com.smithsmodding.armory.common.registries.CommonRegistryInitializer")
    public static CommonRegistryInitializer registryInitializer;

    //Stored to get the loaded side when needed
    public static Side side;

    public Armory() {
        ArmoryAPI.initialize();
    }

    @Mod.EventHandler
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"PreInit\"-Phase");

        side = preInitializationEvent.getSide();

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onPreInit(preInitializationEvent);
        });
        
        ModLogger.getInstance().info("Finished \"PreInit\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onInit(@Nonnull FMLInitializationEvent initializationEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Init\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onInit(initializationEvent);
        });

        ModLogger.getInstance().info("Finished \"Init\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onPostInit(@Nonnull FMLPostInitializationEvent postInitializationEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"PostInit\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onPostInit(postInitializationEvent);
        });

        ModLogger.getInstance().info("Finished \"PostInit\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onServerStarting(@Nonnull FMLServerStartingEvent serverStartingEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Starting Server\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onServerStarting(serverStartingEvent);
        });

        ModLogger.getInstance().info("Finished \"Starting Server\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onServerStarted(@Nonnull FMLServerStartedEvent serverStartedEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Server Started\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onServerStarted(serverStartedEvent);
        });

        ModLogger.getInstance().info("Finished \"Server Started\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onServerStopping(@Nonnull FMLServerStoppingEvent serverStoppingEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Server Stopping\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onServerStopping(serverStoppingEvent);
        });

        ModLogger.getInstance().info("Finished \"Server Stopping\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onServerStopped(@Nonnull FMLServerStoppedEvent serverStoppedEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Server Stopped\"-Phase");

        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onServerStopped(serverStoppedEvent);
        });

        ModLogger.getInstance().info("Finished \"Server Stopped\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onLoadCompleted(FMLLoadCompleteEvent loadCompleteEvent) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"Load Complete\"-Phase");

        APICallbackManager.callAPIRequests();
        IArmoryAPI.Holder.getInstance().getRegistryManager().getInitializationComponentRegistry().forEach(iInitializationComponent ->  {
            iInitializationComponent.onLoadCompleted(loadCompleteEvent);
        });

        ModLogger.getInstance().info("Finished \"Load Complete\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void onIMCMessage(@Nonnull FMLInterModComms.IMCEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        ModLogger.getInstance().info("Starting \"IMCCallback\"-Phase");

        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (!message.isStringMessage())
                continue;

            if (message.key.equalsIgnoreCase("registerapi")) {
                ModLogger.getInstance().info(String.format("   - Receiving registration request from [ %s ] for method %s", message.getSender(), message.getStringValue()));
                APICallbackManager.registerAPICallback(message.getStringValue(), message.getSender());
            }
        }

        ModLogger.getInstance().info("Finished \"IMCCallback\"-Phase after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }
}
