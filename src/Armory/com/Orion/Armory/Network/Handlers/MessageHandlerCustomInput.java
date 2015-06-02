package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.Common.TileEntity.ICustomInputHandler;
import com.Orion.Armory.Network.Messages.MessageCustomInput;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

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

        TileEntity tEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.iXCoord, message.iYCoord, message.iZCoord);
        if (tEntity instanceof ICustomInputHandler) {
            ((ICustomInputHandler) tEntity).HandleCustomInput(message.iInputID, message.iInput);
        }

        return null;
    }
}
