/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Inventory;

import com.SmithsModding.Armory.API.Knowledge.IBluePrintContainerItem;
import com.SmithsModding.Armory.API.Knowledge.IBluePrintItem;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Common.TileEntity.Core.ICustomInputHandler;
import com.SmithsModding.Armory.Common.TileEntity.TileEntityBookBinder;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerBookBinder extends ContainerArmory {
    InventoryPlayer iInventoryPlayer;

    public ContainerBookBinder(InventoryPlayer pPlayerInventory, TileEntityBookBinder pTargetTE, int modSlots) {
        super(pTargetTE, modSlots);

        iInventoryPlayer = pPlayerInventory;
        createBookBindingSlots();
    }


    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        super.updateComponentResult(pComponent, pComponentID, pNewValue);
        HandleCustomInput(pComponentID, pNewValue);
    }

    @Override
    public void HandleCustomInput(String pInputID, String pInput) {
        ((ICustomInputHandler) iTargetTE).HandleCustomInput(pInputID, pInput);

        if (pInputID.equals(References.InternalNames.InputHandlers.Components.TABCHANGED)) {
            this.inventorySlots.clear();
            this.inventoryItemStacks.clear();

            switch (Integer.parseInt(pInput)) {
                case 0:
                    createBookBindingSlots();
                    return;
                case 1:
                    createResearchSlots();
                    return;
            }
        }
    }

    private void createBookBindingSlots() {
        addSlotToContainer(new Slot((IInventory) iTargetTE, 0, 113, 47) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return pStack.getItem() instanceof IBluePrintContainerItem;
            }
        });

        addSlotToContainer(new Slot((IInventory) iTargetTE, 1, 46, 47) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return pStack.getItem() instanceof IBluePrintItem;
            }
        });

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(iInventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 92 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(iInventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 150));
        }
    }

    private void createResearchSlots() {
        addSlotToContainer(new Slot((IInventory) iTargetTE, 0, 49, 52));

        addSlotToContainer(new Slot((IInventory) iTargetTE, 1, 26, 110) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                if (pStack.getItem().getUnlocalizedName().equals("item.paper")) return true;

                for (int tOreID : OreDictionary.getOreIDs(pStack)) {
                    GeneralRegistry.iLogger.info(OreDictionary.getOreName(tOreID));
                    if (OreDictionary.getOreName(tOreID).equals("paper")) return true;
                }

                return false;
            }
        });

        addSlotToContainer(new Slot((IInventory) iTargetTE, 2, 74, 110) {
            @Override
            public boolean isItemValid(ItemStack pStack) {
                return false;
            }
        });

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(iInventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 37 + inventoryColumnIndex * 18, 154 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(iInventoryPlayer, actionBarSlotIndex, 37 + actionBarSlotIndex * 18, 212));
        }
    }

}
