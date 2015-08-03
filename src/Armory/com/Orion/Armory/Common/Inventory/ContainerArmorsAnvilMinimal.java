package com.Orion.Armory.Common.Inventory;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.Common.Item.ItemHammer;
import com.Orion.Armory.Common.Item.ItemTongs;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 15.05.2015
 * 13:38
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ContainerArmorsAnvilMinimal extends ContainerArmory {
    public ContainerArmorsAnvilMinimal (InventoryPlayer pPlayerInventory, TileEntityArmorsAnvil pTargetTE) {
        super(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS);

        for (int tSlotIndex = 0; tSlotIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex++) {
            int tRowIndex = ((tSlotIndex) / 5);
            int tColumnIndex = (tSlotIndex) % 5;

            addSlotToContainer(new Slot(pTargetTE, tSlotIndex, 34 + 18 * tColumnIndex, 59 + 18 * tRowIndex));
        }

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, 164, 95) {
            @Override
            public boolean isItemValid (ItemStack pItemStack) {
                return false;
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS, 164, 59) {

            @Override
            public boolean isItemValid (ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemHammer);
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS, 164, 131) {

            @Override
            public boolean isItemValid (ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemTongs);
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS + TileEntityArmorsAnvil.MAX_COOLSLOTS, 185, 14) {

            @Override
            public boolean isItemValid(ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof IBluePrintContainerItem);
            }
        });


        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 28 + inventoryColumnIndex * 18, 180 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 28 + actionBarSlotIndex * 18, 238));
        }
    }

    @Override
    public ItemStack slotClick (int id, int posX, int posY, EntityPlayer player) {
        return super.slotClick(id, posX, posY, player);
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
}
