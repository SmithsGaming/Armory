package com.Orion.Armory.Common.Inventory;

import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 12:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ContainerArmorsAnvilStandard extends ContainerArmory {
    public ContainerArmorsAnvilStandard(InventoryPlayer pPlayerInventory, TileEntityArmorsAnvil pTargetTE) {
        super(pTargetTE);

        for (int tSlotIndex = 0; tSlotIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex ++)
        {
            int tRowIndex = ((tSlotIndex) / 5);
            int tColumnIndex = (tSlotIndex) % 5;

            addSlotToContainer(new Slot(pTargetTE, tSlotIndex, 18 + 18 * tColumnIndex, 59 + 18 * tRowIndex));
        }

        addSlotToContainer(new Slot(pTargetTE, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, 148, 95) {
            @Override
            public boolean isItemValid(ItemStack pItemStack)
            {
                return false;
            }
        });

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 53 + inventoryColumnIndex * 18, 180 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 53 + actionBarSlotIndex * 18, 238));
        }
    }
}
