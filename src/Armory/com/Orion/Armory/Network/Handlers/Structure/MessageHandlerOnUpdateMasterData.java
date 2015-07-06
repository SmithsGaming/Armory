package com.Orion.Armory.Network.Handlers.Structure;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Network.Messages.Structure.MessageOnUpdateMasterData;
import com.Orion.Armory.Util.Core.Coordinate;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 16:01
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnUpdateMasterData implements IMessageHandler<MessageOnUpdateMasterData, IMessage> {

    @Override
    public IMessage onMessage(MessageOnUpdateMasterData message, MessageContext ctx) {

        TileEntity tMasterEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.getXComponent(), message.iTECoordinate.getYComponent(), message.iTECoordinate.getZComponent());
        if (tMasterEntity instanceof IStructureComponent)
        {
            ((IStructureComponent) tMasterEntity).initiateAsMasterEntity();

            for (Coordinate tSlaveCoord : message.iSlaveCoords)
            {
                try
                {
                    ((IStructureComponent) tMasterEntity).registerNewSlave(FMLClientHandler.instance().getClient().theWorld.getTileEntity(tSlaveCoord.getXComponent(), tSlaveCoord.getYComponent(), tSlaveCoord.getZComponent()));
                }
                catch (Exception Ex)
                {
                    GeneralRegistry.iLogger.info("Failed to handle a Coordinate while synchronising structure data: " + tSlaveCoord.toString());
                }
            }
        }

        return null;
    }
}
