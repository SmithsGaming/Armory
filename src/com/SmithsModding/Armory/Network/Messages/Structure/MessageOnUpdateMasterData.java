package com.SmithsModding.Armory.Network.Messages.Structure;

import com.SmithsModding.Armory.API.Structures.IStructureComponent;
import com.SmithsModding.Armory.Util.Core.Coordinate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 15:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnUpdateMasterData implements IMessage {

    public Coordinate iTECoordinate;
    public ArrayList<Coordinate> iSlaveCoords = new ArrayList<Coordinate>();

    public MessageOnUpdateMasterData(IStructureComponent pMasterComponent) {
        iTECoordinate = pMasterComponent.getMasterEntity().getLocation();

        for (Coordinate tSlave : pMasterComponent.getMasterEntity().getSlaveEntities().keySet())
            iSlaveCoords.add(tSlave);
    }

    public MessageOnUpdateMasterData() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        iTECoordinate = Coordinate.fromBytes(buf);

        int tSlaveCount = buf.readInt();

        for (int tSlave = 0; tSlave < tSlaveCount; tSlave++)
            iSlaveCoords.add(Coordinate.fromBytes(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        iTECoordinate.toBytes(buf);

        buf.writeInt(iSlaveCoords.size());

        for (Coordinate tSlave : iSlaveCoords)
            tSlave.toBytes(buf);
    }
}

