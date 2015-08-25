/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentButton;
import com.Orion.Armory.Client.GUI.Components.ComponentLabel;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Client.GUI.Components.Tab.ITabbedHost;
import com.Orion.Armory.Client.GUI.Components.Tab.Tab;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.Colors;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TempTab extends Tab {

    public TempTab() {
        super("TestTab", new ItemStack(GeneralRegistry.Items.iHammer, 1), String.valueOf((new Random()).nextInt(1000)));
    }

    @Override
    public void initializeTab(ITabbedHost pHost) {
        iComponents = new StandardComponentManager(pHost);
        iLedgers = new LedgerManager(pHost);
        iTabManager = pHost.getTabManager();

        getComponents().addComponent(new ComponentBorder(pHost, "TestBorder", pHost.getXOrigin(), pHost.getYOrigin(), 256, 256, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentLabel(pHost, "TestLabel", 20, 20, "Hello: " + (new Random()).nextInt(1000)));
        getComponents().addComponent(new ComponentButton(pHost, "TestButton", 20, 30, 250, 34, "TextBox", "", Colors.General.ORANGE));
    }
}
