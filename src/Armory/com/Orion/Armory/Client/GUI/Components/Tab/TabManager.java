/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.Tab;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Client.GUI.Components.SlotManagement.SlotManager;
import com.Orion.Armory.Client.GUI.Components.ToolTips.ToolTipRenderer;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TabManager implements ITabbedHost {
    public static final int TABSIZEX = 24;
    public static final int TABSIZEY = 24;
    Tab iActiveTab;
    private ITabbedHost iHost;
    private ArrayList<Tab> iTabs;

    public TabManager(ITabbedHost pHost) {
        iTabs = new ArrayList<Tab>();
        iHost = pHost;
    }

    public void registerTab(Tab pNewTab) {
        pNewTab.initializeTab(this);

        iTabs.add(pNewTab);

        if (iTabs.size() == 1) {
            iActiveTab = iTabs.get(0);
            iHost.onActiveTabChanged();
        }
    }

    public void clearTabs() {
        iTabs = new ArrayList<Tab>();
    }

    public ArrayList<Tab> getAllRegisteredTabs() {
        return iTabs;
    }

    public Tab getActiveTab() {
        return iActiveTab;
    }

    public void setActiveTab(Tab iNewActiveTab) {
        if (!iTabs.contains(iNewActiveTab))
            return;

        iActiveTab = iNewActiveTab;
        iHost.onActiveTabChanged();
    }

    public boolean handleMouseClick(int pMouseX, int pMouseY, int pMouseButton) {
        if (iTabs.size() == 1)
            return iActiveTab.getComponents().handleMouseClicked(pMouseX, pMouseY, pMouseButton);

        if (pMouseY < TABSIZEY && pMouseY >= 0) {
            if (pMouseX > 0 && pMouseX < iTabs.size() * TABSIZEX) {
                int tTabIndex = (pMouseX - 4) / TABSIZEX;

                if (tTabIndex >= iTabs.size() || tTabIndex < 0)
                    return false;

                if (pMouseButton == 0) {
                    iActiveTab = iTabs.get(tTabIndex);
                    iHost.onActiveTabChanged();
                }

                return true;
            }
        }

        return iActiveTab.getComponents().handleMouseClicked(pMouseX, pMouseY - TABSIZEY, pMouseButton);
    }

    public void renderTabsBackground(float pPartialTickTime, int pMouseX, int pMouseY) {
        if (iTabs.size() == 1) {
            if (iActiveTab == null)
                iActiveTab = iTabs.get(0);

            iActiveTab.getLedgerManager().drawLedgers();
            iActiveTab.getComponents().drawComponents();
        } else {
            int tRenderedTabs = 0;

            iActiveTab.getLedgerManager().drawLedgers();

            for (Tab tTab : iTabs) {
                if (iActiveTab != tTab) {
                    GL11.glPushMatrix();

                    GL11.glTranslatef(4 + tRenderedTabs * TABSIZEX, 0, 0);
                    GL11.glRotatef(180, 0F, 0F, 1F);

                    GL11.glTranslatef(-TABSIZEX, -TABSIZEY - 2, 0);

                    ComponentBorder iTabBorder = new ComponentBorder(this, References.InternalNames.GUIComponents.TAB + tRenderedTabs, 0, 0, TABSIZEX, TABSIZEY, Colors.General.GRAY, ComponentBorder.CornerTypes.Outwarts);
                    iTabBorder.drawBackGround(0, 0);
                    Color.resetGLColor();

                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(4 + tRenderedTabs * TABSIZEX, 2, 0);
                    GuiHelper.drawItemStack(tTab.getIconStack(), 4, 4);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glPopMatrix();

                }

                tRenderedTabs++;
            }

            GL11.glPushMatrix();
            GL11.glTranslatef(0F, TABSIZEY - 3, 0F);
            iActiveTab.getComponents().drawComponents();
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(4 + iTabs.indexOf(iActiveTab) * TABSIZEX, 0, 0);
            GL11.glRotatef(180, 0F, 0F, 1F);
            GL11.glTranslatef(-TABSIZEX, -TABSIZEY, 0);

            ComponentBorder iTabBorder = new ComponentBorder(this, References.InternalNames.GUIComponents.TAB + iTabs.indexOf(iActiveTab), 0, 0, TABSIZEX, TABSIZEY, Colors.DEFAULT, ComponentBorder.CornerTypes.Outwarts);
            iTabBorder.drawBackGround(0, 0);

            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(4 + iTabs.indexOf(iActiveTab) * TABSIZEX, 0, 0);
            GuiHelper.drawItemStack(iActiveTab.getIconStack(), 4, 4);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glPopMatrix();
        }


    }

    public void renderTabsForeground(int pMouseX, int pMouseY) {
        if (iTabs.size() < 2)
            return;

        for (Tab tTab : iTabs) {
            ToolTipRenderer.renderToolTipAt(tTab, pMouseX - iHost.getXOrigin(), pMouseY - iHost.getYOrigin(), pMouseX, pMouseY);
        }
    }

    @Override
    public ContainerArmory getContainer() {
        return iHost.getContainer();
    }

    @Override
    public Object getComponentRelatedObject(String pComponentID) {
        return iHost.getComponentRelatedObject(pComponentID);
    }

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        return iHost.getProgressBarValue(pProgressBarID);
    }

    @Override
    public ItemStack getItemStackInSlot(int pSlotIndex) {
        return iHost.getItemStackInSlot(pSlotIndex);
    }

    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        iHost.updateComponentResult(pComponent, pComponentID, pNewValue);
    }

    @Override
    public void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer) {
        iHost.drawHoveringText(pToolTipLines, pX, pY, pFontRenderer);
    }

    @Override
    public SlotManager getSlotManager() {
        return iHost.getSlotManager();
    }

    @Override
    public LedgerManager getLedgerManager() {
        if (iActiveTab == null)
            return null;

        return iActiveTab.getLedgerManager();
    }

    @Override
    public int getXOrigin() {
        return iHost.getXOrigin();
    }

    @Override
    public int getYOrigin() {
        if (iTabs.size() == 1)
            return iHost.getYOrigin();

        return iHost.getYOrigin() + TABSIZEY - 3;
    }

    @Override
    public int getXSize() {
        if (iActiveTab == null)
            return iHost.getXSize();

        return iActiveTab.getXSize();
    }

    @Override
    public int getYSize() {
        if (iActiveTab == null)
            return iHost.getYSize();

        if (iTabs.size() == 1)
            return iActiveTab.getYSize();

        return iActiveTab.getYSize() + TABSIZEY;
    }

    @Override
    public TabManager getTabManager() {
        return this;
    }

    @Override
    public void onActiveTabChanged() {
        iHost.onActiveTabChanged();
    }
}
