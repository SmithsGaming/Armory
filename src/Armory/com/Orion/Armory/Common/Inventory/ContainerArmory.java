package com.Orion.Armory.Common.Inventory;
/*
 *   ContainerArmory
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerArmory extends Container
{
    protected final int PLAYER_INVENTORY_ROWS = 3;
    protected final int PLAYER_INVENTORY_COLUMNS = 9;

    public TileEntityArmory iTargetTE;

    ContainerArmory(TileEntityArmory pTargetTE)
    {
         iTargetTE = pTargetTE;
    }

    @Override
    public boolean canInteractWith(EntityPlayer pPlayer) {
        return true;
    }

    @Override
    protected boolean mergeItemStack(ItemStack pItemStack, int pSlotMin, int pSlotMax, boolean pAscending)
    {
        boolean tSlotFound = false;
        int tCurrentSlotIndex = pAscending ? pSlotMax - 1 : pSlotMin;

        Slot tSlot;
        ItemStack tSlotInStack;

        if (pItemStack.isStackable())
        {
            while (pItemStack.stackSize > 0 && (!pAscending && tCurrentSlotIndex < pSlotMax || pAscending && tCurrentSlotIndex >= pSlotMin))
            {
                tSlot = (Slot) this.inventorySlots.get(tCurrentSlotIndex);
                tSlotInStack = tSlot.getStack();

                if (tSlot.isItemValid(pItemStack) && ItemStackHelper.equalsIgnoreStackSize(pItemStack, tSlotInStack))
                {
                    int combinedStackSize = tSlotInStack.stackSize + pItemStack.stackSize;
                    int slotStackSizeLimit = Math.min(tSlotInStack.getMaxStackSize(), tSlot.getSlotStackLimit());

                    if (combinedStackSize <= slotStackSizeLimit)
                    {
                        pItemStack.stackSize = 0;
                        tSlotInStack.stackSize = combinedStackSize;
                        tSlot.onSlotChanged();
                        tSlotFound = true;
                    }
                    else if (tSlotInStack.stackSize < slotStackSizeLimit)
                    {
                        pItemStack.stackSize -= slotStackSizeLimit - tSlotInStack.stackSize;
                        tSlotInStack.stackSize = slotStackSizeLimit;
                        tSlot.onSlotChanged();
                        tSlotFound = true;
                    }
                }

                tCurrentSlotIndex += pAscending ? -1 : 1;
            }
        }

        if (pItemStack.stackSize > 0)
        {
            tCurrentSlotIndex = pAscending ? pSlotMax - 1 : pSlotMin;

            while (!pAscending && tCurrentSlotIndex < pSlotMax || pAscending && tCurrentSlotIndex >= pSlotMin)
            {
                tSlot = (Slot) this.inventorySlots.get(tCurrentSlotIndex);
                tSlotInStack = tSlot.getStack();

                if (tSlot.isItemValid(pItemStack) && tSlotInStack == null)
                {
                    tSlot.putStack(ItemStackHelper.cloneItemStack(pItemStack, Math.min(pItemStack.stackSize, tSlot.getSlotStackLimit())));
                    tSlot.onSlotChanged();

                    if (tSlot.getStack() != null)
                    {
                        pItemStack.stackSize -= tSlot.getStack().stackSize;
                        tSlotFound = true;
                    }

                    break;
                }

                tCurrentSlotIndex += pAscending ? -1 : 1;
            }
        }

        return tSlotFound;
    }
}
