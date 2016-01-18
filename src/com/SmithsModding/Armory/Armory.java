package com.smithsmodding.Armory;

import com.google.common.base.*;
import com.smithsmodding.Armory.Common.*;
import com.smithsmodding.Armory.Common.Logic.*;
import com.smithsmodding.Armory.Util.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;
import org.apache.logging.log4j.*;

import java.util.concurrent.*;

/**
 * Base class for Armory
 * <p/>
 * Created by: Orion 25-3-2014
 */

@Mod(modid = References.General.MOD_ID, name = "Armory", version = References.General.VERSION,
        dependencies = "required-after:Forge@[11.15,);required-after:SmithsCore;")
public class Armory {
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(References.General.MOD_ID)
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.smithsmodding.Armory.Client.ArmoryClientProxy", serverSide = "com.smithsmodding.Armory.Common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;
    //Stored to get the loaded side when needed
    public static Side side;
    //Logger
    private static Logger logger = LogManager.getLogger("Armory");

    public static Logger getLogger () {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        getLogger().info("Starting pre init");

        side = event.getSide();

        proxy.preInitializeArmory();
        proxy.registerEventHandlers();

        getLogger().info("Finished pre init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        getLogger().info("Starting init");

        proxy.initializeArmory();

        getLogger().info("Finished init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
        getLogger().info("Starting post init");

        ArmoryInitializer.postInitializeServer();

        getLogger().info("Finished post init after: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
        watch.stop();
    }
}
