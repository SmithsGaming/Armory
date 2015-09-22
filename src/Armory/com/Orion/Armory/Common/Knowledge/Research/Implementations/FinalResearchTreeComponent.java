/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge.Research.Implementations;

import com.Orion.Armory.Common.Knowledge.Research.StandardResearchTreeComponent;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class FinalResearchTreeComponent extends StandardResearchTreeComponent {

    ItemStack iBranchResult;

    public FinalResearchTreeComponent(ItemStack pTargetStack, ItemStack pBranchResult) {
        super(pTargetStack, References.InternalNames.InputHandlers.BookBinder.COMPLETE);

        iBranchResult = pBranchResult;
    }

    @Override
    public boolean isFinalComponentInBranch() {
        return true;
    }

    @Override
    public ItemStack getBranchResult() {
        return iBranchResult;
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToFallback(TranslationKeys.GUI.BookBinder.Research.ResearchComplete);
    }
}
