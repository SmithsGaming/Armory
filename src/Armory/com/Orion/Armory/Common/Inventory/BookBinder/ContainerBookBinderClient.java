/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.BookBinder;

import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Util.References;

public class ContainerBookBinderClient extends ContainerArmory {

    public ContainerBookBinderClient(TileEntityBookBinder pTargetTE, int modSlots) {
        super(pTargetTE, modSlots);
    }

    private void initializeBaseSlots() {

    }


    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        if (pComponent.getInternalName().equals(References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKCONTENTS + ".Scrollbar") && References.InternalNames.InputHandlers.Components.SCROLL.equals(pComponentID)) {

        }

    }

    @Override
    public void OnContainerChanged(String pComponent) {
        if (pComponent.equals(References.InternalNames.InputHandlers.Components.SLOTFILLED)) {
            return;
        } else if (pComponent.equals(References.InternalNames.InputHandlers.Components.SLOTEMPTIED)) {
            return;
        } else if (pComponent.equals(References.InternalNames.InputHandlers.Components.SLOTCHANGED)) {
            return;
        }

        super.OnContainerChanged(pComponent);
    }


}
