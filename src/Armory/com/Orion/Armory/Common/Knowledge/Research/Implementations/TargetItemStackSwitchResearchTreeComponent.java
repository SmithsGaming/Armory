/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge.Research.Implementations;

import com.Orion.Armory.API.Knowledge.IResearchTreeComponent;
import com.Orion.Armory.Common.Knowledge.Research.StandardResearchTreeComponent;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class TargetItemStackSwitchResearchTreeComponent extends StandardResearchTreeComponent {

    public TargetItemStackSwitchResearchTreeComponent(ItemStack pTargetStack) {
        super(pTargetStack, References.InternalNames.InputHandlers.BookBinder.INPUTSWITCH);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ItemStackHelper.equalsIgnoreStackSize(iTargetStack, ((IResearchTreeComponent) obj).getTargetStack());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 4 * iTargetStack.hashCode();
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchChangeTargetStackStart + " " + iTargetStack.getDisplayName() + " " + StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchChangeTargetStackEnd));
    }
}
