/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Structures;

import com.SmithsModding.Armory.Common.PathFinding.IPathComponent;
import com.SmithsModding.Armory.Util.Core.Coordinate;
import com.SmithsModding.Armory.Util.Core.Rectangle;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;

public interface IStructureComponent extends IPathComponent {
    String getStructureType();

    HashMap<Coordinate, IStructureComponent> getSlaveEntities();

    void registerNewSlave(TileEntity pNewSlaveEntity);

    void removeSlave(Coordinate pSlaveCoordinate);

    Rectangle getStructureSpace();

    void initiateAsMasterEntity();

    IStructureData getStructureRelevantData();

    void setStructureData(IStructureData pNewData);


    float getDistanceToMasterEntity();

    boolean isSlaved();

    IStructureComponent getMasterEntity();

    void initiateAsSlaveEntity(IStructureComponent pMasterEntity);

    boolean countsAsConnectingComponent();


    void writeStructureToNBT(NBTTagCompound pTileEntityCompound);

    void readStructureFromNBT(NBTTagCompound pTileEntityCompound);

    IStructureComponentRenderer getRenderer(IStructureComponent pComponentToBeRendered);
}
