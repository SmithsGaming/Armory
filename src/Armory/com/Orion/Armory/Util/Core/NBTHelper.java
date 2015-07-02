package com.Orion.Armory.Util.Core;

import com.Orion.Armory.Util.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by Orion
 * Created on 02.07.2015
 * 13:33
 * <p/>
 * Copyrighted according to Project specific license
 */
public class NBTHelper
{

    public static Coordinate readCoordinateFromNBT(NBTTagCompound pCoordinateData)
    {
        return new Coordinate(pCoordinateData.getInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.XCOORD), pCoordinateData.getInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.YCOORD), pCoordinateData.getInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.ZCOORD));
    }

    public static NBTTagCompound writeCoordinateToNBT(Coordinate pCoordinate)
    {
        NBTTagCompound tCoordinateData = new NBTTagCompound();

        tCoordinateData.setInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.XCOORD, pCoordinate.getXComponent());
        tCoordinateData.setInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.YCOORD, pCoordinate.getYComponent());
        tCoordinateData.setInteger(References.NBTTagCompoundData.TE.Basic.Coordinate.ZCOORD, pCoordinate.getZComponent());

        return tCoordinateData;
    }
}
