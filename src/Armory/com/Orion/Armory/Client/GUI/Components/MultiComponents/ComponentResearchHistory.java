/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.API.Knowledge.IResearchTreeComponent;
import com.Orion.Armory.Client.GUI.Components.ComponentLabel;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.SlotManagement.SlotManager;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Core.Rectangle;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ComponentResearchHistory extends AbstractGUIMultiComponent implements IComponentHost {

    ComponentScrollbar iScrollBar;
    int iCurrentScrolledValue = 0;

    private int iDisplayItemHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 4;
    private int iDisplayCount;

    public ComponentResearchHistory(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pWidth, int pHeigth) {
        super(pHost, pInternalName, pLeft, pTop, pWidth, pHeigth);

        iScrollBar = new ComponentScrollbar(this, pInternalName + ".ScrollBar", pWidth - 7, 0, pHeigth, 0, 100, new Rectangle(0, 0, 0, pWidth, pHeigth));

        iDisplayCount = iHeight / iDisplayItemHeight;
    }

    @Override
    public void draw(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0F);


        GuiHelper.drawColoredRect(new Rectangle(0, 0, 0, getXSize(), getYSize()), Colors.General.GRAY);
        drawComponents();

        GL11.glPopMatrix();
    }

    @Override
    public void drawComponents() {
        iScrollBar.draw(getXOrigin(), getYOrigin());
        GuiHelper.enableScissor(new Rectangle(getXOrigin(), 0, getYOrigin() + iHeight, iWidth - 7, iHeight));

        super.drawComponents();

        GuiHelper.disableScissor();
    }

    @Override
    public void onUpdate() {
        iSubComponents.getComponents().clear();

        ArrayList<IResearchTreeComponent> tComponents = (ArrayList<IResearchTreeComponent>) getComponentRelatedObject(References.InternalNames.GUIComponents.BookBinder.TabResearch.RESEARCHHISTORY);

        if (tComponents.size() < iDisplayCount) {
            int tIndex = 0;

            for (IResearchTreeComponent tComponent : tComponents) {
                if (tComponent != null) {
                    iSubComponents.addComponent(new ComponentLabel(this, getInternalName() + ".Label." + (tIndex), 0, iDisplayItemHeight * (tIndex), tComponent.getDisplayString(), true));
                    tIndex++;
                }
            }
        } else {
            int tEndVisibleIndex = (int) (iDisplayCount + (((tComponents.size() - iDisplayCount) / (float) 100) * iCurrentScrolledValue));
            int tStartVisibleIndex = tEndVisibleIndex - iDisplayCount;

            if (tStartVisibleIndex < 0) {
                tEndVisibleIndex -= Math.abs(tStartVisibleIndex);
                tStartVisibleIndex = 0;

                if (tEndVisibleIndex == 0) {
                    return;
                }
            }

            for (int tComponentIndex = tStartVisibleIndex; tComponentIndex <= tEndVisibleIndex; tComponentIndex++) {
                if (tComponentIndex < tComponents.size() && tComponentIndex >= 0) {
                    iSubComponents.addComponent(new ComponentLabel(this, getInternalName() + ".Label." + (tComponentIndex - tStartVisibleIndex), 0, iDisplayItemHeight * (tComponentIndex - tStartVisibleIndex), tComponents.get(tComponentIndex).getDisplayString(), true));
                }
            }

            iScrollBar.setScrollValue(100);
        }

        iSubComponents.onUpdate();
        iScrollBar.onUpdate();
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        if (iScrollBar.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton))
            return true;

        return iSubComponents.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton);
    }

    @Override
    public ContainerArmory getContainer() {
        return iHost.getContainer();
    }

    @Override
    public Object getComponentRelatedObject(String pComponentID) {
        if (pComponentID.contains(getInternalName() + ".ScrollBar"))
            return true;

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
        if (pComponent.getInternalName().equals(getInternalName() + ".ScrollBar")) {
            iCurrentScrolledValue = Integer.parseInt(pNewValue);
            return;
        }

        iHost.updateComponentResult(pComponent, pComponentID, pNewValue);
    }

    @Override
    public int getXOrigin() {
        return iHost.getXOrigin() + iLeft;
    }

    @Override
    public int getYOrigin() {
        return iHost.getYOrigin() + iTop;
    }

    @Override
    public int getXSize() {
        return iWidth;
    }

    @Override
    public int getYSize() {
        return iHeight;
    }


    @Override
    public void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer) {
        iHost.drawHoveringText(pToolTipLines, pX, pY, pFontRenderer);
    }

    @Override
    public SlotManager getSlotManager() {
        return iHost.getSlotManager();
    }
}
