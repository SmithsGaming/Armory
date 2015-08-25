/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.ArmoryBaseTabbedGui;
import net.minecraft.inventory.Container;

public class GuiBookBinder extends ArmoryBaseTabbedGui {

    public GuiBookBinder(Container pTargetedContainer) {
        super(pTargetedContainer);


    }


    @Override
    protected void initializeTabManager() {
        getTabManager().clearTabs();

        getTabManager().registerTab(new TempTab());
        getTabManager().registerTab(new TempTab());
    }


}


