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

public class TargetItemStackSwitchResearchTreeComponent extends StandardResearchTreeComponent {

    public TargetItemStackSwitchResearchTreeComponent(ItemStack pTargetStack) {
        super(pTargetStack, References.InternalNames.InputHandlers.BookBinder.INPUTSWITCH);
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToFallback(TranslationKeys.GUI.BookBinder.Research.ResearchChangeTargetStackStart + " " + iTargetStack.getDisplayName() + " " + StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchChangeTargetStackEnd));
    }
}
