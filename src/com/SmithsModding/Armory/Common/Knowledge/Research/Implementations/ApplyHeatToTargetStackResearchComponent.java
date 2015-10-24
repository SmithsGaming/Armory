/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Research.Implementations;

import com.SmithsModding.Armory.Common.Knowledge.Research.StandardResearchTreeComponent;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ApplyHeatToTargetStackResearchComponent extends StandardResearchTreeComponent {

    public ApplyHeatToTargetStackResearchComponent(ItemStack pTargetStack) {
        super(pTargetStack, References.InternalNames.InputHandlers.BookBinder.HEAT);
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchHeatTargetStack);
    }
}
