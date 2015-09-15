/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
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

        iXSize = 175;
        iYSize = 155;
    }

    @Override
    public void initializeTab(ITabbedHost pHost) {
        iComponents = new StandardComponentManager(pHost);
        iLedgers = new LedgerManager(pHost);
        iTabManager = pHost.getTabManager();

        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BACKGROUND, 0, 0, 175, 68, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BLUEPRINTBACKGROUND, 42, 22, 24, 24, Colors.General.ELECTRICBLUE, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BLUEPRINTSLOT, 1, 18, 18, 45, 25, Textures.Gui.Basic.Slots.DEFAULT, Colors.General.ELECTRICBLUE, Textures.Gui.BookBinder.TabBookBinding.BLUEPRINTSLOT));
        getComponents().addComponent(new ComponentProgressBar(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.PROGRESSARROW, 72, 18, Textures.Gui.BookBinder.TabBookBinding.BINDINGPROGRESSBACKGROUND, Textures.Gui.BookBinder.TabBookBinding.BINDINGPROGRESSFOREGROUND, Colors.DEFAULT, Colors.DEFAULT));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BORDERBOOKSLOT, 109, 22, 24, 24, Colors.General.BROWN, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.BOOKSLOT, 0, 18, 18, 112, 25, Textures.Gui.Basic.Slots.DEFAULT, Colors.General.BROWN, Textures.Gui.Anvil.BOOKSLOT));
        getComponents().addComponent(new ComponentPlayerInventory(pHost, References.InternalNames.GUIComponents.BookBinder.TabBook.PLAYERINVENTORY, 0, 63, 1, ComponentBorder.CornerTypes.StraightVertical));

    }
}
