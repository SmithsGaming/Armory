/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI;

import com.Orion.Armory.Client.GUI.Components.Tab.ITabbedHost;
import com.Orion.Armory.Client.GUI.Components.Tab.TabManager;
import net.minecraft.inventory.Container;

public abstract class ArmoryBaseTabbedGui extends ArmoryBaseGui implements ITabbedHost {

    private TabManager iTabManager = new TabManager(this);

    public ArmoryBaseTabbedGui(Container pTargetedContainer) {
        super(pTargetedContainer);
    }

    @Override
    public void initGui() {
        super.initGui();

        initializeTabManager();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float pFloat, int pMouseX, int pMouseY) {
        super.drawGuiContainerBackgroundLayer(pFloat, pMouseX, pMouseY);

        iTabManager.renderTabsBackground(pFloat, pMouseX, pMouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int pMouseX, int pMouseY) {
        super.drawGuiContainerForegroundLayer(pMouseX, pMouseY);

        iTabManager.renderTabsForeground(pMouseX, pMouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (iTabManager.handleMouseClick(mouseX - guiLeft, mouseY - guiTop, mouseButton))
            return;

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public TabManager getTabManager() {
        return iTabManager;
    }

    protected abstract void initializeTabManager();
}
