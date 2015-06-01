package com.Orion.Armory.Common.Inventory;

import com.Orion.Armory.Common.Inventory.Slots.SlotFan;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 23.04.2015
 * 13:02
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ContainerHeater extends ContainerArmory {
    private TileEntityHeater iTEHeater;

    public ContainerHeater (InventoryPlayer pPlayerInventory, TileEntityHeater pTEHeater) {
        super(pTEHeater, 1);
        this.iTEHeater = pTEHeater;
        this.addSlotToContainer(new SlotFan(pTEHeater, 0, 80, 24));

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 56 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 114));
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

    public TileEntityHeater getTileEntity () {
        return iTEHeater;
    }
}
