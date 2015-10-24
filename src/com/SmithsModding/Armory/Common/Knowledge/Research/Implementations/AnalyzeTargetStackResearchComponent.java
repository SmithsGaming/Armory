/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Research.Implementations;

import com.SmithsModding.Armory.Common.Knowledge.Research.StandardResearchTreeComponent;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class AnalyzeTargetStackResearchComponent extends StandardResearchTreeComponent {

    public AnalyzeTargetStackResearchComponent(ItemStack pTargetStack) {
        super(pTargetStack, References.InternalNames.InputHandlers.BookBinder.ANALYZE);
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchAnalyseTargetStack);
    }

    @Override
    public boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        return super.matchesInput(pTargetStack, pInputID, pPlayer);
    }
}
