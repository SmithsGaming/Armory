/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.GUI.Implementation.BookBinder;

import com.SmithsModding.Armory.Client.GUI.Components.ComponentBorder;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentButton;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentImage;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentSlot;
import com.SmithsModding.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.SmithsModding.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.SmithsModding.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.SmithsModding.Armory.Client.GUI.Components.MultiComponents.ComponentResearchHistory;
import com.SmithsModding.Armory.Client.GUI.Components.Tab.ITabbedHost;
import com.SmithsModding.Armory.Client.GUI.Components.Tab.Tab;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.Client.GUI.GuiHelper;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.References;


public class TabResearchStation extends Tab {

    public TabResearchStation(String pInternalID, String pToolTipText) {
        super(pInternalID, null, pToolTipText);

        iXSize = 235;
        iYSize = 215;
    }

    @Override
    public void renderTabIcon() {
        GuiHelper.drawResource(Textures.Gui.BookBinder.TabResearchStation.MAGNIFIER, 4, 4);
    }

    @Override
    public void initializeTab(ITabbedHost pHost) {
        iComponents = new StandardComponentManager(pHost);
        iLedgers = new LedgerManager(pHost);
        iTabManager = pHost.getTabManager();

        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.BACKGROUND, 0, 0, 235, 128, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        getComponents().addComponent(new ComponentBorder(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.RESEARCHBACKGROUND, 10, 10, 92, 108, Colors.General.PAPERYELLOW, ComponentBorder.CornerTypes.Inwarts));

        getComponents().addComponent(new ComponentButton(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.BUTTONANALYZESTACK, 77, 38, 18, 18, References.InternalNames.InputHandlers.BookBinder.ANALYZE, Textures.Gui.BookBinder.TabResearchStation.MAGNIFIER));
        getComponents().addComponent(new ComponentButton(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.BUTTONAPPLYHEAT, 17, 38, 18, 18, References.InternalNames.InputHandlers.BookBinder.HEAT, Textures.Gui.BookBinder.TabResearchStation.FLAMEFULL));
        getComponents().addComponent(new ComponentButton(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.BUTTONHITSTACK, 37, 58, 18, 18, References.InternalNames.InputHandlers.BookBinder.HAMMER, Textures.Gui.BookBinder.TabResearchStation.HAMMER));
        getComponents().addComponent(new ComponentButton(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.BUTTONCUTSTACK, 57, 58, 18, 18, References.InternalNames.InputHandlers.BookBinder.TONGS, Textures.Gui.BookBinder.TabResearchStation.TONGS));

        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.SLOTTARGETSTACK, 0, 18, 18, 48, 30, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));

        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.SLOTTARGETSTACK, 1, 18, 18, 25, 88, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT, Textures.Gui.BookBinder.TabResearchStation.BLUEPRINTSLOT));
        getComponents().addComponent(new ComponentImage(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.IMAGEARROW, 47, 89, Textures.Gui.Basic.Components.ARROWEMPTY));
        getComponents().addComponent(new ComponentSlot(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.SLOTTARGETSTACK, 0, 18, 18, 73, 88, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));

        getComponents().addComponent(new ComponentResearchHistory(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.RESEARCHHISTORY, 102, 10, 123, 108));

        getComponents().addComponent(new ComponentPlayerInventory(pHost, References.InternalNames.GUIComponents.BookBinder.TabResearch.PLAYERINVENTORY, (iXSize - ComponentPlayerInventory.WIDTH) / 2, iYSize - ComponentPlayerInventory.HEIGTH, 1, ComponentBorder.CornerTypes.Outwarts));

    }
}
