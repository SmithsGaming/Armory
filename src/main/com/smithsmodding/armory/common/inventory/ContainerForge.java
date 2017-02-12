package com.smithsmodding.armory.common.inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */


import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.inventory.slots.SlotFuelInput;
import com.smithsmodding.armory.common.inventory.slots.SlotHeatable;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ContainerForge extends ContainerSmithsCore {
    private TileEntityForge tileEntityForge;

    public ContainerForge(@Nonnull EntityPlayer playerMP, TileEntityForge tileEntityForge) {
        super(References.InternalNames.TileEntities.ForgeContainer, tileEntityForge, tileEntityForge, playerMP);

        this.tileEntityForge = tileEntityForge;

        generateStandardInventory();
    }

    public TileEntityForge getTileEntity() {
        return tileEntityForge;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onTabChanged(@Nonnull String newActiveTabID) {
        super.onTabChanged(newActiveTabID);

        inventorySlots.clear();
        inventoryItemStacks.clear();

        if (newActiveTabID.endsWith("Inventory")) {
            generateStandardInventory();
        } else {
            generateMoltenInventory();
        }
    }

    private void generateStandardInventory() {
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 0, 23, 51, 0));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 1, 51, 37, 1));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 2, 80, 33, 2));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 3, 109, 37, 3));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 4, 138, 51, 4));

        for (int fuelStackIndex = 0; fuelStackIndex < TileEntityForge.FUELSTACK_AMOUNT; fuelStackIndex++) {
            this.addSlotToContainer(new SlotFuelInput(tileEntityForge, fuelStackIndex + 5, 44 + fuelStackIndex * 18, 87));
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

    private void generateMoltenInventory() {
        for (int infusionStackIndex = 0; infusionStackIndex < TileEntityForge.INFUSIONSTACK_AMOUNT; infusionStackIndex++) {
            this.addSlotToContainer(new SlotSmithsCore(tileEntityForge, infusionStackIndex + TileEntityForge.INGOTSTACKS_AMOUNT + TileEntityForge.FUELSTACK_AMOUNT, 59 + infusionStackIndex * 21, 63));
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
