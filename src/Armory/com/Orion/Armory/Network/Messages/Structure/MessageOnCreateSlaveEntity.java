package com.Orion.Armory.Network.Messages.Structure;

import com.Orion.Armory.API.Structures.IStructureComponent;
import com.Orion.Armory.Util.Core.Coordinate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 16:04
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnCreateSlaveEntity implements IMessage {


    public Coordinate iTECoordinate;
    public Coordinate iMasterCoorinate;

    public MessageOnCreateSlaveEntity(IStructureComponent pComponentToSync, IStructureComponent pMasterComponent)
    {
        iTECoordinate = pComponentToSync.getLocation();
        iMasterCoorinate = pMasterComponent.getLocation();
    }

    public MessageOnCreateSlaveEntity()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        iTECoordinate = Coordinate.fromBytes(buf);
        iMasterCoorinate = Coordinate.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        iTECoordinate.toBytes(buf);
        iMasterCoorinate.toBytes(buf);
    }
}
