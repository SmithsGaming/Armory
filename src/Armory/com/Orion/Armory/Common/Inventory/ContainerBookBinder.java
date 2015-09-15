/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBookBinder extends ContainerArmory {
    public ContainerBookBinder(InventoryPlayer pPlayerInventory, TileEntityBookBinder pTargetTE, int modSlots) {
        super(pTargetTE, modSlots);

        addSlotToContainer(new Slot((IInventory) iTargetTE, 0, 113, 26) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return pStack.getItem() instanceof IBluePrintContainerItem;
            }
        });

        addSlotToContainer(new Slot((IInventory) iTargetTE, 1, 46, 26) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return pStack.getItem() instanceof IBluePrintItem;
            }
        });

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 71 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 129));
        }

    }


}
