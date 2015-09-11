/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.BookBinder;

import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Common.Inventory.Slots.SlotScrollableInventory;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Network.Messages.MessageCustomInput;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBookBinderClient extends ContainerBookBinderServer {

    private int iLastGeneratedStartIndex = -1;

    public ContainerBookBinderClient(InventoryPlayer pPlayerInventory, TileEntityBookBinder pTargetTE, int modSlots) {
        super(pPlayerInventory, pTargetTE, modSlots);
    }

    @Override
    public Slot getSlot(int pSlotIndex) {
        if (pSlotIndex < (1 + 4 * 9))
            return super.getSlot(pSlotIndex);

        if (pSlotIndex < iLastGeneratedStartIndex || pSlotIndex >= iLastGeneratedStartIndex + (8 * 5)) {
            return new Slot((IInventory) iTargetTE, pSlotIndex - 37, -20, -20) {
                @Override
                public boolean isItemValid(ItemStack pAttemptedStack) {
                    return pAttemptedStack.getItem() instanceof IBluePrintItem;
                }
            };
        }

        return super.getSlot(37 + (pSlotIndex - iLastGeneratedStartIndex));
    }

    @Override
    protected void initializeBookSlots(int pStartIndex) {
        int tLastStackIndex = (8 - ((TileEntityBookBinder) iTargetTE).getActiveGroup().Stacks.size() % 8) + ((TileEntityBookBinder) iTargetTE).getActiveGroup().Stacks.size();

        if (tLastStackIndex < 8 * 5) {
            tLastStackIndex = 8 * 5;
        }

        for (int tSlotIndex = 0; tSlotIndex < 8 * 5; tSlotIndex++) {
            if (tSlotIndex + pStartIndex < tLastStackIndex) {
                int tX = (tSlotIndex % 8) * 18;
                int tY = (tSlotIndex / 8) * 18;

                addSlotToContainer(new SlotScrollableInventory((IInventory) iTargetTE, 37 + tSlotIndex, 78 + tX, 14 + tY) {
                    @Override
                    public boolean isItemValid(ItemStack pAttemptedStack) {
                        return pAttemptedStack.getItem() instanceof IBluePrintItem;
                    }
                });
            } else {
                break;
            }
        }

        iLastGeneratedStartIndex = pStartIndex;
    }

    protected void updateBookSlots(int pStartIndex) {
        for (int tSlotIndex = 0; tSlotIndex < 5 * 8; tSlotIndex++) {
            SlotScrollableInventory tSlot = (SlotScrollableInventory) this.inventorySlots.get(tSlotIndex + 37);
            tSlot.setSlotIndex(pStartIndex + tSlotIndex);
        }

        iLastGeneratedStartIndex = pStartIndex;
    }


    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        if (pComponentID.equals(References.InternalNames.InputHandlers.Components.TABCHANGED) && Integer.parseInt(pNewValue) == 0) {
            clearSlots();
            initializeBaseBookbindingSlots();

            return;
        }

        if (pComponent.getInternalName().equals(References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKCONTENTS + ".Scrollbar") && References.InternalNames.InputHandlers.Components.SCROLL.equals(pComponentID)) {
            updateBookSlots((Integer.parseInt(pNewValue) / 8) * 8);

            NetworkManager.INSTANCE.sendToServer(new MessageCustomInput(pComponentID, "-"));
        }
    }
}
