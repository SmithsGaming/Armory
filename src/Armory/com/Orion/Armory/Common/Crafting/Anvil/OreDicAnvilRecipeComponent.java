package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p/>
 * Copyrighted according to Project specific license
 */
public class OreDicAnvilRecipeComponent implements IAnvilRecipeComponent {

    private String iOreDicName;
    private int iComponentUsage;

    OreDicAnvilRecipeComponent(ItemStack pTargetStack)
    {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(1);
    }

    OreDicAnvilRecipeComponent(ItemStack pTargetStack, int pComponentUsage)
    {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(pComponentUsage);
    }

    OreDicAnvilRecipeComponent(String pOreDicName, int pComponentUsage)
    {
        iOreDicName = pOreDicName;
        setComponentStackUsage(pComponentUsage);
    }



    @Override
    public ItemStack getComponentTargetStack() {
        ArrayList<ItemStack> tStacks  = OreDictionary.getOres(iOreDicName);

        if (tStacks.size() == 0) { return null; }

        return tStacks.get(0);
    }

    @Override
    public OreDicAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        int[] tTargetIDs = OreDictionary.getOreIDs(pNewTargetStack);

        if (tTargetIDs.length == 0)
        {
            GeneralRegistry.iLogger.error("The given Argument is not registered to the OreDictionary." + ItemStackHelper.toString(pNewTargetStack));
        }

        iOreDicName = OreDictionary.getOreName(tTargetIDs[0]);

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        if (!isValidComponentForSlot(pComponentStack))
        {
            return pComponentStack.stackSize;
        }

        return pComponentStack.stackSize - iComponentUsage;
    }

    @Override
    public OreDicAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        iComponentUsage = pNewUsage;

        return this;
    }

    @Override
    public boolean isValidComponentForSlot(ItemStack pComparedItemStack) {
        ArrayList<ItemStack> tStacks = OreDictionary.getOres(iOreDicName);
        ItemStack tSingleton = ItemStackHelper.cloneItemStack(pComparedItemStack, 1);

        if (tStacks.contains(tSingleton) && pComparedItemStack.stackSize >= iComponentUsage)
        {
            return true;
        }

        return false;
    }
}
