/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.GUI.Components.Tab;

import com.SmithsModding.Armory.Client.GUI.Components.Ledgers.ILedgerHost;

public interface ITabbedHost extends ILedgerHost {

    TabManager getTabManager();

    void onActiveTabChanged();
}
