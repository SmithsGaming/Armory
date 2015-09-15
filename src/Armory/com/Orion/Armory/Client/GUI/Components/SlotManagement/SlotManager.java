/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.SlotManagement;

import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;

public class SlotManager {
    IComponentHost iHost;

    ArrayList<ISlotControler> iControllers = new ArrayList<ISlotControler>();

    public SlotManager(IComponentHost pHost) {
        iHost = pHost;
        iControllers = new ArrayList<ISlotControler>();
    }

    public ArrayList<ISlotControler> getControlers() {
        return iControllers;
    }

    public void registerSlotController(ISlotControler pControler) {
        if (!iControllers.contains(pControler))
            iControllers.add(pControler);
    }

    public boolean ShouldSlotBeVisible(Slot pSlot) {
        if (iControllers.size() == 0)
            return true;

        ISlotControler tActiveController = this.getControllerForSlot(pSlot);

        return tActiveController != null;

    }

    public void ModifyVisibleSlot(Slot pSlot) {
        if (iControllers.size() == 0)
            return;

        ISlotControler tActiveController = this.getControllerForSlot(pSlot);

        if (tActiveController == null)
            return;

        tActiveController.ModifyVisibleSlot(pSlot);
    }

    public ISlotControler getControllerForSlot(Slot pSlot) {
        for (ISlotControler tController : iControllers) {
            if (tController.ShouldSlotBeVisible(pSlot))
                return tController;
        }

        return null;
    }

}
