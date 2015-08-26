/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentButton;
import com.Orion.Armory.Client.GUI.Components.ComponentProgressBar;
import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.Orion.Armory.Client.GUI.Components.Tab.ITabbedHost;
import com.Orion.Armory.Client.GUI.Components.Tab.Tab;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

public class TabBookBinder extends Tab {

    public TabBookBinder(String pInternalID, ItemStack pIconStack, String pToolTipText) {
        super(pInternalID, pIconStack, pToolTipText);

        iXSize = 250;
        iYSize = 205;
    }

    @Override
    public void initializeTab(ITabbedHost pHost) {
        iComponents = new StandardComponentManager(pHost);
        iLedgers = new LedgerManager(pHost);
        iTabManager = pHost.getTabManager();

        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BACKGROUND, 0, 0, 250, 118, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BORDERBOOKSLOT, 7, 7, 24, 24, Colors.General.BROWN, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKSLOT, 0, 18, 18, 10, 10, Textures.Gui.Anvil.BOOKSLOT, Colors.General.BROWN));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BORDERBLUEPRINTS, 65, 7, 178, 104, Colors.General.PAPERYELLOW, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentProgressBar(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.PROGRESSARROW, 38, 12, Colors.DEFAULT, Colors.DEFAULT));

        getComponents().addComponent(new ComponentPlayerInventory(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.PLAYERINVENTORY, 37, 115, 1, ComponentBorder.CornerTypes.Outwarts));
        getComponents().addComponent(new ComponentButton(pHost, "TestButton", 68, 10, 25, 25, "TestButton", "Hello", Colors.DEFAULT));
        getComponents().addComponent(new ComponentButton(pHost, "TestButton", 68, 38, 25, 25, "TestButton", "Hello", Colors.DEFAULT));



    }
}
