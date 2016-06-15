package com.smithsmodding.armory.api.crafting.blacksmiths.component;

import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StandardAnvilRecipeComponent implements IAnvilRecipeComponent {
    private ItemStack iTargetItemStack;
    private int iComponentUsage;

    public StandardAnvilRecipeComponent(ItemStack pTargetStack) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(1);
    }

    public StandardAnvilRecipeComponent(ItemStack pTargetStack, int pComponentUsage) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(pComponentUsage);
    }

    @Override
    public ItemStack getComponentTargetStack() {
        return iTargetItemStack;
    }

    @Override
    public StandardAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        iTargetItemStack = pNewTargetStack;

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        if (ItemStackHelper.equalsIgnoreStackSize(pComponentStack, iTargetItemStack)) {
            return pComponentStack.stackSize - iComponentUsage;
        }


        return pComponentStack.stackSize;
    }

    @Override
    public StandardAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        iComponentUsage = pNewUsage;

        return this;
    }

    @Override
    public boolean isValidComponentForSlot(ItemStack pComparedItemStack) {
        if (ItemStackHelper.equalsIgnoreStackSize(pComparedItemStack, iTargetItemStack)) {
            return getResultingStackSizeForComponent(pComparedItemStack) >= 0;
        }

        return false;
    }
}
