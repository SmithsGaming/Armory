package com.smithsmodding.armory.api.crafting.blacksmiths.component;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 *
 * Copyrighted according to Project specific license
 */
public interface IAnvilRecipeComponent {

    @Nullable ItemStack getComponentTargetStack();

    @NotNull IAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack);

    int getResultingStackSizeForComponent(ItemStack pComponentStack);

    @NotNull IAnvilRecipeComponent setComponentStackUsage(int pNewUsage);

    boolean isValidComponentForSlot(ItemStack pComparedItemStack);
}
