/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.GUI.Components.MultiComponents;

import com.SmithsModding.Armory.Client.GUI.Components.ComponentSlot;
import com.SmithsModding.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.Armory.Client.GUI.Components.SlotManagement.ISlotControler;
import com.SmithsModding.Armory.Client.GUI.Components.SlotManagement.SlotManager;
import com.SmithsModding.Armory.Common.Inventory.ContainerArmory;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.Core.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ComponentScrollableIInventory extends AbstractGUIMultiComponent implements IComponentHost, ISlotControler {

    int iHorizontalStackCount;
    int iVerticalStackCount;

    int iStartStackIndex;
    int iEndStackIndex;

    int iLastActiveStackIndex;
    int iLastRenderedStackIndex;

    private ComponentScrollbar iScrollbar;
    private boolean iActiveState;

    public ComponentScrollableIInventory(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pStartStackIndex, int pEndStackIndex, int pHorizontalStackCount, int pVerticalStackCount) {
        super(pHost, pInternalName, pLeft, pTop, pHorizontalStackCount * 18 + 7, pVerticalStackCount * 18);

        iHorizontalStackCount = pHorizontalStackCount;
        iVerticalStackCount = pVerticalStackCount;

        iStartStackIndex = pStartStackIndex;
        iEndStackIndex = Integer.parseInt(String.valueOf(iHost.getComponentRelatedObject(this.getInternalName() + ".StackCount")));

        if (iEndStackIndex < iStartStackIndex)
            iEndStackIndex = pEndStackIndex;

        initializeSlots();

        setActiveState(true);
    }

    @Override
    public void onUpdate() {
        int tEnd = iEndStackIndex;

        iEndStackIndex = Integer.parseInt(String.valueOf(iHost.getComponentRelatedObject(this.getInternalName() + ".StackCount")));

        if (iEndStackIndex < iStartStackIndex)
            iEndStackIndex = tEnd;

        iLastActiveStackIndex = (8 - (iEndStackIndex - iStartStackIndex) % 8) + (iEndStackIndex - iStartStackIndex);

        if (iLastActiveStackIndex < iHorizontalStackCount * iVerticalStackCount)
            iLastActiveStackIndex = iHorizontalStackCount * iVerticalStackCount;

        iScrollbar.calculateRange(0, iLastActiveStackIndex);
    }

    private int initializeSlots() {
        getComponents().clear();

        iLastActiveStackIndex = (8 - (iEndStackIndex - iStartStackIndex) % 8) + (iEndStackIndex - iStartStackIndex);

        if (iLastActiveStackIndex < iHorizontalStackCount * iVerticalStackCount)
            iLastActiveStackIndex = iHorizontalStackCount * iVerticalStackCount;

        int tSlotIndex;
        for (tSlotIndex = 0; tSlotIndex < iHorizontalStackCount * iVerticalStackCount; tSlotIndex++) {
            int tX = (tSlotIndex % iHorizontalStackCount) * 18;
            int tY = (tSlotIndex / iHorizontalStackCount) * 18;

            getComponents().add(new ComponentSlot(iHost, getInternalName() + ".Slot." + tSlotIndex, tSlotIndex, 18, 18, tX, tY, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));
        }

        iScrollbar = new ComponentScrollbar(this, getInternalName() + ".Scrollbar", iHorizontalStackCount * 18, 0, iVerticalStackCount * 18, iStartStackIndex, iLastActiveStackIndex, new Rectangle(0, 0, 0, iWidth, iHeight));
        iScrollbar.setDeltaValuePerClick(iHorizontalStackCount);

        getComponents().add(iScrollbar);

        return tSlotIndex;
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return iSubComponents.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton);
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
        if (pComponent.getInternalName().equals(getInternalName() + ".Scrollbar")) {
            iLastRenderedStackIndex = Integer.parseInt(pNewValue);
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
        return iHorizontalStackCount * 18 + 7;
    }

    @Override
    public int getYSize() {
        return iVerticalStackCount * 18;
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
    public boolean ShouldSlotBeVisible(Slot pSlot) {
        if (pSlot.getSlotIndex() >= iStartStackIndex && pSlot.getSlotIndex() < iLastActiveStackIndex && iActiveState) {
            return pSlot.getSlotIndex() >= iLastRenderedStackIndex && pSlot.getSlotIndex() < (iLastRenderedStackIndex + (iHorizontalStackCount - iVerticalStackCount));
        }

        return true;
    }

    @Override
    public void ModifyVisibleSlot(Slot pSlot) {
        if (!iActiveState)
            return;

        if (pSlot.getSlotIndex() >= iStartStackIndex && pSlot.getSlotIndex() < iLastActiveStackIndex && iActiveState) {
            int tInternalSlotIndex = pSlot.getSlotIndex() - iLastRenderedStackIndex;

            int tX = (tInternalSlotIndex % iHorizontalStackCount) * 18;
            int tY = (tInternalSlotIndex / iHorizontalStackCount) * 18;

            pSlot.xDisplayPosition = iHost.getXOrigin() + iLeft + tX;
            pSlot.yDisplayPosition = iHost.getYOrigin() + iTop + tY;
        }
    }

    public void setActiveState(boolean pState) {
        iActiveState = pState;

        if (pState) {
            iHost.getSlotManager().registerSlotController(this);
        } else {
            iHost.getSlotManager().getControlers().remove(this);
        }
    }
}

