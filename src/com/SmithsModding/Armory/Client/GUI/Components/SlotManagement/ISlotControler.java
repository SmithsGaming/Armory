/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.GUI.Components.SlotManagement;

import net.minecraft.inventory.Slot;

public interface ISlotControler {
    boolean ShouldSlotBeVisible(Slot pSlot);

    void ModifyVisibleSlot(Slot pSlot);

}
