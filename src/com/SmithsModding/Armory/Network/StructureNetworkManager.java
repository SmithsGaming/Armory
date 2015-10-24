package com.SmithsModding.Armory.Network;

import com.SmithsModding.Armory.Network.Handlers.Structure.MessageHandlerOnCreateMasterEntity;
import com.SmithsModding.Armory.Network.Handlers.Structure.MessageHandlerOnCreateSlaveEntity;
import com.SmithsModding.Armory.Network.Handlers.Structure.MessageHandlerOnUpdateMasterData;
import com.SmithsModding.Armory.Network.Messages.Structure.MessageOnCreateMasterEntity;
import com.SmithsModding.Armory.Network.Messages.Structure.MessageOnCreateSlaveEntity;
import com.SmithsModding.Armory.Network.Messages.Structure.MessageOnUpdateMasterData;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 16:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StructureNetworkManager {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.General.MOD_ID.toLowerCase() + "-structures");

    public static void Init() {
        INSTANCE.registerMessage(MessageHandlerOnCreateMasterEntity.class, MessageOnCreateMasterEntity.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerOnCreateSlaveEntity.class, MessageOnCreateSlaveEntity.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerOnUpdateMasterData.class, MessageOnUpdateMasterData.class, 3, Side.CLIENT);
    }
}
