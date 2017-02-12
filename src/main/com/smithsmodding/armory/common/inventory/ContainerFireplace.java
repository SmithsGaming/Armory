package com.smithsmodding.armory.common.inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */


import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.inventory.slots.SlotFuelInput;
import com.smithsmodding.armory.common.inventory.slots.SlotInventoryCheck;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ContainerFireplace extends ContainerSmithsCore {
    private TileEntityFireplace tileEntityFireplace;

    public ContainerFireplace(@NotNull EntityPlayer playerMP, TileEntityFireplace tileEntityFireplace) {
        super(References.InternalNames.TileEntities.FireplaceContainer, tileEntityFireplace, tileEntityFireplace, playerMP);

        this.tileEntityFireplace = tileEntityFireplace;

        generateStandardInventory();
    }

    public TileEntityFireplace GetTileEntity() {
        return tileEntityFireplace;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onTabChanged(@NotNull String newActiveTabID) {
        super.onTabChanged(newActiveTabID);

        inventorySlots.clear();
        inventoryItemStacks.clear();

        if (newActiveTabID.endsWith("Inventory")) {
            generateStandardInventory();
        } else {
            generateFoodInventory();
        }
    }

    private void generateStandardInventory() {
        this.addSlotToContainer(new SlotInventoryCheck(tileEntityFireplace, 0, 51, 37));
        this.addSlotToContainer(new SlotInventoryCheck(tileEntityFireplace, 1, 80, 33));
        this.addSlotToContainer(new SlotInventoryCheck(tileEntityFireplace, 2, 109, 37));

        for (int fuelStackIndex = 0; fuelStackIndex < TileEntityFireplace.FUELSLOTCOUNT; fuelStackIndex++) {
            this.addSlotToContainer(new SlotFuelInput(tileEntityFireplace, fuelStackIndex + TileEntityFireplace.INGOTSLOTCOUNT + TileEntityFireplace.FOODCOOKINPUTCOUNT + TileEntityFireplace.FOODCOOKOUTPUTCOUNT, 80 + fuelStackIndex * 18, 83));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(getPlayerInventory(), inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 108 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(getPlayerInventory(), actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 167));
        }
    }

    private void generateFoodInventory() {
        for (int foodInputStackIndex = 0; foodInputStackIndex < TileEntityFireplace.FOODCOOKINPUTCOUNT; foodInputStackIndex++) {
            this.addSlotToContainer(new SlotInventoryCheck(tileEntityFireplace, foodInputStackIndex + TileEntityFireplace.INGOTSLOTCOUNT, 44 + foodInputStackIndex * 21, 43, 64));
        }

        for (int infusionStackIndex = 0; infusionStackIndex < TileEntityFireplace.FOODCOOKOUTPUTCOUNT; infusionStackIndex++) {
            this.addSlotToContainer(new SlotInventoryCheck(tileEntityFireplace, infusionStackIndex + TileEntityFireplace.INGOTSLOTCOUNT + TileEntityFireplace.FOODCOOKINPUTCOUNT, 98 + infusionStackIndex * 21, 63));
        }

        for (int fuelStackIndex = 0; fuelStackIndex < TileEntityFireplace.FUELSLOTCOUNT; fuelStackIndex++) {
            this.addSlotToContainer(new SlotFuelInput(tileEntityFireplace, fuelStackIndex + TileEntityFireplace.INGOTSLOTCOUNT + TileEntityFireplace.FOODCOOKINPUTCOUNT + TileEntityFireplace.FOODCOOKOUTPUTCOUNT, 44 + fuelStackIndex * 18, 83));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(getPlayerInventory(), inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 108 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(getPlayerInventory(), actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 167));
        }
    }
}
