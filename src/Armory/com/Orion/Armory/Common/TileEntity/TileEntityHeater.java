package com.Orion.Armory.Common.TileEntity;
/*
 *   TileEntityHeater
 *   Created by: Orion
 *   Created on: 12-10-2014
 */

import com.Orion.Armory.Util.References;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityHeater extends TileEntity implements IInventory
{
    protected boolean iIsHeater = false;
    protected ArrayList<ItemStack> iFuelStacks = new ArrayList<ItemStack>(5);
    protected ItemStack iFanStack = null;

    protected int iNumPlayersUsing;
    protected ForgeDirection iCurrentDirection = ForgeDirection.NORTH;
    protected String iName = "Heater";

    @Override
    public int getSizeInventory() {
        if (iIsHeater) return 0;

        return 5;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        return iFuelStacks.get(pSlotID);
    }

    @Override
    public ItemStack decrStackSize(int pSlotID, int pAmount) {
        if (this.iFuelStacks.get(pSlotID) != null)
        {
            ItemStack itemstack;

            if (this.iFuelStacks.get(pSlotID).stackSize <= pAmount)
            {
                itemstack = this.iFuelStacks.get(pSlotID);
                this.iFuelStacks.set(pSlotID, null);
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.iFuelStacks.get(pSlotID).splitStack(pAmount);

                if (this.iFuelStacks.get(pSlotID).stackSize == 0)
                {
                    this.iFuelStacks.set(pSlotID, null);
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotID) {
        if (this.iFuelStacks.get(pSlotID) != null)
        {
            ItemStack itemstack = this.iFuelStacks.get(pSlotID);
            this.iFuelStacks.set(pSlotID, null);
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        this.iFuelStacks.set(p_70299_1_, p_70299_2_);

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return References.InternalNames.TileEntities.HeaterComponent;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
        if (this.iNumPlayersUsing < 0)
        {
            this.iNumPlayersUsing = 0;
        }

        ++this.iNumPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.iNumPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        if (this.getBlockType() instanceof BlockChest)
        {
            --this.iNumPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.iNumPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }

    @Override
    public boolean isItemValidForSlot(int pSlotID, ItemStack pStack) {
        return false;
    }

    public void updateEntity()
    {
        
    }
}
