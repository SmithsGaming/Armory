package com.SmithsModding.Armory.Network.Handlers;

import com.SmithsModding.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityArmory;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 4/17/2015
 * 2:37 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class MessageHandlerTileEntityArmory {
    public IMessage onMessage(MessageTileEntityArmory message, MessageContext ctx) {
        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityArmory) {
            ((TileEntityArmory) tEntity).setDisplayName(message.iName);
            ((TileEntityArmory) tEntity).setDirection(message.iCurrentDirection);
        }

        FMLClientHandler.instance().getClient().theWorld.func_147451_t(message.xCoord, message.yCoord, message.zCoord);

        return null;
    }
}
