package com.SmithsModding.Armory.Network.Handlers;

import com.SmithsModding.Armory.Common.TileEntity.Core.ICustomInputHandler;
import com.SmithsModding.Armory.Network.Messages.MessageCustomInput;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.inventory.Container;

/**
 * Created by Orion
 * Created on 02.06.2015
 * 10:56
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerCustomInput implements IMessageHandler<MessageCustomInput, IMessage> {

    @Override
    public IMessage onMessage(MessageCustomInput message, MessageContext ctx) {

        Container tOpenContainer = ctx.getServerHandler().playerEntity.openContainer;

        if (tOpenContainer instanceof ICustomInputHandler) {
            ((ICustomInputHandler) tOpenContainer).HandleCustomInput(message.iInputID, message.iInput);
        }

        return null;
    }
}
