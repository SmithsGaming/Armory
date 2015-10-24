package com.SmithsModding.Armory.Network.Handlers.Structure;

import com.SmithsModding.Armory.API.Structures.IStructureComponent;
import com.SmithsModding.Armory.Network.Messages.Structure.MessageOnCreateSlaveEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 16:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnCreateSlaveEntity implements IMessageHandler<MessageOnCreateSlaveEntity, IMessage> {

    @Override
    public IMessage onMessage(MessageOnCreateSlaveEntity message, MessageContext ctx) {

        TileEntity tSlaveEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.getXComponent(), message.iTECoordinate.getYComponent(), message.iTECoordinate.getZComponent());
        if (tSlaveEntity instanceof IStructureComponent) {
            ((IStructureComponent) tSlaveEntity).initiateAsSlaveEntity((IStructureComponent) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iMasterCoorinate.getXComponent(), message.iMasterCoorinate.getYComponent(), message.iMasterCoorinate.getZComponent()));
        }

        return null;
    }
}
