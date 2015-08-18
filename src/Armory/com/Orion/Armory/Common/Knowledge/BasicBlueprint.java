/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge;

import com.Orion.Armory.API.Knowledge.IBlueprint;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class BasicBlueprint implements IBlueprint {

    static Random iRand = new Random();

    String iID;
    String iRecipeID;
    float iMaxFloatValue;
    float iMinFloatValue;

    public BasicBlueprint(String pID, String pRecipeID) {
        iID = pID;
        iRecipeID = pRecipeID;

        iMaxFloatValue = 1F;
        iMinFloatValue = 0F;
    }

    @Override
    public String getID() {
        return iID;
    }

    @Override
    public String getRecipeID() {
        return iRecipeID;
    }

    @Override
    public float getMaxFloatValue() {
        return iMaxFloatValue;
    }

    @Override
    public float getMinFloatValue() {
        return iMinFloatValue;
    }

    @Override
    public String getTranslatedQuality(float pFloatValue) {
        if (pFloatValue > iMaxFloatValue)
            return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.CreativeTier) + EnumChatFormatting.RESET;

        if (pFloatValue <= iMinFloatValue)
            return EnumChatFormatting.RED + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Tier1) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.45F)
            return EnumChatFormatting.GOLD + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Tier2) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.65F)
            return EnumChatFormatting.GREEN + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Tier3) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.85F)
            return EnumChatFormatting.BLUE + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Tier4) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.95F)
            return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Tier5) + EnumChatFormatting.RESET;

        return getTranslatedQuality(iMaxFloatValue + 1F);
    }

    @Override
    public int handleRecipeResultFromItemStackForPlayer(EntityPlayer pPlayer, ItemStack pRecipeResult, float pBlueprintQuality) {
        if (pRecipeResult.stackSize > 1) {
            if (pBlueprintQuality > iMaxFloatValue)
                return pRecipeResult.stackSize;

            if (pBlueprintQuality <= iMinFloatValue)
                return (int) (pRecipeResult.stackSize * 0.5);

            if (pBlueprintQuality <= 0.45F)
                return (int) (pRecipeResult.stackSize * 0.6);

            if (pBlueprintQuality <= 0.65F)
                return (int) (pRecipeResult.stackSize * 0.75);

            if (pBlueprintQuality <= 0.85F)
                return (int) (pRecipeResult.stackSize * 0.95);

            if (pBlueprintQuality <= 0.95F)
                return (int) (pRecipeResult.stackSize * ((95F + iRand.nextInt(10)) / 100));

            return pRecipeResult.stackSize;
        } else {
            if (pBlueprintQuality > iMaxFloatValue)
                return pRecipeResult.stackSize;

            if (pBlueprintQuality <= iMinFloatValue)
                return 0;

            if (pBlueprintQuality <= 0.45F)
                return 0;

            if (pBlueprintQuality <= 0.65F)
                return iRand.nextInt(1);

            if (pBlueprintQuality <= 0.85F)
                return iRand.nextInt(4) > 0 ? 1 : 0;

            if (pBlueprintQuality <= 0.95F)
                return 1;

            return pRecipeResult.stackSize;
        }
    }

    @Override
    public int handleRecipeResultFromItemStack(ItemStack pRecipeResult, float pBlueprintQuality) {
        return handleRecipeResultFromItemStackForPlayer(null, pRecipeResult, pBlueprintQuality);
    }
}
