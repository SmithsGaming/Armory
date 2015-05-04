package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
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
    private int iTargetProgress;

    IAnvilRecipeComponent[] iComponents = new IAnvilRecipeComponent[TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS];
    IAnvilRecipeComponent[] iAdditionalComponents = new IAnvilRecipeComponent[TileEntityArmorsAnvil.MAX_COOLSLOTS];

    private ItemStack iResult;

    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents)
    {
        if (pCraftingSlotContents.length > TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return false;
        }

        if (pAdditionalSlotContents.length > TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS)
        {
            return false;
        }

        for(int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotID ++)
        {
            ItemStack tSlotContent = pCraftingSlotContents[tSlotID];

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

        for(int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tSlotID ++)
        {
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null)
            {
                if (iAdditionalComponents[tSlotID] == null)
                {
                    return false;
                }
                else if(!iAdditionalComponents[tSlotID].isValidComponentForSlot(tSlotContent))
                {
                    return false;
                }
            }
            else if (iAdditionalComponents[tSlotID] != null)
            {
                return false;
            }
        }

        return true;
    }

    public IAnvilRecipeComponent getComponent(int pComponentIndex)
    {
        if (pComponentIndex >= TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return null;
        }

        return iComponents[pComponentIndex];
    }

    public IAnvilRecipeComponent getAdditionalComponent(int pComponentIndex)
    {
        if (pComponentIndex >= TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS)
        {
            return null;
        }

        return iAdditionalComponents[pComponentIndex];
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

