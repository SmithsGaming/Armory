package com.Orion.Armory;

import com.Orion.Armory.Common.ArmoryCommonProxy;
import com.Orion.Armory.Common.Handlers.GuiHandler;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Base class for Armory
 *
 * Created by: Orion 25-3-2014
 */

@Mod(modid = References.General.MOD_ID, name = "Armory", version = References.General.VERSION,
        dependencies = "required-after:Forge@[10.13,);required-after:Mantle;after:ForgeMultipart;required-after:TConstruct;")
public class Armory
{
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(References.General.MOD_ID)
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide="com.Orion.Armory.Client.ArmoryClientProxy", serverSide="com.Orion.Armory.Common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;

    // Data that is needed throughout the whole mod.
    public static boolean iIsInitialized = false;

    //Stored to get the loaded side when needed
    public static Side iSide;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
        proxy.initializeArmory();
        proxy.registerEventHandlers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkManager.Init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }
}
