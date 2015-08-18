/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBlueprint {
    String getID();

    String getRecipeID();

    float getMaxFloatValue();

    float getMinFloatValue();

    String getTranslatedQuality(float pFloatValue);

    int handleRecipeResultFromItemStackForPlayer(EntityPlayer pPlayer, ItemStack pRecipeResult, float pBlueprintQuality);

    int handleRecipeResultFromItemStack(ItemStack pRecipeResult, float pBlueprintQuality);
}
