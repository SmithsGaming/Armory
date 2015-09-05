/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import com.Orion.Armory.Common.Item.Knowledge.ItemSmithingsGuide;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IBluePrintContainerItem {

    ArrayList<ItemSmithingsGuide.LabelledBlueprintGroup> getBlueprintGroups(ItemStack pStack);

    void writeBlueprintGroupsToStack(ItemStack pStack, ArrayList<ItemSmithingsGuide.LabelledBlueprintGroup> pGroups);
}
