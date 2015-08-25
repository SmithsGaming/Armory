/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.Tab;

import com.Orion.Armory.Client.GUI.Components.Ledgers.ILedgerHost;

public interface ITabbedHost extends ILedgerHost {

    TabManager getTabManager();

    void onActiveTabChanged();
}
