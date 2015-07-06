package com.Orion.Armory.Network.Messages.Structure;

import com.Orion.Armory.Common.PathFinding.IPathComponent;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Util.Core.Coordinate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 15:24
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnCreateMasterEntity implements IMessage
{

    public Coordinate iTECoordinate;

    public MessageOnCreateMasterEntity(IStructureComponent pComponentToSync)
    {
        iTECoordinate = pComponentToSync.getLocation();
    }

    public MessageOnCreateMasterEntity()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        iTECoordinate = Coordinate.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        iTECoordinate.toBytes(buf);
    }
}
