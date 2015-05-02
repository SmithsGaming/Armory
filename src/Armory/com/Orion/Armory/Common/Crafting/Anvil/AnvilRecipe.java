package com.Orion.Armory.Common.Crafting.Anvil;

import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class AnvilRecipe
{
    public static final int MAX_ELEMENTS = 25;

    private int iTargetProgress;

    IAnvilRecipeComponent[] iComponents = new IAnvilRecipeComponent[MAX_ELEMENTS];

    private ItemStack iResult;

    public boolean matchesRecipe(ItemStack[] pSlotContents)
    {
        if (pSlotContents.length > MAX_ELEMENTS)
        {
            return false;
        }

        for(int tSlotID = 0; tSlotID < MAX_ELEMENTS; tSlotID ++)
        {
            ItemStack tSlotContent = pSlotContents[tSlotID];

            if (tSlotContent != null)
            {
                if (iComponents[tSlotID] == null)
                {
                    return false;
                }
                else if(!iComponents[tSlotID].isValidComponentForSlot(tSlotContent))
                {
                    return false;
                }
            }
            else if (iComponents[tSlotID] != null)
            {
                return false;
            }
        }

        return true;
    }

    public boolean recipeComplete(int pCurrentProgress)
    {
        return (iTargetProgress == pCurrentProgress);
    }

    public ItemStack getResult()
    {
        return iResult;
    }

}

