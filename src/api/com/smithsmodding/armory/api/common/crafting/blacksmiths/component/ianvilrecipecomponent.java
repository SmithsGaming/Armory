package com.smithsmodding.armory.api.common.crafting.blacksmiths.component;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 *
 * Copyrighted according to Project specific license
 */
public interface IAnvilRecipeComponent extends Cloneable {

    /**
     * Method to getCreationRecipe the Stack that should be in the slot that this component represents.
     * @return Null if no stack should be their or the ItemStack expected.
     */
    @Nullable
    ItemStack getComponentTargetStack();

    /**
     * Method to set the Stack that should be in the slot that this component represents.
     * @param targetStack  Null if no stack should be their or the ItemStack expected.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipeComponent setComponentTargetStack(ItemStack targetStack);

    /**
     * Method to getCreationRecipe the amount of Items left in the stack after crafting.
     * @param componentStack The stack to check for.
     * @return the amount of Items left in the stack after crafting.
     */
    int getResultingStackSizeForComponent(ItemStack componentStack);

    /**
     * Method to getCreationRecipe the amount of Items left in the stack after crafting.
     * @param newUsage the amount to use of a stack in a crafting attempt.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipeComponent setComponentStackUsage(int newUsage);

    /**
     * Method to check whether or not this component matches the given stack.
     * @param comparedItemStack The stack to check.
     * @return True when the stack can be used in crafting. False when not.
     */
    boolean isValidComponentForSlot(ItemStack comparedItemStack);
}
