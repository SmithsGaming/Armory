package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class AnvilRecipe
{
    public int iTargetProgress;
    public int iHammerUsage;
    public int iTongUsage;

    public boolean iIsShapeLess = false;

    public IAnvilRecipeComponent[] iComponents = new IAnvilRecipeComponent[TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS];
    public IAnvilRecipeComponent[] iAdditionalComponents = new IAnvilRecipeComponent[TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS];

    private int iResultAmount = 1;
    private ItemStack iResult;

    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft) {
        if (pHammerUsagesLeft == 0)
            pHammerUsagesLeft = 150;

        if (pTongsUsagesLeft == 0)
            pTongsUsagesLeft = 150;

        if ((iHammerUsage > 0) && (pHammerUsagesLeft) < iHammerUsage)
            return false;

        if ((iTongUsage > 0) && (pTongsUsagesLeft < iTongUsage))
            return false;

        if (pCraftingSlotContents.length > TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (pAdditionalSlotContents.length > TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        if (!iIsShapeLess)
        {
            for (int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
                ItemStack tSlotContent = pCraftingSlotContents[tSlotID];

                if (tSlotContent != null) {
                    if (iComponents[tSlotID] == null) {
                        return false;
                    } else if (!iComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                        return false;
                    }
                } else if (iComponents[tSlotID] != null) {
                    return false;
                }
            }
        }
        else
        {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(iComponents.clone()));
            for(ItemStack tStack:pCraftingSlotContents) {
                boolean tFoundComponent = false;

                if (tStack == null)
                {
                    continue;
                }

                Iterator<IAnvilRecipeComponent> tIter = tComponentList.iterator();
                while (tIter.hasNext() && !tFoundComponent) {
                    IAnvilRecipeComponent tComponent = tIter.next();

                    if (tComponent != null) {
                        if (tComponent.isValidComponentForSlot(tStack))
                        {
                            tIter.remove();
                            tFoundComponent = true;
                        }
                    }
                }

                if (!tFoundComponent)
                    return false;
                else
                    continue;
            }

            for(IAnvilRecipeComponent tComponent: tComponentList)
            {
                if (tComponent != null)
                    return false;
            }
        }

        for (int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null) {
                if (iAdditionalComponents[tSlotID] == null) {
                    return false;
                } else if (!iAdditionalComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (iAdditionalComponents[tSlotID] != null) {
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

    public AnvilRecipe setCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent)
    {
        if (pSlotIndex >= TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return null;
        }

        iComponents[pSlotIndex] = pComponent;

        return this;
    }

    public AnvilRecipe setAdditionalCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent)
    {
        if (pSlotIndex >= TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS)
        {
            return null;
        }

        iAdditionalComponents[pSlotIndex] = pComponent;

        return this;
    }

    public AnvilRecipe setResult(ItemStack pResult)
    {
        iResultAmount = pResult.stackSize;
        iResult = pResult;

        return this;
    }

    public AnvilRecipe setProgress(int pNewProgress)
    {
        iTargetProgress = pNewProgress;

        return this;
    }

    public AnvilRecipe setHammerUsage(int pNewUsage)
    {
        iHammerUsage = pNewUsage;

        return this;
    }

    public AnvilRecipe setTongUsage(int pNewUsage)
    {
        iTongUsage = pNewUsage;

        return this;
    }

    public AnvilRecipe setShapeLess()
    {
        iIsShapeLess = true;
        return this;
    }

    public ItemStack getResult()
    {
        iResult.stackSize = iResultAmount;
        return iResult;
    }

}

