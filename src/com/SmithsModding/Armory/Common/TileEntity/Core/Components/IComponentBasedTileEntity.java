package com.SmithsModding.Armory.Common.TileEntity.Core.Components;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 30.06.2015
 * 11:17
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IComponentBasedTileEntity {
    ArrayList<ItemStack> getComponentStacks();

    void setComponents(ArrayList<ItemStack> pComponentStacks);

    void updateComponents();

    int getComponentAmount();

    int getComponentStartingSlotIndex();


    @SideOnly(Side.CLIENT)
    GuiContainer getModificationGui(IComponentBasedTileEntity pTileEntityToBeConfigured);
}
