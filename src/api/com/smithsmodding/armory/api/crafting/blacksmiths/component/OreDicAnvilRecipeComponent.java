package com.smithsmodding.armory.api.crafting.blacksmiths.component;


import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p>
 * Copyrighted according to Project specific license
 */
public class OreDicAnvilRecipeComponent implements IAnvilRecipeComponent {

    private String oreDicName;
    private int componentUsage;

    public OreDicAnvilRecipeComponent(@Nonnull ItemStack pTargetStack) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(1);
    }

    public OreDicAnvilRecipeComponent(@Nonnull ItemStack pTargetStack, int pComponentUsage) {
        setComponentTargetStack(pTargetStack);
        setComponentStackUsage(pComponentUsage);
    }

    public OreDicAnvilRecipeComponent(String pOreDicName, int pComponentUsage) {
        oreDicName = pOreDicName;
        setComponentStackUsage(pComponentUsage);
    }


    @Nullable
    @Override
    public ItemStack getComponentTargetStack() {
        ArrayList<ItemStack> tStacks = new ArrayList<>(OreDictionary.getOres(oreDicName));

        if (tStacks.size() == 0) {
            return null;
        }

        return tStacks.get(0);
    }

    @Nonnull
    @Override
    public OreDicAnvilRecipeComponent setComponentTargetStack(@Nonnull ItemStack pNewTargetStack) {
        int[] tTargetIDs = OreDictionary.getOreIDs(pNewTargetStack);

        if (tTargetIDs.length == 0) {
            ModLogger.getInstance().error("The given Argument is not registered to the OreDictionary." + ItemStackHelper.toString(pNewTargetStack));
        }

        oreDicName = OreDictionary.getOreName(tTargetIDs[0]);

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(@Nonnull ItemStack pComponentStack) {
        if (!isValidComponentForSlot(pComponentStack)) {
            return pComponentStack.getCount();
        }

        return pComponentStack.getCount() - componentUsage;
    }

    @Nonnull
    @Override
    public OreDicAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        componentUsage = pNewUsage;

        return this;
    }

    @Override
    public boolean isValidComponentForSlot(@Nonnull ItemStack pComparedItemStack) {
        ArrayList<ItemStack> tStacks = new ArrayList<>(OreDictionary.getOres(oreDicName));
        ItemStack tSingleton = ItemStackHelper.cloneItemStack(pComparedItemStack, 1);

        for (ItemStack tStack : tStacks) {
            if (ItemStackHelper.equalsIgnoreStackSize(tSingleton, tStack) && pComparedItemStack.getCount() >= componentUsage)
                return true;
        }

        return false;
    }
}
