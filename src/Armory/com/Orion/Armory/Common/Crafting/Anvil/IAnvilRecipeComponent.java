package com.Orion.Armory.Common.Crafting.Anvil;

import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IAnvilRecipeComponent {

    ItemStack getComponentTargetStack();

    void setComponentTargetStack(ItemStack pNewTargetStack);

    int getResultingStackSizeForComponent(ItemStack pComponentStack);

    void setComponentStackUsage(int pNewUsage);

    boolean isValidComponentForSlot(ItemStack pComparedItemStack);
}
