package com.Orion.Armory;

import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.ArmoryCommonProxy;
import com.Orion.OrionsBelt.Client.Render.RenderMultiLayeredArmor;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import com.Orion.OrionsBelt.OrionsBelt;

/**
 * Base class for Armory
 *
 * Created by: Orion 25-3-2014
 */

@Mod(modid = "Armory", name = "Armory - Armor? Not useless defence any more!", version = "@VERSION@",
        dependencies = "required-after:Forge@[10.12,);required-after:Mantle;after:ForgeMultipart;after:TContruct;required-after:OrionsBelt")
public class Armory
{
    // Instance of this mod use for internal and Forge references
    @Mod.Instance("Armory")
    public static Armory instance;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide="com.Orion.Armory.Client.ArmoryClientProxy", serverSide="com.Orion.Armory.Common.ArmoryCommonProxy")
    public static ArmoryCommonProxy proxy;

    // Data that is needed throughout the whole mod.
    public static boolean iIsInitialized = false;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
        proxy.initializeArmory();
    }
}
