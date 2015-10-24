/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Research.Implementations;

import com.SmithsModding.Armory.API.Knowledge.IResearchTreeComponent;
import com.SmithsModding.Armory.Common.Knowledge.Research.StandardResearchTreeComponent;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.Core.ItemStackHelper;
import com.SmithsModding.Armory.Util.References;
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
        return StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchChangeTargetStackStart) + " " + iTargetStack.getDisplayName();
    }
}
