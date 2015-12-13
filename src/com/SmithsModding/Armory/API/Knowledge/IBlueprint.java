/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Knowledge;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public interface IBlueprint {
    String getID ();

    String getRecipeID ();

    float getMaxFloatValue ();

    float getMinFloatValue ();

    String getTranslatedQuality (float pFloatValue);

    int handleRecipeResultFromItemStackForPlayer (EntityPlayer pPlayer, ItemStack pRecipeResult, float pBlueprintQuality);

    int handleRecipeResultFromItemStack (ItemStack pRecipeResult, float pBlueprintQuality);

    float getQualityDecrementOnTick (boolean pInGuide);

    String getProductionInfoLine (ItemStack pStack);
}
