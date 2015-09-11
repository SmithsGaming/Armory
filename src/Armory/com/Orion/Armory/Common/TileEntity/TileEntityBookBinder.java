/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.TileEntity;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Common.Item.Knowledge.LabelledBlueprintGroup;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class TileEntityBookBinder extends TileEntityArmory implements IInventory {

    ItemStack iBookStack;
    ArrayList<LabelledBlueprintGroup> iGroups;
    LabelledBlueprintGroup iActiveGroup;
    Boolean iBookBindingTabOpen = true;

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        return 1F;
    }

    @Override
    public int getSizeInventory() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotIndex) {
        if (iBookBindingTabOpen) {
            if (pSlotIndex == 0) {
                return iBookStack;
            } else {
                if (iActiveGroup == null)
                    return null;

                pSlotIndex -= 1;

                if (pSlotIndex >= iActiveGroup.Stacks.size())
                    return null;

                return iActiveGroup.Stacks.get(pSlotIndex);
            }
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack == null) {
            return tItemStack;
        }
        if (tItemStack.stackSize < pDecrAmount) {
            setInventorySlotContents(pSlotIndex, null);
        } else {
            tItemStack = tItemStack.splitStack(pDecrAmount);
            if (tItemStack.stackSize == 0) {
                setInventorySlotContents(pSlotIndex, null);
            }
        }

        return tItemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotIndex) {
        return pSlotIndex == 0 ? iBookStack : null;
    }

    @Override
    public void setInventorySlotContents(int pSlotIndex, ItemStack pStack) {
        if (iBookBindingTabOpen) {
            if (pSlotIndex == 0) {
                iBookStack = pStack;

                if (pStack == null) {
                    iBookStack = null;
                    iActiveGroup = null;
                    return;
                }

                iGroups = ((IBluePrintContainerItem) pStack.getItem()).getBlueprintGroups(pStack);
                if (iGroups.size() == 0) {
                    LabelledBlueprintGroup tUnsortedGroup = new LabelledBlueprintGroup();
                    iGroups.add(tUnsortedGroup);
                    iActiveGroup = tUnsortedGroup;
                    ((IBluePrintContainerItem) pStack.getItem()).writeBlueprintGroupsToStack(pStack, iGroups);
                } else {
                    iActiveGroup = iGroups.get(0);
                }
            } else {
                pSlotIndex -= 1;
                if (iActiveGroup != null) {
                    if (pSlotIndex > iActiveGroup.Stacks.size())
                        pSlotIndex = iActiveGroup.Stacks.size();

                    if (pSlotIndex < iActiveGroup.Stacks.size()) {
                        iActiveGroup.Stacks.remove(pSlotIndex);
                    }

                    if (pStack != null) {
                        iActiveGroup.Stacks.add(pSlotIndex, pStack);
                    }

                    if (iBookStack != null) {
                        ((IBluePrintContainerItem) iBookStack.getItem()).writeBlueprintGroupsToStack(iBookStack, iGroups);
                    }
                }
            }
        }
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.getDisplayName() : StatCollector.translateToLocal(References.InternalNames.Blocks.ArmorsAnvil);
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
    public boolean isItemValidForSlot(int pSlotID, ItemStack pItemStack) {
        return pSlotID == 0 && pItemStack.getItem() instanceof IBluePrintItem;
    }

    public LabelledBlueprintGroup getActiveGroup() {
        return iActiveGroup;
    }

    @Override
    public Object getGUIComponentRelatedObject(String pComponentID) {
        if (pComponentID.equals(References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKCONTENTS + ".StackCount") && iActiveGroup != null) {
            return iActiveGroup.Stacks.size() + 1;
        } else if (pComponentID.equals(References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKCONTENTS + ".StackCount")) {
            return 1;
        }

        return 0;
    }
}
