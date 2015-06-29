package com.Orion.Armory.Common.TileEntity.Core.Multiblock;

import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Orion
 * Created on 29.06.2015
 * 16:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IMasterTileEntity
{

    HashMap<Coordinate, TileEntity> getSlaveEntities();

    void registerNewSlave(TileEntity pNewSlaveEntity);

    void removeSlave(Coordinate pSlaveCoordinate);
}
