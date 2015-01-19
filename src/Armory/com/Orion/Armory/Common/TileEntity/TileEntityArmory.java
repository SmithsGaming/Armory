package com.Orion.Armory.Common.TileEntity;
/*
 *   TileEntityArmory
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.Orion.Armory.Util.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityArmory extends TileEntity
{
    protected String iName = "";
    protected ForgeDirection iCurrentDirection;

    @Override
    public void readFromNBT(NBTTagCompound pCompound) {
        super.readFromNBT(pCompound);

        if (pCompound.hasKey(References.NBTTagCompoundData.TE.Basic.DIRECTION))
        {
            this.iCurrentDirection = ForgeDirection.getOrientation(pCompound.getByte(References.NBTTagCompoundData.TE.Basic.DIRECTION));
        }

        if (pCompound.hasKey(References.NBTTagCompoundData.TE.Basic.NAME))
        {
            this.iName = pCompound.getString(References.NBTTagCompoundData.TE.Basic.NAME);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound)
    {
        super.writeToNBT(pCompound);
        pCompound.setByte(References.NBTTagCompoundData.TE.Basic.DIRECTION, (byte) iCurrentDirection.ordinal());

        if (this.hasCustomName())
        {
            pCompound.setString(References.NBTTagCompoundData.TE.Basic.NAME, iName);
        }
    }

    public void setDirection(int pNewDirection)
    {
        iCurrentDirection = ForgeDirection.getOrientation(pNewDirection);
    }

    public void setDirection(ForgeDirection pNewDirection)
    {
        this.iCurrentDirection = pNewDirection;
    }

    public ForgeDirection getDirection()
    {
        return this.iCurrentDirection;
    }

    public boolean hasCustomName()
    {
        return iName != null && iName.length() > 0;
    }

    public void setDisplayName(String pName)
    {
        this.iName = pName;
    }

    public String getDisplayName()
    {
        return this.iName;
    }
}
