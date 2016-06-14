package com.smithsmodding.armory.api.crafting.blacksmiths.component;

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

    IAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack);

    int getResultingStackSizeForComponent(ItemStack pComponentStack);

    IAnvilRecipeComponent setComponentStackUsage(int pNewUsage);

    boolean isValidComponentForSlot(ItemStack pComparedItemStack);
}
