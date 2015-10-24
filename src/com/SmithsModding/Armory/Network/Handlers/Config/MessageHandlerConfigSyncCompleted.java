package com.SmithsModding.Armory.Network.Handlers.Config;

import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.AnvilRecipeRegistry;
import com.SmithsModding.Armory.Common.Config.ArmoryConfig;
import com.SmithsModding.Armory.Common.Logic.ArmoryInitializer;
import com.SmithsModding.Armory.Network.Messages.Config.MessageConfigSyncCompleted;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:26
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerConfigSyncCompleted implements IMessageHandler<MessageConfigSyncCompleted, IMessage> {

    @Override
    public IMessage onMessage(MessageConfigSyncCompleted message, MessageContext ctx) {
        AnvilRecipeRegistry.getInstance().clearAllStoredRecipes();
        ArmoryInitializer.MedievalInitialization.initializeAnvilRecipes();

        ArmoryConfig.materialPropertiesSet = true;
        ArmoryConfig.ConfigHandler.markMaterialPropetiesAsSeeded();

        return null;
    }
}
