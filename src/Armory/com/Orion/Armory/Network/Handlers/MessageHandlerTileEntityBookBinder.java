/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Network.Messages.MessageTileEntityBookBinder;
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
            ((TileEntityBookBinder) tEntity).setInventorySlotContents(0, message.iBindingBookStack);
            ((TileEntityBookBinder) tEntity).setInventorySlotContents(1, message.iBindingBluePrintStack);
            ((TileEntityBookBinder) tEntity).setInventorySlotContents(2, message.iResearchingTargetStack);
            ((TileEntityBookBinder) tEntity).setInventorySlotContents(3, message.iResearchingOutputStack);


            ((TileEntityBookBinder) tEntity).setOperationMode(message.iOpMode);
            ((TileEntityBookBinder) tEntity).setOperationProgress(message.iOperationProgress);
        }


        return null;
    }
}
