/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity.FirePit;
/*
 *   TileEntityHeater
 *   Created by: Orion
 *   Created on: 12-10-2014
 */

import com.SmithsModding.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityHeater;
import com.SmithsModding.Armory.Network.NetworkManager;
import com.SmithsModding.Armory.Util.Core.Coordinate;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TileEntityHeater extends TileEntityArmory implements IInventory, IFirePitComponent {
    public ItemStack iFanStack = null;
    public int iItemInSlotTicks = 0;
    public float iLastRotationAngle = 0F;
    int iTargetX;
    int iTargetY;
    int iTargetZ;

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID == 0) {
            return iFanStack;
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize(int pSlotID, int pAmount) {
        if (pSlotID != 0) {
            return null;
        }

        if (iFanStack == null) {
            return null;
        }

        ItemStack itemstack;

        if (iFanStack.stackSize <= pAmount) {
            itemstack = iFanStack;
            iFanStack = null;
            this.markDirty();
            return itemstack;
        } else {
            itemstack = iFanStack.splitStack(pAmount);

            if (iFanStack.stackSize == 0) {
                iFanStack = null;
            }

            this.markDirty();
            return itemstack;
        }
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotID) {
        if (pSlotID == 0) {
            return iFanStack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int pSlotID, ItemStack pNewItemStack) {
        iFanStack = pNewItemStack;

        if (iFanStack != null) {
            if (iFanStack.getItemDamage() == 0) {
                iFanStack.setItemDamage(Short.MAX_VALUE);
            }
        }

        if (iFanStack == null) {
            iItemInSlotTicks = 0;
            iLastRotationAngle = 0F;
        }

        if (pNewItemStack != null && pNewItemStack.stackSize > this.getInventoryStackLimit()) {
            pNewItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.getDisplayName() : StatCollector.translateToLocal(References.InternalNames.Blocks.FirePit);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return ((this.getDisplayName().length() > 0) && this.getDisplayName().isEmpty() == false);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer pPlayer) {
        return true;
    }

    @Override
    public void openInventory() {
        //No animation and definitely no cat on top of this nice puppy
    }

    @Override
    public void closeInventory() {
        //NOOP
    }

    @Override
    public boolean isItemValidForSlot(int pSlotID, ItemStack pStack) {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound) {
        super.writeToNBT(pCompound);

        pCompound.setInteger(References.NBTTagCompoundData.TE.Heater.TICKSINSLOT, iItemInSlotTicks);
    }

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        return 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound pCompound) {
        super.readFromNBT(pCompound);

        pCompound.setInteger(References.NBTTagCompoundData.TE.Heater.TICKSINSLOT, iItemInSlotTicks);
    }

    @Override
    public void updateEntity() {
        if (IsHelpingAFirePit()) {
            iItemInSlotTicks++;
        } else {
            iItemInSlotTicks = 0;
        }
    }

    public boolean IsContainingAFan() {
        return (iFanStack != null);
    }

    public boolean IsHelpingAFirePit() {
        iTargetX = xCoord;
        iTargetY = yCoord + 1;
        iTargetZ = zCoord;

        if (!IsContainingAFan()) {
            return false;
        }

        TileEntity tTargetTE = getWorldObj().getTileEntity(iTargetX, iTargetY, iTargetZ);


        return tTargetTE instanceof TileEntityFirePit;


    }


    @Override
    public Packet getDescriptionPacket() {
        return NetworkManager.INSTANCE.getPacketFrom(new MessageTileEntityHeater(this));
    }

    public boolean validateTarget() {
        ForgeDirection oppositeDirection = getDirection().getOpposite();

        iTargetX = this.xCoord + oppositeDirection.offsetX;
        iTargetY = this.yCoord + oppositeDirection.offsetY;
        iTargetZ = this.zCoord + oppositeDirection.offsetZ;

        TileEntity tTargetTE = getWorldObj().getTileEntity(iTargetX, iTargetY, iTargetZ);
        if (tTargetTE == null) {
            return false;
        }

        return (tTargetTE instanceof TileEntityFirePit);
    }

    public boolean tryDamageFan(int damageAmount) {
        if (iFanStack == null)
            return false;

        Random tRand = new Random();

        if (tRand.nextInt(128) == 0) {
            iFanStack.setItemDamage(iFanStack.getItemDamage() - damageAmount);
            if (iFanStack.getItemDamage() == 0)
                iFanStack = null;
        }

        return true;
    }

    @Override
    public float getPositiveInflunce() {
        return 2.85F;
    }

    @Override
    public float getNegativeInfluece() {
        return 1.0F;
    }

    @Override
    public int getMaxTempInfluence() {
        return 1450;
    }

    @Override
    public boolean canInfluenceTE(Coordinate tTECoordinate) {
        return ((tTECoordinate.getYComponent() - yCoord) == 1) && tryDamageFan(1);
    }
}

