package com.Orion.Armory.Common.TileEntity.Core.Multiblock;

import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.Rectangle;
import com.sun.javaws.exceptions.InvalidArgumentException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Orion
 * Created on 29.06.2015
 * 16:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IStructureComponent
{
    String getStructureType();

    HashMap<Coordinate, IStructureComponent> getSlaveEntities();

    void registerNewSlave(TileEntity pNewSlaveEntity) throws InvalidArgumentException;

    void removeSlave(Coordinate pSlaveCoordinate);

    Rectangle getStructureSpace();

    void initiateAsMasterEntity();



    float getDistanceToMasterEntity();

    boolean isSlaved();

    IStructureComponent getMasterEntity();

    void initiateAsSlaveEntity(IStructureComponent pMasterEntity);



    void writeStructureToNBT(NBTTagCompound pTileEntityCompound);

    void readStructureFromNBT(NBTTagCompound pTileEntityCompound);


    @SideOnly(Side.CLIENT)
    TileEntitySpecialRenderer getRenderer(IStructureComponent pComponentToBeRendered);
}
