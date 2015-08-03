/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.Items;

import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;

public class InventorySmithingsGuideBlueprints implements IInventory {
    ItemStack iBaseStack;
    HashMap<Integer, ItemStack> iBlueprints = new HashMap<Integer, ItemStack>();

    public InventorySmithingsGuideBlueprints(ItemStack pStack) {
        iBaseStack = pStack;

        loadStacksFromStorage();
    }

    private void loadStacksFromStorage() {
        NBTTagCompound tStackCompound = iBaseStack.getTagCompound();

        NBTTagList tInventory = tStackCompound.getTagList(References.NBTTagCompoundData.Item.ItemInventory.INVENTORY, 10);
        for (int tCompoundIndex = 0; tCompoundIndex < tInventory.tagCount(); tCompoundIndex++) {
            NBTTagCompound tInventoryStackCompound = tInventory.getCompoundTagAt(tCompoundIndex);
            iBlueprints.put(tInventoryStackCompound.getInteger(References.NBTTagCompoundData.Item.ItemInventory.SLOTID), ItemStack.loadItemStackFromNBT(tInventoryStackCompound.getCompoundTag(References.NBTTagCompoundData.Item.ItemInventory.STACK)));
        }
    }

    private void saveStacksToStorage() {
        ArrayList<ItemStack> tStacks = (ArrayList<ItemStack>) iBlueprints.values();
        iBlueprints.clear();

        for (int tNewStackIndex = 0; tNewStackIndex < tStacks.size(); tNewStackIndex++) {
            iBlueprints.put(tNewStackIndex, tStacks.get(tNewStackIndex));
        }

        NBTTagCompound tStackCompound = iBaseStack.getTagCompound();

        NBTTagList tInventory = new NBTTagList();
        for (Integer tSlotID : iBlueprints.keySet()) {
            ItemStack tSlotStack = iBlueprints.get(tSlotID);

            NBTTagCompound tInventoryStackCompound = new NBTTagCompound();
            tInventoryStackCompound.setInteger(References.NBTTagCompoundData.Item.ItemInventory.SLOTID, tSlotID);

            NBTTagCompound tStackDataCompound = new NBTTagCompound();
            tSlotStack.writeToNBT(tStackDataCompound);
            tInventoryStackCompound.setTag(References.NBTTagCompoundData.Item.ItemInventory.STACK, tStackDataCompound);

            tInventory.appendTag(tInventoryStackCompound);
        }

        tStackCompound.setTag(References.NBTTagCompoundData.Item.ItemInventory.INVENTORY, tInventory);
        iBaseStack.setTagCompound(tStackCompound);
    }

    public ItemStack getBaseStack() {
        return iBaseStack;
    }

    public ArrayList<ItemStack> getBluePrints() {
        return (ArrayList<ItemStack>) iBlueprints.values();
    }

    @Override
    public int getSizeInventory() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        return iBlueprints.get(pSlotID);
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
        iBlueprints.put(pSlotID, pStack);
        saveStacksToStorage();
    }

    @Override
    public String getInventoryName() {
        return iBaseStack.getDisplayName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return iBaseStack.hasDisplayName();
    }

    @Override
    public int getInventoryStackLimit() {
        return iBlueprints.size();
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
