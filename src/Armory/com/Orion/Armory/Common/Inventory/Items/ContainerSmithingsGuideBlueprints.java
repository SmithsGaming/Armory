/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.Items;

import com.Orion.Armory.Common.Inventory.ContainerArmory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSmithingsGuideBlueprints extends ContainerArmory {
    InventorySmithingsGuideBlueprints iBaseInventory;
    EntityPlayer iPlayer;

    ContainerSmithingsGuideBlueprints(EntityPlayer pPlayer, InventorySmithingsGuideBlueprints pInventory) {
        super(null, pInventory.getSizeInventory());

        iBaseInventory = pInventory;
        iPlayer = pPlayer;

        setSlotContents(0);
    }

    private void setSlotContents(int tStartSlotID) {
        for (int tRowIndex = 0; tRowIndex < 7; tRowIndex++) {
            for (int tColumnIndex = 0; tColumnIndex < PLAYER_INVENTORY_COLUMNS; tColumnIndex++) {
                addSlotToContainer(new Slot(iBaseInventory, tStartSlotID, 8 + tColumnIndex * 18, 8 + tRowIndex * 18));
                tStartSlotID++;
            }
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(iPlayer.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 152 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(iPlayer.inventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 214));
        }
    }


    @Override
    public void HandleCustomInput(String pInputID, String pInput) {

    }
}
