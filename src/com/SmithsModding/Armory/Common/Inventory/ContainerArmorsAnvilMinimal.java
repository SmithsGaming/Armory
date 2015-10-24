package com.SmithsModding.Armory.Common.Inventory;

import com.SmithsModding.Armory.API.Knowledge.IBluePrintContainerItem;
import com.SmithsModding.Armory.Common.Item.ItemHammer;
import com.SmithsModding.Armory.Common.Item.ItemTongs;
import com.SmithsModding.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.SmithsModding.Armory.Common.TileEntity.Core.ICustomInputHandler;
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
    public ContainerArmorsAnvilMinimal(InventoryPlayer pPlayerInventory, TileEntityArmorsAnvil pTargetTE) {
        super(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS);

        for (int tSlotIndex = 0; tSlotIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex++) {
            int tRowIndex = ((tSlotIndex) / 5);
            int tColumnIndex = (tSlotIndex) % 5;

            addSlotToContainer(new Slot(pTargetTE, tSlotIndex, 18 + 18 * tColumnIndex, 59 + 18 * tRowIndex));
        }

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, 148, 95) {
            @Override
            public boolean isItemValid(ItemStack pItemStack) {
                return false;
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS, 185, 110) {

            @Override
            public boolean isItemValid(ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemHammer);
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS, 185, 132) {

            @Override
            public boolean isItemValid(ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemTongs);
            }
        });

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS + TileEntityArmorsAnvil.MAX_COOLSLOTS, 185, 58) {

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
    public ItemStack slotClick(int id, int posX, int posY, EntityPlayer player) {
        return super.slotClick(id, posX, posY, player);
    }


    @Override
    public void HandleCustomInput(String pInputID, String pInput) {
        ((ICustomInputHandler) iTargetTE).HandleCustomInput(pInputID, pInput);
    }
}
