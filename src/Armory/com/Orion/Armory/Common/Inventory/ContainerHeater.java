package com.Orion.Armory.Common.Inventory;

import com.Orion.Armory.Common.Inventory.Slots.SlotFan;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * Created by Orion
 * Created on 23.04.2015
 * 13:02
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ContainerHeater extends ContainerArmory
{
    private TileEntityHeater iTEHeater;

    public ContainerHeater(InventoryPlayer pPlayerInventory, TileEntityHeater pTEHeater)
    {
        this.iTEHeater = pTEHeater;

        this.addSlotToContainer(new SlotFan(pTEHeater, 0, 80, 24));

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 56 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 114));
        }
    }

    public TileEntityHeater getTileEntity()
    {
        return iTEHeater;
    }
}
