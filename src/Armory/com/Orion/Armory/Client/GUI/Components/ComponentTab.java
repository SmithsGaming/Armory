/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Tab.TabManager;
import com.Orion.Armory.Client.GUI.Components.ToolTips.IToolTip;
import com.Orion.Armory.Client.Handler.ArmoryClientTickHandler;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Core.Rectangle;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class ComponentTab implements IGUIComponent {

    TabManager iInternalTabManager;
    IComponentHost iHost;
    String iInternalName;

    int iLeft;
    int iTop;

    @Override
    public void onUpdate() {
        iInternalTabManager.getActiveTab().getComponents().onUpdate();
    }

    @Override
    public IComponentHost getComponentHost() {
        return iHost;
    }

    @Override
    public String getInternalName() {
        return iInternalName;
    }

    @Override
    public Rectangle getToolTipVisibileArea() {
        return new Rectangle(-1, -1, -1, 0, 0);
    }

    @Override
    public void setToolTipVisibleArea(Rectangle pNewArea) {

    }

    @Override
    public Rectangle getOccupiedArea() {
        return null;
    }

    @Override
    public ArrayList<IToolTip> getToolTipLines() {
        return new ArrayList<IToolTip>();
    }

    @Override
    public int getHeight() {
        if (iInternalTabManager.getActiveTab() == null)
            return TabManager.TABSIZEY;

        return iInternalTabManager.getActiveTab().getYSize() + TabManager.TABSIZEY;
    }

    @Override
    public int getWidth() {
        if (iInternalTabManager.getActiveTab() == null)
            return TabManager.TABSIZEX * iInternalTabManager.getAllRegisteredTabs().size();

        return iInternalTabManager.getXSize();
    }

    @Override
    public void draw(int pX, int pY) {
        drawBackGround(pX + iLeft, pY + iTop);
        drawForeGround(pX + iLeft, pY + iTop);
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        iInternalTabManager.renderTabsForeground(Mouse.getX() / GuiHelper.GUISCALE, Mouse.getY() / GuiHelper.GUISCALE);
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        iInternalTabManager.renderTabsBackground(ArmoryClientTickHandler.getPartialRenderTick(), Mouse.getX() / GuiHelper.GUISCALE, Mouse.getY() / GuiHelper.GUISCALE);
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        return (new Rectangle(iLeft, 0, iTop, getWidth(), getHeight()).contains(pTargetX, pTargetY));
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return iInternalTabManager.handleMouseClick(pMouseX - iLeft, pMouseY - iTop, pMouseButton);
    }

    @Override
    public boolean handleKeyTyped(char pKey, int pPara) {
        return iInternalTabManager.getActiveTab().getComponents().handleKeyTyped(pKey, pPara);
    }

    @Override
    public boolean requiresForcedInput() {
        return true;
    }
}
