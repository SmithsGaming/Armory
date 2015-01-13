package com.Orion.Armory.Network;
/*
 *   NetworkManager
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkManager
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.General.MOD_ID.toLowerCase());

    public static void Init()
    {

    }
}
