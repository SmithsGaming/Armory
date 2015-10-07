package com.Orion.Armory.Common.Knowledge.Blueprint;

import com.Orion.Armory.API.Knowledge.IBlueprint;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Config.ArmoryConfig;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * Created by marcf on 10/4/2015.
 */
public class MedievalUpgradeBlueprint implements IBlueprint {

    IArmorMaterial iMaterial;

    public MedievalUpgradeBlueprint(IArmorMaterial pMaterial) {
        iMaterial = pMaterial;
    }

    @Override
    public String getID() {
        return iMaterial.getInternalMaterialName() + ".Medieval.Upgrade";
    }

    @Override
    public String getRecipeID() {
        return "";
    }

    @Override
    public float getMaxFloatValue() {
        return 1F;
    }

    @Override
    public float getMinFloatValue() {
        return 0F;
    }

    @Override
    public String getTranslatedQuality(float pFloatValue) {
        if (pFloatValue > 1F)
            return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.CreativeTier) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0F)
            return EnumChatFormatting.RED + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier1) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.45F)
            return EnumChatFormatting.GOLD + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier2) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.65F)
            return EnumChatFormatting.GREEN + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier3) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.85F)
            return EnumChatFormatting.BLUE + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier4) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.95F)
            return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier5) + EnumChatFormatting.RESET;

        return getTranslatedQuality(1.1F);
    }

    @Override
    public int handleRecipeResultFromItemStackForPlayer(EntityPlayer pPlayer, ItemStack pRecipeResult, float pBlueprintQuality) {
        return pRecipeResult.stackSize;
    }

    @Override
    public int handleRecipeResultFromItemStack(ItemStack pRecipeResult, float pBlueprintQuality) {
        return pRecipeResult.stackSize;
    }

    @Override
    public float getQualityDecrementOnTick(boolean pInGuide) {
        if (pInGuide) {
            return ArmoryConfig.medievalArmorUpgradeBlueprintDeteriation;
        }

        return ArmoryConfig.medievalArmorUpgradeBlueprintDeteriation;
    }

    @Override
    public String getProductionInfoLine(ItemStack pStack) {
        return StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.MedievalUpgrade1) + iMaterial.getVisibleNameColor() + iMaterial.getVisibleName() + EnumChatFormatting.RESET + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.MedievalUpgrade2);
    }
}
