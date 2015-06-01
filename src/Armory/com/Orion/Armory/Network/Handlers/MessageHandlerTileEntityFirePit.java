package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Network.Messages.MessageTileEntityFirePit;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * Created by Orion
 * Created on 4/17/2015
 * 2:37 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerTileEntityFirePit extends MessageHandlerTileEntityArmory implements IMessageHandler<MessageTileEntityFirePit, IMessage>
{
    @Override
    public IMessage onMessage(MessageTileEntityFirePit message, MessageContext ctx) {
        super.onMessage(message, ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityFirePit)
        {
            ((TileEntityFirePit) tEntity).iIngotStacks = message.iIngotStacks;
            ((TileEntityFirePit) tEntity).iFuelStacks = message.iFuelStacks;
            ((TileEntityFirePit) tEntity).iFuelStackBurningTime = message.iFuelStackBurningTime;
            ((TileEntityFirePit) tEntity).iFuelStackFuelAmount = message.iFuelStackFuelAmount;
            ((TileEntityFirePit) tEntity).iMaxTemperature = message.iMaxTemperature;
            ((TileEntityFirePit) tEntity).iCurrentTemperature = message.iCurrentTemperature;
            ((TileEntityFirePit) tEntity).iLastAddedHeat = message.iLastAddedHeat;
            ((TileEntityFirePit) tEntity).iIsBurning = message.iIsBurning;
        }

        return null;
    }
}
