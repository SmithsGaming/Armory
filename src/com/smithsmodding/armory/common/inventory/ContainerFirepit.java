package com.smithsmodding.armory.common.inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */


import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.inventory.slots.SlotFuelInput;
import com.smithsmodding.armory.common.inventory.slots.SlotHeatable;
import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerFirepit extends ContainerSmithsCore {
    private TileEntityFirePit tileEntityFirePit;

    public ContainerFirepit (EntityPlayer playerMP, TileEntityFirePit pTEFirePit) {
        super(References.InternalNames.TileEntities.FirePitContainer, pTEFirePit, pTEFirePit, playerMP);

        this.tileEntityFirePit = pTEFirePit;

        generateStandardInventory();
    }

    public TileEntityFirePit GetTileEntity () {
        return tileEntityFirePit;
    }

    @Override
    public boolean canInteractWith (EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onTabChanged (String newActiveTabID) {
        super.onTabChanged(newActiveTabID);

        inventorySlots.clear();
        inventoryItemStacks.clear();

        if (newActiveTabID.endsWith("Inventory"))
        {
            generateStandardInventory();
        }
        else
        {
            generateMoltenInventory();
        }
    }

    private void generateStandardInventory()
    {
        this.addSlotToContainer(new SlotHeatable(tileEntityFirePit, 0, 23, 51, 0));
        this.addSlotToContainer(new SlotHeatable(tileEntityFirePit, 1, 51, 37, 1));
        this.addSlotToContainer(new SlotHeatable(tileEntityFirePit, 2, 80, 33, 2));
        this.addSlotToContainer(new SlotHeatable(tileEntityFirePit, 3, 109, 37, 3));
        this.addSlotToContainer(new SlotHeatable(tileEntityFirePit, 4, 138, 51, 4));

        for (int fuelStackIndex = 0; fuelStackIndex < TileEntityFirePit.FUELSTACK_AMOUNT; fuelStackIndex++) {
            this.addSlotToContainer(new SlotFuelInput(tileEntityFirePit, fuelStackIndex + 5, 44 + fuelStackIndex * 18, 87));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(getPlayerInventory(), inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 112 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(getPlayerInventory(), actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 171));
        }
    }

    private void generateMoltenInventory()
    {
        for (int infusionStackIndex = 0; infusionStackIndex < TileEntityFirePit.INFUSIONSTACK_AMOUNT; infusionStackIndex++) {
            this.addSlotToContainer(new SlotSmithsCore(tileEntityFirePit, infusionStackIndex + TileEntityFirePit.INGOTSTACKS_AMOUNT + TileEntityFirePit.FUELSTACK_AMOUNT, 59 + infusionStackIndex * 21, 63));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(getPlayerInventory(), inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 139 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(getPlayerInventory(), actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 198));
        }
    }
}
