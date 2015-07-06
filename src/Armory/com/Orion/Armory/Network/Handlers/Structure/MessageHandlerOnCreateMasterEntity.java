package com.Orion.Armory.Network.Handlers.Structure;

import com.Orion.Armory.Common.PathFinding.IPathComponent;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Network.Messages.Structure.MessageOnCreateMasterEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 15:54
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnCreateMasterEntity implements IMessageHandler<MessageOnCreateMasterEntity, IMessage> {

    @Override
    public IMessage onMessage(MessageOnCreateMasterEntity message, MessageContext ctx) {

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.getXComponent(), message.iTECoordinate.getYComponent(), message.iTECoordinate.getZComponent());
        if (tEntity instanceof IStructureComponent)
        {
            ((IStructureComponent) tEntity).initiateAsMasterEntity();
        }

        return null;
    }
}
