/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import net.minecraft.item.ItemStack;

public interface IBluePrintItem {

    String getBlueprintID(ItemStack pStack);

    float getBluePrintQuality(ItemStack pStack);

    String getTranslatedBluePrintQuality(float pStackQuality);
}
