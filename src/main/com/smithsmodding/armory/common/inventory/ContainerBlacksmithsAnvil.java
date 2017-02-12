package com.smithsmodding.armory.common.inventory;

import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.item.ItemHammer;
import com.smithsmodding.armory.common.item.ItemTongs;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Author Marc (Created on: 16.06.2016)
 */
public class ContainerBlacksmithsAnvil extends ContainerSmithsCore {
    public ContainerBlacksmithsAnvil(@NotNull EntityPlayer player, TileEntityBlackSmithsAnvil te) {
        super(References.InternalNames.TileEntities.ArmorsAnvil, te, te, player);

        for (int tSlotIndex = 0; tSlotIndex < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex++) {
            int tRowIndex = ((tSlotIndex) / 5);
            int tColumnIndex = (tSlotIndex) % 5;

            addSlotToContainer(new SlotSmithsCore(te, tSlotIndex, 18 + 18 * tColumnIndex, 59 + 18 * tRowIndex));
        }

        addSlotToContainer(new SlotSmithsCore(te, ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, 148, 95) {
            @Override
            public boolean isItemValid(ItemStack pItemStack) {
                return false;
            }
        });

        addSlotToContainer(new SlotSmithsCore(te, ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS, 185, 110) {

            @Override
            public boolean isItemValid(@NotNull ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemHammer);
            }
        });

        addSlotToContainer(new SlotSmithsCore(te, ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS, 185, 132) {

            @Override
            public boolean isItemValid(@NotNull ItemStack pItemStack) {
                return (pItemStack.getItem() instanceof ItemTongs);
            }
        });


        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(player.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 28 + inventoryColumnIndex * 18, 180 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(player.inventory, actionBarSlotIndex, 28 + actionBarSlotIndex * 18, 239));
        }
    }
}