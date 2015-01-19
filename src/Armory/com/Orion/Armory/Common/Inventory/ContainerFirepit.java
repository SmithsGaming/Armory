package com.Orion.Armory.Common.Inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import com.Orion.Armory.Common.Inventory.Slots.SlotFuelInput;
import com.Orion.Armory.Common.Inventory.Slots.SlotHeatable;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;

public class ContainerFirePit extends ContainerArmory
{
    private TileEntityFirePit iTEFirePit;
    private ArrayList<Integer> iFuelStackBurningTime = new ArrayList<Integer>(iTEFirePit.FUELSTACK_AMOUNT);
    private ArrayList<Integer> iFuelStackFuelAmount = new ArrayList<Integer>(iTEFirePit.FUELSTACK_AMOUNT);

    public ContainerFirePit(InventoryPlayer pPlayerInventory, TileEntityFirePit pTEFirePit)
    {
        this.iTEFirePit = pTEFirePit;

        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 0, 23, 27));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 1, 51, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 2, 80, 9));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 3, 109, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 4, 138, 27));

        for (int tSlotIndex = 0; tSlotIndex < pTEFirePit.FUELSTACK_AMOUNT; tSlotIndex++)
        {
            this.addSlotToContainer(new SlotFuelInput(pTEFirePit, tSlotIndex + 5, 44 + tSlotIndex * 18, 63));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }
}
