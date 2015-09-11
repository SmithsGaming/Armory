/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ComponentScrollableIInventory extends AbstractGUIMultiComponent implements IComponentHost {

    int iHorizontalStackCount;
    int iVerticalStackCount;

    int iStartStackIndex;
    int iEndStackIndex;

    private ComponentScrollbar iScrollbar;

    public ComponentScrollableIInventory(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pStartStackIndex, int pEndStackIndex, int pHorizontalStackCount, int pVerticalStackCount) {
        super(pHost, pInternalName, pLeft, pTop, pHorizontalStackCount * 18 + 7, pVerticalStackCount * 18);

        iHorizontalStackCount = pHorizontalStackCount;
        iVerticalStackCount = pVerticalStackCount;

        iStartStackIndex = pStartStackIndex;
        iEndStackIndex = Integer.parseInt(String.valueOf(iHost.getComponentRelatedObject(this.getInternalName() + ".StackCount")));

        if (iEndStackIndex < iStartStackIndex)
            iEndStackIndex = pEndStackIndex;

        initializeSlots(pStartStackIndex);
    }

    @Override
    public void onUpdate() {
        int tEnd = iEndStackIndex;

        iEndStackIndex = Integer.parseInt(String.valueOf(iHost.getComponentRelatedObject(this.getInternalName() + ".StackCount")));

        if (iEndStackIndex < iStartStackIndex)
            iEndStackIndex = tEnd;

        int tLastStackIndex = (8 - (iEndStackIndex - iStartStackIndex) % 8) + (iEndStackIndex - iStartStackIndex);

        if (tLastStackIndex < iHorizontalStackCount * iVerticalStackCount)
            tLastStackIndex = iHorizontalStackCount * iVerticalStackCount;

        iScrollbar.calculateRange(iHorizontalStackCount * iVerticalStackCount, tLastStackIndex);
    }

    private int initializeSlots(int pStartIndex) {
        getComponents().clear();

        int tLastStackIndex = (8 - (iEndStackIndex - iStartStackIndex) % 8) + (iEndStackIndex - iStartStackIndex);

        if (tLastStackIndex < iHorizontalStackCount * iVerticalStackCount)
            tLastStackIndex = iHorizontalStackCount * iVerticalStackCount;

        int tSlotIndex;
        for (tSlotIndex = 0; tSlotIndex < iHorizontalStackCount * iVerticalStackCount; tSlotIndex++) {
            int tX = (tSlotIndex % iHorizontalStackCount) * 18;
            int tY = (tSlotIndex / iHorizontalStackCount) * 18;

            getComponents().add(new ComponentSlot(iHost, getInternalName() + ".Slot." + tSlotIndex, tSlotIndex + pStartIndex, 18, 18, tX, tY, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));
        }

        if (iScrollbar == null) {
            iScrollbar = new ComponentScrollbar(this, getInternalName() + ".Scrollbar", iHorizontalStackCount * 18, 0, iVerticalStackCount * 18, iHorizontalStackCount * iVerticalStackCount, tLastStackIndex, new Rectangle(0, 0, 0, iWidth, iHeight));
            iScrollbar.setDeltaValuePerClick(iHorizontalStackCount);
        }
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
            initializeSlots(Integer.parseInt(pNewValue) - iHorizontalStackCount * iVerticalStackCount);
        }

        iHost.updateComponentResult(pComponent, pComponentID, String.valueOf(Integer.parseInt(pNewValue) - iHorizontalStackCount * iVerticalStackCount));
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
}
