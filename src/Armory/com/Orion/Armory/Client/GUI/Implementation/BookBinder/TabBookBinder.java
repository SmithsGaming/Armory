/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Client.GUI.Components.Tab.ITabbedHost;
import com.Orion.Armory.Client.GUI.Components.Tab.Tab;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

public class TabBookBinder extends Tab {

    public TabBookBinder(String pInternalID, ItemStack pIconStack, String pToolTipText) {
        super(pInternalID, pIconStack, pToolTipText);

        iXSize = 250;
        iYSize = 118;
    }

    @Override
    public void initializeTab(ITabbedHost pHost) {
        iComponents = new StandardComponentManager(pHost);
        iLedgers = new LedgerManager(pHost);
        iTabManager = pHost.getTabManager();

        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BACKGROUND, 0, 0, iXSize, iYSize, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BORDERBOOKSLOT, 7, 7, 24, 24, Colors.General.BROWN, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BORDERBOOKSLOT, 65, 7, 178, 104, Colors.General.PAPERYELLOW, ComponentBorder.CornerTypes.Inwarts));
    }
}
