/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.Items;

import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Common.Item.Knowledge.ItemSmithingsGuide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InventorySmithingsGuideBlueprints implements IInventory {
    ItemSmithingsGuide.LabelledBlueprintGroup iBaseGroup;

    public InventorySmithingsGuideBlueprints(ItemSmithingsGuide.LabelledBlueprintGroup pBaseGroup) {
        iBaseGroup = pBaseGroup;
    }

    public ArrayList<ItemStack> getBluePrints() {
        return iBaseGroup.Stacks;
    }

    @Override
    public int getSizeInventory() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        return iBaseGroup.Stacks.get(pSlotID);
    }

    @Override
    public ItemStack decrStackSize(int pSlotID, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotID);
        if (tItemStack == null) {
            return tItemStack;
        }
        if (tItemStack.stackSize < pDecrAmount) {
            setInventorySlotContents(pSlotID, null);
        } else {
            tItemStack = tItemStack.splitStack(pDecrAmount);
            if (tItemStack.stackSize == 0) {
                setInventorySlotContents(pSlotID, null);
            }
        }

        return tItemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotID) {
        ItemStack tItemStack = getStackInSlot(pSlotID);
        if (tItemStack != null) {
            setInventorySlotContents(pSlotID, null);
        }

        return tItemStack;
    }

    @Override
    public void setInventorySlotContents(int pSlotID, ItemStack pStack) {
        iBaseGroup.Stacks.add(pSlotID, pStack);
    }

    @Override
    public String getInventoryName() {
        return iBaseGroup.LabelStack.getDisplayName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return iBaseGroup.LabelStack.hasDisplayName();
    }

    @Override
    public int getInventoryStackLimit() {
        return iBaseGroup.Stacks.size();
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer pPlayer) {
        return true;
    }

    @Override
    public void openInventory() {
        ///NOOP
    }

    @Override
    public void closeInventory() {
        ///NOOP
    }

    @Override
    public boolean isItemValidForSlot(int pSlotID, ItemStack pStack) {
        return pStack.getItem() instanceof IBluePrintItem;
    }
}
