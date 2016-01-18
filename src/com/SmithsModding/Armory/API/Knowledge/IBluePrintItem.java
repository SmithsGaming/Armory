/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.knowledge;

import net.minecraft.item.*;

public interface IBluePrintItem {

    String getBlueprintID (ItemStack pStack);

    float getBluePrintQuality (ItemStack pStack);

    void setBluePrintQuality (ItemStack pStack, float pNewQuality);

    String getTranslatedBluePrintQuality (ItemStack pStack);
}
