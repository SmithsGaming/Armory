package com.Orion.Armory.Common.Inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import com.Orion.Armory.Common.Inventory.Slots.SlotFuelInput;
import com.Orion.Armory.Common.Inventory.Slots.SlotHeatable;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFirepit extends ContainerArmory {
    private TileEntityFirePit iTEFirePit;

    public ContainerFirepit (InventoryPlayer pPlayerInventory, TileEntityFirePit pTEFirePit) {
        super(pTEFirePit, 10);

        this.iTEFirePit = pTEFirePit;

        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 0, 23, 27));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 1, 51, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 2, 80, 9));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 3, 109, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 4, 138, 27));

        for (int tSlotIndex = 0; tSlotIndex < pTEFirePit.FUELSTACK_AMOUNT; tSlotIndex++) {
            this.addSlotToContainer(new SlotFuelInput(pTEFirePit, tSlotIndex + 5, 44 + tSlotIndex * 18, 59));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot (EntityPlayer entityPlayer, int slotIndex) {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < modSlots) {
                if (!this.mergeItemStack(itemStack, modSlots, inventorySlots.size(), false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 0, modSlots, false)) {
                return null;
            }

            if (itemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }

    public TileEntityFirePit GetTileEntity () {
        return iTEFirePit;
    }
}
