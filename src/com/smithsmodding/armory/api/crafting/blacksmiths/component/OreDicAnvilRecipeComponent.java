package com.smithsmodding.armory.api.crafting.blacksmiths.component;


import com.smithsmodding.armory.Armory;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

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

    public OreDicAnvilRecipeComponent(ItemStack pTargetStack) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(1);
    }

    public OreDicAnvilRecipeComponent(ItemStack pTargetStack, int pComponentUsage) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(pComponentUsage);
    }

    public OreDicAnvilRecipeComponent(String pOreDicName, int pComponentUsage) {
        iOreDicName = pOreDicName;
        setComponentStackUsage(pComponentUsage);
    }


    @Override
    public ItemStack getComponentTargetStack() {
        ArrayList<ItemStack> tStacks = new ArrayList<>(OreDictionary.getOres(iOreDicName));

        if (tStacks.size() == 0) {
            return null;
        }

        return tStacks.get(0);
    }

    @Override
    public OreDicAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        int[] tTargetIDs = OreDictionary.getOreIDs(pNewTargetStack);

        if (tTargetIDs.length == 0) {
            Armory.getLogger().error("The given Argument is not registered to the OreDictionary." + ItemStackHelper.toString(pNewTargetStack));
        }

        iOreDicName = OreDictionary.getOreName(tTargetIDs[0]);

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        if (!isValidComponentForSlot(pComponentStack)) {
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
        ArrayList<ItemStack> tStacks = new ArrayList<>(OreDictionary.getOres(iOreDicName));
        ItemStack tSingleton = ItemStackHelper.cloneItemStack(pComparedItemStack, 1);

        for (ItemStack tStack : tStacks) {
            if (ItemStackHelper.equalsIgnoreStackSize(tSingleton, tStack) && pComparedItemStack.stackSize >= iComponentUsage)
                return true;
        }

        return false;
    }
}
