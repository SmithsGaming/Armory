package com.Orion.Armory;

import com.Orion.Armory.Common.ArmoryCommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Base class for Armory
 *
 * Created by: Orion 25-3-2014
 */

@Mod(modid = "TConstruct-Armory", name = "TConstruct - Armory extension", version = "0.0.1 Alpha 22",
        dependencies = "required-after:Forge@[9.11,);required-after:Mantle;after:ForgeMultipart;after:TContruct")
public class Armory
{
    // Instance of this mod use for internal and Forge references
    @Mod.Instance("TConstruct-Armory")
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide="com.Orion.Armory.Common.ArmoryCommonProxy", serverSide="com.Orion.Armory.Client.ArmoryClientProxy")
    public static ArmoryCommonProxy proxy;

    // Data that is needed throughout the whole mod.
    public static boolean iIsInitialized = false;


    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
        proxy.registerEvents();
        proxy.initializeArmory();
    }
}
