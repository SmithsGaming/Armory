package com.Orion.Armory.Network;

import com.Orion.Armory.Network.Handlers.Config.MessageHandlerConfigSyncCompleted;
import com.Orion.Armory.Network.Handlers.Config.MessageHandlerMetarialPropertyValue;
import com.Orion.Armory.Network.Messages.Config.MessageConfigSyncCompleted;
import com.Orion.Armory.Network.Messages.Config.MessageMaterialPropertyValue;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 16:40
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ConfigNetworkManager
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.General.MOD_ID.toLowerCase() + "-config");

    public static void Init()
    {
        INSTANCE.registerMessage(MessageHandlerConfigSyncCompleted.class, MessageConfigSyncCompleted.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerMetarialPropertyValue.class, MessageMaterialPropertyValue.class, 1, Side.CLIENT);
    }
}
