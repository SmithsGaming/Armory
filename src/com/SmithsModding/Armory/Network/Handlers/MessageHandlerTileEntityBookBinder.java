/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Network.Handlers;

import com.SmithsModding.Armory.Common.TileEntity.TileEntityBookBinder;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityBookBinder;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

public class MessageHandlerTileEntityBookBinder extends MessageHandlerTileEntityArmory implements IMessageHandler<MessageTileEntityBookBinder, IMessage> {


    @Override
    public IMessage onMessage(MessageTileEntityBookBinder message, MessageContext ctx) {

        super.onMessage(message, ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityBookBinder) {

            ((TileEntityBookBinder) tEntity).setOperationMode(message.iOpMode);

            if (message.iOpMode == TileEntityBookBinder.OperationMode.BookBinding) {
                ((TileEntityBookBinder) tEntity).iBindingBookStack = message.iBindingBookStack;
                ((TileEntityBookBinder) tEntity).iBindingBluePrintStack = message.iBindingBluePrintStack;
            } else {
                ((TileEntityBookBinder) tEntity).iResearchingTargetStack = message.iResearchingTargetStack;
                ((TileEntityBookBinder) tEntity).iResearchingOutputStack = message.iResearchingOutputStack;
                ((TileEntityBookBinder) tEntity).iPaperStack = message.iPaperStack;

            }

            ((TileEntityBookBinder) tEntity).setOperationProgress(message.iOperationProgress);
        }


        return null;
    }
}
