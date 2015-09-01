/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.Core;

import com.Orion.Armory.Common.Inventory.ContainerArmory;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IComponentHost {
    ContainerArmory getContainer();

    Object getComponentRelatedObject(String pComponentID);

    float getProgressBarValue(String pProgressBarID);

    ItemStack getItemStackInSlot(int pSlotIndex);

    void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue);

    int getXOrigin();

    int getYOrigin();

    int getXSize();

    int getYSize();

    void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer);
}
