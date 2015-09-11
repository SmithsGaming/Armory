/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.TileEntity.Core;
/*
 *   TileEntityArmory
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.Orion.Armory.Util.References;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityArmory extends TileEntity
{
    private String iName = "";
    private ForgeDirection iCurrentDirection = ForgeDirection.UNKNOWN;

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

        if (this instanceof IInventory) {
            NBTTagList tInventoryCompound = pCompound.getTagList(References.NBTTagCompoundData.TE.Basic.INVENTORY, 10);
            IInventory tContents = (IInventory) this;

            for (int tComponentIndex = 0; tComponentIndex < tInventoryCompound.tagCount(); tComponentIndex++) {
                NBTTagCompound tStackCompound = tInventoryCompound.getCompoundTagAt(tComponentIndex);
                ItemStack tSlotContents = ItemStack.loadItemStackFromNBT(tStackCompound);

                Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

                tContents.setInventorySlotContents(tSlotIndex, tSlotContents);
            }
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

        if (this instanceof IInventory) {
            NBTTagList tInventoryCompound = new NBTTagList();
            IInventory tContents = (IInventory) this;

            for (int tSlotIndex = 0; tSlotIndex < tContents.getSizeInventory(); tSlotIndex++) {
                ItemStack tSlotContents = tContents.getStackInSlot(tSlotIndex);
                if (tSlotContents == null)
                    continue;

                NBTTagCompound tStackCompound = new NBTTagCompound();
                tStackCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tSlotIndex);
                tSlotContents.writeToNBT(tStackCompound);

                tInventoryCompound.appendTag(tStackCompound);
            }

            pCompound.setTag(References.NBTTagCompoundData.TE.Basic.INVENTORY, tInventoryCompound);
        }
    }

    public void setDirection(int pNewDirection)
    {
        iCurrentDirection = ForgeDirection.getOrientation(pNewDirection);
    }

    public ForgeDirection getDirection()
    {
        return this.iCurrentDirection;
    }

    public void setDirection(ForgeDirection pNewDirection)
    {
        this.iCurrentDirection = pNewDirection;
    }

    public boolean hasCustomName()
    {
        return iName != null && iName.length() > 0;
    }

    public String getDisplayName()
    {
        return this.iName;
    }

    public void setDisplayName(String pName)
    {
        this.iName = pName;
    }

    public abstract float getProgressBarValue(String pProgressBarID);

    public void handleGuiComponentUpdate(String pInputID, String pInput) {
    }

    public Object getGUIComponentRelatedObject(String pComponentID)
    {
        return 0;
    }
}
