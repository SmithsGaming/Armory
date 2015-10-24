package com.SmithsModding.Armory.Network.Handlers;

import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityFirePit;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 4/17/2015
 * 2:37 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerTileEntityFirePit extends MessageHandlerTileEntityArmory implements IMessageHandler<MessageTileEntityFirePit, IMessage> {
    @Override
    public IMessage onMessage(MessageTileEntityFirePit message, MessageContext ctx) {
        super.onMessage(message, ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityFirePit) {
            ((TileEntityFirePit) tEntity).iIngotStacks = message.iIngotStacks;
            ((TileEntityFirePit) tEntity).iFuelStacks = message.iFuelStacks;
            ((TileEntityFirePit) tEntity).iMaxTemperature = message.iMaxTemperature;
            ((TileEntityFirePit) tEntity).iCurrentTemperature = message.iCurrentTemperature;
            ((TileEntityFirePit) tEntity).iLastAddedHeat = message.iLastAddedHeat;
            ((TileEntityFirePit) tEntity).iIsBurning = message.iIsBurning;

            ((TileEntityFirePit) tEntity).setStructureData(message.iData);
        }

        return null;
    }
}
