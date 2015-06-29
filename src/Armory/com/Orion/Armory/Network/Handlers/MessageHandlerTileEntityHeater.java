package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityHeater;
import com.Orion.Armory.Network.Messages.MessageTileEntityHeater;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 24.04.2015
 * 18:42
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerTileEntityHeater extends MessageHandlerTileEntityArmory implements IMessageHandler<MessageTileEntityHeater, IMessage>
{
    @Override
    public IMessage onMessage(MessageTileEntityHeater message, MessageContext ctx){
        super.onMessage(message,ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord,message.yCoord,message.zCoord);
        if(tEntity instanceof TileEntityHeater)
        {
            ((TileEntityHeater) tEntity).iFanStack = message.iFanStack;
            ((TileEntityHeater) tEntity).iItemInSlotTicks = message.iItemInSlotTicks;
        }

        return null;
    }
}
