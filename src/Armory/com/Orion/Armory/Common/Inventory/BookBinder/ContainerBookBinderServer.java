/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.BookBinder;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Common.Inventory.Slots.SlotReportable;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBookBinderServer extends ContainerArmory {

    public InventoryPlayer iPlayerInventory;

    public ContainerBookBinderServer(InventoryPlayer pPlayerInventory, TileEntityBookBinder pTargetTE, int modSlots) {
        super(pTargetTE, modSlots);

        iPlayerInventory = pPlayerInventory;

        this.OnContainerChanged(References.InternalNames.InputHandlers.Components.SLOTCHANGED);
    }

    @Override
    public void HandleCustomInput(String pInputID, String pInput) {
        if (pInputID.equals(References.InternalNames.InputHandlers.Components.TABCHANGED) && Integer.parseInt(pInput) == 0) {
            clearSlots();
            initializeBaseBookbindingSlots();
            initializeBookSlots(0);

            return;
        }
    }

    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        if (pComponentID.equals(References.InternalNames.InputHandlers.Components.TABCHANGED) && Integer.parseInt(pNewValue) == 0) {
            clearSlots();
            initializeBaseBookbindingSlots();

            super.updateComponentResult(pComponent, pComponentID, pNewValue);

            return;
        }
    }

    protected void clearSlots() {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();
    }

    protected void initializeBaseBookbindingSlots() {
        addSlotToContainer(new SlotReportable(this, (IInventory) iTargetTE, 0, 11, 11) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return pStack.getItem() instanceof IBluePrintContainerItem;
            }
        });

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(iPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 45 + inventoryColumnIndex * 18, 123 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(iPlayerInventory, actionBarSlotIndex, 45 + actionBarSlotIndex * 18, 181));
        }
    }

    protected void initializeBookSlots(int pStartIndex) {
        int tLastStackIndex = (8 - ((TileEntityBookBinder) iTargetTE).getActiveGroup().Stacks.size() % 8) + ((TileEntityBookBinder) iTargetTE).getActiveGroup().Stacks.size();

        if (tLastStackIndex < 8 * 5) {
            tLastStackIndex = 8 * 5;
        }

        for (int tSlotIndex = 0; tSlotIndex < tLastStackIndex; tSlotIndex++) {
            int tX = (tSlotIndex % 8) * 18;
            int tY = (tSlotIndex / 8) * 18;

            addSlotToContainer(new Slot((IInventory) iTargetTE, 1 + tSlotIndex, 78 + tX, 14 + tY) {
                @Override
                public boolean isItemValid(ItemStack pAttemptedStack) {
                    return pAttemptedStack.getItem() instanceof IBluePrintItem;
                }
            });
        }

    }

    @Override
    public void OnContainerChanged(String pComponent) {

        if (pComponent.equals(References.InternalNames.InputHandlers.Components.SLOTCHANGED)) {
            if (((IInventory) iTargetTE).getStackInSlot(0) != null) {
                clearSlots();
                initializeBaseBookbindingSlots();
                initializeBookSlots(0);
            } else {
                clearSlots();
                initializeBaseBookbindingSlots();
            }
        }

        super.OnContainerChanged(pComponent);
    }


}
