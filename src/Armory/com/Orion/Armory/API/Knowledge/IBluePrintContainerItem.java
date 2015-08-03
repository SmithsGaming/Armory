/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IBluePrintContainerItem {

    ArrayList<ItemStack> getStoredBluePrints(ItemStack pStack);
}
