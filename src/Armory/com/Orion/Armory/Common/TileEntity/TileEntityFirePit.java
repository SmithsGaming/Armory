package com.Orion.Armory.Common.TileEntity;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Registry.IngotRegistry;
import com.Orion.Armory.Util.HeatedIngots.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileEntityFirePit extends TileEntity implements IInventory
{
    protected ArrayList<ItemStack> iIngotsInFire = new ArrayList<ItemStack>(5);
    protected int iNumPlayersUsing;
    protected float iCurrentTemperature = 20;
    protected float iLastAddedHeat = 0;

    @Override
    public int getSizeInventory() {
        return iIngotsInFire.size();
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return iIngotsInFire.get(p_70301_1_);
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        if (this.iIngotsInFire.get(p_70298_1_) != null)
        {
            ItemStack itemstack;

            if (this.iIngotsInFire.get(p_70298_1_).stackSize <= p_70298_2_)
            {
                itemstack = this.iIngotsInFire.get(p_70298_1_);
                this.iIngotsInFire.set(p_70298_1_, null);
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.iIngotsInFire.get(p_70298_1_).splitStack(p_70298_2_);

                if (this.iIngotsInFire.get(p_70298_1_).stackSize == 0)
                {
                    this.iIngotsInFire.set(p_70298_1_, null);
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
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        if (this.iIngotsInFire.get(p_70304_1_) != null)
        {
            ItemStack itemstack = this.iIngotsInFire.get(p_70304_1_);
            this.iIngotsInFire.set(p_70304_1_, null);
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        this.iIngotsInFire.set(p_70299_1_, p_70299_2_);

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return References.InternalNames.TileEntities.FirePitContainter;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
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
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    public void updateEntity()
    {
        int tTotalIngots = iIngotsInFire.size();
        float tAddedHeat = this.iLastAddedHeat / (float) (tTotalIngots + 1);
        for (ItemStack tIngot : iIngotsInFire)
        {
            int tIndex = iIngotsInFire.indexOf(tIngot);

            if (IngotRegistry.geInstance().isHeatable(tIngot))
            {
                tIngot = HeatedIngotFactory.getInstance().convertToHeatedIngot(tIngot);
            }

            tIngot = NBTHelper.heatHeatedItem(tIngot, (int) tAddedHeat);

            if (NBTHelper.getTemperatureOfIngot(tIngot) <= 20)
            {
                tIngot = HeatedIngotFactory.getInstance().convertToCooleadIngot(tIngot);
            }

            iIngotsInFire.add(tIndex, tIngot);
            this.markDirty();
        }
    }

    public float heatFurnace(float pFuelAmount)
    {
        this.iCurrentTemperature += pFuelAmount / 20F;
        this.iLastAddedHeat = pFuelAmount / 20F;
        return this.iCurrentTemperature;
    }
}
